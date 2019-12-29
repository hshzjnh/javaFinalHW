package myorganism;
import java.lang.*;
import java.lang.reflect.*;

public class Position<T extends Organism> {
    private int x;
    private int y;
    private T creature;
    public Position(Position p)
    {
        this.x=p.x;
        this.y=p.y;
    }
    public Position(int x,int y)
    {
        this.x=x;
        this.y=y;
        creature=null;
    }
    public void set(T t)
    {
        creature=t;
        if(t==null) {
            return;
        }
    try {
        Class c=creature.getClass();
        Method m=c.getMethod("setpos", Position.class);
        m.invoke(creature,this);
    }
    catch (NoSuchMethodException e) {}
    catch (IllegalAccessException e){}
    catch (InvocationTargetException e){}
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public T get()
    {
    	synchronized(this) {
        if(creature==null)
            return null;
        return creature;
    	}
    }
    public void die() {
        creature=null;
    }
    public String getName()
    {
        return this.creature.getClass().getName();
    }
    public String coordinateStr()
    {
        return '('+Integer.toString(x)+','+Integer.toString(y)+')';
    }

}
