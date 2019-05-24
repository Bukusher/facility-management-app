package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.SceneChanger;

public class Main extends Application {

    private SceneChanger sceneChange = new SceneChanger();
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../scenes/Scene1Login.fxml"));
        Scene scene =new Scene(root, 600, 400);
        primaryStage.setTitle("Facility Management Application");
        primaryStage.setScene(scene);
        //Darkmode
        if (sceneChange.isDarkModeOnFileRead())
            scene.getStylesheets().add("/scenes/DarkTheme.css");
        //Darkmode over
        primaryStage.show();

    }

    public static void main(String[] args) {
        DB_Connector db_connector = new DB_Connector();
        db_connector.connect();

        launch(args);


    }
}
