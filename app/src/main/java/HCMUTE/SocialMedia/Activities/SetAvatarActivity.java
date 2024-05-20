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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import HCMUTE.SocialMedia.Utils.RealPathUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetAvatarActivity extends AppCompatActivity {

    public static final int MY_REQUEST_CODE = 100;
    public static String[] storage_permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    public static String[] storage_permissions_33 = {"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_VIDEO"};
    private APIService apiService;
    private String postMedia = PrefManager.getAvatarURL();
    private String fullName = PrefManager.getFullname();
    private String avatar = PrefManager.getAvatarURL();
    private String username = PrefManager.getUsername();
    private ImageView ivAvatar, ibBack;
    private Button btnSelectAvatar;
    private ImageButton ibUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_avatar);
        ivAvatar = findViewById(R.id.ivAvatar);
        ibBack = findViewById(R.id.ibBack);
        btnSelectAvatar = findViewById(R.id.btnSelectAvatar);
        ibUpdate = findViewById(R.id.ibUpdate);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        selectAvatar();
    }

    private void selectAvatar() {
        btnSelectAvatar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View v) {
                CheckPermission();
            }
        });
        Glide.with(SetAvatarActivity.this).load(avatar).into(ivAvatar);
        ibUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> reqBody = new HashMap<>();
                reqBody.put("avatarURL", postMedia);
                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                apiService.updateAvatar(PrefManager.getUsername(), reqBody).enqueue(new Callback<SimpleResponse<String>>() {
                    @Override
                    public void onResponse(Call<SimpleResponse<String>> call, Response<SimpleResponse<String>> response) {
                        if (response.isSuccessful()){
                            if (response.body().isSuccess()){
                                Toast.makeText(SetAvatarActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                                finish();
                                PrefManager.setAvatarURL(postMedia);
                            }
                        }
                        else{
                            Toast.makeText(SetAvatarActivity.this, "Update fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse<String>> call, Throwable t) {
                        Toast.makeText(SetAvatarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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

        Log.d("SetAvatarActivity", "123456");
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p = storage_permissions;
        }
        return p;
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        Log.d("CreatePostActivity", "154851158");
        startForResult.launch(Intent.createChooser(intent, "Select Picture"));
    }
    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o != null && o.getResultCode() == RESULT_OK && o.getData() != null && o.getData().getData() != null) {
                try {
                    Uri selectedFileUri = o.getData().getData();
                    changeAvatar(selectedFileUri);
                } catch (Exception e) {
                    Log.d("CreatePostActivity", "Failed in startForResult" + e.getMessage());
                }
            }
        }
    });

    private void changeAvatar(Uri selectedFileUri) {
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
                        Glide.with(getApplicationContext()).load(postMedia).into(ivAvatar);
                    } else {
                        int statusCode = response.code();
                        Toast.makeText(SetAvatarActivity.this, "Upload fail", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponse<String>> call, Throwable t) {
                    Log.d("CreatePostActivity", "Request failed: " + t.getMessage());
                }
            });
        }
    }
}