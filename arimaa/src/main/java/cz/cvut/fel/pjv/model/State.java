package cz.cvut.fel.pjv.model;


import java.sql.Time;

public class State {        // state of game that is being saved in Memento

    // TODO hold a state of board --> position of each Piece
    // hold position base on some coding of some kind

    // TODO hold time of the game and who's turn is
    String currentPlayer;
    Time time;

}
