// Generated by view binder compiler. Do not edit!
package com.example.masc_mobile.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.masc_mobile.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final Button btnRegister;

  @NonNull
  public final EditText etEmail;

  @NonNull
  public final EditText etPassword;

  @NonNull
  public final EditText etUsername;

  @NonNull
  public final ImageView headerImage;

  @NonNull
  public final ImageView profileImage;

  @NonNull
  public final Spinner spinnerLocality;

  @NonNull
  public final Spinner spinnerProvince;

  private ActivityRegisterBinding(@NonNull ScrollView rootView, @NonNull Button btnRegister,
      @NonNull EditText etEmail, @NonNull EditText etPassword, @NonNull EditText etUsername,
      @NonNull ImageView headerImage, @NonNull ImageView profileImage,
      @NonNull Spinner spinnerLocality, @NonNull Spinner spinnerProvince) {
    this.rootView = rootView;
    this.btnRegister = btnRegister;
    this.etEmail = etEmail;
    this.etPassword = etPassword;
    this.etUsername = etUsername;
    this.headerImage = headerImage;
    this.profileImage = profileImage;
    this.spinnerLocality = spinnerLocality;
    this.spinnerProvince = spinnerProvince;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_register;
      Button btnRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnRegister == null) {
        break missingId;
      }

      id = R.id.et_email;
      EditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.et_password;
      EditText etPassword = ViewBindings.findChildViewById(rootView, id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.et_username;
      EditText etUsername = ViewBindings.findChildViewById(rootView, id);
      if (etUsername == null) {
        break missingId;
      }

      id = R.id.header_image;
      ImageView headerImage = ViewBindings.findChildViewById(rootView, id);
      if (headerImage == null) {
        break missingId;
      }

      id = R.id.profile_image;
      ImageView profileImage = ViewBindings.findChildViewById(rootView, id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.spinner_locality;
      Spinner spinnerLocality = ViewBindings.findChildViewById(rootView, id);
      if (spinnerLocality == null) {
        break missingId;
      }

      id = R.id.spinner_province;
      Spinner spinnerProvince = ViewBindings.findChildViewById(rootView, id);
      if (spinnerProvince == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((ScrollView) rootView, btnRegister, etEmail, etPassword,
          etUsername, headerImage, profileImage, spinnerLocality, spinnerProvince);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
