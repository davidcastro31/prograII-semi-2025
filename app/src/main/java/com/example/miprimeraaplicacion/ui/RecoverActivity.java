package com.example.miprimeraaplicacion.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miprimeraaplicacion.R;
import com.example.miprimeraaplicacion.firebase.FirebaseAuthHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RecoverActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private MaterialButton btnRecover;
    private ImageButton btnBack;
    private FirebaseAuthHelper authHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);  // crea este layout

        etEmail    = findViewById(R.id.etEmailRecover);
        btnRecover = findViewById(R.id.btnRecover);
        btnBack    = findViewById(R.id.btnBack);

        authHelper = new FirebaseAuthHelper();

        btnBack.setOnClickListener(v -> finish());
        btnRecover.setOnClickListener(v -> performRecover());
    }

    private void performRecover() {
        String email = etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Introduce un correo", Toast.LENGTH_SHORT).show();
            return;
        }

        btnRecover.setEnabled(false);
        authHelper.recoverPassword(email, new FirebaseAuthHelper.VoidCallback() {
            @Override public void onSuccess() {
                btnRecover.setEnabled(true);
                Toast.makeText(RecoverActivity.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override public void onFailure(String error) {
                btnRecover.setEnabled(true);
                Toast.makeText(RecoverActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

