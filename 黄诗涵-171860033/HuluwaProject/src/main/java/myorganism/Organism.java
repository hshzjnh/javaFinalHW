package myorganism;
import huluwabattle.Controller;
import organismdata.Record;
import organismdata.Detail.EVENT;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import myinterfaces.CreatureActions;
import myinterfaces.CreatureThread;
import myinterfaces.CreatureUI;
import myinterfaces.OrganismSettings;

import javax.swing.*;
import java.util.Random.*;
import java.util.*;
import java.awt.*;
import java.util.concurrent.Semaphore;
import java.io.Serializable;
import organismdata.*;
public class Organism<T> extends Parent implements Runnable,CreatureActions,CreatureUI,CreatureThread,OrganismSettings{
    enum DIRECTION{RIGHT,LEFT,UP,DOWN};
    protected static STATUS status = STATUS.FIGHTING;
    protected STATUS myStatus= STATUS.MOVE;
    protected DIRECTION direction;
    protected static int monsterGoal=0;
    protected static int monsterKilled=0;
    protected static boolean modeReplay=false;
    protected OrganismData myData=new OrganismData();
    protected Record record=new Record();
    protected Record record2;
    public static AnchorPane myAnchorPane=new AnchorPane();
    protected Position[][] position;
   // protected Vector<Action> record;
    protected Position mypos;
    protected ImageView imageView;
    protected ImageView deadImage;
    protected PathTransition pathTransition = new PathTransition();
    protected Vector<T> deadHuluwaList=new Vector<>();
    protected static Vector<Monster> deadMonsterList=new Vector<Monster>();
    protected int layoutx=0;
    protected int layouty=0;
    protected long timer=0;
    protected volatile boolean exit = false;
    
    public Organism() {
    	timer=System.currentTimeMillis();
        pathTransition.setNode(this.imageView);
        myAnchorPane.setLayoutX(0);
        myAnchorPane.setLayoutY(0);
    }
    public Organism(OrganismData data) {
        myData=data;
        pathTransition.setNode(this.imageView);
        myAnchorPane.setLayoutX(0);
        myAnchorPane.setLayoutY(0);
    }
    public AnchorPane getMyAnchorPane()
    {
        return myAnchorPane;
    }
    public STATUS getStatus()
    {
    	return status;
    }
    public void initMyAnchorPane()
    {
    	myAnchorPane=new AnchorPane();
    }
    public void setStatus(STATUS status)
    {
    	this.status=status;
    }
    public void setModeReplay(boolean f) {
    	this.modeReplay=f;
    }
    public void setMonsterGoal(int i) {
    	this.monsterGoal=i;
    }
    public void setMonsterKilled(int i) {
    	this.monsterKilled=i;
    }
    public void initMonsterList() {
    	deadMonsterList=new Vector<Monster>();
    }
    public void setImageVisible(boolean f1,boolean f2)
    {
    	if(this.imageView!=null)
    		this.imageView.setVisible(f1);
    	if(this.deadImage!=null)
    		this.deadImage.setVisible(f2);
    }
    public void setLayout() {
    	synchronized (this) {
			
		
        layoutx=50 * mypos.getY();
        layouty=50*mypos.getX()+100;
        this.setLayoutX(layoutx);
        this.setLayoutY(layouty);
    	}
    }
    public void setData(OrganismData data) {
        myData=data;
    }
    public OrganismData getData() {
        return myData;
    }
    public void setRecord(Record record) {
    	this.record2=record;
    }
    public Record getRecord(){
    	return record;
    }
    public void setpos(Position p) {
        String[] classname = this.getClass().getName().split("\\.");
        if (this.mypos != null) {
            if (p != null) {
                mypos.die();
                System.out.printf("%s(ID:%s) : %s->%s\n", classname[1], myData.getId(), mypos.coordinateStr(), p.coordinateStr());
            } else
                mypos.die();
        } else {
            if (p != null) {
                mypos = p;
                setLayout();
                System.out.printf("%s(ID:%s) : …œ≥° %s\n", classname[1], myData.getId(), p.coordinateStr());
            }
        }
        mypos = p;

    }

    public void setId(int id) {
        this.myData.setId(id);
    }

    public int getId_() {
        return myData.getId();
    }

