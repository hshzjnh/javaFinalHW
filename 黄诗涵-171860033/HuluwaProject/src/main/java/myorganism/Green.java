package myorganism;

import huluwabattle.Controller;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Green<T extends Organism> extends Huluwa{
    public ImageView[] fireBalls=new ImageView[5];
    public Green(){
        super();
        num=4;
        imageView=new ImageView(new Image("file:src\\main\\resources\\image\\green.png"));
        getChildren().addAll(imageView);
        for(int i=0;i<5;i++)
        {
            fireBalls[i]=new ImageView(new Image("file:src\\main\\resources\\image\\fireball.gif"));
            fireBalls[i].setVisible(false);
            getChildren().addAll(fireBalls[i]);
        }
        myData.setAtk(900);
        myData.setDef(220);
        myData.setHp(1000);
        myData.setMaxHp(1000);
        myData.setDodge(20);
        myData.setDefaultX(3);
        myData.setDefaultY(3);
        myData.setSkillCount(2);
    }
  
    public void attack()
    {
        synchronized (this) {
            int x = mypos.getX();
            int y = mypos.getY();
            double lx=imageView.getLayoutX();
            double ly=imageView.getLayoutY();
            boolean flag=false;
            for(int i=y+1;i<y+5;i++) {
            	 if(i>=myData.getWidth())
                     break;
            	 if(!modeReplay) {
                 	T enemy = (T) position[x][i].get();
                 	if (enemy != null && (Monster.class.isInstance(enemy) || Snack.class.isInstance(enemy) || Scorpion.class.isInstance(enemy))) {
                 		enemy.deductHP(myData.getAtk());
                 		flag=true;
                 	}
                 }
            }
          /*  if(flag==false) {
            	myStatus=STATUS.MOVE;
            	return;
            }*/
            new Thread(() -> {
                Semaphore semaphore = new Semaphore(1);
                try {
                    semaphore.acquire();
                    Platform.runLater(() -> {
                    	for(int i=y+1;i<=y+5;i++)
                    	{
                    		if(i>=myData.getWidth())
                    			break;
                    		fireBalls[i-y-1].setLayoutX(lx+50*(i-y));
                    		fireBalls[i-y-1].setLayoutY(ly);
                    		fireBalls[i-y-1].setVisible(true);
                    		FadeTransition ft = new FadeTransition(Duration.millis(1000), fireBalls[i-y-1]);
                    		ft.setFromValue(1.0);
                    		ft.setToValue(0);
                    		ft.setCycleCount(1);
                    		ft.setAutoReverse(true);
                    		ft.play();
                    	}
                    	semaphore.release();});   
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }).start();
                
            
            myStatus = STATUS.MOVE;
        }
    }
}
