package uk.co.deveroonie.hyvotejava.models;

public class Vote {
    public String playerName;    // Player username
    public String voteSite;      // Voting site identifier
    public long timestamp;       // Unix timestamp
    public String nonce;         // Replay protection nonce
}