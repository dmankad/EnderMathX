package com.mankadsoft.endermathx.util;

import android.util.Log;

import com.mankadsoft.endermathx.entities.Kid;
import com.mankadsoft.endermathx.entities.KidLevel;

import java.util.ArrayList;
import java.util.Iterator;

public class KidLevelUtil {

    private static ArrayList<KidLevel> levels;

    static {
        levels = new ArrayList<KidLevel>();
        levels.add(new KidLevel("Newbie Ninja", "You just arrived, get some practice.", 0));
        levels.add(new KidLevel("Junior Ninja", "You are showing some promise, but still a long way to go.", 300));
        levels.add(new KidLevel("Apprentice Ninja", "The older ninjas are noticing your skills.", 1000));
        levels.add(new KidLevel("Green Ninja", "You just arrived, get some practice!", 1500));
        levels.add(new KidLevel("Red Ninja", "You just arrived, get some practice!", 2500));
        levels.add(new KidLevel("Blue Ninja", "You just arrived, get some practice!", 3500));
        levels.add(new KidLevel("Black Ninja", "You just arrived, get some practice!", 5000));
        levels.add(new KidLevel("Gold Ninja", "You just arrived, get some practice!", 10000));
    }

    public static KidLevel GetLevelByScore(Kid currentKid) {
        KidLevel lastLevel = null;
        int score = currentKid.getPoints();
        for(KidLevel level : levels) {

            Log.d("mankad", "Scanning Level: " + level);
            if(lastLevel != null && level.getScore() >= score) {
                Log.d("mankad", "Found Level: " + lastLevel);
                return lastLevel;
            }
            lastLevel = level;
        }
        return null;
    }

    public static KidLevel GetNextLevel(KidLevel currentLevel) {
        Iterator<KidLevel> iter = levels.iterator();
        while(iter.hasNext()) {
            KidLevel level = iter.next();
            Log.d("mankad", "Scanning Level: " + level);
            if(level.equals(currentLevel)) {
                if(iter.hasNext()) {
                    KidLevel next = iter.next();
                    Log.d("mankad", "Found Next Level: " + next);
                    return next;
                }
                else {
                    Log.d("mankad", "Count not find next level: " + level);
                    return null;
                }
            }
        }
        return null;
    }

}
