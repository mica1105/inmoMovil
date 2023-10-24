package com.example.inmobiliariamovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.databinding.ActivityLoginBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private SensorManager manager;
    private EventoSensor listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        solicitarPerimisos();
        inicializar();
    }

    private void inicializar() {
        loginViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvMensaje.setText(s);
                binding.tvMensaje.setVisibility(View.VISIBLE);
            }
        });
        binding.btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvMensaje.setText("");
                binding.tvMensaje.setVisibility(View.GONE);
                loginViewModel.autenticar(binding.etCorreo.getText().toString(), binding.etContrasenia.getText().toString());
                binding.etCorreo.setText("");
                binding.etContrasenia.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(listener);
    }

    private void configurarListener(){
        manager= (SensorManager) getSystemService(SENSOR_SERVICE);
        listener= new EventoSensor();
        List<Sensor> sensores= manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensores.size()>0){
            Sensor acelerometro= sensores.get(0);
            manager.registerListener(listener, acelerometro,SensorManager.SENSOR_DELAY_GAME);
        }
    }
    private void solicitarPerimisos(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        )
        {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }
    }

    private class EventoSensor implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (event.values[0] >= 20 || event.values[0]<= -20) {
                    llamarInmobiliaria();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
        private void llamarInmobiliaria(){
            int permissionsCheck= ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE);
            if(permissionsCheck == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "2664348707"));
                startActivity(intent);

            } else {
                Toast.makeText(LoginActivity.this, "Permisos denegados", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
            }
        }
    }
}