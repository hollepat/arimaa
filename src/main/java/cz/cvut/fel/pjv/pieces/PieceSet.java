package cz.cvut.fel.pjv.pieces;

import java.util.*;

public class PieceSet {

    private static Map<ColorPiece, Map<PieceType, List<Piece>>> pieces;

    private static PieceSet pieceSetInstance = new PieceSet();

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

    public static List<Piece> getPieces(ColorPiece color) {
        List<Piece> list = new ArrayList<>();
        Map<PieceType, List<Piece>> tmp = pieces.get(color);
        for (Map.Entry<PieceType, List<Piece>> entry : tmp.entrySet()) {
            for (Piece p : entry.getValue()) {
                list.add(p);
            }
        }

        return list;
    }

    private static void init() {
        pieces = new LinkedHashMap<ColorPiece, Map<PieceType, List<Piece>>>();

        Map<PieceType, List<Piece>> goldPieces = new LinkedHashMap<PieceType, List<Piece>>();
        Map<PieceType, List<Piece>> silverPieces = new LinkedHashMap<PieceType, List<Piece>>();

        List<Piece> silverRabbits = new ArrayList<Piece>();
        List<Piece> goldRabbits = new ArrayList<Piece>();
        for (int i = 0; i < 8; i++) {
            silverRabbits.add(new Rabbit(ColorPiece.SILVER));
            goldRabbits.add(new Rabbit(ColorPiece.GOLD));
        }

        List<Piece> silverElephant = new ArrayList<Piece>();
        List<Piece> goldElephant = new ArrayList<Piece>();
        silverElephant.add(new Elephant(ColorPiece.SILVER));
        goldElephant.add(new Elephant(ColorPiece.GOLD));

        List<Piece> silverCamel = new ArrayList<Piece>();
        List<Piece> goldCamel = new ArrayList<Piece>();
        silverCamel.add(new Camel(ColorPiece.SILVER));
        goldCamel.add(new Camel(ColorPiece.GOLD));

        List<Piece> silverCat = new ArrayList<Piece>();
        List<Piece> goldCat = new ArrayList<Piece>();
        for (int i = 0; i < 2; i++) {
            silverCat.add(new Cat(ColorPiece.SILVER));
            goldCat.add(new Cat(ColorPiece.GOLD));
        }

        List<Piece> silverDog = new ArrayList<Piece>();
        List<Piece> goldDog = new ArrayList<Piece>();
        for (int i = 0; i < 2; i++) {
            silverDog.add(new Dog(ColorPiece.SILVER));
            goldDog.add(new Dog(ColorPiece.GOLD));
        }

        List<Piece> silverHorse = new ArrayList<Piece>();
        List<Piece> goldHorse = new ArrayList<Piece>();
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
