package scenes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.DB_Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by jonat on 21.05.2019.
 */
public class BookingHistoryController extends ChangeScene {
    @FXML
    TextArea TAbookinghistory;
    @FXML
    TextField TFdeletebookingentry;
    private DB_Connector Connector = new DB_Connector();
    public ResultSet resultSetBookingHistory;


    /*@FXML
    protected void initialize()
    {
        PreparedStatement number = (PreparedStatement) Connector.select("SELECT COUNT (*) FROM `pc2fma2`.`booking`");
        System.out.println(number);
    }*/

    @FXML
    public void initialize() {
        resultSetBookingHistory = Connector.select("SELECT * FROM `pc2fma2`.`booking` WHERE `account_email` = '" + currentusermail() + "' ORDER BY `end_time` DESC");
        String bookinghistoryresults = "";
        try {
            int entry = 1;
            while (resultSetBookingHistory.next()) {
                bookinghistoryresults += "Entry " + entry + " - " + /*" ID " + resultSetBookingHistory.getString(1) + */" Starttime " + resultSetBookingHistory.getString(3) + " Endtime " + resultSetBookingHistory.getString(4) + " Room " + resultSetBookingHistory.getString(5) + "\n";
                //Booking ID disabled, as uninteresting for consumer
                entry++;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        TAbookinghistory.setText(bookinghistoryresults);
    }


    public void deletebooking(javafx.event.ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Booking Deletion");
        alert.setHeaderText("Deleting booking");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {


            int chosenentry = Integer.parseInt(TFdeletebookingentry.getText());
            while (!resultSetBookingHistory.isFirst())
                resultSetBookingHistory.previous();
            for (int i = 1; i < chosenentry; i++)
                resultSetBookingHistory.next();

            //Compare to current time to avoid deleting past bookings
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            StringBuilder sbbookingtime = new StringBuilder(resultSetBookingHistory.getString(4));
            sbbookingtime.setCharAt(10, 'T');
            ChronoLocalDateTime bookingtime = LocalDateTime.parse(sbbookingtime + ".000");
            if (now.compareTo(bookingtime) < 0) {
                String sqldeletebooking = "DELETE FROM `pc2fma2`.`booking` WHERE `booking_id` = '" + resultSetBookingHistory.getString(1) + "'";
                Connector.executeSQL(sqldeletebooking);
                initialize();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Can't delete past bookings");
                a.setContentText("Please delete only future bookings");
                a.showAndWait();
            }
        }

    }
}
