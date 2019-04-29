package me.bloodskreaper.wantedpointssystem;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class WantedPointsManager {
    private static HashMap<UUID, WantedPoint> wantedPlayers = new HashMap<>();


    public static int getWantedPoints(UUID uuid){
        WantedPoint wps = wantedPlayers.get(uuid);
        if(wps == null){
            return 0;
        }
        return wps.getWantedPoints();
    }

    public static void addWantedPoints(UUID uuid, int points, String reason){
        if(points <1) return;
        WantedPoint wps = wantedPlayers.get(uuid);
        if(wps == null){
            wps = new WantedPoint();
        }
        wps.addWantedPoints(points, reason);
        wantedPlayers.put(uuid, wps);
    }

    public static void addWantedPoint(UUID uuid, int points, List<String> reasons){
        if(points <1) return;
        WantedPoint wantedPoint = new WantedPoint(points, reasons);
        wantedPlayers.put(uuid, wantedPoint);
    }

    public static void resetWantedPoints(UUID uuid){
        wantedPlayers.remove(uuid);
    }

    public static String getWantedReason(UUID uuid){
        String reason = "";
        if(!wantedPlayers.containsKey(uuid)){
            return reason;
        }
        List<String> reasons = wantedPlayers.get(uuid).getReasons();
        reason = reasons.get(0);
        for(int i = 1; i<reasons.size();i++){
            reason += "; "+reasons.get(i);
        }
        return reason;
    }
    public static HashMap<UUID, WantedPoint> getWantedPlayers(){
        if(wantedPlayers.size() > 0) {
            return wantedPlayers;
        }
        return null;
    }
}
