package com.example.inmobiliariamovil.ui.inquilinos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.databinding.FragmentInquilinoBinding;
import com.example.inmobiliariamovil.modelo.Inquilino;

public class InquilinoFragment extends Fragment {

    private InquilinoViewModel mViewModel;
    private FragmentInquilinoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentInquilinoBinding.inflate(inflater,container,false);
        View root= binding.getRoot();
        inicializar(root);

        return root;
    }

    private void inicializar(View root) {
        mViewModel= new ViewModelProvider(this).get(InquilinoViewModel.class);
        mViewModel.getInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.etCodigo.setText(inquilino.getId()+"");
                binding.etApellido.setText(inquilino.getApellido());
                binding.etNombre.setText(inquilino.getNombre());
                binding.etDni.setText(inquilino.getDni());
                binding.etEmail.setText(inquilino.getEmail());
                binding.etTelefono.setText(inquilino.getTelefono());
                binding.etGarante.setText(inquilino.getNombreGarante());
                binding.etTelGarante.setText(inquilino.getTelefonoGarante());
            }
        });
        mViewModel.cargarInquilino(getArguments());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InquilinoViewModel.class);
        // TODO: Use the ViewModel
    }

}