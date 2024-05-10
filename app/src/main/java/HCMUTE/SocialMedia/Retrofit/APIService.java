package HCMUTE.SocialMedia.Retrofit;

import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.FriendModel;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.Models.NotifyCardModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIService {

    @GET("post/{username}")
    Call<ResponseModel<PostCardModel>> getPostsWithUsername(@Path("username") String str);

    @GET("friend/yourfriend/{username}")
    Call<ResponseModel<FriendModel>> getYourFriendsWithUsername(@Path("username") String str);

    @GET("friend/friendrequest/{username}")
    Call<ResponseModel<FriendModel>> getFriendRequestsWithUsername(@Path("username") String str);

    @GET("notification/{username}")
    Call<ResponseModel<NotifyCardModel>> getNotificationReceiptsWithUsername(@Path("username") String str);

    @GET("conversation/{username}")
    Call<ResponseModel<ConversationCardModel>> getConversationsWithUsername(@Path("username") String str);

    @GET("message/{conversationId}/{username}")
    Call<ResponseModel<MessageModel>> getMessagesWithConversationIdAndUsername(@Path("conversationId") long j, @Path("username") String str);

    @Multipart
    @POST("message/sendmessage")
    Call<ResponseModel<String>> sendMessage(@Part("jsonBody") RequestBody jsonBody);

    @Multipart
    @POST("message/sendmedia")
    Call<ResponseModel<String>> sendMedia(@Part("jsonBody") RequestBody jsonBody, @Part MultipartBody.Part part);

}
