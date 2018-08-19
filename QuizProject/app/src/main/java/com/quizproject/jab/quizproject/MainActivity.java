package com.quizproject.jab.quizproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.regex.Pattern;

import okhttp3.OkHttpClient;

//import com.facebook.stetho.Stetho;
//import com.facebook.stetho.okhttp3.StethoInterceptor;

//import okhttp3.OkHttpClient;

public class MainActivity extends SharedMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // testing below
//

    }

    // simple function to validate email
    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    // Validate the email and start the next activity
    public void startQuiz(View view) {

        EditText edtEmail = findViewById(R.id.edtEmail);
        String email = edtEmail.getText().toString();

        if (email.equals("")) {
            Toast.makeText(this, "Email is Required", Toast.LENGTH_SHORT).show();
        }
        else if (isValidEmail(email) == false) {
            Toast.makeText(this, "Invalid Email Entered", Toast.LENGTH_SHORT).show();
        }
        // email validated
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
        values.put(SchemaContract.Results.COLUMN_NAME_QUIZ_QUESTIONS, "10");
        values.put(SchemaContract.Results.COLUMN_NAME_QUIZ_RESULTS, "5/10");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(SchemaContract.Results.TABLE_NAME, null, values);
    }
}
