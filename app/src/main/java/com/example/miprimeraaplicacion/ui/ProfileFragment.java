package com.example.miprimeraaplicacion.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.miprimeraaplicacion.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private MaterialCardView cardEdit, cardPrefs, cardLogout;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Mostrar nombre de usuario real
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName() != null
                    ? user.getDisplayName()
                    : user.getEmail();
            tvUsername.setText(name);
        }

        cardEdit   = view.findViewById(R.id.card_edit);
        cardPrefs  = view.findViewById(R.id.card_prefs);
        cardLogout = view.findViewById(R.id.card_logout);

        NavController nav = NavHostFragment.findNavController(this);

        cardEdit.setOnClickListener(v ->
                nav.navigate(R.id.action_profileFragment_to_editProfileFragment)
        );

        cardPrefs.setOnClickListener(v ->
                nav.navigate(R.id.action_profileFragment_to_preferencesFragment)
        );

        cardLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(), "Sesión cerrada con éxito", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        });
    }
}




