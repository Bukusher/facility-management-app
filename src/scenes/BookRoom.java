package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
//import jdk.nashorn.internal.ir.WhileNode;
import sample.DB_Connector;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;


public class BookRoom extends ChangeScene{
    private DB_Connector Connector = new DB_Connector();
    private ResultSet rs;
    private StringBuilder fromsb;
    private StringBuilder tosb;
    @FXML
    TextField TFsearchroomname;
    @FXML
    TextField TFsearchchairamount;
    @FXML
    TextField TFsearchroomsize;
    @FXML
    CheckBox CBsearchtv;
    @FXML
    CheckBox CBsearchprojector;
    @FXML
    CheckBox CBsearchwhiteboard;
    @FXML
    CheckBox CBsearchsink;
    @FXML
    CheckBox CBsearchmicrophone;
    @FXML
    CheckBox CBsearchspeakers;
    @FXML
    CheckBox CBsearchoverheadprojector;
    @FXML
    DateTimePicker DPsearchfrom;
    @FXML
    DateTimePicker DPsearchto;
    @FXML
    TextArea TArooms;
    @FXML
    TextField TFroombookentry;

    @FXML
    private void searchRooms(ActionEvent event) throws SQLException {
        String room = TFsearchroomname.getText();
        Integer chairs;
        if(TFsearchchairamount.getText().isEmpty())
            chairs=-1;
        else
            chairs = Integer.parseInt(TFsearchchairamount.getText());
        Integer size;
        if(TFsearchroomsize.getText().isEmpty())
            size=-1;
        else
            size = Integer.parseInt(TFsearchroomsize.getText());
        Boolean tv = CBsearchtv.isSelected();
        Boolean projector = CBsearchoverheadprojector.isSelected();
        Boolean whiteboard = CBsearchwhiteboard.isSelected();
        Boolean sink = CBsearchsink.isSelected();
        Boolean microphone = CBsearchmicrophone.isSelected();
        Boolean speaker = CBsearchspeakers.isSelected();
        Boolean overhead = CBsearchoverheadprojector.isSelected();
        fromsb = new StringBuilder(DPsearchfrom.getDateTimeValue().toString());
        tosb = new StringBuilder(DPsearchto.getDateTimeValue().toString());
        fromsb.setCharAt(10, ' ');
        tosb.setCharAt(10, ' ');
        fromsb.append(":00");
        tosb.append(":00");






        //Make SQL queurie
        String sqlroom="SELECT * FROM `pc2fma2`.`room` WHERE `room_availability` = '1'";
        if (!room.isEmpty())
        {
            sqlroom += " AND `room_id` = '" + room + "'";
        }
        if (chairs>=0)
        {
            sqlroom += " AND `chairs` BETWEEN '" + chairs + "' AND '2147483647'";
        }
        if (size>=0)
        {
            sqlroom += " AND `size` BETWEEN '" + size + "' AND '2147483647'";
        }
        if (tv)
        {
            sqlroom += " `tv` = '1'";
        }
        if (projector)
        {
            sqlroom += " AND `prejector` = '1'";        //really called prEjector on Server
        }
        if (whiteboard)
        {
            sqlroom += " AND `whiteboard` = '1'";
        }
        if (sink)
        {
            sqlroom += " AND `sink` = '1'";
        }
        if (microphone)
        {
            sqlroom += " AND `microphones` = '1'";
        }
        if (speaker)
        {
            sqlroom += " AND `stereo` = '1'";
        }
        if (overhead)
        {
            sqlroom += " AND `overhead_projector` = '1'";
        }
        //sqlroom+=" EXCEPT SELECT * FROM `pc2fma2`.`booking` WHERE `start_time` BETWEEN '" + fromsb + "' AND '" + tosb + "' OR `end_time` BETWEEN '" + fromsb + "' AND '" + tosb + "'";
        //sqlroom+= "LEFT JOIN `pc2fma2`.`booking` ON `pc2fma2`.`room`.`room_id` = `pc2fma2`.`booking`.`room_room_id` WHERE `start_time` NOT BETWEEN '" + fromsb + "' AND '" + tosb + "' OR `end_time` NOT BETWEEN '" + fromsb + "' AND '" + tosb + "'";
        //sqlroom+=" EXCEPT SELECT * FROM `pc2fma2`.`booking` WHERE `start_time` BETWEEN '" + fromsb + "' AND '" + tosb + "' OR `end_time` BETWEEN '" + fromsb + "' AND '" + tosb + "'";






        /*
        String sqltime = "SELECT * FROM `pc2fma2`.`booking`"; //WHERE `start_time` BETWEEN '" + fromsb + "' AND '" + tosb + "'";
        ResultSet rs2 = Connector.select(sqltime);
        try {
        while (rs2.next())

            System.out.println(rs.getString(1));
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        */


        //Check if booking is in future
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        ChronoLocalDateTime timefrom = ChronoLocalDateTime.from(DPsearchfrom.getDateTimeValue());
        ChronoLocalDateTime timeto = ChronoLocalDateTime.from(DPsearchto.getDateTimeValue());

        System.out.println(timefrom.compareTo(timeto));

        //Switch between being able and not being able to book in the past
        if(now.compareTo(timefrom)<0 && now.compareTo(timeto)<0 && timefrom.compareTo(timeto)<0)
        //if (true)
        {
            //Execute SQL
            rs = Connector.select(sqlroom);

            Integer entry = 1;
            String outputresults = "";
            while (rs.next()) {
                outputresults += entry + " - " + rs.getString(1) + "\n";
                entry++;
            }
            TArooms.setText(outputresults);

        }
        else if (timeto.compareTo(timefrom)<0)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Your 'from' is later than your 'to'.");
            a.setContentText("Please note, that you will get this error as well, if you are trying to book during the current minute");
            a.showAndWait();
        }
        else if (timefrom.compareTo(timeto)==0)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("You are trying to book between 2 identical times.");
            a.setContentText("This would result in no actual time being booked.");
            a.showAndWait();
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Can't book in the past!");
            a.setContentText("Please book only in the future.");
            a.showAndWait();
        }
    }

    @FXML
    private void bookroom(ActionEvent event) throws SQLException {
        Integer chosenentry = 1;
        if(!TFroombookentry.getText().isEmpty())
            chosenentry=Integer.parseInt(TFroombookentry.getText());
        while (!rs.isFirst())
            rs.previous();
        for (int i=1; i<chosenentry;i++)
            rs.next();
        String sqlbook = "INSERT INTO `pc2fma2`.`booking` (`account_email`, `start_time`, `end_time`, `room_room_id`) VALUES ('" + currentusermail() + "', '"+String.valueOf(fromsb)+"', '"+String.valueOf(tosb)+"', '" + rs.getString(1) + "')";
        Connector.executeSQL(sqlbook);
        TFroombookentry.setText("");
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Bookingconfirmation");
        a.setHeaderText("The booking was successful!");
        a.setContentText("You booked room " + rs.getString(1) + " from " + String.valueOf(fromsb) + " to " + String.valueOf(tosb) + ".");
        a.showAndWait();
    }
}
