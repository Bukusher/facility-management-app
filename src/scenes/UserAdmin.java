package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.DB_Connector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserAdmin {

    @FXML
    private TextField TFsearchMail;
    @FXML
    private TextField TFnewMail;
    @FXML
    private TextField TFnewPassword;
    @FXML
    private TextField TFfirstName;
    @FXML
    private TextField TFsurname;
    @FXML
    private ChoiceBox roleBox;

    private ObservableList<String> rolesList = FXCollections.observableArrayList("administrator", "manager", "employee");

    private SceneChanger sceneChange = new SceneChanger();
    private DB_Connector connector = new DB_Connector();
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    public void initialize() {
        roleBox.setItems(rolesList);
    }

    @FXML
    public void fetchUser(ActionEvent event) throws IOException {
        try {
            String fetchUserQuery = "SELECT * FROM `pc2fma2`.`account` where email = '" + TFsearchMail.getText() + "'";
            String rsEmail = "";
            String rsName = "";
            String rsSurname = "";
            String rsPassword = "";
            String rsAccount = "";
            ResultSet resultSet = connector.select(fetchUserQuery);
            if (resultSet.next()) {
                rsEmail = resultSet.getString(1);
                rsName = resultSet.getString(2);
                rsSurname = resultSet.getString(3);
                rsPassword = resultSet.getString(4);
                rsAccount = resultSet.getString(5);
            }
            TFfirstName.setText(rsName);
            TFsurname.setText(rsSurname);
            TFnewMail.setText(rsEmail);
            TFnewPassword.setText(rsPassword);
            roleBox.setValue(rsAccount);
        } catch (SQLException ex) {
            System.err.println(new java.util.Date() + " : " + ex.getMessage());
        }
    }

    @FXML
    public void DeleteUser(ActionEvent event) throws IOException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Delete Account Confirmation");
        dialog.setHeaderText("Delete Account Confirmation");
        dialog.setContentText("Please reenter the username:");
        Optional<String> result = dialog.showAndWait();
        String confirmationMail = result.get();
        String searchMail = TFnewMail.getText();
        try {
            if (searchMail.equals(confirmationMail)) {
                connector.executeSQL("DELETE FROM account WHERE email = '" + searchMail + "'");
                TFsearchMail.setText("");
                TFfirstName.setText("");
                TFsurname.setText("");
                TFnewMail.setText("");
                TFnewPassword.setText("");
                roleBox.setValue(null);
            } else {
                alert.setTitle("Error");
                alert.setHeaderText("Username did not match!");
                alert.setContentText("Please try again");
                alert.showAndWait();
            }
        } catch (Exception e) {
            System.err.println(new java.util.Date() + " : " + e.getMessage());
        }
    }

    @FXML
    public void AddNewUser(ActionEvent event) throws IOException {
        String newMail = TFnewMail.getText();
        String newPassword = TFnewPassword.getText();
        String firstName = TFfirstName.getText();
        String surname = TFsurname.getText();
        String role = String.valueOf(roleBox.getValue());
        String AddNewUserQuery = "INSERT INTO pc2fma2.account " +
                "(`email`, `name`, `surname`, `password`, `account_type`, `darktheme`) " +
                "VALUES ('" + newMail + "','" + firstName + "','" + surname + "','" + newPassword + "','" + role + "', '0');";
        try {
            connector.executeSQL(AddNewUserQuery);
            TFfirstName.setText("");
            TFsurname.setText("");
            TFnewMail.setText("");
            TFnewPassword.setText("");
            roleBox.setValue(null);
        } catch (Exception e) {
            System.err.println(new java.util.Date() + " : " + e.getMessage());
        }
    }

    public void EditUser(ActionEvent event) throws IOException {
        String searchMail = TFsearchMail.getText();
        String newMail = TFnewMail.getText();
        String newPassword = TFnewPassword.getText();
        String firstName = TFfirstName.getText();
        String surname = TFsurname.getText();
        String role = String.valueOf(roleBox.getValue());
        String editUserQuery = "UPDATE account SET `email` = '" + newMail + "', `name` = '" + firstName + "', `surname` = '" + surname +
                "', `password` = '" + newPassword + "', `account_type` = '" + role + "' WHERE `email` = '" + searchMail + "'";
        try {
            connector.executeSQL(editUserQuery);
            TFsearchMail.setText("");
            TFfirstName.setText("");
            TFsurname.setText("");
            TFnewMail.setText("");
            TFnewPassword.setText("");
            roleBox.setValue(null);
        } catch (Exception e) {
            System.err.println(new java.util.Date() + " : " + e.getMessage());
        }
    }

    @FXML
    public void returnDashboard(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene2Dashboard.fxml");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Logout");
        alert.setHeaderText("This will log you out.");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sceneChange.SceneChange(event, "Scene1Login.fxml");
        }
    }
}
