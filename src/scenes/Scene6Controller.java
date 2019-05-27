package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.DB_Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Scene6Controller extends ParentController {
    private DB_Connector Connector = new DB_Connector();
    private ResultSet rs;
    private StringBuilder fromsb;
    private StringBuilder tosb;
    public ArrayList<Integer> entryindex=new ArrayList();
    private ObservableList<String> sortList = FXCollections.observableArrayList("Roomname", "Chairs", "Size");
    private ObservableList<String> ascdescList = FXCollections.observableArrayList("Ascending", "Descending");
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
    private ChoiceBox DDsortby;
    @FXML
    private ChoiceBox DDascdesc;

    @FXML
    public void initialize()
    {
        //Sets the Multiple Choice DropDown Menus
        DDsortby.setItems(sortList);
        DDascdesc.setItems(ascdescList);
        DDsortby.setValue("Roomname");
        DDascdesc.setValue("Ascending");
    }

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
        fromsb.delete(19,29);
        tosb.delete(19,29);


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
        //Sorting the results
        sqlroom+= " ORDER BY ";
        if(DDsortby.getValue().equals("Roomname"))
            sqlroom+="`room_id` ";
        else if (DDsortby.getValue().equals("Chairs"))
            sqlroom+="`chairs` ";
        else if (DDsortby.getValue().equals("Size"))
            sqlroom+="`size` ";
        if(DDascdesc.getValue().equals("Ascending"))
            sqlroom+= "ASC";
        else if (DDascdesc.getValue().equals("Descending"))
            sqlroom+="DESC";


        //Check if booking is in future
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        ChronoLocalDateTime searchtimefrom = ChronoLocalDateTime.from(DPsearchfrom.getDateTimeValue());
        ChronoLocalDateTime searchtimeto = ChronoLocalDateTime.from(DPsearchto.getDateTimeValue());


        //check with past bookings for intersections.
        //This could potentially done by a better Single SQL select that excludes romms that have bookings in the given timeframe, however multiple JOINs and EXCLUDEs are needed and I didn't get it to work
        String sqlorders = "SELECT * FROM `pc2fma2`.`booking`";
        ResultSet rsbookings =Connector.select(sqlorders);

        ArrayList<String> bookedroomsarraylist = new ArrayList();
        while (rsbookings.next())
        {
            StringBuilder sbtempfrom = new StringBuilder(rsbookings.getString(3));
            StringBuilder sbtempto = new StringBuilder(rsbookings.getString(4));
            sbtempfrom.setCharAt(10, 'T');
            sbtempto.setCharAt(10, 'T');
            ChronoLocalDateTime existingbookingfrom = ChronoLocalDateTime.from(LocalDateTime.parse(sbtempfrom));
            ChronoLocalDateTime existingbookingto = ChronoLocalDateTime.from(LocalDateTime.parse(sbtempto));


            //Checking and excluding already booked room
            /*if searchtimeto<existingbookingfrom ->ok
            if existingbookingto<searchtimefrom ->ok
             */
            if(searchtimeto.compareTo(existingbookingfrom)<=0 ||existingbookingto.compareTo(searchtimefrom)<=0)
            {
                //for some reason doesn't work any other way properly, please don't change!
            }
            else
            {
                bookedroomsarraylist.add(rsbookings.getString(5));
            }
        }



        //Switch between being able and not being able to book in the past
        if(now.compareTo(searchtimefrom)<0 && now.compareTo(searchtimeto)<0 && searchtimefrom.compareTo(searchtimeto)<0)
        //if (true)
        {
            //Execute SQL
            rs = Connector.select(sqlroom);

            Integer entry = 1;
            String outputresults = "";
            boolean flag;
            for (int i=0;rs.next();i++) {
                flag=false;
                for (int j=0;j<bookedroomsarraylist.size();j++)
                {
                    if(rs.getString(1).equals(bookedroomsarraylist.get(j))) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    outputresults +=  "Entry: " + entry + " Room: " + rs.getString(1) + " Chairs: " + rs.getString(4) + " Size: " + rs.getString(5) + "sqm Equipment: ";
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
                    entry++;
                    entryindex.add(i+1);
                }
            }
            TArooms.setText(outputresults);

        }
        else if (searchtimeto.compareTo(searchtimefrom)<0)
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Your 'from' is later than your 'to'.");
            a.setContentText("Please note, that you will get this error as well, if you are trying to book during the current minute");
            a.showAndWait();
        }
        else if (searchtimefrom.compareTo(searchtimeto)==0)
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

    private boolean checkRoomForBooking (String room) throws SQLException {
        String sqlorders = "SELECT * FROM `pc2fma2`.`booking`";
        ResultSet rsbookings =Connector.select(sqlorders);

        ChronoLocalDateTime searchtimefrom = ChronoLocalDateTime.from(DPsearchfrom.getDateTimeValue());
        ChronoLocalDateTime searchtimeto = ChronoLocalDateTime.from(DPsearchto.getDateTimeValue());


        //Checking and excluding already booked room
            /*if searchtimeto<existingbookingfrom ->ok
            if existingbookingto<searchtimefrom ->ok
             */
            boolean flag = false;
        while (rsbookings.next()) {
            StringBuilder sbtempfrom = new StringBuilder(rsbookings.getString(3));
            StringBuilder sbtempto = new StringBuilder(rsbookings.getString(4));
            sbtempfrom.setCharAt(10, 'T');
            sbtempto.setCharAt(10, 'T');
            ChronoLocalDateTime existingbookingfrom = ChronoLocalDateTime.from(LocalDateTime.parse(sbtempfrom));
            ChronoLocalDateTime existingbookingto = ChronoLocalDateTime.from(LocalDateTime.parse(sbtempto));
            if(room.equals(rsbookings.getString(5))) {
                if (searchtimeto.compareTo(existingbookingfrom) <= 0 || existingbookingto.compareTo(searchtimefrom) <= 0) {
                    //for some reason doesn't work any other way properly, please don't change!
                } else {
                    flag=true;
                    break;
                }
            }
        }
        return flag;
    }

    @FXML
    private void bookroom(ActionEvent event) throws SQLException {
        if (rs == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Please make a new search before you try to book.");
            a.setContentText("There might have been an error, or you tried to book without making a new search.");
            a.showAndWait();
        } else {
            Integer chosenentry = entryindex.get(0);
            if (!TFroombookentry.getText().isEmpty())
                chosenentry = entryindex.get(Integer.parseInt(TFroombookentry.getText()) - 1);
            while (!rs.isFirst())
                rs.previous();
            for (int i = 1; i < chosenentry; i++)
                rs.next();
            if (checkRoomForBooking(rs.getString(1))) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                while (!rs.isFirst())
                    rs.previous();
                a.setTitle("ERROR");
                a.setHeaderText("The chosen room was booked while you waited.");
                a.setContentText("please select a new room");
                a.showAndWait();
            } else {
                String sqlbook = "INSERT INTO `pc2fma2`.`booking` (`account_email`, `start_time`, `end_time`, `room_room_id`) VALUES ('" + currentusermail() + "', '" + String.valueOf(fromsb) + "', '" + String.valueOf(tosb) + "', '" + rs.getString(1) + "')";
                System.out.println(sqlbook);
                Connector.executeSQL(sqlbook);
                TFroombookentry.setText("");
                fromsb.delete(16, 29);
                tosb.delete(16, 29);
                TFroombookentry.setText("");
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Bookingconfirmation");
                a.setHeaderText("The booking was successful!");
                a.setContentText("You booked room " + rs.getString(1) + " from " + String.valueOf(fromsb) + " to " + String.valueOf(tosb) + ".");
                rs = null;
                TArooms.setText("");
                a.showAndWait();
            }
        }
    }
}