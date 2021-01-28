package game;

public class Char {

    public static final String CLASSID = "Char";
    private final char displayChar;
    //private String displayString = null;

    public Char(char c) {
        displayChar = c;
    }

    //public Char(String s) {
    //    displayString = s;
    //}
    
    public char getChar( ) {
        return displayChar;
    }

    //public String getStr( ) {
    //    return displayString;
    //}
}
