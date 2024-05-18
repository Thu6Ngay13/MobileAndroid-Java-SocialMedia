package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import HCMUTE.SocialMedia.Adapters.CreatePostAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.RegisterResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private APIService apiService;
    private String fullName = "Phap Nguyen";
    private String avatar = "link";
    private String username = "phap";
    private ImageView ivBack;
    private Button btPost;
    private TextView tvFullName;
    private TextView ivAvatar;
    private EditText etTextPost;
    private ImageView ivPostImage;
    private Spinner spin;
    String[] modeName={"Public","Private"};
    int modeImage[] = {R.mipmap.ic_global_72_dark, R.mipmap.ic_lock_72_dark};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        initialize();
        loadData();
//        createPost();
//        ivBack.setOnClickListener(v -> finish());
    }

    private void createPost() {
        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postText = etTextPost.getText().toString();
                String postMedia = "";
                PostCardModel post = new PostCardModel(avatar, username, fullName, "time", 1, postText,postMedia,false);
                Call<PostCardModel> call = apiService.createPost(post);
                call.enqueue(new Callback<PostCardModel>() {
                    @Override
                    public void onResponse(Call<PostCardModel> call, Response<PostCardModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CreatePostActivity.this, "Create post successful", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(CreatePostActivity.this, "An error occurred please try again later ...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<PostCardModel> call, Throwable t) {
                        Toast.makeText(CreatePostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadData(){
        spin.setOnItemSelectedListener(this);
        CreatePostAdapter createPostAdapter = new CreatePostAdapter(CreatePostActivity.this, modeImage, modeName);
        spin.setAdapter(createPostAdapter);
//        apiService = RetrofitClient.getRetrofit().create(APIService.class);
//        tvFullName.setText(Const.FULLNAME);
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(getApplicationContext(), modeName[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void initialize(){
        spin = (Spinner) findViewById(R.id.spModePost);
        ivBack = findViewById(R.id.ivBack);
        btPost = findViewById(R.id.btPost);
        tvFullName = findViewById(R.id.tvFullName);
        ivAvatar = findViewById(R.id.ivAvatar);
        etTextPost = findViewById(R.id.etTextPost);
        ivPostImage = findViewById(R.id.ivPostImage);
    }
}