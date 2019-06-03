package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Created by jonat on 21.05.2019.
 */
public class Scene7Controller extends ParentController {
    @FXML
    TableView TVbookinghistory;

    @FXML
    public void initialize()
    {
        //Clear out previous searches
        TVbookinghistory.getColumns().clear();
        TVbookinghistory.setEditable(true);
        ObservableList<Booking> bookingObservableList = FXCollections.observableArrayList();

        ResultSet rs = connector.select("SELECT * FROM `pc2fma2`.`booking` WHERE `account_email` = '" + currentusermail() + "' ORDER BY `end_time` DESC");
        String bookinghistoryresults ="";

        //Making Observable itemlist
        try {
            while (rs.next()) {
                bookingObservableList.add(new Booking(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        }
            catch (SQLException e) {
            errorAlert(e);
        }

        //Making columns
        TableColumn idcol = new TableColumn("ID");
        idcol.setVisible(false);
        idcol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("bookingID"));

        TableColumn usercol = new TableColumn("User");
        usercol.setVisible(false);
        usercol.setCellValueFactory(new PropertyValueFactory<Room, String>("mail"));

        TableColumn roomcol = new TableColumn("Room");
        roomcol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("room"));

        TableColumn startcol = new TableColumn("Starttime");
        startcol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("starttime"));

        TableColumn endcol = new TableColumn("Endtime");
        endcol.setCellValueFactory(new PropertyValueFactory<Room, String>("endtime"));
        TVbookinghistory.setItems(bookingObservableList);
        TVbookinghistory.getColumns().addAll(idcol, roomcol, usercol, startcol, endcol);


        //Listen for doubleclicks in table
        TVbookinghistory.setOnMouseClicked( eventclick -> {
            if( eventclick.getClickCount() == 2 ) {
                Booking currentclicked = (Booking) TVbookinghistory.getSelectionModel().getSelectedItem();
                try {
                    deletebooking(currentclicked.getBookingID(),currentclicked.getRoom(), currentclicked.getStarttime(), currentclicked.getEndtime());
                } catch (Exception e) {
                    errorAlert(e);
                }
            }});
    }

    public void deletebooking (Integer id, String room, String start, String end){


        //Confirmation alert for delete booking
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Booking Deletion");
        alert.setHeaderText("This will delete booking.\nRoom: " + room + "\nStarttime: " + start + "\nEndtime: " + end);
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

            //Compare to current time to avoid deleting past bookings
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            StringBuilder sbbookingtime = new StringBuilder(end);
            sbbookingtime.setCharAt(10, 'T');
            ChronoLocalDateTime bookingtime = LocalDateTime.parse(sbbookingtime + ".000");
            if (now.compareTo(bookingtime) < 0) {
                String sqldeletebooking = "DELETE FROM `pc2fma2`.`booking` WHERE `booking_id` = '" + id + "'";
                connector.executeSQL(sqldeletebooking);
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
    @FXML
    private void help(ActionEvent e)
    {
        helpAlert("Here you can see all your bookings, sorted by their ending date. You can change the sorting by pressing on the column names. If you want to delete a booking, double click on it. You can only delete entries that aren't over yet.");
    }
}
