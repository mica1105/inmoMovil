package com.example.inmobiliariamovil.ui.contratos;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.inmobiliariamovil.MainViewModel;
import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.modelo.Contrato;
import com.example.inmobiliariamovil.modelo.Inmueble;
import com.example.inmobiliariamovil.modelo.Pago;
import com.example.inmobiliariamovil.request.ApiClient;
import com.example.inmobiliariamovil.request.ApiClientRetrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> contrato;
    private Contrato contratoActual;
    private Context context;
    //private ApiClient apiClient;

    public ContratoViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        //apiClient= ApiClient.getApi();
    }

    public LiveData<Contrato> getContrato() {
        if (contrato== null){
            contrato= new MutableLiveData<>();
        }
        return contrato;
    }


    public void cargarContrato(Bundle bundle){
        Inmueble inmueble =(Inmueble) bundle.getSerializable("inmueble1");
        String token= ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria api= ApiClientRetrofit.getApiInmobiliaria();
        Call<Contrato> call= api.recuperarContrato(token, inmueble);
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if(response.isSuccessful()){
                    contratoActual= response.body();
                    contrato.postValue(contratoActual);
                } else {
                    Log.d("salida respuesta", response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.d("salida falla", t.getMessage());
            }
        });


    }

}