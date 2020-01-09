package myorganism;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import myorganism.Organism.DIRECTION;

import organismdata.Detail;
import organismdata.Detail.EVENT;

import java.io.File;
import java.util.Vector;

public class Scorpion<T extends Organism> extends Monster<Organism> {
    public Scorpion() {
    	direction=DIRECTION.LEFT;
        imageView=new ImageView(new Image("file:src"+File.separator+"main"+File.separator+"resources"+File.separator+"image"+File.separator+"scorpion.png"));
        deadImage=new ImageView(new Image("file:src"+File.separator+"main"+File.separator+"resources"+File.separator+"image"+File.separator+"deadScorpion.png"));
        deadImage.setVisible(false);
        getChildren().clear();
        getChildren().addAll(imageView,deadImage);
        myData.setAtk(1500);
        myData.setDef(720);
        myData.setHp(3000);
        myData.setMaxHp(3000);
        myData.setDodge(20);
        myData.setDefaultX(3);
        myData.setDefaultY(8);
        myData.setSkillCount(1);
        myData.setTimeInterval(800);
    }
 
/*    public void run()
    {
    	
    }*/
 /*   public void run()
    {
    	if(status==STATUS.REPLAY)
    		run_replay();
        while(!exit)
        {
            synchronized (this) {
                if (status != STATUS.FIGHTING ) {
                    exitThread();break;
                }
            }
            synchronized (this) {
                switch (myStatus) {
                    case MOVE: {
                    	record.setDetail(new Detail(EVENT.MOVE, this.getClass().getName(), myData, System.currentTimeMillis()-timer));
                        move();
                        try {
                            Thread.sleep(myData.getTimeInterval());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case ATTACK:
                    	record.setDetail(new Detail(EVENT.ATTACK, this.getClass().getName(), myData, System.currentTimeMillis()-timer));
                        attack();
                        break;
                    case DIE:
                    	record.setDetail(new Detail(EVENT.DIE, this.getClass().getName(), myData, System.currentTimeMillis()-timer));
                        //System.out.println(this.getClass().getName()+"   die");
                        die();
                        myData.setAlive(false);
                        myStatus=STATUS.WAIT;
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.println(this.getClass().getName()+" exit!");
    }
   */
}