package com.example.inmobiliariamovil.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.modelo.Propietario;
import com.example.inmobiliariamovil.request.ApiClient;
import com.example.inmobiliariamovil.request.ApiClientRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> usuario;
    private MutableLiveData<Boolean> estado;
    private MutableLiveData<String> textoBoton;
    private ApiClient apiClient;
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        apiClient= ApiClient.getApi();
    }


    public LiveData<Propietario> getUsuario() {
        if (usuario == null){
            usuario= new MutableLiveData<>();
        }
        return usuario;
    }
    public LiveData<Boolean> getEstado() {
        if (estado == null){
            estado= new MutableLiveData<>();
        }
        return estado;
    }

    public LiveData<String> getTextoBoton(){
        if (textoBoton== null){
            textoBoton= new MutableLiveData<>();
        }
        return textoBoton;
    }
    public void accionBoton(String textoB, Propietario propietario){
        if (textoB.equals("Editar")){
            textoBoton.setValue("Guardar");
            estado.setValue(true);
        }
        if (textoB.equals("Guardar")){
            String token = ApiClientRetrofit.leerToken(context);
            ApiClientRetrofit.ApiInmobiliaria api=ApiClientRetrofit.getApiInmobiliaria();
            Call<Propietario> call= api.modificarPropietario(token, propietario);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if(response.isSuccessful()){
                        Propietario propietario= response.body();
                        //propietario.setAvatar(R.drawable.sonia);
                        usuario.postValue(propietario);
                    } else {
                        Log.d("salida respuesta", response.raw().message());
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Log.d("salida falla",t.getMessage());
                }
            });
            textoBoton.setValue("Editar");
            estado.setValue(false);

        }
    }
    public void cargarUsuario(){
        String token = ApiClientRetrofit.leerToken(context);
        ApiClientRetrofit.ApiInmobiliaria api=ApiClientRetrofit.getApiInmobiliaria();
        Call<Propietario> call= api.obtenerPropietario(token);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()){
                    //Log.d("salida", response.body().getApellido());
                    Propietario propietario= response.body();
                    //propietario.setAvatar(R.drawable.sonia);
                    usuario.postValue(propietario);
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