package scenes;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.DB_Connector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;

public class Dashboard extends ChangeScene{
    private SceneChanger sceneChange = new SceneChanger();
    private DB_Connector connector = new DB_Connector();
    @FXML
    ImageView IVlogo;

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
}
