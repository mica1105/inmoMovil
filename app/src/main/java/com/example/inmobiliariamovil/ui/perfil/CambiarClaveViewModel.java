package com.example.inmobiliariamovil.ui.perfil;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> error;
    private MutableLiveData<String> exito;

    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }
    public LiveData<String> getError() {
        if (error== null){
            error= new MutableLiveData<>();
        }
        return error;
    }

    public LiveData<String> getExito() {
        if(exito == null){
            exito= new MutableLiveData<>();
        }
        return exito;
    }

    public void editarClave(String actual, String nueva){
        if(actual.equals("")){
            error.setValue("*El campo clave actual es requerido");
        }
        if(nueva.equals("")){
            error.setValue("*El campo clave nueva es requerido");
        }
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria api= ApiClientRetrofit.getApiInmobiliaria();
        Call<String> call= api.editarClave(token,actual,nueva);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String tokenGuardar="Bearer "+response.body();
                    ApiClientRetrofit.guardarToken(context,tokenGuardar);
                    exito.setValue("Clave Modificada");
                } else {
                    error.postValue("*"+response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });

    }
}