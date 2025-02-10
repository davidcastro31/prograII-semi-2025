package com.example.miprimeraaplicacion;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TabHost tbh;
    Button btn;
    TextView tempVal;
    EditText textCantidad;
    TextView lblRespuesta;
    Spinner spnDe, spnA;
    conversores objConversores = new conversores();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhConversor);
        tbh.setup();
        tbh.addTab(tbh.newTabSpec("Moneda").setContent(R.id.tabMoneda).setIndicator("MONEDAS", null));
        tbh.addTab(tbh.newTabSpec("Longitud").setContent(R.id.tabLongitud).setIndicator("LONGITUD", null));
        tbh.addTab(tbh.newTabSpec("Tiempo").setContent(R.id.tabTiempo).setIndicator("TIEMPO", null));
        tbh.addTab(tbh.newTabSpec("Almacenamiento").setContent(R.id.tabAlmacenamiento).setIndicator("ALMACENAMIENTO", null));
        tbh.addTab(tbh.newTabSpec("Tranferencia").setContent(R.id.tabTransferencia).setIndicator("TRANFERENCIA", null));
        btn = findViewById(R.id.btnCalcular);
        textCantidad = findViewById(R.id.textCantidad);
        lblRespuesta = findViewById(R.id.lblRespuesta);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int opcion = tbh.getCurrentTab();

                    if (opcion == 0) {
                        spnDe = findViewById(R.id.spnDeMonedas);
                        spnA = findViewById(R.id.spnAMonedas);
                    } else if (opcion == 1) {
                        spnDe = findViewById(R.id.spnDeLongitud);
                        spnA = findViewById(R.id.spnALongitud);
                    } else if (opcion == 2) {
                        spnDe = findViewById(R.id.spnDeTiempo);
                        spnA = findViewById(R.id.spnATiempo);
                    } else if (opcion == 3) {
                        spnDe = findViewById(R.id.spnDeAlmacenamiento);
                        spnA = findViewById(R.id.spnAAlmacenamiento);
                    } else if (opcion == 4) {
                        spnDe = findViewById(R.id.spnDeTransferencia);
                        spnA = findViewById(R.id.spnATransferencia);
                    }


                    int de = spnDe.getSelectedItemPosition();
                    int a = spnA.getSelectedItemPosition();

                    String cantidadTexto = textCantidad.getText().toString().trim();
                    if (cantidadTexto.isEmpty()) {
                        Toast.makeText(MainActivity.this, "⚠ Ingrese una cantidad", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double cantidad = Double.parseDouble(cantidadTexto);

                    if (cantidad < 0) {
                        Toast.makeText(MainActivity.this, "⚠ Ingrese un número positivo", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (de == a) {
                        Toast.makeText(MainActivity.this, "⚠ Seleccione diferentes unidades", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double respuesta = objConversores.convertir(opcion, de, a, cantidad);

                    Toast.makeText(MainActivity.this, "✅ Conversión: " + respuesta, Toast.LENGTH_LONG).show();
                    lblRespuesta.setText("Respuesta: " + respuesta);

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "❌ Error: Solo se permiten números", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "❌ Error inesperado: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    class conversores {
        double[][] valores = {
                {1, 0.98, 7.73, 25.45, 36.78, 508.87, 8.74},
                {1, 0.001, 100, 1000, 39.37, 3.2808, 1.0936, 0.00062137},
                {1, 1.0/60, 1.0/3600, 1.0/86400, 1.0/604800, 1.0/2628000, 1.0/31536000},
                {1, 0.000976563, 9.53674e-7, 9.31323e-10, 9.09495e-13, 8.88178e-16},
                {1, 0.001, 0.000001, 0.000000001, 0.000000000001}

        };

        public double convertir(int opcion, int de, int a, double cantidad){
            return valores[opcion][a] / valores[opcion][de] * cantidad;
        }
    }
}



