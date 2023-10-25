package com.example.inmobiliariamovil.ui.inmuebles;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.modelo.Inmueble;
import com.example.inmobiliariamovil.modelo.RealPathUtil;
import com.example.inmobiliariamovil.request.ApiClientRetrofit;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmueble;
    private MutableLiveData<String> error;
    private Context context;

    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Inmueble> getInmueble(){
        if(inmueble== null){
            inmueble= new MutableLiveData<>();
        }
        return inmueble;
    }

    public LiveData<String> getError() {
        if(error== null){
            error= new MutableLiveData<>();
        }
        return error;
    }

    public void crearInmueble(Inmueble inmueble1, Uri uri){
        if(inmueble1.getDireccion().equals("")){
            error.setValue("*Ingrese un domicilio");
        } else if (inmueble1.getTipo().equals("")) {
            error.setValue("*Seleccione un tipo");
        } else if (inmueble1.getAmbientes()== 0) {
            error.setValue("*Ingrese nro de Ambientes");
        } else if (inmueble1.getUso().equals("")) {
            error.setValue("*Seleccione el uso");
        } else if (inmueble1.getPrecio()== 0) {
            error.setValue("*Ingrese el precio");
        } else if (uri.equals("")){
            error.setValue("*Cargue una Imagen");
        } else {
            String token= ApiClientRetrofit.leerToken(context);
            ApiClientRetrofit.ApiInmobiliaria api= ApiClientRetrofit.getApiInmobiliaria();
            RequestBody direccion = RequestBody.create(MediaType.parse("application/json"),inmueble1.getDireccion());
            RequestBody tipo = RequestBody.create(MediaType.parse("application/json"),inmueble1.getTipo());
            RequestBody ambientes = RequestBody.create(MediaType.parse("application/json"),inmueble1.getAmbientes()+"");
            RequestBody uso = RequestBody.create(MediaType.parse("application/json"), inmueble1.getUso());
            RequestBody precio = RequestBody.create(MediaType.parse("application/json"), inmueble1.getPrecio()+"");

            String path= RealPathUtil.getRealPath(context,uri);
            File file = new File(path);
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imagenFile", file.getName(), fileBody);
            Call<Inmueble> call= api.crearInmueble(token, direccion,tipo,ambientes,uso,precio, imagePart);
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if(response.isSuccessful()){
                        inmueble.postValue(response.body());
                    } else {
                        error.setValue(response.raw().message());
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    error.setValue("*Verifique extencion de la foto: "+ t.getMessage());
                }
            });
        }
    }
}