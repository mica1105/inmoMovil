package com.example.inmobiliariamovil.ui.ubicacion;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionViewModel extends AndroidViewModel {

    private MutableLiveData<MiMapa> mapa;
    private Context context;
    private static final LatLng Inmobiliaria= new LatLng(-33.280576,-66.332482);

    public UbicacionViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
    }

    public LiveData<MiMapa> getMiMapa() {
        if (mapa== null){
            mapa= new MutableLiveData<>();
        }
        return mapa;
    }

    public void obtenerMapa(){
        MiMapa ma= new MiMapa();
        mapa.setValue(ma);
    }

    public class MiMapa implements OnMapReadyCallback {

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(Inmobiliaria).title("Inmobiliaria"));
            CameraPosition campos= new CameraPosition.Builder()
                    .target(Inmobiliaria)
                    .zoom(19)
                    .bearing(45)
                    .tilt(70)
                    .build();
            CameraUpdate update= CameraUpdateFactory.newCameraPosition(campos);
            googleMap.animateCamera(update);
        }
    }
}