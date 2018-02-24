package ar.uba.fi.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ar.uba.fi.carpooling.util.Consts;
import ar.uba.fi.carpooling.util.InputInformationValidator;

public class SignUpActivity extends AppCompatActivity {

    private Button finishRegisterBtn;
    private EditText emailRegister;
    private EditText passwordRegister;
    private EditText confirmPasswordRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        finishRegisterBtn = (Button) findViewById(R.id.finishRegisterBtn);
        finishRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private void addToFirebase(String email, String password) {
        // User is registered and logged in
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Sign up error", Toast.LENGTH_SHORT).show();
                } else {
                    String userId = mAuth.getCurrentUser().getUid();
                    Log.d("USER ID ADDED TO FIREBASE", userId);
                    // TODO Start map activity here
                }
            }
        });
    }

    private boolean validateCredentials() {
        String email = emailRegister.getText().toString();
        String password = passwordRegister.getText().toString();
        String confirmPassword = confirmPasswordRegister.getText().toString();

        if (!InputInformationValidator.getValidator().isEmailValid(email)) {
            emailRegister.setError(Consts.INVALID_EMAIL);
            return false;
        }

        if (!InputInformationValidator.getValidator().isPasswordValid(password)) {
            passwordRegister.setError(Consts.INVALID_PASSWORD);
            return false;
        }

        if (!InputInformationValidator.getValidator().arePasswordsEqual(password, confirmPassword)) {
            confirmPasswordRegister.setError(Consts.PASSWORDS_DONT_MATCH);
            return false;
        }

        return true;
    }

    private void signup() {
        emailRegister = (EditText) findViewById(R.id.emailRegister);
        passwordRegister = (EditText) findViewById(R.id.passwordRegister);
        confirmPasswordRegister = (EditText) findViewById(R.id.confirmPasswordRegister);
        finishRegisterBtn.setEnabled(false);

        if (!validateCredentials()) {
            Toast.makeText(SignUpActivity.this, Consts.INVALID_INPUT_TEXT, Toast.LENGTH_SHORT).show();
            finishRegisterBtn.setEnabled(true);
            return;
        }

        addToFirebase(emailRegister.getText().toString(), passwordRegister.getText().toString());

        // TODO send registration information to the server

        Intent loginIntent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(loginIntent);
    }
}
