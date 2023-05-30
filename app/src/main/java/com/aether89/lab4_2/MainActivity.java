package com.aether89.lab4_2;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final int gridSize = 3;

    Player playerX = new Player("X","X");
    Player playerO = new Player("O","O");
    Player playerNull = new Player("Null","");

    boolean turn = false;
    boolean win = false;
    String str_btn_ = "btn_";
    int maxTurn = gridSize * gridSize - 1 ;
    int turnTotal = 0;
    String symbolTurn;
    String nameTurn = "";

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String nomX = data.getStringExtra("nomX");
                        String nomO = data.getStringExtra("nomO");

                        if (nomX != null) {
                            playerX.setName(nomX);
                        }
                        if (nomO != null) {
                            playerO.setName(nomO);
                        }

                        statusUpdate();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_layout);

        Button tmpBtn;
        for (int i = 1; i <= gridSize; i++) {
            for (int j = 1; j <= gridSize; j++) {
                tmpBtn = findViewById(getResID(str_btn_, i, j));
                tmpBtn.setOnClickListener(btnOnClickListener);
            }
        }
        Button btnIDPlayers = findViewById(R.id.btn_tictactoe_Players);
        Button btnReset = findViewById(R.id.btn_reset);
        Button btnScore = findViewById(R.id.btn_tictactoeScores);

        btnReset.setOnClickListener(btnOnClickReset);
        btnIDPlayers.setOnClickListener(btnClickIDPlayers);
        btnScore.setOnClickListener(btnClickScores);
        statusUpdate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater monMenu = getMenuInflater();
        monMenu.inflate(R.menu.menu_principal, menu);
        return true; //Doit retourner true pour afficher le menu.
    }

    public void MenuClick(MenuItem menu) {
        int itemId = menu.getItemId();
        if (itemId == R.id.lbl_menuRefresh) {
            onClickReset();
        } else if (itemId == R.id.lbl_menuPlayerID) {
            clickIdPlayers();
        } else if (itemId == R.id.lbl_menuScore) {
            clickScores();
        } else if (itemId == R.id.lbl_menuQuit) {
            finish();
        }
    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;

            if (btn.getText().toString().equals("") && !win) {
                btn.setText(symbolTurn);
                switchTurn();
                verifierGagnant();
            }
        }
    };

    private void onClickReset() {
        Button tmpBtn;
        turnSymbol();
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                tmpBtn = findViewById(getResID("btn_", i, j));
                tmpBtn.setText("");
            }
        }

        turn = false;
        win = false;
        turnTotal = 0;
        statusUpdate();
    }

    View.OnClickListener btnOnClickReset = v -> {
        onClickReset();
    };

    private void clickIdPlayers() {
        Intent intent = new Intent(MainActivity.this, NomJoueurActivity.class);
        mGetContent.launch(intent);
    }

    View.OnClickListener btnClickIDPlayers = v -> {
        clickIdPlayers();
    };

    private void clickScores(){
        Intent intent = new Intent(MainActivity.this, TableauScoreActivity.class);
        intent.putExtra("nomX", playerX.getName());
        intent.putExtra("scoreX", playerX.getScore().toString());
        intent.putExtra("nomO", playerO.getName());
        intent.putExtra("scoreO", playerO.getScore().toString());
        intent.putExtra("scoreN", playerNull.getScore().toString());

        mGetContent.launch(intent);
    }

    View.OnClickListener btnClickScores = v -> {
        clickScores();
    };



    public void turnSymbol() {

        if (turn) {
            symbolTurn = playerO.getSymbol();
            nameTurn = playerO.getName();
        } else {
            symbolTurn = playerX.getSymbol();
            nameTurn = playerX.getName();

        }

    }

    public int compterSymbol(int number1, int number2) {
        Button tmpBtn = findViewById(getResID(str_btn_, number1, number2));
        String buttonText = tmpBtn.getText().toString();
        if (buttonText.equals(playerX.getSymbol())) {
            return 1;
        } else if (buttonText.equals(playerO.getSymbol())) {
            return -1;
        }
        return 0;
    }

    public String checkWinnerName(HashMap<String, Integer> countVictoire) {
        if (countVictoire.containsValue(gridSize)) {
            playerX.addScore();
            return playerX.getName();
        } else {
            playerO.addScore();
            return playerO.getName();
        }
    }

    public void verifierGagnant() {

        if (turnTotal >= gridSize) {
            HashMap<String, Integer> countVictoire = new HashMap<>();
            String winner = "";
            countVictoire.put("Vertical", 0);
            countVictoire.put("Horizontal", 0);

            for (int i = 1; i <= gridSize; i++) {
                for (int j = 1; j <= gridSize; j++) {
                    countVictoire.put("Horizontal", countVictoire.get("Horizontal") + compterSymbol(i, j));
                    countVictoire.put("Vertical", countVictoire.get("Vertical") + compterSymbol(j, i));
                }

                if (((countVictoire.containsValue(gridSize))) || ((countVictoire.containsValue(-gridSize)))) {
                    win = true;
                    winner = checkWinnerName(countVictoire);
                    break;
                }

                countVictoire.put("Vertical", 0);
                countVictoire.put("Horizontal", 0);

            }

            if (!win) {
                int diagonalTopLeftToBottomRight = 1;
                int diagonalBottomLeftToTopRight = gridSize;

                while (diagonalTopLeftToBottomRight <= gridSize) {

                    countVictoire.put("Horizontal", countVictoire.get("Horizontal") + compterSymbol(diagonalTopLeftToBottomRight, diagonalTopLeftToBottomRight));
                    countVictoire.put("Vertical", countVictoire.get("Vertical") + compterSymbol(diagonalBottomLeftToTopRight, diagonalTopLeftToBottomRight));
                    diagonalTopLeftToBottomRight++;
                    diagonalBottomLeftToTopRight--;
                }

                if (((countVictoire.containsValue(gridSize))) || ((countVictoire.containsValue(-gridSize)))) {
                    win = true;
                    winner = checkWinnerName(countVictoire);
                }
            }

            String strStatus;
            TextView status = findViewById(R.id.lbl_status);
            if (win) {
                Toast.makeText(getApplicationContext(), getString(R.string.str_winner, winner),
                        Toast.LENGTH_LONG).show();
                strStatus = getResources().getString(R.string.str_winnerStatus, winner);

                status.setText(strStatus);
            }

            if (turnTotal == maxTurn) {
                Toast.makeText(getApplicationContext(), R.string.str_draw,
                        Toast.LENGTH_LONG).show();
                status.setText(R.string.str_drawStatus);
                playerNull.addScore();
            }
        }

        turnTotal++;

    }

    public void statusUpdate() {
        turnSymbol();
        TextView status = findViewById(R.id.lbl_status);
        String strStatus = getResources().getString(R.string.str_txtStatus) + " " + nameTurn;
        status.setText(strStatus);
    }

    public void switchTurn() {
        turn = !turn;
        statusUpdate();
    }

    public int getResID(String buttonName, int number1, int number2) {
// https://stackoverflow.com/questions/4865244/android-using-findviewbyid-with-a-string-in-a-loop
        return getResources().getIdentifier(buttonName + number1 + number2, "id", getPackageName());
    }
}

