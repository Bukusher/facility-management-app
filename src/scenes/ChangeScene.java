package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ChangeScene {



    @FXML
    private void login(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene2Dashboard.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene1Login.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void RoomAdmin(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene3roomdashboard.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void Setting(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene4settings.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void BookingHistory(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene7BookingHistory.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void BookingRoom(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene6BookRoom.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void UserAdmin(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene5UserAdmin.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void Logs(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene9Logs.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void AdminSearch(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene8AdminSearch.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }


    @FXML
    private void EditRoom(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene3,1editroom.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }


    @FXML
    private void AddRoom(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene3,2addroom.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void DeleteRoom(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene3,3deleteroom.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void SignUp(ActionEvent event) throws IOException {

        //This scene is for now as a test set to automatically have the dark theme!

        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene1,1Signuppopup.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        homePageScene.getStylesheets().add("/scenes/DarkTheme.css");
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }



    @FXML
    private void ForgotPassword(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene1,2forgotpasswordpopup.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void DeleteRoomConfirmation(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene3,3,1deleteroomconfirmpoppup.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }
    @FXML
    private void ConfirmDeleteAccount(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene4,3deleteaccountpopup.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void ConfirmChangeMail(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene4,2mailpopup.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void ConfirmNewPassword(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene4,1passowrdpopup.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }

    @FXML
    private void BackToLogin(ActionEvent event) throws IOException {
        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene1Login.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }
}
