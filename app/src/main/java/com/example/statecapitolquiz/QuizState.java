package com.example.statecapitolquiz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Random;

/**
 * QuizState holds the name of a state and the associated state capitol.
 */
class QuizState implements Parcelable {
    /**
     * Used for random number generation throughout the activity.
     */
    private static Random randomGenerator = new Random();

    /**
     * Backing variable to hold the name of the state this object represents.
     */
    private String stateName;

    /**
     * Backing variable to hold the name of the capitol for the current state object.
     */
    private String capitolName;

    /**
     * Holds the current QuizState object that should be asked about.
     */
    private static QuizState currentQuizState = null;

    /**
     * Parameterized constructor.
     *
     * @param stateName The name of the state we're creating.
     * @param capitolName The capitol of the state we're creating.
     */
    private QuizState(String stateName, String capitolName) {
        this.stateName = stateName;
        this.capitolName = capitolName;
    }

    /**
     * Accessor for the stateName variable, holding the name of the state this object
     * represents.
     *
     * @return The string representation of the state we're representing.
     */
    String getStateName() {
        return stateName;
    }

    /**
     * Accessor for the capitolName variable, holding the name of the capitol for the
     * state being represented.
     *
     * @return The string representation of the capitol for the state we're representing.
     */
    String getCapitolName() {
        return capitolName;
    }

    /**
     * Accessor for the currentQuizState variable, which holds the QuizState object
     * that the current question should be asking about.
     *
     * @return The QuizState object that's currently selected for querying.
     */
    @SuppressWarnings("unused")
    static QuizState getCurrentQuizState() {
        return QuizState.currentQuizState;
    }

    /**
     * Part of the Parcelable interface.  Allows the object to be stored easily in an
     * intent.  Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation.
     *
     * @return PARCELABLE_WRITE_RETURN_VALUE, an integer.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Part of the Parcelable interface.  Flatten this object in to a Parcel.
     *
     * @param out The Parcel to be written to.
     * @param flags The flags to use, if any, when writing to the Parcel.
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getStateName());
        out.writeString(getCapitolName());
    }

    /**
     * Constructor which accepts a Parcel object.
     *
     * @param in The Parcel to use when creating the object.
     */
    private QuizState(Parcel in) {
        stateName = in.readString();
        capitolName = in.readString();
    }

    /**
     * Part of the Parcelable interface.  Used to create a QuizState object.
     */
    public static final Parcelable.Creator<QuizState> CREATOR = new Parcelable.Creator<QuizState>() {
        public QuizState createFromParcel(Parcel in) {
            return new QuizState(in);
        }

        public QuizState[] newArray(int size) {
            return new QuizState[size];
        }
    };

    /**
     * Selects a random QuizState object from the given array list.  The selected
     * QuizState is removed from the given array list and returned to the calling
     * function.
     *
     * @param arrayList The array list to retrieve a random QuizState object from.
     * @return The selected QuizState object.
     */
    static QuizState SelectRandomAndRemove(ArrayList<QuizState> arrayList) {
        int listSize = arrayList.size();
        QuizState qs = arrayList.get(randomGenerator.nextInt(listSize));
        RemoveFromArrayList(arrayList, qs);

        return qs;
    }

    /**
     * Given an ArrayList of QuizState objects this function removes any QuizState objects
     * in the ArrayList which have a matching state and capitol name as the provided
     * QuizState.
     *
     * @param arrayList The ArrayList to remove appropriate QuizState objects from.
     * @param removeValue The QuizState object representing the values to be removed.
     */
    static void RemoveFromArrayList(ArrayList<QuizState> arrayList, QuizState removeValue) {
        for(int i = arrayList.size() - 1; i >= 0; i--) {
            QuizState workValue = arrayList.get(i);

            if( workValue.getCapitolName().equalsIgnoreCase(removeValue.getCapitolName()) &&
                    workValue.getStateName().equalsIgnoreCase(removeValue.getStateName())) {
                arrayList.remove(i);
            }
        }
    }

