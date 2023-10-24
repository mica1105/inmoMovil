package com.example.inmobiliariamovil.ui.inmuebles;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.databinding.FragmentInmueblesBinding;
import com.example.inmobiliariamovil.modelo.Inmueble;

import java.util.ArrayList;


public class InmueblesFragment extends Fragment {

    private FragmentInmueblesBinding binding;
    private InmueblesViewModel inmueblesViewModel;
    InmuebleAdapter adapter;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context= root.getContext();
        inicializar(root);

        return root;
    }

    private void inicializar(View root) {
        inmueblesViewModel =
                new ViewModelProvider(this).get(InmueblesViewModel.class);
        inmueblesViewModel.getInmuebles().observe(getViewLifecycleOwner(), new Observer<ArrayList<Inmueble>>() {
            @Override
            public void onChanged(ArrayList<Inmueble> inmuebles) {
                GridLayoutManager gridLayoutManager= new GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false);
                binding.rvInmuebles.setLayoutManager(gridLayoutManager);
                adapter= new InmuebleAdapter(context, inmuebles, getLayoutInflater());
                binding.rvInmuebles.setAdapter(adapter);
            }
        });
        inmueblesViewModel.cargarInmuebles();
        binding.btFlotanteAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                        .navigate(R.id.crearInmuebleFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}