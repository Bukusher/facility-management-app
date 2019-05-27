package scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class Scene3Controller extends ParentController {

    @FXML
    private void EditRoom(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene3,1editroom.fxml");
    }
    @FXML
    private void AddRoom(ActionEvent event) throws IOException {
        sceneChanger.SceneChange(event, "Scene3,2addroom.fxml");
    }
    @FXML
    private void help (ActionEvent e)
    {
        helpAlert("Here you can chose to either\n-\tEdit or delete an existing room\n-\tAdd a new room");
    }
}
