package edu.cnm.deepdive.googlesignindemo.controller;

import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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
        .subscribe(
            this::switchToMain,
            (throwable) -> {
          binding = ActivityLoginBinding.inflate(getLayoutInflater());
          binding.signIn.setOnClickListener((v) ->
              service.startSignIn(this,
                  LOGIN_REQUEST_CODE));  //if fail display form, on click listener to start sign in.  We set the code in the field.
          setContentView(binding.getRoot());  //root is parent layout of activity xml
        });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    if (requestCode == LOGIN_REQUEST_CODE) {
      service.completeSignIn(data)
          .addOnSuccessListener(this::switchToMain)
          .addOnFailureListener((throwable) ->
              Toast.makeText(this, "Unable to sign in with the provided credentials.",
                  Toast.LENGTH_LONG)
                  .show());//use toast to send message to user that something is wrong.  credentials are not accepted
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }

  }

  //SWITCH TO MAIN method
  private void switchToMain(GoogleSignInAccount account) {    /*similar to builder pattern, aka chaining*/
    Intent intent = new Intent(this, MainActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
            | Intent.FLAG_ACTIVITY_NEW_TASK); /*Signals to system to do something.  Switching from one activity to new activivty*/
    startActivity(intent);
  }

}