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

public class BookRoom {
    SceneChanger sceneChange = new SceneChanger();
    private DB_Connector Connector = new DB_Connector();
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
        String sql="SELECT * FROM `pc2fma2`.`room`";
        if(amountqualifiers>0)
            sql += " where";

        if (!building.isEmpty())
        {
            sql += " `room_building_id` = '";
            sql += building;
            sql += "'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }
        if (!room.isEmpty())
        {
            sql += " `room_id` = '";
            sql += room;
            sql += "'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }
        if (chairs>=0)
        {
            sql += " `chairs` BETWEEN '";
            sql += chairs;
            sql += "' AND '2147483647'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }
        if (size>=0)
        {
            sql += " `size` BETWEEN '";
            sql += size;
            sql += "' AND '2147483647'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }
        if (tv)
        {
            sql += " `tv` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }
        if (projector)
        {
            sql += " `prejector` = '1'";        //really called prEjector on Server? just checked with editroom code
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }
        if (whiteboard)
        {
            sql += " `whiteboard` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }
        if (sink)
        {
            sql += " `sink` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }
        if (microphone)
        {
            sql += " `microphones` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }
        if (speaker)
        {
            sql += " `stereo` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }
        if (overhead)
        {
            sql += " `overhead_projector` = '1'";
            amountqualifiers--;
            if(amountqualifiers>0)
                sql += " and";
        }

        //Execute SQL
        ResultSet rs = Connector.select(sql);

        Integer entry =1;
        String outputresults="";
        while(rs.next())
        {
            outputresults += entry;
            outputresults += " - ";
            outputresults+=rs.getString(1);
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
