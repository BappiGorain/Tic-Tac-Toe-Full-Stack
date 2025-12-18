package com.tictactoe.service;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeGame {

    public static final char PLAYER_X = 'X';  // AI
    public static final char PLAYER_O = 'O';  // Human
    public static final char EMPTY = '_';     // blank

    public static class Move {
        public int row;
        public int col;
    }

    public static class EvalResult {
        public int score;             // -10, 10, 0
        public List<String> line;     // coords of winning line
    }

    public static EvalResult evaluate(char[][] board){
        return evaluateBoard(board);
    }

    private static EvalResult evaluateBoard(char[][] board) {

        EvalResult res = new EvalResult();
        res.line = new ArrayList<>();

        // check rows
        for(int r=0;r<3;r++){
            if(board[r][0] != EMPTY &&
               board[r][0] == board[r][1] &&
               board[r][1] == board[r][2]){

                res.score = board[r][0] == PLAYER_X ? 10 : -10;
                res.line.add(r+"0");
                res.line.add(r+"1");
                res.line.add(r+"2");
                return res;
            }
        }

        // columns
        for(int c=0;c<3;c++){
            if(board[0][c] != EMPTY &&
               board[0][c] == board[1][c] &&
               board[1][c] == board[2][c]){

                res.score = board[0][c] == PLAYER_X ? 10 : -10;
                res.line.add("0"+c);
                res.line.add("1"+c);
                res.line.add("2"+c);
                return res;
            }
        }

        // diagonal
        if(board[0][0] != EMPTY &&
           board[0][0] == board[1][1] &&
           board[1][1] == board[2][2]){

            res.score = board[0][0] == PLAYER_X ? 10 : -10;
            res.line.add("00");
            res.line.add("11");
            res.line.add("22");
            return res;
        }

        // anti diag
        if(board[0][2] != EMPTY &&
           board[0][2] == board[1][1] &&
           board[1][1] == board[2][0]){

            res.score = board[0][2] == PLAYER_X ? 10 : -10;
            res.line.add("02");
            res.line.add("11");
            res.line.add("20");
            return res;
        }

        res.score = 0;
        return res;
    }


    public static boolean isMoveLeft(char[][] board){
        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                if(board[r][c] == EMPTY) return true;
            }
        }
        return false;
    }


    public static int minimax(char[][] board,int depth,boolean isMax){

        int score = evaluateBoard(board).score;

        if(score == 10) return score - depth;
        if(score == -10) return score + depth;
        if(!isMoveLeft(board)) return 0;

        if(isMax){ // AI turn
            int best = Integer.MIN_VALUE;

            for(int r=0;r<3;r++){
                for(int c=0;c<3;c++){
                    if(board[r][c] == EMPTY){
                        board[r][c] = PLAYER_X;
                        best = Math.max(best,minimax(board,depth+1,false));
                        board[r][c] = EMPTY;
                    }
                }
            }
            return best;
        }
        else{ // player turn
            int best = Integer.MAX_VALUE;

            for(int r=0;r<3;r++){
                for(int c=0;c<3;c++){
                    if(board[r][c] == EMPTY){
                        board[r][c] = PLAYER_O;
                        best = Math.min(best,minimax(board,depth+1,true));
                        board[r][c] = EMPTY;
                    }
                }
            }
            return best;
        }
    }


    public static Move findBestMove(char[][] board){

        Move bestMove = new Move();
        int bestVal = Integer.MIN_VALUE;

        bestMove.row = -1;
        bestMove.col = -1;

        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                if(board[r][c] == EMPTY){

                    board[r][c] = PLAYER_X;

                    int moveVal = minimax(board,0,false);

                    board[r][c] = EMPTY;

                    if(moveVal > bestVal){
                        bestVal = moveVal;
                        bestMove.row = r;
                        bestMove.col = c;
                    }
                }
            }
        }
        return bestMove;
    }
}
