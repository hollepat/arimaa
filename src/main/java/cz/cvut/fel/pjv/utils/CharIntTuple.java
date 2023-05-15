package cz.cvut.fel.pjv.utils;

public class CharIntTuple {
    private final char character;
    private final int integer;

    /**
     * Constructor for CharIntTuple.
     * @param character char
     * @param integer int
     */
    public CharIntTuple(char character, int integer) {
        this.character = character;
        this.integer = integer;
    }

    public char getCharacter() {
        return character;
    }

    public int getInteger() {
        return integer;
    }
}

