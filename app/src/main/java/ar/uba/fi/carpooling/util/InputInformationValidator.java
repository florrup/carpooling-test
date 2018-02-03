package ar.uba.fi.carpooling.util;

import android.util.Patterns;

public class InputInformationValidator {
    private static InputInformationValidator validator = null;

    private InputInformationValidator() {};

    public static InputInformationValidator getValidator() {
        if (validator == null) {
            validator = new InputInformationValidator();
        }
        return validator;
    }

    public boolean isEmailValid(String email) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    public boolean isPasswordValid(String password) {
        if (password.isEmpty() || password.length() < 8) {
            return false;
        }
        return true;
    }

    public boolean arePasswordsEqual(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            return false;
        }
        return true;
    }
}
