package cz.cvut.fel.pjv.pieces;

import java.util.*;

public class PieceSet {

    private static Map<ColorPiece, Map<PieceType, List<Piece>>> pieces;

    private static final PieceSet pieceSetInstance = new PieceSet();

    /**
     * Constructor for PieceSet, creating all Pieces.
     */
    private PieceSet() {
        init();
    }

    /**
     * Getter.
     * @param color is GOLD or SILVER
     * @param pieceType is some animal
     * @return Return Pieces of color and pieceType. E.g. gold rabbits.
     */
    public static List<Piece> getPieces(ColorPiece color, PieceType pieceType) {
        return pieces.get(color).get(pieceType);
    }

    /**
     * Get all pieces of color.
     * @param color of pieces to get
     * @return list of pieces
     */
    public static List<Piece> getPieces(ColorPiece color) {
        List<Piece> list = new ArrayList<>();
        Map<PieceType, List<Piece>> tmp = pieces.get(color);
        for (Map.Entry<PieceType, List<Piece>> entry : tmp.entrySet()) {
            list.addAll(entry.getValue());
        }

        return list;
    }

    private static void init() {
        pieces = new LinkedHashMap<>();

        Map<PieceType, List<Piece>> goldPieces = new LinkedHashMap<>();
        Map<PieceType, List<Piece>> silverPieces = new LinkedHashMap<>();

        List<Piece> silverRabbits = new ArrayList<>();
        List<Piece> goldRabbits = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            silverRabbits.add(new Rabbit(ColorPiece.SILVER));
            goldRabbits.add(new Rabbit(ColorPiece.GOLD));
        }

        List<Piece> silverElephant = new ArrayList<>();
        List<Piece> goldElephant = new ArrayList<>();
        silverElephant.add(new Elephant(ColorPiece.SILVER));
        goldElephant.add(new Elephant(ColorPiece.GOLD));

        List<Piece> silverCamel = new ArrayList<>();
        List<Piece> goldCamel = new ArrayList<>();
        silverCamel.add(new Camel(ColorPiece.SILVER));
        goldCamel.add(new Camel(ColorPiece.GOLD));

        List<Piece> silverCat = new ArrayList<>();
        List<Piece> goldCat = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            silverCat.add(new Cat(ColorPiece.SILVER));
            goldCat.add(new Cat(ColorPiece.GOLD));
        }

        List<Piece> silverDog = new ArrayList<>();
        List<Piece> goldDog = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            silverDog.add(new Dog(ColorPiece.SILVER));
            goldDog.add(new Dog(ColorPiece.GOLD));
        }

        List<Piece> silverHorse = new ArrayList<>();
        List<Piece> goldHorse = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            silverHorse.add(new Horse(ColorPiece.SILVER));
            goldHorse.add(new Horse(ColorPiece.GOLD));
        }

        silverPieces.put(PieceType.RABBIT, silverRabbits);
        silverPieces.put(PieceType.CAT, silverCat);
        silverPieces.put(PieceType.DOG, silverDog);
        silverPieces.put(PieceType.HORSE, silverHorse);
        silverPieces.put(PieceType.CAMEL, silverCamel);
        silverPieces.put(PieceType.ELEPHANT, silverElephant);

        goldPieces.put(PieceType.RABBIT, goldRabbits);
        goldPieces.put(PieceType.CAT, goldCat);
        goldPieces.put(PieceType.DOG, goldDog);
        goldPieces.put(PieceType.HORSE, goldHorse);
        goldPieces.put(PieceType.CAMEL, goldCamel);
        goldPieces.put(PieceType.ELEPHANT, goldElephant);


        pieces.put(ColorPiece.SILVER, silverPieces);
        pieces.put(ColorPiece.GOLD, goldPieces);

    }
}
