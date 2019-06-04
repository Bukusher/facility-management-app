package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sample.DB_Connector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Scene3_1Controller extends Scene3Controller{

    @FXML
    TextField TFsearchRoomEdit;
    @FXML
    TextField TFnewNameEdit;
    @FXML
    TextField TFnewChairEdit;
    @FXML
    TextField TFnewSizeEdit;
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
    CheckBox CBroomavailable;


    @FXML
    private void Search(ActionEvent event) throws IOException {
        String searchRoom = "SELECT * FROM `pc2fma2`.`room` where room_id  = '" + TFsearchRoomEdit.getText() + "'";
        String rsRoomName = "";
        String rsChairsAmount = "";
        String rsSize = "";
        byte rsTv = 0;
        byte rsPrejector = 0;
        byte rsWhiteboard = 0;
        byte rsSink = 0;
        byte rsMicrophone = 0;
        byte rsStereo = 0;
        byte rsOverheadProjector = 0;
        byte rsavailable =0;

        try {
            ResultSet resultSet = connector.select(searchRoom);
            if (resultSet.next()) {
                rsRoomName = resultSet.getString(1);
                rsChairsAmount = resultSet.getString(4);
                rsSize = resultSet.getString(5);
                rsTv = resultSet.getByte(6);
                rsPrejector = resultSet.getByte(7);
                rsWhiteboard = resultSet.getByte(8);
                rsSink = resultSet.getByte(9);
                rsMicrophone = resultSet.getByte(10);
                rsStereo = resultSet.getByte(11);
                rsOverheadProjector = resultSet.getByte(12);
                rsavailable = resultSet.getByte(2);
            }
            if (rsRoomName.equals(TFsearchRoomEdit.getText())) {
                TFnewNameEdit.setText(rsRoomName);
                TFnewChairEdit.setText(rsChairsAmount);
                TFnewSizeEdit.setText(rsSize);
                boolean tvOut = rsTv != 0;
                CBtv.setSelected(tvOut);
                boolean prejectorOut = rsPrejector != 0;
                CBprojector.setSelected(prejectorOut);
                boolean whiteboardOut = rsWhiteboard != 0;
                CBwhiteboard.setSelected(whiteboardOut);
                boolean sinkOut = rsSink != 0;
                CBsink.setSelected(sinkOut);
                boolean microphoneOut = rsMicrophone != 0;
                CBmicrophone.setSelected(microphoneOut);
                boolean stereoOut = rsStereo != 0;
                CBspeakers.setSelected(stereoOut);
                boolean overheadProjectorOut = rsOverheadProjector != 0;
                CBoverheadProjector.setSelected(overheadProjectorOut);
                boolean availableOut = rsavailable != 0;
                CBroomavailable.setSelected(availableOut);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Room Does Not Exist");
                alert.setHeaderText(null);
                alert.setContentText("This room do not exist, Search another Room ID ");

                alert.showAndWait();

            }
        } catch (SQLException ex) {
            System.err.println(ex);
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
        boolean inAvailable = CBroomavailable.isSelected();
        byte outavailable = (byte) (inAvailable ? 1 : 0);
        String nameedit = TFnewNameEdit.getText();
        String chairedit = TFnewChairEdit.getText();
        String size = TFnewSizeEdit.getText();
        String searchRoom = TFsearchRoomEdit.getText();
        int chairEditInt = Integer.parseInt(chairedit);
        int sizeInt = Integer.parseInt(size);
        String editRoomQuery = "UPDATE room SET `room_id` = '" + nameedit + "', `room_availability` = '" + outavailable + "', `chairs` = '" + chairedit + "', `tv` = '" + outTv +
                "', `size` = '" + size + "', `prejector` = '" + outProjector + "', `whiteboard` = '" + outWhiteboard +
                "', `sink` = '" + outSink + "', `microphones` = '" + outMicrophone + "', `stereo` = '" + outStero + "', `overhead_projector` = '" + outOverheadProjector +
                "' WHERE `room_id` = '" + searchRoom + "'";
        if (chairEditInt > 0 && sizeInt > 0) {
            connector.executeSQL(editRoomQuery);
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
            CBroomavailable.setSelected(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chair or size amount is negative");
            alert.setHeaderText(null);
            alert.setContentText("Chair or room size amount is negative please enter positive number ");

            alert.showAndWait();
        }
    }
    @FXML
    private void deleteRoom(ActionEvent event) throws IOException {
        String roomName = TFsearchRoomEdit.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Room Confirmation");
        alert.setHeaderText("Delete Room Confirmation");
        alert.setContentText("Are you sure you want to delete room?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            connector.executeSQL("DELETE FROM booking WHERE `room_room_id` = '" + roomName + "'");
            connector.executeSQL("DELETE FROM room WHERE `room_id` = '" + roomName + "'");
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
            CBroomavailable.setSelected(false);
        }
    }
    @FXML
    private void helpeditroom(ActionEvent e)
    {
        helpAlert("By writing a room name in the searchfield and pressing the search button, the information about the room will be loaded into the appropriate fields. You can then change those attributes and press 'Apply changes' to upload them to the server. Alternatively, you can delete the room permanently (A very long time).");
    }
}


