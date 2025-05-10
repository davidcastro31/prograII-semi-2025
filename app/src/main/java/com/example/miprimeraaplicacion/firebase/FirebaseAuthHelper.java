package com.example.miprimeraaplicacion.firebase;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;
import java.util.Locale;

public class FirebaseAuthHelper {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public interface AuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(String error);
    }

    public interface VoidCallback {
        void onSuccess();
        void onFailure(String error);
    }

    /** LOGIN */
    public void login(String email, String password, AuthCallback cb) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            cb.onFailure("Email y contraseña requeridos");
            return;
        }
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            // Ya no comprobamos isEmailVerified()
                            cb.onSuccess(user);
                        } else {
                            cb.onFailure("Error interno: usuario nulo");
                        }
                    } else {
                        cb.onFailure(task.getException().getMessage());
                    }
                });
    }


    /** REGISTER */
    // Dentro de FirebaseAuthHelper.java

    public void register(String username, String email, String password, AuthCallback cb) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            cb.onFailure("Todos los campos son obligatorios");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        cb.onFailure(task.getException().getMessage());
                        return;
                    }
                    FirebaseUser user = auth.getCurrentUser();
                    if (user == null) {
                        cb.onFailure("Error interno: usuario nulo");
                        return;
                    }
                    // Generar y guardar el perfil con código de amigo
                    String friendCode = generateFriendCode();
                    UserProfile profile = new UserProfile(username, email, friendCode);
                    database.getReference("users")
                            .child(user.getUid())
                            .setValue(profile)
                            .addOnCompleteListener(dbTask -> {
                                if (!dbTask.isSuccessful()) {
                                    cb.onFailure(dbTask.getException().getMessage());
                                } else {
                                    // Directamente consideramos registro exitoso
                                    cb.onSuccess(user);
                                }
                            });
                });
    }



    /** RECOVER PASSWORD */
    public void recoverPassword(String email, VoidCallback cb) {
        if (TextUtils.isEmpty(email)) {
            cb.onFailure("Email requerido");
            return;
        }
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cb.onSuccess();
                    } else {
                        cb.onFailure(task.getException().getMessage());
                    }
                });
    }

    /** SIGN OUT */
    public void signOut() {
        auth.signOut();
    }

    /** Helper para generar código único de amigo */
    private String generateFriendCode() {
        SecureRandom rnd = new SecureRandom();
        byte[] buf = new byte[4];
        rnd.nextBytes(buf);
        return String.format(Locale.US, "%08X", buf);
    }

    /** Modelo de usuario para la base de datos */
    public static class UserProfile {
        public String username;
        public String email;
        public String friendCode;
        public UserProfile() {}
        public UserProfile(String u, String e, String c) {
            username = u; email = e; friendCode = c;
        }
    }
}



