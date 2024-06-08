package de.cobra;

public class EloRating {

    private static final int K = 32; // K-Faktor für die Berechnung des Elo-Ratings

    public static int calculateNewRating(int currentRating, int opponentRating, boolean hasWon) {
        double expectedScore = 1 / (1 + Math.pow(10, (opponentRating - currentRating) / 400.0));
        int actualScore = hasWon ? 1 : 0;
        return (int) (currentRating + K * (actualScore - expectedScore));
    }
}
