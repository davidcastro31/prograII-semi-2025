package com.example.miprimeraaplicacion.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miprimeraaplicacion.R;
import com.google.android.material.textfield.TextInputEditText;

public class ChatFragment extends Fragment {

    private ImageButton btnChatBack, btnChatMenu, btnAttach, btnSendMessage;
    private RecyclerView rvChat;
    private TextInputEditText etMessage;

    public ChatFragment() { }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        btnChatBack    = view.findViewById(R.id.btnChatBack);
        btnChatMenu    = view.findViewById(R.id.btnChatMenu);
        rvChat         = view.findViewById(R.id.rvChat);
        etMessage      = view.findViewById(R.id.etMessage);
        btnAttach      = view.findViewById(R.id.btnAttach);
        btnSendMessage = view.findViewById(R.id.btnSendMessage);

        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        // TODO: set adapter

        btnChatBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnChatMenu.setOnClickListener(v -> {
            PopupMenu menu = new PopupMenu(requireContext(), btnChatMenu);
            menu.getMenuInflater().inflate(R.menu.chat_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(this::onMenuItemSelected);
            menu.show();
        });

        return view;
    }

    private boolean onMenuItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_view_files) {
            NavController nav = NavHostFragment.findNavController(this);
            nav.navigate(R.id.action_chat_to_files);
            return true;
        }
        // else if block user...
        return false;
    }
}



