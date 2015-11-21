package ct.pinlogin.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasinda on 11/16/15.
 */
public class KeyPress {
    private long startAt=Long.MAX_VALUE;
    private long endAt=Long.MIN_VALUE;
    private double startPressure;
    private double endPressure;
    private long duration;
    private List<Double> pressurePoints=new ArrayList<>();
    private double pressure;
    private int key;

    public long getStartAt() {
        return startAt;
    }

    public void setStartAt(long startAt) {
        this.startAt = startAt;
    }

    public long getEndAt() {
        return endAt;
    }

    public void setEndAt(long endAt) {
        this.endAt = endAt;
        duration=endAt-startAt;
    }

    public long getDuration() {
        return duration;
    }


    public double getStartPressure() {
        return startPressure;
    }

    public void setStartPressure(double startPressure) {
        this.startPressure = startPressure;
    }

    public double getEndPressure() {
        return endPressure;
    }

    public void setEndPressure(double endPressure) {
        this.endPressure = endPressure;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void addPressurePoint(double pressure){
        pressurePoints.add(pressure);
    }
}
