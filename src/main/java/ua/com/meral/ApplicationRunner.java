package ua.com.meral;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.com.meral.constant.AppConstant;
import ua.com.meral.controller.MainSceneController;
import ua.com.meral.util.CFGReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ApplicationRunner extends Application {

    public static final Logger LOG = Logger.getLogger(ApplicationRunner.class);

    private ResourceBundle resourceBundle;

    public static void main(String[] args) {
        launch(args);
    }

    private void loadOptions() {
        LOG.debug("loading application configuration");
        Map<String, String> options = CFGReader.read("options.cfg");
        resourceBundle = ResourceBundle.getBundle("lang/locale", getLocale(options.getOrDefault("lang", "english")));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadOptions();
        primaryStage.setTitle(AppConstant.APPLICATION_TITLE);
        primaryStage.getIcons().add(getImage(AppConstant.MERAL_IMAGE));

        primaryStage.setWidth(AppConstant.APP_WIDTH);
        primaryStage.setHeight(AppConstant.APP_HEIGHT);

        primaryStage.setScene(loadScene(AppConstant.FXML.MAIN_SCENE));

        primaryStage.show();
    }

    private Scene loadScene(String resourceName) throws IOException {
        LOG.debug("trying to load scene -> " + resourceName);
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(resourceBundle);
        URL xmlUrl = getClass().getResource(AppConstant.FXML_PATH + resourceName);
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        return new Scene(root);
    }

    private Image getImage(String file) {
        LOG.debug("trying to get image -> " + file);
        InputStream iconStream = getClass().getResourceAsStream(AppConstant.IMAGES_PATH + file);
        return new Image(iconStream);
    }

    private Locale getLocale(String lang) {
        Locale locale = Locale.ENGLISH;
        switch (lang) {
            case "russian":
                locale = new Locale("ru");
                break;
            case "english":
                locale = Locale.ENGLISH;
                break;
        }
        return locale;
    }
}
