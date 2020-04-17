package com.example.statecapitolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Presents the user with a summary of their performance.
 */
public class SummaryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent callingIntent = getIntent();

        int questionsCorrect = callingIntent.getIntExtra("questionsCorrect", 0);
        int questionsAsked = callingIntent.getIntExtra("questionsAsked", 0);
        float percentage = (float) questionsCorrect / (float) questionsAsked * 100;
        int percentageDisplay = (int) percentage;

        TextView tv = findViewById(R.id.summary_text);
        String summaryText = getResources().getString(R.string.summary, questionsAsked, questionsCorrect, percentageDisplay);

        // We assemble the summary text separately to make it easier to read and modify.
        tv.setText(summaryText);

        Toast t = Toast.makeText(getApplicationContext(), summaryText, Toast.LENGTH_SHORT);
        t.show();
    }

    /**
     * Overrides the back button so that when we're on the summary screen
     * we return to the MainActivity rather than the last question asked.
     */
    @Override
    public void onBackPressed() {
        // Return to the MainActivity.  Don't bring the QuestionActivity back up.
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("callingActivity", ActivityConstants.SUMMARY_ACTIVITY);
        startActivity(intent);
    }

    /**
     * Handles the clicking of the button which resets the quiz application.
     * Sends the user to the MainActivity to reset the application.
     *
     * @param view The view in which the click occurred.
     */
    public void activity_summary_ResetOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
