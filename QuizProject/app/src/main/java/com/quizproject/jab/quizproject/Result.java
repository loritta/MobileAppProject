package com.quizproject.jab.quizproject;

// Results fetched from the database
public class Result {
    private String id;
    private String email;
    private String numberOfQuestions;
    private String results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(String difficulty) {
        this.numberOfQuestions = difficulty;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
