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


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Orange<T extends Organism> extends Huluwa{
    public ImageView arrow=new ImageView(new Image("file:src\\main\\resources\\image\\orangeATK.png"));
    public Orange(){
        super();
     
        num=2;
        imageView=new ImageView(new Image("file:src\\main\\resources\\image\\orange.png"));
        getChildren().addAll(imageView);
        myAnchorPane.getChildren().addAll(arrow);
        myData.setAtk(1070);
        myData.setDef(120);
        myData.setHp(12000);
        myData.setMaxHp(12000);
        myData.setDodge(5);
        myData.setDefaultX(1);
        myData.setDefaultY(2);
        myData.setSkillCount(1);
        arrow.setVisible(false);
    }
  
    public void attack()
    {
        synchronized (this) {
            int x = mypos.getX();
            int y = mypos.getY();
            double lx=imageView.getLayoutX();
            double ly=imageView.getLayoutY();
            double lx2=lx;
            double ly2=ly;
            double maxDistance=0;
            T enemy=null;
            int a=0,b=0;
            for (int i = 0; i < myData.getHeight(); i++) {
                for (int j = 0; j < myData.getWidth(); j++) {
                    T temp = (T) position[i][j].get();
                    if(temp==null)
                    	continue;
                    if ((Monster.class.isInstance(temp))&&(temp.getData().getHp()>0)) {
                        double distance = Math.pow(i - mypos.getX(), 2) + Math.pow(j - mypos.getY(), 2);
                        if (distance >= maxDistance) {
                            enemy = temp;
                            maxDistance = distance;
                            a=i;b=j;
                        }
                    }
                }
            }
            lx2=50*b;
            ly2 = 50 * a+135;
            if(enemy!=null)
            {
            	enemy.deductHP(myData.getAtk());
            	arrow.setLayoutX(50*b);
            	arrow.setLayoutY(50*a+100);
            	arrow.setVisible(true);
            	FadeTransition ft = new FadeTransition(Duration.millis(300), arrow);
            	ft.setFromValue(1.0);
            	ft.setToValue(0);
            	ft.setCycleCount(1);
            	ft.setAutoReverse(true);
            	ft.play();
            }
           

            myStatus = STATUS.MOVE;
        }
    }
}