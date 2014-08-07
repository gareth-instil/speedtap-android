package co.instil.speedtap;

import android.os.Handler;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class StopWatch {

    private double targetTime;
    private double startTime;
    private double elapsedTime;
    private Handler handler;
    private int delay = 100;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            setElapsedTime((System.currentTimeMillis() - getStartTime())/1000);
            handler.postDelayed(this, delay);
        }
    };
    private PropertyChangeSupport propertyChangeSupport;

    public StopWatch(double targetTime) {
        this.targetTime = targetTime;
        handler = new Handler();
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void start() {
        startTime = System.currentTimeMillis();
        handler.postDelayed(timerRunnable, delay);
    }

    public void stop() {
        handler.removeCallbacks(timerRunnable);
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        double oldElapsedTime = this.elapsedTime;
        this.elapsedTime = elapsedTime;
        this.propertyChangeSupport.firePropertyChange("elapsedTime", oldElapsedTime, elapsedTime);
    }

    public double getDifferenceFromTarget() {
        return (targetTime/1000) - elapsedTime;
    }
}
