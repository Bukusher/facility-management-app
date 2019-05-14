package scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.DB_Connector;

import java.io.IOException;

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



    }

    @FXML
    public void AddNewUser(ActionEvent event) throws IOException {
        String newMail = TFnewMail.getText();
        String newPassword = TFnewPassword.getText();
        String firstName = TFfirstName.getText();
        String surname = TFsurname.getText();
        String role = String.valueOf(roleBox.getValue());

        String columns = "email, name, surname, password, account-type";
        String values = "'" + newMail + "', '" + firstName + "', '" + surname + "', '" + newPassword + "', '" + role +"'";
        connector.insert("user", columns, values);
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

        String columns = "'email', 'name', 'surname', 'password', 'account-type'";
        String values = "'" + newMail + "', '" + firstName + "', '" + surname + "', '" + newPassword + "', '" + role +"'";
        connector.updateWhere("user", columns, values, searchMail);
    }

    @FXML
    public void returnDashboard(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene2Dashboard.fxml");
    }

    public void logout(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene1Login.fxml");
    }
}
