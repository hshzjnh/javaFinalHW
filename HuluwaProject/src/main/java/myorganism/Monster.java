package myorganism;
import huluwabattle.Controller;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import myinterfaces.CreatureActions;
import organismdata.Detail;
import organismdata.OrganismData;
import organismdata.Detail.EVENT;

import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Monster <T extends Organism> extends Organism implements CreatureActions {
    public Monster()
    {
        super();
        direction=DIRECTION.LEFT;
        imageView=new ImageView(new Image("file:src"+File.separator+"main"+File.separator+"resources"+File.separator+"image"+File.separator+"Monster_.png"));
        deadImage=new ImageView(new Image("file:src"+File.separator+"main"+File.separator+"resources"+File.separator+"image"+File.separator+"deadMonster.png"));
        deadImage.setVisible(false);
        getChildren().addAll(imageView,deadImage);
        myData.setAtk(1000);
        myData.setDef(220);
        myData.setHp(2000);
        myData.setMaxHp(2000);
        myData.setDodge(10);
        myData.setSkillCount(1);
    }
   
    public void setLayout() {
        layoutx=50 * mypos.getY()+15;
        layouty=50 * mypos.getX()+150-15;
     //   myData.setLayoutx(50 * mypos.getY()+13);
      //  myData.setLayouty(50 * mypos.getX()+150-15);
        this.setLayoutX(layoutx);
        this.setLayoutY(layouty);
        deadImage.setLayoutX(0);
        deadImage.setLayoutY(0);
     //   imageView.setLayoutX(layoutx);
     //   imageView.setLayoutY(layouty);
    //    imageView.setLayoutX(layoutx);
      //  imageView.setLayoutY(layouty);
    }
    public synchronized void move() {
        	if(mypos.getY()==0) {
        		mypos.set(null);
        		monsterGoal+=1;
        		setImageVisible(false, false);
        		deadMonsterList.add(this);
        		this.myStatus=STATUS.DIE;
        	}
            Position nextPos = getNextPos();
            if (nextPos == null||nextPos.get()!=null) {
                myStatus = STATUS.ATTACK;
                return;
            }
            moveImage(50*(nextPos.getY()-mypos.getY()),50*(nextPos.getX()-mypos.getX()));
            nextPos.set(this);
            myData.setX(nextPos.getX());
            myData.setY(nextPos.getY());
            myStatus = STATUS.ATTACK;


    }
    public Position getNextPos() {
        synchronized (this) {
            synchronized (position) {
            	
                if (mypos.getY() > 0 && getLeft() == null)
                    return position[mypos.getX()][mypos.getY() - 1];
                //       else if (mypos.getX() > 0 && getUp() == null)
                //         return position[mypos.getX() - 1][mypos.getY()];
                //   else if (mypos.getX() < 0 && getDown() == null)
                //     return position[mypos.getX() + 1][mypos.getY()];
                //     else if (mypos.getY() < width - 1 && getRight() == null)
//                return position[mypos.getX()][mypos.getY() + 1];
            }
        }
        return null;
    }
    public void moveImage(int dx,int dy) {

        synchronized (this) {
            int x1 = (int) layoutx;
            int y1 = (int) layouty;
            int x2 = x1 - 50;
            int y2 = y1;
            layoutx=x2;
            new Thread(() -> {
                Semaphore semaphore = new Semaphore(1);
                try {
                    semaphore.acquire();
                    Platform.runLater(() -> {
                        Path path = new Path();
                        if (direction == DIRECTION.LEFT) {
                            if (x2 >= 0 && y2 >= 0) {
                                this.setLayoutX(x2);
                            }
                            path.getElements().add(new MoveTo(50, 0));
                            path.getElements().add(new LineTo(0, 0));
                        } else {
                            myData.setLayoutx(x1 + 50);
                            this.setLayoutX(x1);
                            path.getElements().add(new MoveTo(0, 0));
                            path.getElements().add(new LineTo(50, 0));
                        }
                        pathTransition = new PathTransition();
                        pathTransition.setDuration(Duration.millis(300));
                        pathTransition.setPath(path);
                        pathTransition.setNode(this.imageView);
                        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                        pathTransition.setCycleCount(1);
                        pathTransition.setAutoReverse(true);
                        pathTransition.play();
                        semaphore.release();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }



    public void exitThread()
    {
        exit=true;
        myData.setAlive(false);
        if(mypos!=null)
            mypos.set(null);
    }
    public void attack()
    {
        int x=mypos.getX();
        int y=mypos.getY();
        for (int i = x - 1; i <= x + 1; i++) {
            if (i < 0 || i >= myData.getHeight())
                continue;
            for (int j = y - 1; j <= y + 1; j++) {
                if (j < 0 || j >= myData.getWidth())
                    continue;
                synchronized (this) {
                	T enemy = (T) position[i][j].get();
                	if (enemy != null && Huluwa.class.isInstance(enemy)) {
                   
                        enemy.deductHP(myData.getAtk());
                        break;
                    }

                }
            }
        }
        myStatus=STATUS.MOVE;

    }
    public void die() {
        synchronized (this) {
        	monsterKilled+=1;
            mypos.set(null);
            setImageVisible(false, true);
            if(Monster.class.isInstance(this))
            	deadMonsterList.add(this);
        }
    }
    public void resurrect(int hp) {
    /*	if(modeReplay) {
    		 myStatus = STATUS.MOVE;
             imageView.setVisible(true);
             deadImage.setVisible(false);
             mypos = null;
    		position[myData.getX()][myData.getY()].set(this);
    		return;
    	}*/
        int count=0;
        while(true)
        {
          //  Random r =new Random();
           // int t=r.nextInt(myData.getHeight());
            synchronized (this) {
                if (position[myData.getDefaultX()][myData.getWidth() - 2].get() == null) {
                    myStatus = STATUS.MOVE;
                    setImageVisible(true, false);
                 //   imageView.setVisible(true);
                   // deadImage.setVisible(false);
                    mypos = null;
                    position[myData.getDefaultX()][myData.getWidth() - 2].set(this);               
                    myData.setHp(hp);
                    myData.setAlive(true);
                    myData.setX(myData.getDefaultX());
                    myData.setY(myData.getWidth()-2);
                    record.setDetail(new Detail(EVENT.RESURRECT, this.getClass().getName(), myData, System.currentTimeMillis()-timer));
                    break;
                }
                else {
					Thread.yield();
				}
            }
            if(count++>5)
                break;
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
                        die();
                        myData.setAlive(false);
                        myStatus=STATUS.WAIT;
                        break;
                    default:
                    	Thread.yield();
                        break;
                }
            }
        }
        this.setImageVisible(false, false);
        System.out.println(this.getClass().getName()+" exit!");

    }
    public void run_replay() {
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
                        try {
                            Thread.sleep(myData.getTimeInterval());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case ATTACK:
                    		attack();
                        break;
                    case DIE:
                        die();
                        myData.setAlive(false);
                        myStatus=STATUS.WAIT;
                        break;
                    default:
                        break;
                }
            }
            
		}
	}
}


