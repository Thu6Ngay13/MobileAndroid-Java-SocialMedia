package HCMUTE.SocialMedia.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Requests.RegisterRequest;
import HCMUTE.SocialMedia.Responses.RegisterResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.Utils.StringHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etBirthday, etEmail, etUsername, etPassword, etPasswordAgain, etFullName;
    private Button btnCreateAccount;
    private TextView tvGoToLogin;
    private Calendar calendar;
    private CheckBox cbShowPassword;
    private RelativeLayout pbWait;
    private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
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
        // Go to login if you had your account.
        tvGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbWait.setVisibility(View.VISIBLE);
                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setUsername(etUsername.getText().toString());
                registerRequest.setEmail(etEmail.getText().toString());
                registerRequest.setFullname(etFullName.getText().toString());
                registerRequest.setDateOfBirth(etBirthday.getText().toString() + "T00:00:00");
                registerRequest.setPassword(etPasswordAgain.getText().toString());
                creatAccount(registerRequest);
            }
        });
        calendar = Calendar.getInstance();
    }
    private void initialize(){
        etFullName = (EditText) findViewById(R.id.etFullName);
        etBirthday = findViewById(R.id.etBirthday);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordAgain = (EditText) findViewById(R.id.etPasswordAgain);
        btnCreateAccount = (Button) findViewById(R.id.btnRegister);
        tvGoToLogin = (TextView) findViewById(R.id.tvGoToLogin);
        String textWithUnderline = "<u>Login</u>";
        tvGoToLogin.setText(Html.fromHtml(textWithUnderline));
        cbShowPassword = findViewById(R.id.cbShowPassword);
        pbWait = findViewById(R.id.pbWait);
        pbWait.setVisibility(View.GONE);
    }
    private void goToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivities(new Intent[]{intent});
        finish();
    }
    private void creatAccount(RegisterRequest request){
        if (!validateFullName() || !validateEmail() || !validateBirthday()
                || !validateUsername()|| !validatePassword()){
            return;
        }
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        Call<RegisterResponse> call = apiService.register(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Create account successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, VerifyNewAccountActivity.class);
                    intent.putExtra("registeredEmail", request.getEmail());
                    startActivity(intent);
                    pbWait.setVisibility(View.GONE);
                    finish();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "An error occurred please try again later ...", Toast.LENGTH_SHORT).show();
                }
                pbWait.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                pbWait.setVisibility(View.GONE);
            }
        });
    }
    public void showDatePickerDialog(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateEditTextBirthday();
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void updateEditTextBirthday() {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());

        etBirthday.setText(simpleDateFormat.format(calendar.getTime()));
    }
    private boolean validateFullName(){
        String firstName = etFullName.getText().toString();
        if (firstName.isEmpty()){
            etFullName.setError("Full name cannot be empty!");
            return false;
        } else {
            etFullName.setError(null);
            return true;
        }
    }
    private boolean validateEmail(){
        String email = etEmail.getText().toString();
        if (email.isEmpty()){
            etEmail.setError("Email cannot be empty!");
            return false;
        } else if (!StringHelper.regexEmailValidationPattern(email)){
            etEmail.setError("Please enter a valid email");
            return false;
        }else {
            etEmail.setError(null);
            return true;
        }
    }
    private boolean validateBirthday(){
        String birthday = etBirthday.getText().toString();
        if (birthday.isEmpty()){
            etBirthday.setError("Birthday cannot be empty!");
            return false;
        } else {
            etBirthday.setError(null);
            return true;
        }
    }
    private boolean validateUsername(){
        String username = etUsername.getText().toString();
        if (username.isEmpty()){
            etUsername.setError("Username cannot be empty!");
            return false;
        } else {
            etUsername.setError(null);
            return true;
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
}