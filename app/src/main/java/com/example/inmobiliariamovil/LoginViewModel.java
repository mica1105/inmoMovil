package com.example.inmobiliariamovil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamovil.modelo.Propietario;
import com.example.inmobiliariamovil.request.ApiClient;
import com.example.inmobiliariamovil.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context context;

    private MutableLiveData error;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<String> getError() {
        if (error== null){
            error= new MutableLiveData<>();
        }
        return error;
    }

    public void autenticar(String usuario, String password){

        ApiClientRetrofit.ApiInmobiliaria api=ApiClientRetrofit.getApiInmobiliaria();
        Call<String> llamada =api.login(usuario,password);
        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    //Log.d("salida",response.body());
                    String tokenGuardar="Bearer "+response.body();
                    ApiClientRetrofit.guardarToken(context,tokenGuardar);
                    Intent i= new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }  else {
                    error.postValue("La clave o contraseña son incorrectas");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }

}
