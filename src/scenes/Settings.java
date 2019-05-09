package scenes;

import javafx.css.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.IOException;

public class Settings {

    private boolean darkTheme = false;


    @FXML
    private Button BTSettingsHelp;

    @FXML
    private Button BTSettingsBack;

    @FXML
    private Button BTSettingsLogout;

    @FXML
    private TextField TFSettingsChangemail;

    @FXML
    private TextField TFSettingsChangepassword;

    @FXML
    private Button BTSettingsChangemail;

    @FXML
    private Button BTSettingsChangepassword;

    @FXML
    private Button BTSettingsDeleteaccount;

    @FXML
    private CheckMenuItem CMISettingsDarkTheme;



    public void darktheme(ActionEvent event) throws IOException {

        if (CMISettingsDarkTheme.isSelected()){
            darkTheme = true;
        } else darkTheme = false;

        if (darkTheme) {
            Parent Psettings = FXMLLoader.load(getClass().getResource("Scene4settings.fxml"));
            Scene Ssettings = new Scene(Psettings);
            Ssettings.getStylesheets().add("DarkTheme.css");
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(Ssettings);
            appStage.show();
        } else {
            Parent Psettings = FXMLLoader.load(getClass().getResource("Scene4settings.fxml"));
            Scene Ssettings = new Scene(Psettings);
            Ssettings.getStylesheets().remove("DarkTheme.css");
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(Ssettings);
            appStage.show();
        }
    }

}
