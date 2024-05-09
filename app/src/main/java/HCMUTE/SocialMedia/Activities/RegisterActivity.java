package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import HCMUTE.SocialMedia.Helpers.StringHelper;
import HCMUTE.SocialMedia.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etBirthday, etEmail, etUsername, etPassword, etPasswordAgain, etFullName;
    private Button btnCreateAccount;
    private TextView tvGoToLogin;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();

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
                creatAccount();
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
        tvGoToLogin = (TextView) findViewById(R.id.tvLogin);
    }
    private void goToLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivities(new Intent[]{intent});
        finish();
    }
    private void creatAccount(){
        if (!validateFullName() || !validateEmail() || !validateBirthday()
                || !validateUsername()|| !validatePassword()){
            return;
        }
        Toast.makeText(this, "Create account successfully", Toast.LENGTH_SHORT).show();
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
        String dateFormat = "dd/MM/yyyy";
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
            etPasswordAgain.setError("Confirm field is different from pssword");
            return false;
        }else {
            etPassword.setError(null);
            etPasswordAgain.setError(null);
            return true;
        }
    }
}