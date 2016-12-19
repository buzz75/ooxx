package com.test.jef.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private boolean player = true; // RED = true ;; YELLOW = false
    private boolean gameLock = false;
    private Button reset;
    private int[] P1 = {};
    private int[] P2 = {};
    private int[][] winCondition = {{0,1,2}, {3,4,5}, {6,7,8},
                                    {0,3,6}, {1,4,7}, {2,5,8},
                                    {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridLayout OXlayout= (GridLayout)findViewById(R.id.oxgrid);
        int count = OXlayout.getChildCount();

        TextView gameDialog = (TextView) findViewById(R.id.gamestate);
        gameDialog.setText("GAME START");
        gameDialog.setTextSize(20);
        Log.e("JEF", "check grid count = " + count);
        //Set Reset BTN
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clear all game data
                P1 = new int[0];
                P2 = new int[0];
                player = true;
                for(int i=0; i< OXlayout.getChildCount(); i++) {
                    ((ImageView) OXlayout.getChildAt(i)).setImageResource(0);
                }
                TextView gameDialog = (TextView) findViewById(R.id.gamestate);
                gameDialog.setText("GAME START");
                gameDialog.setTextSize(20);
                gameLock = false;
            }

        });
    }
    public void getLocation(View view) {
        ImageView clickItem = (ImageView) view;
        int clickPosition = Integer.parseInt(clickItem.getTag().toString());
        int[] pos = new int[2];
        clickItem.getLocationInWindow(pos);
        Log.e("JEF","check Position" + Arrays.toString(pos));
        clickItem.setTranslationY(-1000f);
        if (!gameLock) {
            if (player) {
                if (contains(P1, clickPosition) ||  contains(P2, clickPosition)) {
                    Log.e("RED", "Already tap location " + clickPosition);
                } else {
                    P1 = addElement(P1, clickPosition);
                    clickItem.setImageResource(R.drawable.red);
                    clickItem.animate().translationYBy(1000f).rotation(0).setDuration(300);
                    Log.e("RED", "Add location, list all RED location " + Arrays.toString(P1));
                    player = false;
                    //Log.e("RED", "red state " + checkGameState(P1));
                    if (checkGameState(P1))
                        showGameState(true);
                }
            } else {
                if (contains(P1, clickPosition) || contains(P2, clickPosition)) {
                    Log.e("YELLOW", "Already tap location " + clickPosition);
                } else {
                    P2 = addElement(P2, clickPosition);
                    clickItem.setImageResource(R.drawable.yellow);
                    Log.e("YELLOW", "Add location, list all YELLOW location " + Arrays.toString(P2));
                    clickItem.animate().translationYBy(1000f).rotation(0).setDuration(300);
                    player = true;
                    //Log.e("YELLOW", "yellow state " + checkGameState(P2));
                    if (checkGameState(P2))
                        showGameState(true);
                }

            }
        }
    }
    private int[] addElement(int[] a, int e) {
        a = Arrays.copyOf(a, a.length + 1);
        a[a.length -1] = e;
        return a;
    }
    private boolean contains(int[] a, int e) {
        int index = Arrays.binarySearch(a, e);
        return index >= 0;
    }
    private boolean checkGameState(int[] a) {
        //sort first
        Arrays.sort(a);
        Log.e("JEF", "check a array " + Arrays.toString(a));
        if (a.length < 3) {
         return false;
        }
        for(int[] winItem: winCondition) {
            if (a.length >= 3) {
                if ( contains(a, winItem[0]) && contains(a,winItem[1]) && contains(a, winItem[2])) {
                    Log.e("CONTAINS", "MATCH!!!!!");
                    return true;
                }
                /*
                for(int i=0; i <= a.length-3; i++) {
                    int [] sub_a = Arrays.copyOfRange(a, 0+i, 3+i);
                    Log.e("SUB_A", "List SUB_A " + Arrays.toString(sub_a));
                    if (Arrays.equals(sub_a,winItem)) {
                        Log.e("SUB_A","MATCH !!!");
                        return true;
                    }
                 }
                 */
            }
        }
        if (P1.length + P2.length == 9) {
             showGameState(false);
        }
        return false;
    }
    //true = Win;
    private void showGameState(boolean r) {
        TextView gameText = (TextView) findViewById(R.id.gamestate);
        if (r) {
            gameText.setText(player ? "YELLOW" : "RED");
            gameText.append("\tWIN !!!!!");
            gameText.setTextSize(20);
            gameLock = true;
        } else {
            gameText.setText("DRAW");
            gameText.setTextSize(20);

        }
    }

}
