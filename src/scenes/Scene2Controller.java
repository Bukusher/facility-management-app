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

public class Scene2Controller extends ParentController {
    private SceneChanger sceneChange = new SceneChanger();
    private DB_Connector connector = new DB_Connector();
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
            if (sceneChange.isDarkModeOnFileRead())
                fn="src/logo/LogoDarkBackground.png";
            else
                fn="src/logo/LogoWhiteBackground.png";
            IVlogo.setImage(new Image(new FileInputStream(fn)));
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
        //Logo over
        String RoleQuery = "SELECT `account_type` FROM `pc2fma2`.`account` where email = '" + currentusermail() + "'";
        try {
            ResultSet resultSet = connector.select(RoleQuery);
            String role = "employee";
            if (resultSet != null) {
                while (resultSet.next()) {
                    role = resultSet.getString(1);
                }
            }
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
        } catch (Exception e) {
            System.err.println(e);
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
}
