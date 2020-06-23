package ua.com.meral;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.com.meral.constant.AppConstant;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ApplicationRunner extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(AppConstant.APPLICATION_TITLE);
        primaryStage.getIcons().add(getImage(AppConstant.MERAL_IMAGE));

        primaryStage.setWidth(AppConstant.APP_WIDTH);
        primaryStage.setHeight(AppConstant.APP_HEIGHT);

        primaryStage.setScene(loadScene(AppConstant.FXML.MAIN_SCENE));

        primaryStage.show();
    }

    private Scene loadScene(String resourceName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource(AppConstant.FXML_PATH + resourceName);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        return new Scene(root);
    }

    private Image getImage(String file) {
        InputStream iconStream = getClass().getResourceAsStream(AppConstant.IMAGES_PATH + file);
        return new Image(iconStream);
    }
}
