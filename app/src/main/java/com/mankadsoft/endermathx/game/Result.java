package com.mankadsoft.endermathx.game;

public class Result {
    public static final String CORRECT = "CORRECT";
    public static final String INCORRECT = "INCORRECT";
    public static final String EMPTY = "EMPTY";

    private Problem problem;
    private String attemptedAnswer;
    private String result;
    private Integer points;

    public Result(Problem problem, String attemptedAnswer, String result, Integer points) {
        this.problem = problem;
        this.attemptedAnswer = attemptedAnswer;
        this.result = result;
        this.points = points;
    }

    public Result() {
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public void setAttemptedAnswer(String attemptedAnswer) {
        this.attemptedAnswer = attemptedAnswer;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Problem getProblem() {
        return problem;
    }

    public String getAttemptedAnswer() {
        return attemptedAnswer;
    }

    public String getResult() {
        return result;
    }

    public Integer getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Result{" +
                "problem=" + problem +
                ", attemptedAnswer='" + attemptedAnswer + '\'' +
                ", result='" + result + '\'' +
                ", points=" + points +
                '}';
    }
}
