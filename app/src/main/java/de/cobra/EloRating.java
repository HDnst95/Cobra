package de.cobra;

public class EloRating {
    private static final int K = 32;

    public static int calculateNewRating(int oldRating, int opponentRating, boolean isWinner) {
        double expectedScore = 1.0 / (1.0 + Math.pow(10, (opponentRating - oldRating) / 400.0));
        int score = isWinner ? 1 : 0;
        return (int) (oldRating + K * (score - expectedScore));
    }
}
