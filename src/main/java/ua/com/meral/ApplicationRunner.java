package ua.com.meral;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.com.meral.constant.AppConstant;

import java.io.InputStream;

public class ApplicationRunner extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX Test Application");
        primaryStage.getIcons().add(getImage(AppConstant.MERAL_IMAGE));
        primaryStage.show();
    }

    private Image getImage(String file) {
        InputStream iconStream = getClass().getResourceAsStream(AppConstant.IMAGES_PATH + file);
        return new Image(iconStream);
    }
}
