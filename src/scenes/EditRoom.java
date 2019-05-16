package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sample.DB_Connector;

import java.io.IOException;

public class EditRoom {

    SceneChanger sceneChange = new SceneChanger();
    private DB_Connector Connector;

    @FXML
    TextField TFNewNameEdit;
    @FXML
    TextField TFBewChairEdit;
    @FXML
    TextField TFBewSizeEdit;
    @FXML
    CheckBox CBtv;
    @FXML
    CheckBox CBProjector;
    @FXML
    CheckBox CBWhiteboard;
    @FXML
    CheckBox CBSink;
    @FXML
    CheckBox CBMicrophone;
    @FXML
    CheckBox CBSpeakers;
    @FXML
    CheckBox CBOverheadProjector;

    @FXML
    private void DashboardRoom(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene3roomdashboard.fxml");
    }

}


