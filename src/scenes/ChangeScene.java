package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import sample.DB_Connector;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ChangeScene {

    SceneChanger sceneChange = new SceneChanger();


    @FXML
    private void login(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene2Dashboard.fxml");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene1Login.fxml");
    }

    @FXML
    private void RoomAdmin(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene3roomdashboard.fxml");
    }

    @FXML
    private void Setting(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene4settings.fxml");
    }

    @FXML
    private void BookingHistory(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene7BookingHistory.fxml");
    }

    @FXML
    private void BookingRoom(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene6BookRoom.fxml");
    }

    @FXML
    private void UserAdmin(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene5UserAdmin.fxml");
    }

    @FXML
    private void Logs(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene9Logs.fxml");
    }

    @FXML
    private void AdminSearch(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene8AdminSearch.fxml");
    }

//not implemented yet
    @FXML
    private void ForgotPassword(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene1,2forgotpasswordpopup.fxml");
    }

    @FXML
    private void DeleteRoomConfirmation(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene3,3,1deleteroomconfirmpoppup.fxml");
    }

    @FXML
    private void BackToLogin(ActionEvent event) throws IOException {
        sceneChange.SceneChange(event, "Scene1Login.fxml");
    }

    public String currentusermail() {
        String s = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("user.credit"));
            s = br.readLine();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

 /*   @FXML
    private void SignUp(ActionEvent event) throws IOException {

        //This scene is for now as a test set to automatically have the dark theme!

        Parent homePageParent = FXMLLoader.load(getClass().getResource("Scene1,1Signuppopup.fxml"));
        Scene homePageScene = new Scene(homePageParent);
        homePageScene.getStylesheets().add("/scenes/DarkTheme.css");
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(homePageScene);
        appStage.show();
    }
    */
}
