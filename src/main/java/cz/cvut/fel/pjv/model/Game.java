package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.gui.BoardPanel;
import cz.cvut.fel.pjv.gui.GameFrame;
import cz.cvut.fel.pjv.gui.TimerPanel;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.utilities.MyFormatter;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.logging.*;


public class Game {

    public Player currentPlayer;
    public int movesInTurn = 0;
    public final int ZERO_MOVES = 0;
    public final int MAX_MOVES = 3;

    private int timeLimit;
    private Player playerGold;
    private Player playerSilver;
    private GameStatus gameStatus;
    private MoveLogger moveLogger;
    private BoardModel boardModel;  // originator
    private GameValidator gameValidator;
    private BoardPanel boardPanel;
    private TimerPanel timerPanel;
    private GameFrame gameFrame;
    private Boolean logging;
    private Boolean ownLayout;
    public static Logger logger = Logger.getLogger(Game.class.getName());
    public static final Level level = Level.FINE;

    public boolean pushPromise = false;   // have to move stronger current Players Piece on source position of last move

    /**
     * Constructor for new Game.
     *
     * @param log boolean to indicated if log messages should be on/off
     * @param timeLimit is how much time Player has for his moves
     * @param ownLayout boolean to indicated if Players want their own layout or preset
     */
    public Game(Boolean log, int timeLimit, Boolean ownLayout) {
        logger.log(Level.CONFIG, "log = " + log + ", timeLimit = " + timeLimit + "ownLayout = " + ownLayout);
        this.gameStatus = GameStatus.ACTIVE;
        this.logging = log;
        this.timeLimit = timeLimit;
        this.ownLayout = ownLayout;
        initPlayers();
        initModel();
        initGUI();
        setUpLogger();
    }

    /**
     * Constructor for loaded Game.
     * @param file containing move log from previous game
     */
    public Game(File file) {
        // TODO init from file
    }


    private void initPlayers() {
        this.playerGold = new Player(ColorPiece.GOLD, timerPanel,timeLimit);
        this.playerSilver = new Player(ColorPiece.SILVER, timerPanel, timeLimit);
        this.currentPlayer = this.playerGold;   // starting Player
    }

    private void initModel() {
        boardModel = new BoardModel();
        moveLogger = new MoveLogger();
        gameValidator = new GameValidator(boardModel, this);

    }

    private void initGUI() {
        timerPanel = new TimerPanel(this);
        boardPanel = new BoardPanel(this);
        gameFrame = new GameFrame(this);

    }

    private void setUpLogger() {
        logger.setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        logger.addHandler(handler);

        if (this.logging) {
            logger.setLevel(level);
            handler.setLevel(level);
        }
        else { logger.setLevel(Level.OFF); }

        handler.setFormatter(new MyFormatter());
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }



    /**
     * Check Move for validity.
     *
     * @param sx char coordinate of Piece
     * @param sy int coordinate of Piece
     * @param dx char coordinate of Piece
     * @param dy int coordinate of Piece
     */
    public void moveRequest(char sx, int sy, char dx, int dy) {
        Move move = new Move(boardModel.getSpot(sx, sy).getPiece(), sx, sy, dx, dy, currentPlayer, this.movesInTurn);
        Game.logger.log(Level.CONFIG, "Request to move from " + sx + " " + sy + " to " + dx + " " + dy);
        List<Move> validMoves = gameValidator.generateValidMoves(boardModel.getSpot(sx, sy).getPiece(), boardModel.getSpot(sx, sy), currentPlayer);

        for (Move validMove : validMoves) {
            if (move.getDy() == validMove.getDy() && move.getDx() == validMove.getDx()) {
                move.pushPromise = validMove.pushPromise;
                execute(move);
                checkMovesInTurn();
                moveLogger.saveMove(move);
                Game.logger.log(Level.INFO,
                    "Move executed! " +
                    move.toString()
                );
            }
        }


        if (gameValidator.isTrapped(move)) {
            Game.logger.log(Level.INFO,
                    "Killing " + move.getPiece().toString() + " on " + move.getDx() + " " + move.getDy());
            boardModel.removePiece(move.getPiece(), move.getDx(), move.getDy());
            boardPanel.removePiece(move.getDx(), move.getDy());
        }

        if (gameValidator.endMove(move)) {
            Game.logger.log(Level.INFO, "End of game!");
            showWinnerDialog();
        }
    }

    /**
     * Check number of moves in turn and switch player if
     * movesInTurn == MAX_MOVES. There can be only 1..4 moves in turn.
     */
    private void checkMovesInTurn() {
        if (movesInTurn == MAX_MOVES) {
            switchCurrentPlayer();
            movesInTurn = ZERO_MOVES;
        } else {
            movesInTurn++;
        }
    }

    /**
     * Handle move requests between player and PC.
     */
    public void moveRequestPC() {

    }

    private void execute(Move move) {
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
            Game.logger.log(Level.INFO, "Undo move: " + lastMove.toString());
            boardPanel.makeUndo(lastMove);
            boardModel.undoMove(lastMove);
            this.movesInTurn = lastMove.getMoveNumInTurn();
            switchCurrentPlayer(lastMove.getPlayer());
        }
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
        this.movesInTurn = ZERO_MOVES;
        gameFrame.changeMsg("Current player is: " + currentPlayer.getColor());
        Game.logger.log(Level.INFO, "Current player is: " + currentPlayer.getColor());
    }

    /**
     * Change currentPlayer.
     *
     * @param player is new currentPlayer
     */
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

    public TimerPanel getTimerPanel() {
        return timerPanel;
    }


}
