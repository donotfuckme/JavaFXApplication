package ua.com.meral;

import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationRunner extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
    }
}
