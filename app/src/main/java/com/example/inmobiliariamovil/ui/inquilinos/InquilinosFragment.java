package com.example.inmobiliariamovil.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariamovil.R;


import com.example.inmobiliariamovil.databinding.FragmentInquilinosBinding;
import com.example.inmobiliariamovil.modelo.Inmueble;

import java.util.ArrayList;

public class InquilinosFragment extends Fragment {
    private FragmentInquilinosBinding binding;

    private InquilinosViewModel mViewModel;
    private InquilinoAdapter adapter;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentInquilinosBinding.inflate(inflater,container,false);
        View root= binding.getRoot();
        context= root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel= new ViewModelProvider(this).get(InquilinosViewModel.class);
        mViewModel.getInmuebles().observe(getViewLifecycleOwner(), new Observer<ArrayList<Inmueble>>() {
            @Override
            public void onChanged(ArrayList<Inmueble> inmuebles) {
                GridLayoutManager gridLayoutManager= new GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false);
                binding.rvInquilinos.setLayoutManager(gridLayoutManager);
                adapter= new InquilinoAdapter(context,inmuebles,getLayoutInflater());
                binding.rvInquilinos.setAdapter(adapter);
            }
        });
        mViewModel.cargarInmubles();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding= null;
    }
}