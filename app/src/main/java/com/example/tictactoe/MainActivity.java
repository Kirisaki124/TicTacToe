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

        if(playerTurn) { // Player x
            ((Button) findViewById(view.getId())).setText("X");
            playerTurn = false;
        }
        else { // Player o
            ((Button) findViewById(view.getId())).setText("O");
            playerTurn = true;
        }

        mapButton(playerTurn, id);

        this.logMap();
    }

    private boolean checkWin(int x, int y){
        
        return true;
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
