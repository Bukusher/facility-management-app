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

    SceneChanger sceneChange = new SceneChanger();
    private boolean darkTheme = false;


    @FXML
    private Button BTSettingsHelp;

    @FXML
    private Button BTSettingsBack;

    @FXML
    private Button BTSettingsLogout;

    @FXML
    private TextField TFSettingsChangeMail;

    @FXML
    private TextField TFSettingsChangePassword;

    @FXML
    private Button BTSettingsChangeMail;

    @FXML
    private Button BTSettingsChangePassword;

    @FXML
    private Button BTSettingsDeleteAccount;

    @FXML
    private ToggleButton TBSettingsDarkTheme;



    @FXML
    private void settingsBackToDashboard(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene2Dashboard.fxml");
    }

    public void settingsLogout(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene1Login.fxml");
    }

    @FXML
    private void ConfirmChangeMail(ActionEvent event) throws IOException {
        //to be implemented
    }

    @FXML
    private void ConfirmNewPassword(ActionEvent event) throws IOException {
        //to be implemented
    }

    @FXML
    private void ConfirmDeleteAccount(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene4,3deleteaccountpopup.fxml");
    }


    public void setDarkTheme(ActionEvent event) throws IOException {

        if (TBSettingsDarkTheme.isSelected()){
            darkTheme = true;
        } else darkTheme = false;

        if (darkTheme) {
            Parent homePageParent = FXMLLoader.load(getClass().getResource("scenes/Scene4settings.fxml"));
            Scene scene = new Scene(homePageParent);
            scene.getStylesheets().add("scenes/DarkTheme.css");
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        } else {

        }
    }

}
