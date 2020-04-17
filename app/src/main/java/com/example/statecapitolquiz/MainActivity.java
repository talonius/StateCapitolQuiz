package com.example.statecapitolquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

/**
 * The MainActivity for StateCapitolQuiz.  Displays the title screen and queries
 * the user as to how many state capitols they want to be quizzed on.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Fires when the activity is created.  Responsible for asking the user for the
     * number of state capitols they want to be quizzed on.
     *
     * @param savedInstanceState The saved instance data for this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Fires when the Next button is clicked in the MainActivity.  Reads the number of
     * state capitols from the spinner and invokes the QuestionActivity.
     *
     * @param view The parent view from which this function was executed.
     */
    public void activity_main_NextOnClick(@SuppressWarnings("UnusedParameters") View view) {
        Spinner questionCountSpinner = findViewById(R.id.spinner_number_of_questions);

        int questionCount = Integer.parseInt(questionCountSpinner.getSelectedItem().toString());

        // Invoke the QuestionActivity.
        Intent intent = new Intent(this, QuestionCoordinatorActivity.class);
        intent.putExtra("callingActivity", ActivityConstants.MAIN_ACTIVITY);
        intent.putExtra("questionCount", questionCount);

        startActivity(intent);
    }
}
