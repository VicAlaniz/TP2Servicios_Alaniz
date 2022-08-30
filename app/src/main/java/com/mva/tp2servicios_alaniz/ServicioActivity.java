package com.mva.tp2servicios_alaniz;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class ServicioActivity extends Service {

    private Timer tiempo = new Timer();
    private ContentResolver contR ;
    public ServicioActivity() {

    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        contR = this.getContentResolver();
        tiempo.schedule(new TimerTask() {
            @Override
            public void run() {
                verMensajes();
            }
        },0,9000);
        return START_STICKY;

    }

    @Override
    public boolean stopService(Intent parar) {
        super.stopService(parar);
        tiempo.cancel();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tiempo.cancel();
        Log.d("salida","Destroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }

    private void verMensajes(){
        Uri sms = Uri.parse("content://sms/");
        Cursor cr = contR.query(sms,null,null,null,null);
        if (cr.getCount()>0) {
            int fecha = cr.getColumnIndex(Telephony.Sms.DATE);
            int contacto = cr.getColumnIndex(Telephony.Sms.ADDRESS);
            int mensaje = cr.getColumnIndex(Telephony.Sms.BODY);

            int count = 0;
            while (cr.moveToNext() && count<5) {
                String date = cr.getString(fecha);
                String recibidoDe = cr.getString(contacto);
                String texto = cr.getString(mensaje);

                Log.d("salida ", "Mensaje recibido de: " + recibidoDe + ", el: " + date + ", que dice:" + texto);
                count++;
                }
        }
    }
}
