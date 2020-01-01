package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Kinda dumb here :) setVisible 0 at the start
        findViewById(R.id.mainGame).setVisibility(View.VISIBLE);
        findViewById(R.id.mainGame).animate().alpha(0).setDuration(0);


    }

    private String game[][];
    private boolean playerTurn = true;
    // True = x
    // false = o
    int gameSize;

    private boolean isShowing = false;
    public void onClickStartBtn(View view){
        Log.i("Info", "Start button");
        if(!isShowing) {
//           Start game
            findViewById(R.id.mainGame).animate().alpha(1).setDuration(500);

            isShowing = true;
            this.createGame();
        }
        else {
//            Restart game
            findViewById(R.id.mainGame).animate().alpha(0).setDuration(500);
            isShowing = false;
        }
    }

    public void logMap(){
        Log.i("Log map", "map");
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                Log.i("Position:" + i + "/" + j,game[i][j]);
            }
        }
    }

    public void mapButton(boolean playerTurn, int id){
        int count = 0;
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if(count == id) {
                    if(playerTurn) game[i][j] = "X";
                    else game[i][j] = "O";
                    count++;
                }
                else count++;
            }
        }
    }

    public void buttonClick(View view){
        int id = view.getId();
        boolean isWin = false;

        mapButton(playerTurn, id);
        if(playerTurn) { // Player X
            ((Button) findViewById(view.getId())).setText("X");
            isWin = checkWin(gameSize, "X", id);
            playerTurn = false;
        }
        else { // Player O
            ((Button) findViewById(view.getId())).setText("O");
            isWin = checkWin(gameSize,"O", id);
            playerTurn = true;
        }


        this.logMap();
        if(isWin) {
            if (!playerTurn) Toast.makeText(getApplicationContext(), "X win", Toast.LENGTH_LONG).show();
            else Toast.makeText(getApplicationContext(), "O win", Toast.LENGTH_LONG).show();
        }
    }
    private int getBtnX(int btnId) {
        int count = 0;
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if (count == btnId) return i;
                count++;
            }
        }
        return 0;
    }

    private int getBtnY(int btnId) {
        int count = 0;
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if (count == btnId) return j;
                count++;
            }
        }
        return 0;
    }

    private boolean checkWinRow(int gameSize, String playerTurn) {

        for (int j = 0; j < gameSize; j++) {
            boolean win = true;
            for (int i = 0; i < gameSize; i++) {
                if (!game[j][i].equalsIgnoreCase(playerTurn)) {
                    win = false;
                }
            }
            if (win) return win;
        }
        return false;
    }

    private boolean checkWinColumn(int gameSize, String playerTurn) {
        for (int j = 0; j < gameSize; j++) {
            boolean win = true;
            for (int i = 0; i < gameSize; i++) {
                if (!game[i][j].equalsIgnoreCase(playerTurn)) {
                    win = false;
                }
            }
            if (win) return win;

        }
        return false;
    }

//    private boolean checkWinDiagonalLeftToRight(int gameSize, int x, int y){
//        boolean win = true;
//        for (int i = 0; i < gameSize; i++) {
//            try {
//                if (!game[x][y].equalsIgnoreCase(game[x + i][y + i])) {
//                    win = false;
//                }
//            }catch (Exception e) {
//                continue;
//            }
//        }
//        if (win) {
//            return win;
//        }
//
//        return false;
//    }
//
//    private boolean checkWinDiagonalRightToLeft(int gameSize, int x, int y){
//        boolean win = true;
//        for (int i = gameSize; i < 0; i--) {
//            try {
//                if (!game[x][y].equalsIgnoreCase(game[x + i][y + i])) {
//                    win = false;
//                }
//            }catch (Exception e) {
//                continue;
//            }
//        }
//        if (win) {
//            return win;
//        }
//
//        return false;
//    }

    private boolean checkWin(int gameSize, String playerTurn, int btnId){

//        Check row
        if(this.checkWinRow(gameSize, playerTurn)){
            return true;
        }
//        Check column
        if(this.checkWinColumn(gameSize, playerTurn)){
            return true;
        }

//        int x = getBtnX(btnId);
//        int y = getBtnY(btnId);

////        Check diagonal left to right
//        if(this.checkWinDiagonalLeftToRight(gameSize, x, y)) {
//            return true;
//        }
//
////        Check diagonal right to left
//        if(this.checkWinDiagonalRightToLeft(gameSize, x, y)) {
//            return true;
//        }
        return false;
    }

    private void createGame() {

        gameSize = Integer.parseInt(((EditText)findViewById(R.id.gameSize)).getText().toString());
        ((TableLayout) findViewById(R.id.mainGame)).removeAllViews();

//        Create new game
        int countId = 0;

        int width = (findViewById(R.id.mainGame)).getWidth();

        game = new String[gameSize][gameSize];
        for (int i = 0; i < gameSize; i++) {
            TableRow row = new TableRow(this);

            for (int j = 0; j < gameSize; j++) {
                Button btn = new Button(this);
                btn.setWidth(width/gameSize);
                btn.setHeight(width/gameSize);
                btn.setId(countId++);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonClick(v);
                    }
                });

//                Add to view
                row.addView(btn);
            }

            ((TableLayout) findViewById(R.id.mainGame)).addView(row);


        }

        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                game[i][j] = "-";
            }
        }
    }
}
