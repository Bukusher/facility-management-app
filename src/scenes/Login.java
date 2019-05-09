package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.*;

import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;


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

    EmployeeHashMap users = new EmployeeHashMap();

    @FXML
    private void login(ActionEvent event) {

        Administrator admin = new Administrator("Admin", "Admin", "Admin", "Admin");
        users.putEmployee("Admin", admin);

        //ResultSet resultSet = connector.select("SELECT password FROM user WHERE (email = " + email + ")");

        try {
            String email = TFemail.getText();
            String inputPassword = TFpassword.getText();
            String password = users.getEmployee(email).getPassword();

            if (inputPassword.equals(password)) {
                Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene2Dashboard.fxml"));
                Scene homePageScene = new Scene(homePageParent);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(homePageScene);
                appStage.show();
            } else {
                alert.setTitle("Error");
                alert.setHeaderText("Wrong email or password");
                alert.setContentText("Please try again");
                alert.showAndWait();
            }
        } catch (Exception bruh) {
            alert.setTitle("Error");
            alert.setHeaderText("Wrong email or password");
            alert.setContentText("Please try again");
            alert.showAndWait();
            System.err.println("Username not registered");
        }




    }

    @FXML
    private void sighUp(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene1,1Signuppopup.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene1Login.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    public int randomizePin() {
        pin = rand.nextInt(999999);
        System.out.println(pin);
        return pin;
    }

    @FXML
    public void createAccount(ActionEvent event) throws IOException {
        String name = TFname.getText();
        String surname = TFsurname.getText();
        String email = TFmailSighUp.getText();
        String password1 = TFpasswordSignUp.getText();
        String password2 = TFpasswordconfirm.getText();
        String pinConfirm = TFpin.getText();
        String pinString = String.format("%06d", pin);
        if (pinString.equals(pinConfirm)) {
            if (password1.equals(password2)) {
                Employee employee = new Employee(name, surname, email, password1);
                /*
                String values = "'" + mail + "', '" + name + "', '" + surname + "', '" + password1 + "', 'employee'";
                System.out.println(values);
                connector.executeSQL("INSERT INTO `pc2fma2`.`user` " +
                        "(`email`,`name`,`surname`,`password`,`account-type`) " +
                        "VALUES ('" + mail + "','" + name + "','" + surname + "','" + password1 + "','employee');");
                */
                //Employee employee = new Employee(name, surname, mail, password1);
                users.putEmployee(email, employee);
                Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene1Login.fxml"));
                Scene homePageScene = new Scene(homePageParent);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setScene(homePageScene);
                appStage.show();
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
