package com.example.inmobiliariamovil.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.inmobiliariamovil.R;
import com.example.inmobiliariamovil.modelo.Inmueble;

import java.util.List;


public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder>{
    private Context context;
    private List<Inmueble> inmuebles;
    private LayoutInflater inflater;

    public ContratoAdapter(Context context, List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.context = context;
        this.inmuebles = inmuebles;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.item_contrato, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Inmueble inmueble= inmuebles.get(position);
        String url="http://192.168.1.12:5000";
        Glide.with(context)
                .load(url+inmuebles.get(position).getImagen())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.inmueble);

        holder.direccion.setText(inmueble.getDireccion());
        holder.verContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle= new Bundle();
                bundle.putSerializable("inmueble1", inmueble);
                Navigation.findNavController((Activity) v.getContext(),R.id.nav_host_fragment_content_main)
                        .navigate(R.id.contratoFragment,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView direccion;
        private Button verContrato;
        private ImageView inmueble;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            inmueble= itemView.findViewById(R.id.ivImagenInmueble);
            direccion= itemView.findViewById(R.id.tvDireccion);
            verContrato= itemView.findViewById(R.id.btnVer);
        }
    }
}
