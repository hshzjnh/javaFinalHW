package huluwabattle;

import myorganism.InitModule;
import myorganism.Monster;
import organismdata.OrganismData;
import organismdata.Record;

import java.io.Serializable;
import java.util.Vector;

public class OrganismSerial implements Serializable {
    private OrganismData redData;
    private Record redRecord;
    private OrganismData orangeData;
    private Record orangeRecord;
    private OrganismData yellowData;
    private Record yellowRecord;
    private OrganismData greenData;
    private Record greenRecord;
    private OrganismData indigoData;
    private Record indigoRecord;
    private OrganismData blueData;
    private Record blueRecord;
    private OrganismData purpleData;
    private Record purpleRecord;
    private OrganismData grandfatherData;
    private Record grandfatherRecord;
    private Vector<OrganismData> monsterData=new Vector<>();
    private Vector<Record> monsterRecords=new Vector<Record>();
    private OrganismData scorpionData;
    private Record scorpionRecord;
    private OrganismData snackData;
    private Record snackRecord;
    public OrganismSerial() {

    }
    public OrganismSerial(InitModule initModule)
    {
        redData=new OrganismData(initModule.getRed().getData());
        orangeData=new OrganismData(initModule.getOrange().getData());
        yellowData=new OrganismData(initModule.getYellow().getData());
        greenData=new OrganismData(initModule.getGreen().getData());
        indigoData=new OrganismData(initModule.getIndigo().getData());
        blueData=new OrganismData(initModule.getBlue().getData());
        purpleData=new OrganismData(initModule.getPurple().getData());
        grandfatherData=new OrganismData(initModule.getGrandfather().getData());
        Vector<Monster> monsters=initModule.getMonsters();
        for(int i=0;i<monsters.size();i++)
            monsterData.add(new OrganismData(monsters.get(i).getData()));
        scorpionData=new OrganismData(initModule.getScorpion().getData());
        snackData=new OrganismData(initModule.getSnack().getData());
    }
    public void setOrganismSerial(InitModule initModule,int flag)
    {
    	if(flag==0) {
    		redData=initModule.getRed().getData();
    	    orangeData=initModule.getOrange().getData();
    	    yellowData=initModule.getYellow().getData();
    	    greenData=initModule.getGreen().getData();
    	    indigoData=initModule.getIndigo().getData();
    	    blueData=initModule.getIndigo().getData();
            purpleData=initModule.getPurple().getData();
            grandfatherData=initModule.getGrandfather().getData();
    	    Vector<Monster> monsters=initModule.getMonsters();
    	    for(int i=0;i<monsters.size();i++)
    	    	monsterData.add(monsters.get(i).getData());
    	    scorpionData=initModule.getScorpion().getData();
    	    snackData=initModule.getSnack().getData();
    	}
    	else {
    		redRecord=initModule.getRed().getRecord();
    	    orangeRecord=initModule.getOrange().getRecord();
    	    yellowRecord=initModule.getYellow().getRecord();
    	    greenRecord=initModule.getGreen().getRecord();
    	    indigoRecord=initModule.getIndigo().getRecord();
    	    blueRecord=initModule.getIndigo().getRecord();
            purpleRecord=initModule.getPurple().getRecord();
            grandfatherRecord=initModule.getPurple().getRecord();
    	    Vector<Monster> monsters=initModule.getMonsters();
    	    for(int i=0;i<monsters.size();i++)
    	    	monsterRecords.add(monsters.get(i).getRecord());
    	    scorpionRecord=initModule.getScorpion().getRecord();
    	    snackRecord=initModule.getSnack().getRecord();
		}
    	
    }
    public void setRedData(OrganismData data) {
        redData=data;
    }
    public OrganismData getRedData() {
        return redData;
    }
    public OrganismData getOrangeData() {
        return orangeData;
    }
    public OrganismData getYellowData() {
        return yellowData;
    }
    public OrganismData getGreenData() {
        return greenData;
    }
    public OrganismData getIndigoData() {
        return indigoData;
    }
    public OrganismData getBlueData() {
        return blueData;
    }
    public OrganismData getPurpleData() {
        return purpleData;
    }
    public OrganismData getGrandFatherData() {
        return grandfatherData;
    }
    public OrganismData getMonsterData(int i) {
        if(i<monsterData.size()) {
            return monsterData.get(i);
        }
        return null;
    }
    public OrganismData getScorpionData() {
        return scorpionData;
    }
    public OrganismData getSnackData() {
        return snackData;
    }
    public Record getRedRecord() {
        return redRecord;
    }
    public Record getOrangeRecord() {
        return orangeRecord;
    }
    public Record getYellowRecord() {
        return yellowRecord;
    }
    public Record getGreenRecord() {
        return greenRecord;
    }
    public  Record getIndigoRecord() {
        return indigoRecord;
    }
    public  Record getBlueRecord() {
        return blueRecord;
    }
    public  Record getPurpleRecord() {
        return purpleRecord;
    }
    public  Record getGrandFatherRecord() {
        return grandfatherRecord;
    }
    public  Record getMonsterRecord(int i) {
        if(i<monsterRecords.size()) {
            return monsterRecords.get(i);
        }
        return null;
    }
    public  Record getScorpionRecord() {
        return scorpionRecord;
    }
    public  Record getSnackRecord() {
        return snackRecord;
    }


}
