package scenes;

import javafx.css.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.IOException;

public class Settings {

    private boolean darkTheme = false;


    @FXML
    private Button BThelp;

    @FXML
    private Button BTback;

    @FXML
    private Button BTlogout;

    @FXML
    private TextField TFchangemail;

    @FXML
    private TextField TFChangepassword;

    @FXML
    private Button BTchangemail;

    @FXML
    private Button BTchangepassword;

    @FXML
    private Button BTdeleteaccount;

    @FXML
    private ToggleSwitch TSdarktheme;



    public void darktheme(ActionEvent actionEvent) throws IOException {

        if (TSdarktheme.isPressed()){
            darkTheme = true;
        } else darkTheme = false;

        if (darkTheme) {


        }
    }

}
