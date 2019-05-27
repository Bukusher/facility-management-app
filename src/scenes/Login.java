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


public class Login extends ChangeScene {
    @FXML
    private TextField TFemail;
    @FXML
    private PasswordField TFpassword;
    @FXML
    private TextField TFname;
    @FXML
    private TextField TFsurname;
    @FXML
    private TextField TFmailSighUp;
    @FXML
    private PasswordField TFpasswordSignUp;
    @FXML
    private PasswordField TFpasswordconfirm;
    @FXML
    private TextField TFpin;
    @FXML
    private TextField TFpasswordFogot;
    @FXML
    private TextField TFpasswordFogotRepeat;
    @FXML
    private TextField TFpinForgot;
    @FXML
    private TextField TFemailForgot;

    private int pin;

    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private SecureRandom rand = new SecureRandom();
    private DB_Connector connector = new DB_Connector();
    private SceneChanger sceneChanger = new SceneChanger();
    private CryptoUtil cryptoUtil = new CryptoUtil();

    public Login() throws NoSuchAlgorithmException {
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
    private void resetPassword(ActionEvent event) {
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
                    if (passwordForgot.length() > 6) {
                        String encryptPasswordForget = cryptoUtil.encrypt(passwordForgot);
                        String resetPasswordQuery = "UPDATE account SET `password` = '" + encryptPasswordForget + "' WHERE `email` = '" + emailForgot + "'";
                        connector.executeSQL(resetPasswordQuery);
                        sceneChanger.SceneChange(event, "Scene1Login.fxml");
                    } else {
                        alert.setTitle("Error");
                        alert.setHeaderText("Password is shorter than 6 figures");
                        alert.setContentText("Please retype your password.");
                        alert.showAndWait();
                    }
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
    public void createAccount(ActionEvent event) {
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
                    if (password1.length() > 6) {
                        String encryptPassword = cryptoUtil.encrypt(password1);
                        String createAccountQuery = "INSERT INTO pc2fma2.account " +
                                "(`email`, `name`, `surname`, `password`, `account_type`, `darktheme`) " +
                                "VALUES ('" + mail + "','" + name + "','" + surname + "','" + encryptPassword + "','employee', '0');";
                        connector.executeSQL(createAccountQuery);
                        sceneChanger.SceneChange(event, "Scene1Login.fxml");
                    } else {
                        alert.setTitle("Error");
                        alert.setHeaderText("Password is shorter than 6 figures");
                        alert.setContentText("Please retype your password.");
                        alert.showAndWait();
                    }
                } else {
                    alert.setTitle("Error");
                    alert.setHeaderText("Passwords do not match!");
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
    public void loginHelp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Login Help Screen");
        alert.setContentText("Please enter your username and email into the fields bellow. \n " +
                "If you have not registered you can do that via the Sign Up button. \n " +
                "If you have forgotten you password please reset your password using the forgot password button. \n " +
                "If still unable to login please contact an Admin for the application.");
        alert.showAndWait();
    }

    @FXML
    public void sighUpHelp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Login Help Screen");
        alert.setContentText("Please enter all the fields to create an account. \n" +
                "After you have enter your email, press send pin to receive an activation pin the your email. \n" +
                "If you do not get this email within 5 minutes please reenter your email and press send pin. \n" +
                "Password must be longer than 6 figures. ");
        alert.showAndWait();
    }

    @FXML
    public void forgotPasswordHelp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Login Help Screen");
        alert.setContentText("Please enter all the fields to create an account. \n" +
                "After you have enter your email, press send pin to receive an activation pin the your email. \n" +
                "If you do not get this email within 5 minutes please reenter your email and press send pin. \n" +
                "Password must be longer than 6 figures. ");
        alert.showAndWait();
    }
}