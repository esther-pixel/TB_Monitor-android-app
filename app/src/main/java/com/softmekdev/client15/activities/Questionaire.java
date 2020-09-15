package com.softmekdev.client15.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.softmekdev.client15.R;

public class Questionaire extends AppCompatActivity {

    Button nextButton;
    RadioButton yes_a, yes_b, yes_c, yes_d, yes_e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);
        this.setTitle("Questionaire");

        /*Initialization of views*/
        nextButton = findViewById(R.id.next);
        yes_a = findViewById(R.id.yes_a);
        yes_b = findViewById(R.id.yes_b);
        yes_c = findViewById(R.id.yes_c);
        yes_d = findViewById(R.id.yes_d);
        yes_e = findViewById(R.id.yes_e);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchedDoctor();
            }
        });
    }

    private void matchedDoctor() {
        if (yes_a.isChecked() || yes_b.isChecked() || yes_c.isChecked() || yes_d.isChecked() || yes_e.isChecked()) {
            /*Launch Next QuestionActivity*/
            Intent quest = new Intent(Questionaire.this, Quest.class);
            startActivity(quest);
            // finish();
        } else {
            Intent quest = new Intent(Questionaire.this, Preventive.class);
            startActivity(quest);
            //Toast.makeText(this, "Yet To Achieve", Toast.LENGTH_SHORT).show();
        }
    }

}





































































































































/*
        radioGroupA.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_a) {
                    Toast.makeText(Questionaire.this, "YES ONE CHECKED", Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.no_a) {
                    Toast.makeText(Questionaire.this, "NO ONE CHECKED", Toast.LENGTH_SHORT).show();
                }
            }
        });
        radioGroupB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_b) {
                    Toast.makeText(Questionaire.this, "YES TWO CHECKED", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.no_b) {
                    Toast.makeText(Questionaire.this, "NO TWO CHECKED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGroupC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_c) {
                    Toast.makeText(Questionaire.this, "YES TWO CHECKED", Toast.LENGTH_SHORT).show();
                    matchedDoctor();
                } else if (checkedId == R.id.no_c) {
                    Toast.makeText(Questionaire.this, "NO TWO CHECKED", Toast.LENGTH_SHORT).show();
                }
            }
        });
        radioGroupD.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_d) {
                    Toast.makeText(Questionaire.this, "YES TWO CHECKED", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.no_d) {
                    Toast.makeText(Questionaire.this, "NO TWO CHECKED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGroupE.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yes_e) {
                    Toast.makeText(Questionaire.this, "YES TWO CHECKED", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.no_e) {
                    Toast.makeText(Questionaire.this, "NO TWO CHECKED", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/

