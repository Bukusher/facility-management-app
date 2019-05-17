package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sample.DB_Connector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditRoom {

    SceneChanger sceneChange = new SceneChanger();
    private DB_Connector Connector = new DB_Connector();
    @FXML
    TextField TFsearchRoomEdit;
    @FXML
    TextField TFnewNameEdit;
    @FXML
    TextField TFnewChairEdit;
    @FXML
    TextField TFnewSizeEdit;
    @FXML
    TextField TFbuildingIdEdit;
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
    CheckBox CBoverheadProjector;

    @FXML
    private void DashboardRoom(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene3roomdashboard.fxml");
    }

    @FXML
    private void Search(ActionEvent event) throws IOException {
        String searchRoom = "SELECT * FROM `pc2fma2`.`room` where room_id  = '" + TFsearchRoomEdit.getText() + "' AND " + "room_building_id = '" + TFbuildingIdEdit.getText() + "'";
        String rsRoomName = "";
        String rsChairsAmount = "";
        String rsSize = "";
        byte rsTv = 0;
        byte rsPrejector =0;
        byte rsWhiteboard=0;
        byte rsSink = 0;
        byte rsMicrophone=0;
        byte rsStereo=0;
        byte rsOverheadProjector=0;

        try {
            ResultSet resultSet = Connector.select(searchRoom);
            if (resultSet.next()) {
                rsRoomName = resultSet.getString(1);
                rsChairsAmount = resultSet.getString(4);
                rsSize = resultSet.getString(5);
                rsTv = resultSet.getByte(6);
                rsPrejector = resultSet.getByte(7);
                rsWhiteboard= resultSet.getByte(8);
                rsSink=resultSet.getByte(9);
                rsMicrophone=resultSet.getByte(10);
                rsStereo=resultSet.getByte(11);
                rsOverheadProjector=resultSet.getByte(12);


            }
            TFnewNameEdit.setText(rsRoomName);
            TFnewChairEdit.setText(rsChairsAmount);
            TFnewSizeEdit.setText(rsSize);
            boolean tvOut = rsTv!=0;
            CBtv.setSelected(tvOut);
            boolean prejectorOut = rsPrejector!=0;
            CBprojector.setSelected(prejectorOut);
            boolean whiteboardOut = rsWhiteboard!=0;
            CBwhiteboard.setSelected(whiteboardOut);
            boolean sinkOut = rsSink!=0;
            CBsink.setSelected(sinkOut);
            boolean microphoneOut = rsMicrophone!=0;
            CBmicrophone.setSelected(microphoneOut);
            boolean stereoOut = rsStereo!=0;
            CBspeakers.setSelected(stereoOut);
            boolean overheadProjectorOut = rsOverheadProjector!=0;
            CBoverheadProjector.setSelected(overheadProjectorOut);

        } catch (SQLException ex) {
            System.out.println("bruh");

        }
    }


    @FXML
    private void applyChange(ActionEvent event) throws IOException {

    }



}


