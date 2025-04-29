package com.example.miprimeraaplicacion;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class lista_productos extends Activity {
    Bundle parametros = new Bundle();
    ListView ltsProductos;
    Cursor cProductos;
    DB db;

    // Lista completa de productos
    final ArrayList<Producto> alProductos = new ArrayList<>();
    // Copia para restaurar la lista tras búsquedas
    final ArrayList<Producto> alProductosCopia = new ArrayList<>();

    JSONArray jsonArray;
    JSONObject jsonObject;
    Producto producto;
    FloatingActionButton fab;

    // Posición del item seleccionado en la lista filtrada
    int posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        parametros.putString("accion", "nuevo");
        db = new DB(this);

        fab = findViewById(R.id.fabAgregarProducto);
        fab.setOnClickListener(view -> abrirVentana());

        obtenerDatosProductos();
        buscarProductos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);

        // Quita la opción "Agregar" del menú contextual
        menu.removeItem(R.id.mnxNuevo);

        try {
            // Obtener la posición del item dentro de la lista mostrada (filtrada o no)
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = info.position;

            // Tomar el producto de la lista filtrada, no de jsonArray
            Producto prodSeleccionado = alProductos.get(posicion);
            menu.setHeaderTitle(prodSeleccionado.getCodigo());

        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            // Tomar el producto de la lista filtrada en la posición 'posicion'
            Producto prodSeleccionado = alProductos.get(posicion);

            if (item.getItemId() == R.id.mnxModificar) {
                parametros.putString("accion", "modificar");

                // Construir un JSONObject con los datos del producto seleccionado
                JSONObject datos = new JSONObject();
                datos.put("idProducto", prodSeleccionado.getIdProducto());
                datos.put("codigo", prodSeleccionado.getCodigo());
                datos.put("descripcion", prodSeleccionado.getDescripcion());
                datos.put("marca", prodSeleccionado.getMarca());
                datos.put("presentacion", prodSeleccionado.getPresentacion());
                datos.put("precio", prodSeleccionado.getPrecio());
                datos.put("foto", prodSeleccionado.getFoto());

                parametros.putString("producto", datos.toString());
                abrirVentana();

            } else if (item.getItemId() == R.id.mnxEliminar) {
                new android.app.AlertDialog.Builder(lista_productos.this)
                        .setTitle("Confirmar eliminación")
                        .setMessage("¿Estás seguro de eliminar este producto?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            try {
                                // Eliminar usando el ID real del producto seleccionado
                                String idProducto = prodSeleccionado.getIdProducto();
                                String[] datos = {idProducto, "", "", "", "", "", ""};
                                db.administrar_productos("eliminar", datos);
                                mostrarMsg("Producto eliminado con éxito.");

                                // Limpiar el campo de búsqueda y recargar la lista
                                ((EditText) findViewById(R.id.txtBuscarProducto)).setText("");
                                obtenerDatosProductos();
                            } catch (Exception e) {
                                mostrarMsg("Error: " + e.getMessage());
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            return true;

        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
            return super.onContextItemSelected(item);
        }
    }

    private void abrirVentana() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(parametros);
        startActivity(intent);
    }

    private void obtenerDatosProductos() {
        try {
            cProductos = db.lista_productos();
            if (cProductos.moveToFirst()) {
                jsonArray = new JSONArray();
                do {
                    jsonObject = new JSONObject();
                    jsonObject.put("idProducto", cProductos.getString(0));
                    jsonObject.put("codigo", cProductos.getString(1));
                    jsonObject.put("descripcion", cProductos.getString(2));
                    jsonObject.put("marca", cProductos.getString(3));
                    jsonObject.put("presentacion", cProductos.getString(4));
                    jsonObject.put("precio", cProductos.getString(5));
                    jsonObject.put("foto", cProductos.getString(6));
                    jsonArray.put(jsonObject);
                } while (cProductos.moveToNext());

                mostrarDatosProductos();

            } else {
                mostrarMsg("No hay productos registrados.");
                abrirVentana();
            }
        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }

    private void mostrarDatosProductos() {
        try {
            if (jsonArray.length() > 0) {
                ltsProductos = findViewById(R.id.ltsProductos);
                alProductos.clear();
                alProductosCopia.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    producto = new Producto(
                            jsonObject.getString("idProducto"),
                            jsonObject.getString("codigo"),
                            jsonObject.getString("descripcion"),
                            jsonObject.getString("marca"),
                            jsonObject.getString("presentacion"),
                            jsonObject.getString("precio"),
                            jsonObject.getString("foto")
                    );
                    alProductos.add(producto);
                }
                // Copia de la lista completa
                alProductosCopia.addAll(alProductos);

                ltsProductos.setAdapter(new AdaptadorProductos(this, alProductos));
                registerForContextMenu(ltsProductos);

            } else {
                mostrarMsg("No hay productos registrados.");
                abrirVentana();
            }
        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }

    private void buscarProductos() {
        final EditText txtBuscar = findViewById(R.id.txtBuscarProducto);
        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                alProductos.clear();
                String buscar = txtBuscar.getText().toString().trim().toLowerCase();

                if (buscar.length() <= 0) {
                    // Si no hay texto, restaurar la lista completa
                    alProductos.addAll(alProductosCopia);
                } else {
                    // Filtrar la lista
                    for (Producto item : alProductosCopia) {
                        if (item.getCodigo().toLowerCase().contains(buscar) ||
                                item.getDescripcion().toLowerCase().contains(buscar) ||
                                item.getMarca().toLowerCase().contains(buscar) ||
                                item.getPresentacion().toLowerCase().contains(buscar) ||
                                item.getPrecio().toLowerCase().contains(buscar)) {
                            alProductos.add(item);
                        }
                    }
                }
                // Refrescar el adaptador con la lista filtrada
                ltsProductos.setAdapter(new AdaptadorProductos(getApplicationContext(), alProductos));
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void mostrarMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}

