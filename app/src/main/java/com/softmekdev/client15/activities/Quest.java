package com.softmekdev.client15.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.softmekdev.client15.R;

public class Quest extends AppCompatActivity {

    Button prevButton, submit;
    RadioButton yes_f, yes_g, yes_h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        this.setTitle("Questionaire");

        /*View Initialization*/
        prevButton = findViewById(R.id.previous);
        submit = findViewById(R.id.submit_answers);
        yes_f = findViewById(R.id.yes_f);
        yes_g = findViewById(R.id.yes_g);
        yes_h = findViewById(R.id.yes_h);


        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Quest.this, Questionaire.class);
                startActivity(intent);
                //finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yes_f.isChecked() || yes_g.isChecked() || yes_h.isChecked()) {
                    /*Launch Matching Doctors*/
                    Intent submitted = new Intent(Quest.this, MatchDoc.class);
                    startActivity(submitted);
                } else {
                    /*Launch Preventive Activity*/
                    Intent preventive = new Intent(Quest.this, Preventive.class);
                    startActivity(preventive);
                }

            }
        });
    }

}
