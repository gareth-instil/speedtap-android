package co.instil.speedtap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class TapActivity extends Activity {

    private StopWatch stopWatch;
    private Button goButton;
    private Button stopButton;
    private TextView elapsedTimeLabel;
    private TextView resultsLabel;

    private PropertyChangeListener elapsedTimeListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            if (event.getPropertyName().equals(StopWatch.ELAPSED_TIME_PROPERTY)) {
                double elapsedTime = (Double) event.getNewValue();
                elapsedTimeLabel.setText(String.format("%.2f", elapsedTime));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);
        goButton = (Button) findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goButton.setVisibility(View.INVISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                resultsLabel.setVisibility(View.INVISIBLE);
                stopWatch.start();
            }
        });
        stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.INVISIBLE);
                stopWatch.stop();
                displayResult();
            }
        });
        elapsedTimeLabel = (TextView) findViewById(R.id.elapsedTimeLabel);
        resultsLabel = (TextView) findViewById(R.id.resultsLabel);
        stopWatch = new StopWatch(5000);
        stopWatch.addPropertyChangeListener(elapsedTimeListener);
    }

    private void displayResult() {
        double timeDifference = stopWatch.getDifferenceFromTarget();
        String resultText;
        if (timeDifference == 0) {
            resultText = "On the nose !!!";
        }
        else {
            String suffix = (timeDifference < 0) ? "late" : "early";
            resultText = String.format("You were %.2f seconds too %s", Math.abs(timeDifference), suffix);
        }
        resultsLabel.setText(resultText);
        resultsLabel.setVisibility(View.VISIBLE);
    }

}
