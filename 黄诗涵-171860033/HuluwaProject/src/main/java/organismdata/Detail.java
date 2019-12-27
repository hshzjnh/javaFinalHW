package organismdata;

import java.io.Serializable;

public class Detail implements Serializable {
	 public enum EVENT{MOVE,ATTACK,ATTACKED,DIE,SLEEP,WAIT,RECOVER,RESURRECT,EXIT};
	    private EVENT event;
	    private String name;
	    private OrganismData currentData;
	    private long time;
	    public Detail(EVENT event,String name,OrganismData data,long time) {
	    	this.event=event;
	    	this.name=name;
	    	this.currentData=new OrganismData(data);
	    	this.time=time;
		}
	    private void setTime(long time) {
			this.time=time;
		}
	  /*  public void setId(int id) {
	        this.id = id;
	    }
*/
	    public void setCurrentData(OrganismData currentData) {
	        this.currentData = currentData;
	    }

	    public void setEvent(EVENT event) {
	        this.event = event;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }
	    public EVENT getEvent(){
	    	return event;
	    }
	    public String getName() {
			return name;
		}
	 /*   public int getId()
	    {
	    	return id;
	    }*/
	    public OrganismData getCurrentData() {
			return currentData;
		}
	    public long getTime() {
			return time;
		}
	    
	    
}
