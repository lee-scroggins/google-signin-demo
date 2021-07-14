package edu.cnm.deepdive.googlesignindemo.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.cnm.deepdive.googlesignindemo.R;
import edu.cnm.deepdive.googlesignindemo.databinding.ActivityLoginBinding;
import edu.cnm.deepdive.googlesignindemo.service.GoogleSignInService;

public class LoginActivity extends AppCompatActivity {

  private static final int LOGIN_REQUEST_CODE = 1000;   //request code, distinguish activities being switched between (aka sign/countersign)
  private GoogleSignInService service;
  private ActivityLoginBinding binding;


  @Override  //On-create Method
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    service = GoogleSignInService.getInstance();
    service.refresh()
        .addOnSuccessListener((account) -> {/*if succeed switch to the MainActivity*/})
        .addOnFailureListener((throwable) -> { /*if fail proceed with following*/
          binding = ActivityLoginBinding.inflate(getLayoutInflater());
          binding.signIn.setOnClickListener((v) ->
              service.startSignIn(this, LOGIN_REQUEST_CODE));  //if fail display form, on click listener to start sign in
          setContentView(binding.getRoot());  //root is parent layout of activity xml
        });
  }
}