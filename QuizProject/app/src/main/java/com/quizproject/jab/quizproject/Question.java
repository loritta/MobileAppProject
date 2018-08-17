package com.quizproject.jab.quizproject;

import java.util.ArrayList;

public class Question {
    String question;
    String correctAnswer;
    ArrayList<String> answers;

    public Question() {
    }

    public Question(String question, String correctAnswer, ArrayList<String> answers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }





}
