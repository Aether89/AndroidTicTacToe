package com.aether89.lab4_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NomJoueurActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nom_joueur);

        Button btnSave = findViewById(R.id.btn_playersNameSave);
        btnSave.setOnClickListener(btnClickSave);

    }
    View.OnClickListener btnClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;

            Intent resultIntent = new Intent();
            EditText nomX = findViewById(R.id.txtinput_PlayerX);
            EditText nomO = findViewById(R.id.txtinput_PlayerO);
            resultIntent.putExtra("nomX", nomX.getText().toString());
            resultIntent.putExtra("nomO", nomO.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        }


    };

}
