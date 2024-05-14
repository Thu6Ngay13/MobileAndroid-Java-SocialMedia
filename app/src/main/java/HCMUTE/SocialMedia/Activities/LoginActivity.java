package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import HCMUTE.SocialMedia.Helpers.StringHelper;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Requests.AuthRequest;
import HCMUTE.SocialMedia.Responses.AuthResponse;
import HCMUTE.SocialMedia.Responses.RegisterResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmailOrUsername, etPassword;
    private Button btnLogin, btnGoToRegister;
    private TextView tvForgetPassword;
    private CheckBox cbRemember;
    private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        if(PrefManager.getInstance(getApplicationContext()).isLoggedIn()){
            finish();
            String role = PrefManager.getInstance(getApplicationContext()).getRole();
            startMainActivity(role);
        }
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetAccountActivity.class);
                startActivities(new Intent[]{intent});
            }
        });
        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivities(new Intent[]{intent});
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void startMainActivity(String role) {
        if (role.equals("USER")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
        }
    }

    private void login() {
        etEmailOrUsername.setError(null);
        etPassword.setError(null);
        String emailOrUsername = etEmailOrUsername.getText().toString();
        String password = etPassword.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)){
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(emailOrUsername)){
            etEmailOrUsername.setError(getString(R.string.error_field_required));
            focusView = etEmailOrUsername;
            cancel = true;
        }
        if(cancel){
            focusView.requestFocus();
        } else {
            AuthRequest authRequest = new AuthRequest();
            authRequest.setEmailOrUsername(etEmailOrUsername.getText().toString());
            authRequest.setPassword(etPassword.getText().toString());
            apiService = RetrofitClient.getRetrofit().create(APIService.class);
            Call<AuthResponse> call = apiService.authenticate(authRequest);
            call.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    if (response.isSuccessful()) {
                        AuthResponse authResponse = response.body();
                        saveLoginDetails(authResponse.getUsername(), authResponse.getEmail(), authResponse.getAccessToken(), authResponse.getRole().name());
                        startMainActivity(authResponse.getRole().name());
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "An error occurred please try again later ...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    private void saveLoginDetails(String username, String email, String accessToken, String role) {
        PrefManager.getInstance(getApplicationContext()).login(username, email, accessToken, role);
    }





    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

    private boolean isEmailOrUsernameValid(String emailOrUsername) {
        return emailOrUsername.length() > 0;
    }
    public void initialize(){
        etEmailOrUsername = findViewById(R.id.etEmailOrUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnCreateAccount);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        String textWithUnderline = "<u>Forget password</u>";
        tvForgetPassword.setText(Html.fromHtml(textWithUnderline));
    }
}