    public boolean isAlive() {
        return myData.getAlive();
    }
    public void switchDirection()
    {
        if(direction==DIRECTION.RIGHT)
            direction=DIRECTION.LEFT;
        else if(direction==DIRECTION.LEFT)
            direction=DIRECTION.RIGHT;
    }
    public T getRight()
    {
        if(mypos.getY()<myData.getWidth()-1) {
            int x=mypos.getX();
            int y=mypos.getY();
            T creature=(T)position[mypos.getX()][mypos.getY() + 1].get();
            return creature;
        }
        else {
            switchDirection();
            return null;
        }

    }
    public T getUp() {
        if (mypos.getX()<=0)
            return null;
        return (T)position[mypos.getX()-1][mypos.getY()].get();
    }
    public T getDown() {
        if (mypos.getX()>=myData.getWidth()-1)
            return null;
        return (T)position[mypos.getX()+1][mypos.getY()].get();
    }
    public T getLeft()
    {
        if (mypos.getY()<=0){
            switchDirection();
            return null;
        }
        return (T)position[mypos.getX()][mypos.getY()-1].get();
    }
    public Position getNextPos() {
        synchronized (this) {
            if(direction==DIRECTION.RIGHT) {
                if ((mypos.getY() < myData.getWidth() - 1) && getRight() == null)
                    return position[mypos.getX()][mypos.getY() + 1];
            }
     //       else if (mypos.getX() > 0 && getUp() == null)
       //         return position[mypos.getX() - 1][mypos.getY()];
         //   else if (mypos.getX() < 0 && getDown() == null)
           //     return position[mypos.getX() + 1][mypos.getY()];
            else if(direction==DIRECTION.LEFT) {
                if (mypos.getY() > 0 && getLeft() == null)
                    return position[mypos.getX()][mypos.getY() - 1];
            }
            return null;
        }
    }
   
    public void moveImage(int dx,int dy) {
        synchronized (this) {
            int x1 = (int) layoutx;
            int y1 = (int) layouty;
            int x2 = x1+dx;
            int y2 = y1;
            layoutx=x2;
           // myData.setLayoutx(x2);
            //   this.layouty=y2;
            new Thread(() -> {
                Semaphore semaphore = new Semaphore(1);
                try {
                    semaphore.acquire();
                    Platform.runLater(() -> {
                        Path path = new Path();
                        if(dx>=0) {
                            this.setLayoutX(x1);
                            path.getElements().add(new MoveTo(0, 0));
                            path.getElements().add(new LineTo(50, 0));
                        }
                        else {
                            this.setLayoutX(x2);
                            path.getElements().add(new MoveTo(50, 0));
                            path.getElements().add(new LineTo(00, 0));
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
        if(mypos!=null)
            mypos.set(null);
    }
    public double getlx()
    {
        return imageView.getLayoutX();
    }
    public double getly()
    {
        return imageView.getLayoutY();
    }
    public void attack(int atk) {};
    public void die() {
        synchronized (this) {
            mypos.set(null);
            myData.setAlive(false);
            deadImage.setVisible(true);
            imageView.setVisible(false);
            myStatus=STATUS.WAIT;
        }
    }
    public void move() {
        synchronized (this) {
        	Position nextPos = getNextPos();
      		if (nextPos == null||nextPos.get()!=null) {
        			myStatus = STATUS.ATTACK;
        			return;
        		}
        //    System.out.println("WHY NOT MOVE IMAGE");
            moveImage(50*(nextPos.getY()-mypos.getY()),50*(nextPos.getX()-mypos.getX()));
            nextPos.set(this);
            myData.setX(nextPos.getX());
            myData.setY(nextPos.getX());
            myStatus = STATUS.ATTACK;
        }

    }
    public void deductHP(int atk)
    {
      /*  Random r =new Random();
        int t=r.nextInt(100);
        if(t<myData.getDodge())//miss
            return;*/
   // 	synchronized (this) {
    	
		
    	myData.setHp(myData.getHp()-atk-myData.getDef());
        if(!modeReplay&&myData.getHp()<=0)
        {
            myStatus=STATUS.DIE;
            myData.setAlive(false);
        }
    	
    }
    public void run()
    {
    }
}