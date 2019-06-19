package com.example.taller_7_navigationdrawer.Fragments;

import android.support.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.example.taller_7_navigationdrawer.R;

public class Fragment1Directions {
  private Fragment1Directions() {
  }

  @NonNull
  public static NavDirections fragment1to2() {
    return new ActionOnlyNavDirections(R.id.fragment1to2);
  }
}
