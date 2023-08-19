package com.mankadsoft.endermathx.util;

import android.os.Build;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mankadsoft.endermathx.game.GameEvent;
import com.mankadsoft.endermathx.game.GameSession;
import com.mankadsoft.endermathx.game.Problem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class InsightCalculator {

    private List<GameSession> gameScope;
    private HashMap<Problem, List<GameEvent>> flatProblemList;

    public InsightCalculator(GameSession game) {
        this.gameScope = new ArrayList<GameSession>();
        this.gameScope.add(game);
    }
    public InsightCalculator(List<GameSession> games) {
        this.gameScope = games;
    }


    public static List<GameSession> FilterByDateRange(List<GameSession> games, long startTime, long endTime) {
        //Stub
        return null;
    }
    public BigDecimal getAverageProblemTime() {

        if(this.flatProblemList == null) {
            this.initFlatProblemView();
        }

        long starts = 0;
        long ends = 0;

        Set<Problem> pids = this.flatProblemList.keySet();
        for (Problem pid : pids) {
            List<GameEvent> events = this.flatProblemList.get(pid);
            GameEvent endEvent = this.findEvent(events, GameEvent.CORRECT_ANSWER);
            if (endEvent != null) {
                GameEvent startEvent = this.findEvent(events, GameEvent.PRESENT_PROBLEM);
                ends = ends + endEvent.getTimestamp();
                starts = starts + startEvent.getTimestamp();
            }
        }

        Log.d("mankad", "ends: "+ends+", starts: "+ starts +", diff: " + (ends-starts) + ", count: "+ pids.size());
        int solved = getTotalProblemsSolved();
        if(solved==0) {
            return BigDecimal.ZERO;
        }
        BigDecimal avgCalc = BigDecimal.valueOf(ends-starts);
        avgCalc = avgCalc.divide(BigDecimal.valueOf(getTotalProblemsSolved()),2, RoundingMode.HALF_UP);
        avgCalc = avgCalc.divide(BigDecimal.valueOf(1000),2, RoundingMode.HALF_UP);

        Log.d("mankad", "Average Time Calc: " + avgCalc.toString());
        return avgCalc;
    }

    public Integer getTotalProblemsPresented() {
        if(this.flatProblemList == null) {
            this.initFlatProblemView();
        }

        return this.flatProblemList.size();
    }

    public Integer getTotalProblemsSolved() {
        if(this.flatProblemList == null) {
            this.initFlatProblemView();
        }

        int totalProblemsSolved = this.flatProblemList.size();

        Set pids = this.flatProblemList.keySet();
        for (Problem p : (Iterable<Problem>) pids) {
            List<GameEvent> events = this.flatProblemList.get(p);
            GameEvent endEvent = this.findEvent(events, GameEvent.CORRECT_ANSWER);
            if (endEvent == null) {
                totalProblemsSolved--;
            }
        }
        return totalProblemsSolved;
    }

    public List<Problem> getHardestProblems(int limit) {
        if(this.flatProblemList == null) {
            this.initFlatProblemView();
        }

        Log.d("mankad", "Calculating hardest problems.");

        SortedMap<Long, Problem> sortedP = new TreeMap<Long, Problem>();
        Set<Problem> pids = this.flatProblemList.keySet();

        Log.d("mankad", "Problems count: " + pids.size());

        for (Problem p : (Iterable<Problem>) pids) {
            List<GameEvent> events = this.flatProblemList.get(p);
            GameEvent endEvent = this.findEvent(events, GameEvent.CORRECT_ANSWER);
            if (endEvent != null) {
                GameEvent startEvent = this.findEvent(events, GameEvent.PRESENT_PROBLEM);
                long duration = endEvent.getTimestamp()-startEvent.getTimestamp();
                Log.d("mankad", "Duration: " + duration + ", Problem: " + p);
                sortedP.put(duration, p);
            }
        }

        Log.d("mankad", "Sorted: " + sortedP);
        ArrayList<Problem> pList = new ArrayList<Problem>(sortedP.values());
        Collections.reverse(pList);
        Log.d("mankad", "Returning: " + pList);
        if(pList.size() > limit) {
            return pList.subList(0, limit);
        }
        return pList;
    }


    private GameEvent findEvent(List<GameEvent> events, String eventType) {
        Iterator<GameEvent> iter = events.iterator();
        while(iter.hasNext()) {
            GameEvent e = iter.next();
            if(e.getEventType().equals(eventType)) {
                return e;
            }
        }
        return null;
    }

    private void initFlatProblemView() {

        this.flatProblemList = new HashMap<Problem, List<GameEvent>>();

        for (GameSession g : gameScope) {
            for (GameEvent e : g.getEvents()) {
                Problem p = e.getProblem();
                if (p != null) {
                    if (this.flatProblemList.containsKey(p)) {
                        this.flatProblemList.get(p).add(e);
                    } else {
                        ArrayList<GameEvent> eL = new ArrayList<GameEvent>();
                        eL.add(e);
                        this.flatProblemList.put(p, eL);
                    }
                }
            }
        }
    }

}

