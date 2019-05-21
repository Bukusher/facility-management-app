package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.AccountType;
import sample.DB_Connector;

import java.io.DataInput;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

import static jdk.nashorn.internal.parser.TokenType.NOT;

public class BookRoom {
    SceneChanger sceneChange = new SceneChanger();
    private DB_Connector Connector = new DB_Connector();
    private ResultSet rs;
    @FXML
    TextField TFsearchbuildingname;
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
        String building = TFsearchbuildingname.getText();
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
        StringBuilder fromsb = new StringBuilder(DPsearchfrom.getDateTimeValue().toString());
        StringBuilder tosb = new StringBuilder(DPsearchto.getDateTimeValue().toString());
        fromsb.setCharAt(10, ' ');
        tosb.setCharAt(10, ' ');
        fromsb.append(":00");
        tosb.append(":00");
        String from = String.valueOf(fromsb);
        String to = String.valueOf(tosb);




        //Check how many Qualifiers are selected
        Integer amountqualifiers=0;
        if(!building.isEmpty())
            amountqualifiers++;
        if(!room.isEmpty())
            amountqualifiers++;
        if(chairs>=0)
            amountqualifiers++;
        if(size>=0)
            amountqualifiers++;
        if(tv)
            amountqualifiers++;
        if(projector)
            amountqualifiers++;
        if(whiteboard)
            amountqualifiers++;
        if(sink)
            amountqualifiers++;
        if(microphone)
            amountqualifiers++;
        if(speaker)
            amountqualifiers++;
        if(overhead)
            amountqualifiers++;


        //Make SQL queurie
        String sqlroom="SELECT * FROM `pc2fma2`.`room`";
        String sqlbooking="SELECT * FROM `pc2fma2`.`booking`";
        if(amountqualifiers>0)
            sqlroom += " where";

        if (!building.isEmpty())
        {
            sqlroom += " `room_building_id` = '";
            sqlroom += building;
            sqlroom += "'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }
        if (!room.isEmpty())
        {
            sqlroom += " `room_id` = '";
            sqlroom += room;
            sqlroom += "'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }
        if (chairs>=0)
        {
            sqlroom += " `chairs` BETWEEN '";
            sqlroom += chairs;
            sqlroom += "' AND '2147483647'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }
        if (size>=0)
        {
            sqlroom += " `size` BETWEEN '";
            sqlroom += size;
            sqlroom += "' AND '2147483647'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }
        if (tv)
        {
            sqlroom += " `tv` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }
        if (projector)
        {
            sqlroom += " `prejector` = '1'";        //really called prEjector on Server? just checked with editroom code
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }
        if (whiteboard)
        {
            sqlroom += " `whiteboard` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }
        if (sink)
        {
            sqlroom += " `sink` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }
        if (microphone)
        {
            sqlroom += " `microphones` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }
        if (speaker)
        {
            sqlroom += " `stereo` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }
        if (overhead)
        {
            sqlroom += " `overhead_projector` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sqlroom += " and";
        }

        //Execute SQL
        rs = Connector.select(sqlroom);

        Integer entry =1;
        String outputresults="";
        while(rs.next())
        {
            outputresults+=entry + " - " + rs.getString(2) + "-" + rs.getString(1) + "\n";
            entry++;
        }
        TArooms.setText(outputresults);
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
        StringBuilder fromsb = new StringBuilder(DPsearchfrom.getDateTimeValue().toString());
        StringBuilder tosb = new StringBuilder(DPsearchto.getDateTimeValue().toString());
        fromsb.setCharAt(10, ' ');
        tosb.setCharAt(10, ' ');
        fromsb.append(":00");
        tosb.append(":00");
        String from = String.valueOf(fromsb);
        String to = String.valueOf(tosb);
        //String sqlbook = "INSERT INTO `pc2fma2`.`booking` VALUES ('Admin', '" + rs.getString(1) + "', '" + rs.getString(2) + "', '" + from +"', '" + to + "', 'test');";
        //String sqlbook = "INSERT INTO `pc2fma2`.`booking` (user-email,room-id, building-id, start-time, end-time) VALUES ('Admin', '1', '1', '2019-04-30 14:40:00', '2019-05-31 14:40:00')";
        String sqlbook = "INSERT INTO `pc2fma2`.`booking` (user-email,room-id, start-time, end-time) VALUES ('Admin', '1', '2019-04-30 14:40:00', '2019-05-31 14:40:00')";
        //String sqlbook = "INSERT INTO `pc2fma2`.`booking` NOT NULL AUTOINCREMENT, 'Admin', '1', '1', '" + DPsearchfrom.getDateTimeValue() + ":00', '" + DPsearchto.getDateTimeValue() + "', 'TEST'";
        System.out.println(sqlbook);
        Connector.select(sqlbook);
        ResultSet rs2 = Connector.select("SELECT * FROM `pc2fma2`.`booking`");
        String outputresults ="";
        Integer entry=1;
        while(rs2.next())
        {
            outputresults += entry;
            outputresults += " - ";
            outputresults+=rs.getString(1);
            outputresults+=rs.getString(2);
            outputresults+=rs.getString(3);
            outputresults+=rs.getString(4);
            outputresults+=rs.getString(5);
            outputresults+=rs.getString(6);
            outputresults+=rs.getString(7);

            entry++;
            outputresults +="\n";
        }
        TArooms.setText(outputresults);
    }




    @FXML
    private void Dashboard(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene2Dashboard.fxml");
    }


    @FXML
    private void logout(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene1Login.fxml");
    }
}
