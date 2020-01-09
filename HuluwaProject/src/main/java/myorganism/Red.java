package myorganism;

import huluwabattle.Controller;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.Vector;

public class Red<T extends Organism> extends Huluwa{
    public ImageView fist=new ImageView(new Image("file:src"+File.separator+"main"+File.separator+"resources"+File.separator+"image"+File.separator+"fist.png"));
    public Red(){
        super();
        num=1;
        imageView=new ImageView(new Image("file:src"+File.separator+"main"+File.separator+"resources"+File.separator+"image"+File.separator+"red.png"));
        getChildren().addAll(imageView);
        getChildren().addAll(fist);
        fist.setVisible(false);
        myData.setAtk(1000);
        myData.setDef(220);
        myData.setHp(300);
        myData.setMaxHp(300);
        myData.setDodge(10);
        myData.setDefaultX(2);
        myData.setDefaultY(4);
        myData.setSkillCount(1);
    }
   
    public void attack()
    {
        synchronized (this) {
            int x = mypos.getX();
            int y = mypos.getY();
            double lx=imageView.getLayoutX();
            double ly=imageView.getLayoutY();
            if(y+1>=myData.getWidth()) {
                myStatus=STATUS.MOVE;
                return;
            }
            fist.setLayoutX(lx+50);
            fist.setLayoutY(ly);
            fist.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(100), fist);
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(3);
            ft.setAutoReverse(true);
            ft.play();
          
            T enemy = (T) position[x][y+1].get();
           	if (enemy != null && (Monster.class.isInstance(enemy) || Snack.class.isInstance(enemy) || Scorpion.class.isInstance(enemy))) {
           		enemy.deductHP(myData.getAtk());
           	}
       }
        myStatus = STATUS.MOVE;
    }
}