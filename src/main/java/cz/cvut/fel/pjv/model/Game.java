package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.BoardPanel;
import cz.cvut.fel.pjv.gui.GameFrame;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.utilities.MyFormatter;

import javax.swing.*;
import java.util.logging.*;


public class Game {

    public Player currentPlayer;
    public int movesInTurn = 0;
    private Player playerGold;
    private Player playerSilver;
    private GameStatus gameStatus;
    private MoveLogger moveLogger;
    private BoardModel boardModel;  // originator
    private GameValidator gameValidator;
    private BoardPanel boardPanel;
    private GameFrame gameFrame;
    private Boolean logging;
    public static Logger logger = Logger.getLogger(Game.class.getName());
    public boolean pushPromise = false;   // have to move stronger current Players Piece on source position of last move

    /**
     * Constructor for Game
     */
    public Game(Boolean log) {
        this.gameStatus = GameStatus.ACTIVE;
        this.logging = log;
        initPlayers();
        initGUI();
        initModel();
        setUpLogger();

    }

    private void initPlayers() {
        this.playerGold = new Player(ColorPiece.GOLD);
        this.playerSilver = new Player(ColorPiece.SILVER);
        this.currentPlayer = this.playerGold;   // starting Player
    }

    private void initModel() {
        boardModel = new BoardModel();
        moveLogger = new MoveLogger();
        gameValidator = new GameValidator(boardModel, this);

    }

    private void initGUI() {
        boardPanel = new BoardPanel(this);
        gameFrame = new GameFrame(this);

    }

    private void setUpLogger() {
        if (this.logging) { logger.setLevel(Level.ALL); }
        else { logger.setLevel(Level.OFF); }

        Handler handler = new ConsoleHandler();
        handler.setFormatter(new MyFormatter());
        logger.addHandler(handler);
    }



    /**
     * Check Move for validity.
     *
     * @param sourceX char coordinate of Piece
     * @param sourceY int coordinate of Piece
     * @param destinationX char coordinate of Piece
     * @param destinationY int coordinate of Piece
     */
    public void moveRequest(char sourceX, int sourceY, char destinationX, int destinationY) {
        Move move = new Move(boardModel.getSpot(sourceX, sourceY).getPiece(), sourceX, sourceY, destinationX, destinationY, currentPlayer, this.movesInTurn);
        if (gameValidator.validateMove(move)) {
            execute(move);
            moveLogger.saveMove(move);
        }
        if (gameValidator.endMove(move)) {
            showWinnerDialog();
        }
    }



    private void execute(Move move) {
        // TODO save Move
        boardModel.doMove(move);    // Update move in board model
        boardPanel.makeMove(move);  // Update move in board panel

    }

    /**
     * Save history of moves into file.
     */
    public void saveToFile() {
        // TODO save current game set to file
    }


    /**
     * Undo last Move in GUI (BoardPanel) and model (BoardModel).
     */
    public void undoMove() {
        Move lastMove = moveLogger.undoMove();
        if (lastMove != null) {
            boardPanel.makeUndo(lastMove);
            boardModel.makeUndo(lastMove);
        }
        this.movesInTurn = lastMove.getMoveNumInTurn();
        switchCurrentPlayer(lastMove.getPlayer());
    }



    private void showWinnerDialog() {
        Move lastMove = moveLogger.getLastMove();
        JOptionPane.showMessageDialog(null, "You win " + lastMove.getPlayer().getColor() + "!");
    }


    // ----- Getters and Setters -----

    /**
     * Change currentPlayer to opposite one.
     */
    public void switchCurrentPlayer() {
        switch (this.currentPlayer.getColor()) {
            case GOLD -> this.currentPlayer = this.getPlayerSilver();
            case SILVER -> this.currentPlayer = this.getPlayerGold();
            default -> Game.logger.log(Level.WARNING, "Current player is null!");
        }
        this.movesInTurn = gameValidator.ZERO_MOVES;
        gameFrame.changeMsg("Current player is: " + currentPlayer.getColor());
        Game.logger.log(Level.INFO, "Current player is: " + currentPlayer.getColor());
    }

    private void switchCurrentPlayer(Player player) {
        this.currentPlayer = player;
        gameFrame.changeMsg("Current player is: " + currentPlayer.getColor());
        Game.logger.log(Level.INFO, "Current player is: " + currentPlayer.getColor());
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public MoveLogger getMoveLogger() {
        return moveLogger;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public Player getPlayerGold() {
        return playerGold;
    }

    public Player getPlayerSilver() {
        return playerSilver;
    }

    public static void main(String[] argv) {
        Game game = new Game(true);
    }

}
