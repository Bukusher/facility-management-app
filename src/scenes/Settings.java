package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import sample.DB_Connector;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Settings {

    private SceneChanger sceneChange = new SceneChanger();
    DB_Connector connector = new DB_Connector();
    private boolean darkTheme = false;


    @FXML
    private Button BTSettingsHelp;

    @FXML
    private Button BTSettingsBack;

    @FXML
    private Button BTSettingsLogout;

    @FXML
    private TextField TFSettingsOldMail;

    @FXML
    private TextField TFSettingsNewMail;

    @FXML
    private TextField TFSettingsOldPassword;

    @FXML
    private TextField TFSettingsNewPassword;

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

        try {
            String oldMail = TFSettingsOldMail.getText();
            String newMail = TFSettingsNewMail.getText();

            if (!oldMail.isEmpty() && !newMail.isEmpty()) {
                ResultSet DBMail = connector.simpleSelect("email", "account", "email", oldMail);
                DBMail.next();
                if (DBMail.getString(1).equals(oldMail)) {

                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Confirmation");
                    dialog.setHeaderText("Enter your password to confirm");
                    dialog.setContentText("Password:");
                    Optional<String> result = dialog.showAndWait();

                    AtomicReference<String> password = new AtomicReference<>();
                    result.ifPresent(password::set);

                    ResultSet DBPassword = connector.simpleSelect("password","account", "email", oldMail);
                    DBPassword.next();
                    if (DBPassword.getString(1).equals(password.get())){
                        connector.update("account", "email", newMail, "email", oldMail);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Wrong Password");
                        alert.setContentText("Password does not match the email!");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Email information");
                    alert.setContentText("Emails not registered in the database!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Email information");
                alert.setContentText("Both emails should be entered before clicking!");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void ConfirmNewPassword(ActionEvent event) throws IOException {
        try {
            String oldPassword = TFSettingsOldPassword.getText();
            //sql to be implemented
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void ConfirmDeleteAccount(ActionEvent event) throws IOException {
        //to be replaced by alerts
        sceneChange.SceneChange(event, "Scene4,3deleteaccountpopup.fxml");
    }


    public void setDarkTheme(ActionEvent event) throws IOException {

        darkTheme = TBSettingsDarkTheme.isSelected();

        if (darkTheme) {
            Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene4settings.fxml"));
            Scene scene = new Scene(homePageParent);
            scene.getStylesheets().add("DarkTheme.css");
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        } else {
            Parent homePageParent = FXMLLoader.load(getClass().getResource(".../scenes/Scene4settings.fxml"));
            Scene scene = new Scene(homePageParent);
            scene.getStylesheets().remove(".../scenes/DarkTheme.css");
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        }
    }

}
