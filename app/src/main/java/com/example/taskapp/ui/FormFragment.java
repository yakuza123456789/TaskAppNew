package com.example.taskapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskapp.App;
import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.example.taskapp.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormFragment extends Fragment {

    EditText editText;
    NavController navController;
    private Task task;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.et_text);
        task = (Task) requireArguments().getSerializable("task");
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        if (task != null) {
            editText.setText(task.getTitle());

        }
    }

    private void save() {
        String s = editText.getText().toString().trim();
        if (!s.isEmpty()) {
            if (task != null) {
                task.setTitle(s);
                task.setUpdateAt(System.currentTimeMillis());
                App.getInstance().getDatabase().taskDao().update(task);

            } else {
                task = new Task(s, System.currentTimeMillis());


//            Bundle bundle = new Bundle();
//            bundle.putSerializable("task",task);
//            bundle.putBoolean("edit",edit);
//            getParentFragmentManager().setFragmentResult("form",bundle);
                App.getInstance().getDatabase().taskDao().insert(task);
                saveDataToFirestore(task);
            }
            navController.navigateUp();
        } else {
            editText.setError("Заполните это поле");
        }
    }

    private void saveDataToFirestore(Task task) {
        FirebaseFirestore.getInstance().collection("tasks").add(task).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    
                } else {
                    Log.e("TAG", "onComplete:" + task.getException().getMessage());
                }
            }
        });
    }

}