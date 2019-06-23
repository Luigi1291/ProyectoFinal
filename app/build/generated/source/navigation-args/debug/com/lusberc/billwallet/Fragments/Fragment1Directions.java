package com.lusberc.billwallet.Fragments;

import android.support.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.lusberc.billwallet.R;

public class Fragment1Directions {
  private Fragment1Directions() {
  }

  @NonNull
  public static NavDirections fragment1to2() {
    return new ActionOnlyNavDirections(R.id.fragment1to2);
  }
}
