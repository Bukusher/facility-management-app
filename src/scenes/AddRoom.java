package scenes;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddRoom {
    SceneChanger sceneChange = new SceneChanger();

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
    private void AddRoom(ActionEvent event) throws IOException{
      byte tv;
      byte projector;
      byte whiteboard;
      byte sink;
      byte microphone;
      byte speakers;
      byte overheadprojector;
     String nameadd = TFnewNameAdd.getText();
     String chairadd=TFnewChairAdd.getText();
     String size=TFnewSizeAdd.getText();
        if (CBtv.isSelected()){
         tv=1;
      }else tv=0;
    if (CBprojector.isSelected()){
        projector=1;
    }else projector=0;
    if (CBwhiteboard.isSelected()){
        whiteboard=1;
    }else whiteboard=0;
    if (CBsink.isSelected()){
        sink=1;
    }else sink=0;
    if (CBmicrophone.isSelected()){
        microphone=1;
    }else microphone=0;
    if (CBspeakers.isSelected()){
        speakers=1;
    }else speakers=0;
    if (CBoverheadprojector.isSelected()){
        overheadprojector=1;
    }else overheadprojector=0;
    //Putting all vaules in variables
    }



}
