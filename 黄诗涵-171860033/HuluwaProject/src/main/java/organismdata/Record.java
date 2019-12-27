package organismdata;

import java.io.Serializable;
import java.util.Vector;

public class Record implements Serializable{
   Vector<Detail> details=new Vector<Detail>();
   public Record(){   
   }
   public Vector<Detail> getDetails() {
	   return details;
   }
   public void setDetail(Detail detail) {
	   details.add(detail);
   }
}


















