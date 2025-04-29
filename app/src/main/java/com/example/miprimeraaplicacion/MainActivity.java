package com.example.miprimeraaplicacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    Button btn;
    TextView tempVal;
    ImageView imgFotoProducto;  // Para mostrar la foto
    DB db;
    String accion = "nuevo", idProducto = "";

    // Variables para la cámara
    String urlCompletaFoto = "";
    Intent tomarFotoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB(this);

        // Botón para guardar producto
        btn = findViewById(R.id.btnGuardarProducto);
        btn.setOnClickListener(view -> guardarProducto());

        // FAB para ir a la lista de productos
        fab = findViewById(R.id.fabListaProductos);
        fab.setOnClickListener(view -> abrirVentana());

        // ImageView donde se mostrará la foto
        imgFotoProducto = findViewById(R.id.imgFotoProducto);
        // Agregamos la lógica para tomar foto
        tomarFoto();

        // Cargar datos si es "modificar"
        mostrarDatosProducto();
    }

    private void mostrarDatosProducto() {
        try {
            Bundle parametros = getIntent().getExtras();
            accion = parametros.getString("accion");
            if (accion.equals("modificar")) {
                JSONObject datos = new JSONObject(parametros.getString("producto"));
                idProducto = datos.getString("idProducto");

                // txtCodigo
                tempVal = findViewById(R.id.txtCodigo);
                tempVal.setText(datos.getString("codigo"));

                // txtDescripcion
                tempVal = findViewById(R.id.txtDescripcion);
                tempVal.setText(datos.getString("descripcion"));

                // txtMarca
                tempVal = findViewById(R.id.txtMarca);
                tempVal.setText(datos.getString("marca"));

                // txtPresentacion
                tempVal = findViewById(R.id.txtPresentacion);
                tempVal.setText(datos.getString("presentacion"));

                // txtPrecio
                tempVal = findViewById(R.id.txtPrecio);
                tempVal.setText(datos.getString("precio"));

                // Ruta de la foto (columna 'foto')
                urlCompletaFoto = datos.getString("foto");
                if (!urlCompletaFoto.isEmpty()) {
                    // Mostrar la foto en el ImageView
                    imgFotoProducto.setImageURI(Uri.parse(urlCompletaFoto));
                }
            }
        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }

    private void mostrarMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void abrirVentana() {
        Intent intent = new Intent(this, lista_productos.class);
        startActivity(intent);
    }

    private void guardarProducto() {
        // txtCodigo
        tempVal = findViewById(R.id.txtCodigo);
        String codigo = tempVal.getText().toString().trim();

        // txtDescripcion
        tempVal = findViewById(R.id.txtDescripcion);
        String descripcion = tempVal.getText().toString().trim();

        // txtMarca
        tempVal = findViewById(R.id.txtMarca);
        String marca = tempVal.getText().toString().trim();

        // txtPresentacion
        tempVal = findViewById(R.id.txtPresentacion);
        String presentacion = tempVal.getText().toString().trim();

        // txtPrecio
        tempVal = findViewById(R.id.txtPrecio);
        String precio = tempVal.getText().toString().trim();

        // Validación
        if (codigo.isEmpty() || descripcion.isEmpty() || marca.isEmpty() || presentacion.isEmpty() || precio.isEmpty()) {
            mostrarMsg("Todos los campos deben ser completados.");
            return;
        }

        String[] datos;
        if (accion.equals("modificar")) {
            // Actualizar
            datos = new String[]{idProducto, codigo, descripcion, marca, presentacion, precio, urlCompletaFoto};
            db.administrar_productos("modificar", datos);
            mostrarMsg("Producto modificado con éxito.");
        } else {
            // Agregar
            datos = new String[]{"", codigo, descripcion, marca, presentacion, precio, urlCompletaFoto};
            db.administrar_productos("agregar", datos);
            mostrarMsg("Producto guardado con éxito.");
        }
        abrirVentana();
    }

    // ===================== Lógica de la cámara =====================

    private void tomarFoto() {
        // Al pulsar en la imagen, abrimos la cámara
        imgFotoProducto.setOnClickListener(view -> {
            tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File fotoProducto = null;
            try {
                fotoProducto = crearImagenProducto();
                if (fotoProducto != null) {
                    // Uri para la foto
                    Uri uriFotoProducto = FileProvider.getUriForFile(
                            MainActivity.this,
                            "com.example.miprimeraaplicacion.fileprovider", // Ajusta a tu applicationId
                            fotoProducto
                    );
                    tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoProducto);
                    startActivityForResult(tomarFotoIntent, 1);
                } else {
                    mostrarMsg("No se pudo crear la imagen.");
                }
            } catch (Exception e) {
                mostrarMsg("Error: " + e.getMessage());
            }
        });
    }

    // Crear el archivo donde se guardará la foto
    private File crearImagenProducto() throws Exception {
        String fechaHora = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "imagen_" + fechaHora + "_";

        // Carpeta DCIM (cámara)
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if (dirAlmacenamiento != null && !dirAlmacenamiento.exists()) {
            dirAlmacenamiento.mkdir();
        }

        File image = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        urlCompletaFoto = image.getAbsolutePath();  // Ruta absoluta
        return image;
    }

    // Recibe el resultado de la cámara
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Muestra la foto en el ImageView
            imgFotoProducto.setImageURI(Uri.parse(urlCompletaFoto));
        } else {
            mostrarMsg("No se tomó la foto.");
        }
    }
}









