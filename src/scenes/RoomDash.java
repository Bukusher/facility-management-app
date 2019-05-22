package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.util.Optional;

public class RoomDash {
    public SceneChanger sceneChanger = new SceneChanger();

    @FXML
    private void EditRoom(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene3,1editroom.fxml");
    }


    @FXML
    private void AddRoom(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene3,2addroom.fxml");
    }


    @FXML
    private void Dashboard(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene2Dashboard.fxml");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation logout");
        alert.setHeaderText("This action will bring you back to the login page");
        alert.setContentText("Are you ok with this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sceneChanger.SceneChange(event, "Scene1Login.fxml");
        }
    }


}
