package com.example.taskapp.profileFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskapp.BookAdapter;
import com.example.taskapp.R;
import com.example.taskapp.models.Books;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragmentPhotos extends Fragment {
    List<Books> lstBook ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstBook = new ArrayList<>();
        lstBook.add(new Books("The Vegitarian","Categorie Book","Description book",R.drawable.thevigitarian));
        lstBook.add(new Books("The Wild Robot","Categorie Book","Description book",R.drawable.thewildrobot));
        lstBook.add(new Books("Maria Semples","Categorie Book","Description book",R.drawable.mariasemples));
        lstBook.add(new Books("The Martian","Categorie Book","Description book",R.drawable.themartian));
        lstBook.add(new Books("He Died with...","Categorie Book","Description book",R.drawable.hediedwith));
        lstBook.add(new Books("The Vegitarian","Categorie Book","Description book",R.drawable.thevigitarian));
        lstBook.add(new Books("The Wild Robot","Categorie Book","Description book",R.drawable.thewildrobot));
        lstBook.add(new Books("Maria Semples","Categorie Book","Description book",R.drawable.mariasemples));
        lstBook.add(new Books("The Martian","Categorie Book","Description book",R.drawable.themartian));
        lstBook.add(new Books("He Died with...","Categorie Book","Description book",R.drawable.hediedwith));
        lstBook.add(new Books("The Vegitarian","Categorie Book","Description book",R.drawable.thevigitarian));
        lstBook.add(new Books("The Wild Robot","Categorie Book","Description book",R.drawable.thewildrobot));
        lstBook.add(new Books("Maria Semples","Categorie Book","Description book",R.drawable.mariasemples));
        lstBook.add(new Books("The Martian","Categorie Book","Description book",R.drawable.themartian));
        lstBook.add(new Books("He Died with...","Categorie Book","Description book",R.drawable.hediedwith));

        RecyclerView myrv = view.findViewById(R.id.recyclerview_id);
        BookAdapter bookAdapter = new BookAdapter(requireActivity(),lstBook);
        myrv.setLayoutManager(new GridLayoutManager(requireActivity(),3));
        myrv.setAdapter(bookAdapter);


    }
}