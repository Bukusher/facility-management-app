package scenes;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.awt.*;
import java.io.IOException;

public class AddRoom {
    SceneChanger sceneChange = new SceneChanger();

    @FXML
    TextField TFnewroomname;
    @FXML
    TextField TFnewchairamount;
    @FXML
    TextField TFnewroomsize;
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




}
