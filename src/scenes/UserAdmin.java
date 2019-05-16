package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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

    @FXML
    public void initialize() {
        roleBox.setItems(rolesList);
    }

    @FXML
    public void fetchUser(ActionEvent event) throws IOException {
        String fetchUserQuery = "SELECT * FROM `pc2fma2`.`account` where email = '" + TFsearchMail.getText() + "'";
        String rsEmail = "";
        String rsName = "";
        String rsSurname = "";
        String rsPassword = "";
        String rsAccount = "";
        try {
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
        if (searchMail.equals(confirmationMail)) {
            connector.executeSQL("DELETE FROM account WHERE email = '" + searchMail + "'");
            TFfirstName.setText("");
            TFsurname.setText("");
            TFnewMail.setText("");
            TFnewPassword.setText("");
            roleBox.setValue("");
        }
    }

    @FXML
    public void AddNewUser(ActionEvent event) throws IOException {
        String newMail = TFnewMail.getText();
        String newPassword = TFnewPassword.getText();
        String firstName = TFfirstName.getText();
        String surname = TFsurname.getText();
        String role = String.valueOf(roleBox.getValue());

        String values = "'" + newMail + "', '" + firstName + "', '" + surname + "', '" + newPassword + "', 'employee'";
        System.out.println(values);
        String AddNewUserQuery = "INSERT INTO pc2fma2.account " +
                "(`email`, `name`, `surname`, `password`, `account_type`) " +
                "VALUES ('" + newMail + "','" + firstName + "','" + surname + "','" + newPassword + "','employee');";
        System.out.println(AddNewUserQuery);
        connector.executeSQL(AddNewUserQuery);
        TFfirstName.setText("");
        TFsurname.setText("");
        TFnewMail.setText("");
        TFnewPassword.setText("");
        roleBox.setValue("");

    }

    public void EditUser(ActionEvent event) throws IOException {
        String searchMail = TFsearchMail.getText();
        String newMail = TFnewMail.getText();
        String newPassword = TFnewPassword.getText();
        String firstName = TFfirstName.getText();
        String surname = TFsurname.getText();
        String role = String.valueOf(roleBox.getValue());

        String columns = "`email`, `name`, `surname`, `password`, `account_type`";
        String editUserQuery = "UPDATE account SET `email` = '" + newMail + "', `name` = '" + firstName + "', `surname` = '" + surname +
                "', `password` = '" + newPassword + "', `account_type` = '" + role + "' WHERE `email` = '" + searchMail + "'";
        System.out.println(editUserQuery);
        connector.executeSQL(editUserQuery);
    }

    @FXML
    public void returnDashboard(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene2Dashboard.fxml");
    }

    public void logout(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene1Login.fxml");
    }
}
