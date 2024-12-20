// Generated by view binder compiler. Do not edit!
package com.example.masc_mobile.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.masc_mobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;

public final class FooterBinding implements ViewBinding {
  @NonNull
  private final BottomNavigationView rootView;

  @NonNull
  public final BottomNavigationView bottomNavigation;

  private FooterBinding(@NonNull BottomNavigationView rootView,
      @NonNull BottomNavigationView bottomNavigation) {
    this.rootView = rootView;
    this.bottomNavigation = bottomNavigation;
  }

  @Override
  @NonNull
  public BottomNavigationView getRoot() {
    return rootView;
  }

  @NonNull
  public static FooterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FooterBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.footer, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FooterBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    BottomNavigationView bottomNavigation = (BottomNavigationView) rootView;

    return new FooterBinding((BottomNavigationView) rootView, bottomNavigation);
  }
}
