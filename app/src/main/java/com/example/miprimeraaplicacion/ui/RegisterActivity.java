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

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etEmail, etPass, etPassConf;
    private MaterialButton btnRegister;
    private ImageButton btnBack;
    private FirebaseAuthHelper authHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);  // crea este layout

        etUsername  = findViewById(R.id.etUsername);
        etEmail     = findViewById(R.id.etEmailReg);
        etPass      = findViewById(R.id.etPasswordReg);
        etPassConf  = findViewById(R.id.etPasswordConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack     = findViewById(R.id.btnBack);

        authHelper = new FirebaseAuthHelper();

        btnBack.setOnClickListener(v -> finish());
        btnRegister.setOnClickListener(v -> performRegister());
    }

    private void performRegister() {
        String name  = etUsername.getText().toString().trim();
        String email = etEmail   .getText().toString().trim();
        String pass  = etPass    .getText().toString().trim();
        String conf  = etPassConf.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(pass) || TextUtils.isEmpty(conf)) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.equals(conf)) {
            Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        btnRegister.setEnabled(false);
        authHelper.register(name, email, pass, new FirebaseAuthHelper.AuthCallback() {
            @Override public void onSuccess(com.google.firebase.auth.FirebaseUser user) {
                Toast.makeText(RegisterActivity.this,
                        "Registro exitoso, revisa tu correo",
                        Toast.LENGTH_SHORT).show();
                finish();  // volver a LoginActivity
            }
            @Override public void onFailure(String error) {
                btnRegister.setEnabled(true);
                Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
