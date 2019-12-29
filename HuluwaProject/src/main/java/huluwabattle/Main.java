package huluwabattle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javafx.application.Application;

public class Main extends Application{


    private static Controller controller;
    public static Stage stage;
    public static Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("huluwabattle.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (KeyCode.SPACE.equals(ke.getCode())) {
                    controller.startBattle();
                }
            }
        });
        primaryStage.setScene(scene);
        stage=primaryStage;
        primaryStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);

        //battleMap.getRecord();

       // battleMap.printAction();


    }
}
