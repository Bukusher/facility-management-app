package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import sample.CryptoUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Scene4Controller extends ParentController {

    private boolean darkTheme = false;


    @FXML
    private Button BTSettingsHelp;

    @FXML
    private Button BTSettingsBack;

    @FXML
    private Button BTSettingsLogout;

    @FXML
    private TextField TFSettingsNewMail;

    @FXML
    private TextField TFSettingsMail;

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
    private Button BTDeleteAccountBack;

    @FXML
    private TextField TFDeleteAccountPassword;

    @FXML
    private Button BTDeleteAccount;

    @FXML
    private ToggleSwitch TSSettingsDarkTheme;

    private CryptoUtil cryptoUtil = new CryptoUtil();

    public Scene4Controller() throws NoSuchAlgorithmException {
    }


    @FXML
    private void ConfirmChangeMail(ActionEvent event) throws IOException {

        try {
            String oldMail = currentusermail();
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

                    ResultSet DBPassword = connector.simpleSelect("password", "account", "email", oldMail);
                    DBPassword.next();
                    if (cryptoUtil.decrypt(DBPassword.getString(1)).equals(password.get())) {
                        connector.update("account", "email", newMail, "email", oldMail);
                        PrintWriter pw = new PrintWriter(new FileWriter("user.credit"));
                        pw.print(newMail);
                        pw.close();
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
            String mail = currentusermail();
            String oldPassword = TFSettingsOldPassword.getText();
            String newPassword = TFSettingsNewPassword.getText();

            if (!mail.isEmpty() && !oldPassword.isEmpty() && !newPassword.isEmpty()) {
                ResultSet DBMail = connector.simpleSelect("email", "account", "email", mail);
                DBMail.next();
                ResultSet DBPassword = connector.simpleSelect("password", "account", "email", mail);
                DBPassword.next();

                if (DBMail.getString(1).equals(mail) && cryptoUtil.decrypt(DBPassword.getString(1)).equals(oldPassword)) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Confirmation");
                    dialog.setHeaderText("Re-enter your new password to confirm");
                    dialog.setContentText("New Password: ");
                    Optional<String> result = dialog.showAndWait();

                    AtomicReference<String> confirmationPassword = new AtomicReference<>();
                    result.ifPresent(confirmationPassword::set);

                    if (newPassword.equals(confirmationPassword.get())) {
                        if (newPassword.length() > 6) {
                            String encryptNewPassword = cryptoUtil.encrypt(newPassword);
                            connector.update("account", "email", encryptNewPassword, "email", mail);
                            TFSettingsMail.setText("");
                            TFSettingsOldPassword.setText("");
                            TFSettingsNewPassword.setText("");
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Password is shorter than 6 figures");
                            alert.setContentText("Please retype your password.");
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Passwords do not match");
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Email or password not valid!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One or more fields empty");
                alert.setContentText("All fields should be complete before clicking!");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void deleteAccountPopUp(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene4,3deleteaccountpopup.fxml");
    }

    @FXML
    private void deleteAccountBack(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene4settings.fxml");
    }

    @FXML
    private void ConfirmDeleteAccount(ActionEvent event) throws IOException {
        try {
            String email = currentusermail();
            String password = TFDeleteAccountPassword.getText();

            if (!email.isEmpty() && !password.isEmpty()) {

                try {
                    ResultSet DBMail = connector.simpleSelect("email", "account", "email", email);
                    DBMail.next();
                    if (!DBMail.getString(1).isEmpty()) {
                        ResultSet DBPassword = connector.simpleSelect("password", "Account", "email", email);
                        DBPassword.next();
                        if (DBPassword.getString(1).equals(password)) {
                            connector.executeSQL("DELETE FROM `account` WHERE `email` = '" + email + "'");
                            sceneChanger.SceneChange(event, "Scene1Login.fxml");
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Password is not valid!");
                            alert.showAndWait();
                        }
                    }
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Email is not valid!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("One or more fields empty");
                alert.setContentText("All fields should be complete before clicking!");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void DarkTheme(ActionEvent event) throws IOException, SQLException {
        ResultSet rs = connector.select("SELECT * FROM `pc2fma2`.`account` WHERE `email` = '" + currentusermail() + "'");
        rs.next();
        String sqlupdate = "UPDATE`pc2fma2`.`account` SET `darktheme` = '";
        if(!rs.getString(6).equals("1"))
        {
            sqlupdate+="1";
            setDarkthemeFileWrite("ON");
        }

        else {
            sqlupdate += "0";
            setDarkthemeFileWrite("OFF");
        }
        sqlupdate+="' WHERE `email` = '" + currentusermail() + "'";
        connector.executeSQL(sqlupdate);
        sceneChanger.SceneChange(event, "Scene4settings.fxml");

    }

    @FXML
    private void settingsHelp(ActionEvent event) {
        helpAlert("In your settings, you can change your mailadress and or password and also switch between the darkmode and lightmode. If you have no need for your account anymore, you can also delete it here.");
    }
}
