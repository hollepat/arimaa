package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.controller.Game;
import cz.cvut.fel.pjv.controller.GameStatus;
import cz.cvut.fel.pjv.pieces.ColorPiece;
import cz.cvut.fel.pjv.pieces.Piece;
import cz.cvut.fel.pjv.pieces.PieceSet;
import cz.cvut.fel.pjv.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static cz.cvut.fel.pjv.pieces.ColorPiece.GOLD;
import static cz.cvut.fel.pjv.pieces.ColorPiece.SILVER;

public class MoveValidator {


    private final BoardModel boardModel;
    private final Game game;

    /**
     * Constructor.
     *
     * @param boardModel is model of Board
     * @param game object using GameValidator to validate moves.
     */
    public MoveValidator(BoardModel boardModel, Game game) {
        this.boardModel = boardModel;
        this.game = game;
    }

    /**
     * Generate all valid moves for pctbm by current player
     * @param player player moving piece
     * @return List of valid moves for Piece
     */
    public List<Move> generateValidMoves(Spot spot, Player player) {
        Game.logger.log(Level.INFO, "Generating moves for piece on " + spot.getX() + " " + spot.getY());

        List<Move> moves = new ArrayList<>();
        if (spot.getX() != 'h') {
            Move moveUp = new Move(spot.getPiece(), spot.getX(), spot.getY(), addX(spot.getX(), 1), spot.getY(), player);
            moves.add(moveUp);
        }
        if (spot.getX() != 'a') {
            Move moveDown = new Move(spot.getPiece(), spot.getX(), spot.getY(), addX(spot.getX(), -1), spot.getY(), player);
            moves.add(moveDown);
        }
        if (spot.getY() < 8) {
            Move moveLeft = new Move(spot.getPiece(), spot.getX(), spot.getY(), spot.getX(), spot.getY() + 1, player);
            moves.add(moveLeft);
        }
        if (spot.getY() > 1) {
            Move moveRight = new Move(spot.getPiece(), spot.getX(), spot.getY(), spot.getX(), spot.getY() - 1, player);
            moves.add(moveRight);
        }
        Game.logger.log(Level.CONFIG, "Filtering valid moves: " + moves);
        List<Move> validMoves = moves.stream().filter(move -> isValid(move, player)).collect(Collectors.toList());
        Game.logger.log(Level.CONFIG,"Valid moves: " + validMoves);
        return validMoves;
    }

    /**
     * Validate if Move can be played regarding the rules of Game.
     * @param move which has been just played
     * @return true == valid
     */
    public boolean isValid(Move move, Player currentPlayer) {

        if (move.getPiece() == null) {
            Game.logger.log(Level.WARNING, "No piece found on " + move.getSx() + " " + move.getSy() +" !");
            return false;
        }

        // check if spot is free
        if (boardModel.getSpot(move.getDx(), move.getDy()).isOccupied()) {
            Game.logger.log(Level.WARNING,
                    "Spot " + move.getDx() + " " + move.getDy() + " is occupied by another Piece!");
            return false;
        }


        // check for push promise
        Move previousMove = game.getMoveLogger().peekMove();
        if (previousMove != null && previousMove.pushPromise) {     // promise to move own piece into place after push
            if (!keepingPromise(move)) { return false; }
        }

        // check if move is push or drag
        if (move.getPiece().getColor() != currentPlayer.getColor()) {
            if (!isDraggedByPiece(move)) {
                return isPushedByPiece(move);
            }
            return true;
        }

        // check if piece itself allows this move
        if (!move.getPiece().isValidMove(move) && !move.pushPromise) {
            Game.logger.log(Level.WARNING, "Rabbit cannot move backwards!");
            return false;
        }

        // check if piece is frozen
        if (isFrozen(move)) {
            Game.logger.log(Level.CONFIG, move.getPiece().toString() + " is frozen!");
            return false;
        }

        return true;
    }

