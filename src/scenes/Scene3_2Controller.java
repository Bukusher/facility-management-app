package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sample.DB_Connector;

import java.io.IOException;
import java.util.Optional;

public class Scene3_2Controller extends Scene3Controller {

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
    CheckBox CBroomavailable;




    //sets up new rooms to be available as standard, still changable by user though
    @FXML
    protected void initialize()
    {
        CBroomavailable.setSelected(true);
    }

    @FXML
    private void AddRoom(ActionEvent event) throws IOException, NullPointerException {
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
        boolean inOverheadProjector = CBoverheadprojector.isSelected();
        byte outOverheadProjector = (byte) (inOverheadProjector ? 1 : 0);
        boolean inAvailable = CBroomavailable.isSelected();
        byte outAvailable = (byte) (inAvailable ? 1 : 0);
        String nameAdd = TFnewNameAdd.getText();
        String chairAdd = TFnewChairAdd.getText();
        String size = TFnewSizeAdd.getText();
        //Putting all values in variables
        String AddNewRoomQuery = "INSERT INTO pc2fma2.room " +
                "(`room_id`, `room_availability`, `chairs`, `tv`, `size`, `prejector`, `whiteboard`, `sink`, `microphones`, `stereo`, `overhead_projector`) " +
                "VALUES ('" + nameAdd + "','" + outAvailable + "','" + chairAdd + "','" + outTv + "','"+size + "','"+ outProjector+"','"+outWhiteboard+"','"+outSink+"','"+ outMicrophone+"','"+outStero +"','"+outOverheadProjector+"');";
        connector.executeSQL(AddNewRoomQuery);
        TFnewNameAdd.setText("");
        TFnewSizeAdd.setText("");
        TFnewChairAdd.setText("");
        CBtv.setSelected(false);
        CBoverheadprojector.setSelected(false);
        CBspeakers.setSelected(false);
        CBmicrophone.setSelected(false);
        CBprojector.setSelected(false);
        CBsink.setSelected(false);
        CBwhiteboard.setSelected(false);
    }
    @FXML
    private void helpaddroom()
    {
        helpAlert("Here you can add new rooms.\nPlease fill out all information. If you want the room to be unusable for now (maintanance etc.), please uncheck the corresponding checkbox.");
    }

}
