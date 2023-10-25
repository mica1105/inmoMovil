package com.example.inmobiliariamovil.ui.inmuebles;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.databinding.FragmentCrearInmuebleBinding;
import com.example.inmobiliariamovil.modelo.Inmueble;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CrearInmuebleFragment extends Fragment {

    private CrearInmuebleViewModel mViewModel;
    private FragmentCrearInmuebleBinding binding;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private Uri uriF;

    public static CrearInmuebleFragment newInstance() {
        return new CrearInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentCrearInmuebleBinding.inflate(inflater,container,false);
        View root= binding.getRoot();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);
        ArrayAdapter<CharSequence> adaptadorUsos= ArrayAdapter.createFromResource(getContext(), R.array.array_usos,R.layout.dropdown_item);
        binding.acUso.setAdapter(adaptadorUsos);
        ArrayAdapter<CharSequence> adaptadorTipos= ArrayAdapter.createFromResource(getContext(),R.array.array_tipos,
                R.layout.dropdown_item);
        binding.acTipo.setAdapter(adaptadorTipos);

        pickMedia= registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),uri->{
            binding.ivFotoForm.setImageURI(uri);
            uriF= uri;
        });
        mViewModel.getError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvError.setText(s);
                binding.tvError.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.getInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                Toast.makeText(getContext(),"Inmueble "+ inmueble.getId()+ " creado con exito", Toast.LENGTH_LONG).show();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_inmuebles);
            }
        });
        binding.btnAddImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inmueble inmueble= new Inmueble();
                inmueble.setDireccion( binding.etDireccionForm.getText().toString());
                inmueble.setTipo( binding.acTipo.getText().toString());
                inmueble.setUso(binding.acUso.getText().toString());
                inmueble.setAmbientes(Integer.parseInt(binding.etAmbientesForm.getText().toString()));
                inmueble.setPrecio(Double.valueOf(binding.etPrecioForm.getText().toString()));
                mViewModel.crearInmueble(inmueble, uriF);
            }
        });
    }

}