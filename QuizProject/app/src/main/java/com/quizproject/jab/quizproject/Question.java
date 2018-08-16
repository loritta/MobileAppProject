package com.quizproject.jab.quizproject;

public class Question {
    String question;
    String correctAnswer;
    String [] answers;

    public Question(String question, String correctAnswer, String[] wanswers) {
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

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] wrongAnswers) {
        this.answers = wrongAnswers;
    }





}
