package HCMUTE.SocialMedia.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.CommentCardAdapter;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Models.CommentCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
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

public class CommentActivity extends AppCompatActivity {
    public CommentActivity(){}
    public static final int MY_REQUEST_CODE = 100;
    public static String[] storage_permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    public static String[] storage_permissions_33 = {"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_VIDEO"};
    private APIService apiService;
    private String fullName = PrefManager.getFullname();
    private String avatar = PrefManager.getAvatarURL();
    private String username = PrefManager.getUsername();
    private String commentImage = "";
    private ImageView ivCommentImage, ivMedia, ivSent;
    private EditText etComment;
    long postId = -1;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        String id = getIntent().getStringExtra("postId");
        postId = Long.valueOf(id);
        initialize();
        createComment();
        loadComments();
    }

    public void loadComments()
    {
        final List<CommentCardModel> commentCardModels = new ArrayList<>();

        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getCommentWithPostId(postId).enqueue(new Callback<ResponseModel<CommentCardModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<CommentCardModel>> call, Response<ResponseModel<CommentCardModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<CommentCardModel> responseModel = response.body();
                    List<CommentCardModel> responseModelResult = new ArrayList<>();
                    if (responseModel != null && responseModel.isSuccess()) {
                        responseModelResult = responseModel.getResult();
                        commentCardModels.addAll(responseModelResult);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this.getApplicationContext()));
                        recyclerView.setAdapter(new CommentCardAdapter(CommentActivity.this.getApplicationContext(), commentCardModels));
                    }
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<CommentCardModel>> call, Throwable t) {
            }
        });

    }
    private void createComment() {
        ivMedia.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View view) {
                CheckPermission();
            }
        });

        if (commentImage == "") {
            ivCommentImage.setVisibility(View.GONE);
        } else {
            ivCommentImage.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(commentImage).into(ivCommentImage);
        }

        ivSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = etComment.getText().toString();
                CommentCardModel comment = new CommentCardModel(avatar, username, fullName,  commentText, commentImage,false,"time",postId);
                etComment.setText("");
                ivCommentImage.setVisibility(View.GONE);
                apiService = RetrofitClient.getRetrofit().create(APIService.class);
                Call<CommentCardModel> call = apiService.createComment(comment);
                call.enqueue(new Callback<CommentCardModel>() {
                    @Override
                    public void onResponse(Call<CommentCardModel> call, Response<CommentCardModel> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Create comment successful", Toast.LENGTH_LONG).show();
                            loadComments();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Vui lòng thêm nội dung!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<CommentCardModel> call, Throwable t) {
                        Log.d("Activity", "Request failed: " + t.getMessage());
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
                    Log.d("Activity", "Failed in startForResult" + e.getMessage());
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
                        commentImage = responseModel.getResult();
                        if (commentImage == "") {
                            ivCommentImage.setVisibility(View.GONE);
                        } else {
                            ivCommentImage.setVisibility(View.VISIBLE);
                            Glide.with(getApplicationContext()).load(commentImage).into(ivCommentImage);
                        }
                    } else {
                        int statusCode = response.code();
                        Log.d("Activity", "Request error: " + statusCode);
                        // Handle request errors depending on status code
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponse<String>> call, Throwable t) {
                    Log.d("Activity", "Request failed: " + t.getMessage());
                }
            });
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startForResult.launch(Intent.createChooser(intent, "Select Picture"));
    }


    public void initialize(){
        recyclerView = (RecyclerView) CommentActivity.this.findViewById(R.id.rvCommentArea);
        ivCommentImage = findViewById(R.id.ivCommentImage);
        ivSent = findViewById(R.id.ivSent);
        ivMedia = findViewById(R.id.ivMedia);
        etComment = findViewById(R.id.etComment);
    }
}