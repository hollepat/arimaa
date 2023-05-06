package cz.cvut.fel.pjv.controller;

import cz.cvut.fel.pjv.model.*;
import cz.cvut.fel.pjv.view.BoardPanel;
import cz.cvut.fel.pjv.view.GameFrame;
import cz.cvut.fel.pjv.view.TimerPanel;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.utils.MyFormatter;

import javax.swing.*;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.*;
import java.util.stream.Collectors;


public class Game {

    public Player currentPlayer;
    public final int ZERO_MOVES = 0;
    public final int MAX_MOVES = 3;
    private Turn currentTurn;
    public int moveCnt = 0;
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
    public static final Level level = Level.CONFIG;

    /**
     * Constructor for new Game.
     *
     * @param log       boolean to indicated if log messages should be on/off
     * @param timeLimit is how much time Player has for his moves
     * @param ownLayout boolean to indicated if Players want their own layout or preset
     */
    public Game(Boolean log, int timeLimit, Boolean ownLayout) {
        logger.log(Level.CONFIG, "log = " + log + ", timeLimit = " + timeLimit + "ownLayout = " + ownLayout);
        this.gameStatus = GameStatus.ACTIVE;
        this.logging = log;
        this.timeLimit = timeLimit;
        this.ownLayout = ownLayout;
        setUpLogger();
        initPlayers();
        initModel();
        //initGUI();
    }

    /**
     * Constructor for loaded Game.
     *
     * @param file containing move log from previous game
     */
    public Game(File file) {
        // TODO init from file
    }


    private void initPlayers() {
        this.playerGold = new Player(ColorPiece.GOLD, timerPanel, timeLimit);
        this.playerSilver = new Player(ColorPiece.SILVER, timerPanel, timeLimit);
        this.currentPlayer = this.playerGold;   // starting Player
    }

    private void initModel() {
        boardModel = new BoardModel(this);
        moveLogger = new MoveLogger(this);
        gameValidator = new GameValidator(boardModel, this);
        Game.logger.log(Level.INFO, boardModel.toString());
    }

    private void initGUI() {
        timerPanel = new TimerPanel(this);
        boardPanel = new BoardPanel(this);
        gameFrame = new GameFrame(this);

    }

    private void setUpLogger() {
        logger.setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new MyFormatter());
        logger.addHandler(handler);

        if (this.logging) {
            logger.setLevel(level);
            handler.setLevel(level);
        } else {
            logger.setLevel(Level.OFF);
        }
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
        // create new Move
        Move move = new Move(boardModel.getSpot(sx, sy).getPiece(), sx, sy, dx, dy, currentPlayer, moveCnt);
        Game.logger.log(Level.CONFIG, "Request to move from " + sx + " " + sy + " to " + dx + " " + dy);
        // generate all possible Moves for Piece
        List<Move> validMoves = gameValidator.generateValidMoves(boardModel.getSpot(sx, sy).getPiece(), boardModel.getSpot(sx, sy), currentPlayer);

        // check if Move is in valid Moves
        validMoves = validMoves.stream().filter(move1 -> move.getDy() == move1.getDy() && move.getDx() == move1.getDx()).collect(Collectors.toList());
        if (!validMoves.isEmpty()) {
            move.pushPromise = validMoves.get(0).pushPromise;
            execute(move);
            moveLogger.saveMove(move);
            Game.logger.log(Level.INFO, "Move " + move.getMoveNumInTurn() + " executed by " + currentPlayer.getColor() + "! " + move.getNotation());
        } else { // undo also push move (previous) if promised move rejected
            if (!moveLogger.isEmpty() && moveLogger.peekMove().pushPromise) {
                undoMove();
            }
            Game.logger.log(Level.INFO, "Invalid Move!!! " + move.getNotation());
            return; // throw away invalid move
        }

        // update Turn
        updateTurn(move);

        // check traps
        gameValidator.checkTrapped(move);
        for (Map.Entry<String, Piece> entry : move.getKilledPieces().entrySet()) {
            Game.logger.log(Level.INFO, "Killing " + entry.getValue().toString() + " on " + entry.getKey());
            boardModel.removePiece(entry.getValue(), entry.getKey().charAt(0), Integer.parseInt(String.valueOf(entry.getKey().charAt(1))));
            //boardPanel.removePiece(entry.getKey().charAt(0), Integer.parseInt(String.valueOf(entry.getKey().charAt(1))));
        }
        // check end game
        if (gameValidator.endMove(move)) {
            Game.logger.log(Level.INFO, "End of game!");
            showWinnerDialog();
        }

