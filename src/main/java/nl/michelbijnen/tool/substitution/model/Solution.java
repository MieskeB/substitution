package nl.michelbijnen.tool.substitution.model;

public class Solution {
    private String encoded;
    private String decoded;
    private String key;
    private String contains;

    public Solution(String encoded, String decoded, String key) {
        this.encoded = encoded;
        this.decoded = decoded;
        this.key = key;
        this.contains = "";
    }

    public String getEncoded() {
        return encoded;
    }

    public String getDecoded() {
        return decoded;
    }

    public String getKey() {
        return key;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getContains() {
        return contains;
    }
}
