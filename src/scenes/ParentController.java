package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import sample.DB_Connector;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ParentController {

    public SceneChanger sceneChanger = new SceneChanger();
    public DB_Connector connector = new DB_Connector();


    @FXML
    private void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Logout");
        alert.setHeaderText("This will log");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sceneChanger.SceneChange(event, "Scene1Login.fxml");
        }
    }

    @FXML
    private void RoomAdmin(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene3roomdashboard.fxml");
    }

    @FXML
    private void BackToDashboard (ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene2Dashboard.fxml");
    }


    public String currentusermail() {
        String s = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("user.credit"));
            s = br.readLine();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    public String currentuseraccounttype() throws SQLException {
        ResultSet rs = connector.select( "SELECT `account_type` FROM `pc2fma2`.`account` where email = '" + currentusermail() + "'");
        rs.next();
        return rs.getString(1);
    }

    public void setDarkthemeFileWrite(String s) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("dark.mode"));
        pw.print(s);
        pw.close();
    }

    public void helpAlert(String a)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setContentText(a);
        alert.showAndWait();
    }
}
