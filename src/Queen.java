public class Queen extends Piece {
    public Queen(int currentX, int currentY, boolean isWhite){
        super(currentX, currentY, isWhite);
    }

    @Override
    public boolean isValidMove(int toX, int toY) {
        Piece p1 = new Rook(getCurrentX(), getCurrentY(), isWhite());
        Piece p2 = new Bishop(getCurrentX(), getCurrentY(), isWhite());
        return p1.isValidMove(toX, toY) || p2.isValidMove(toX, toY);
    }
}
