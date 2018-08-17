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

public class AllResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_results);

        getAllResults();
    }

    public void getAllResults() {
        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // define the columns to fetch
        // ID and email for now, eventually replace with actual results
        String[] projection = {
                SchemaContract.Users._ID,
                SchemaContract.Users.COLUMN_NAME_EMAIL
        };

        Cursor cursor = db.query(
                SchemaContract.Users.TABLE_NAME,   // The table to query
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

            result.setId(cursor.getString(cursor.getColumnIndexOrThrow(SchemaContract.Users._ID)));
            result.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(SchemaContract.Users.COLUMN_NAME_EMAIL)));
            results.add(result);
        }
        cursor.close();

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
            TextView txvId = new TextView(this);
            txvId.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView txvEmail = new TextView(this);
            txvEmail.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // set the content values and center the views
            txvId.setText(r.getId());
            txvEmail.setText(r.getEmail());
            txvId.setGravity(Gravity.CENTER_HORIZONTAL);
            txvEmail.setGravity(Gravity.CENTER_HORIZONTAL);

            // add the content to the row
            tr.addView(txvId);
            tr.addView(txvEmail);
            // add the row to the layout
            table.addView(
                    tr,
                    new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
        }


    }
}
