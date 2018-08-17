package com.quizproject.jab.quizproject;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import android.widget.TextView;


import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Random;


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
        ArrayList<Question> questions = new ArrayList<>();

        try {
            Question question = new Question();
            for (int i = 0; i < results.length(); i++) {

                JSONObject o = results.getJSONObject(i);

                question.setQuestion(o.getString("question"));
                question.setCorrectAnswer(o.getString("correct_answer"));

                //getting the incorrect answers
                JSONArray incorrectAnswers = o.getJSONArray("incorrect_answers");

                ArrayList<String> answers = new ArrayList<>();
                ArrayList<String> answersShuffled = new ArrayList<>();

                for (int j = 0; j < incorrectAnswers.length(); j++) {
                    answers.add(j,incorrectAnswers.getString(j));
                }
                answers.add(question.getCorrectAnswer());
                answersShuffled=answersIndexRandomOrder(answers);
                question.setAnswers(answersShuffled);
                questions.add(question);
                createTable(questions);
            }


        }
        catch (JSONException e) {
            // handle  the exception
            return;
        }
        // Parse the json data here
        Log.d("JSON", results.toString());

    }

    //creates a dynamic table based on the quantity of questions requested by the user
    public void createTable(ArrayList<Question> q){
        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.question_list);
        

        for(int i = 0; i < q.size() ; i++) {
            TextView question = new TextView(this);

            question.setText(q.get(i).getQuestion());
            parentLayout.addView(question);

            ArrayList<String> answers = q.get(i).getAnswers();

            for(int j = 0; j < answers.size(); j++) {
                CheckBox answer = new CheckBox(this);
                answer.setText(answers.get(j));
                answer.setId(i+j);
                parentLayout.addView(answer);
            }

        }
    }

    //shuffle the answers order
    public ArrayList<String> answersIndexRandomOrder(ArrayList<String> a){
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<String> shuffledAnswers = new ArrayList<>();
        Random randomGenerator = new Random();
        while (numbers.size() < a.size()) {

            int random = randomGenerator .nextInt(a.size());
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        for(int j = 0; j < a.size(); j++) {
            shuffledAnswers.add(a.get(numbers.get(j)));
        }
        return shuffledAnswers;
    }

    //create the on click function sendResults
    public void sendResults(View view){}

}
