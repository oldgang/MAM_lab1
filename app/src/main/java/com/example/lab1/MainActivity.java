
package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sm;
    Sensor accSensor;
    Sensor sensorMagnetic;
    Sensor gravSensor;

    float[] gravity = null;
    float[] accelerometer = null;
    float[] magnetic = null;

    TextView accelText;
    TextView orientationText;

    float[] orientation = new float[3];
    float azimuth;
    float pitch;
    float roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorMagnetic = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gravSensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        accelText = (TextView) findViewById(R.id.textAccelerometer);
        orientationText = (TextView) findViewById(R.id.textOrientation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accSensor!=null)
        {
            sm.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_GAME);
        }
        if (sensorMagnetic!=null)
        {
            sm.registerListener(this, sensorMagnetic, SensorManager.SENSOR_DELAY_GAME);
        }
        if (gravSensor!=null)
        {
            sm.registerListener(this, gravSensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch(sensorEvent.sensor.getType()) {
            case Sensor.TYPE_GRAVITY:
                gravity = sensorEvent.values.clone();
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accelerometer = sensorEvent.values.clone();
                String txt = "Acceleration:\nx: " + accelerometer[0] + "\ny: " + accelerometer[1] + "\nz: " + accelerometer[2];
                accelText.setText(txt);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetic = sensorEvent.values.clone();
                break;
        }
        if(gravity != null && magnetic != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, gravity, magnetic);
            if(success){
                SensorManager.getOrientation(R, orientation);
                azimuth = orientation[0];
                pitch = orientation[1];
                roll = orientation[2];
                String txt = "\nOrientation:\nAzimuth: " + azimuth + "\nPitch: " + pitch + "\nRoll: " + roll;
                orientationText.setText(txt);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    //do 3 zadania wyklad slajd 'wlasna klasa widoku'
}