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

    private StopWatch stopWatch = new StopWatch(5000);
    private Button goButton;
    private Button stopButton;
    private TextView elapsedTimeLabel;
    private TextView resultsLabel;

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
        observeElapsedTime();
    }

    private void displayResult() {
        double timeDifference = stopWatch.getDifferenceFromTarget();
        String resultText;
        if (timeDifference == 0) {
            resultText = "Well done !!!";
        }
        else {
            String suffix = (timeDifference < 0) ? "late" : "early";
            resultText = String.format("You were %.2f seconds too %s", Math.abs(timeDifference), suffix);
        }
        resultsLabel.setText(resultText);
        resultsLabel.setVisibility(View.VISIBLE);
    }

    private void observeElapsedTime() {
        stopWatch.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if (event.getPropertyName().equals("elapsedTime")) {
                    double elapsedTime = (Double) event.getNewValue();
                    elapsedTimeLabel.setText(String.format("%.2f", elapsedTime));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tap, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
