public class Pawn extends Piece {
    private boolean isFirstMove = true;
    public Pawn(int currentX, int currentY, boolean isWhite){
        super(currentX, currentY, isWhite);
    }
    @Override
    public boolean isValidMove(int toX, int toY){
        int cX = this.getCurrentX();
        int cY = this.getCurrentY();
        if (!super.isValidMove(toX, toY)) return false;
        int moveCount = toX - cX;
        if ((!this.isWhite() && moveCount < 0) || (this.isWhite() && moveCount > 0)){return false;}

        //pawn is moving forward
        if(toY == cY){
            if(!Board.board[toX][toY].isEmpty()) {
                return false;
            }
            if (Math.abs(moveCount) == 2 && this.isFirstMove){
                int i = isWhite() ? -1 : 1;
                if (Board.board[cX + i][cY].isEmpty()){
                    return true;
                }
                else{
                    return false;
                }
            }
            else if(Math.abs(moveCount) == 1){
                return true;
            }
            else {
                return false;
            }
        }
        //pawn capturing a piece
        else if(Math.abs(toX - cX) == 1 && Math.abs(toY - cY) == 1){
            return canCapture(toX, toY);
        }
        else if (Math.abs(toX - cX) == 1 && Math.abs(toY - cY) == 1 && Board.lastMove[1][0] == cX){
            int lNewX, lNewY, lOldX, lOldY;
            lOldX = Board.lastMove[0][0];
            lNewX = Board.lastMove[1][0];
            lNewY = Board.lastMove[1][1];
            if (Board.board[lNewX][lNewY].getClass().getName().equals("Pawn")){
                if (Math.abs(lOldX - lNewX) == 2){
                    return canCapture(toX, toY);
                }
            }
            return false;
        }
        else{return false;}
    }
    @Override
    public void setFirstMove() {
        this.isFirstMove = !this.isFirstMove;
    }
    @Override
    public boolean getFirstMove() {
        return this.isFirstMove;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
