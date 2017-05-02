package com.example.sails.cornersgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class BoardActivity extends AppCompatActivity {

    GridView gridView;
    Button btnNext, btnPrev;
    TextView textViewMessage;
    TextView player1Chance;
    TextView player2Chance;
    int position;
    Game game;

    int selectedPos = -1;
    int positionToMove;
    int whoseTurn = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        init();
    }

    private void init() {

        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        textViewMessage = (TextView) findViewById(R.id.textViewMessage);
        player1Chance = (TextView) findViewById(R.id.textViewPlayer1Chance);
        player2Chance = (TextView) findViewById(R.id.textViewPlayer2Chance);

        gridView = (GridView) findViewById(R.id.gridViewBoard);
        gridView.setNumColumns(8);

        game = new Game();
        game.showBoard();

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game = new Game();
                updateBoard();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.computeAndMakeMove(whoseTurn);
                System.out.println(whoseTurn);
                toggleTurn();
                updateBoard();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("position = " + i);
                System.out.println("selectedPos = " + selectedPos);
                System.out.println(game.getPossibleMovesFromCell(selectedPos));
                position = i;

                if (selectedPos >= 0 && game.getPossibleMovesFromCell(selectedPos).contains(i)) {

                    System.out.println("selectedPos >=0 " + selectedPos);
                    if (game.makeMove(selectedPos, i)) {
                        selectedPos = -1;
                        game.toggleTurn();
                        toggleTurn();
                    }
                    else System.out.println("CANNOT MAKE THIS MOVE ( " +selectedPos + " -> " + i);
                }

                if (selectedPos < 0 && game.cellHasFigureIn(i, whoseTurn)) {
                    selectedPos = i;
                }

                updateBoard();
            }
        });

        updateBoard();
    }

    private void updateBoard() {
        System.out.println("update");
        BoardAdapter boardAdapter = new BoardAdapter(getApplicationContext(), position, game);
        gridView.setAdapter(boardAdapter);


        if (game.checkWinner() == 3) {
            textViewMessage.setText("No winner");
        } else if (game.checkWinner() == 2) {
            textViewMessage.setText("Black won");
        } else if (game.checkWinner() == 1) {
            textViewMessage.setText("White won");
        } else if (game.checkWinner() == 0) {
            textViewMessage.setText(" ");
        }

        player1Chance.setText(game.findPlayersMark(1) + "");
        player2Chance.setText(game.findPlayersMark(2) + "");

    }


    private void toggleTurn() {
        if (whoseTurn == 1)
            whoseTurn = 2;
        else whoseTurn = 1;
    }


}
