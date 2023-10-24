package com.example.inmobiliariamovil;

import android.app.Application;
import android.content.Context;
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

public class MainViewModel extends AndroidViewModel {
    private Context context;
    private ApiClient apiClient;
    private MutableLiveData<Propietario> propietario;

    public MainViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        apiClient= ApiClient.getApi();
    }

    public LiveData<Propietario> getPropietario(){
        if(propietario == null){
            propietario= new MutableLiveData<>();
        }
        return propietario;
    }

    public void obtenerUsuario(){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria api=ApiClientRetrofit.getApiInmobiliaria();
        Call<Propietario> call= api.obtenerPropietario(token);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    Propietario usuario= response.body();
                    //usuario.setAvatar(R.drawable.sonia);
                    propietario.postValue(usuario);
                } else {
                    Log.d("salida respuesta", response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.d("salida falla", t.getMessage());
            }
        });
    }
}
