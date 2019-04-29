package me.bloodskreaper.wantedpointssystem;

import java.util.ArrayList;
import java.util.List;

public class WantedPoint {
    private int points;
    private List<String> reasons;

    public WantedPoint(){
        reasons = new ArrayList<>();
    }

    public WantedPoint(int points, List<String> reasons){
        this.points = points;
        this.reasons = reasons;
    }

    public void addWantedPoints(int points, String reason){
        this.points = this.points+points;
        int maxpoints = 100;
        if(this.points > maxpoints){
            this.points = maxpoints;
        }
        reasons.add(reason);
    }

    public List<String> getReasons(){
        return reasons;
    }

    public int getWantedPoints(){
        return points;
    }
}
