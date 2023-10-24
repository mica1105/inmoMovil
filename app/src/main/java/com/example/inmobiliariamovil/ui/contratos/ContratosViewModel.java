package com.example.inmobiliariamovil.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamovil.modelo.Inmueble;
import com.example.inmobiliariamovil.request.ApiClient;
import com.example.inmobiliariamovil.request.ApiClientRetrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<ArrayList<Inmueble>> inmuebles;
    //private ApiClient apiClient;

    public ContratosViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        //apiClient= ApiClient.getApi();
    }

    public LiveData<ArrayList<Inmueble>> getInmuebles() {
        if (inmuebles== null){
            inmuebles= new MutableLiveData<>();
        }
        return inmuebles;
    }

    public void cargarInmuebles(){
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria api= ApiClientRetrofit.getApiInmobiliaria();
        Call<ArrayList<Inmueble>> call= api.listaAlquilados(token);
        call.enqueue(new Callback<ArrayList<Inmueble>>() {
            @Override
            public void onResponse(Call<ArrayList<Inmueble>> call, Response<ArrayList<Inmueble>> response) {
                if(response.isSuccessful()){
                    ArrayList<Inmueble> conContratos= response.body();
                    inmuebles.postValue(conContratos);
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