package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //Group root = new Group();
        primaryStage.setTitle("World Weather Viewer");
        primaryStage.setScene(new Scene(root, Color.GREEN));
        //primaryStage.setFullScreen(true);

        primaryStage.show();

    }

    public static void main(String[] args)  {
        launch(args);
    }

}
