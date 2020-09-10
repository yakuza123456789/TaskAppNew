package com.example.taskapp.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;
import com.example.taskapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    ArrayList<User> listUser;
    Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerUser);
        recyclerView.setHasFixedSize(true);
        listUser = new ArrayList<>();
        clearAll();

        getDataFromFirebase();


    }

    private void clearAll() {
        if (listUser != null) {
            listUser.clear();

            if (userAdapter != null) {
                userAdapter.notifyDataSetChanged();
            }
        }
        listUser = new ArrayList<>();
    }

    private void getDataFromFirebase() {
        FirebaseFirestore.getInstance().collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {

                        for (DocumentChange doc : value.getDocumentChanges()) {
                            User users = new User();
                            users.setTextNikname(doc.getDocument().getData().get("name").toString());
                            users.setImageAvatar(doc.getDocument().getData().get("avatarUrl").toString());

                            listUser.add(users);
                        }

                        userAdapter = new UserAdapter( listUser, getContext());
                        recyclerView.setAdapter(userAdapter);
                        userAdapter.notifyDataSetChanged();

                    }
                });

    }
}