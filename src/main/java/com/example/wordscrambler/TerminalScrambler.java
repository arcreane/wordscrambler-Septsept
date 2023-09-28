package com.example.wordscrambler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TerminalScrambler {
    private final Game a_game;
    private String a_sDifficulty;
    Scanner scan = new Scanner(System.in);

    public TerminalScrambler() throws IOException {
        a_game = new Game();
        String answer = null;
        boolean userAnsweredQuit = false;
        boolean userWantToQuit = false;
        boolean userWantToSave = false;

        while(!userWantToQuit){
            Start();

            while(!userWantToSave){
                System.out.println("Veux-tu sauvegarder ? (\"oui\" ou \"non\")");
                answer = scan.nextLine();
                if(answer.equalsIgnoreCase("non") || answer.equalsIgnoreCase("oui")) {
                    userWantToSave = true;
                }
            }

            a_game.saveGame();

            while(!userAnsweredQuit){
                System.out.println("Veux-tu recommencer ? (\"oui\" ou \"non\")");
                answer = scan.nextLine();
                if(answer.equalsIgnoreCase("non") || answer.equalsIgnoreCase("oui")) {
                    userAnsweredQuit = true;
                }
            }

            if(answer.equalsIgnoreCase("non")){
                userWantToQuit = true;
            }
        }
    }

    private void Start() throws IOException {
        boolean userWon = false;
        int numberOfTries = 1;
        String answer;

        System.out.println("######################################################################");
        System.out.println("#                          JEU DU SCRAMBLER                          #");
        System.out.println("######################################################################\n");

        SetDifficulty();

        System.out.println("\n\n######################################################################");
        System.out.println("#                               " + a_sDifficulty + "                               #");
        System.out.println("######################################################################\n");

        System.out.println("Devinez le bon mot correspondant à cette suite de lettre (un seul mot est correct) :");
        System.out.println(a_game.a_sBlendedWord + "\n");

        a_game.StartTimer();

        while(!userWon){


            System.out.println("Tentative n°" + numberOfTries + " :");
            answer = scan.nextLine();

            if(answer.equalsIgnoreCase(a_game.a_sWord)){
                userWon = true;
            } else {
                numberOfTries++;
            }
        }
        a_game.EndTimer();
        System.out.println("Félicitation tu as trouvé la réponse en " + numberOfTries + " tentatives et " + a_game.a_sTimer + ".\n\n");
    }

    private void SetDifficulty() throws IOException {
        String difficulty;
        boolean userChosen = false;
        int minChar = 0;
        int maxChar = 0;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File("src/main/resources/com/example/wordscrambler/Config/config.json"));

        while(!userChosen){
            System.out.println("Veuillez choisir la difficulté : (tapez \"facile\",\"moyen\" ou \"difficile\")");
            difficulty = scan.nextLine();

            if(difficulty.equalsIgnoreCase("facile")) {
                minChar = jsonNode.get("MIN_CHAR_EASY").asInt();
                maxChar = jsonNode.get("MAX_CHAR_EASY").asInt();
                a_sDifficulty = "FACILE";
                userChosen = true;
            } else if (difficulty.equalsIgnoreCase("moyen")) {
                minChar = jsonNode.get("MIN_CHAR_MEDIUM").asInt();
                maxChar = jsonNode.get("MAX_CHAR_MEDIUM").asInt();
                a_sDifficulty = "MOYEN ";
                userChosen = true;
            } else if (difficulty.equalsIgnoreCase("difficile")) {
                minChar = jsonNode.get("MIN_CHAR_HARD").asInt();
                maxChar = jsonNode.get("MAX_CHAR_HARD").asInt();
                a_sDifficulty = " HARD ";
                userChosen = true;
            }
            if (userChosen) {
                a_game.Difficulty(minChar, maxChar);
            }
        }
    }
}