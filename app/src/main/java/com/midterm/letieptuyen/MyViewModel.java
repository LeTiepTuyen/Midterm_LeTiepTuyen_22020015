package com.midterm.letieptuyen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;

public class MyViewModel extends ViewModel {

    private MutableLiveData<Boolean> isTrueButtonSelected = new MutableLiveData<>();
    private MutableLiveData<Boolean> isFalseButtonSelected = new MutableLiveData<>();
    private MutableLiveData<String> currentQuestion = new MutableLiveData<>();
    private MutableLiveData<Boolean> userAnswer = new MutableLiveData<>();
    private Map<String, Boolean> questionListWithAnswer;
    public MutableLiveData<Boolean> getIsTrueButtonSelected() {
        return isTrueButtonSelected;
    }

    public void setIsTrueButtonSelected(boolean isTrueButtonSelected) {
        this.isTrueButtonSelected.setValue(isTrueButtonSelected);
    }

    public MutableLiveData<Boolean> getIsFalseButtonSelected() {
        return isFalseButtonSelected;
    }

    public void setIsFalseButtonSelected(boolean isFalseButtonSelected) {
        this.isFalseButtonSelected.setValue(isFalseButtonSelected);
    }

    // Getter for currentQuestion
    public MutableLiveData<String> getCurrentQuestion() {
        if (currentQuestion == null) {
            currentQuestion = new MutableLiveData<>();
        }

        return  currentQuestion;
    }

    // Getter for userAnswer
    public MutableLiveData<Boolean> getUserAnswer() {
        return userAnswer;
    }

    // Method to set current question
    public void setCurrentQuestion(String question) {
        currentQuestion.setValue(question);
    }

    // Method to set user's answer
    public void setUserAnswer(boolean answer) {
        userAnswer.setValue(answer);
    }

    // Method to set question list with answer


    // Method to get correct answer for given question
    public Boolean getCorrectAnswer(String question) {
        return questionListWithAnswer.get(question);
    }


    // Getter for questionListWithAnswer
    public Map<String, Boolean> getQuestionListWithAnswer() {
        return questionListWithAnswer;
    }

    // Setter for questionListWithAnswer
    public void setQuestionListWithAnswer(Map<String, Boolean> questionListWithAnswer) {
        this.questionListWithAnswer = questionListWithAnswer;
    }





}
