import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    public final static int BOARD_SIZE = 8;
    public static Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
    public static ArrayList<Piece> whitePieces = new ArrayList<Piece>();
    public static ArrayList<Piece> blackPieces = new ArrayList<Piece>();
    public static Piece lastCaptured;

    public static int[][] lastMove = new int[2][2];
    private static boolean whiteTurn = true;
    public Board(){
    }

    //intialize pieces
    public void initPieces(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Piece(i,j, true);
            }
        }
        for (int j = 0; j < BOARD_SIZE; j++) {
            board[1][j] = new Pawn(1, j, false);
            board[6][j] = new Pawn(6, j, true);
        }
        boolean w = false;
        for (int j = 0; j < BOARD_SIZE; j+= 7){
            board[j][0] = new Rook(j,0, w);
            board[j][7] = new Rook(j,7, w);
            board[j][2] = new Bishop(j,2, w);
            board[j][5] = new Bishop(j,5, w);
            board[j][1] = new Knight(j,1, w);
            board[j][6] = new Knight(j,6, w);
            board[j][3] = new Queen(j, 3, w);
            board[j][4] = new King(j, 4, w);
            w = true;
        }
    }
    public void startGame(){
        this.initPieces();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        int cX, cY, toX, toY;
        drawBoard();
        while (true){
            if(kingInCheck(this.whiteTurn)){
                System.out.println("Your king is in check!");
                if (isCheckmate(this.whiteTurn)){
                    System.out.println((this.whiteTurn ? "Black": "White")+" wins!");
                    break;
                }
            }

            System.out.println("Select a piece: ");
            input = scanner.nextLine().toLowerCase();
            int[] parsed = parseInput(input);
            if (parsed.length == 1){
                if(input.equals("show")){
                    drawBoard();
                    continue;
                }
                else if (invalidInput(input)){
                    continue;
                }
                else{
                    break;
                }
            }
            cX = parsed[0];
            cY = parsed[1];

            Piece p = board[cX][cY];
            if (p.isWhite() != this.getWhiteTurn()){
                System.out.println("Invalid piece. Select again.");
                continue;
            }

            System.out.println("Enter move: ");
            input = scanner.nextLine().toLowerCase();

            if (input.equals("castle kingside")) {
                toX = cX;
                toY = 6;
            }
            else if (input.equals("castle queenside")){
                toX = cX;
                toY = 2;
            }
            else{
                parsed = parseInput(input);
                if (parsed.length == 1) {
                    if (invalidInput(input)) {
                        continue;
                    } else {
                        break;
                    }
                }
                toX = parsed[0];
                toY = parsed[1];
            }

            //Move Piece
            if(!p.move(toX, toY)) {
                System.out.println("The move is invalid!");
                continue;
            }
            //Check if king is in check after moving
            System.out.println("The move is valid!");
            registerBoard();
            storeLastMove(cX, cY, toX, toY);
            drawBoard();

            this.whiteTurn = !this.whiteTurn;

        }
        System.out.println("Game has Ended.");
    }
    public void registerBoard(){
        whitePieces.clear();
        blackPieces.clear();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Piece p = board[i][j];
                if (p.getClass().getName().equals("Piece")) continue;
                else if (p.isWhite()) whitePieces.add(p);
                else blackPieces.add(p);
            }
        }
    }
    public boolean invalidInput(String input){
        if(input.equals("stop")){
            System.out.println("Terminating game...");
            return false;
        }
        else if(input.contains("castle")){
            System.out.println("Please select your king before castling. Specify either\"kingside\" or \"queenside\"" +
                    " Example: castle kingside ");
        }
        else{
            System.out.println("Invalid input. Please enter a coordinate in the form of \"A1\"");
        }
        return true;
    }
    public static boolean kingInCheck(boolean isWhite){
        ArrayList<Piece> list, kList;
        Piece king = board[0][0];
        if (!isWhite) {
            list = whitePieces;
            kList = blackPieces;
        }
        else {
            list = blackPieces;
            kList = whitePieces;
        }
        for (Piece p: kList){
            if (p.getClass().getName().equals("King")){
                king = p;
                break;
            }
        }
        for (Piece p: list) {
            if (p.isValidMove(king.getCurrentX(), king.getCurrentY())) {
                return true;
            }
        }
        return false;
    }
    public boolean kingInCheckAfterMove(Piece p,int toX, int toY, boolean isWhite){
        boolean ans;
        if (p.move(toX, toY)){
            ans = kingInCheck(isWhite);
            p.reverse_move();
        }
        else return true;
        return ans;
    }
    public void drawBoard(){
        System.out.print("|    || ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(String.format("%2s | ", (char) ('A' + i)));
        }
        System.out.println("");
        System.out.println("----------------------------------------------");
        for (int i = 0; i < BOARD_SIZE; i++){
            System.out.print(String.format("| %2d || ", BOARD_SIZE-i));
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(String.format("%2s | ", board[i][j].toString()));
            }
            System.out.println("");
        }
    }
    public int[] parseInput(String input){
        try {
            char x, y;
            y = input.charAt(0);
            x = input.charAt(1);

            if((x < '1' || x > '8') || (y < 'a' || y > 'h')) throw new Exception("error");

            int[] res = new int[2];
            res[0] = 7 - (x - '1');
            res[1] = y - 'a';
            return res;
        }
        catch(Exception e){
            return new int[1];
        }
    }
    public boolean isCheckmate(boolean isWhite){
        ArrayList<Piece> list = whitePieces;
        if (!isWhite) list = blackPieces;
        for (Piece p: list){
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if(kingInCheckAfterMove(p, i, j, isWhite)){ //Need to fix code
                        return false;
                    }
                }
            }
        }
        System.out.println("Checkmate!");
        return true;
    }
    public void storeLastMove(int cX, int cY, int toX, int toY){
        int[] v1 = new int[2];
        v1[0] = cX;
        v1[1] = cY;
        int[] v2 = new int[2];
        v2[0] = toX;
        v2[1] = toY;
        lastMove[0] = v1;
        lastMove[1] = v2;
    }
    public static boolean getWhiteTurn() {
        return whiteTurn;
    }
}
