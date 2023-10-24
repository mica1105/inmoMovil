package com.example.inmobiliariamovil.ui.contratos;

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

import com.example.inmobiliariamovil.databinding.FragmentPagosBinding;
import com.example.inmobiliariamovil.modelo.Pago;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel mViewModel;
    private FragmentPagosBinding binding;
    private Context context;
    private PagosAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentPagosBinding.inflate(inflater, container, false);
        View root= binding.getRoot();
        context= root.getContext();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        mViewModel.getPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                GridLayoutManager gridLayoutManager= new GridLayoutManager(context,1, GridLayoutManager.VERTICAL,false);
                binding.rvPagos.setLayoutManager(gridLayoutManager);
                adapter= new PagosAdapter(context,pagos,getLayoutInflater());
                binding.rvPagos.setAdapter(adapter);
            }
        });
        mViewModel.cargarPagos(getArguments());
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}