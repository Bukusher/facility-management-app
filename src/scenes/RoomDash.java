package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class RoomDash {
SceneChanger sceneChanger=new SceneChanger();

    @FXML
    private void EditRoom(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene3,1editroom.fxml");
    }


    @FXML
    private void AddRoom(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene3,2addroom.fxml");
    }

    @FXML
    private void DeleteRoom(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene3,3deleteroom.fxml");
    }

    @FXML
    private void Dashboard(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene2Dashboard.fxml");
    }



}
