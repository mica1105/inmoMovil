package com.example.inmobiliariamovil.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliariamovil.modelo.Contrato;
import com.example.inmobiliariamovil.modelo.Pago;
import com.example.inmobiliariamovil.request.ApiClientRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Pago>> pagos;
    //private ApiClient api;
    public PagosViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        //api= ApiClient.getApi();
    }

    public LiveData<List<Pago>> getPagos() {
        if(pagos == null){
            pagos= new MutableLiveData<>();
        }
        return pagos;
    }

    public void cargarPagos(Bundle bundle){
        Contrato contrato= (Contrato) bundle.getSerializable("contrato");
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria api=ApiClientRetrofit.getApiInmobiliaria();
        Call<ArrayList<Pago>> call= api.listaPagos(token, contrato.getId());
        call.enqueue(new Callback<ArrayList<Pago>>() {
            @Override
            public void onResponse(Call<ArrayList<Pago>> call, Response<ArrayList<Pago>> response) {
                if (response.isSuccessful()){
                    ArrayList<Pago> misPagos= response.body();
                    pagos.postValue(misPagos);
                } else {
                    Log.d("salida respuesta pagos", response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pago>> call, Throwable t) {
                Log.d("salida falla pagos", t.getMessage());
            }
        });
    }
}