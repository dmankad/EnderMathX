package com.mankadsoft.endermathx.game;

import android.util.Log;

import com.mankadsoft.endermathx.util.InsightCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleMultiplication implements MathGame {

    private static final int MAX_VAL = 10;
    private static final int GAME_DURATION = 60;
    public static final String GAME_NAME = "Simple Multiplication";
    private static final int RECHALLENGE_RATE = 25;
    private static final int[] valBs = new int[]{0, 1, 2, 2, 2, 3, 3, 3, 5, 5, 5, 10};
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
                    Integer.toString(getRandomNumberInRange(2, MAX_VAL)),
                    Integer.toString(valBs[new Random().nextInt(valBs.length)]),
                    "x");
        }
    }

    @Override
    public Result checkAnswer(Problem problem, String attemptedAnswer) {
        try {
            int valA = Integer.parseInt(problem.getValA());
            int valB = Integer.parseInt(problem.getValB());
            int answer = Integer.parseInt(attemptedAnswer);
            if (valA * valB == answer) {
                return new Result(problem, attemptedAnswer, Result.CORRECT, 2);
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
