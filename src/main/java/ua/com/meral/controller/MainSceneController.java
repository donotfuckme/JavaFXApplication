package ua.com.meral.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import ua.com.meral.util.Localization;

import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

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

    private Localization localization;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        localization = new Localization(resources);

        dataGridPane = new GridPane();


    }
}
