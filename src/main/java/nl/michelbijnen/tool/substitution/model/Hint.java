package nl.michelbijnen.tool.substitution.model;

public class Hint {
    private char from;
    private char to;

    public Hint(char encoded, char decoded) {
        this.from = encoded;
        this.to = decoded;
    }

    public char getFrom() {
        return from;
    }

    public char getTo() {
        return to;
    }

    public int getFromLocation() {
        // Starting at 0, so a=0, b=1, c=2....
        return (int) from - 97;
    }

    public int getToLocation() {
        return (int) to - 97;
    }
}
