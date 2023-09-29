package com.example.wordscrambler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class ScramblerController implements Initializable {
    public StackPane background;
    public Label labelTimer;
    public Label labelTry;
    private ObservableList<String> a_lTries;
    public Label Difficulty;
    public Label WordToFind;
    public TextField InputWord;
    @FXML
    public ListView<String> ListTries;
    public List<String> a_lRetry;
    public Button TryButton;
    private final Game game;
    private Timeline timer;
    private int m_iSeconds = 0;
    private int m_iMinutes = 0;


    public ScramblerController() {
        game = new Game();
        a_lRetry = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputStream input = Main.class.getResourceAsStream("view/images/background.jpg");
        a_lTries = FXCollections.observableArrayList();
        ListTries.setItems(a_lTries);
        SetRetry();
    }

    public void TryWord(ActionEvent actionEvent) throws IOException {
        if(!InputWord.getText().isEmpty()) {
            a_lTries.add(InputWord.getText().toUpperCase());


            if (InputWord.getText().equalsIgnoreCase(game.a_sWord)) {
                Win();
            } else {
                Difficulty.setText(a_lRetry.get((int) (Math.random() * a_lRetry.size())));
            }

            InputWord.clear();
        }
    }

    private void Win() throws IOException {
        Difficulty.setText("Bravo tu as trouvé le mot !");
        InputWord.setDisable(true);
        TryButton.setDisable(true);
        stopTimer();

        game.saveGame();
    }

    public void Easy(ActionEvent actionEvent) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File("src/main/resources/com/example/wordscrambler/Config/config.json"));


        int minChar = jsonNode.get("MIN_CHAR_EASY").asInt();
        int maxChar = jsonNode.get("MAX_CHAR_EASY").asInt();

        game.Difficulty(minChar, maxChar);
        Difficulty.setText("Facile");
        PutWord();
    }

    public void Medium(ActionEvent actionEvent) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File("src/main/resources/com/example/wordscrambler/Config/config.json"));

        int minChar = jsonNode.get("MIN_CHAR_MEDIUM").asInt();
        int maxChar = jsonNode.get("MAX_CHAR_MEDIUM").asInt();

        game.Difficulty(minChar, maxChar);
        Difficulty.setText("Moyen");
        PutWord();
    }

    public void Hard(ActionEvent actionEvent) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File("src/main/resources/com/example/wordscrambler/Config/config.json"));

        int minChar = jsonNode.get("MIN_CHAR_HARD").asInt();
        int maxChar = jsonNode.get("MAX_CHAR_HARD").asInt();

        game.Difficulty(minChar, maxChar);
        Difficulty.setText("Difficile");
        PutWord();
    }

    private void PutWord(){
        labelTimer.setVisible(true);
        labelTry.setVisible(true);
        InputWord.setVisible(true);
        TryButton.setVisible(true);
        ListTries.setVisible(true);

        InputWord.setDisable(false);
        TryButton.setDisable(false);

        WordToFind.setText(game.a_sBlendedWord);
        a_lTries.clear();
        ResetTimer();
        StartTimer();
        System.out.println(game.a_sWord);
    }

    private void SetRetry(){
        a_lRetry.add("Eh non !");
        a_lRetry.add("Dommage");
        a_lRetry.add("N'abandonne pas !");
        a_lRetry.add("Réessaie");
        a_lRetry.add("Féliciation tu l'as pas trouvé");
        a_lRetry.add("MDR");
        a_lRetry.add("Sérieux ?");
        a_lRetry.add("Un petit effort");
        a_lRetry.add("T'es mauvais sah");
        a_lRetry.add("Mael ?");
        a_lRetry.add("Nope");
    }

    private void StartTimer() {
        if (timer == null) {
            timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
            timer.setCycleCount(Timeline.INDEFINITE);
        }
        timer.play();
    }
    @FXML
    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
    private void ResetTimer()
    {
        m_iMinutes = 0;
        m_iSeconds = 0;
        String timeString = String.format("%02d:%02d", m_iMinutes, m_iSeconds);
        labelTimer.setText(timeString);
    }
    private void updateTimer() {
        m_iSeconds++;
        if (m_iSeconds == 60) {
            m_iSeconds = 0;
            m_iMinutes++;
        }
        String timeString = String.format("%02d:%02d", m_iMinutes, m_iSeconds);
        labelTimer.setText(timeString);
        game.a_sTimer = timeString;
    }

}