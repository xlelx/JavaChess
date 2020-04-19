public class King extends Piece {
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
        return false;
    }
}