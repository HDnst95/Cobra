package de.cobra;

        /**
         * The EloRating class provides a method to calculate the new Elo rating 
         * for a player based on the player's old rating, the opponent's rating, 
         * and the outcome of the match.
         */
        public class EloRating {
            private static final int K = 32;

            /**
             * Calculates the new Elo rating.
             * 
             * @param oldRating The player's current rating.
             * @param opponentRating The opponent's rating.
             * @param isWinner Whether the player won the match.
             * @return The player's new rating.
             */
            public static int calculateNewRating(int oldRating, int opponentRating, boolean isWinner) {
                double expectedScore = 1.0 / (1.0 + Math.pow(10, (opponentRating - oldRating) / 400.0));
                int score = isWinner ? 1 : 0;
                return (int) (oldRating + K * (score - expectedScore));
            }
        }