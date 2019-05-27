package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.DB_Connector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Scene2Controller extends ParentController {
    @FXML
    ImageView IVlogo;
    @FXML
    Button BTbooking;
    @FXML
    Button BThistory;
    @FXML
    Button BTroomAdmin;
    @FXML
    Button BTadmin;
    @FXML
    Button BTsearch;
    @FXML
    Button BTlog;

    @FXML
    public void initialize() {
        //Logo
        try {
            String fn;
            if (sceneChanger.isDarkModeOnFileRead())
                fn = "src/logo/LogoDarkBackground.png";
            else
                fn = "src/logo/LogoWhiteBackground.png";
            IVlogo.setImage(new Image(new FileInputStream(fn)));
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
        //Logo over

        //set buttons to visible/invisible for different user types
        try {
            String role = currentuseraccounttype();
            if (role != null)
                switch (role) {
                    case "employee":
                        BTbooking.setVisible(true);
                        BThistory.setVisible(true);
                        BTroomAdmin.setVisible(false);
                        BTadmin.setVisible(false);
                        BTsearch.setVisible(false);
                        BTlog.setVisible(false);
                        break;
                    case "administrator":
                        BTbooking.setVisible(true);
                        BThistory.setVisible(true);
                        BTroomAdmin.setVisible(true);
                        BTadmin.setVisible(true);
                        BTsearch.setVisible(true);
                        BTlog.setVisible(true);
                        break;
                    case "manager":
                        BTbooking.setVisible(true);
                        BThistory.setVisible(true);
                        BTroomAdmin.setVisible(true);
                        BTadmin.setVisible(false);
                        BTsearch.setVisible(true);
                        BTlog.setVisible(false);
                }
        } catch (SQLException e) {
            System.err.println(e);
            ;
        }
    }


    @FXML
    private void Setting(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene4settings.fxml");
    }

    @FXML
    private void BookingHistory(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene7BookingHistory.fxml");
    }

    @FXML
    private void BookingRoom(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene6BookRoom.fxml");
    }

    @FXML
    private void UserAdmin(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene5UserAdmin.fxml");
    }

    @FXML
    private void Logs(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene9Logs.fxml");
    }

    @FXML
    private void AdminSearch(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene8AdminSearch.fxml");
    }

    @FXML
    private void help(ActionEvent event) throws SQLException {
        String role = currentuseraccounttype();
        if (role != null)
            switch (role) {
                case "employee":
                    helpAlert("Here you can select what you want to do. Your options are\n-\tBook a room\n-\tSee your bookings and delete one if you don't need it anymore\n-\tChange your settings, like your password, mail or change between Darkmode and Lightmode");
                    break;
                case "administrator":
                    helpAlert("Here you can select what you want to do. Your options are\n-\tBook a room\n-\tSee your bookings and delete one if you don't need it anymore\n-\tAdd or edit rooms\n-\tAdd, edit or delete a user\n-\tsearch and delete room bookings from any one user or for any one room\n-\tView the Server logs\n-\tChange your settings, like your password, mail or change between Darkmode and Lightmode");
                    break;
                case "manager":
                    helpAlert("Here you can select what you want to do. Your options are\n-\tBook a room\n-\tSee your bookings and delete one if you don't need it anymore\n-\tAdd or edit rooms\n-\tChange your settings, like your password, mail or change between Darkmode and Lightmode");
                    break;
                default:
                    helpAlert("idk");
                    break;

            }
    }
}
