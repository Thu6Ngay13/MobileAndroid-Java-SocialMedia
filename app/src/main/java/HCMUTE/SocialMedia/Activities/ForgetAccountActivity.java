package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.AuthResponse;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetAccountActivity extends AppCompatActivity {

    private ImageView ivBack;
    private Button btnFindAccount;
    private EditText etEmail;
    private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_account);
        initialize();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnFindAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findByEmail(etEmail.getText().toString());
            }
        });
    }

    private void findByEmail(String email) {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        Call<SimpleResponse<AccountCardModel>> call = apiService.findByEmail(email);
        call.enqueue(new Callback<SimpleResponse<AccountCardModel>>() {
            @Override
            public void onResponse(Call<SimpleResponse<AccountCardModel>> call, Response<SimpleResponse<AccountCardModel>> response) {
                if (response.isSuccessful()){
                    SimpleResponse<AccountCardModel> simpleResponse = response.body();
                    Gson gson = new Gson();
                    if (simpleResponse.isSuccess()) {
                        AccountCardModel model = gson.fromJson(gson.toJson(simpleResponse.getResult()), AccountCardModel.class);
                        Intent intent = new Intent(ForgetAccountActivity.this, FindAccountActivity.class);
                        intent.putExtra("findaccount", model);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(ForgetAccountActivity.this, "list null", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ForgetAccountActivity.this, "Account does not exist, please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse<AccountCardModel>> call, Throwable t) {
                Log.e("NetworkError", "Error: " + t.getMessage());
            }
        });
    }

    private void initialize(){
        ivBack = findViewById(R.id.ivBack);
        btnFindAccount = findViewById(R.id.btnFindAccount);
        etEmail = findViewById(R.id.etEmail);
    }
}