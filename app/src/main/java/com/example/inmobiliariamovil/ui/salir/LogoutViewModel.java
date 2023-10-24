package com.example.inmobiliariamovil.ui.salir;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.inmobiliariamovil.request.ApiClientRetrofit;

public class LogoutViewModel extends AndroidViewModel {
    private Context context;
    public LogoutViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }
    public void salir(){
        ApiClientRetrofit.borrarToken(context);
    }
}
