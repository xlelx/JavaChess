public class King extends Piece {
    private boolean isFirstMove = true;
    public King(int currentX, int currentY, boolean isWhite) {
        super(currentX, currentY, isWhite);
    }

    @Override
    public boolean isValidMove(int toX, int toY) {
        int cX = this.getCurrentX();
        int cY = this.getCurrentY();
        if (!super.isValidMove(toX, toY)) return false;

        if (Math.abs(toX - cX)<= 1 && Math.abs(toY - cY) <= 1){
            return this.canCapture(toX, toY);
        }
        else if(toX == cX && this.isFirstMove){
            int i;
            //queenside castle
            if(toY == 2){
                if(Board.board[cX][0].getFirstMove()){
                    for (i = 1; i < 4; i++){
                        if(!Board.board[cX][i].isEmpty()) return false;
                    }
                    Board.board[cX][0].move(cX, 3);
                    return true;
                }
            }
            //kingside castle
            else if (toY == 6){
                if(Board.board[cX][7].getFirstMove()) {
                    for (i = 5; i < 7; i++) {
                        if (!Board.board[cX][i].isEmpty()) return false;
                    }
                    Board.board[cX][7].move(cX, 5);
                    return true;
                }
            }

        }
        return false;
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