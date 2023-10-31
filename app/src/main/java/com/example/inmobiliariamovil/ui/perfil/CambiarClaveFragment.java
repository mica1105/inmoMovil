package com.example.inmobiliariamovil.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.databinding.FragmentCambiarClaveBinding;
import com.example.inmobiliariamovil.databinding.FragmentCrearInmuebleBinding;

public class CambiarClaveFragment extends Fragment {

    private CambiarClaveViewModel mViewModel;
    private FragmentCambiarClaveBinding binding;

    public static CambiarClaveFragment newInstance() {
        return new CambiarClaveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentCambiarClaveBinding.inflate(inflater,container,false);
        View root= binding.getRoot();
        inicializar(root);
        return root;
    }

    private void inicializar(View root) {
        mViewModel = new ViewModelProvider(this).get(CambiarClaveViewModel.class);
        mViewModel.getError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvErrores.setText(s);
                binding.tvErrores.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.getExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).
                        navigate(R.id.nav_perfil);
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        });
        binding.btCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvErrores.setText("");
                binding.tvErrores.setVisibility(View.VISIBLE);
                String actual= binding.etClaveActual.getText().toString();
                String nueva= binding.etClaveNueva.getText().toString();
                mViewModel.editarClave(actual, nueva);
            }
        });

    }

}