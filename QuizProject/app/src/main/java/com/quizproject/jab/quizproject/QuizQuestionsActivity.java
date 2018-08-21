package com.quizproject.jab.quizproject;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class QuizQuestionsActivity extends SharedMenu implements OnCallCompleted {

    AsyncRestClientCalls restCall;
    ArrayList<RadioGroup> radioGroupList;
    String userEmail;
    String numberOfQuestions;
    int correctAnswers;
    ArrayList<Integer> checkedButtonsId;
    ArrayList<Question> questions;
    ArrayList<Integer> savedCheckedButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questions);

        // if there is a saved instance
        if (savedInstanceState != null) {

            // get the user email
            userEmail = savedInstanceState.getString("userEmail");
            // get number of questions
            numberOfQuestions = savedInstanceState.getString("numberOfQuestions");
            // get the list of questions
            questions = savedInstanceState.getParcelableArrayList("questions");
            // create the table from the questions
            createTable(questions);
            // get the list of ids for checked buttons
            savedCheckedButtons = savedInstanceState.getIntegerArrayList("checkedButtons");

            // method below gets called with the onAttachedToWindow() callback
            //checkButtons();

            // enable the send results button (disabled by default)
            Button b = findViewById(R.id.btnSendResults);
            b.setEnabled(true);
        }
        // if its a new instance
        else {
            // initialize the intent and the params object
            Intent intent = getIntent();
            // Fetch the user email from the extra
            userEmail = intent.getStringExtra("userEmail");

            // Get the user option selections and make api call with the params
            RequestParams params = new RequestParams();

            // number of questions should never be null
            params.put("amount", intent.getStringExtra("numberOfQuestions"));

            if (intent.getStringExtra("categoryID") != null) {
                params.put("category", intent.getStringExtra("categoryID"));
            }
            if (intent.getStringExtra(
                    "difficulty") != null) {
                params.put("difficulty", intent.getStringExtra("difficulty"));
            }
            if (intent.getStringExtra("type") != null) {
                params.put("type", intent.getStringExtra("type"));
            }

            restCall = new AsyncRestClientCalls(this, this);
            restCall.getQuizQuestions(params);
        }

    }

    // Now I dont believe this callback is necessary (more testing is needed)
    // however there was issues with checkboxes not being checked properly from the onCreate callback
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        checkButtons();
    }

    // Checks the buttons from the saved instance checkedButtonIds list
    public void checkButtons() {

        // if the array isnt initilized yet (new instance)
        // (just a quick fix)
        if (savedCheckedButtons == null) return;

        // make sure its not empty (in case no buttons were checked)
        if (!savedCheckedButtons.isEmpty()) {

            // The savedCheckedButtons array is "parallel" to the radioGroupList array
            // both arrays have the same length
            // So for each radio groups, if there is a buttonId at the same index
            // in the savedCheckButtons array, check the button with that id.

            // loop through the radio button groups
            for (int i = 0; i < radioGroupList.size(); i++) {
                int checkedId = savedCheckedButtons.get(i);
                RadioGroup g = radioGroupList.get(i);

                // if checkedId is -1 then no radio buttons were selected
                if (checkedId != -1) {
                    g.check(checkedId);
                }
            }

        }
    }

    // Called after API call success
    @Override
    public void taskCompleted(JSONArray results) {

        try {

            // get the number of questions to save to the database records later
            numberOfQuestions = String.valueOf(results.length());

            questions = new ArrayList<>();

            // loop through the results
            for (int i = 0; i < results.length(); i++) {
                Question question = new Question();
                JSONObject o = results.getJSONObject(i);

                //transforming the json html encoding to a regular string
                Spanned sQuestion = Html.fromHtml(o.getString("question"));
                String tempQuestion=sQuestion.toString();
                Spanned sCorrectAnswers = Html.fromHtml(o.getString("correct_answer"));
                String tempCorAnswer=sCorrectAnswers.toString();

                //saving to the question object the values needed later
                question.setQuestion(tempQuestion);
                question.setCorrectAnswer(tempCorAnswer);

                //getting the incorrect answers
                JSONArray incorrectAnswers = o.getJSONArray("incorrect_answers");

                ArrayList<String> answers = new ArrayList<>();
                ArrayList<String> answersShuffled;

                for (int j = 0; j < incorrectAnswers.length(); j++) {
                    Spanned sIncorrectAnswers = Html.fromHtml(incorrectAnswers.getString(j));
                    String tempIncorrect=sIncorrectAnswers.toString();
                    answers.add(j,tempIncorrect);
                }
                answers.add(question.getCorrectAnswer());
                answersShuffled=answersIndexRandomOrder(answers);
                question.setAnswers(answersShuffled);
                questions.add(question);

            }
            // Create the table and enable the send results button
            createTable(questions);
            Button btnSend = findViewById(R.id.btnSendResults);
            btnSend.setEnabled(true);
        }
        catch (JSONException e) {
            // very informative log
            Log.e("JSONError", "JSON Error");
            return;
        }
    }

    //creates a dynamic table based on the number of questions requested by the user
    public void createTable(ArrayList<Question> q){

     LinearLayout parentLayout = (LinearLayout) findViewById(R.id.question_list);
     radioGroupList = new ArrayList<>();

        // Question is a custom object holding the parsed JSON questions data
        // for each questions, create a textview to render them
        for(Question quest : q) {

            TextView question = new TextView(this);

            question.setText(quest.getQuestion());
            parentLayout.addView(question);

            ArrayList<String> answers = quest.getAnswers();
            int counter=0;

            RadioGroup group = new RadioGroup(this);


            for(String ans : answers) {
                RadioButton answer = new RadioButton(this);

                answer.setText(ans);

                // the tag is used later to verify the user's answers
                if (ans.equals(quest.getCorrectAnswer())) {
                    answer.setTag("correct");
                }
                group.addView(answer);
                counter++;
                answer.setId(10+counter);
            }
            parentLayout.addView(group);
            radioGroupList.add(group);
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

    // calculate the final results, save the data to the database and call the show results activity
    public void sendResults(View view){

        correctAnswers = 0;
        // check if all questions have answers
        for (RadioGroup g : radioGroupList) {
            if (g.getCheckedRadioButtonId() == -1) {
                // warn the user all questions have to be answered
                Toast.makeText(this, R.string.tstWarningAllQuestions, Toast.LENGTH_LONG ).show();
                return;
            }
            else {
                int buttonId = g.getCheckedRadioButtonId();
                RadioButton btn = findViewById(buttonId);
                //Object o = btn.getTag();
                if (btn.getTag() != null && btn.getTag().equals("correct")) {
                    correctAnswers++;
                }
            }
        }
        // parse to string for simplicity
        String correctAnswersString = Integer.toString(correctAnswers);
        // save the results to the database and call the final activity
        saveResultsToDb();
        Intent intent = new Intent(this, FinalResults.class);
        // pass the 3 variables to the new intent
        intent.putExtra("userEmail", userEmail);
        intent.putExtra("questions", numberOfQuestions);
        intent.putExtra("results", correctAnswersString);
        startActivity(intent);
    }


    public void saveResultsToDb() {
        DbHelper dbHelper = new DbHelper(this);
        String TAG = "Row of written values";

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SchemaContract.Results.COLUMN_NAME_USER_EMAIL, userEmail);
        values.put(SchemaContract.Results.COLUMN_NAME_QUIZ_QUESTIONS, numberOfQuestions);
        values.put(SchemaContract.Results.COLUMN_NAME_QUIZ_RESULTS, correctAnswers);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SchemaContract.Results.TABLE_NAME, null, values);
//        Log.i(TAG, Long.toString(newRowId));
    }

    // to avoid losing user selections and fetched questions
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the email and numberOfQuestions
        outState.putString("userEmail", userEmail);
        outState.putString("numberOfQuestions", numberOfQuestions);
        // the Question class was made to implement Parcelable, to be able to save it in the bundle.
        outState.putParcelableArrayList("questions", questions);


        // list of all the checked button ids
        checkedButtonsId = new ArrayList<Integer>();

        for (RadioGroup g : radioGroupList) {
            // if a button is checked add it to the list of ids
            if (g.getCheckedRadioButtonId() != -1) {
                checkedButtonsId.add(g.getCheckedRadioButtonId());
            }
            else {
                // if no buttons are checked, add -1 (to keep the arrays the same length)
                checkedButtonsId.add(-1);
            }
        }

        outState.putIntegerArrayList("checkedButtons", checkedButtonsId);
    }
}
