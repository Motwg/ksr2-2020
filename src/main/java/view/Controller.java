package view;

import enumerate.Operator;
import enumerate.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.SimpleFuzzifyWeather;
import model.Weather;
import net.sourceforge.jFuzzyLogic.FIS;
import readers.DataReader;
import readers.FlcReader;
import summaries.LinguisticSummary;
import summaries.Qualifier;
import summaries.Summarizer;
import summaries.multi.FormFour;
import summaries.multi.FormOne;
import summaries.multi.FormTwo;
import summaries.multi.MultiSubjectLinguisticSummary;
import summaries.quantifiers.Absolute;
import summaries.quantifiers.IQuantifier;
import summaries.quantifiers.Relative;
import utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Controller {

    private static final String FIRST_FILENAME = "2016.csv";
    private static final String SECOND_FILENAME = "2017.csv";

    FIS fis;
    List<Weather> weatherData;
    List<SimpleFuzzifyWeather> fWeatherData;

    @FXML
    ToggleGroup quantifierGroup;

    @FXML
    ComboBox qComboBox;

    @FXML
    ComboBox pComboBox;

    @FXML
    ComboBox kComboBox;

    @FXML
    ComboBox s1ComboBox;

    @FXML
    ComboBox s2ComboBox;

    @FXML
    ComboBox s3ComboBox;

    @FXML
    TextField t1Entry;

    @FXML
    TextField t2Entry;

    @FXML
    TextField t3Entry;

    @FXML
    TextField t4Entry;

    @FXML
    TextField t5Entry;

    @FXML
    TextField t6Entry;

    @FXML
    TextField t7Entry;

    @FXML
    TextField t8Entry;

    @FXML
    TextField t9Entry;

    @FXML
    TextField t10Entry;

    @FXML
    TextField t11Entry;

    @FXML
    ToggleGroup quantifierMultiGroup;

    @FXML
    ComboBox qMultiComboBox;

    @FXML
    ComboBox p1MultiComboBox;

    @FXML
    ComboBox p2MultiComboBox;

    @FXML
    ComboBox kMultiComboBox;

    @FXML
    ComboBox s1MultiComboBox;

    @FXML
    ComboBox s2MultiComboBox;

    @FXML
    ComboBox s3MultiComboBox;

    @FXML
    TextField tMultiEntry;

    @FXML
    public void initialize() {
        //Dane:
        setData();

        //Jednopodmiotowe:
        setQComboBoxWithId(((RadioButton) quantifierGroup.getSelectedToggle()).getId(), false);
        setPComboBox();
        setKComboBox();
        setS1AndS2AndS3ComboBox();

        //Wielopodmiotowe:
        setQComboBoxWithId(((RadioButton) quantifierMultiGroup.getSelectedToggle()).getId(), true);
        setP1AndP2MultiComboBox();
        setKMultiComboBox();
        setS1AndS2AndS3MultiComboBox();
    }

    private void setData() {
        weatherData = DataReader.readWeatherFromCsv(FIRST_FILENAME);
        weatherData.addAll(DataReader.readWeatherFromCsv(SECOND_FILENAME));
        fis = FlcReader.load(Constants.INPUT_FCL_NAME);
        fWeatherData = weatherData.stream()
                .map(w -> w.fuzzify(fis))
                .collect(Collectors.toList());
    }

    @FXML
    public void changeQType(ActionEvent event) {
        event.consume();
        String id =  ((Node) event.getSource()).getId();
        setQComboBoxWithId(id, false);
    }

    @FXML
    public void changeQMultiType(ActionEvent event) {
        event.consume();
        String id =  ((Node) event.getSource()).getId();
        setQComboBoxWithId(id, true);
    }

    private void setQComboBoxWithId(String id, boolean isMulti) {
        List<String> options;
        switch(id) {
            case "qRelative":
            case "qMultiRelative":
                options = new ArrayList<>(fis.getVariable("percentage").getLinguisticTerms().keySet());
                break;
            case "qAbsolute":
            case "qMultiAbsolute":
                options = new ArrayList<>(fis.getVariable("absolute").getLinguisticTerms().keySet());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        if (isMulti) {
            qMultiComboBox.getItems().removeAll(qMultiComboBox.getItems());
            options.forEach(o -> qMultiComboBox.getItems().add(o));
        } else {
            qComboBox.getItems().removeAll(qComboBox.getItems());
            options.forEach(o -> qComboBox.getItems().add(o));
        }
    }

    private void setPComboBox() {
        pComboBox.getItems().removeAll(pComboBox.getItems());
        //pComboBox.getItems().add("Weather measurements");
        List<String> options = Arrays.stream(Season.values()).map(Season::toString).collect(Collectors.toList());
        options.forEach(o -> pComboBox.getItems().add(o));
    }

    private void setP1AndP2MultiComboBox() {
        p1MultiComboBox.getItems().removeAll(p1MultiComboBox.getItems());
        p2MultiComboBox.getItems().removeAll(p2MultiComboBox.getItems());
        List<String> options = Arrays.stream(Season.values()).map(Season::toString).collect(Collectors.toList());
        options.forEach(o -> {
            p1MultiComboBox.getItems().add(o);
            p2MultiComboBox.getItems().add(o);
        });
    }

    private void setKComboBox() {
        kComboBox.getItems().removeAll(kComboBox.getItems());
        List<String> options = new ArrayList<>(fis.getVariable("day_time").getLinguisticTerms().keySet());
        options.forEach(o -> kComboBox.getItems().add(o));
    }

    private void setKMultiComboBox() {
        kMultiComboBox.getItems().removeAll(kMultiComboBox.getItems());
        List<String> options = new ArrayList<>(fis.getVariable("day_time").getLinguisticTerms().keySet());
        options.forEach(o -> kMultiComboBox.getItems().add(o));
    }

    private void setS1AndS2AndS3ComboBox() {
        s1ComboBox.getItems().removeAll(s1ComboBox.getItems());
        s2ComboBox.getItems().removeAll(s2ComboBox.getItems());
        s3ComboBox.getItems().removeAll(s3ComboBox.getItems());

        List<String> options = new ArrayList<>();
        options.addAll(fis.getVariable("cloudiness").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("dampness").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("wind_velocity").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("precipitation_six").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("snow").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("pressure_station").getLinguisticTerms().keySet());
        //options.addAll(fis.getVariable("pressure_sea").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("temperature_winter").getLinguisticTerms().keySet());
        //options.addAll(fis.getVariable("temperature_wet_winter").getLinguisticTerms().keySet());

        options.forEach(o -> {
            s1ComboBox.getItems().add(o);
            s2ComboBox.getItems().add(o);
            s3ComboBox.getItems().add(o);
        });
    }

    private void setS1AndS2AndS3MultiComboBox() {
        s1MultiComboBox.getItems().removeAll(s1MultiComboBox.getItems());
        s2MultiComboBox.getItems().removeAll(s2MultiComboBox.getItems());
        s3MultiComboBox.getItems().removeAll(s3MultiComboBox.getItems());

        List<String> options = new ArrayList<>();
        options.addAll(fis.getVariable("cloudiness").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("dampness").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("wind_velocity").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("precipitation_six").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("snow").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("pressure_station").getLinguisticTerms().keySet());
        //options.addAll(fis.getVariable("pressure_sea").getLinguisticTerms().keySet());
        options.addAll(fis.getVariable("temperature_winter").getLinguisticTerms().keySet());
        //options.addAll(fis.getVariable("temperature_wet_winter").getLinguisticTerms().keySet());

        options.forEach(o -> {
            s1MultiComboBox.getItems().add(o);
            s2MultiComboBox.getItems().add(o);
            s3MultiComboBox.getItems().add(o);
        });
    }

    private void drawAlertWindow() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Niepełna konfiguracja");
        alert.setContentText("Nie uzupełniono wszystkich wymaganych pól!!!");
        alert.showAndWait();
    }

    @FXML
    public void generateSummary() {
        if (qComboBox.getSelectionModel().isEmpty() || pComboBox.getSelectionModel().isEmpty()
                || kComboBox.getSelectionModel().isEmpty() || s1ComboBox.getSelectionModel().isEmpty()) {
            drawAlertWindow();
        } else {
            List<String> summarizerList = new ArrayList<>();
            summarizerList.add(s1ComboBox.getSelectionModel().getSelectedItem().toString());
            if (!s2ComboBox.getSelectionModel().isEmpty()) {
                summarizerList.add(s2ComboBox.getSelectionModel().getSelectedItem().toString());
            }
            if (!s3ComboBox.getSelectionModel().isEmpty()) {
                summarizerList.add(s3ComboBox.getSelectionModel().getSelectedItem().toString());
            }
            Summarizer summarizer = new Summarizer(summarizerList, Operator.and);

            Qualifier qualifier = new Qualifier(kComboBox.getSelectionModel().getSelectedItem().toString());

            IQuantifier quantifier;
            String quantifierTerm = qComboBox.getSelectionModel().getSelectedItem().toString();
            switch (((RadioButton)quantifierGroup.getSelectedToggle()).getId()) {
                case ("qRelative"):
                    quantifier = new Relative(fis, quantifierTerm);
                    break;
                case ("qAbsolute"):
                    quantifier = new Absolute(fis, quantifierTerm);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + ((RadioButton) quantifierGroup.getSelectedToggle()).getId());
            };

            LinguisticSummary linguisticSummary = LinguisticSummary.builder()
                    .weatherList(fWeatherData)
                    .summarizer(summarizer)
                    .qualifier(qualifier)
                    .quantifier(quantifier)
                    .fis(fis)
                    .build();

            setFromT1ToT11(linguisticSummary);
        }
    }

    @FXML
    public void generateMultiSummary() {
        if (p1MultiComboBox.getSelectionModel().isEmpty() || p2MultiComboBox.getSelectionModel().isEmpty()
                || s1MultiComboBox.getSelectionModel().isEmpty()) {
            drawAlertWindow();
        } else {
            List<String> summarizerList = new ArrayList<>();
            summarizerList.add(s1MultiComboBox.getSelectionModel().getSelectedItem().toString());
            if (!s2MultiComboBox.getSelectionModel().isEmpty()) {
                summarizerList.add(s2MultiComboBox.getSelectionModel().getSelectedItem().toString());
            }
            if (!s3MultiComboBox.getSelectionModel().isEmpty()) {
                summarizerList.add(s3MultiComboBox.getSelectionModel().getSelectedItem().toString());
            }
            Summarizer summarizer = new Summarizer(summarizerList, Operator.and);

            Qualifier qualifier = null;
            if (!kMultiComboBox.getSelectionModel().isEmpty()) {
                qualifier = new Qualifier(kMultiComboBox.getSelectionModel().getSelectedItem().toString());
            }

            Relative quantifier = null;
            if (!qMultiComboBox.getSelectionModel().isEmpty()) {
                String quantifierTerm = qMultiComboBox.getSelectionModel().getSelectedItem().toString();
                quantifier = new Relative(fis, quantifierTerm);
            }
            MultiSubjectLinguisticSummary multiSubjectLinguisticSummary;
            Season season1 = Season.valueOf(p1MultiComboBox.getSelectionModel().getSelectedItem().toString());
            Season season2 = Season.valueOf(p2MultiComboBox.getSelectionModel().getSelectedItem().toString());
            if (qualifier == null) {
                if (quantifier == null) {
                    multiSubjectLinguisticSummary = new FormFour(season1, season2, fWeatherData, summarizer, fis);
                } else {
                    multiSubjectLinguisticSummary = new FormOne(season1, season2, fWeatherData, summarizer, quantifier, fis);
                }
            } else {
                multiSubjectLinguisticSummary = new FormTwo(season1, season2, fWeatherData, summarizer, quantifier, qualifier, fis);
            }
            setMultiT(multiSubjectLinguisticSummary);
        }
    }

    private void setFromT1ToT11(LinguisticSummary linguisticSummary) {
        t1Entry.setText(String.valueOf(linguisticSummary.t1()));
        t2Entry.setText(String.valueOf(linguisticSummary.t2()));
        t3Entry.setText(String.valueOf(linguisticSummary.t3()));
        t4Entry.setText(String.valueOf(linguisticSummary.t4()));
        t5Entry.setText(String.valueOf(linguisticSummary.t5()));
        t6Entry.setText(String.valueOf(linguisticSummary.t6()));
        t7Entry.setText(String.valueOf(linguisticSummary.t7()));
        t8Entry.setText(String.valueOf(linguisticSummary.t8()));
        t9Entry.setText(String.valueOf(linguisticSummary.t9()));
        t10Entry.setText(String.valueOf(linguisticSummary.t10()));
        t11Entry.setText(String.valueOf(linguisticSummary.t11()));
    }

    private void setMultiT(MultiSubjectLinguisticSummary multiSubjectLinguisticSummary) {
        tMultiEntry.setText(String.valueOf(multiSubjectLinguisticSummary.t()));
    }
}
