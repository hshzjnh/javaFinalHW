package myorganism;
import huluwabattle.Controller;

import javax.swing.*;
import javafx.animation.PathTransition;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import myinterfaces.CreatureActions;
import myinterfaces.OrganismSettings.STATUS;
import organismdata.Detail;
import organismdata.Detail.EVENT;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
public class Huluwa <T extends Organism> extends Organism implements CreatureActions{
    // protected static Random rand = new Random();
    protected String rank;
    protected String color;
    protected int num;
    public Huluwa() {
        direction=DIRECTION.RIGHT;
        deadImage=new ImageView(new Image("file:src\\main\\resources\\image\\deadHuluwa.png"));
        deadImage.setVisible(false);
        getChildren().addAll(deadImage);
        //type=Species.HULUWA;
    }
   

    public int getnum() {
        return num;
    }
    public String getRank() {
        return rank;
    }
    public String getColor() {
        return color;
    }


    public void searchEnemy()
    {

    }

    public void move() {
        synchronized (this) {
            Position nextPos = getNextPos();
            if (nextPos == null) {
                myStatus = STATUS.ATTACK;
                return;
            }
            moveImage(50*(nextPos.getY()-mypos.getY()),50*(nextPos.getX()-mypos.getX()));
            nextPos.set(this);
            myStatus = STATUS.ATTACK;
        }

    }
    public void attack()
    {
        synchronized (this) {
            int x = mypos.getX();
            int y = mypos.getY();
            for (int i = x - 1; i <= x + 1; i++) {
                if (i < 0 || i >=myData.getHeight())
                    continue;
                for (int j = y - 1; j <= y + 1; j++) {
                    if (j < 0 || j >= myData.getWidth())
                        continue;
                    if (i == x && j == y)
                        continue;
                    synchronized (this) {
                        T enemy = (T) position[i][j].get();
                        if (enemy != null && (Monster.class.isInstance(enemy) || Snack.class.isInstance(enemy) || Scorpion.class.isInstance(enemy))) {
                            enemy.deductHP(myData.getAtk());
                            break;
                        }

                    }
                }
            }
            myStatus = STATUS.MOVE;
        }
    }
    public void recover(int atk) {
        synchronized (this) {
        	//System.out.println("huluwa recover");
        	if(myData.getMaxHp()<=myData.getHp())
        		return;
            myData.setHp(myData.getHp()+atk);
        }
    }
    public void run() {
    	if(modeReplay)
    		run_replay();
        while(!exit)
        {
            synchronized (this) {
                if (status != STATUS.FIGHTING ) {
                    exitThread();break;
                }
            }
            System.out.println(this.getClass().getName());
            System.out.println(myStatus);
            synchronized (this){
                switch (myStatus)
                {
                    case MOVE:record.setDetail(new Detail(EVENT.ATTACK, this.getClass().getName(), myData, System.currentTimeMillis()-timer));
                        attack(); break;
                    case ATTACK:record.setDetail(new Detail(EVENT.ATTACK, this.getClass().getName(), myData, System.currentTimeMillis()-timer));
                    	attack();break;
                    case DIE:record.setDetail(new Detail(EVENT.DIE, this.getClass().getName(), myData, System.currentTimeMillis()-timer));die();break;
                    default:Thread.yield();
                }
            }
           try {
                Thread.sleep(myData.getTimeInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(this.getClass().getName()+" exit!");
    }
    public synchronized void run_replay() {
		long startTime=System.currentTimeMillis();
		for(int i=0;i<record2.getDetails().size();i++) {
			Detail detail=record2.getDetails().get(i);
			synchronized (this) {
                if (status != STATUS.FIGHTING ) {
                    exitThread();break;
                }
            }
            synchronized (this) {
                switch (detail.getEvent()) {
                    case MOVE: {
                        move();
                        break;
                    }
                    case ATTACK:
                        attack();
                        break;
                    case DIE:
                        die();
                        myStatus=STATUS.WAIT;
                        break;
                    default:
                    	//Thread.yield();
                        break;
                }
                try {
                    Thread.sleep(myData.getTimeInterval());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
		}
	}
}

