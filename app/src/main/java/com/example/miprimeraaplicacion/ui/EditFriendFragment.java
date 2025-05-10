package com.example.miprimeraaplicacion.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.example.miprimeraaplicacion.R;

public class EditFriendFragment extends Fragment {

    private ImageButton btnBack;
    private TextInputEditText etFriendName;
    private MaterialButton btnSaveFriend;
    private MaterialButton btnBlockFriend;

    public EditFriendFragment() { }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_friend, container, false);

        btnBack         = view.findViewById(R.id.btnBack);
        etFriendName    = view.findViewById(R.id.etFriendName);
        btnSaveFriend   = view.findViewById(R.id.btnSaveFriend);
        btnBlockFriend  = view.findViewById(R.id.btnBlockFriend);

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        // btnSaveFriend -> lógica para guardar nombre
        // btnBlockFriend -> lógica para bloquear amigo

        return view;
    }
}
