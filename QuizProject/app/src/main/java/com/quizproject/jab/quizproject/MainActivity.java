package com.quizproject.jab.quizproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
