public class Piece {
    private int currentX;
    private int currentY;
    private boolean isWhite;

    public Piece(int currentX,int currentY, boolean isWhite){
        this.currentX = currentX;
        this.currentY = currentY;
        this.isWhite = isWhite;
    }
    public boolean isValidMove(int toX, int toY){
        if ((toX < 0 || toX >= Board.BOARD_SIZE)||(toY < 0 || toY >= Board.BOARD_SIZE)||(toX == this.getCurrentX() && toY == this.getCurrentY())){
            return false;
        }
        return true;
    }

    public boolean canCapture(int toX, int toY){
        if(Board.board[toX][toY].isEmpty() || Board.board[toX][toY].isWhite() != this.isWhite()){
            return true;
        }
        return false;
    }
    public final boolean move(int toX, int toY){
        boolean valid = this.isValidMove(toX, toY);
        if (!valid){
            return false;
        }
        Board.board[currentX][currentY] = new Piece(currentX, currentY, true);
        this.currentX = toX;
        this.currentY = toY;
        Board.board[currentX][currentY] = this;
        return true;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
    public void setFirstMove(){
        return;
    }
    public boolean getFirstMove(){
        return false;
    }
    public boolean isEmpty(){
        return true;
    }
    @Override
    public String toString() {
        if (this.getClass().getName().equals("Piece")) return "-";
        if (this.getClass().getName().equals("Knight")) return "N" + (this.isWhite() ? "w" : "b");
        return this.getClass().getName().substring(0,1).toUpperCase() + (this.isWhite() ? "w" : "b");
    }
}
