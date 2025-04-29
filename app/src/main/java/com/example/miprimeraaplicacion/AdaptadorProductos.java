package com.example.miprimeraaplicacion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class AdaptadorProductos extends BaseAdapter {
    Context context;
    ArrayList<Producto> alProductos;
    Producto producto;
    LayoutInflater inflater;

    public AdaptadorProductos(Context context, ArrayList<Producto> alProductos) {
        this.context = context;
        this.alProductos = alProductos;
    }

    @Override
    public int getCount() {
        return alProductos.size();
    }

    @Override
    public Object getItem(int position) {
        return alProductos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.fotos, parent, false);
        try {
            producto = alProductos.get(position);

            TextView txtCodigo = itemView.findViewById(R.id.lblCodigoAdaptador);
            txtCodigo.setText(producto.getCodigo());

            TextView txtDescripcion = itemView.findViewById(R.id.lblDescripcionAdaptador);
            txtDescripcion.setText(producto.getDescripcion());

            TextView txtPrecio = itemView.findViewById(R.id.lblPrecioAdaptador);
            txtPrecio.setText("$" + producto.getPrecio());

            // === Aquí agregas el ImageView para la foto ===
            ImageView imgFotoAdaptador = itemView.findViewById(R.id.imgFotoAdaptador);
            String rutaFoto = producto.getFoto();
            if (rutaFoto != null && !rutaFoto.isEmpty()) {
                Bitmap bitmap = BitmapFactory.decodeFile(rutaFoto);
                if (bitmap != null) {
                    imgFotoAdaptador.setImageBitmap(bitmap);
                } else {
                    // Si no se puede decodificar, coloca un placeholder
                    imgFotoAdaptador.setImageResource(R.mipmap.ic_launcher_round);
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return itemView;
    }
}

