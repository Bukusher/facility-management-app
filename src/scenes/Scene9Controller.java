package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Scene9Controller extends ParentController {
    @FXML
    private TextArea TAseeall;

    @FXML
    public void seeallusers(ActionEvent e) throws SQLException {
        ResultSet rs = connector.select("SELECT * FROM `pc2fma2`.`account`");
        String out ="";
        while (rs.next())
        {
            out+="Mail: " + rs.getString(1) + " Name: " + rs.getString(2) + " Lastname: " + rs.getString(3) + " Account: " + rs.getString(5) + "\n";
        }
        TAseeall.setText(out);
    }
    @FXML
    public void seeallrooms(ActionEvent e) throws SQLException {
        ResultSet rs = connector.select("SELECT * FROM `pc2fma2`.`room`");
        String outputresults ="";
        while (rs.next())
        {
            outputresults += " Room: " + rs.getString(1) + " Chairs: " + rs.getString(4) + " Size: " + rs.getString(5) + "sqm Equipment: ";
            //Equipment
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
            outputresults+="\n";
        }
        TAseeall.setText(outputresults);
    }
    @FXML
    public void seeallbookings(ActionEvent e) throws SQLException {
        ResultSet rs = connector.select("SELECT * FROM `pc2fma2`.`booking`");
        String out ="";
        while (rs.next())
        {
            out+="ID: " + rs.getString(1) + " User: " + rs.getString(2) + " Starttime: " + rs.getString(3) + " Endtime: " + rs.getString(4) + " Room: " + rs.getString(5) + "\n";
        }
        TAseeall.setText(out);
    }
    @FXML
    public void helpseeall(ActionEvent event)
    {
        helpAlert("Here you can see the server logs and download them to your computer if you press the 'download' button.");
    }
}
