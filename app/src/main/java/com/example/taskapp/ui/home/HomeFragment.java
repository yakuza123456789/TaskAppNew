package com.example.taskapp.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.TaskAdapter;
import com.example.taskapp.models.Task;
import com.example.taskapp.ui.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment  extends Fragment {
    private TaskAdapter taskAdapter;
    ArrayList<Task> list;
    boolean isTrue;
    boolean isTrueDate;
    private int currentPos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initList(view);
//        initResultlistener();



    }

//    private void initResultlistener() {
//        getParentFragmentManager().setFragmentResultListener("form", getViewLifecycleOwner(), new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
//                Log.e("home", "onFragmentResult");
//                Task task = (Task) result.getSerializable("task");
//                boolean edit = result.getBoolean("edit");
//                if (edit){
//                    list.set(currentPos,task);
//                }else {
//                    list.add(0, task);
//                }
//                taskAdapter.notifyDataSetChanged();
//            }
//        });
//    }

    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        list = new ArrayList<>();
        App.getInstance().getDatabase().taskDao().getAllLive().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(App.getInstance().getDatabase().taskDao().getAll());
                taskAdapter.notifyDataSetChanged();
            }
        });
        taskAdapter = new TaskAdapter(list);
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItem(int position) {
                currentPos = position;
                Task task = list.get(position);
                openForm(task);
            }

            @Override
            public void onItemLongClick(int position) {
                showAlert(list.get(position));
            }

        });
    }

    private void showAlert(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("удалить запись" + task.getTitle() + "?");
        builder.setNegativeButton("отмена", null);
        builder.setPositiveButton("да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                App.getInstance().getDatabase().taskDao().delete(task);
            }
        });
        builder.show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            openForm(null);
        }
        if (item.getItemId() == R.id.sortBtn) {
            list.clear();
            if (!isTrue) {
                list.addAll(App.getInstance().getDatabase().taskDao().getPersonsAlphabetically(false));
                isTrue = true;
                taskAdapter.notifyDataSetChanged();
            } else {
                list.clear();
                list.addAll(App.getInstance().getDatabase().taskDao().getPersonsAlphabetically(true));
                isTrue = false;
                taskAdapter.notifyDataSetChanged();
            }
        }
        if (item.getItemId() == R.id.sortDateBtn) {
            list.clear();
            if (!isTrueDate) {
                list.addAll(App.getInstance().getDatabase().taskDao().getLast());
                isTrueDate = true;
                taskAdapter.notifyDataSetChanged();
            } else {
                list.clear();
                list.addAll(App.getInstance().getDatabase().taskDao().getAll());
                isTrueDate = false;
                taskAdapter.notifyDataSetChanged();
            }
        }
        if (item.getItemId() == R.id.action_clear) {
            App.getInstance().getDatabase().taskDao().deleteAll();
            taskAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);

    }

    private void openForm(Task task) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_navigation_home_to_formFragment, bundle);
    }
}
