package myorganism;

import huluwabattle.Controller;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Vector;

public class Yellow<T extends Organism> extends Huluwa{
   // public ImageView shield=new ImageView(new Image("file:resource\\image\\shield.png"));
    public Yellow(){
        super();
        num=3;
        imageView=new ImageView(new Image("file:src\\main\\resources\\image\\yellow_shield.png"));
        getChildren().addAll(imageView);
        myData.setAtk(300);
        myData.setDef(520);
        myData.setHp(300);
        myData.setMaxHp(300);
        myData.setDodge(10);
        myData.setDefaultX(2);
        myData.setDefaultY(2);
        myData.setSkillCount(1);
    }
   
    public  void attack()
    {
        synchronized (this) {
      
            int x = mypos.getX();
            int y = mypos.getY();
            for (int i = x - 1; i <= x + 1; i++) {
                if (i < 0 || i >= myData.getHeight())
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
}


