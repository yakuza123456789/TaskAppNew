package com.example.taskapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    MainActivity mainActivity;
    ImageView imageButton;
    private ProgressBar progressBar;
    Button save;
    EditText editName;
    ImageView imageView, imageBackG;
    Uri downloadUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editName = view.findViewById(R.id.editName);
        progressBar = view.findViewById(R.id.progressBar);
        save = view.findViewById(R.id.btnSave);


        final BottomNavigationView profileNavView = view.findViewById(R.id.profileNavView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.layout.fragment_profile_photos, R.layout.fragment_profile_videos, R.layout.fragment_pro_plans)
                .build();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_profile_fragment);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, appBarConfiguration);
        NavigationUI.setupWithNavController(profileNavView, navController);

        imageButton = view.findViewById(R.id.imageBackBtn);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.navigation_fragment_photos || destination.getId() == R.id.navigation_fragment_plans
                        || destination.getId() == R.id.navigation_fragment_videos){

                }
            }
        });

        Log.d("TAG", "Bottom: " + profileNavView.getScrollY());
        profileNavView.setScrollY(-30);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = FirebaseAuth.getInstance().getUid();
                String name = editName.getText().toString().trim();
                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                FirebaseFirestore.getInstance().collection("users").document(userId).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(requireContext(), "result " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        final NavController navControllerProfile = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navControllerProfile.navigateUp();
            }
        });


        imageView = view.findViewById(R.id.imageAvatar);
        imageBackG = view.findViewById(R.id.imageBackground);
        imageBackG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK);
                imageIntent.setType("image/*");
                startActivityForResult(imageIntent, 41);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent(Intent.ACTION_PICK);
                imageIntent.setType("image/*");
                startActivityForResult(imageIntent, 42);
            }
        });

        Glide.with(this).load("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBw8QDw8PDxAPDw8NDw4QDw0QDxAODw0PFREWFxURFxMYHiggGBslHBYVIjIiJikrMC4wFyA3ODMsQygtOisBCgoKDQ0NDw0NDi0ZFRktKysrKystLSsrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrK//AABEIARMAtwMBIgACEQEDEQH/xAAcAAEAAAcBAAAAAAAAAAAAAAAAAQIDBAUHCAb/xAA5EAACAgIABAQEBAIJBQAAAAAAAQIDBBEFEiExBgcTQSJRYXEUMoGRobEIFSMzQlJyweFiY5PR8P/EABUBAQEAAAAAAAAAAAAAAAAAAAAB/8QAFBEBAAAAAAAAAAAAAAAAAAAAAP/aAAwDAQACEQMRAD8AxmNidEX9WKXlGN0Re14xUWFeMXMMYv68YrwxwLCGOVoYxkI0FaNAGPjjlRY5kFSJQS/X3+QFiqCdY5ieMeMuH4kuW29Tm1vkqXPJLpr6e/zMVi+Z/D5TUXzQUnpSnzr37v4dL9wPW/hy2z8ujHrdt1kIQ7bbT5n20l7s1t438xrrJuvh79PHrfLPIcISd0+r1He0o9H9WeC4pxrIypKWRY7XGPLHajFRXz1FJb+oVvunjWHKr1lkVKvljLnlOMfb3T6op4HiDAyJclOTVOffkTe0u2+3zaOeCti5NlUlOucoTi01KL0+j3/siDpN1a6tJJd23rRTjKEtqMoyaemoyT0/Y0JxfxVnZair75SUNaUVGtb/AMzUUtv6lLgviHKw5ynRZyuxp2KUYzVnXepbW+v0KOg3USuo8R4S8xq8iUacyKptk1GFlal6VjfZNbbi9/p9jYSr+/69GEWEqi3spMrKoo2VAYS6gx+RjnobaixupA8vlYwMvkUEAPRUUdEXddJWqpLmFQFvCorRqLiNZOqwKEayjxHOqx4KVsox5mo1pv4rbH2hGPeUn8kXtjjBOUmoxituT6KK+bfsjSXE/EHrPJ4o/j9O9YvDaZLcKG1zOfK+jbWt9NvtvSA2svEWKqI5E7Iwrmuib3PfySXf/hvsjyvjHx7VHAtsxZasus/D1S3XNppbnNNSe9J91tblHqajxp25mVTG6UrlO6uMk3pf2k0paS/Lt99E/iu/Elkawo8tME4704xm1OSU4xbfeChv3b2FYiybk23ttttt922+rJd/wBAgjsgAAAAAAAD3vhzzItx6aqbldcoSe7PUhzxr9klKL5tf9T/Y8EAOhuAeL8TLVMef07ropxhZHkVkkvijGW3FtPut7+hnpxOYaL3Dqu/Xp7du/wB10a+qXyOifCvFYX4tClcrb1TD1LHGUFdJaUpx5kudb6Nr3+WyovbKyzuqMrZBFtbWBhL6gX19ZAD0VdRXjWVa6yqoAUVWTemVuUjoDyfjep2Y12PW+WcqLLW10bjXqXpr/Vp/opL3OdrruWMa4zk/RtsdbScU09fH809xR0Z4rksW1Zr5nD0Z02RT3F80o8nw/NvSX1cfmc9eJli/iZyw7J2USe051uqUX7rTb3899O76EVjK7HFqUXqS7NdGiQiQAAAAAAAAAAAAAABmvCkbHkL0anbausNWSr5En8Um13Wn137bMKVsfJnW3yScd63p63oDpPw9kTtxaZzTi5VxlyylzSimui5tfF9/cu7YmO8J5dUsSlVWRtioLc4yjpTfVx1t/wA217mVsRUY66IKtyAHrVUQ5S85CjOIFDQ0TNDQGvvN9v8Aq6xJwjLnrmpSlCH5J7UVtp83T23/ACNJYHE6ZerXk1QdVsp2bhBRuqsfvXYluPy5X8PTsu63xl8LxMniWVTn1xsunGqeCrtSr/CqqCmqlJa5larOb31KP01heO+UmNKStw1XXOKbeNa7JY93zi2nzQ+67BWkuJ4sK7GqrY3V7+Ca6PXykvZ9u20WZnPEfAMjFvnCeNZSnNqEHL1fdJJSXdNvo/f69TFZ2HZRbZTdB120zlCyD7xnF6aIKAAAAAAAAAAAAAAAXPDa4yvpjNpRlbXGTfZRckm3+gG3vKDhk6a7vXj6dvNCUYSUo2KDi+q30a+q+u+xsSUSrRTGKXLrWko9NfD06CxFRj7okCrdEiB69zKciCZDYEGiXlJmyVsCy4rwnHyoKGRVC2MZKUeZda5+04yXWMvqmmV8eiNcIwjvlgkltuT19W+r/UqtmO41xrGwqnflWwprT0nLbc5a3yxiusn07IC+tqrfxWRg1XuXNKKfIl3e327HL/mPxuObxPJuhFRhzuENa6xi2tvXd/U2l478e2TwrasTC4hD1oqLybaPRhGqW1zLq31eu6RoiUWnp9Gnpr5MKlBGXcgQAAAAAAAAAAAIpkCemtylGMesptRS+bb0gOrMCfNVXL/NXCXbXeK6FSaJOHUenTVXtv064Q2+/wAMUv8AYqyRUWd0QVLYkQM8gQADZBkWQAlkaG8N+MabeKvM41KacIzhi18jnRhT5+vwd01rW9N7232TW+meB8E8HxsiHGqsimu2MuN5/NXZBPlW48uvddG9NfMDD+YniDDoyMXKhZO6nOx76pyolC2qcYKKj762nY/fpt9DSmTZW3BwU0+SPqucubnt2+aS+S7dDYPmI8HhzuwMCbtdzTuhao3RwX03GE3152ku/WK99vprYiotkAAAAAAAAAAAAAHofL7F9bimDDSf9up6etagnP3/ANJ542Z5HcKsnmW5XInTRVKvmftbNxa0vd6i/wB0Bu3RLJFTRLIqLeaBNNADNaGibQAk0QaJyDAk0eVVGVg52VbTizy8TiEq7pxpnTG7GyYxUJbjZKKlCSUXtPaafQ9WAOQOLwtWRer4yjd61jtjNNSU3JuW0/qWZ015keGsTJwsrItoVmRj41sqrI7jY5Ri3GO1+Zb+eznHjFEK8m+uvrXXdbCD3vcYzaT3+hFWYAAAAAAAAAAAACrjUSsnCuC5p2SjCEei5pSektv6nUPg7gEcDCoxlrnjFStkuvNbLrN/bfY1d5H+F3bfPiFsE6aVKunmX573rckmuqSb6/N/Q3e0BQaJZIqtEkiotrERJpogBmiBEgAJWyJKwIECYaAxHi31f6uzfRUnb+Gu5OVc0k+V7aj7tLbS99HJbOzYnIniXFjTmZNEIuEce6dMVLak1W+Xmf1et/qRWMAAAAAAAAAAAy3hXgNvEMunEq6O1/FPT1VWuspv7L+Ol7mJPReEvGGTwt2yxYUc96hGVttcpzUItvkj8SSTbTfT2QHTXCuG1YlFWNQuWqiChBe+vm37tvbf3Lhs8t5c+LnxTE9ScVC+mXp3xjtQb0nGcd9k17fRnp5FRK2SNiRI2BJMEs2AM5ogT6IOIEjIE7RDQEmiJEgANBedXApwzJ3RiuSUXdzJdXCclz7+1jl/5Eb9Naee9sYcOg+07bPRUuXvHmjNx37fk/gwOfAARQAAAAAAAAAAbo/o+f3fEP8AXj6XX5T39PkbakjWPkJGiGJkf21LyL7ub8OrF60aoR0m4d+7k/to2jJFRbyKbK8kU3EC3mRJ5RAGf0NAAQcSRoqMptgStEpMyUBoseN8Gx82ieNk1qyqzW49U4yXaUZL8sl8y/SKOdm1UVyuunGFdabcm0vbst+4GlvFHlLgYVbvnn3qtvlrolXV6tkn7epzJfryniuN+GqMXDqvsla7sjmlUk48nptvkbTjttpN9H7/AEL7zH8X28SypuE3DGo36VbaS5dpc7XvJv8A+6GI4txyN2LTTLrOiEa1PW3Yl/ib7rSS6fQK84ACAAAAAAAACth5VlNkLapyrsrkpQnF6lGS90dT+D+OR4hg0Za0pWQ1bFdoXR6TX22nr6NHKR7fy38fT4XOddsZW4dvNKdUOXnhbpasjv7JNb/kB0Y0StHh+BebPDMq1VT9XFctKE71BVyb9nOLaj93pHun/P3+ZUUJoiRmgBmSDYbJWBBslbJtENAShE2i14jmQoqlbPtFNqKaTlpdkBbeIeN04OPPIu24wTahHrOelvS/9nOHjrx/lcTsa3KrFUt14yfRdNbk/d9/3Mp5teJLb741b5YSirJRT3tS/LFv3XTf6muyKjsgAAAAAAAAAAAAAAAD2nhDzJzuHqNTaycaOkse1vdcflXYusfs9r6HiwB0dwPzN4VlJc934SzXWvI+BL7WL4X+6f0BziAO1WCOiBUCllXxrrnbN6hVCU5vvqMVtv8AYkz82miDtvshVXHvOySjFfqzXHizzg4ZXC2iiE86U4zhLlfpU6ktP+0a2+j9kwMfxPzzojJrHxJ2R20p2WenzL2ly6ev+DzOb5mRy+aWS5x3GSjCEekFzL8vXvrfX56+RrjiOUrbZWKCrUu0I9or7+5bEVmvFvFK8rKdlSarUIQjv313lr2Tbb0YUAAAAAAAAAAAAAAAAAAAAAAA7WKeTfCuErLJRhXXFynOT1GMV3bZVNT+fniH0saGDCWpXtTtW+vIn8Mf3Tf6IqNdea/jX+s8pRq2sXF5o1J95t/msa+p4UAigAAAAAAAAAAAAAAAAAAAAAAAAAA7XOXfNjibyeIys3uEk5VfL0uZxg1+kd/qb88wOM/g+F5d6ep+lKut/wDcn8Mf5/wOY/Edu7Ko616OJh163vqqIN/brJlGKABAAAAAAAAAAAAAAAAAAAAAAAAAAAG7f6QfFmqsTDT/ALyU75/aPwxX7t/sac4nkKy6c1vUmtbWnrSX+x6zzh4k7+LXx/w40a6YrrrpHml0+8n+x4kAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAyHiHK9bMyrl1V2RfNfaVja/gY8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA//2Q==").circleCrop().into(imageView);
        Glide.with(this).load("https://images.unsplash.com/photo-1538455687899-25c6244d8f9c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1000&q=80").centerCrop().into(imageBackG);
        getNameFromFirestore();
