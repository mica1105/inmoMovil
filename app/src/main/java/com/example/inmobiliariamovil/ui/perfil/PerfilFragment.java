package com.example.inmobiliariamovil.ui.perfil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmobiliariamovil.LoginActivity;
import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.databinding.FragmentPerfilBinding;
import com.example.inmobiliariamovil.modelo.Propietario;
import com.example.inmobiliariamovil.ui.ubicacion.UbicacionViewModel;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel perfilViewModel;
    private Propietario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        inicializar(root);
        perfilViewModel.getUsuario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                if(propietario == null){
                    Log.d("error", "el usuario es nulo");
                }else {
                    usuario= propietario;
                    binding.etCodigo.setText(propietario.getId() + "");
                    binding.etDni.setText(propietario.getDni() + "");
                    binding.etApellido.setText(propietario.getApellido());
                    binding.etNombre.setText(propietario.getNombre());
                    binding.etEmail.setText(propietario.getEmail());
                    binding.etPassword.setText(propietario.getClave());
                    binding.etTelefono.setText(propietario.getTelefono());
                    String url="http://192.168.1.12:5000";
                    Glide.with(getContext())
                            .load(url+propietario.getAvatar())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(binding.ivFotoPerfil);
                }
            }
        });
        perfilViewModel.getEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etDni.setEnabled(aBoolean);
                binding.etApellido.setEnabled(aBoolean);
                binding.etNombre.setEnabled(aBoolean);
                binding.etTelefono.setEnabled(aBoolean);
            }
        });
        perfilViewModel.getTextoBoton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btEditar.setText(s);
            }
        });
        perfilViewModel.cargarUsuario();
        return root;
    }

    private void inicializar(View root) {

        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setDni(binding.etDni.getText().toString());
                usuario.setNombre(binding.etNombre.getText().toString());
                usuario.setApellido(binding.etApellido.getText().toString());
                usuario.setEmail(binding.etEmail.getText().toString());
                usuario.setClave(binding.etPassword.getText().toString());
                usuario.setTelefono(binding.etTelefono.getText().toString());
                String texto= binding.btEditar.getText().toString();
                if (texto!= null){
                    perfilViewModel.accionBoton(texto,usuario);
                }
            }
        });
        binding.btEditarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main)
                        .navigate(R.id.cambiarClaveFragment);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}