package com.example.statecapitolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The EditTextQuestionActivity is responsible for constructing a question using an EditText
 * control, collecting the answer to the question, and return the correctness of that answer
 * to the QuestionCoordinatorActivity.
 */
public class EditTextQuestionActivity extends AppCompatActivity {
    /**
     * This boolean variable is used to track whether we have shown the user whether
     * or not their answer is correct.  It's used to manage the two different phases
     * that we'll find ourselves in while inside this activity.
     */
    private boolean isSecondPhase = false;

    /**
     * Tracking variable to help track whether or not the user answered the question
     * correctly.  (This allows the variable to be passed back to the
     * QuestionCoordinatorActivity.)
     */
    private boolean isAnswerCorrect = false;

    /**
     * Holds the first QuizState object that is the target of the current question.
     */
    private QuizState quizState = null;

    /**
     * Fires when the activity is created.  Responsible for reading the number
     * of questions to be asked from the invoking activity and setting up the
     * first question.
     *
     * @param savedInstanceState The saved instance state to rehydrate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_question);
        quizState = getIntent().getParcelableExtra("quizState");

        SetupQuestion();
    }

    /**
     * Handles setting the enabled state of the button and it's caption.
     *
     * @param text The caption that should appear on the button.
     */
    protected void SetButton(String text) {
        Button b = findViewById(R.id.next_button);
        b.setText(text);
    }

    /**
     * Helper function to handle the common task of setting a TextView's Text property.
     *
     * @param id The ID of the TextView to have the text set.
     * @param text The text to set for the TextView.
     */
    protected void SetTextViewText(int id, String text) {
        TextView tv = findViewById(id);
        tv.setText(text);
    }

    /**
     * SetupQuestion is responsible for picking a QuizState object from
     * the available states (those that have not been used during the current
     * application execution) and the configured number of answers.
     */
    protected void SetupQuestion() {
        Resources res = getResources();

        // Reset the variable tracking whether we've told the user that they're correct.
        isSecondPhase = false;

        // Set the query TextView up to display the question.
        SetTextViewText(R.id.query, res.getString(R.string.query_prefix, quizState.getStateName()));

        SetButton(res.getString(R.string.caption_check_answer));
        SetTextViewText(R.id.answer_state, "");
    }

    /**
     * Checks the current text entered into the EditText control to see if it matches
     * the name of the capitol being asked about.
     *
     * @return True if the value matches the state capitol, false otherwise.
     */
    protected boolean CheckEditTextIsCorrect() {
        EditText et = findViewById(R.id.answer);
        String userAnswer = et.getText().toString();

        return (userAnswer.equalsIgnoreCase(quizState.getCapitolName()));
    }

    /**
     * Handles deciding whether the answer given was correct or not, and the corresponding
     * display of the status.
     */
    protected void HandleSecondPhase() {
        isSecondPhase = true;
        isAnswerCorrect = CheckEditTextIsCorrect();

        int color = isAnswerCorrect ? Color.GREEN : Color.RED;

        Resources res = getResources();
        String statusText = res.getString(isAnswerCorrect ? R.string.status_correct : R.string.status_incorrect);

        TextView status = findViewById(R.id.answer_state);
        status.setTextColor(color);
        status.setText(statusText);

        SetButton(res.getString(R.string.caption_next_question));
    }

    /**
     * This function is called both when the user clicks "Check Answer" and when
     * the user clicks "Next Question".
     *
     * @param view The parent view holding the button that was clicked.
     */
    public void activity_radio_group_question_AnswerOnClick(View view) {
        if (!isSecondPhase) {
            HandleSecondPhase();
        } else {
            Intent intent = new Intent(this, QuestionCoordinatorActivity.class);
            intent.putExtra("callingActivity", ActivityConstants.RADIO_GROUP_QUESTION_ACTIVITY);
            intent.putExtra("questionCorrect", isAnswerCorrect);
            startActivity(intent);
        }
    }
}
