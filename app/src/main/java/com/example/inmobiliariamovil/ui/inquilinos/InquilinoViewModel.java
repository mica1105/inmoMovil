package com.example.inmobiliariamovil.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamovil.modelo.Inmueble;
import com.example.inmobiliariamovil.modelo.Inquilino;
import com.example.inmobiliariamovil.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> inquilino;
    //private ApiClient api;
    private Context context;
    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        //api= ApiClient.getApi();
    }

    public LiveData<Inquilino> getInquilino() {
        if (inquilino==null){
            inquilino= new MutableLiveData<>();
        }
        return inquilino;
    }

    public void cargarInquilino(Bundle bundle){
        Inmueble inmueble= (Inmueble) bundle.getSerializable("inmueble");
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria api= ApiClientRetrofit.getApiInmobiliaria();
        Call<Inquilino> call= api.obtenerInquilino(token, inmueble);
        call.enqueue(new Callback<Inquilino>() {
            @Override
            public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                if(response.isSuccessful()){
                     Inquilino inqui= response.body();
                     inquilino.postValue(inqui);
                } else {
                    Log.d("salida respuesta inqui", response.raw().message()+ " "+ inmueble.getDireccion());
                }
            }

            @Override
            public void onFailure(Call<Inquilino> call, Throwable t) {
                Log.d("salida falla inqui", t.getMessage());
            }
        });
    }
}