package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Requests.AuthRequest;
import HCMUTE.SocialMedia.Requests.ResetPasswordRequest;
import HCMUTE.SocialMedia.Responses.AuthResponse;
import HCMUTE.SocialMedia.Responses.ResetPasswordResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etToken1, etToken2, etToken3, etToken4, etToken5, etToken6, etPassword, etPasswordAgain;
    private TextView tvNotify;
    private Button btnContinue;
    private CheckBox cbShowPassword;
    private APIService apiService;
    private AccountCardModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initialize();
        model = new AccountCardModel();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            model.setUsername(bundle.getString("RESET_USERNAME"));
            model.setFullname(bundle.getString("RESET_FULLNAME"));
            model.setAvatarURL(bundle.getString("RESET_AVATARURL"));
            model.setEmail(bundle.getString("RESET_EMAIL"));
        }
        tvNotify.setText("Enter the code we sent to " + model.getEmail());
        etToken1.requestFocus();
        etToken1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){

                }
                else
                    etToken2.requestFocus();
            }
        });
        etToken2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    etToken1.requestFocus();
                }
                else
                    etToken3.requestFocus();
            }
        });
        etToken3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    etToken2.requestFocus();
                }
                else
                    etToken4.requestFocus();
            }
        });
        etToken4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    etToken3.requestFocus();
                }
                else
                    etToken5.requestFocus();
            }
        });
        etToken5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    etToken4.requestFocus();
                }
                else
                    etToken6.requestFocus();
            }
        });
        etToken6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    etToken5.requestFocus();
                }
            }
        });
        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    etPasswordAgain.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPasswordAgain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String token = etToken1.getText().toString() +
                etToken2.getText().toString() +
                etToken3.getText().toString() +
                etToken4.getText().toString() +
                etToken5.getText().toString() +
                etToken6.getText().toString();
        etPassword.setError(null);
        etPasswordAgain.setError(null);
        if (validatePassword()){
            ResetPasswordRequest request = new ResetPasswordRequest();
            request.setEmail(model.getEmail());
            request.setNewPassword(etPasswordAgain.getText().toString());
            request.setToken(token);
            apiService = RetrofitClient.getRetrofit().create(APIService.class);
            Call<ResetPasswordResponse> call = apiService.resetPassword(request);
            call.enqueue(new Callback<ResetPasswordResponse>() {
                @Override
                public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                    if (response.body().isSuccess()) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(ResetPasswordActivity.this);
                        alert.setTitle("Success");
                        alert.setMessage("Reset new password successfully🎉🎉\nPlease return to the login page");
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                startActivities(new Intent[]{intent});
                                finish();
                                dialog.dismiss();
                            }
                        });
                        alert.show();
                    }
                    else {
                        Toast.makeText(ResetPasswordActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                    Toast.makeText(ResetPasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private boolean validatePassword(){
        String password = etPassword.getText().toString();
        String passwordAgain = etPasswordAgain.getText().toString();
        if (password.isEmpty() || passwordAgain.isEmpty()){
            etPassword.setError("Password cannot be empty!");
            etPasswordAgain.setError("Confirm field cannot be empty!");
            return false;
        } else if (password.length() < 8){
            etPassword.setError("Length of password must be longer than 8 characters");
            return false;
        }else if (!password.equals(passwordAgain)){
            etPasswordAgain.setError("Confirm field is different from password");
            return false;
        }else {
            etPassword.setError(null);
            etPasswordAgain.setError(null);
            return true;
        }
    }

    public void initialize(){
        etToken1 = findViewById(R.id.etToken1);
        etToken2 = findViewById(R.id.etToken2);
        etToken3 = findViewById(R.id.etToken3);
        etToken4 = findViewById(R.id.etToken4);
        etToken5 = findViewById(R.id.etToken5);
        etToken6 = findViewById(R.id.etToken6);
        etPassword = findViewById(R.id.etPassword);
        etPasswordAgain = findViewById(R.id.etPasswordAgain);
        cbShowPassword = findViewById(R.id.cbShowPassword);
        tvNotify = findViewById(R.id.tvNotify);
        btnContinue = findViewById(R.id.btnContinue);
    }
}