package com.mankadsoft.endermathx.game;

import android.util.Log;

import com.mankadsoft.endermathx.util.InsightCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComplexAdditionGame implements MathGame {

    private static final int MAX_VAL = 30;
    private static final int GAME_DURATION = 60;
    public static final String GAME_NAME = "Complex Addition";
    private static final int RECHALLENGE_RATE = 25;

    private List<Problem> hardestProblems = new ArrayList<Problem>();
    private Random rand;

    @Override
    public void init() {
        rand = new Random();
        InsightCalculator calc = new InsightCalculator(GameSessionManager.GetSessionsFilteredByRecent(GAME_NAME, 4));
        hardestProblems = calc.getHardestProblems(3);
    }

    @Override
    public Problem generateProblem() {
        int hardChance = rand.nextInt(100);
        Log.d("mankad", "Hard Roll: " + hardChance + ", Rechallenge Rate: " + RECHALLENGE_RATE);


        if(hardChance < RECHALLENGE_RATE && hardestProblems.size()>0) {
            Log.d("mankad", "Presenting Hard Problem.");
            return hardestProblems.get(rand.nextInt(hardestProblems.size()));
        }
        else {
            Log.d("mankad", "Presenting New Problem.");
            return new Problem(Long.toString(java.util.Calendar.getInstance().getTimeInMillis()),
                    Integer.toString(getRandomNumberInRange(10, MAX_VAL)),
                    Integer.toString(getRandomNumberInRange(10, MAX_VAL)),
                    "+");
        }
    }

    @Override
    public Result checkAnswer(Problem problem, String attemptedAnswer) {
        try {
            int valA = Integer.parseInt(problem.getValA());
            int valB = Integer.parseInt(problem.getValB());
            int answer = Integer.parseInt(attemptedAnswer);
            if (valA + valB == answer) {
                return new Result(problem, attemptedAnswer, Result.CORRECT, 4);
            } else {
                return new Result(problem, attemptedAnswer, Result.INCORRECT, 0);
            }
        }
        catch(Exception e) {
            return new Result(problem, attemptedAnswer, Result.EMPTY, 0);
        }
    }

    @Override
    public String getGameName() {
        return GAME_NAME;
    }

    @Override
    public Integer getGameDuration() {
        return GAME_DURATION;
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return rand.nextInt((max - min) + 1) + min;
    }
}
