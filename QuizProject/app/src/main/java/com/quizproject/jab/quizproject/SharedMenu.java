package com.quizproject.jab.quizproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SharedMenu extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent passedIntent = getIntent();
        Intent intent;
        String userEmail = "";
        if (passedIntent.getStringExtra("userEmail") != null) {
            userEmail = passedIntent.getStringExtra("userEmail");
        }

        switch (item.getItemId()) {
            case R.id.menuItemNewQuiz:
                intent = new Intent(this, OptionsSelectActivity.class);
                intent.putExtra("userEmail", userEmail);
                //startActivity(new Intent(this, OptionsSelectActivity.class));
                startActivity(intent);
                return true;
            case R.id.menuItemViewRecentResults:
                intent = new Intent(this, AllResultsActivity.class);
                intent.putExtra("userEmail", userEmail);
                //startActivity(new Intent(this, AllResultsActivity.class));
                startActivity(intent);

                return true;
            case R.id.menuItemSignOut:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("userEmail", userEmail);
                //startActivity(new Intent(this, MainActivity.class));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
