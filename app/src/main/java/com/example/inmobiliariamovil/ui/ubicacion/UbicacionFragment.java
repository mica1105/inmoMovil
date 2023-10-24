package com.example.inmobiliariamovil.ui.ubicacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.databinding.FragmentUbicacionBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionFragment extends Fragment {

    private FragmentUbicacionBinding binding;
    private UbicacionViewModel ubicacionViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ubicacionViewModel = new ViewModelProvider(this).get(UbicacionViewModel.class);

        binding = FragmentUbicacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ubicacionViewModel.getMiMapa().observe(getViewLifecycleOwner(), new Observer<UbicacionViewModel.MiMapa>() {
            @Override
            public void onChanged(UbicacionViewModel.MiMapa miMapa) {
                SupportMapFragment smp= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                smp.getMapAsync(miMapa);
            }
        });
        ubicacionViewModel.obtenerMapa();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}