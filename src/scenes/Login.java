package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Administrator;
import sample.DB_Connector;
import sample.Employee;

import java.security.SecureRandom;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Login {
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
                    sceneChanger.SceneChange(event, "Scene2Dashboard.fxml");
                } else {
                    alert.setTitle("Error");
                    alert.setHeaderText("Wrong email or password");
                    alert.setContentText("Please try again");
                    alert.showAndWait();
                }

            }
        } catch (SQLException ex) {
            System.err.println(new java.util.Date() + " : " + ex.getMessage());
            alert.setTitle("Error");
            alert.setHeaderText("Wrong email or password");
            alert.setContentText("Please try again");
            alert.showAndWait();
            System.err.println("Username not registered");
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
        System.out.println(pin);
        return pin;
    }

    @FXML
    public void createAccount(ActionEvent event) throws IOException {
        String name = TFname.getText();
        String surname = TFsurname.getText();
        String mail = TFmailSighUp.getText();
        String password1 = TFpasswordSignUp.getText();
        String password2 = TFpasswordconfirm.getText();
        String pinConfirm = TFpin.getText();
        String pinString = String.format("%06d", pin);
        if (pinString.equals(pinConfirm)) {
            if (password1.equals(password2)) {
                String values = "'" + mail + "', '" + name + "', '" + surname + "', '" + password1 + "', 'employee'";
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

    }
}
