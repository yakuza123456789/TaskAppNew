package com.example.taskapp.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class CodeFragment extends Fragment {
    EditText editText;
    String phoneNumber;
    TextView textView;
    CountDownTimer countDownTimer;
    public long millisUntilFinished;
    private boolean timerRunning;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String verificationId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.timeCounter);
        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                textView.setText("Отправить код ещё раз: " + millisUntilFinished / 1000);
            }

            public void onFinish() {

                Bundle bundle = new Bundle();
                bundle.putBoolean("Aza",true);
                getParentFragmentManager().setFragmentResult("Talgar",bundle);
                getActivity().onBackPressed();
            }

        }.start();



        getParentFragmentManager().setFragmentResultListener("phone2", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                phoneNumber = (String) result.getString("phone");
                requestSms(phoneNumber);
            }
        });


        editText = view.findViewById(R.id.editCode);
        view.findViewById(R.id.btnAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editText.getText().toString().trim();
                if (code.isEmpty() ) {

                    editText.setError("Enter code...");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);

            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("Phone", "onVerificationCompleted");
                String code = phoneAuthCredential.getSmsCode();
                String s = editText.getText().toString().trim();
                if (code == s) {
                    verifyCode(code);
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", "user");
                    map.put("avatarUrl", "https://firebasestorage.googleapis.com/v0/b/taskapp-ffe97.appspot.com/o/avatars%2FQdd9exT9G9YL3zDGgtjw0gePGqr2.jpg?alt=media&token=c94878a6-9597-463d-b58c-ec302a7569b6");
                    FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Phone", "onVerificationFailed" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.e("Phone", "onCodeSent");
                verificationId = s;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Log.e("Phone", "onCodeAutoRetrievalTimeOut");
            }
        };



    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "succes", Toast.LENGTH_SHORT).show();
                            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                            navController.navigate(R.id.navigation_home);

                        } else {
                            Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void requestSms(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks
        );

    }
}