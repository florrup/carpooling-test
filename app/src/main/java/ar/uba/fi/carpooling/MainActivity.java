package ar.uba.fi.carpooling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ar.uba.fi.carpooling.util.Consts;
import ar.uba.fi.carpooling.util.InputInformationValidator;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button registerBtn;
    private ProgressDialog progressDialog;
    private EditText emailLoginText;
    private EditText passwordLoginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    private boolean validateCredentials() {
        String email = emailLoginText.getText().toString();
        String password = passwordLoginText.getText().toString();
        if (!InputInformationValidator.getValidator().isEmailValid(email)) {
            emailLoginText.setError(Consts.INVALID_EMAIL);
            return false;
        }

        if (!InputInformationValidator.getValidator().isPasswordValid(password)) {
            passwordLoginText.setError(Consts.INVALID_PASSWORD);
            return false;
        }
        return true;
    }

    private void login() {
        emailLoginText = (EditText) findViewById(R.id.emailLoginText);
        passwordLoginText = (EditText) findViewById(R.id.passwordLoginText);
        loginBtn.setEnabled(false); // so that the user won't click the button again

        if (!validateCredentials()) {
            Toast.makeText(MainActivity.this, Consts.INVALID_INPUT_TEXT, Toast.LENGTH_SHORT).show();
            loginBtn.setEnabled(true);
            return;
        }

        // Show a ProgressDialog in this Thread
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Autenticando...");
        progressDialog.setMessage("Por favor, espere.");
        progressDialog.show();

        String email = emailLoginText.getText().toString();
        String password = passwordLoginText.getText().toString();

        // New Thread that will be in charge of the login
        new LoginTaskThread().execute("Parámetros que necesite el LoginTaskThread");
    }

    public void displayResult(String textoAMostrar){
        emailLoginText.setText(textoAMostrar);
    }

    private class LoginTaskThread extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            // Pretend to fetch credentials
            for (int i=1;i<40000; i++) {
                Log.i("Login", "Probando");
            }
            return "Resultado";
        }
        @Override
        protected void onPostExecute(Object result) {
            displayResult((String)result);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    }
}

