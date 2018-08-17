package com.quizproject.jab.quizproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // testing below
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        //testData();
    }

    // Comment here
    public void startQuiz(View view) {

        EditText edtEmail = findViewById(R.id.edtEmail);
        String email = edtEmail.getText().toString();

        if (email.equals("")) {
            // give a warning here that field cant be empty
            Log.e("Error", "Email is required");
        }
        // optionally validate if its a proper email with regex
        // here
        else {
            // start options select activity
            Intent intent = new Intent(this, OptionsSelectActivity.class);
            // pass the email as it might be needed
            intent.putExtra("userEmail", email);
            startActivity(intent);
        }
    }

    public void viewResults(View view) {
        Intent intent = new Intent(this, AllResultsActivity.class);
        startActivity(intent);
    }

    public void testData() {
        DbHelper dbHelper = new DbHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SchemaContract.Results.COLUMN_NAME_USER_EMAIL, "user@email.com");
        values.put(SchemaContract.Results.COLUMN_NAME_QUIZ_DIFFICULTY, "Hard");
        values.put(SchemaContract.Results.COLUMN_NAME_QUIZ_RESULTS, "5/10");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SchemaContract.Results.TABLE_NAME, null, values);
    }
}
