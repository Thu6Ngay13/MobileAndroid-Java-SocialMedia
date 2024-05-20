package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.AuthResponse;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindAccountActivity extends AppCompatActivity {
    private TextView tvUsername;
    private CircleImageView civAvatar;
    private Button btnContinue;
    private ImageView ivBack;
    private APIService apiService;
    private AccountCardModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_account);
        initialize();
        model = new AccountCardModel();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            model.setUsername(bundle.getString("FIND_USERNAME"));
            model.setFullname(bundle.getString("FIND_FULLNAME"));
            model.setAvatarURL(bundle.getString("FIND_AVATARURL"));
            model.setEmail(bundle.getString("FIND_EMAIL"));
        }
        tvUsername.setText(model.getUsername());
        Glide.with(this)
                .load(model.getAvatarURL())
                .into(civAvatar);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(model.getEmail());
            }
        });
    }

    private void sendEmail(String email) {
        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("email", email);
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        Call<SimpleResponse<String>> call = apiService.sendEmail(reqBody);
        call.enqueue(new Callback<SimpleResponse<String>>() {
            @Override
            public void onResponse(Call<SimpleResponse<String>> call, Response<SimpleResponse<String>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccess()){
                        Intent intent = new Intent(FindAccountActivity.this, ResetPasswordActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("RESET_USERNAME", model.getUsername());
                        bundle.putString("RESET_AVATARURL", model.getAvatarURL());
                        bundle.putString("RESET_EMAIL", model.getEmail());
                        bundle.putString("RESET_FULLNAME", model.getFullname());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(FindAccountActivity.this, "An error occurred please try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse<String>> call, Throwable t) {
                Toast.makeText(FindAccountActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialize(){
        tvUsername = findViewById(R.id.tvUsername);
        civAvatar = findViewById(R.id.civAvatar);
        btnContinue = findViewById(R.id.btnContinue);
        ivBack = findViewById(R.id.ivBack);
    }
}