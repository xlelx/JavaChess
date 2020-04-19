public class Rook extends Piece {
    public Rook(int currentX, int currentY, boolean isWhite){
        super(currentX, currentY, isWhite);
    }
    @Override
    public boolean isValidMove(int toX, int toY){
        int cX = this.getCurrentX();
        int cY = this.getCurrentY();
        if (!super.isValidMove(toX, toY)) return false;
        int i, j;
        //moving horizontally
        if (cX == toX && cY != toY){
            if (toY > cY) {i = cY; j = toY;}
            else {i = toY; j = cY;}
            for (i = i + 1; i < j; i++){
                if (!Board.board[cX][i].isEmpty()) return false;
            }
            return canCapture(toX, toY);
        }
        //moving vertically
        else if (cX != toX && cY == toY){
            if (toX > cX) {i = cX; j = toX;}
            else {i = toX; j = cX;}
            for (i = i + 1; i < j; i++){
                if (!Board.board[i][cY].isEmpty()) return false;
            }
            return canCapture(toX, toY);
        }
        return false;
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
}
