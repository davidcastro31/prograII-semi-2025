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

public class AddFriendFragment extends Fragment {

    private ImageButton btnBack;
    private TextInputEditText etFriendCode;
    private TextInputEditText etFriendName;
    private MaterialButton btnAddFriend;

    public AddFriendFragment() { }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_friend, container, false);

        btnBack        = view.findViewById(R.id.btnBack);
        etFriendCode   = view.findViewById(R.id.etFriendCode);
        etFriendName   = view.findViewById(R.id.etFriendName);
        btnAddFriend   = view.findViewById(R.id.btnAddFriend);

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
        // btnAddFriend -> lógica para añadir amigo usando código

        return view;
    }
}

