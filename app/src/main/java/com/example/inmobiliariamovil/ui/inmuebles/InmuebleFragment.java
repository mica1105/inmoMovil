package com.example.inmobiliariamovil.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.databinding.FragmentInmuebleBinding;
import com.example.inmobiliariamovil.modelo.Inmueble;

public class InmuebleFragment extends Fragment {

    private InmuebleViewModel mViewModel;
    private FragmentInmuebleBinding binding;
    private Inmueble miInmueble;

    public static InmuebleFragment newInstance() {
        return new InmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(InmuebleViewModel.class);
        mViewModel.getInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                miInmueble= inmueble;
                binding.tvDireccion.setText(inmueble.getDireccion());
                binding.tvTipo.setText(inmueble.getTipo());
                binding.tvUso.setText(inmueble.getUso());
                binding.tvAmbientes.setText(inmueble.getAmbientes()+"");
                binding.tvPrecio.setText("$"+inmueble.getPrecio());
                binding.cbDisponible.setChecked(inmueble.isEstado());
                String url="http://192.168.1.13:5000";
                Glide.with(getContext())
                        .load(url+inmueble.getImagen())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivFotoInmueble);
            }
        });
        mViewModel.cargarInmueble(getArguments());
        binding.cbDisponible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean disponible= binding.cbDisponible.isChecked();
                miInmueble.setEstado(disponible);
                mViewModel.editarDisponibilidad(miInmueble);
            }
        });
    }

}