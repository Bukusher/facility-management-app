package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import sample.CryptoUtil;
import sample.DB_Connector;
import sample.SendEmail;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Scene1Controller extends ParentController {
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
    private CryptoUtil cryptoUtil = new CryptoUtil();

    public Scene1Controller() throws NoSuchAlgorithmException {
    }


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
                String decryptPassword = cryptoUtil.decrypt(password);
                if (inputPassword.equals(decryptPassword)) {

                    //Store username in file for use in other scenes
                    PrintWriter pw = new PrintWriter(new FileWriter("user.credit"));
                    pw.print(TFemail.getText());
                    pw.close();


                    //Darkmode
                    try {
                        ResultSet rs = connector.select("SELECT `darktheme` FROM `pc2fma2`.`account` WHERE `email` = '" + currentusermail() + "'");
                        rs.next();
                        if (rs.getString(1).equals("1"))
                            setDarkthemeFileWrite("ON");
                        else
                            setDarkthemeFileWrite("OFF");
                    } catch (SQLException e) {
                        e.getMessage();
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
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            System.err.println(new java.util.Date() + " : " + ex.getMessage());
            alert.setTitle("Error");
            alert.setHeaderText("Wrong email or password");
            alert.setContentText("Please try again or contact the Admin if error keeps happening");
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
                    String encryptPasswordForget = cryptoUtil.encrypt(passwordForgot);
                    String resetPasswordQuery = "UPDATE account SET `password` = '" + encryptPasswordForget + "' WHERE `email` = '" + emailForgot + "'";
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
            e.printStackTrace();
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("The email has sent!");
            alert.setContentText("press ok to return to last page");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Pin not sent!");
            alert.setContentText("Please double check your email or contact the admin");

            alert.showAndWait();
            e.printStackTrace();
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("The email has sent!");
            alert.setContentText("press ok to return to last page");

            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Pin not sent!");
            alert.setContentText("Please double check your email or contact the admin");

            alert.showAndWait();
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
                    String encryptPassword = cryptoUtil.encrypt(password1);
                    String createAccountQuery = "INSERT INTO pc2fma2.account " +
                            "(`email`, `name`, `surname`, `password`, `account_type`, `darktheme`) " +
                            "VALUES ('" + mail + "','" + name + "','" + surname + "','" + encryptPassword + "','employee', '0');";
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
            e.printStackTrace();
        }
    }
}