//        getAvatarFromFirestore();
    }

//    private void getAvatarFromFirestore() {
//        String userId = FirebaseAuth.getInstance().getUid();
//        FirebaseFirestore.getInstance().collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    Glide.with(requireActivity()).load(downloadUri).circleCrop().into(imageView);
//                }
//            }
//        });
//    }

    private void getNameFromFirestore() {
        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    editName.setText(task.getResult().getString("name"));
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42 && resultCode == getActivity().RESULT_OK) {
            assert data != null;
            Glide.with(this).load(data.getData()).circleCrop().into(imageView);
            uploadAvatarToStorage(data.getData());
        }
        if (requestCode == 41 && resultCode == getActivity().RESULT_OK) {
            Glide.with(this).load(data.getData()).into(imageBackG);
        }
    }

    private void uploadAvatarToStorage(Uri data) {
        String userId = FirebaseAuth.getInstance().getUid();
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference reference = FirebaseStorage.getInstance().getReference().child("avatars/"+ userId + ".jpg");
        UploadTask uploadTask = reference.putFile(data);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();
                    Log.e("profile", "url = " + downloadUri );
                    updateUserAvatar(downloadUri.toString());

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "upload error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateUserAvatar(String avatarUrl) {
        String userId = FirebaseAuth.getInstance().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("avatarUrl", avatarUrl);
        FirebaseFirestore.getInstance().collection("users")
                .document(userId)
                .update(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "result " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

