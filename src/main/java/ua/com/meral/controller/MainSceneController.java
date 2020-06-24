package ua.com.meral.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;
import ua.com.meral.extractor.PassengerExtractor;
import ua.com.meral.model.Passenger;
import ua.com.meral.util.CSVReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;

public class MainSceneController implements Initializable {

    private static final Logger LOG = Logger.getLogger(MainSceneController.class);

    private static final Function<String[], Passenger> extractor = new PassengerExtractor();

    @FXML
    public Tab dataTab;

    @FXML
    public ComboBox<String> langComboBox;

    @FXML
    public GridPane dataGridPane;

    @FXML
    public Tab barGraphTab;

    @FXML
    public ComboBox<String> fieldComboBoxOnBarGraphTab;

    @FXML
    public Tab graphTab;

    @FXML
    public ComboBox<String> fieldComboBoxOnGraphTab;

    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;

        initView();
        langComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = langComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                resourceBundle = ResourceBundle.getBundle("lang/locale", selectedIndex == 0 ? Locale.ENGLISH : new Locale("ru"));
                initView();
                LOG.debug(String.format("old -> %s new -> %s", oldValue, newValue));
            }
        });

        List<String[]> data = CSVReader.read("titanic.csv");
        data.remove(0);
        List<Passenger> passengers = new ArrayList<>(data.size());
    }

    private void initView() {
        initTabs();
        initLangComboBox();
    }

    private void initTabs() {
        dataTab.setText(resourceBundle.getString("dataTab"));
        barGraphTab.setText(resourceBundle.getString("barGraphTab"));
        graphTab.setText(resourceBundle.getString("graphTab"));
    }

    private void initLangComboBox() {
        List<String> languageList = Arrays.asList(
                resourceBundle.getString("englishLang"),
                resourceBundle.getString("russianLang")
        );
        ObservableList<String> languages = FXCollections.observableList(languageList);
        langComboBox.setItems(languages);
        langComboBox.getSelectionModel().select(resourceBundle.getLocale() == Locale.ENGLISH ? 0 : 1);
    }
}
