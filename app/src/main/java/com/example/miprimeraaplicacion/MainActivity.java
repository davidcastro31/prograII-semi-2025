package com.example.miprimeraaplicacion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabHost tbh;
    Button btnCalcular;
    TextView tempVal;
    EditText textCantidad;
    TextView lblRespuesta;
    Spinner spnDe, spnA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbh = findViewById(R.id.tbhConversor);
        btnCalcular = findViewById(R.id.btnCalcular);
        textCantidad = findViewById(R.id.txtNum1);
        lblRespuesta = findViewById(R.id.lblRespuesta);

        tbh.setup();
        TabHost.TabSpec tab1 = tbh.newTabSpec("Moneda");
        tab1.setIndicator("Moneda");
        tab1.setContent(R.id.tabCuota);
        tbh.addTab(tab1);

        TabHost.TabSpec tab2 = tbh.newTabSpec("Longitud");
        tab2.setIndicator("Longitud");
        tab2.setContent(R.id.tabLongitud);
        tbh.addTab(tab2);


        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcularTarifa();
            }
        });

    }
    private void calcularTarifa() {
        String input = textCantidad.getText().toString();
        if (input.isEmpty()) {
            lblRespuesta.setText("Ingrese la cantidad de metros consumidos");
            return;
        }

        int metrosConsumidos = Integer.parseInt(input);
        double totalPagar = 0;

        if (metrosConsumidos <= 18) {
            totalPagar = 6;
        } else if (metrosConsumidos <= 28) {
            totalPagar = 6 + (metrosConsumidos - 18) * 0.45;
        } else {
            totalPagar = 6 + (10 * 0.45) + ((metrosConsumidos - 28) * 0.65);
        }

        lblRespuesta.setText("Total a pagar: $" + String.format("%.2f", totalPagar));
    }
}






