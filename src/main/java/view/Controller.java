package view;

import enumerate.Operator;
import enumerate.Season;
import exception.QuantifierException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import model.SimpleFuzzifyWeather;
import model.Weather;
import net.sourceforge.jFuzzyLogic.FIS;
import readers.DataReader;
import readers.FlcReader;
import summaries.LinguisticSummary;
import summaries.Qualifier;
import summaries.Summarizer;
import summaries.multi.*;
import summaries.quantifiers.Absolute;
import summaries.quantifiers.IQuantifier;
import summaries.quantifiers.Relative;
import utils.Constants;

import java.util.*;
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
    ToggleGroup formMultiTypeGroup;

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
            qMultiComboBox.getItems().add("");
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
        pComboBox.getItems().add("");
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
        Set<String> options = getAllTerms();
        kComboBox.getItems().add("");
        options.forEach(o -> kComboBox.getItems().add(o));
    }

    private void setKMultiComboBox() {
        kMultiComboBox.getItems().removeAll(kMultiComboBox.getItems());
        Set<String> options = getAllTerms();
        kMultiComboBox.getItems().add("");
        options.forEach(o -> kMultiComboBox.getItems().add(o));
    }

    private Set<String> getAllTerms() {
        Set<String> allTerms = new HashSet<>();
        Constants.WEATHER_VAR_NAMES.forEach(s -> {
            String term = s;
            if (s.contains("temperature")) {
                term += "_winter";
            }
            allTerms.addAll(fis.getVariable(term).getLinguisticTerms().keySet());});
        return allTerms;
    }

    private void setS1AndS2AndS3ComboBox() {
        s1ComboBox.getItems().removeAll(s1ComboBox.getItems());
        s2ComboBox.getItems().removeAll(s2ComboBox.getItems());
        s3ComboBox.getItems().removeAll(s3ComboBox.getItems());
        Set<String> options = getAllTerms();
        s1ComboBox.getItems().add("");
        s2ComboBox.getItems().add("");
        s3ComboBox.getItems().add("");
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
        Set<String> options = getAllTerms();
        s1MultiComboBox.getItems().add("");
        s2MultiComboBox.getItems().add("");
        s3MultiComboBox.getItems().add(" ");
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
        alert.setContentText("Nie uzupełniono wszystkich wymaganych pól!");
        alert.showAndWait();
    }

    private void drawAlertWindow(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Niepoprawna konfiguracja");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    @FXML
    public void generateSummary() {

        if (qComboBox.getSelectionModel().isEmpty()
                || s1ComboBox.getSelectionModel().isEmpty() || s1ComboBox.getSelectionModel().getSelectedItem().equals("")) {
            drawAlertWindow();
        } else {
            List<String> summarizerList = new ArrayList<>();
            summarizerList.add(s1ComboBox.getSelectionModel().getSelectedItem().toString());
            if (!s2ComboBox.getSelectionModel().isEmpty() && !s2ComboBox.getSelectionModel().getSelectedItem().equals("")) {
                summarizerList.add(s2ComboBox.getSelectionModel().getSelectedItem().toString());
            }
            if (!s3ComboBox.getSelectionModel().isEmpty() && !s3ComboBox.getSelectionModel().getSelectedItem().equals("")) {
                summarizerList.add(s3ComboBox.getSelectionModel().getSelectedItem().toString());
            }
            Summarizer summarizer = new Summarizer(summarizerList, Operator.and);
            Qualifier qualifier = null;
            if (!kComboBox.getSelectionModel().isEmpty() && !kComboBox.getSelectionModel().getSelectedItem().equals("")) {
                qualifier = new Qualifier(kComboBox.getSelectionModel().getSelectedItem().toString());
            }
            IQuantifier quantifier;
            String quantifierTerm = qComboBox.getSelectionModel().getSelectedItem().toString();
            switch (((RadioButton) quantifierGroup.getSelectedToggle()).getId()) {
                case ("qRelative"):
                    quantifier = new Relative(fis, quantifierTerm);
                    break;
                case ("qAbsolute"):
                    quantifier = new Absolute(fis, quantifierTerm);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + ((RadioButton) quantifierGroup.getSelectedToggle()).getId());
            }

            List<SimpleFuzzifyWeather> filteredFWeatherData = fWeatherData;
            if (!pComboBox.getSelectionModel().isEmpty() && !pComboBox.getSelectionModel().getSelectedItem().equals("")) {
                filteredFWeatherData = fWeatherData.parallelStream()
                        .filter(weather -> weather.getSeason().compareTo(
                                Season.valueOf(pComboBox.getSelectionModel().getSelectedItem().toString())
                        ) == 0)
                        .collect(Collectors.toList());
            }

            LinguisticSummary linguisticSummary = LinguisticSummary.builder()
                    .weatherList(filteredFWeatherData)
                    .summarizer(summarizer)
                    .qualifier(qualifier)
                    .quantifier(quantifier)
                    .fis(fis)
                    .build();

            setFromT1ToT11(linguisticSummary);
        }
    }

    @FXML
    public void changeActiveMultiComboBoxes(ActionEvent event) {
        event.consume();
        String id =  ((Node) event.getSource()).getId();
        if (id.equals("firstForm")) {
            qMultiComboBox.setDisable(false);
            kMultiComboBox.setDisable(true);
        } else if (id.equals("secondForm") || id.equals("thirdForm")) {
            qMultiComboBox.setDisable(false);
            kMultiComboBox.setDisable(false);
        } else {
            qMultiComboBox.setDisable(true);
            kMultiComboBox.setDisable(true);
        }
    }

    @FXML
    public void generateMultiSummary() {
        String form = ((RadioButton) formMultiTypeGroup.getSelectedToggle()).getId();
        if (p1MultiComboBox.getSelectionModel().isEmpty() || p2MultiComboBox.getSelectionModel().isEmpty()
                || s1MultiComboBox.getSelectionModel().isEmpty() || s1MultiComboBox.getSelectionModel().getSelectedItem().equals("")) {
            drawAlertWindow();
            return;
        }
        Relative quantifier = null;
        if (!form.equals("fourthForm")) {
            if (qMultiComboBox.getSelectionModel().isEmpty() || qMultiComboBox.getSelectionModel().getSelectedItem().equals("")) {
                drawAlertWindow();
                return;
            } else {
                String quantifierTerm = qMultiComboBox.getSelectionModel().getSelectedItem().toString();
                quantifier = new Relative(fis, quantifierTerm);
            }
        }
        Qualifier qualifier = null;
        if ((form.equals("secondForm") || form.equals("thirdForm"))) {
            if (kMultiComboBox.getSelectionModel().isEmpty() || kMultiComboBox.getSelectionModel().getSelectedItem().equals("")) {
                drawAlertWindow();
                return;
            } else {
                qualifier = new Qualifier(kMultiComboBox.getSelectionModel().getSelectedItem().toString());
            }
        }

        List<String> summarizerList = new ArrayList<>();
        summarizerList.add(s1MultiComboBox.getSelectionModel().getSelectedItem().toString());
        if (!s2MultiComboBox.getSelectionModel().isEmpty() && !s2MultiComboBox.getSelectionModel().getSelectedItem().equals("")) {
            summarizerList.add(s2MultiComboBox.getSelectionModel().getSelectedItem().toString());
        }
        if (!s3MultiComboBox.getSelectionModel().isEmpty() && !s3MultiComboBox.getSelectionModel().getSelectedItem().equals("")) {
            summarizerList.add(s3MultiComboBox.getSelectionModel().getSelectedItem().toString());
        }
        Summarizer summarizer = new Summarizer(summarizerList, Operator.and);

        Season season1 = Season.valueOf(p1MultiComboBox.getSelectionModel().getSelectedItem().toString());
        Season season2 = Season.valueOf(p2MultiComboBox.getSelectionModel().getSelectedItem().toString());
        MultiSubjectLinguisticSummary multiSubjectLinguisticSummary;
        switch (form) {
            case "firstForm":
                multiSubjectLinguisticSummary = new FormOne(season1, season2, fWeatherData, summarizer, quantifier, fis);
                break;
            case "secondForm":
                multiSubjectLinguisticSummary = new FormTwo(season1, season2, fWeatherData, summarizer, quantifier, qualifier, fis);
                break;
            case "thirdForm":
                multiSubjectLinguisticSummary = new FormThree(season1, season2, fWeatherData, summarizer, quantifier, qualifier, fis);
                break;
            case "fourthForm":
                multiSubjectLinguisticSummary = new FormFour(season1, season2, fWeatherData, summarizer, fis);
                break;
            default:
                multiSubjectLinguisticSummary = null;
        }
        setMultiT(multiSubjectLinguisticSummary);
    }

    private void setFromT1ToT11(LinguisticSummary linguisticSummary) {
        try {
            t1Entry.setText(String.valueOf(linguisticSummary.t1()));
            t2Entry.setText(String.valueOf(linguisticSummary.t2()));
            t3Entry.setText(String.valueOf(linguisticSummary.t3()));
            t4Entry.setText(String.valueOf(linguisticSummary.t4()));
            t5Entry.setText(String.valueOf(linguisticSummary.t5()));
            t6Entry.setText(String.valueOf(linguisticSummary.t6()));
            t7Entry.setText(String.valueOf(linguisticSummary.t7()));
            t8Entry.setText(String.valueOf(linguisticSummary.t8()));
            if (linguisticSummary.getQualifier() != null) {
                t9Entry.setText(String.valueOf(linguisticSummary.t9()));
                t10Entry.setText(String.valueOf(linguisticSummary.t10()));
                t11Entry.setText(String.valueOf(linguisticSummary.t11()));
            } else {
                String empty = "Brak";
                t9Entry.setText(empty);
                t10Entry.setText(empty);
                t11Entry.setText(empty);
            }
        } catch (QuantifierException e) {
            drawAlertWindow(e);
        }
    }

    private void setMultiT(MultiSubjectLinguisticSummary multiSubjectLinguisticSummary) {
        tMultiEntry.setText(String.valueOf(multiSubjectLinguisticSummary.t1()));
    }
}
