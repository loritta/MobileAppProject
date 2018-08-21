package com.quizproject.jab.quizproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AllResultsActivity extends SharedMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_results);

        getAllResults();
    }

    //This function gets all the results from the previous quizzes from the database
    public void getAllResults() {
        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // define the columns to fetch
        String[] projection = {
                SchemaContract.Results.COLUMN_NAME_USER_EMAIL,
                SchemaContract.Results.COLUMN_NAME_QUIZ_QUESTIONS,
                SchemaContract.Results.COLUMN_NAME_QUIZ_RESULTS
        };

        Cursor cursor = db.query(
                SchemaContract.Results.TABLE_NAME,   // The table to query
                projection,                         // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );


        ArrayList<Result> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            Result result = new Result();

            //result.setId(cursor.getString(cursor.getColumnIndexOrThrow(SchemaContract.Users._ID)));
            result.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(SchemaContract.Results.COLUMN_NAME_USER_EMAIL)));
            result.setNumberOfQuestions(cursor.getString(cursor.getColumnIndexOrThrow(SchemaContract.Results.COLUMN_NAME_QUIZ_QUESTIONS)));
            result.setResults(cursor.getString(cursor.getColumnIndexOrThrow(SchemaContract.Results.COLUMN_NAME_QUIZ_RESULTS)));
            results.add(result);
        }
        cursor.close();

        createTable(results);



    }

    //create the layout to display the data
    public  void createTable(ArrayList<Result> results){
        // create the table layout from the fetched data
        TableLayout table = findViewById(R.id.resultsTable);

        // foreach results create a new row and add to the table to display it
        for (Result r : results) {
            // create a new row
            TableRow tr = new TableRow(this);
            // set the layout params
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // create the content of the table
            TextView txvUserEmail = new TextView(this);
            txvUserEmail.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView txvNumberOfQuestions = new TextView(this);
            txvNumberOfQuestions.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView txvQuizResults = new TextView(this);
            txvQuizResults.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // set the content values and center the views
            txvUserEmail.setText(r.getEmail());
            txvNumberOfQuestions.setText(r.getNumberOfQuestions());
            txvQuizResults.setText(r.getResults());

            txvUserEmail.setGravity(Gravity.CENTER_HORIZONTAL);
            txvNumberOfQuestions.setGravity(Gravity.CENTER_HORIZONTAL);
            txvQuizResults.setGravity(Gravity.CENTER_HORIZONTAL);

            // add the content to the row
            tr.addView(txvUserEmail);
            tr.addView(txvNumberOfQuestions);
            tr.addView(txvQuizResults);
            // add the row to the layout
            table.addView(
                    tr,
                    new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
