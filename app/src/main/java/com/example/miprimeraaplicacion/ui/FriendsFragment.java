// com/example/miprimeraaplicacion/ui/FriendsFragment.java
package com.example.miprimeraaplicacion.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.miprimeraaplicacion.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class FriendsFragment extends Fragment {

    private RecyclerView rvFriends;
    private FloatingActionButton fabAddFriend;

    public FriendsFragment() { }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        rvFriends     = view.findViewById(R.id.rvFriends);
        fabAddFriend  = view.findViewById(R.id.fabAddFriend);

        rvFriends.setLayoutManager(new LinearLayoutManager(getContext()));
        // TODO: adapter

        NavController nav = NavHostFragment.findNavController(this);
        fabAddFriend.setOnClickListener(v ->
                nav.navigate(R.id.action_friendsFragment_to_addFriendFragment)
        );

        return view;
    }
}


