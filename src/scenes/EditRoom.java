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
        boolean inTv = CBtv.isSelected();
        byte outTv = (byte) (inTv ? 1 : 0);
        boolean inProjector = CBprojector.isSelected();
        byte outProjector = (byte) (inProjector ? 1 : 0);
        boolean inWhiteboard = CBwhiteboard.isSelected();
        byte outWhiteboard = (byte) (inWhiteboard ? 1 : 0);
        boolean inSink = CBsink.isSelected();
        byte outSink = (byte) (inSink ? 1 : 0);
        boolean inMicrophone = CBmicrophone.isSelected();
        byte outMicrophone = (byte) (inMicrophone ? 1 : 0);
        boolean inStero = CBspeakers.isSelected();
        byte outStero = (byte) (inStero ? 1 : 0);
        boolean inOverheadProjector = CBoverheadProjector.isSelected();
        byte outOverheadProjector = (byte) (inOverheadProjector ? 1 : 0);
        String nameedit = TFnewNameEdit.getText();
        String chairedit = TFnewChairEdit.getText();
        String size = TFnewSizeEdit.getText();
        String buildingID = TFbuildingIdEdit.getText();
        String searchRoom=TFsearchRoomEdit.getText();

               String editRoomQuery = "UPDATE room SET `room_id` = '" + nameedit + "', `chairs` = '" + chairedit + "', `tv` = '" + outTv +
                "', `size` = '" + size + "', `prejector` = '" + outProjector + "', `whiteboard` = '" + outWhiteboard +
                       "', `sink` = '" + outSink + "', `microphones` = '" + outMicrophone +"', `stereo` = '" + outStero +"', `overhead_projector` = '" + outOverheadProjector +
                       "' WHERE `room_id` = '" +searchRoom  + "' AND room_building_id = '"+buildingID +"'" ;

        Connector.executeSQL(editRoomQuery);
        TFnewNameEdit.setText("");
        TFnewSizeEdit.setText("");
        TFnewChairEdit.setText("");
        CBtv.setSelected(false);
        CBoverheadProjector.setSelected(false);
        CBspeakers.setSelected(false);
        CBmicrophone.setSelected(false);
        CBprojector.setSelected(false);
        CBsink.setSelected(false);
        CBwhiteboard.setSelected(false);
    }



}


