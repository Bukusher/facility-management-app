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
        String rsPrejector = "";
        boolean Checkvalue;
        try {
            ResultSet resultSet = Connector.select(searchRoom);
            if (resultSet.next()) {
                rsRoomName = resultSet.getString(1);
                rsChairsAmount = resultSet.getString(4);
                rsSize = resultSet.getString(5);
                rsTv = resultSet.getByte(6);
                rsPrejector = resultSet.getString(7);
            }
            System.out.println(rsRoomName + " " + rsChairsAmount + " " + rsPrejector + " " + rsSize + " " + rsTv);
            TFnewNameEdit.setText(rsRoomName);
            TFnewChairEdit.setText(rsChairsAmount);
            TFnewSizeEdit.setText(rsSize);
            boolean TvOut = rsTv!=0;
            CBtv.setSelected(TvOut);

            //CheckBox.set(rsPassword);
            //roleBox.setValue(rsAccount);


        } catch (SQLException ex) {
            System.out.println("bruh");

        }
    }


    @FXML
    private void applyChange(ActionEvent event) throws IOException {

    }


}


