package com.quizproject.jab.quizproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;

public class FinalResults extends SharedMenu {

    String userEmail;
    String numberOfQuestions;
    String correctAnswers;

    TextView email;
    TextView questions;
    TextView results;

    @Override
    // fetch the passed extras data and display it
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_results);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");
        numberOfQuestions = intent.getStringExtra("questions");
        correctAnswers = intent.getStringExtra("results");

        email = findViewById(R.id.txvFinalResultsUserEmail);
        questions = findViewById(R.id.txvFinalResultsNumberOfQuestions);
        results = findViewById(R.id.txvFinalResultsCorrectAnswers);

        email.setText(userEmail);
        questions.setText(numberOfQuestions);
        results.setText(correctAnswers);

        // start the async email sending task
        new EmailTask().execute();

        // Preferably only send this when the task is completed successfully
        Toast.makeText(FinalResults.this, "We've sent you an email with your results!", Toast.LENGTH_SHORT).show();

    }
    // starts a new quiz for the user
    public void startNewQuiz(View view) {

        Intent intent = new Intent(this, OptionsSelectActivity.class);
        intent.putExtra("userEmail", userEmail);
        startActivity(intent);
    }

    // send an email to the user email with the results
    public void sendResultsEmail() {

        // Probably not a great place to store this info
        // Definitely not a good place.
        final String userName = "Jabquizproj2018@gmail.com";
        final String password = "mobileappproject";
        try {
            GMailSender sender = new GMailSender(userName, password);
            sender.sendMail("Quiz Results",
                    "Number of Questions: " + numberOfQuestions + "\n\nCorrect Answers: " + correctAnswers,
                    userName,
                    userEmail);

        } catch (Exception e) {
            Log.e("SendMailError", e.getMessage(), e);
        }
    }

    private class EmailTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            sendResultsEmail();
            return null;
        }

    }
}



