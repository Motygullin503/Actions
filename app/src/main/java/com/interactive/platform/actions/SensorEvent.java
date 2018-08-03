package com.interactive.platform.actions;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorEvent implements SensorEventListener {

    float [] history = new float[2];
    String [] direction = {"NONE","NONE"};

    public SensorEvent(Context context) {

        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(android.hardware.SensorEvent event) {

        float xChange = history[0] - event.values[0];

        history[0] = event.values[0];
        history[1] = event.values[1];

        if (xChange > 2){
            direction[0] = "LEFT";
        }
        else if (xChange < -2){
            direction[0] = "RIGHT";
        }




    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
