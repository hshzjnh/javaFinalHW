package myinterfaces;

import organismdata.OrganismData;
import organismdata.Record;

public interface OrganismSettings {
	enum STATUS {MOVE,ATTACK,DIE,FIGHTING,WAIT, HULUWAWIN, MONSTERWIN};
	public void setStatus(STATUS status);
	public void setModeReplay(boolean f);
	public void setMonsterGoal(int i) ;
	public void setMonsterKilled(int i) ;
	public void setData(OrganismData data);
	public void setRecord(Record record) ;
}
