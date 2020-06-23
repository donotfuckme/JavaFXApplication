package ua.com.meral;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import ua.com.meral.constant.AppConstant;
import ua.com.meral.extractor.PassengerExtractor;
import ua.com.meral.model.Passenger;
import ua.com.meral.util.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ApplicationRunner extends Application {

    public static final Logger LOG = Logger.getLogger(ApplicationRunner.class);

    private static final Function<String[], Passenger> extractor = new PassengerExtractor();

    public static void main(String[] args) {
        List<String[]> data = CSVReader.read("titanic.csv");
        data.remove(0);
        List<Passenger> passengers = new ArrayList<>(data.size());
        data.forEach(strings -> passengers.add(extractor.apply(strings)));
        passengers.forEach(System.out::println);

//        launch(args);
    }

    private void loadOptions() {

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
        LOG.debug("trying to load scene -> " + resourceName);
        FXMLLoader loader = new FXMLLoader();
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
}
