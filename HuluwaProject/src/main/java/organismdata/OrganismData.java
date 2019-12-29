package organismdata;

import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import myorganism.Organism;
import myorganism.Position;

import java.io.Serializable;
import java.util.Vector;

public class OrganismData implements Serializable {

    protected long timeInterval = 1000;
    protected static int width=16;
    protected static int height=5;
    protected int defaultX;
    protected int defaultY;
    protected int x;
    protected int y;
    protected int layoutx;
    protected int layouty;
    protected int id;
    protected boolean alive=true;
    protected int atk=70;
    protected int def=10;
    protected int hp=300;
    protected int maxHp=300;
    protected int dodge=30;
    protected int skillCount=0;
    public OrganismData()
    {
    	
    }
    public OrganismData(OrganismData data)
    {
    	this.timeInterval=data.timeInterval;
    	this.width=data.width;
    	this.height=data.height;
    	this.defaultX=data.defaultX;
    	this.defaultY=data.defaultY;
    	this.x=data.x;
    	this.y=data.y;
    	this.layoutx=data.layoutx;
    	this.layouty=data.layouty;
    	this.id=data.id;
    	this.alive=data.alive;
    	this.atk=data.atk;
    	this.def=data.def;
    	this.hp=data.hp;
    	this.maxHp=data.maxHp;
    	this.dodge=data.dodge;
    	this.skillCount=data.skillCount;
    	
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setDefaultX(int defaultX) {
        this.defaultX = defaultX;
    }

    public void setDefaultY(int defaultY) {
        this.defaultY = defaultY;
    }

    public void setDodge(int dodge) {
        this.dodge = dodge;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLayoutx(int layoutx) {
        this.layoutx = layoutx;
    }

    public void setLayouty(int layouty) {
        this.layouty = layouty;
    }

    public void setSkillCount(int skillCount) {
        this.skillCount = skillCount;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setTimeInterval(int timeInterval) {
        this.timeInterval=timeInterval;
    }
    public void setX(int x) {
		this.x=x;
	}
    public void setY(int y) {
		this.y=y;
	}
    public void setMaxHp(int maxHp) {
    	this.maxHp=maxHp;
    }
    public int getX() {
  		return x;
  	}
      public int getY() {
  		return y;
  	}
    public boolean getAlive()
    {
        return alive;
    }
    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getDefaultX() {
        return defaultX;
    }

    public int getDefaultY() {
        return defaultY;
    }

    public int getDodge() {
        return dodge;
    }

    public int getHeight() {
        return height;
    }

    public int getHp() {
        return hp;
    }

    public int getId() {
        return id;
    }

    public int getLayoutx() {
        return layoutx;
    }

    public int getLayouty() {
        return layouty;
    }

    public int getSkillCount() {
        return skillCount;
    }

    public int getWidth() {
        return width;
    }
    public long getTimeInterval() {
        return timeInterval;
    }
    public int getMaxHp() {
    	return maxHp;
    }
}
