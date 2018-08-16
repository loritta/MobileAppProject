package com.quizproject.jab.quizproject;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;


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

    public void getListOfQuestions(){
        ArrayList<Question> q = new ArrayList<>();
        createTable(q);

    }


    //creates a dynamic table based on the quantity of questions requested by the user
    public void createTable(ArrayList<Question> q){
        Serializable quizInfo = getIntent().getSerializableExtra("quizInfo");

        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.question_container);

        for(int i = 0; i < q.size() ; i++) {
            TextView question = new TextView(this);

            question.setText(q.get(i).getQuestion());
            parentLayout.addView(question);
            int answersQuantity = q.get(i).getAnswers().length;
            for(int j = 0; j < answersQuantity; j++) {
                CheckBox answer = new CheckBox(this);
                answer.setText(q.get(i).getAnswers()[j]);
                answer.setId(i+j);
                parentLayout.addView(answer);
            }

        }
    }

}
