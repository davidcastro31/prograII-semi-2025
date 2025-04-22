package com.ugb.miprimeraaplicacion;

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
    Producto miProducto;
    LayoutInflater inflater;

    public AdaptadorProductos(Context context, ArrayList<Producto> alProductos) {
        this.context = context;
        this.alProductos = alProductos;
    }

    @Override public int getCount() { return alProductos.size(); }
    @Override public Object getItem(int position) { return alProductos.get(position); }
    @Override public long getItemId(int position) { return 0; }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        try {
            if (itemView == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = inflater.inflate(R.layout.fotos, parent, false);
            }

            miProducto = alProductos.get(position);

            // Configurar código
            TextView lblCodigo = itemView.findViewById(R.id.lblCodigoAdaptador);
            lblCodigo.setText(miProducto.getCodigo());

            // Configurar descripción
            TextView lblDescripcion = itemView.findViewById(R.id.lblDescripcionAdaptador);
            lblDescripcion.setText(miProducto.getDescripcion());

            // Configurar precio
            TextView lblPrecio = itemView.findViewById(R.id.lblPrecioAdaptador);
            lblPrecio.setText("$" + miProducto.getPrecio());

            // Configurar imagen
            ImageView imgFoto = itemView.findViewById(R.id.imgFotoAdaptador);
            String fotoPath = miProducto.getFoto();

            if (fotoPath != null && !fotoPath.isEmpty()) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(fotoPath);
                    imgFoto.setImageBitmap(bitmap);
                } catch (Exception e) {
                    // Si hay error cargando la imagen, mostrar placeholder
                    imgFoto.setImageResource(R.mipmap.ic_launcher_round);
                }
            } else {
                // Si no hay ruta de imagen válida
                imgFoto.setImageResource(R.mipmap.ic_launcher_round);
            }

        } catch (Exception e) {
            Toast.makeText(context, "Error cargando producto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return itemView;
    }
}
