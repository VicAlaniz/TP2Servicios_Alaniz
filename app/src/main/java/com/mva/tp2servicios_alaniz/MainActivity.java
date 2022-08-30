package com.mva.tp2servicios_alaniz;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btIniciar, btFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permisos();
        Intent intent = new Intent(this, ServicioActivity.class);

        btIniciar = findViewById(R.id.btIniciar);
        btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(intent);
                btIniciar.setEnabled(false);
                btFinalizar.setEnabled(true);
            }
        });
        btFinalizar = findViewById(R.id.btFinalizar);
        btFinalizar.setEnabled(false);
        btFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
                btIniciar.setEnabled(true);
                btFinalizar.setEnabled(false);
            }
        });
    }
        private void permisos() {
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                    && checkSelfPermission(Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_SMS},1000);
            }
        }
}