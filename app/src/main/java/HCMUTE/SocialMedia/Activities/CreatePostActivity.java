package HCMUTE.SocialMedia.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.CreatePostAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.RegisterResponse;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import HCMUTE.SocialMedia.Utils.ProcessTime;
import HCMUTE.SocialMedia.Utils.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public static final int MY_REQUEST_CODE = 100;
    public static String[] storage_permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    public static String[] storage_permissions_33 = {"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_VIDEO"};

    private APIService apiService;
    private String fullName = Const.FULLNAME;
    private String avatar = Const.AVATAR;
    private String username = Const.USERNAME;
    private String postMedia = "";
    private long modeId = 0;
    private ImageView ivBack, ivAvatar, ivPostImage, ivMedia;
    private Button btPost;
    private TextView tvFullName;
    private EditText etTextPost;
    private Spinner spin;
    String[] modeName={"Public", "Friend", "Private"};
    int modeImage[] = {R.mipmap.ic_global_72_dark, R.mipmap.ic_friend_72_full, R.mipmap.ic_lock_72_dark};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        initialize();

        loadData();
        createPost();
        ivBack.setOnClickListener(v -> finish());
    }

    private void createPost() {

        ivMedia.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View view) {
                CheckPermission();
            }
        });

        if (postMedia == "") {
            ivPostImage.setVisibility(View.GONE);
        } else {
            ivPostImage.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(postMedia).into(ivPostImage);
        }

        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postText = etTextPost.getText().toString();
                PostCardModel post = new PostCardModel(avatar, username, fullName, "time", modeId, postText, postMedia,false);
                etTextPost.setText("");
                ivPostImage.setVisibility(View.GONE);

                Call<PostCardModel> call = apiService.createPost(post);
                call.enqueue(new Callback<PostCardModel>() {
                    @Override
                    public void onResponse(Call<PostCardModel> call, Response<PostCardModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Create post successful", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Vui lòng thêm nội dung!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<PostCardModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadData(){
        spin.setOnItemSelectedListener(this);
        CreatePostAdapter createPostAdapter = new CreatePostAdapter(CreatePostActivity.this, modeImage, modeName);
        spin.setAdapter(createPostAdapter);
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        tvFullName.setText(fullName);
        Glide.with(getApplicationContext()).load(avatar).into(ivAvatar);

    }

    private void CheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermissions(permissions(), MY_REQUEST_CODE);
                openGallery();
            }
        }
    }

    public static String[] permissions() {

        Log.d("CreatePostActivity", "123456");
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p = storage_permissions;
        }
        return p;
    }

    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o != null && o.getResultCode() == RESULT_OK && o.getData() != null && o.getData().getData() != null) {
                try {
                    Uri selectedFileUri = o.getData().getData();
                    mediaPost(selectedFileUri);
                } catch (Exception e) {
                    Log.d("CreatePostActivity", "Failed in startForResult" + e.getMessage());
                }
            }
        }
    });

    private void mediaPost(Uri selectedFileUri) {
        if (selectedFileUri == null) {
            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_LONG).show();
        } else {
            // Lấy đường dẫn thực tế của file
            String IMAGE_PATH = RealPathUtil.getRealPath(getApplicationContext(), selectedFileUri);
            File file = new File(IMAGE_PATH);
            String fileName = file.getName();
            // Tạo RequestBody cho phần multipart
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part multipart = MultipartBody.Part.createFormData("media", fileName, requestBody);
            apiService = RetrofitClient.getRetrofit().create(APIService.class);
            apiService.mediaPost(multipart).enqueue(new Callback<SimpleResponse<String>>() {
                @Override
                public void onResponse(Call<SimpleResponse<String>> call, Response<SimpleResponse<String>> response) {
                    if (response.isSuccessful()) {
                        SimpleResponse<String> responseModel = response.body();
                        postMedia = responseModel.getResult();
                        if (postMedia == "") {
                            ivPostImage.setVisibility(View.GONE);
                        } else {
                            ivPostImage.setVisibility(View.VISIBLE);
                            Glide.with(getApplicationContext()).load(postMedia).into(ivPostImage);
                        }
                    } else {
                        int statusCode = response.code();
                        Log.d("CreatePostActivity", "Request error: " + statusCode);
                        // Handle request errors depending on status code
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponse<String>> call, Throwable t) {
                    Log.d("CreatePostActivity", "Request failed: " + t.getMessage());
                }
            });
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        Log.d("CreatePostActivity", "154851158");
        startForResult.launch(Intent.createChooser(intent, "Select Picture"));
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        modeId = position+1;
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
        ivMedia = findViewById(R.id.ivMedia);
    }
}