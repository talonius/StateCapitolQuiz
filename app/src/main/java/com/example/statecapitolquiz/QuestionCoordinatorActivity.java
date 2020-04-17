package com.example.statecapitolquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Random;

public class QuestionCoordinatorActivity extends AppCompatActivity {
    /**
     * Used for random number generation.
     */
    private static Random random = new Random();

    /**
     * This array list holds all of the QuizStates which have not yet been used.
     */
    private static ArrayList<QuizState> availableQuestions = new ArrayList<>();

    /**
     * Holds the total number of questions to be asked.  This is defined by the user
     * on the first screen.
     */
    private static int questionsToBeAsked = 0;

    /**
     * Holds the number of questions that have been asked.  Incremented each time a
     * new question is setup.
     */
    private static int questionsAsked = 0;

    /**
     * The number of questions the user has answered correctly.  Passed to the
     * SummaryActivity for display.s
     */
    private static int questionsCorrect = 0;

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
        setContentView(R.layout.activity_question_coordinator);

        Intent intent = getIntent();
        ActivityConstants callingActivity = (ActivityConstants) intent.getSerializableExtra("callingActivity");

        // If we came from MainActivity reset the AvailableQuestions ArrayList, pick out a
        // QuizState to represent the current question, and invoke one of the question types
        // to display the question.
        if (callingActivity == ActivityConstants.MAIN_ACTIVITY) {
            HandleMainActivityOrigin();
        }

        if (callingActivity == ActivityConstants.RADIO_GROUP_QUESTION_ACTIVITY ||
            callingActivity == ActivityConstants.TEXT_ENTRY_QUESTION_ACTIVITY ||
            callingActivity == ActivityConstants.CHECK_BOX_QUESTION_ACTIVITY)
        {
            HandleQuestionActivityOrigin();
        }
    }

    /**
     * Handles being invoked by the MainActivity.  Sets the number of questions to
     * be answered and then sets up a question to be asked.
     */
    protected void HandleMainActivityOrigin() {
        Intent intent = getIntent();

        questionsToBeAsked = intent.getIntExtra("questionCount", 0);
        availableQuestions.clear();
        availableQuestions.addAll(QuizState.GetQuizStates());

        SetupAndInvokeQuestion();
    }

    /**
     * Handles the intent being invoked by a question activity.  Increases the question count
     * and handles determining whether it's time to invoke the summary activity.
     */
    protected void HandleQuestionActivityOrigin() {
        boolean wasAnswerCorrect = getIntent().getBooleanExtra("questionCorrect", false);

        questionsAsked++;           // Increment number of questions asked.

        if (wasAnswerCorrect)
            questionsCorrect++;     // Increment number of questions correct.

        if (questionsAsked >= questionsToBeAsked) {
            Intent intent = new Intent(this, SummaryActivity.class);
            intent.putExtra("callingActivity", ActivityConstants.QUESTION_COORDINATOR_ACTIVITY);
            intent.putExtra("questionsCorrect", questionsCorrect);
            intent.putExtra("questionsAsked", questionsAsked);
            startActivity(intent);
        } else {
            SetupAndInvokeQuestion();
        }
    }

    /**
     * Responsible for determining which question type to use and invoking the
     * appropriate activity.  Also responsible for determining the appropriate
     * quiz states to be passed to the called activity.
     */
    protected void SetupAndInvokeQuestion() {
        Intent intent;
        QuizState qs1;
        QuizState qs2;

        // Determine which type of question to invoke.
        switch(random.nextInt(3)) {
            case 0:
                qs1 = QuizState.SelectRandomAndRemove(availableQuestions);

                intent = new Intent(this, RadioGroupQuestionActivity.class);
                intent.putExtra("callingActivity", ActivityConstants.QUESTION_COORDINATOR_ACTIVITY);
                intent.putExtra("quizState", qs1);
                startActivity(intent);
                break;

            case 1:
                qs1 = QuizState.SelectRandomAndRemove(availableQuestions);
                qs2 = QuizState.SelectRandomAndRemove(availableQuestions);

                intent = new Intent(this, CheckBoxQuestionActivity.class);
                intent.putExtra("callingActivity", ActivityConstants.QUESTION_COORDINATOR_ACTIVITY);
                intent.putExtra("quizState1", qs1);
                intent.putExtra("quizState2", qs2);
                startActivity(intent);
                break;

            case 2:
                qs1 = QuizState.SelectRandomAndRemove(availableQuestions);

                intent = new Intent(this, EditTextQuestionActivity.class);
                intent.putExtra("callingActivity", ActivityConstants.QUESTION_COORDINATOR_ACTIVITY);
                intent.putExtra("quizState", qs1);
                startActivity(intent);
                break;
        }
    }
}
