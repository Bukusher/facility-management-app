package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.DB_Connector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SceneChanger {
    private DB_Connector connector = new DB_Connector();

    public void SceneChange(ActionEvent event, String file) throws IOException {
        try {
            Parent homePageParent = FXMLLoader.load(getClass().getResource(file));
            Scene homePageScene = new Scene(homePageParent);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //Darkmode
            if (isDarkModeOnFileRead())
                homePageScene.getStylesheets().add("/scenes/DarkTheme.css");
            else
                homePageScene.getStylesheets().add("com/sun/javafx/scene/control/skin/modena/modena.css");
            //Darkmode over

            appStage.setScene(homePageScene);
            appStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isDarkModeOnFileRead() {
        String s = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("dark.mode"));
            s = br.readLine();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (s.equals("ON"))
            return true;
        else
            return false;
    }
}
