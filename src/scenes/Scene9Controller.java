package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Scene9Controller extends ParentController {
    @FXML
    private TableView TVseeall;

    @FXML
    public void seeallusers(ActionEvent e){
        cleartv();
        ResultSet rs = connector.select("SELECT * FROM `pc2fma2`.`account`");
        ObservableList<User> accounts = FXCollections.observableArrayList();

        try {
            while (rs.next())
            {
                accounts.add(new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(5)));
            }
        }catch(Exception ex)
        {
            errorAlert(ex);
        }
        TableColumn mailcol = new TableColumn("Mail");
        mailcol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("mail"));

        TableColumn namecol = new TableColumn("Name");
        namecol.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));

        TableColumn surnamecol = new TableColumn("Surname");
        surnamecol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("surname"));

        TableColumn acccol = new TableColumn("Account type");
        acccol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("account"));

        TVseeall.setItems(accounts);
        TVseeall.getColumns().addAll(mailcol, namecol, surnamecol, acccol);
    }
    @FXML
    public void seeallrooms(ActionEvent e){
        cleartv();
        ResultSet rs = connector.select("SELECT * FROM `pc2fma2`.`room`");
        ObservableList<Room> roomObservableList = FXCollections.observableArrayList();
        try
        {
            while (rs.next())
            {
                //Equipment
                String outputresults ="";
                if(rs.getString(6).equals("1"))
                    outputresults += "TV ";
                if(rs.getString(7).equals("1"))
                    outputresults += "Projector ";
                if(rs.getString(8).equals("1"))
                    outputresults += "Whiteboard ";
                if(rs.getString(9).equals("1"))
                    outputresults += "Sink ";
                if(rs.getString(10).equals("1"))
                    outputresults += "Microphone(s) ";
                if(rs.getString(11).equals("1"))
                    outputresults += "Stereo/Speakers ";
                if(rs.getString(12).equals("1"))
                    outputresults += "Overhead Projector ";
                roomObservableList.add(new Room(1, rs.getString(1), rs.getInt(4), rs.getInt(5), outputresults));
            }

            TableColumn entrycol = new TableColumn("Entry");
            entrycol.setVisible(false);
            entrycol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("Entry"));

            TableColumn roomcol = new TableColumn("Roomname");
            roomcol.setCellValueFactory(new PropertyValueFactory<Room, String>("roomname"));

            TableColumn chairscol = new TableColumn("Chairs");
            chairscol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("chairs"));

            TableColumn sizecol = new TableColumn("Size (sqm)");
            sizecol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("size"));

            TableColumn equipmentcol = new TableColumn("Equipment");
            equipmentcol.setCellValueFactory(new PropertyValueFactory<Room, String>("equipment"));

            TVseeall.setItems(roomObservableList);
            TVseeall.getColumns().addAll(entrycol, roomcol, chairscol, sizecol, equipmentcol);
        }catch(Exception ex)
        {
            errorAlert(ex);
        }

    }
    @FXML
    public void seeallbookings(ActionEvent e) {
        cleartv();
        ResultSet rs = connector.select("SELECT * FROM `pc2fma2`.`booking`");
        ObservableList<Booking> bookingObservableList = FXCollections.observableArrayList();

        //Making Observable itemlist
        try {
            while (rs.next()) {
                bookingObservableList.add(new Booking(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        }
        catch (SQLException ex) {
            errorAlert(ex);
        }
        //Making columns
        TableColumn idcol = new TableColumn("ID");
        idcol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("bookingID"));

        TableColumn usercol = new TableColumn("User");
        usercol.setCellValueFactory(new PropertyValueFactory<Room, String>("mail"));

        TableColumn roomcol = new TableColumn("Room");
        roomcol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("room"));

        TableColumn startcol = new TableColumn("Starttime");
        startcol.setCellValueFactory(new PropertyValueFactory<Room, Integer>("starttime"));

        TableColumn endcol = new TableColumn("Endtime");
        endcol.setCellValueFactory(new PropertyValueFactory<Room, String>("endtime"));
        TVseeall.setItems(bookingObservableList);
        TVseeall.getColumns().addAll(idcol, roomcol, usercol, startcol, endcol);
    }
    @FXML
    public void helpseeall(ActionEvent event)
    {
        helpAlert("Here you can see all users, bookings or rooms. You can access each by the corresponding button. If you want to sort the items, you can press on the column description.");
    }
    public void cleartv()
    {
        TVseeall.getColumns().clear();
        TVseeall.setEditable(true);
    }
}
