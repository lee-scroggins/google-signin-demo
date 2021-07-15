package edu.cnm.deepdive.googlesignindemo;

import android.app.Application;
import edu.cnm.deepdive.googlesignindemo.service.GoogleSignInService;

public class SignInApplication extends  Application{




  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setContext(this);
  }
}
