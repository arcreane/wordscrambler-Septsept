package com.example.wordscrambler;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ScramblerController implements Initializable {
    public StackPane background;
    public ImageView imageTest;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputStream input = Main.class.getResourceAsStream("view/images/background.jpg");

//        background.setBackground( new BackgroundImage(
//                new Image(input),
//                null,
//                null,
//                null,
//                null
//                ));
//        imageTest.setImage(new Image(input));
    }



}