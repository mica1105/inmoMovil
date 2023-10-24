package com.example.inmobiliariamovil.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamovil.modelo.Inmueble;
import com.example.inmobiliariamovil.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmueble;
    //private ApiClient apiClient;
    Context context;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        //apiClient= ApiClient.getApi();
    }

    public LiveData<Inmueble> getInmueble(){
        if (inmueble== null){
            inmueble= new MutableLiveData<>();
        }
        return inmueble;
    }

    public void cargarInmueble(Bundle bundle){
        Inmueble propiedad= (Inmueble) bundle.getSerializable("inmueble");
        inmueble.setValue(propiedad);
    }

    public void editarDisponibilidad(Inmueble propiedad){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria api=ApiClientRetrofit.getApiInmobiliaria();
        Call<Inmueble> call= api.editarInmueble(token,propiedad);
        call.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    inmueble.postValue(response.body());
                } else {
                    Log.d("salida respuesta editar", response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("salida falla editar", t.getMessage());
            }
        });

    }
}