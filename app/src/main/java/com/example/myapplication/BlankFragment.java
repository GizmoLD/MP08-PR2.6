package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;


public class BlankFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    ProgressBar xProgressBar, yProgressBar, zProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new, container, false);

        xProgressBar = view.findViewById(R.id.xProgressBar);
        yProgressBar = view.findViewById(R.id.yProgressBar);
        zProgressBar = view.findViewById(R.id.zProgressBar);

        // Seleccionem el tipus de sensor (veure doc oficial)
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // registrem el Listener per capturar els events del sensor
        if( sensor != null ) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Desregistras el Listener cuando se destruye la vista del Fragment
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float xAcc = sensorEvent.values[0];
        float yAcc = sensorEvent.values[1];
        float zAcc = sensorEvent.values[2];

        int progressBarMaxValue = 100;
        int xProgress = (int) ((xAcc / SensorManager.GRAVITY_EARTH) * progressBarMaxValue);
        int yProgress = (int) ((yAcc / SensorManager.GRAVITY_EARTH) * progressBarMaxValue);
        int zProgress = (int) ((zAcc / SensorManager.GRAVITY_EARTH) * progressBarMaxValue);

        xProgressBar.setProgress(xProgress);
        yProgressBar.setProgress(yProgress);
        zProgressBar.setProgress(zProgress);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Es pot ignorar aquesta CB de moment
    }
}