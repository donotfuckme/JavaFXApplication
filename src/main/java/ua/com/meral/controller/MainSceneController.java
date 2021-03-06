package ua.com.meral.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.apache.log4j.Logger;
import ua.com.meral.constant.AppConstant;
import ua.com.meral.extractor.PassengerExtractor;
import ua.com.meral.model.Passenger;
import ua.com.meral.util.CSVReader;
import ua.com.meral.util.CacheUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
    public BarChart<String, Number> barChart;

    @FXML
    public ComboBox<String> fieldComboBoxOnBarGraphTab;

    @FXML
    public Tab graphTab;

    @FXML
    public ComboBox<String> fieldComboBoxOnGraphTab;

    @FXML
    public LineChart<String, Number> lineChart;

    private List<String[]> passengersData;

    private List<Passenger> passengers;

    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;

        passengersData = CSVReader.read("titanic.csv");
        passengers = passengersData.stream().skip(1).map(extractor).collect(Collectors.toList());
        passengers.forEach(System.out::println);

        initView();
        langComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = langComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                resourceBundle = ResourceBundle.getBundle("lang/locale", selectedIndex == 0 ? Locale.ENGLISH : new Locale("ru"));
                initView();
                LOG.debug(String.format("old -> %s new -> %s", oldValue, newValue));
            }
        });

        fieldComboBoxOnBarGraphTab.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = fieldComboBoxOnBarGraphTab.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                initBarChart();
            }
        });

        fieldComboBoxOnGraphTab.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = fieldComboBoxOnGraphTab.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                initLineChart();
            }
        });
    }

    private void initView() {
        initTabs();
        initLangComboBox();
        initDataGridPane();
        initFieldComboBoxOnBarGraphTab();
        initFieldComboBoxOnGraphTab();
    }

    private void initFieldComboBoxOnGraphTab() {
        List<String> comboBoxFields = Arrays.asList(
                resourceBundle.getString("age"),
                resourceBundle.getString("fare")
        );
        ObservableList<String> strings = FXCollections.observableList(comboBoxFields);
        fieldComboBoxOnGraphTab.setItems(strings);
    }

    private void initFieldComboBoxOnBarGraphTab() {
        List<String> comboBoxFields = Arrays.asList(
                resourceBundle.getString("survived"),
                resourceBundle.getString("sex"),
                resourceBundle.getString("pClass"),
                resourceBundle.getString("embarked")
        );
        ObservableList<String> strings = FXCollections.observableList(comboBoxFields);
        fieldComboBoxOnBarGraphTab.setItems(strings);
    }

    private void initBarChart() {
        barChart.getData().clear();
        setBarChartData(barChart);
    }

    private void setBarChartData(BarChart<String, Number> barChart) {
        String selected = fieldComboBoxOnBarGraphTab.getSelectionModel().getSelectedItem();
        String survivedLabel = resourceBundle.getString("survived");
        String deadLabel = resourceBundle.getString("dead");
        String sexLabel = resourceBundle.getString("sex");
        String maleLabel = resourceBundle.getString("male");
        String femaleLabel = resourceBundle.getString("female");
        String pClassLabel = resourceBundle.getString("pClass");
        String embarkedLabel = resourceBundle.getString("embarked");

        if (selected != null) {
            if (selected.equals(survivedLabel)) {
                XYChart.Series<String, Number> seriesOne = new XYChart.Series<>();
                XYChart.Series<String, Number> seriesTwo = new XYChart.Series<>();

                long survived = passengers.stream().filter(Passenger::isSurvived).count();
                seriesOne.setName(survivedLabel);
                seriesOne.getData().add(new XYChart.Data<>(survivedLabel, survived));

                seriesTwo.setName(deadLabel);
                seriesTwo.getData().add(new XYChart.Data<>(survivedLabel, passengers.size() - survived));

                barChart.getData().add(seriesOne);
                barChart.getData().add(seriesTwo);
            } else if (selected.equals(sexLabel)) {
                XYChart.Series<String, Number> seriesOne = new XYChart.Series<>();
                XYChart.Series<String, Number> seriesTwo = new XYChart.Series<>();

                long male = passengers.stream().filter(passenger -> passenger.getSex().equals("male")).count();
                seriesOne.setName(maleLabel);
                seriesOne.getData().add(new XYChart.Data<>(sexLabel, male));

                seriesTwo.setName(femaleLabel);
                seriesTwo.getData().add(new XYChart.Data<>(sexLabel, passengers.size() - male));

                barChart.getData().add(seriesOne);
                barChart.getData().add(seriesTwo);
            } else if (selected.equals(pClassLabel)) {
                XYChart.Series<String, Number> seriesOne = new XYChart.Series<>();
                XYChart.Series<String, Number> seriesTwo = new XYChart.Series<>();
                XYChart.Series<String, Number> seriesThree = new XYChart.Series<>();

                long firstClass = passengers.stream().filter(passenger -> passenger.getPClass() == 1).count();
                long secondClass = passengers.stream().filter(passenger -> passenger.getPClass() == 2).count();
                long thirdClass = passengers.stream().filter(passenger -> passenger.getPClass() == 3).count();

                seriesOne.setName("1");
                seriesOne.getData().add(new XYChart.Data<>(pClassLabel, firstClass));

                seriesTwo.setName("2");
                seriesTwo.getData().add(new XYChart.Data<>(pClassLabel, secondClass));

                seriesThree.setName("3");
                seriesThree.getData().add(new XYChart.Data<>(pClassLabel, thirdClass));

                barChart.getData().add(seriesOne);
                barChart.getData().add(seriesTwo);
                barChart.getData().add(seriesThree);
            } else if (selected.equals(embarkedLabel)) {
                XYChart.Series<String, Number> seriesOne = new XYChart.Series<>();
                XYChart.Series<String, Number> seriesTwo = new XYChart.Series<>();
                XYChart.Series<String, Number> seriesThree = new XYChart.Series<>();

                long qCounter = passengers.stream().filter(passenger -> passenger.getEmbarked().equals("Q")).count();
                long cCounter = passengers.stream().filter(passenger -> passenger.getEmbarked().equals("C")).count();
                long sCounter = passengers.stream().filter(passenger -> passenger.getEmbarked().equals("S")).count();

                seriesOne.setName("Q");
                seriesOne.getData().add(new XYChart.Data<>(embarkedLabel, qCounter));

                seriesTwo.setName("C");
                seriesTwo.getData().add(new XYChart.Data<>(embarkedLabel, cCounter));

                seriesThree.setName("S");
                seriesThree.getData().add(new XYChart.Data<>(embarkedLabel, sCounter));

                barChart.getData().add(seriesOne);
                barChart.getData().add(seriesTwo);
                barChart.getData().add(seriesThree);
            }
        }
    }

    private void initLineChart() {
        lineChart.getData().clear();
        setLineChartData(lineChart);
    }

    @SuppressWarnings("unchecked")
    private void setLineChartData(LineChart<String, Number> lineChart) {
        String selected = fieldComboBoxOnGraphTab.getSelectionModel().getSelectedItem();
        String ageLabel = resourceBundle.getString("age");
        String fareLabel = resourceBundle.getString("fare");

        if (selected != null) {
            if (selected.equals(ageLabel)) {
                Map<Float, Integer> ages = (HashMap<Float, Integer>) CacheUtil.getInstance().getCache().getOrDefault(AppConstant.AGES_CACHE, new HashMap<Float, Integer>());
                if (ages.isEmpty()) {
                    passengers.forEach(passenger -> {
                        float age = passenger.getAge();
                        if (!ages.containsKey(age)) {
                            ages.put(age, 1);
                        }
                        ages.put(age, ages.get(age) + 1);
                    });
                }
                CacheUtil.getInstance().getCache().put(AppConstant.AGES_CACHE, ages);

                ages.forEach((aFloat, integer) -> {
                    XYChart.Series<String, Number> series = new XYChart.Series<>();

                    series.setName(String.valueOf(aFloat));
                    series.getData().add(new XYChart.Data<>(String.valueOf(aFloat), integer));
                    lineChart.getData().add(series);
                });

            } else if (selected.equals(fareLabel)) {
                Map<Float, Integer> fares = (HashMap<Float, Integer>) CacheUtil.getInstance().getCache().getOrDefault(AppConstant.FARES_CACHE, new HashMap<Float, Integer>());
                if (fares.isEmpty()) {
                    passengers.forEach(passenger -> {
                        float fare = passenger.getFare();
                        if (!fares.containsKey(fare)) {
                            fares.put(fare, 1);
                        }
                        fares.put(fare, fares.get(fare) + 1);
                    });
                }
                CacheUtil.getInstance().getCache().put(AppConstant.FARES_CACHE, fares);

                fares.forEach((aFloat, integer) -> {
                    XYChart.Series<String, Number> series = new XYChart.Series<>();

                    series.setName(String.valueOf(aFloat));
                    series.getData().add(new XYChart.Data<>(String.valueOf(aFloat), integer));
                    lineChart.getData().add(series);
                });
            }
        }
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

    private void initDataGridPane() {
        if (dataGridPane.getRowConstraints().size() != passengersData.size()) {
            int numRows = passengersData.size();
            int numColumns = passengersData.get(1).length;

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

            String[] labelNames = passengersData.get(0);
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
    }
}
