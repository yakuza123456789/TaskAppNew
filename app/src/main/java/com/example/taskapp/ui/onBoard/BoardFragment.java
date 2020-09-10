package com.example.taskapp.ui.onBoard;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.taskapp.R;
import com.example.taskapp.ui.interfaces.OnItemClickListener;
import com.example.taskapp.ui.interfaces.OnViewListener;
import com.google.android.material.tabs.TabLayout;

public class BoardFragment extends Fragment {
    AppBarConfiguration appBarConfiguration;
    NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        final ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new PageAdapter());
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        final PageAdapter pageAdapter = (PageAdapter) viewPager.getAdapter();
        pageAdapter.setOnViewClickListener(new OnViewListener() {
            @Override
            public void onViewPagerClick() {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_boardFragment_to_navigation_home);
            }
        });
        view.findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+2);
            }
        });


    }


}