package com.example.xavi.multiquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MultiQuizActivity extends AppCompatActivity {

    private int ids_answer[] = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };
    private int correct_answer;
    private int current_question;
    private String[] all_questions;
    private TextView text_question;
    private boolean answer_is_correct[];
    private RadioGroup group;
    private Button btn_next;
    private Button btn_prev;
    private int[] btn_answer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_quiz);

        text_question = (TextView) findViewById(R.id.text_question);
        group = (RadioGroup) findViewById(R.id.group_answer);
        btn_next = (Button) findViewById(R.id.btn_check);
        btn_prev = (Button) findViewById(R.id.btn_prev);

        all_questions = getResources().getStringArray(R.array.all_questions);
        answer_is_correct = new boolean[all_questions.length];
        btn_answer = new  int[all_questions.length];
        for (int i=0; i < btn_answer.length; i++){
            btn_answer[i] =-1;
        }
        current_question = 0;
        show_question();


         btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();

                if (current_question < all_questions.length-1) {
                    current_question++;
                    show_question();
                }
                else {
                    int correctas =0 , incorrectas =0;
                    for(boolean b : answer_is_correct){
                        if(b) correctas++;
                        else  incorrectas++;
                    }
                    String resultado =
                            String.format("Correctas: %d -- Incorrectas: %d", correctas, incorrectas);
                    Toast.makeText(MultiQuizActivity.this, resultado, Toast.LENGTH_LONG).show();
                    finish();
                }
                }



        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
                if (current_question > 0){
                    current_question--;
                    show_question();
                }
            }
        });

    }

    private void checkAnswer() {
        int id = group.getCheckedRadioButtonId();
        int answer = -1;
        for (int i = 0; i < ids_answer.length; i++) {
            if (ids_answer[i] == id) {
                answer = i;
            }
        }

        answer_is_correct[current_question] = (answer == correct_answer);
        btn_answer[current_question] = answer;
    }

    private void show_question() {
        String q = all_questions[current_question];
        String[] parts = q.split(";");


        group.clearCheck();
        text_question.setText(parts[0]);

        for (int i = 0; i < ids_answer.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answer[i]);
            String answer = parts[i + 1];
            if (answer.charAt(0) == '*') {
                correct_answer = i;
                answer = answer.substring(1);
            }
            rb.setText(answer);
            if (btn_answer[current_question] == i){
                rb.setChecked(true);
            }

        }
        if (current_question==0){
            btn_prev.setVisibility(View.INVISIBLE);
        }
        else btn_prev.setVisibility(View.VISIBLE);

        if (current_question == all_questions.length-1){
             btn_next.setText(R.string.Finish);
        }
        else btn_next.setText(R.string.next);
    }
}