    /**
     * Creates and returns an ArrayList of QuizState objects for use.
     *
     * @return An ArrayList of QuizState objects.
     */
    static ArrayList<QuizState> GetQuizStates() {
        ArrayList<QuizState> returnValue = new ArrayList<>();

        // Add the states and their capitols to the list.
        returnValue.add(new QuizState("Alabama", "Montgomery"));
        returnValue.add(new QuizState("Alaska", "Juneau"));
        returnValue.add(new QuizState("Arizona", "Phoenix"));
        returnValue.add(new QuizState("Arkansas", "Little Rock"));
        returnValue.add(new QuizState("California", "Sacramento"));
        returnValue.add(new QuizState("Colorado", "Denver"));
        returnValue.add(new QuizState("Connecticut", "Hartford"));
        returnValue.add(new QuizState("Delaware", "Dover"));
        returnValue.add(new QuizState("Florida", "Tallahassee"));
        returnValue.add(new QuizState("Georgia", "Atlanta"));
        returnValue.add(new QuizState("Hawaii", "Honolulu"));
        returnValue.add(new QuizState("Idaho", "Boise"));
        returnValue.add(new QuizState("Illinois", "Springfield"));
        returnValue.add(new QuizState("Indiana", "Indianapolis"));
        returnValue.add(new QuizState("Iowa", "Des Moines"));
        returnValue.add(new QuizState("Kansas", "Topeka"));
        returnValue.add(new QuizState("Kentucky", "Frankfort"));
        returnValue.add(new QuizState("Louisiana", "Baton Rouge"));
        returnValue.add(new QuizState("Maine", "Augusta"));
        returnValue.add(new QuizState("Maryland", "Annapolis"));
        returnValue.add(new QuizState("Massachusetts", "Boston"));
        returnValue.add(new QuizState("Michigan", "Lansing"));
        returnValue.add(new QuizState("Minnesota", "Saint Paul"));
        returnValue.add(new QuizState("Mississippi", "Jackson"));
        returnValue.add(new QuizState("Missouri", "Jefferson City"));
        returnValue.add(new QuizState("Montana", "Helena"));
        returnValue.add(new QuizState("Nebraska", "Lincoln"));
        returnValue.add(new QuizState("Nevada", "Carson City"));
        returnValue.add(new QuizState("New Hampshire", "Concord"));
        returnValue.add(new QuizState("New Jersey", "Trenton"));
        returnValue.add(new QuizState("New Mexico", "Santa Fe"));
        returnValue.add(new QuizState("New York", "Albany"));
        returnValue.add(new QuizState("North Carolina", "Raleigh"));
        returnValue.add(new QuizState("North Dakota", "Bismarck"));
        returnValue.add(new QuizState("Ohio", "Columbus"));
        returnValue.add(new QuizState("Oklahoma", "Oklahoma City"));
        returnValue.add(new QuizState("Oregon", "Salem"));
        returnValue.add(new QuizState("Pennsylvania", "Harrisburg"));
        returnValue.add(new QuizState("Rhode Island", "Providence"));
        returnValue.add(new QuizState("South Carolina", "Columbia"));
        returnValue.add(new QuizState("South Dakota", "Pierre"));
        returnValue.add(new QuizState("Tennessee", "Nashville"));
        returnValue.add(new QuizState("Texas", "Austin"));
        returnValue.add(new QuizState("Utah", "Salt Lake City"));
        returnValue.add(new QuizState("Vermont", "Montpelier"));
        returnValue.add(new QuizState("Virginia", "Richmond"));
        returnValue.add(new QuizState("Washington", "Olympia"));
        returnValue.add(new QuizState("West Virginia", "Charleston"));
        returnValue.add(new QuizState("Wisconsin", "Madison"));
        returnValue.add(new QuizState("Wyoming", "Cheyenne"));

        return returnValue;
    }
}
