package com.quizproject.jab.quizproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONException;

public class OptionsSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_select);

        setSpinnerValues();


    }

    // sets the values for the spinners
    public void setSpinnerValues() {
        Spinner spnQuestions = findViewById(R.id.spnNumberQuestions);
        Spinner spnCategory = findViewById(R.id.spnCategory);
        Spinner spnDifficulty = findViewById(R.id.spnDifficulty);
        Spinner spnType = findViewById(R.id.spnType);

        // get the string array resources values (the hard coded values)

        // Number of Questions
        ArrayAdapter<CharSequence> questionsAdapter = ArrayAdapter.createFromResource(
                this, R.array.numberQuestionsArray, R.layout.support_simple_spinner_dropdown_item);
        spnQuestions.setAdapter(questionsAdapter);

        // Difficulty Setting
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(
                this, R.array.difficultyArray, R.layout.support_simple_spinner_dropdown_item);
        spnDifficulty.setAdapter(difficultyAdapter);

        // Quiz Type
        ArrayAdapter<CharSequence> quizTypeAdapter = ArrayAdapter.createFromResource(
                this, R.array.quizTypeArray, R.layout.support_simple_spinner_dropdown_item);
        spnType.setAdapter(quizTypeAdapter);

        // load all categories from the external API https://opentdb.com/api_category.php
        AsyncRestClientCalls restCall = new AsyncRestClientCalls();

        try {
            String response = restCall.getAllCategories();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this, R.array.quizTypeArray, R.layout.support_simple_spinner_dropdown_item);
        spnCategory.setAdapter(categoryAdapter);
    }
}
