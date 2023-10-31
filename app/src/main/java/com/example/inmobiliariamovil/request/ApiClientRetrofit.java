package com.example.inmobiliariamovil.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inmobiliariamovil.modelo.Contrato;
import com.example.inmobiliariamovil.modelo.Inmueble;
import com.example.inmobiliariamovil.modelo.Inquilino;
import com.example.inmobiliariamovil.modelo.Pago;
import com.example.inmobiliariamovil.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClientRetrofit {
    private static final String URLBASE="http://192.168.1.12:5000/";
    private static ApiInmobiliaria apiInmobiliaria;
    public static ApiInmobiliaria getApiInmobiliaria(){
        Gson gson= new GsonBuilder().setLenient().create();
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInmobiliaria= retrofit.create(ApiInmobiliaria.class);
        return apiInmobiliaria;
    }


    public interface ApiInmobiliaria{
        @FormUrlEncoded
        @POST("Propietarios/login")
        Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("Propietarios")
        Call<Propietario> obtenerPropietario (@Header("Authorization") String token);

        @PUT("Propietarios")
        Call<Propietario> modificarPropietario (@Header("Authorization") String token, @Body Propietario propietario);

        @FormUrlEncoded
        @PUT("Propietarios/EditarClave")
        Call<String> editarClave(@Header("Authorization") String token,@Field("actual") String actual,
                                 @Field("nueva") String nueva);

        @GET("Inmuebles")
        Call<ArrayList<Inmueble>> listaInmuebles(@Header("Authorization") String token);

        @PUT("Inmuebles")
        Call<Inmueble> editarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @Multipart
        @POST("Inmuebles/Crear")
        Call<Inmueble> crearInmueble(@Header("Authorization") String token, @Part("direccion")RequestBody direccion,
                                     @Part("tipo") RequestBody tipo, @Part("ambientes") RequestBody ambientes,
                                     @Part("uso") RequestBody uso, @Part("precio") RequestBody precio,
                                     @Part MultipartBody.Part imagenFile);
        @GET("Inmuebles/Alquilados")
        Call<ArrayList<Inmueble>> listaAlquilados(@Header("Authorization") String token);

        @POST("Inquilinos")
        Call<Inquilino> obtenerInquilino(@Header("Authorization") String token, @Body Inmueble inmueble);

        @POST("Contratos")
        Call<Contrato> recuperarContrato(@Header("Authorization") String token,@Body Inmueble inmueble);

        @GET("Pagos/{id}")
        Call<ArrayList<Pago>> listaPagos(@Header("Authorization") String token, @Path("id") int id);


    }
    public static void guardarToken(Context context,String token){
        SharedPreferences sp= context.getSharedPreferences("token.xml",0);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public static String leerToken(Context context){
        SharedPreferences sp= context.getSharedPreferences("token.xml",0);
        return sp.getString("token","");
    }

    public static void borrarToken(Context context){
        SharedPreferences sp= context.getSharedPreferences("token.xml",0);
        SharedPreferences.Editor editor= sp.edit();
        editor.putString("token","");
        editor.commit();
    }
}
