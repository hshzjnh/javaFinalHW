package myorganism;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import organismdata.Detail;
import organismdata.Detail.EVENT;

import java.io.File;
import java.util.Random;
import java.util.Vector;

public class Snack<T extends Organism> extends Organism {
    public Snack()
    {
        super();
        imageView=new ImageView(new Image("file:src"+File.separator+"main"+File.separator+"resources"+File.separator+"image"+File.separator+"snack.png"));
        getChildren().addAll(imageView);
        myData.setAtk(2000);
        myData.setDef(120);
        myData.setHp(4100);
        myData.setMaxHp(4100);
        myData.setDodge(60);
        myData.setDefaultX(1);
        myData.setDefaultY(15);
        myData.setSkillCount(1);
        myData.setTimeInterval(2000);
    }
   
    public void resurrect() {
        synchronized (this) {
            int num = deadMonsterList.size();
            if (num > 0) {

             //   Random r = new Random();
               // int t = r.nextInt(num);
                ((Monster) deadMonsterList.get(0)).resurrect(myData.getAtk());
                deadMonsterList.remove(0);
            }
        }
    }
    public void run()
    {

        while (!exit) {
        	try {
                Thread.sleep(myData.getTimeInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                if (status != STATUS.FIGHTING ) {
                    exitThread();break;
                }
            }
            if(myData.getHp()>0)
            	resurrect();
            
            
        }
        record.setDetail(new Detail(EVENT.EXIT, this.getClass().getName(), myData, System.currentTimeMillis()-timer));
    }
    public void run_replay() {
    	long startTime=System.currentTimeMillis();
    	Detail detail=record2.getDetails().get(0);
    	long time=detail.getTime();
		while(true) {
			if(System.currentTimeMillis()-startTime>=time)
				break;
			else {
				Thread.yield();
			}
		}
		
	}
}