package myorganism;

import huluwabattle.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Vector;

public class Purple extends Huluwa{
    public Purple(){
        super();
      
        num=7;
        imageView=new ImageView(new Image("file:src\\main\\resources\\image\\purple.png"));
        getChildren().addAll(imageView);
        myData.setAtk(650);
        myData.setDef(520);
        myData.setHp(1800);
        myData.setMaxHp(1800);
        myData.setDodge(40);
        myData.setDefaultX(2);
        myData.setDefaultY(1);
        myData.setSkillCount(1);
        myData.setTimeInterval(1500);
    }
   
    public void attack()
    {
    	int size=deadMonsterList.size();
    	synchronized(deadHuluwaList) {
    		if(size>0){
    			((Monster) deadMonsterList.get(0)).setImageVisible(false,false);
    			((Monster) deadMonsterList.get(0)).exitThread();
    			deadMonsterList.remove(0);
    		}
    	}
    }
}
