package scenes;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import sample.DB_Connector;

import javax.annotation.Resources;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jonat on 21.05.2019.
 */
public class BookingHistoryController extends ChangeScene{
    @FXML
    TextArea TAbookinghistory;
    private DB_Connector Connector = new DB_Connector();
    public ResultSet resultSetBookingHistory;
    

    @FXML
    protected void initialize()
    {
        resultSetBookingHistory =Connector.select("SELECT * FROM `pc2fma2`.`booking`");
        String bookinghistoryresults ="";
        try {
            resultSetBookingHistory.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            do {
                bookinghistoryresults+="ID " + resultSetBookingHistory.getString(1) + " Mail " + resultSetBookingHistory.getString(2) + " Starttime " + resultSetBookingHistory.getString(3) + " Endtime " + resultSetBookingHistory.getString(4) + " Room " + resultSetBookingHistory.getString(5) + "\n";
            }while (!resultSetBookingHistory.last());
        } catch (SQLException e) {
            System.err.println(e);
        }
        TAbookinghistory.setText(bookinghistoryresults);
    }
}
