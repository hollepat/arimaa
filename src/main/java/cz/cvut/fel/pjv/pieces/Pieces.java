package cz.cvut.fel.pjv.pieces;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Pieces {

    private static Map<ColorPiece, Map<Type, List<Piece>>> pieces;

    private static Pieces piecesInstance = new Pieces();


    private Pieces() {
        init();
    }

    public static List<Piece> getPieces(ColorPiece color, Type type) {
        return pieces.get(color).get(type);
    }

    private static void init() {
        pieces = new LinkedHashMap<ColorPiece, Map<Type, List<Piece>>>();

        Map<Type, List<Piece>> goldPieces = new LinkedHashMap<Type, List<Piece>>();
        Map<Type, List<Piece>> silverPieces = new LinkedHashMap<Type, List<Piece>>();

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

        silverPieces.put(Type.RABBIT, silverRabbits);
        silverPieces.put(Type.CAT, silverCat);
        silverPieces.put(Type.DOG, silverDog);
        silverPieces.put(Type.HORSE, silverHorse);
        silverPieces.put(Type.CAMEL, silverCamel);
        silverPieces.put(Type.ELEPHANT, silverElephant);

        goldPieces.put(Type.RABBIT, goldRabbits);
        goldPieces.put(Type.CAT, goldCat);
        goldPieces.put(Type.DOG, goldDog);
        goldPieces.put(Type.HORSE, goldHorse);
        goldPieces.put(Type.CAMEL, goldCamel);
        goldPieces.put(Type.ELEPHANT, goldElephant);


        pieces.put(ColorPiece.SILVER, silverPieces);
        pieces.put(ColorPiece.GOLD, goldPieces);

    }
}
