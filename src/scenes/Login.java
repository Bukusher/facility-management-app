package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import sample.DB_Connector;
import sample.SendEmail;

import java.io.*;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Login extends ChangeScene {
    @FXML
    TextField TFemail;
    @FXML
    PasswordField TFpassword;
    @FXML
    TextField TFname;
    @FXML
    TextField TFsurname;
    @FXML
    TextField TFmailSighUp;
    @FXML
    PasswordField TFpasswordSignUp;
    @FXML
    PasswordField TFpasswordconfirm;
    @FXML
    TextField TFpin;
    @FXML
    TextField TFpasswordFogot;
    @FXML
    TextField TFpasswordFogotRepeat;
    @FXML
    TextField TFpinForgot;
    @FXML
    TextField TFemailForgot;

    private int pin;

    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private SecureRandom rand = new SecureRandom();
    private DB_Connector connector = new DB_Connector();
    private SceneChanger sceneChanger = new SceneChanger();


    @FXML
    private void login(ActionEvent event) throws IOException {
        String email = TFemail.getText();
        String inputPassword = TFpassword.getText();
        String logInQuery = "SELECT `password` FROM `pc2fma2`.`account` where email = '" + email + "'";
        try {
            ResultSet resultSet = connector.select(logInQuery);
            String password;

            if (resultSet != null) {
                do {
                    resultSet.next();
                    password = resultSet.getString(1);
                } while (resultSet.next());
                if (inputPassword.equals(password)) {

                    //Store username in file for use in other scenes
                    PrintWriter pw = new PrintWriter(new FileWriter("user.credit"));
                    pw.print(TFemail.getText());
                    pw.close();

                    //Darkmode
                    try {
                        ResultSet rs = connector.select("SELECT * FROM `pc2fma2`.`account` WHERE `email` = '" + currentusermail() + "'");
                        rs.next();
                        if (rs.getString(6).equals("1"))
                            setDarkthemeFileWrite("ON");
                        else
                            setDarkthemeFileWrite("OFF");
                    } catch (Exception e) {
                        System.err.println(new java.util.Date() + " : " + e);
                    }

                    //change scene
                    sceneChanger.SceneChange(event, "Scene2Dashboard.fxml");

                } else {
                    alert.setTitle("Error");
                    alert.setHeaderText("Wrong email or password");
                    alert.setContentText("Please try again");
                    alert.showAndWait();
                }
            }
        } catch (SQLException ex) {
            System.err.println(new java.util.Date() + " : " + ex);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong email or password");
            alert.setContentText("Please try again");
            alert.showAndWait();
        }
    }

    @FXML
    private void forgotPasswordBTN(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene1,2forgotPassword.fxml");
    }

    @FXML
    private void resetPassword(ActionEvent event) throws IOException {
        try {
            String pinForgot = TFpinForgot.getText();
            String emailForgot = TFemailForgot.getText();
            String passwordForgot = TFpasswordFogot.getText();
            String passwordForgotRepeat = TFpasswordFogotRepeat.getText();
            String pinString = String.format("%06d", pin);
            SendEmail.send(emailForgot, "FMA pin for account creation", "Welcome " + emailForgot + ". \n" + "The pin to reset your password is: " + pinString);
            //checks pin
            if (pinForgot.equals(pinString)) {
                //checks password
                if (passwordForgot.equals(passwordForgotRepeat)) {
                    String resetPasswordQuery = "UPDATE account SET `password` = '" + passwordForgot + "' WHERE `email` = '" + emailForgot + "'";
                    connector.executeSQL(resetPasswordQuery);
                    sceneChanger.SceneChange(event, "Scene1Login.fxml");
                } else {
                    alert.setTitle("Error");
                    alert.setHeaderText("Passwords does not match!");
                    alert.setContentText("Please retype your password.");
                    alert.showAndWait();
                }
            } else {
                alert.setTitle("Error");
                alert.setHeaderText("Pin does not match!");
                alert.setContentText("Please retype the pin you received or press 'send pin' for a new pin.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println(new java.util.Date() + " : " + e);
        }
    }

    @FXML
    private void sighUp(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene1,1Signuppopup.fxml");
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene1Login.fxml");
    }

    @FXML
    public int randomizePin() {
        pin = rand.nextInt(999999 - 100000) + 100000;
        String name = TFname.getText();
        String surname = TFsurname.getText();
        String mail = TFmailSighUp.getText();
        String pinString = String.format("%06d", pin);
        try {
            SendEmail.send(mail, "FMA pin for account creation", "Welcome " + surname + ", " + name + ". \n" + "The pin for your account is: " + pinString);
        } catch (Exception e) {
            System.err.println(new java.util.Date() + " : " + e.getMessage());
        }
        return pin;
    }

    @FXML
    public int randomizePinForget() {
        pin = rand.nextInt(999999 - 100000) + 100000;
        String mail = TFemailForgot.getText();
        String pinString = String.format("%06d", pin);
        try {
            SendEmail.send(mail, "FMA pin for account creation", "Welcome " + mail + ". \n" + "The pin to reset your password is: " + pinString);
        } catch (Exception e) {
            System.err.println(new java.util.Date() + " : " + e.getMessage());
        }
        return pin;
    }

    @FXML
    public void createAccount(ActionEvent event) throws IOException {
        try {
            String name = TFname.getText();
            String surname = TFsurname.getText();
            String mail = TFmailSighUp.getText();
            String password1 = TFpasswordSignUp.getText();
            String password2 = TFpasswordconfirm.getText();
            String pinConfirm = TFpin.getText();
            String pinString = String.format("%06d", pin);
            if (pinString.equals(pinConfirm)) {
                if (password1.equals(password2)) {
                    String createAccountQuery = "INSERT INTO pc2fma2.account " +
                            "(`email`, `name`, `surname`, `password`, `account_type`) " +
                            "VALUES ('" + mail + "','" + name + "','" + surname + "','" + password1 + "','employee');";
                    connector.executeSQL(createAccountQuery);
                    sceneChanger.SceneChange(event, "Scene1Login.fxml");
                } else {
                    alert.setTitle("Error");
                    alert.setHeaderText("Passwords does not match!");
                    alert.setContentText("Please retype your password.");
                    alert.showAndWait();
                }
            } else {
                alert.setTitle("Error");
                alert.setHeaderText("Pin does not match!");
                alert.setContentText("Please retype the pin you received or press 'send pin' for a new pin.");
                alert.showAndWait();
            }

        } catch (Exception e) {
            System.err.println(new java.util.Date() + " : " + e.getMessage());
        }
    }
}