package com.aether89.lab4_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TableauScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableau_score);

        TextView playerXName = findViewById(R.id.lbl_ScorePlayerXName);
        TextView playerOName = findViewById(R.id.lbl_ScorePlayerOName);
        TextView playerNullName = findViewById(R.id.lbl_ScorePlayerNullName);
        TextView playerXScore = findViewById(R.id.lbl_ScorePlayerXScore);
        TextView playerOScore = findViewById(R.id.lbl_ScorePlayerOScore);
        TextView playerNullScore = findViewById(R.id.lbl_ScorePlayerNullScore);

        playerXName.setText(getIntent().getStringExtra("nomX"));
        playerXScore.setText(getIntent().getStringExtra("scoreX"));

        playerOName.setText(getIntent().getStringExtra("nomO"));
        playerOScore.setText(getIntent().getStringExtra("scoreO"));

        playerNullName.setText(getResources().getString(R.string.str_namePlayerNull));
        playerNullScore.setText(getIntent().getStringExtra("scoreN"));

        Button btnScoreReturn = findViewById(R.id.btn_scoreReturn);
        btnScoreReturn.setOnClickListener(btnClickScoreReturn);
    }
    View.OnClickListener btnClickScoreReturn = v -> finish();
    
}