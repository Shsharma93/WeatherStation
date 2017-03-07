package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Weather Station");       /* Set the title of the window */
        primaryStage.setScene(new Scene(root, 1100, 700));       /* Set the dimensions of the window */
        root.getStylesheets().add("theme.css");
        primaryStage.setResizable(false);       /* set the window size fixed */
        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);
    }
}
