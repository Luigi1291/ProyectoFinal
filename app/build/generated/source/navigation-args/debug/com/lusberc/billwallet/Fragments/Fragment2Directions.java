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

public class Fragment2Directions {
  private Fragment2Directions() {
  }

  @NonNull
  public static Fragment2to3 fragment2to3() {
    return new Fragment2to3();
  }

  public static class Fragment2to3 implements NavDirections {
    private final HashMap arguments = new HashMap();

    private Fragment2to3() {
    }

    @NonNull
    public Fragment2to3 setMessage(@NonNull String message) {
      if (message == null) {
        throw new IllegalArgumentException("Argument \"message\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("message", message);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("message")) {
        String message = (String) arguments.get("message");
        __result.putString("message", message);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.fragment2to3;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getMessage() {
      return (String) arguments.get("message");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      Fragment2to3 that = (Fragment2to3) object;
      if (arguments.containsKey("message") != that.arguments.containsKey("message")) {
        return false;
      }
      if (getMessage() != null ? !getMessage().equals(that.getMessage()) : that.getMessage() != null) {
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
      result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "Fragment2to3(actionId=" + getActionId() + "){"
          + "message=" + getMessage()
          + "}";
    }
  }
}
