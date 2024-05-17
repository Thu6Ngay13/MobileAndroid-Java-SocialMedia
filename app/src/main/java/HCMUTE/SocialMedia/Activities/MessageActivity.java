package HCMUTE.SocialMedia.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.MessageAdapter;
import HCMUTE.SocialMedia.Enums.TypeMessageEnum;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.RealTime.SocketIO;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.Utils.RealPathUtil;
import io.socket.emitter.Emitter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {
    public static final int MY_REQUEST_CODE = 100;
    private static final String TAG = "MessageActivity";
    private static final String USERNAME = "abc";

    public static String[] storage_permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    public static String[] storage_permissions_33 = {"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_VIDEO"};

    private long conversationId;

    private ImageButton btSendMedia;
    private ImageButton btSendMessage;

    private EditText etTypeMessage;
    private ImageButton ibBack;

    private MessageAdapter messageCardAdapter;
    private List<MessageModel> messageCards;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageCards = new ArrayList<>();
        recyclerView = findViewById(R.id.rvMessageArea);

        messageCardAdapter = new MessageAdapter(getApplicationContext(), messageCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(messageCardAdapter);

        ibBack = findViewById(R.id.ibBack);
        etTypeMessage = findViewById(R.id.etTypeMessage);
        btSendMedia = findViewById(R.id.btSendMedia);
        btSendMessage = findViewById(R.id.btSendMessage);

        ibBack.setOnClickListener(v -> finish());
        btSendMedia.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View view) {
                CheckPermission();
            }
        });
        btSendMessage.setOnClickListener(v -> onClickSendMessage());

        Intent intent = getIntent();
        conversationId = intent.getLongExtra("conversationId", -1L);

        getMessage();
        connectToSocketServer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketIO.offMessage(onReceiveMessage);
    }

    private void getMessage() {
        APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getMessagesWithConversationIdAndUsername(this.conversationId, USERNAME).enqueue(new Callback<ResponseModel<MessageModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<MessageModel>> call, Response<ResponseModel<MessageModel>> response) {
                if (response.isSuccessful()) {
                    ResponseModel<MessageModel> responseModel = response.body();
                    if (responseModel != null && responseModel.isSuccess()) {
                        List<MessageModel> responseModelResult = responseModel.getResult();
                        messageCardAdapter.addItems(responseModelResult, recyclerView);
                    }
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<MessageModel>> call, Throwable t) {
            }
        });
    }

    private void connectToSocketServer() {
        SocketIO.onMessage(onReceiveMessage);
    }

    private final Emitter.Listener onReceiveMessage = args -> {
        try {
            JSONObject jsonReceive = new JSONObject(args[0].toString());
            Log.d("", "" + jsonReceive.toString());

            String typeReceiveString = jsonReceive.getString("typeSender");
            TypeMessageEnum typeReceive = TypeMessageEnum.fromString(typeReceiveString);
            if (typeReceive == null) return;

            String fullname = jsonReceive.getString("fullname");
            String messageSendingAt = jsonReceive.getString("messageSendingAt");
            String message = jsonReceive.getString("message");
            String mediaURL = jsonReceive.getString("media");

            List<MessageModel> messageModels = new ArrayList<>();
            messageModels.add(new MessageModel(typeReceive, fullname, messageSendingAt, message, mediaURL));
            messageCardAdapter.addItems(messageModels, recyclerView);
        } catch (JSONException e) {
            Log.d(TAG, "Failed on onReceiveMessage: " + e.getMessage());
        }
    };

    private void onClickSendMessage() {
        try {
            String message = String.valueOf(etTypeMessage.getText()).trim();
            String messageSendingAt = Calendar.getInstance().getTime().toString();

            if (message.isEmpty()) return;
            etTypeMessage.setText("");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("typeSender", "MESSAGE");
            jsonObject.put("conversationId", conversationId);
            jsonObject.put("username", USERNAME);
            jsonObject.put("fullname", USERNAME);
            jsonObject.put("messageSendingAt", messageSendingAt);
            jsonObject.put("message", message);
            jsonObject.put("media", "");

            saveToServer(jsonObject, null);
        } catch (Exception e) {
            Log.d(TAG, "Failed on onClickSendMessage: " + e.getMessage());
        }
    }

    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o != null && o.getResultCode() == RESULT_OK && o.getData() != null && o.getData().getData() != null) {
                try {
                    Uri selectedFileUri = o.getData().getData();
                    String messageSendingAt = Calendar.getInstance().getTime().toString();

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("typeSender", "MEDIA");
                    jsonObject.put("conversationId", conversationId);
                    jsonObject.put("username", USERNAME);
                    jsonObject.put("fullname", USERNAME);
                    jsonObject.put("messageSendingAt", messageSendingAt);
                    jsonObject.put("message", "");
                    jsonObject.put("media", "");

                    saveToServer(jsonObject, selectedFileUri);
                } catch (Exception e) {
                    Log.d(TAG, "Failed in startForResult" + e.getMessage());
                }
            }
        }
    });

    private void saveToServer(JSONObject jsonObject, Uri selectedFileUri) {
        // Tạo RequestBody cho phần JSON
        RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        if (selectedFileUri == null) {
            APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
            apiService.sendMessage(jsonBody).enqueue(new Callback<ResponseModel<String>>() {
                @Override
                public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                    if (response.isSuccessful()) {
                        ResponseModel<String> responseModel = response.body();

                    } else {
                        int statusCode = response.code();
                        // handle request errors depending on status code
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                }
            });
        } else {
            // Lấy đường dẫn thực tế của file
            String IMAGE_PATH = RealPathUtil.getRealPath(getApplicationContext(), selectedFileUri);
            File file = new File(IMAGE_PATH);
            String fileName = file.getName();

            // Tạo RequestBody cho phần multipart
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part multipart = MultipartBody.Part.createFormData("media", fileName, requestBody);

            APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
            apiService.sendMedia(jsonBody, multipart).enqueue(new Callback<ResponseModel<String>>() {
                @Override
                public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                    if (response.isSuccessful()) {
                        ResponseModel<String> responseModel = response.body();

                    } else {
                        int statusCode = response.code();
                        // handle request errors depending on status code
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void CheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermissions(permissions(), MY_REQUEST_CODE);
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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startForResult.launch(Intent.createChooser(intent, "Select Picture"));
    }
}