package myorganism;

import huluwabattle.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.Vector;

public class Blue extends Huluwa{
    public Blue(){
        super();
        num=6;
        imageView=new ImageView(new Image("file:src"+File.separator+"main"+File.separator+"resources"+File.separator+"image"+File.separator+"blue.png"));//src\\main\\resources\\image\\blue.png"));
        getChildren().addAll(imageView);
        myData.setAtk(700);
        myData.setDef(120);
        myData.setHp(200);
        myData.setMaxHp(200);
        myData.setDodge(60);
        myData.setDefaultX(3);
        myData.setDefaultY(2);
        myData.setSkillCount(1);
    }
 
}