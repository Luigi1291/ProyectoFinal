package com.example.taller_7_navigationdrawer.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class Fragment3Args implements NavArgs {
  private final HashMap arguments = new HashMap();

  private Fragment3Args() {
  }

  private Fragment3Args(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static Fragment3Args fromBundle(@NonNull Bundle bundle) {
    Fragment3Args __result = new Fragment3Args();
    bundle.setClassLoader(Fragment3Args.class.getClassLoader());
    if (bundle.containsKey("message")) {
      String message;
      message = bundle.getString("message");
      if (message == null) {
        throw new IllegalArgumentException("Argument \"message\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("message", message);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getMessage() {
    return (String) arguments.get("message");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("message")) {
      String message = (String) arguments.get("message");
      __result.putString("message", message);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    Fragment3Args that = (Fragment3Args) object;
    if (arguments.containsKey("message") != that.arguments.containsKey("message")) {
      return false;
    }
    if (getMessage() != null ? !getMessage().equals(that.getMessage()) : that.getMessage() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Fragment3Args{"
        + "message=" + getMessage()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(Fragment3Args original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder() {
    }

    @NonNull
    public Fragment3Args build() {
      Fragment3Args result = new Fragment3Args(arguments);
      return result;
    }

    @NonNull
    public Builder setMessage(@NonNull String message) {
      if (message == null) {
        throw new IllegalArgumentException("Argument \"message\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("message", message);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getMessage() {
      return (String) arguments.get("message");
    }
  }
}
