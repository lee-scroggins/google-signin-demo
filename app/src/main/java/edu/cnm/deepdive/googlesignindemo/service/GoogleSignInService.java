package edu.cnm.deepdive.googlesignindemo.service;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import edu.cnm.deepdive.googlesignindemo.BuildConfig;

public class GoogleSignInService {

  private static Application context;

  private final GoogleSignInClient client;

  private GoogleSignInAccount account;

  private GoogleSignInService() {              //builder pattern   -- builder class nested inside the class
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()   //first sign in -- see's screen for access to email.  Setting up sign in options.
        .requestId()  //not user name, this is the oauth key (unique identifer that persists in google even if you change your name or machine)
        .requestProfile()  //give first/last name and user name + avatar
        .requestIdToken(BuildConfig.CLIENT_ID)
        .build();
    client = GoogleSignIn.getClient(context, options);
  }

  public static void setContext(Application context) {
    GoogleSignInService.context = context;
  }


  //Singleton incoming
  public static GoogleSignInService getInstance() {
    return InstanceHolder.INSTANCE;
  }


  public GoogleSignInAccount getAccount() {
    return account;
  }

  //refresh method
  public Task<GoogleSignInAccount> refresh() {
    return client.silentSignIn()
        .addOnSuccessListener(this::setAccount);
  }

  //Start sign in process method
  public void startSignIn(Activity activity, int requestCode) {
    account = null;
    /*intent object - requests for outside the app.  sign in client returns special intent object for opening Google sign in screen.*/
    Intent intent = client.getSignInIntent();
    /*new activity for result*/
    activity.startActivityForResult(intent, requestCode);
  }

  //COMPLETE SIGN IN method
  public Task<GoogleSignInAccount> completeSignIn(Intent data) {
    Task<GoogleSignInAccount> task = null;
    try {
      task = GoogleSignIn.getSignedInAccountFromIntent(data);
      setAccount(task.getResult(ApiException.class));
    } catch (ApiException e) {
      // Exception will be passed automatically to onFailureListener
    }
    return task;
  }

  //Sign out method
  public Task<Void> signOut() {
    return client.signOut()
        .addOnCompleteListener((ignored) -> setAccount(
            null)); //Whatever happens, success or failure, set account to null and sign out
  }

  private void setAccount(GoogleSignInAccount account) {
    this.account = account;
    if (account != null) {
      Log.d(getClass().getSimpleName(), account.getIdToken());
    }
  }

  //Instance holder - implemented with nested class
  private static class InstanceHolder {

    private static final GoogleSignInService INSTANCE = new GoogleSignInService();
  }
}
