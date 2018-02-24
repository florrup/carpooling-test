package ar.uba.fi.carpooling;

import android.app.ProgressDialog;
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

import java.util.ArrayList;

import ar.uba.fi.carpooling.data.RetrofitInstance;
import ar.uba.fi.carpooling.data.ServiceGenerator;
import ar.uba.fi.carpooling.data.UserEntity;
import ar.uba.fi.carpooling.data.UserResponse;
import ar.uba.fi.carpooling.data.UserService;
import ar.uba.fi.carpooling.util.Consts;
import ar.uba.fi.carpooling.util.InputInformationValidator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button registerBtn;
    private ProgressDialog progressDialog;
    private EditText emailLoginText;
    private EditText passwordLoginText;

    private static final String TAG = "USERDEX";
    private Retrofit retrofit;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Log.i("Logeado", "Ya estaba logeado");
            // TODO start map activity
        }

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        /* Testing API with Retrofit */
        retrofit = RetrofitInstance.getRetrofit();
        obtainUsers();

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

    private void obtainUsers() {
        UserService service = ServiceGenerator.getUserService();
        Call<UserResponse> userResponseCall = service.getUsers();

        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    ArrayList<UserEntity> listaUsers = userResponse.getResults();

                    for (int i = 0; i < listaUsers.size(); i++) {
                        UserEntity u = listaUsers.get(i);
                        //Log.i(TAG, " Pokemon: " + u.getName());
                    }

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Log in error", Toast.LENGTH_SHORT).show();
                }
                // TODO start map activity
                Log.i("Logeado", "Ha sido logeado");
            }
        });

    }

}

