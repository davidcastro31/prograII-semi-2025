package com.example.miprimeraaplicacion.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miprimeraaplicacion.R;
import com.example.miprimeraaplicacion.firebase.FirebaseAuthHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin, btnGoRegister, btnGoRecover;
    private FirebaseAuth auth;
    private FirebaseAuthHelper authHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        authHelper = new FirebaseAuthHelper();

        // Verificar si hay un usuario autenticado
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // Usuario ya autenticado, ir a MainActivity directamente
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        TextView btnGoRegister = findViewById(R.id.tvGoRegister);
        TextView btnGoRecover  = findViewById(R.id.tvRecover);

        btnLogin.setOnClickListener(view -> performLogin());

        btnGoRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
        btnGoRecover.setOnClickListener(v ->
                startActivity(new Intent(this, RecoverActivity.class)));

    }

    private void performLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

