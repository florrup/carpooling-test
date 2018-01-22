package ar.uba.fi.carpooling.util;

public final class Consts {

    // Login and register messages
    public static final String INVALID_EMAIL = "Email inválido";
    public static final String INVALID_PASSWORD = "Contraseña inválida: debe tener más de 8 caracteres";
    public static final String PASSWORDS_DONT_MATCH = "Las contraseñas ingresadas no coinciden";
    public static final String INVALID_INPUT_TEXT = "Datos ingresados inválidos";

    // Prevent from constructing objects of this class by declaring a private constructor
    private Consts(){
        throw new AssertionError();
    }

}
