package cz.cvut.fel.pjv.controller;

import cz.cvut.fel.pjv.model.*;
import cz.cvut.fel.pjv.view.*;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.utils.MyFormatter;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;


public class Game {

    private Player currentPlayer;
    private Player npcPlayer;
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
    private MoveValidator moveValidator;
    private BoardPanel boardPanel;
    private TimerPanel timerPanel;
    private GameFrame gameFrame;
    private Boolean ownLayout;
    private Boolean isLog = true;
    public static Logger logger = Logger.getLogger(Game.class.getName());
    public static final Level level = Level.FINE;

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
        this.timeLimit = timeLimit;
        this.ownLayout = ownLayout;

        setUpLogger();
        initPlayers();
        initModel();
        initGUI();
    }

    /**
     * Constructor for new Game against NPC.
     *
     * @param log       boolean to indicated if log messages should be on/off
     * @param timeLimit is how much time Player has for his moves
     * @param ownLayout boolean to indicated if Players want their own layout or preset
     */
    public Game(Boolean log, int timeLimit, Boolean ownLayout, Boolean NPCisGold, Boolean NPCisSilver) {
        logger.log(Level.CONFIG, "log = " + log + ", timeLimit = " + timeLimit + ", ownLayout = " +
                ownLayout + ", NPCisGold = " + NPCisGold + ", NPCisSilver = " + NPCisSilver);

        this.gameStatus = GameStatus.ACTIVE;
        this.timeLimit = timeLimit;
        this.ownLayout = ownLayout;
        this.isLog = log;

        setUpLogger();
        initPlayers(NPCisGold, NPCisSilver);
        initModel();
        initGUI();

        if (currentPlayer == npcPlayer) {
            moveRequestPC();
        }
    }

    /**
     * Constructor for Game loaded from file.
     *
     * @param file containing move log from previous game
     */
    public Game(File file) {
        Game.logger.log(Level.INFO, file.getAbsolutePath());
        List<String> loadedGame = readGameFromFile(file);
        if (loadedGame.isEmpty()) {
            Game.logger.log(Level.WARNING, "Game couldn't be loaded!");
            return;
        }
        int offset = setUpConfigData(loadedGame);
        setUpLogger();
        if (playerGold == null || playerSilver == null) { initPlayers(); }
        moveLogger = new MoveLogger(this);
        boardModel = new BoardModel(this, loadedGame.get(offset), loadedGame.get(offset+1));
        moveValidator = new MoveValidator(boardModel, this);
        Game.logger.log(Level.INFO, boardModel.toString());
        initGUI();
        try {
            for (int i = offset+2; i < loadedGame.size(); i++) {
                parseTurn(loadedGame.get(i).split(" "));
            }
        } catch (Exception e) {
            Game.logger.log(Level.WARNING, "Cannot recreate Game!");

            gameFrame.dispose();
            LaunchScreen launchScreen = new LaunchScreen();
        }

    }

    /**
     * Read config data from file.
     * @param loadedGame List of information
     * @return index where is starts log of Game
     */
    private int setUpConfigData(List<String> loadedGame) {
        Iterator<String> stringIterator = loadedGame.iterator();
        String line;
        int idx = 0;
        while (!(line = stringIterator.next()).equals("--------")) {
            setData(List.of(line.split(" ")));
            idx++;
        }
        return ++idx;
    }

    private void setData(List<String> list) {
        switch (list.get(0)) {
            case "timeLimit" -> this.timeLimit = Integer.parseInt(list.get(1));
            case "NPC" -> initPlayers(list.get(1));
            case "isLog" -> this.isLog = Boolean.parseBoolean(list.get(1));
        }
    }

    private void parseTurn(String[] s) {
        char offset = '0';
        for (int i = 1; i < s.length; i++) {
            moveRequest(s[i].charAt(1), s[i].charAt(2)-offset,
                    getDxFromDirection(s[i].charAt(1), s[i].charAt(3)),
                    getDyFromDirection(s[i].charAt(2)-offset, s[i].charAt(3)));
        }
    }

    private int getDyFromDirection(int y, char dir) {
        return switch (dir) {
            case 'n' -> y + 1;
            case 's' -> y -1;
            default -> y;
        };
    }

    private char getDxFromDirection(char x, char dir) {
        return switch (dir) {
            case 'e' -> moveValidator.addX(x, 1);
            case 'w' -> moveValidator.addX(x, -1);
            default -> x;
        };
    }

    private List<String> readGameFromFile(File file) {
        List<String> loadedGame = new ArrayList<>();
        try {
            FileReader reader = new FileReader(file); // open the file for reading
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) { // read the contents of the file line by line
                Game.logger.log(Level.INFO, line); // print the line to the console
                loadedGame.add(line);
            }
            bufferedReader.close(); // close the BufferedReader
        } catch (IOException e) { // if an IOException occurs, print the error message
           Game.logger.log(Level.WARNING, "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return loadedGame;
    }


    private void initPlayers(Boolean NPCisGold, Boolean NPCisSilver) {
        initPlayers();
        this.npcPlayer = NPCisGold ? this.playerGold : this.playerSilver;
    }

    private void initPlayers(String npc) {
        initPlayers();
        switch (npc) {
            case "s" -> this.npcPlayer = this.playerSilver;
            case "g" -> this.npcPlayer = this.playerGold;
        }
    }

    private void initPlayers() {
        this.playerGold = new Player(ColorPiece.GOLD, timerPanel, timeLimit);
        this.playerSilver = new Player(ColorPiece.SILVER, timerPanel, timeLimit);
        this.currentPlayer = this.playerGold;   // starting Player
    }

    private void initModel() {
        boardModel = new BoardModel(this);
        moveLogger = new MoveLogger(this);
        moveValidator = new MoveValidator(boardModel, this);
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

        if (this.isLog) {
            logger.setLevel(level);
            handler.setLevel(level);
        } else {
            logger.setLevel(Level.OFF);
        }
    }


    /**
     * Handle single request to move Piece.
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
        List<Move> validMoves = moveValidator.generateValidMoves(boardModel.getSpot(sx, sy).getPiece(), boardModel.getSpot(sx, sy), currentPlayer);

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
        moveValidator.checkTrapped(move);
        for (Map.Entry<String, Piece> entry : move.getKilledPieces().entrySet()) {
            Game.logger.log(Level.INFO, "Killing " + entry.getValue().toString() + " on " + entry.getKey());
            boardModel.removePiece(entry.getValue(), entry.getKey().charAt(0), Integer.parseInt(String.valueOf(entry.getKey().charAt(1))));
            boardPanel.removePiece(entry.getKey().charAt(0), Integer.parseInt(String.valueOf(entry.getKey().charAt(1))));
        }
        // check end game
        if (moveValidator.endMove(move)) {
            Game.logger.log(Level.INFO, "End of game!");
            showWinnerDialog();
        }

        Game.logger.log(Level.CONFIG, boardModel.toString());
    }

    /**
     * Update turns. One Turn consists of 1-4 moves of one Player.
     * @param move in Turn
     */
    private void updateTurn(Move move) {
        if (currentTurn == null) {
            currentPlayer.increaseTurn();
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
     * Generate moves for PC player. Then switch current Player.
     */
    public void moveRequestPC() {
        Game.logger.log(Level.INFO, "NPC is making Turn!");
        Random random = new Random();
        while (moveCnt == 0) {
            int numberOfMoves = random.nextInt(4) + 1;
            for (int i = 0; i < numberOfMoves; i++) {
                // TODO choose Piece on board
                Spot s = getRandomPiece();
                Game.logger.log(Level.FINE, s.toString() + " to be moved by NPC!");
                // TODO push or pull move if can be apply
                Spot weaker = moveValidator.isWeakerAround(s);
                if (weaker != null && moveCnt < 2) {
                    Game.logger.log(Level.FINE, weaker.toString() + " to be moved by NPC!");
                    int pushOrPull = random.nextInt(2);
                    switch (pushOrPull) {
                        case 0 -> doPushPC(weaker, s);
                        case 1 -> doPullPC(weaker, s);
                    };
                } else {
                    executeRandomMove(s);
                }
            }
        }

        if (currentPlayer == npcPlayer) {
            nextTurn();
        }

    }

    private void executeRandomMove(Spot s) {
        Random rand = new Random();
        Game.logger.log(Level.FINE, "Execute random move for " + s.toString());
        // TODO generate possible moves
        List<Move> validMoves = moveValidator.generateValidMoves(s.getPiece(), s, currentPlayer);
        try {
            Move m = validMoves.get(rand.nextInt(validMoves.size()));
            // TODO execute randomlyGenerated move
            moveRequest(m.getSx(), m.getSy(), m.getDx(), m.getDy());
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            Game.logger.log(Level.WARNING, s.getPiece().toString() + " has no Moves!");
        }
    }

    private void doPushPC(Spot weaker, Spot s) {
        executeRandomMove(weaker);
        moveRequest(s.getX(), s.getY(), weaker.getX(), weaker.getY());
    }

    private void doPullPC(Spot weaker, Spot s) {
        executeRandomMove(s);
        moveRequest(weaker.getX(), weaker.getY(), s.getX(), s.getY());
    }

    private Spot getRandomPiece() {
        List<Spot> list = boardModel.getPieces(npcPlayer.getColor());
        Random random = new Random();
        return list.get(random.nextInt(list.size()));

    }


    private void execute(Move move) {
        boardModel.doMove(move);    // Update move in board model
        boardPanel.makeMove(move);  // Update move in board panel

    }

    /**
     * Undo last two turns so currentPlayer can replace his previous turn.
     */
    public void undoTurn() {
        // undo moves in current turn
        Game.logger.log(Level.INFO, currentPlayer.getTurn() + String.valueOf(currentPlayer.getNotation()) + " takeback");
        ListIterator<Move> listIterator = currentTurn.getMoves().listIterator(currentTurn.getMoves().size());
        currentPlayer.decreaseTurn();
        while (listIterator.hasPrevious()) {
            Move prev = listIterator.previous();
            boardPanel.makeUndo(prev);
            boardModel.undoMove(prev);
        }
        // undo moves in Opponent's turn
        Turn lastTurn = moveLogger.popTurn();
        if (lastTurn != null) {
            Game.logger.log(Level.INFO, lastTurn.getCnt() + String.valueOf(lastTurn.getPlayer().getNotation()) + " takeback");
            lastTurn.getPlayer().decreaseTurn();
            switchCurrentPlayer(lastTurn.getPlayer());
            listIterator = lastTurn.getMoves().listIterator(lastTurn.getMoves().size());
            while (listIterator.hasPrevious()) {
                Move prev = listIterator.previous();
                boardPanel.makeUndo(prev);
                boardModel.undoMove(prev);
            }
        }
        // undo moves in previous turn to replace them
        lastTurn = moveLogger.popTurn();
        if (lastTurn != null) {
            switchCurrentPlayer(lastTurn.getPlayer());
            listIterator = lastTurn.getMoves().listIterator(lastTurn.getMoves().size());
            while (listIterator.hasPrevious()) {
                Move prev = listIterator.previous();
                boardPanel.makeUndo(prev);
                boardModel.undoMove(prev);
            }
        }
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
            boardPanel.makeUndo(lastMove);
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

        // NPC will make its turn
        if (npcPlayer != null && currentPlayer == npcPlayer) {
            moveRequestPC();
        }
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
        gameFrame.changeMsg("Current player is: " + currentPlayer.getColor());
        Game.logger.log(Level.INFO, "Switch to (current) player is: " + currentPlayer.getColor());
    }

    /**
     * Change currentPlayer.
     *
     * @param player is new currentPlayer
     */
    private void switchCurrentPlayer(Player player) {
        this.currentPlayer = player;
        gameFrame.changeMsg("Current player is: " + currentPlayer.getColor());
        Game.logger.log(Level.INFO, "Switch to (current) player is: " + currentPlayer.getColor());
    }

    /**
     * Save history of moves into file.
     */
    public void saveToFile() throws IOException {
        Game.logger.log(Level.INFO, "Saving current Game!");
        String fileSeparator = System.getProperty("file.separator");
        String fileName = "saved_games" + fileSeparator + "recordArimaa.txt";
        try {
            // create File
            File file = new File(fileName); // create a new File object with the desired filename
            if (file.createNewFile()) { // create a new file with the specified name
                Game.logger.log(Level.FINE, "File created: " + file.getName()); // print the filename of the newly created file
            } else {
                Game.logger.log(Level.FINE,"File already exists."); // if the file already exists, print a message indicating so
            }
            // write to File
            FileWriter writer = new FileWriter(file);
            writeToFileConfigData(writer);
            writer.write(boardModel.getCurrentLayoutGold() + "\n");
            writer.write(boardModel.getCurrentLayoutSilver() + "\n");
            moveLogger.saveMovesToFile(file, writer);
            writer.close();
            // show msg
            JOptionPane.showMessageDialog(null, "Game was saved!");
        } catch (IOException e) { // if an IOException occurs, print the error message
            Game.logger.log(Level.WARNING, "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }


    }

    public void writeToFileConfigData(FileWriter writer) throws IOException {
        writer.write("timeLimit " + timeLimit + "\n");
        if (npcPlayer != null) {
            writer.write("NPC " + npcPlayer.getNotation() + "\n");
        }
        writer.write("isLog " + this.isLog + "\n");
        writer.write("--------\n");
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getNpcPlayer() {
        return npcPlayer;
    }

    public Boolean getOwnLayout() {
        return ownLayout;
    }
}