    /**
     * Check for move all trap spots and add to move all pieces that has been killed in this move.
     * @param move that triggers possibility of some piece being trapped
     */
    public void checkTrapped(Move move){

        try {
            if (!isFriendlyAround('c', 3)) {
                Game.logger.log(Level.CONFIG, boardModel.getSpot('c', 3).getPiece().toString() + " is trapped!");
                move.addKilledPiece('c' + Integer.toString(3), boardModel.getSpot('c', 3).getPiece());
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (!isFriendlyAround('c', 6)) {
                Game.logger.log(Level.CONFIG, boardModel.getSpot('c', 6).getPiece().toString() + " is trapped!");
                move.addKilledPiece('c' + Integer.toString(6), boardModel.getSpot('c', 6).getPiece());
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (!isFriendlyAround('f', 3)) {
                Game.logger.log(Level.CONFIG, boardModel.getSpot('f', 3).getPiece().toString() + " is trapped!");
                move.addKilledPiece('f' + Integer.toString(3), boardModel.getSpot('f', 3).getPiece());
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (!isFriendlyAround('f', 6)) {
                Game.logger.log(Level.CONFIG, boardModel.getSpot('f', 6).getPiece().toString() + " is trapped!");
                move.addKilledPiece('f' + Integer.toString(6), boardModel.getSpot('f', 6).getPiece());
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }

    }


    private boolean isFrozen(Move move) {
        return !isFriendlyAround(move.getSx(), move.getSy()) && isStrongerAround(move);
    }


    /**
     * Check if move is not winning move.
     * @param move currently made valid move
     * @return true if move is winning
     */
    public boolean endMove(Move move) {
        if (move.getPiece().getType() == PieceType.RABBIT && move.getPiece().getColor() == GOLD && move.getDy() == 8) {
            game.setGameStatus(GameStatus.GOLD_WIN);
            Game.logger.log(Level.INFO, "Gold player wins!");
            return true;
        }

        if (move.getPiece().getType() == PieceType.RABBIT && move.getPiece().getColor() == ColorPiece.SILVER && move.getDy() == 1) {
            game.setGameStatus(GameStatus.SILVER_WIN);
            Game.logger.log(Level.INFO, "Silver player wins!");
            return true;
        }

        if (hasDeadPieces(GOLD)) {
            game.setGameStatus(GameStatus.SILVER_WIN);
            Game.logger.log(Level.INFO, "All " + GOLD + " pieces are dead!");
            return true;
        }
        if (hasDeadPieces(SILVER)) {
            game.setGameStatus(GameStatus.GOLD_WIN);
            Game.logger.log(Level.INFO, "All " + SILVER + " pieces are dead!");
            return true;
        }

        if (!hasMoves(GOLD)) {
            game.setGameStatus(GameStatus.SILVER_WIN);
            Game.logger.log(Level.INFO, "No moves for " + GOLD + "!");
            return true;
        }

        if (!hasMoves(SILVER)) {
            game.setGameStatus(GameStatus.GOLD_WIN);
            Game.logger.log(Level.INFO, "No moves for " + SILVER + "!");
            return true;
        }


        return false;
    }

    private boolean hasMoves(ColorPiece colorPiece) {
        List<Move> moves = generatePossibleMoves(colorPiece);
        return !moves.isEmpty();

    }

    /**
     * Generate all possible moves for player of certain color.
     * @param colorPiece is color of player
     * @return List of all possible valid moves
     */
    public List<Move> generatePossibleMoves(ColorPiece colorPiece) {
        Player p = null;
        switch (colorPiece) {
            case GOLD -> p = game.getPlayerGold();
            case SILVER -> p = game.getPlayerSilver();
        }

        List<Spot> spots = boardModel.getPieces(colorPiece);
        List<Move> moves = new ArrayList<>();
        for (Spot s : spots) {
            List<Move> moveList = generateValidMoves(s, p);
            moves.addAll(moveList);
        }

        return moves;
    }


    private boolean hasDeadPieces(ColorPiece colorPiece) {
        List<Piece> alivePieces = PieceSet.getPieces(colorPiece).stream()
                .filter(Piece::isAlive).toList();
        return alivePieces.isEmpty();
    }



    /**
     * Check if Piece that is weaker than Piece on spot is next.
     * @param spot on which is Pieces to do check
     * @return true if weaker Piece is around
     */
    public Spot isWeakerAround(Spot spot) {
        try {
            if (isStronger(spot.getPiece(), boardModel.getSpot(spot.getX(), spot.getY()-1).getPiece())) {
                return boardModel.getSpot(spot.getX(), spot.getY()-1); }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (isStronger(spot.getPiece(), boardModel.getSpot(spot.getX(), spot.getY()+1).getPiece())) {
                return boardModel.getSpot(spot.getX(), spot.getY()+1);
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (isStronger( spot.getPiece(), boardModel.getSpot(addX(spot.getX(), 1), spot.getY()).getPiece())) {
                return boardModel.getSpot(addX(spot.getX(), 1), spot.getY());
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (isStronger(spot.getPiece(), boardModel.getSpot(addX(spot.getX(), -1), spot.getY()).getPiece())) {
                return boardModel.getSpot(addX(spot.getX(), -1), spot.getY());
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        return null;
    }


    private boolean isStronger(Piece stronger, Piece weaker) {
        if (stronger == null || weaker == null) {
            return false;
        }
        if (stronger.getColor() != weaker.getColor() && stronger.getPieceStrength() > weaker.getPieceStrength()) {
            return true;
        }
        return false;
    }

    private boolean isStrongerAround(Move move) {
        try {
            if (isStronger(boardModel.getSpot(move.getSx(), move.getSy()-1).getPiece() , move.getPiece())) { return true; }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (isStronger(boardModel.getSpot(move.getSx(), move.getSy()+1).getPiece() , move.getPiece())) { return true; }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (isStronger(boardModel.getSpot(addX(move.getSx(), 1), move.getSy()).getPiece() , move.getPiece())) { return true; }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (isStronger(boardModel.getSpot(addX(move.getSx(), -1), move.getSy()).getPiece() , move.getPiece())) { return true; }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        return false;
    }

    private boolean isFriendlyAround(char x, int y) {
        Piece piece = boardModel.getSpot(x, y).getPiece();
        try {
            if (boardModel.getSpot(x, y-1).getPiece().getColor() == piece.getColor()) {
                return true;
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (boardModel.getSpot(x, y+1).getPiece().getColor() == piece.getColor()) {
                return true;
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (boardModel.getSpot(addX(x, 1), y).getPiece().getColor()  == piece.getColor()) {
                return true;
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        try {
            if (boardModel.getSpot(addX(x, - 1), y).getPiece().getColor()  == piece.getColor()) {
                return true;
            }
        } catch (NullPointerException e) {
            Game.logger.log(Level.FINER, e.getMessage());
        }
        return false;
    }

    private boolean isDraggedByPiece(Move move) {
        Move previousMove = game.getMoveLogger().peekMove();
        if (previousMove == null) {
            return false;
        }
        if (move.getDx() == previousMove.getSx()
                && move.getDy() == previousMove.getSy()
                && isStronger(previousMove.getPiece(), move.getPiece())
                && previousMove.getPlayer() == move.getPlayer()) {
            return true;
        }
        return false;
    }

    private boolean keepingPromise(Move move) {
        Move previousMove = game.getMoveLogger().peekMove();
        return previousMove.getSx() == move.getDx() && previousMove.getSy() == move.getDy();
    }

    private boolean isPushedByPiece(Move move) {
         if (game.moveCnt > game.MAX_MOVES-1) {
            Game.logger.log(Level.WARNING, "Cannot proceed with push, you have " + game.moveCnt + " move left!");
            return false;
        }
        if (isStrongerAround(move)) {
            Game.logger.log(Level.CONFIG,
                    """
                            Created push promise!
                            (In next move you have to drag your piece on spot of enemy piece you dragged before)
                            (your piece has to be stronger than enemy ones)""");
            move.pushPromise = true;
            return true;
        }
        return false;
    }

    /**
     * Get x moved in left or right direction.
     * @param x is char position
     * @param d is direction, 1 == right, -1 == left
     * @return new char left or right from x
     */
    public char addX(char x, int d) {
        return (char)((int)x + d);
    }
}
