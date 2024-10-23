// Generated by view binder compiler. Do not edit!
package com.example.masc_mobile.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.masc_mobile.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText biography;

  @NonNull
  public final Button btnDelete;

  @NonNull
  public final Button btnSave;

  @NonNull
  public final EditText email;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final Spinner locationSpinner;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final EditText password;

  @NonNull
  public final EditText phone;

  @NonNull
  public final ImageView profileImage;

  @NonNull
  public final EditText username;

  private ActivityProfileBinding(@NonNull ConstraintLayout rootView, @NonNull EditText biography,
      @NonNull Button btnDelete, @NonNull Button btnSave, @NonNull EditText email,
      @NonNull LinearLayout linearLayout, @NonNull Spinner locationSpinner,
      @NonNull ConstraintLayout main, @NonNull EditText password, @NonNull EditText phone,
      @NonNull ImageView profileImage, @NonNull EditText username) {
    this.rootView = rootView;
    this.biography = biography;
    this.btnDelete = btnDelete;
    this.btnSave = btnSave;
    this.email = email;
    this.linearLayout = linearLayout;
    this.locationSpinner = locationSpinner;
    this.main = main;
    this.password = password;
    this.phone = phone;
    this.profileImage = profileImage;
    this.username = username;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.biography;
      EditText biography = ViewBindings.findChildViewById(rootView, id);
      if (biography == null) {
        break missingId;
      }

      id = R.id.btnDelete;
      Button btnDelete = ViewBindings.findChildViewById(rootView, id);
      if (btnDelete == null) {
        break missingId;
      }

      id = R.id.btnSave;
      Button btnSave = ViewBindings.findChildViewById(rootView, id);
      if (btnSave == null) {
        break missingId;
      }

      id = R.id.email;
      EditText email = ViewBindings.findChildViewById(rootView, id);
      if (email == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.locationSpinner;
      Spinner locationSpinner = ViewBindings.findChildViewById(rootView, id);
      if (locationSpinner == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.password;
      EditText password = ViewBindings.findChildViewById(rootView, id);
      if (password == null) {
        break missingId;
      }

      id = R.id.phone;
      EditText phone = ViewBindings.findChildViewById(rootView, id);
      if (phone == null) {
        break missingId;
      }

      id = R.id.profileImage;
      ImageView profileImage = ViewBindings.findChildViewById(rootView, id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.username;
      EditText username = ViewBindings.findChildViewById(rootView, id);
      if (username == null) {
        break missingId;
      }

      return new ActivityProfileBinding((ConstraintLayout) rootView, biography, btnDelete, btnSave,
          email, linearLayout, locationSpinner, main, password, phone, profileImage, username);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
