package cz.cvut.fel.pjv.controller;

import cz.cvut.fel.pjv.model.Board;
import cz.cvut.fel.pjv.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    private GridPane chessBoard;

    public void initialize(){
        // first home screen, where you choose new game or load old one
        // choose PC or user opponent and which gold or silver you play for
        Game game = new Game(chessBoard);

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}