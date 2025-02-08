package com.example.miprimeraaplicacion;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tempVal;
    RadioGroup rgb;
    RadioButton opt;
    EditText num1, num2;

    Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnCalcular);
        num1 = findViewById(R.id.txtNum1);
        num2 = findViewById(R.id.txtNum2);

        spn = findViewById(R.id.spnOpciones);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 6 || position == 7 || position == 8) {
                    num2.setText("0.00");
                    num2.setEnabled(false);
                } else {
                    num2.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempVal = findViewById(R.id.txtNum1);
                double num1 = Double.parseDouble(tempVal.getText().toString());
                tempVal = findViewById(R.id.txtNum2);
                double num2 = tempVal.isEnabled() ? Double.parseDouble(tempVal.getText().toString()) : 0;
                double respuesta = 0.0;

                spn = findViewById(R.id.spnOpciones);
                switch (spn.getSelectedItemPosition()){
                    case 0:
                        respuesta = num1 + num2;
                        break;
                    case 1:
                        respuesta = num1 - num2;
                        break;
                    case 2:
                        respuesta = num1 * num2;
                        break;
                    case 3:
                        respuesta = num1 / num2;
                        break;
                    case  4:
                        respuesta = Math.pow(num1, num2);
                        break;
                    case 5:
                        respuesta = (num1 * num2) / 100;
                        break;
                    case 6:
                        respuesta = Math.sqrt(num1);
                        break;
                    case  7:
                        respuesta = 1;
                        for (int i = 2; i <= num1; i++){
                            respuesta *= i;
                        }
                        break;
                    case 8:
                        respuesta = Math.cbrt(num1);
                        break;
                    case 9:
                        respuesta = num1 % num2;
                        break;
                    case 10:
                        respuesta = Math.max(num1,num2);
                        break;
                }

                tempVal = findViewById(R.id.lblRespuesta);
                tempVal.setText("Respuesta: " + respuesta);
            }
        });
    }
}



