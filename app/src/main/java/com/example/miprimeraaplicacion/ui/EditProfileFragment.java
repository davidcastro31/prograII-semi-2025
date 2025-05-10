package com.example.miprimeraaplicacion.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.miprimeraaplicacion.R;

public class EditProfileFragment extends Fragment {

    public EditProfileFragment() {
        super(R.layout.fragment_edit_profile);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflamos el layout
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }
}

