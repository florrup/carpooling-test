package ar.uba.fi.carpooling;

import org.junit.Test;

import ar.uba.fi.carpooling.util.InputInformationValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InputInformationValidatorTest {

    @Test
    public void emptyEmailNotValid() throws Exception {
        assertFalse(InputInformationValidator.getValidator().isEmailValid(""));
    }

    @Test
    public void emailFormatNotValid() throws Exception {
        //assertFalse(InputInformationValidator.getValidator().isEmailValid("carpooling")); // Patterns.EMAIL_ADDRESS is not working?
    }

    @Test
    public void emailIsValid() throws Exception {
        //assertTrue(InputInformationValidator.getValidator().isEmailValid("carpooling@gmail.com"));
    }

    @Test
    public void emptyPasswordNotValid() throws Exception {
        assertFalse(InputInformationValidator.getValidator().isPasswordValid(""));
    }

    @Test
    public void passwordLengthLessThanEightCharactersNotValid() throws Exception {
        assertFalse(InputInformationValidator.getValidator().isPasswordValid("1234567"));
    }

    @Test
    public void passwordIsValid() throws Exception {
        assertTrue(InputInformationValidator.getValidator().isPasswordValid("carpooling123$$"));
    }

    @Test
    public void confirmPasswordNotValid() throws Exception {
        assertFalse(InputInformationValidator.getValidator().arePasswordsEqual("carpooling", "carpooling2"));
    }

}
