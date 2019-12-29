package myorganism;
import huluwabattle.OrganismSerial;
import myinterfaces.OrganismSettings.STATUS;
import organismdata.OrganismData;

import java.util.Vector;
import java.io.Serializable;
public class InitModule<T extends Organism> implements Serializable {
    private BattleMap battleMap;
    private Red red;
    private Orange orange;
    private Yellow yellow;
    private Green green;
    private Indigo indigo;
    private Blue blue;
    private Purple purple;
    private Vector<Monster> monsters=new Vector<>();
    private Scorpion scorpion;
    private Grandfather grandfather;
    private Snack snack;


    public InitModule()
    {
    	
    }
    public void setData(OrganismSerial serial) {
        red.setData(serial.getRedData());
        orange.setData(serial.getOrangeData());
        yellow.setData(serial.getYellowData());
        green.setData(serial.getGreenData());
        indigo.setData(serial.getIndigoData());
        blue.setData(serial.getBlueData());
        System.out.println("BLUE DEFAULTX");
        System.out.println(blue.getData().getDefaultX());
        purple.setData(serial.getPurpleData());
        grandfather.setData(serial.getGrandFatherData());
        for(int i=0;i<monsters.size();i++) {
            monsters.get(i).setData(serial.getMonsterData(i));
        }
        scorpion.setData(serial.getScorpionData());
        snack.setData(serial.getSnackData());
    }
    public void setRecord(OrganismSerial serial) {
    	red.setRecord(serial.getRedRecord());
        orange.setRecord(serial.getOrangeRecord());
        yellow.setRecord(serial.getYellowRecord());
        green.setRecord(serial.getGreenRecord());
        indigo.setRecord(serial.getIndigoRecord());
        blue.setRecord(serial.getBlueRecord());
        purple.setRecord(serial.getPurpleRecord());
        grandfather.setRecord(serial.getGrandFatherRecord());
        for(int i=0;i<monsters.size();i++) {
            monsters.get(i).setRecord(serial.getMonsterRecord(i));
        }
        scorpion.setRecord(serial.getScorpionRecord());
        snack.setRecord(serial.getSnackRecord());
		
	}
    public void init(boolean replay) {
		if(replay==true){
			initCharacters();
			red.setStatus(STATUS.FIGHTING);
			red.setModeReplay(true);
			red.setMonsterGoal(0);
			red.setMonsterKilled(0);
			red.initMonsterList();
		}
		else {
			initCharacters();
			red.setStatus(STATUS.FIGHTING);
			red.setModeReplay(false);
			red.setMonsterGoal(0);
			red.setMonsterKilled(0);
			red.initMonsterList();
		}
	}
    public void initCharacters()
    {
        red=new Red();
        orange=new Orange();
        yellow=new Yellow();
        green=new Green();
        indigo=new Indigo();
        blue=new Blue();
        purple=new Purple();
        grandfather=new Grandfather();
        scorpion=new Scorpion();
        int j=0;
        for(int i=0;i<10;i++)
        {
            if(j==5)
                j=0;
            Monster temp=new Monster();
            temp.setId(i);
            temp.myData.setDefaultX(j++);
            temp.myData.setDefaultY(13+(i/5));
            monsters.add(temp);
        }
        scorpion=new Scorpion();
        snack=new Snack();
    }
    public Red getRed() {
        return red;
    }
    public Orange getOrange(){
        return orange;
    }
    public Yellow getYellow() {
        return yellow;
    }
    public Green getGreen() {
        return green;
    }
    public Indigo getIndigo() {
        return indigo;
    }
    public Blue getBlue() {
        return blue;
    }
    public Purple getPurple() {
        return purple;
    }
    public Grandfather getGrandfather() {
        return grandfather;
    }
    public Vector<Monster> getMonsters() {
        return monsters;
    }
    public Scorpion getScorpion() {
        return scorpion;
    }
    public Snack getSnack() {
        return snack;
    }

    public void setRedData(OrganismData data) {
        this.red. setData(data);
    }
    public OrganismData getRedData() {
        return red.getData();
    }
    public void setOrangeData(OrganismData data) {
        this.orange.setData(data);
    }
    public OrganismData getOrangeData() {
        return orange.getData();
    }
}
