package edu.cnm.deepdive.googlesignindemo.model;

import com.google.gson.annotations.Expose;
import java.util.Date;

public class User {




  @Expose
  private Long id;

  @Expose
  private String userName;

  private String oauthKey;

  @Expose
  private Date created;

  @Expose
  private Date connected;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getConnected() {
    return connected;
  }

  public void setConnected(Date connected) {
    this.connected = connected;
  }

  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(String oauthKey) {
    this.oauthKey = oauthKey;
  }
}
