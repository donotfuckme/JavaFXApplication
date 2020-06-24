package ua.com.meral.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.apache.log4j.Logger;
import ua.com.meral.extractor.PassengerExtractor;
import ua.com.meral.model.Passenger;
import ua.com.meral.util.CSVReader;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainSceneController implements Initializable {

    private static final Logger LOG = Logger.getLogger(MainSceneController.class);

    private static final Function<String[], Passenger> extractor = new PassengerExtractor();

    @FXML
    public Tab dataTab;

    @FXML
    public ComboBox<String> langComboBox;

    @FXML
    public ScrollPane scrollPane;

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
        List<Passenger> passengers = data.stream().skip(1).map(extractor).collect(Collectors.toList());

        int numRows = data.size();
        int numColumns = data.get(1).length;

        for (int row = 0; row < numRows; row++) {
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            dataGridPane.getRowConstraints().add(rc);
        }

        for (int col = 0; col < numColumns; col++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            dataGridPane.getColumnConstraints().add(cc);
        }

        String[] labelNames = data.get(0);
        for (int i = 0; i < labelNames.length; i++) {
            Label label = new Label(labelNames[i]);
            dataGridPane.add(label, i, 0);
        }

        int rowCounter = 1;
        for (Passenger passenger : passengers) {
            int colCounter = 0;
            Label labelID = new Label(String.valueOf(passenger.getId()));
            Label labelSurvived = new Label(String.valueOf(passenger.isSurvived()));
            Label labelPClass = new Label(String.valueOf(passenger.getPClass()));
            Label labelName = new Label(String.valueOf(passenger.getName()));
            Label labelSex = new Label(String.valueOf(passenger.getSex()));
            Label labelAge = new Label(String.valueOf(passenger.getAge()));
            Label labelSibSp = new Label(String.valueOf(passenger.getSibSp()));
            Label labelParch = new Label(String.valueOf(passenger.getParch()));
            Label labelTicket = new Label(String.valueOf(passenger.getTicket()));
            Label labelFare = new Label(String.valueOf(passenger.getFare()));
            Label labelCabin = new Label(String.valueOf(passenger.getCabin()));
            Label labelEmbarked = new Label(String.valueOf(passenger.getEmbarked()));

            dataGridPane.add(labelID, colCounter++, rowCounter);
            dataGridPane.add(labelSurvived, colCounter++, rowCounter);
            dataGridPane.add(labelPClass, colCounter++, rowCounter);
            dataGridPane.add(labelName, colCounter++, rowCounter);
            dataGridPane.add(labelSex, colCounter++, rowCounter);
            dataGridPane.add(labelAge, colCounter++, rowCounter);
            dataGridPane.add(labelSibSp, colCounter++, rowCounter);
            dataGridPane.add(labelParch, colCounter++, rowCounter);
            dataGridPane.add(labelTicket, colCounter++, rowCounter);
            dataGridPane.add(labelFare, colCounter++, rowCounter);
            dataGridPane.add(labelCabin, colCounter++, rowCounter);
            dataGridPane.add(labelEmbarked, colCounter, rowCounter++);
        }
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
