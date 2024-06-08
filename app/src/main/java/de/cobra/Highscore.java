package de.cobra;

public class Highscore {
    private String player;
    private int score;

    public Highscore(String player, int score) {
        this.player = player;
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }
}
