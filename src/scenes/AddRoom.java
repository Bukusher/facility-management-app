package scenes;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sample.DB_Connector;

import java.io.IOException;

public class AddRoom {
    SceneChanger sceneChange = new SceneChanger();
    private DB_Connector Connector;
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
    private void AddRoom(ActionEvent event) throws IOException,NullPointerException {
        boolean inTv = CBtv.isSelected();
        byte outTv = (byte)(inTv?1:0);
        boolean inprojector = CBprojector.isSelected();
        byte outprojector=(byte)(inprojector?1:0);
        boolean inwhiteboard = CBwhiteboard.isSelected();
        byte outwhiteboard=(byte)(inwhiteboard?1:0);
        boolean insink = CBsink.isSelected();
        byte outsink=(byte)(insink?1:0);
        boolean inmicrophone = CBmicrophone.isSelected();
        byte outmicrophone=(byte)(inmicrophone?1:0);
        boolean instero = CBspeakers.isSelected();
        byte outstero=(byte)(instero?1:0);
        boolean inoverheadprojector = CBoverheadprojector.isSelected();
        byte outoverheadprojector=(byte)(inoverheadprojector?1:0);
        String nameadd = TFnewNameAdd.getText();
        String chairadd = TFnewChairAdd.getText();
        String size = TFnewSizeAdd.getText();
        String buildingid=TFBuildingIdAdd.getText();
        //Putting all vaules in variables
     Connector.insert("room", "room_id,room_availability,chairs,tv,size,prejector,whiteboard,sink,microphones,stereo,overhead_projector,room_building_id ", "'"+nameadd+"',"+"'1','"+chairadd+"','"+outTv+"','"+size+"','"+outprojector+"','"+outwhiteboard+"','"+outsink+"','"+outmicrophone+"','"+outstero+"','"+outoverheadprojector+"','"+buildingid+"'");
    }


}
