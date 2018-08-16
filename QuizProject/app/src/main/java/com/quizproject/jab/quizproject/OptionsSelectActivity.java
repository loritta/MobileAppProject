package com.quizproject.jab.quizproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class OptionsSelectActivity extends AppCompatActivity implements OnCallCompleted {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_select);

        setSpinnerValues();


    }

    // sets the values for the spinners
    public void setSpinnerValues() {
        Spinner spnQuestions = findViewById(R.id.spnNumberQuestions);
        Spinner spnDifficulty = findViewById(R.id.spnDifficulty);
        Spinner spnType = findViewById(R.id.spnType);
        //Spinner spnCategory = findViewById(R.id.spnCategory);

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
        AsyncRestClientCalls restCall = new AsyncRestClientCalls(this, this);

        try {
            restCall.getAllCategories();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
//                this, R.array.quizTypeArray, R.layout.support_simple_spinner_dropdown_item);
//        spnCategory.setAdapter(categoryAdapter);
    }

    @Override
    public void taskCompleted(JSONArray results) {

        Spinner spnCategory = findViewById(R.id.spnCategory);
        ArrayList<String> categoryNames = new ArrayList<>();

        try {
            for (int i = 0; i < results.length(); i++) {
                JSONObject o = results.getJSONObject(i);
                String category = o.getString("name");
                categoryNames.add(category);
            }
            spnCategory.setAdapter(
                    new ArrayAdapter<String>(
                            this,
                            android.R.layout.simple_spinner_dropdown_item,
                            categoryNames));
        }
        catch (JSONException e) {
            // handle  the exception
            return;
        }
    }
}
