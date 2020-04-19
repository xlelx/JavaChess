public class Bishop extends Piece{
    public Bishop(int currentX, int currentY, boolean isWhite){
        super(currentX, currentY, isWhite);
    }
    @Override
    public boolean isValidMove(int toX, int toY){
        int cX = this.getCurrentX();
        int cY = this.getCurrentY();
        if (!super.isValidMove(toX, toY)) return false;

        int diffX, diffY;

        if (Math.abs(toX - cX) == Math.abs(toY - cY)){
            diffX  = (toX - cX > 0) ? 1 : -1 ;
            diffY  = (toY - cY > 0) ? 1 : -1 ;

            int i = cX + diffX;
            int j = cY + diffY;
            for (;i != toX && j != toY;i += diffX, j+= diffY){
                if (!Board.board[i][j].isEmpty()){
                    return false;
                }
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
