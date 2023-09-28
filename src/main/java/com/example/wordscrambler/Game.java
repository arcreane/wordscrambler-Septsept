package com.example.wordscrambler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Game {
    @JsonProperty("word_to_find")
    public String a_sWord;
    @JsonProperty("blended_word")
    public String a_sBlendedWord;
    @JsonProperty("elapsed_time")
    public String a_sTimer;
    @JsonProperty("number_of_tries")
    public int a_iNumberOfTries;
    @JsonProperty("date")
    public Date a_currentDate;
    @JsonIgnore
    public double a_lStartTimer;

    public Game() {
        a_iNumberOfTries = 0;
        a_currentDate = new Date();
    }

    public void Difficulty(int p_iMin, int p_iMax) throws IOException {
        Path path = Paths.get("src/main/resources/com/example/wordscrambler/words/words.txt");

        List<String> list = Files.readAllLines(path)
                .stream()
                .filter(words -> words.length() >= p_iMin && words.length() <= p_iMax)
                .toList();

        int max = list.size();
        int randomNumber = 1 + (int)(Math.random() * max);

        a_sWord = list.get(randomNumber);
        BlendWord();
    }

    private void BlendWord() {
        char[] characters = a_sWord.toCharArray();

        Random random = new Random();

        for (int i = 0; i < characters.length; i++) {
            int j = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }

        a_sBlendedWord = new String(characters);
    }

    public void StartTimer(){
        a_lStartTimer = System.currentTimeMillis();
    }
    public void EndTimer(){
        long EndTimer = System.currentTimeMillis();

        a_sTimer = ((EndTimer - a_lStartTimer) / 1000) + " secondes";
    }

    public void saveGame() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String saveDirectory = "src/main/resources/com/example/wordscrambler/saves/";

        File directory = new File(saveDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = saveDirectory + "save_" + System.currentTimeMillis() + ".json";

        objectMapper.writeValue(new File(fileName), this);
    }
}

