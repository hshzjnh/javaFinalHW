package huluwabattle;
import myorganism.Organism;
//import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import myinterfaces.OrganismSettings.STATUS;
import myorganism.*;
import javafx.event.*;
import javafx.concurrent.*;
import javafx.application.*;


import javafx.event.ActionEvent;

import java.io.*;
import java.util.Vector;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Controller <T extends Organism> {
    @FXML private TableColumn tableColumn;
    @FXML private TableColumn tableColumn2;
    @FXML private TableColumn tableColumn3;
    @FXML private AnchorPane anchorPane;
    @FXML private AnchorPane anchorPane2;
    @FXML private AnchorPane anchorPane3;
    @FXML private MenuBar menuBar;
    @FXML private  Button button;
    @FXML private Text text;
    private boolean able=false;
    @FXML private  Canvas canvas;

    private Main mainApp;
    /*private static Vector<Action> record=new Vector<Action>();
    private  ObservableList<Action> data= FXCollections.observableArrayList();
    private Action current;*/
    ExecutorService exec = Executors.newFixedThreadPool(50);
    private InitModule initModule=new InitModule();
    private OrganismSerial organismSerial;
    private BattleMap battleMap;
    private Vector<Huluwa> huluwa=new Vector<Huluwa>();
    private Vector<Monster> monsters=new Vector<>();
    private Scorpion scorpion;
    private Grandfather grandfather;
    private Snack snack;

    public void setTableColumn()
    {
     /*   tableView.setItems(data);
        System.out.println("set ITEM");
        tableView.refresh();*/
    }
    public void setMenuBar()
    {
        Menu menuFile = menuBar.getMenus().get(0);
        Menu menuEdit = menuBar.getMenus().get(1);
        Menu menuRun = new Menu("Run");
        menuFile.getItems().remove(0);
        MenuItem menuItem1 = new MenuItem("Open File");
        menuItem1.setAccelerator(KeyCombination.valueOf("L"));
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openFile();
            }
        });

        MenuItem menuItemSave=  new MenuItem("Save Record");
        menuItemSave.setAccelerator(KeyCombination.valueOf("S"));
        menuItemSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveFile();
            }
        });
        MenuItem menuItem2=new MenuItem("Start New Battle");
        menuItem2.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                    	initCharacters(false);
                        startBattle();
                    }
                }
        );
        menuFile.getItems().addAll(menuItemSave);
        menuFile.getItems().addAll(menuItem1);
        menuRun.getItems().addAll(menuItem2);
        menuBar.getMenus().addAll(menuRun);
    }
    public void setBackground()
    {
        BackgroundImage myBI= new BackgroundImage(new Image("file:resource"+File.separator+"image"+File.separator+"background2.png"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        anchorPane2.setBackground(new Background(myBI));
    }

    public void initialize() {
        setMenuBar();
      /*  tableColumn.setCellValueFactory(new PropertyValueFactory<>("creatureStr"));
        tableColumn2.setCellValueFactory(new PropertyValueFactory<>("actionStr"));
        tableColumn3.setCellValueFactory(new PropertyValueFactory<>("positionStr"));
        //tableView.setItems(data);*/
        text.setLayoutX(400);
        text.setLayoutY(40);
        text.setFont(new Font("LiSu", 39));
        text.setText("");
        initModule.initCharacters();
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
      //  setTableColumn();
    }
    public void startBattle()
    {
        anchorPane3.getChildren().addAll(initModule.getRed().getMyAnchorPane());
        text.setText("");
        Service<String> service=new Service<String>() {
            @Override
            protected Task<String> createTask() {
                return new Task<String>() {
                    @Override
                    protected String call() throws Exception {
                    	
                        battleMap = new BattleMap(initModule);
                        System.out.println("new map");
                        //battleMap.setCharacters(initModule);
                        battleMap.run(false);
                        return "success";
                    }
                };
            }
        };
        service.setOnSucceeded((WorkerStateEvent event) -> {
            System.out.println("任务完成");
            if(initModule.getGrandfather().getStatus()==STATUS.HULUWAWIN)
            	text.setText("葫芦娃胜利！");
            else {
				text.setText("蛇精胜利！");
			}
            organismSerial.setOrganismSerial(initModule, 1);
            initModule.getOrange().initMyAnchorPane();
        });
        service.start();
    }
    public  void setNewCharacter(ActionEvent event)
    {
  /*      double x=anchorPane2.getLayoutX()+40*current.getPosY()+40;
        double y=anchorPane2.getLayoutY()+40*current.getPosX()-20;//+100;
        T creature=(T)current.getCreature();
        creature.setLayoutY(y);creature.setLayoutX(x);
        System.out.println("set ");
        anchorPane2.getChildren().addAll(creature);*/
    }
   /* public void addbattleRecord(Action ac) {
        data.add(ac);
        setTableColumn();
    }*/
   
   

    public void initCharacters(boolean flag)
    {
    	anchorPane3.getChildren().clear();
    	if(flag==true) {
    		  initModule=new InitModule<Organism>();
              initModule.init(true);
              initModule.setData(organismSerial);
              initModule.setRecord(organismSerial);
    	}
    	else
    	{
    		initModule=new InitModule<Organism>();
    		initModule.init(false);
    		organismSerial=new OrganismSerial(initModule);
    	}
    	
    	huluwa=new Vector<Huluwa>();
    	huluwa.add(initModule.getRed());
        huluwa.add(initModule.getOrange());
        huluwa.add(initModule.getYellow());
        huluwa.add(initModule.getGreen());
        huluwa.add(initModule.getIndigo());
        huluwa.add(initModule.getBlue());
        huluwa.add(initModule.getPurple());
        anchorPane3.getChildren().addAll(initModule.getRed(),initModule.getOrange(),initModule.getYellow(),initModule.getGreen(),
                initModule.getIndigo(),initModule.getBlue(),initModule.getPurple());
        monsters=initModule.getMonsters();
        for(int i=0;i<10;i++)
            anchorPane3.getChildren().addAll(monsters.get(i));
        grandfather=initModule.getGrandfather();
        snack=initModule.getSnack();
        scorpion=initModule.getScorpion();
        anchorPane3.getChildren().addAll(initModule.getScorpion(),initModule.getSnack(),initModule.getGrandfather());
        System.out.println("init over");
    }
    public void saveFile() {
        try {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("src"+File.separator+"main"+File.separator+"outputfiles"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("SER", "*.ser")
            );
          
            fileChooser.setTitle("Save File");
            File selectedFile=fileChooser.showSaveDialog(mainApp.stage);
            if (fileChooser != null) {
                FileOutputStream fs = new FileOutputStream(selectedFile.getPath());//new FileOutputStream("file\\recordFile.ser");
                ObjectOutputStream os =  new ObjectOutputStream(fs);
         
                organismSerial.setOrganismSerial(initModule, 1);
                os.writeObject(organismSerial);
                os.close();
            }
        }
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }
    public void openFile() {
        try {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("src"+File.separator+"main"+File.separator+"outputfiles"));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SER", "*.ser"));
            fileChooser.setTitle("Open File");
            File selectedFile = fileChooser.showOpenDialog(mainApp.stage);
            if (fileChooser != null) {
                FileInputStream fs = new FileInputStream(selectedFile.getPath());//new FileOutputStream("file\\recordFile.ser");
                ObjectInputStream os = new ObjectInputStream(fs);
                organismSerial = new OrganismSerial();
                organismSerial=(OrganismSerial) os.readObject();
                os.close();
                initCharacters(true);
            }

        } catch (FileNotFoundException e) { }
        catch (IOException e) { }
        catch (ClassNotFoundException e){}
    }
}
