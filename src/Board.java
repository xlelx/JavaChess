import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    public final static int BOARD_SIZE = 8;
    public static Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
    public static ArrayList<Piece> whitePieces = new ArrayList<Piece>();
    public static ArrayList<Piece> blackPieces = new ArrayList<Piece>();
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
        while (!input.equals("stop")){
            if(kingInCheck(this.whiteTurn)) System.out.println("Your king is in check!");

            System.out.println("Select a piece: ");
            input = scanner.nextLine().toLowerCase();
            int[] parsed = parseInput(input);
            cX = parsed[0];
            cY = parsed[1];

            Piece p = board[cX][cY];
            if (p.isWhite() != this.getWhiteTurn()){
                System.out.println("Invalid piece. Select again.");
                continue;
            }

            System.out.println("Enter move: ");
            input = scanner.nextLine().toLowerCase();
            if (input.equals("castle kingside")){
                toX = cX;
                toY = 6;
            }
            else if (input.equals("castle queenside")){
                toX = cX;
                toY = 2;
            }
            else{
                parsed = parseInput(input);
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
            if (p.isValidMove(king.getCurrentX(), king.getCurrentY())) return true;
        }
        return false;
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
        char x, y;
        y = input.charAt(0);
        x = input.charAt(1);
        int[] res = new int[2];
        res[0] = 7 - (x - '1');
        res[1] = y - 'a';
        return res;
    }
    public static boolean getWhiteTurn() {
        return whiteTurn;
    }
}
