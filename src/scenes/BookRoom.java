package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class BookRoom {
    SceneChanger sceneChange = new SceneChanger();
    @FXML
    TextField TFBuildingIdAdd;
    @FXML
    TextField TFnewNameAdd;
    @FXML
    TextField TFnewChairAdd;
    @FXML
    TextField TFnewSizeAdd;
    @FXML
    CheckBox CBtv;
    @FXML
    CheckBox CBprojector;
    @FXML
    CheckBox CBwhiteboard;
    @FXML
    CheckBox CBsink;
    @FXML
    CheckBox CBmicrophone;
    @FXML
    CheckBox CBspeakers;
    @FXML
    CheckBox CBoverheadprojector;



    @FXML
    private void Dashboard(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene2Dashboard.fxml");
    }


    @FXML
    private void logout(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene1Login.fxml");
    }
}
