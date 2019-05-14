package scenes;

import javafx.css.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
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
    private ToggleSwitch TSSettingsDarkTheme;



    @FXML
    private void settingsBackToDashboard(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene2Dashboard.fxml");
    }

    public void settingsLogout(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene1Login.fxml");
    }

    @FXML
    private void ConfirmChangeMail(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene4,2mailpopup.fxml");
    }

    @FXML
    private void ConfirmNewPassword(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene4,1passowrdpopup.fxml");
    }

    @FXML
    private void ConfirmDeleteAccount(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene4,3deleteaccountpopup.fxml");
    }


    public void darktheme(ActionEvent event) throws IOException {

        if (TSSettingsDarkTheme.isPressed()){
            darkTheme = true;
        } else darkTheme = false;

        if (darkTheme) {

        }
    }

}
