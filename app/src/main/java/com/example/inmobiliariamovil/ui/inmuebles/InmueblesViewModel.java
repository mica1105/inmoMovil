package com.example.inmobiliariamovil.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamovil.modelo.Inmueble;
import com.example.inmobiliariamovil.request.ApiClient;
import com.example.inmobiliariamovil.request.ApiClientRetrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<Inmueble>> inmuebles;
    private Context context;
    //private ApiClient api;

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        //api= ApiClient.getApi();
    }

    public LiveData<ArrayList<Inmueble>> getInmuebles(){
        if (inmuebles==null){
            inmuebles= new MutableLiveData<>();
        }
        return inmuebles;
    }

    public void cargarInmuebles(){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria api=ApiClientRetrofit.getApiInmobiliaria();
        Call<ArrayList<Inmueble>> call= api.listaInmuebles(token);
        call.enqueue(new Callback<ArrayList<Inmueble>>() {
            @Override
            public void onResponse(Call<ArrayList<Inmueble>> call, Response<ArrayList<Inmueble>> response) {
                if (response.isSuccessful()){
                    ArrayList<Inmueble> propiedades= response.body();
                    inmuebles.postValue(propiedades);
                } else {
                    Log.d("salida respuesta", response.raw().message());
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Inmueble>> call, Throwable t) {
                Log.d("salida falla", t.getMessage());
            }
        });

    }
}