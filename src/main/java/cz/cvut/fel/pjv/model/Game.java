package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.BoardPanel;
import cz.cvut.fel.pjv.gui.GameFrame;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.utilities.MyFormatter;

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
    public static Logger logger = Logger.getLogger(Game.class.getName());;

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
        Move move = new Move(boardModel.getSpot(sourceX, sourceY).getPiece(), sourceX, sourceY, destinationX, destinationY);
        if (gameValidator.validateMove(move)) {
            moveLogger.saveMove(move);
            execute(move);
        }
        gameValidator.endMove(move);
    }


    private void execute(Move move) {
        // save Move
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
            // TODO if valid move ???
            boardPanel.makeUndo(lastMove);
            boardModel.makeUndo(lastMove);
        }
    }





    // ----- Getters and Setters -----


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
