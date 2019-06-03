package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DB_Connector;
import sample.SendEmail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/**
 * Created by jonat on 22.05.2019.
 */
public class Scene8Controller extends ParentController {
    @FXML
    TextField TFnamesearch;
    @FXML
    TextField TFroom;
    @FXML
    TableView TVadminresults;
    private DB_Connector Connector = new DB_Connector();
    private SceneChanger sceneChanger = new SceneChanger();
    private boolean lastpressed;

    @FXML
    private void searchName (ActionEvent event)
    {
        cleartv();
        if(TFnamesearch.getText().equals(""))
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Searchfield is empty");
            a.setContentText("You need to put the user mail in the field.");
            a.showAndWait();
        }
        else {
            ResultSet rs = Connector.select("SELECT * FROM `pc2fma2`.`booking` WHERE `account_email` = '" + TFnamesearch.getText() + "' ORDER BY `end_time` DESC");
            lastpressed=true;
            writeresultset(rs);
        }
    }

    private void cleartv()
    {
        //Clear out previous searches
        TVadminresults.getColumns().clear();
        TVadminresults.setEditable(true);
    }
    @FXML
    private void searchRoom (ActionEvent event)
    {
        cleartv();
        if(TFroom.getText().equals(""))
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Searchfield is empty");
            a.setContentText("You need to put the roomname in the field.");
            a.showAndWait();
        }
        else {
            ResultSet rs = Connector.select("SELECT * FROM `pc2fma2`.`booking` WHERE `room_room_id` = '" + TFroom.getText() + "'");
            lastpressed=false;
            writeresultset(rs);
        }
    }

    private void writeresultset(ResultSet rs)
    {
        //Making Observable itemlist
        ObservableList<Booking> bookingObservableList = FXCollections.observableArrayList();
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
        usercol.setCellValueFactory(new PropertyValueFactory<Room, String>("mail"));

        TableColumn roomcol = new TableColumn("Room");
        roomcol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("room"));

        TableColumn startcol = new TableColumn("Starttime");
        startcol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("starttime"));

        TableColumn endcol = new TableColumn("Endtime");
        endcol.setCellValueFactory(new PropertyValueFactory<Room, String>("endtime"));
        TVadminresults.setItems(bookingObservableList);
        TVadminresults.getColumns().addAll(idcol, roomcol, usercol, startcol, endcol);
        //Listen for doubleclicks in table
        TVadminresults.setOnMouseClicked( eventclick -> {
            if( eventclick.getClickCount() == 2 ) {
                Booking currentclicked = (Booking) TVadminresults.getSelectionModel().getSelectedItem();
                try {
                    deletebooking(currentclicked.getBookingID(),currentclicked.getRoom(), currentclicked.getStarttime(), currentclicked.getEndtime(), currentclicked.getMail());
                } catch (Exception e) {
                    errorAlert(e);
                }
            }});
    }

    private void deletebooking (Integer id, String room, String start, String end, String user) throws SQLException {
        //Compare to current time to avoid deleting past bookings
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        StringBuilder sbbookingtime =new StringBuilder(end);
        sbbookingtime.setCharAt(10, 'T');
        ChronoLocalDateTime bookingtime = LocalDateTime.parse(sbbookingtime + ".000");
        if (now.compareTo(bookingtime)<0)
        {
            //Confirmation alert for delete booking
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Booking Deletion");
            alert.setHeaderText("This will delete booking.\nRoom: " + room + "\nStarttime: " + start + "\nEndtime: " + end);
            alert.setContentText("Are you ok with this?\nA mail will be sent to the owner of the booking to notify him or her that you delteted the booking");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String sqldeletebooking = "DELETE FROM `pc2fma2`.`booking` WHERE `booking_id` = '" + id + "'";
                //send mail
                SendEmail.send(user, "Your booking was deleted", "The Administrator " + currentusermail() + " deleted your booking with the ID " + id + "\nRoom: " + room + "\nFrom: " + start + "\nTo: " + end);
                //reload, wouldn't work if the corresponding textfield was changed/edited since the last search
                Connector.executeSQL(sqldeletebooking);
                if (lastpressed)
                    searchName(new ActionEvent());
                else
                    searchRoom(new ActionEvent());
            }
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Can't delete past bookings");
            a.setContentText("Please delete only future bookings");
            a.showAndWait();
        }
    }
    @FXML
    private void help (ActionEvent e)
    {
        helpAlert("Here you can search all bookings for any given User or room. You can delete a chosen entry by double clicking it. Be sure that only admins like you can do that.");
    }
}
