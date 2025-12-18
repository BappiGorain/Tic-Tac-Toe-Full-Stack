package com.tictactoe.controller;

import com.tictactoe.service.TicTacToeGame;
import com.tictactoe.service.TicTacToeGame.Move;
import com.tictactoe.service.TicTacToeGame.EvalResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class PageController {

    static char[][] board = new char[3][3];

    @GetMapping("/tictactoe")
    public String game(Model model){

        for(int r=0;r<3;r++)
            for(int c=0;c<3;c++)
                board[r][c] = TicTacToeGame.EMPTY;

        model.addAttribute("board",board);
        return "tictactoe";
    }

    @PostMapping("/move")
    @ResponseBody
    public Map<String,Object> move(@RequestParam int row,
                                   @RequestParam int col){

        Map<String,Object> response = new HashMap<>();

        board[row][col] = TicTacToeGame.PLAYER_O;

        EvalResult eval = TicTacToeGame.evaluate(board);

        if(eval.score == -10){
            response.put("winner","Player");
            response.put("winningLine",eval.line);
            response.put("board",board);
            return response;
        }

        if(TicTacToeGame.isMoveLeft(board)){

            Move aiMove = TicTacToeGame.findBestMove(board);

            board[aiMove.row][aiMove.col] = TicTacToeGame.PLAYER_X;

            eval = TicTacToeGame.evaluate(board);

            if(eval.score == 10){
                response.put("winner","AI");
                response.put("winningLine",eval.line);
                response.put("board",board);
                return response;
            }
        }

        if(!TicTacToeGame.isMoveLeft(board)){
            response.put("winner","Draw");
        }

        response.put("board",board);
        return response;
    }
}
