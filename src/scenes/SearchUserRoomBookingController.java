package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.DB_Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Created by jonat on 22.05.2019.
 */
public class SearchUserRoomBookingController extends ChangeScene {
    @FXML
    TextField TFnamesearch;
    @FXML
    TextField TFroom;
    @FXML
    TextArea TAbookinghistory;
    @FXML
    TextField TFdeletebookingentry;
    private DB_Connector Connector = new DB_Connector();
    private ResultSet rs;
    private SceneChanger sceneChanger = new SceneChanger();
    private boolean lastpressed;

    @FXML
    private void searchName (ActionEvent event)
    {
        if(TFnamesearch.getText().equals(""))
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Searchfield is empty");
            a.setContentText("You need to put the user mail in the field.");
            a.showAndWait();
        }
        else {
            rs = Connector.select("SELECT * FROM `pc2fma2`.`booking` WHERE `account_email` = '" + TFnamesearch.getText() + "' ORDER BY `end_time` DESC");
            lastpressed=true;
            writeresultset();
        }
    }

    @FXML
    private void searchRoom (ActionEvent event)
    {
        if(TFroom.getText().equals(""))
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Searchfield is empty");
            a.setContentText("You need to put the roomname in the field.");
            a.showAndWait();
        }
        else {
            rs = Connector.select("SELECT * FROM `pc2fma2`.`booking` WHERE `room_room_id` = '" + TFroom.getText() + "'");
            lastpressed=false;
            writeresultset();
        }
    }

    private void writeresultset()
    {
        String bookinghistoryresults ="";
        try {
            int entry = 1;
            while (rs.next())
            {
                bookinghistoryresults+= "Entry " + entry + " - " + " ID " + rs.getString(1) + " User " + rs.getString(2) + " Starttime " + rs.getString(3) + " Endtime " + rs.getString(4) + " Room " + rs.getString(5) + "\n";
                entry++;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        TAbookinghistory.setText(bookinghistoryresults);
    }

    @FXML
    private void deletebooking (ActionEvent event) throws SQLException {
        int chosenentry = Integer.parseInt(TFdeletebookingentry.getText());
        while (!rs.isFirst())
            rs.previous();
        for (int i=1; i<chosenentry;i++)
            rs.next();
        //Compare to current time to avoid deleting past bookings
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sbbookingtime =new StringBuilder(rs.getString(4));
        sbbookingtime.setCharAt(10, 'T');
        ChronoLocalDateTime bookingtime = LocalDateTime.parse(sbbookingtime + ".000");
        if (now.compareTo(bookingtime)<0)
        {
            String sqldeletebooking="DELETE FROM `pc2fma2`.`booking` WHERE `booking_id` = '" + rs.getString(1) + "'";

            //reload, wouldn't work if the corresponding textfield was changed/edited since the last search
            Connector.executeSQL(sqldeletebooking);
            if(lastpressed)
                searchName(new ActionEvent());
            else
                searchRoom(new ActionEvent());
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Can't delete past bookings");
            a.setContentText("Please delete only future bookings");
            a.showAndWait();
        }
        TFdeletebookingentry.setText("");
    }
}
