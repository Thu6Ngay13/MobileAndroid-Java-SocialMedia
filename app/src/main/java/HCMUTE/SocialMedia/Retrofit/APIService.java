package HCMUTE.SocialMedia.Retrofit;

import java.util.Map;

import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.CommentCardModel;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.FriendModel;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.Models.NotifyCardModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Requests.AuthRequest;
import HCMUTE.SocialMedia.Requests.ResetPasswordRequest;
import HCMUTE.SocialMedia.Responses.AuthResponse;
import HCMUTE.SocialMedia.Responses.ResetPasswordResponse;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import HCMUTE.SocialMedia.Requests.RegisterRequest;
import HCMUTE.SocialMedia.Responses.AuthResponse;
import HCMUTE.SocialMedia.Responses.OtpResponse;
import HCMUTE.SocialMedia.Responses.RegisterResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    //    Call API POST
    @GET("post/{username}/{page}/{pageSize}")
    Call<ResponseModel<PostCardModel>> getPostOfNewFeedWithUsername(@Path("username") String username, @Path("page") int page, @Path("pageSize") int pageSize);

    @POST("post/{username}/like/{postId}")
    Call<ResponseModel<String>> likePost(@Path("username") String username, @Path("postId") long postId);

    @POST("post/{username}/share/{postId}")
    Call<ResponseModel<String>> sharePost(@Path("username") String username, @Path("postId") long postId);

    @POST("post/{username}/unlike/{postId}")
    Call<ResponseModel<String>> unlikePost(@Path("username") String username, @Path("postId") long postId);

    @POST("post/create")
    Call<PostCardModel> createPost(@Body PostCardModel postCardModel);

    @POST("post/media")
    Call<ResponseModel<String>> mediaPost(@Part MultipartBody.Part part);


    //    Call API FRIEND
    @GET("friend/yourfriend/{username}")
    Call<ResponseModel<FriendModel>> getYourFriendsWithUsername(@Path("username") String username);

    @GET("friend/friendrequest/{username}")
    Call<ResponseModel<FriendModel>> getFriendRequestsWithUsername(@Path("username") String username);

    @POST("friend/friendrequest/{username1}/accept/{username2}")
    Call<ResponseModel<String>> acceptFriend(@Path("username1") String username1, @Path("username2") String username2);

    @POST("friend/friendrequest/{username1}/decline/{username2}")
    Call<ResponseModel<String>> declineFriend(@Path("username1") String username1, @Path("username2") String username2);

    //    Call API NOTIFY
    @GET("notification/{username}")
    Call<ResponseModel<NotifyCardModel>> getNotificationReceiptsWithUsername(@Path("username") String username);

    //    Call API Conversation
    @GET("conversation/{username}")
    Call<ResponseModel<ConversationCardModel>> getConversationsWithUsername(@Path("username") String username);

    @GET("conversation/{username1}/withfriend/{username2}")
    Call<ResponseModel<ConversationCardModel>> getConversationWithFriend(@Path("username1") String username1, @Path("username2") String username2);

    //    Call API MESSAGE
    @GET("message/{conversationId}/{username}")
    Call<ResponseModel<MessageModel>> getMessagesWithConversationIdAndUsername(@Path("conversationId") long conversationId, @Path("username") String username);

    @Multipart
    @POST("message/sendmessage")
    Call<ResponseModel<String>> sendMessage(@Part("jsonBody") RequestBody jsonBody);

    @Multipart
    @POST("message/sendmedia")
    Call<ResponseModel<String>> sendMedia(@Part("jsonBody") RequestBody jsonBody, @Part MultipartBody.Part part);

    @GET("post/{username}")
    Call<ResponseModel<PostCardModel>> getPostsWithUsername(@Path("username") String username);

    //    Call API ACCOUNT
    @POST("v1/auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @GET("v1/auth/register/confirm")
    Call<OtpResponse> confirmToken(@Query("token") String token);

    @POST("v1/auth/authenticate")
    Call<AuthResponse> authenticate(@Body AuthRequest request);
    @POST("v1/auth/find-account")
    Call<SimpleResponse<AccountCardModel>> findByEmail(@Query("email") String email);
    @POST("v1/auth/send-email")
    Call<SimpleResponse<String>> sendEmail(@Body Map<String, String> reqBody);
    @POST("v1/auth/reset-password")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest request);
    @GET("user/my-account/{username}")
    Call<SimpleResponse<AccountCardModel>> getAccountByUsername(@Path("username") String username);
    @GET("comment/{postId}")
    Call<ResponseModel<CommentCardModel>> getCommentWithPostId(@Path("postId") Long postId);

}
