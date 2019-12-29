package myorganism;
import huluwabattle.Controller;
import javafx.scene.layout.AnchorPane;
import com.sun.media.jfxmediaimpl.MetadataParserImpl;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BattleMap{
    ExecutorService exec = Executors.newFixedThreadPool(100);
    private AnchorPane anchorPane;
    private Position[][] position;
    private int size;
    private int mid;
    private int width=16;//y
    private int height=5;//x
    private Vector<Monster> monsters;
    private int monsterNum;
    private Scorpion scorpion;
    private Snack snack;
    private Vector<Huluwa> huluwa;
    private Grandfather grandfather;
    private Controller controller;
    public BattleMap()
    {}
    public BattleMap(InitModule initModule) {
        height=5;width=16;
        position=new Position[height][width];
        for (int i=0;i<height;i++)
            for(int j=0;j<width;j++)
            {
                position[i][j]=new Position(i,j);
            }
        setCharacters(initModule);
    }
    public BattleMap(int height,int width,AnchorPane ancho) {
       try{
           anchorPane=ancho;
           position=new Position[height][width];
           height=5;width=16;
           for (int i=0;i<height;i++)
            for(int j=0;j<width;j++)
            {
                position[i][j]=new Position(i,j);
            }
       }
       catch (IllegalArgumentException e) {
           System.out.println("Size of map too small.(N>=18)"); }
    }
    public void initmap() {
        initHuluwa();
        initMonsters();
    //    shapeMonsters();
    }
    public void initHuluwa()
    {
        for (int i=0;i<huluwa.size();i++)
        {
            Huluwa hulu=huluwa.elementAt(i);
        
            hulu.position=position;
          
            position[hulu.myData.getDefaultX()][hulu.myData.getDefaultY()].set(hulu);
        }
        grandfather.position=position;
        position[grandfather.myData.getDefaultX()][grandfather.myData.getDefaultY()].set(grandfather);
    }
    public void shapeMonsters()
    {
        int i=0;
        for(int j=0;j<monsters.size();j++)
        {
            if(i==5)
                i=0;

            position[i++][13+(j/5)].set(monsters.get(j));
        }
    }
    public void startBattle()
    {
        for (int i = 0; i < huluwa.size(); i++)
            exec.execute(huluwa.elementAt(i));
        for(int i=0;i<monsters.size();i++) {
            exec.execute(monsters.elementAt(i));
        }
        exec.execute(grandfather);
        exec.execute(scorpion);
        exec.execute(snack);
        exec.shutdown();
        try {
            if (!exec.awaitTermination(60, TimeUnit.SECONDS)) {
                exec.shutdownNow();
            }
        } catch (InterruptedException ex) {
            exec.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
  
    public void initMonsters()
    {
        scorpion.position=position;
        snack.position=position;
        position[scorpion.myData.getDefaultX()][scorpion.myData.getDefaultY()].set(scorpion);
        position[snack.myData.getDefaultX()][snack.myData.getDefaultY()].set(snack);
        for(int i=0;i<monsters.size();i++)
        {
           Monster temp=monsters.elementAt(i);
            temp.position=position;
            position[temp.myData.getDefaultX()][temp.myData.getDefaultY()].set(temp);
        }
    }
    public void run(boolean flag)
    {
        if(!flag) {
            initmap();
            startBattle();
        }
        else {
			
		}
    }
    public Vector<Huluwa> getHuluwa()
    {
        return huluwa;
    }
    public void setCharacters(InitModule initModule)
    {
        huluwa=new Vector<Huluwa>();
        huluwa.add(initModule.getRed());
        huluwa.add(initModule.getOrange());
        huluwa.add(initModule.getYellow());
        huluwa.add(initModule.getGreen());
        huluwa.add(initModule.getIndigo());
        huluwa.add(initModule.getBlue());
        huluwa.add(initModule.getPurple());
        this.monsters=initModule.getMonsters();
        this.grandfather=initModule.getGrandfather();
        this.scorpion=initModule.getScorpion();
        this.snack=initModule.getSnack();
    }

}