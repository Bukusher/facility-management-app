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
    private void DashboardRoom(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene3roomdashboard.fxml");
    }

    @FXML
    private void AddRoom(ActionEvent event) throws IOException,NullPointerException {
        boolean inTv = CBtv.isSelected();
        byte outTv = (byte)(inTv?1:0);
        boolean inProjector = CBprojector.isSelected();
        byte outProjector=(byte)(inProjector?1:0);
        boolean inWhiteboard = CBwhiteboard.isSelected();
        byte outWhiteboard=(byte)(inWhiteboard?1:0);
        boolean inSink = CBsink.isSelected();
        byte outSink=(byte)(inSink?1:0);
        boolean inMicrophone = CBmicrophone.isSelected();
        byte outMicrophone=(byte)(inMicrophone?1:0);
        boolean inStero = CBspeakers.isSelected();
        byte outStero=(byte)(inStero?1:0);
        boolean inOverheadProjector = CBoverheadprojector.isSelected();
        byte outOverheadProjector=(byte)(inOverheadProjector?1:0);
        String nameAdd = TFnewNameAdd.getText();
        String chairAdd = TFnewChairAdd.getText();
        String size = TFnewSizeAdd.getText();
        String buildingID=TFBuildingIdAdd.getText();
        //Putting all vaules in variables
     Connector.insert("room", "room_id,room_availability,chairs,tv,size,prejector,whiteboard,sink,microphones,stereo,overhead_projector,room_building_id ", "'"+nameAdd+"',"+"'1','"+chairAdd+"','"+outTv+"','"+size+"','"+outProjector+"','"+outWhiteboard+"','"+outSink+"','"+outMicrophone+"','"+outStero+"','"+outOverheadProjector+"','"+buildingID+"'");
    }


}
