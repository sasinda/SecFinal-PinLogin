package ct.pinlogin.model;

/**
 * Created by sasinda on 11/16/15.
 */
public class KeyPress {
    private long startAt;
    private long endAt;
    private long duration;
    private double startPressure;
    private double endPressure;

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
}