        Game.logger.log(Level.CONFIG, boardModel.toString());

    }

    public void updateTurn(Move move) {
        if (currentTurn == null) {
            currentTurn = new Turn(currentPlayer);
        }

        if (moveCnt >= MAX_MOVES) {
            currentTurn.addMove(move);
            nextTurn();
        } else {
            currentTurn.addMove(move);
            moveCnt++;
        }
    }




    /**
     * Handle move requests between player and PC.
     */
    public void moveRequestPC() {

    }

    private void execute(Move move) {
        boardModel.doMove(move);    // Update move in board model
        //boardPanel.makeMove(move);  // Update move in board panel

    }

    /**
     * Undo last two turns so currentPlayer can replace his previous turn.
     */
    public void undoTurn() {
        // undo moves in current turn
        Game.logger.log(Level.INFO, currentPlayer.getTurn() + String.valueOf(currentPlayer.getNotation()) + " takeback");
        ListIterator<Move> listIterator = currentTurn.getMoves().listIterator(currentTurn.getMoves().size());
        while (listIterator.hasPrevious()) {
            Move prev = listIterator.previous();
            //boardPanel.makeUndo(prev);
            boardModel.undoMove(prev);
        }
        // undo moves in Opponent's turn
        Turn lastTurn = moveLogger.popTurn();
        if (lastTurn != null) {
            Game.logger.log(Level.INFO, lastTurn.getCnt() + String.valueOf(lastTurn.getPlayer().getNotation()) + " takeback");
            lastTurn.getPlayer().decreaseTurn();
            listIterator = lastTurn.getMoves().listIterator(lastTurn.getMoves().size());
            while (listIterator.hasPrevious()) {
                Move prev = listIterator.previous();
                //boardPanel.makeUndo(prev);
                boardModel.undoMove(prev);
            }
        }
        // undo moves in previous turn to replace them
        lastTurn = moveLogger.popTurn();
        if (lastTurn != null) {
            listIterator = lastTurn.getMoves().listIterator(lastTurn.getMoves().size());
            while (listIterator.hasPrevious()) {
                Move prev = listIterator.previous();
                //boardPanel.makeUndo(prev);
                boardModel.undoMove(prev);
            }
        }
        currentPlayer.decreaseTurn();
        currentTurn = new Turn(currentPlayer);
        moveCnt = ZERO_MOVES;
        Game.logger.log(Level.INFO, boardModel.toString());
    }


    /**
     * Undo last Move in GUI (BoardPanel) and model (BoardModel).
     */
    private void undoMove() {
        // get last Move
        Move lastMove = moveLogger.popMove();
        if (lastMove != null) {
            Game.logger.log(Level.INFO, "Undo move: " + lastMove.toString());
            //boardPanel.makeUndo(lastMove);
            boardModel.undoMove(lastMove);
            moveCnt = lastMove.getMoveNumInTurn();
            switchCurrentPlayer(lastMove.getPlayer());
            currentTurn.popMove();
        }
        Game.logger.log(Level.CONFIG, currentTurn.toString());
        Game.logger.log(Level.INFO, boardModel.toString());
    }

    private void showWinnerDialog() {
        Move lastMove = moveLogger.peekMove();
        JOptionPane.showMessageDialog(null, "You win " + lastMove.getPlayer().getColor() + "!");
    }

    private void nextTurn() {
        // old Turn
        moveLogger.saveTurn(currentTurn);
        Game.logger.log(Level.INFO, currentTurn.getNotation());

        // new Turn
        switchCurrentPlayer();
        currentPlayer.increaseTurn();
        currentTurn = new Turn(currentPlayer);
        moveCnt = ZERO_MOVES;
    }

    /**
     * End current turn change players!
     */
    public void endTurn() {
        nextTurn();
    }

    /**
     * Change currentPlayer to opposite one.
     */
    private void switchCurrentPlayer() {
        switch (this.currentPlayer.getColor()) {
            case GOLD -> this.currentPlayer = this.getPlayerSilver();
            case SILVER -> this.currentPlayer = this.getPlayerGold();
            default -> Game.logger.log(Level.WARNING, "Current player is null!");
        }
        //gameFrame.changeMsg("Current player is: " + currentPlayer.getColor());
        Game.logger.log(Level.INFO, "Current player is: " + currentPlayer.getColor());
    }

    /**
     * Change currentPlayer.
     *
     * @param player is new currentPlayer
     */
    private void switchCurrentPlayer(Player player) {
        this.currentPlayer = player;
        //gameFrame.changeMsg("Current player is: " + currentPlayer.getColor());
        Game.logger.log(Level.INFO, "Current player is: " + currentPlayer.getColor());
    }

    /**
     * Save history of moves into file.
     */
    public void saveToFile() {
        // TODO save current game set to file
        Game.logger.log(Level.INFO, "Saving current Game!");

    }

    // -----------------------------
    // ---- Getters and Setters ----
    // -----------------------------

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

    public Player getPlayerGold() {
        return playerGold;
    }

    public Player getPlayerSilver() {
        return playerSilver;
    }

    public Boolean getOwnLayout() {
        return ownLayout;
    }
}
