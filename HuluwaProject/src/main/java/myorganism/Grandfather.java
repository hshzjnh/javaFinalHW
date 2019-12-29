package myorganism;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import myinterfaces.*;
import organismdata.Detail;
import organismdata.Detail.EVENT;

import javax.lang.model.type.NullType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

public class Grandfather<T extends Organism> extends Organism{
    public Grandfather(){
        super();
        imageView=new ImageView(new Image("file:src\\main\\resources\\image\\grandfather.png"));
        getChildren().addAll(imageView);
        myData.setAtk(200);
        myData.setDef(1000);
        myData.setHp(100000);
        myData.setMaxHp(100000);
        myData.setDodge(100);
        myData.setDefaultX(1);
        myData.setDefaultY(0);
        myData.setSkillCount(1);
        myData.setTimeInterval(500);
    }
   

    public void run()
    {
    /*	if(modeReplay)
    	{
    		run_replay();
    		return;
    	}*/
        try {
            Thread.sleep(myData.getTimeInterval());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!exit) {

            synchronized (this) {
            	if(monsterGoal>=3)
            	{
            		status=STATUS.MONSTERWIN;
            		System.out.println("MON WIN");
            	}
            	else if(monsterKilled>=20) {
            		status=STATUS.HULUWAWIN;
            		System.out.println("HULU WIN");
            	}
            	
                if (status != STATUS.FIGHTING ) {
                    exitThread();break;
                }
                
            }
            int count=0;
            int countH=0;
            synchronized (this) {
                for (int i = 0; i < myData.getHeight(); i++) {
                    for (int j = 0; j <myData.getWidth(); j++) {
                        T enemy = (T) position[i][j].get();
                        if (enemy != null && (Monster.class.isInstance(enemy) || Scorpion.class.isInstance(enemy))) {
                            count++;
                        }
                        if (enemy != null && (Huluwa.class.isInstance(enemy))&&enemy.isAlive()==true) {
                        	//((Huluwa)enemy).recover(myData.getAtk());
                            try {
                                Class c=Class.forName("myorganism.Huluwa");
                                Method m=c.getMethod("recover", int.class);
                                m.invoke(enemy,myData.getAtk());
                            }
                            catch (NoSuchMethodException e) {}
                            catch (IllegalAccessException e){}
                            catch (InvocationTargetException e){}
                            catch (ClassNotFoundException e) {}
                            countH++;
                        }

                    }
                    
                }
            }
            if(count==0)
                status=STATUS.HULUWAWIN;
            if(countH==0)
                status=STATUS.MONSTERWIN;
            try {
                Thread.sleep(myData.getTimeInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        record.setDetail(new Detail(EVENT.EXIT, this.getClass().getName(), myData, System.currentTimeMillis()-timer));
        System.out.println(this.getClass().getName()+" exit!");
    }
    public void run_replay() {
    	long startTime=System.currentTimeMillis();
    	Detail detail=record2.getDetails().get(0);
		while(true) {
			if(System.currentTimeMillis()-startTime>=detail.getTime())
				break;
			else {
				Thread.yield();
			}
		}
		
	}
}