
package basics;

public class InitialCastlingEligibility {

    private final boolean whiteKingside;
    private final boolean whiteQueenside;
    private final boolean blackKingside;
    private final boolean blackQueenside;

    public InitialCastlingEligibility(
            boolean whiteKingside,
            boolean whiteQueenside,
            boolean blackKingside,
            boolean blackQueenside) {
        this.whiteKingside = whiteKingside;
        this.whiteQueenside = whiteQueenside;
        this.blackKingside = blackKingside;
        this.blackQueenside = blackQueenside;
    }

    public boolean isWhiteKingsideEligible() {
        return this.whiteKingside;
    }

    public boolean isWhiteQueensideEligible() {
        return this.whiteQueenside;
    }

    public boolean isBlackKingsideEligible() {
        return this.blackKingside;
    }

    public boolean isBlackQueensideEligible() {
        return this.blackQueenside;
    }

    public InitialCastlingEligibility deepCopy() {
        return this;
    }

}
