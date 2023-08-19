package com.mankadsoft.endermathx.game;

public interface MathGame {

    public void init();
    public Problem generateProblem();
    public Result checkAnswer(Problem problem, String attemptedAnswer);
    public String getGameName();
    public Integer getGameDuration();


}
