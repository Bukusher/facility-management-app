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
import sample.DB_Connector;
import sample.Employee;

import java.security.SecureRandom;

import java.io.IOException;

public class Login {

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

    Alert alert = new Alert(Alert.AlertType.ERROR);
    SecureRandom rand = new SecureRandom();
    DB_Connector connector = new DB_Connector();


    @FXML
    private void login(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene2Dashboard.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
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
        String mail = TFmailSighUp.getText();
        String password1 = TFpasswordSignUp.getText();
        String password2 = TFpasswordconfirm.getText();
        String pinConfirm = TFpin.getText();
        String pinString = String.format("%06d", pin);
        if (pinString.equals(pinConfirm)) {
            if (password1.equals(password2)) {
                new Employee(name, surname, mail, password1);
                //String values = "'" + mail + "', '" + name + "', '" + surname + "', '" + password1 + "', 'employee'";
                //System.out.println(values);
                connector.executeSQL("INSERT INTO `pc2fma2`.`user`\n" +
                        "(`email`,\n" +
                        "`name`,\n" +
                        "`surname`,\n" +
                        "`password`,\n" +
                        "`account-type`)\n" +
                        "VALUES\n" +
                        "('"+mail+"',\n" +
                        "'"+name+"',\n" +
                        "'"+surname+"',\n" +
                        "'"+password1+"',\n" +
                        "'employee');\n" +
                        ";\n");
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
