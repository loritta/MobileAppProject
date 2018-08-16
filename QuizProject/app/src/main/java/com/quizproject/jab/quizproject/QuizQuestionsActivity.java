package com.quizproject.jab.quizproject;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;


public class QuizQuestionsActivity extends AppCompatActivity implements OnCallCompleted {

    AsyncRestClientCalls restCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        // initialize the intent and the params object
        Intent intent = getIntent();
        RequestParams params = new RequestParams();

        // number of questions should never be null
        params.put("amount", intent.getStringExtra("numberOfQuestions"));

        if (intent.getStringExtra("categoryID") != null) {
            params.put("category", intent.getStringExtra("categoryID"));
        }
        if (intent.getStringExtra("difficulty") != null) {
            params.put("difficulty", intent.getStringExtra("difficulty"));
        }
        if (intent.getStringExtra("type") != null) {
            params.put("type", intent.getStringExtra("type"));
        }

        restCall = new AsyncRestClientCalls(this, this);
        restCall.getQuizQuestions(params);
    }

    @Override
    public void taskCompleted(JSONArray results) {

        // Parse the json data here
        Log.d("JSON", results.toString());
    }
}
