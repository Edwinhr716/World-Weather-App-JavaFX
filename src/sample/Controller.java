package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {

    @FXML
    private TextField latitude;
    @FXML
    private TextField longitude;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public Controller() {
    }


    public void switchToMainUI(ActionEvent event) throws IOException {
        String latitudeString = latitude.getText();
        String longitudeString = longitude.getText();
        API_Connection data = new API_Connection(latitudeString, longitudeString);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainUI.fxml"));
        root = loader.load();

        MainController mainController = loader.getController();

        //root  = FXMLLoader.load(getClass().getResource("mainUI.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        String css = this.getClass().getResource("main.css").toExternalForm();
        scene.getStylesheets().add(css);
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("background.jpg")));
        mainController.setupMain(data, scene);
       // stage.setFullScreen(true);
        stage.show();

        //JSONArray stats = data.getStats();
        //JSONObject HP = stats.getJSONObject(0);
        //String outputText = String.valueOf(HP.getInt("base_stat"));
    }
}
