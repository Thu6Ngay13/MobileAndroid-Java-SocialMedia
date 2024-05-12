package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.OtpResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyNewAccountActivity extends AppCompatActivity {
    private EditText etToken1, etToken2, etToken3, etToken4, etToken5, etToken6;
    private TextView tvEmail;
    private Button btnContinue;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_new_account);
        initialize();
        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra("registeredEmail"); // Lấy dữ liệu từ Intent bằng key
            tvEmail.setText("Enter the code we sent to " + email);
        }
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
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmToken();
            }
        });
    }

    private void confirmToken() {
        String token = etToken1.getText().toString() +
                etToken2.getText().toString() +
                etToken3.getText().toString() +
                etToken4.getText().toString() +
                etToken5.getText().toString() +
                etToken6.getText().toString();
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.confirmToken(token).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful()){
                    AlertDialog.Builder alert = new AlertDialog.Builder(VerifyNewAccountActivity.this);
                    alert.setTitle("Success");
                    alert.setMessage("Account confirmation successful🎉🎉\nPlease return to the login page");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(VerifyNewAccountActivity.this, LoginActivity.class);
                            startActivities(new Intent[]{intent});
                            finish();
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
                else{
                    Toast.makeText(VerifyNewAccountActivity.this, "An error occurred please try again later...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(VerifyNewAccountActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initialize(){
        etToken1 = findViewById(R.id.etToken1);
        etToken2 = findViewById(R.id.etToken2);
        etToken3 = findViewById(R.id.etToken3);
        etToken4 = findViewById(R.id.etToken4);
        etToken5 = findViewById(R.id.etToken5);
        etToken6 = findViewById(R.id.etToken6);
        tvEmail = findViewById(R.id.tvNotify);
        btnContinue = findViewById(R.id.btnContinue);
    }
}