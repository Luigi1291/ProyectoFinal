package com.lusberc.billwallet.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.lusberc.billwallet.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class Fragment1Directions {
  private Fragment1Directions() {
  }

  @NonNull
  public static Fragment1to2 fragment1to2(@NonNull String bill) {
    return new Fragment1to2(bill);
  }

  public static class Fragment1to2 implements NavDirections {
    private final HashMap arguments = new HashMap();

    private Fragment1to2(@NonNull String bill) {
      if (bill == null) {
        throw new IllegalArgumentException("Argument \"bill\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("bill", bill);
    }

    @NonNull
    public Fragment1to2 setBill(@NonNull String bill) {
      if (bill == null) {
        throw new IllegalArgumentException("Argument \"bill\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("bill", bill);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("bill")) {
        String bill = (String) arguments.get("bill");
        __result.putString("bill", bill);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.fragment1to2;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getBill() {
      return (String) arguments.get("bill");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      Fragment1to2 that = (Fragment1to2) object;
      if (arguments.containsKey("bill") != that.arguments.containsKey("bill")) {
        return false;
      }
      if (getBill() != null ? !getBill().equals(that.getBill()) : that.getBill() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getBill() != null ? getBill().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "Fragment1to2(actionId=" + getActionId() + "){"
          + "bill=" + getBill()
          + "}";
    }
  }
}
