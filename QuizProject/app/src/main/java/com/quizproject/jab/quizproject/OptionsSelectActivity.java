package com.quizproject.jab.quizproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class OptionsSelectActivity extends AppCompatActivity implements OnCallCompleted {

    Spinner spnQuestions;
    Spinner spnDifficulty;
    Spinner spnType;
    Spinner spnCategory;

    AsyncRestClientCalls restCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_select);

        setSpinnerValues();

    }

    // sets the values for the spinners
    // the category select spinner gets its data from an async api call
    public void setSpinnerValues() {
        spnQuestions = findViewById(R.id.spnNumberQuestions);
        spnDifficulty = findViewById(R.id.spnDifficulty);
        spnType = findViewById(R.id.spnType);

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
        restCall = new AsyncRestClientCalls(this, this);

        try {
            restCall.getAllCategories();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // gets the quiz with the selected parameters
    public void getQuiz(View view) {

        RequestParams params = new RequestParams();

        String questions;
        String categoryID;
        String difficulty;
        String type;

        // there is always a value for number of questions
        questions = spnQuestions.getSelectedItem().toString();
        params.put("amount", questions);

        // category ID is needed as the parameter
        Category category = (Category) spnCategory.getSelectedItem();
        // if a category is selected
        if (category.getId() != null) {
            categoryID = category.getId();
            params.put("category", categoryID);
        }

        // if a difficulty is selected
        if (!spnDifficulty.getSelectedItem().toString().equals("any")) {
            difficulty = spnDifficulty.getSelectedItem().toString();
            params.put("difficulty", difficulty);
        }

        // if a type is selected
        if (!spnType.getSelectedItem().toString().equals("any")) {
            String selectedType = spnType.getSelectedItem().toString();

            switch (selectedType) {

                case "multiple choice" :
                    type = "multiple";
                    params.put("type", type);
                    break;
                case "true / false" :
                    type = "boolean";
                    params.put("type", type);
                    break;
                default: return;
            }
        }

        restCall.getQuizQuestions(params);
    }

    // method overrides the OnCallCompleted interface, this method gets called when the api call
    // is successful and gets the results as a parameter
    @Override
    public void taskCompleted(JSONArray results) {

        spnCategory = findViewById(R.id.spnCategory);
        ArrayList<Category> categories = new ArrayList<>();

        // default selection, random category
        Category randomCat = new Category();
        randomCat.setName("Random");
        randomCat.setId(null);
        categories.add(randomCat);

        try {
            for (int i = 0; i < results.length(); i++) {

                Category category = new Category();

                JSONObject o = results.getJSONObject(i);

                category.setName(o.getString("name"));
                category.setId(o.getString("id"));

                categories.add(category);
            }
            spnCategory.setAdapter(
                    new ArrayAdapter<Category>(
                            this,
                            android.R.layout.simple_spinner_dropdown_item,
                            categories));
        }
        catch (JSONException e) {
            // handle  the exception
            return;
        }
        // below works but does not get the ID
//        spnCategory = findViewById(R.id.spnCategory);
//        ArrayList<String> categoryNames = new ArrayList<>();
//
//
//        try {
//            for (int i = 0; i < results.length(); i++) {
//                JSONObject o = results.getJSONObject(i);
//                String category = o.getString("name");
//                categoryNames.add(category);
//            }
//            spnCategory.setAdapter(
//                    new ArrayAdapter<String>(
//                            this,
//                            android.R.layout.simple_spinner_dropdown_item,
//                            categoryNames));
//        }
//        catch (JSONException e) {
//            // handle  the exception
//            return;
//        }
    }
}