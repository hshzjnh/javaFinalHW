package myorganism;


import huluwabattle.Controller;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Indigo<T extends Organism> extends Huluwa{
    public ImageView[] waterColumns=new ImageView[5];
    public Indigo(){
        num=5;
        imageView=new ImageView(new Image("file:src\\main\\resources\\image\\indigo.png"));
        getChildren().addAll(imageView);
        for(int i=0;i<5;i++)
        {
            waterColumns[i]=new ImageView(new Image("file:src\\main\\resources\\image\\watercolumn.gif"));
            waterColumns[i].setVisible(false);
            getChildren().addAll(waterColumns[i]);
        }
        myData.setAtk(300);
        myData.setDef(520);
        myData.setHp(13300);
        myData.setMaxHp(13300);
        myData.setDodge(15);
        myData.setDefaultX(1);
        myData.setDefaultY(3);
        myData.setSkillCount(1);
    }
   
    public void attack()
    {
        synchronized (this) {
            int x = mypos.getX();
            int y = mypos.getY();
            double lx=imageView.getLayoutX();
            double ly=imageView.getLayoutY();
            boolean flag=false;
            for(int i=0;i<5;i++)
            {
                if(y<myData.getWidth()-1)
                {
                    T enemy = (T) position[i][y+1].get();
                    if (enemy != null && (Monster.class.isInstance(enemy) || Snack.class.isInstance(enemy) || Scorpion.class.isInstance(enemy))) {
                    	
                    		enemy.deductHP(myData.getAtk());
                    	
                        flag=true;
                    }
                }
            }
            System.out.println("Indigo Attack");
            myStatus = STATUS.MOVE;
            if(flag==false)
                return;
            System.out.println("Indigo Attack2");
                new Thread(() -> {
                    Semaphore semaphore = new Semaphore(1);
                    try {
                        semaphore.acquire();
                        Platform.runLater(() -> {
                        	 for(int i=0;i<5;i++)
                             {
                                 if(x>=myData.getWidth()-1)
                                 break;
                                 waterColumns[i].setLayoutX(lx+50);
                                 waterColumns[i].setLayoutY(ly-50*mypos.getX()+50*i-100);
                                 waterColumns[i].setVisible(true);
                                 FadeTransition ft = new FadeTransition(Duration.millis(400), waterColumns[i]);
                                 ft.setFromValue(1.0);
                                 ft.setToValue(0);
                                 ft.setCycleCount(1);
                                 ft.setAutoReverse(true);
                                 ft.play();
                             }
                        	 semaphore.release();
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
}