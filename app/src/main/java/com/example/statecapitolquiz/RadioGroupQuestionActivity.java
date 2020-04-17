package com.example.statecapitolquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Activity which takes care of presenting a query to the user.
 */
public class RadioGroupQuestionActivity extends AppCompatActivity {
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
     * Holds the QuizState object that is the target of the current question.
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
        setContentView(R.layout.activity_radio_group_question);
        quizState = getIntent().getParcelableExtra("quizState");

        SetupQuestion();
    }

    /**
     * Handles setting the enabled state of the button and it's caption.
     *
     * @param enabled Whether or not the button should be enabled.
     * @param text The caption that should appear on the button.
     */
    protected void SetButton(boolean enabled, String text) {
        Button b = findViewById(R.id.next_button);
        b.setEnabled(enabled);
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
        RadioGroup rg = findViewById(R.id.answers);

        // This event handler keeps the Check Answer button disabled until a selection is made.
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            SetButton(true, getResources().getString(R.string.caption_check_answer));
            }
        });

        // Reset the variable tracking whether we've told the user that they're correct.
        isSecondPhase = false;

        // Set the query TextView up to display the question.
        SetTextViewText(R.id.query, res.getString(R.string.query_prefix, quizState.getStateName()));

        SetupRadioButtons();
        SetButton(false, res.getString(R.string.caption_check_answer));
        SetTextViewText(R.id.answer_state, "");
    }

    /**
     * Sets up the child RadioButtons representing the answers.
     */
    protected void SetupRadioButtons() {
        ArrayList<QuizState> availableAnswers = new ArrayList<>(QuizState.GetQuizStates());
        QuizState.RemoveFromArrayList(availableAnswers, quizState);

        ArrayList<QuizState> answers = new ArrayList<>();
        answers.add(quizState);

        // Randomly add incorrect answers.
        int NUMBER_OF_ANSWERS = 5;
        while(answers.size() != NUMBER_OF_ANSWERS) {
            answers.add(QuizState.SelectRandomAndRemove(availableAnswers));
        }

        Collections.shuffle(answers);

        RadioGroup rg = findViewById(R.id.answers);
        rg.removeAllViews();

        // The size of the answers ArrayList controls how many RadioButtons are instantiated.
        for(QuizState qs : answers) {
            RadioButton rb = new RadioButton(this);
            rb.setText(qs.getCapitolName());
            rg.addView(rb);
        }
    }

    /**
     * Handles highlighting the correct answer in green.
     */
    private void HighlightCorrectAnswer() {
        RadioGroup rg = findViewById(R.id.answers);

        // This next chunk of code highlights the correct answer in green.
        int childCount = rg.getChildCount();
        for(int i = 0; i < childCount; i++) {
            View v = rg.getChildAt(i);

            if (v instanceof RadioButton) {
                RadioButton vrb = (RadioButton) v;
                if (vrb.getText() == quizState.getCapitolName()) {
                    vrb.setTextColor(Color.GREEN);
                }
            }
        }
    }

    /**
     * Handles deciding whether the answer given was correct or not, and the corresponding
     * display of the status.
     */
    protected void HandleSecondPhase() {
        isSecondPhase = true;
        HighlightCorrectAnswer();

        RadioGroup rg = findViewById(R.id.answers);
        RadioButton rb = findViewById(rg.getCheckedRadioButtonId());

        isAnswerCorrect = (rb.getText() == quizState.getCapitolName());
        int color = isAnswerCorrect ? Color.GREEN : Color.RED;

        Resources res = getResources();
        String statusText = res.getString(isAnswerCorrect ? R.string.status_correct : R.string.status_incorrect);

        rb.setTextColor(color);

        TextView status = findViewById(R.id.answer_state);
        status.setTextColor(color);
        status.setText(statusText);

        SetButton(true, res.getString(R.string.caption_next_question));
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
