package com.quizproject.jab.quizproject;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;


public class QuizQuestionsActivity extends AppCompatActivity implements OnCallCompleted {

    AsyncRestClientCalls restCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

    }

    @Override
    public void taskCompleted(JSONArray results) {

    }
}
