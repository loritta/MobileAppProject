package com.quizproject.jab.quizproject;

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

        switch (item.getItemId()) {
            case R.id.menuItemNewQuiz:
                //startActivity(new Intent(this, About.class));
                return true;
            case R.id.menuItemViewRecentResults:
                //startActivity(new Intent(this, Help.class));
                return true;
            case R.id.menuItemSignOut:
                //startActivity(new Intent(this, Help.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
