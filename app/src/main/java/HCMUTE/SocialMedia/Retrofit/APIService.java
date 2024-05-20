package HCMUTE.SocialMedia.Retrofit;

import java.util.Map;

import HCMUTE.SocialMedia.Models.AccountCardModel;
import HCMUTE.SocialMedia.Models.BanAccountModel;
import HCMUTE.SocialMedia.Models.CommentCardModel;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.FriendModel;
import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.Models.NotifyCardModel;
import HCMUTE.SocialMedia.Models.PostCardModel;
import HCMUTE.SocialMedia.Models.ReportPostModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Models.SearchModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.Requests.AuthRequest;
import HCMUTE.SocialMedia.Requests.RegisterRequest;
import HCMUTE.SocialMedia.Requests.ResetPasswordRequest;
import HCMUTE.SocialMedia.Responses.AuthResponse;
import HCMUTE.SocialMedia.Responses.OtpResponse;
import HCMUTE.SocialMedia.Responses.RegisterResponse;
import HCMUTE.SocialMedia.Responses.ResetPasswordResponse;
import HCMUTE.SocialMedia.Responses.SimpleResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("post/{username}")
    Call<ResponseModel<PostCardModel>> getPostsWithUsername(@Path("username") String username);

    @POST("post/create")
    Call<PostCardModel> createPost(@Body PostCardModel postCardModel);

    @Multipart
    @POST("post/media")
    Call<SimpleResponse<String>> mediaPost(@Part MultipartBody.Part part);

    //    Call API FRIEND
    @GET("friend/yourfriend/{username}")
    Call<ResponseModel<FriendModel>> getYourFriendsWithUsername(@Path("username") String username);

    @GET("friend/friendrequest/{username}")
    Call<ResponseModel<FriendModel>> getFriendRequestsWithUsername(@Path("username") String username);

    @POST("friend/friendrequest/{username1}/accept/{username2}")
    Call<ResponseModel<String>> acceptFriend(@Path("username1") String username1, @Path("username2") String username2);

    @POST("friend/friendrequest/{username1}/decline/{username2}")
    Call<ResponseModel<String>> declineFriend(@Path("username1") String username1, @Path("username2") String username2);

    @POST("friend/{username1}/make/{username2}")
    Call<ResponseModel<String>> makeFriend(@Path("username1") String username1, @Path("username2") String username2);

    @POST("friend/{username1}/unmake/{username2}")
    Call<ResponseModel<String>> unmakeFriend(@Path("username1") String username1, @Path("username2") String username2);
    @POST("friend/{usernameYou}/unfriend/{usernameFriend}")
    Call<ResponseModel<String>> unfriend(@Path("usernameYou") String usernameYou, @Path("usernameFriend") String usernameFriend);

    //    Call API NOTIFY
    @GET("notification/{username}")
    Call<ResponseModel<NotifyCardModel>> getNotificationReceiptsWithUsername(@Path("username") String username);

    //    Call API CONVERSATION
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

    //Call API ACCOUNT
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

    @GET("user/my-account/{username}/friend-account/{usernameFriend}")
    Call<SimpleResponse<AccountCardModel>> getFriendAccountByUsername(@Path("username") String username, @Path("usernameFriend") String usernameFriend);

    @PUT("user/my-account/update")
    Call<SimpleResponse<String>> updateProfile(
            @Query("fullname") String fullname,
            @Query("gender") String gender,
            @Query("description") String description,
            @Query("company") String company,
            @Query("location") String location,
            @Query("isSingle") boolean isSingle,
            @Query("username") String username
    );
    @PUT("user/my-account/{username}/updateAvatar")
    Call<SimpleResponse<String>> updateAvatar(@Query("username") String username, @Body Map<String, String> reqBody);

    @GET("comment/{postId}")
    Call<ResponseModel<CommentCardModel>> getCommentWithPostId(@Path("postId") Long postId);

    @POST("comment/create")
    Call<CommentCardModel> createComment(@Body CommentCardModel commentCardModel);

    @PUT("comment/delete/{commentId}")
    Call<CommentCardModel> deleteComment(@Path("commentId") Long commentId);

    //    Call API GROUP
    @GET("group/posts/{username}")
    Call<ResponseModel<PostCardModel>> getPostInGroupsByUsername(@Path("username") String username);

    @GET("group/groups/{username}")
    Call<ResponseModel<GroupModel>> getGroupsByUsername(@Path("username") String username);

    @POST("group/createGroup")
    Call<SimpleResponse<GroupModel>> createGroup(
            @Query("username") String username,
            @Query("groupName") String groupName,
            @Query("modeId") long modeId,
            @Query("description") String description
    );

    @POST("group/updateGroup")
    Call<SimpleResponse<GroupModel>> updateGroup(
            @Query("groupId") long groupId,
            @Query("username") String username,
            @Query("groupName") String groupName,
            @Query("modeId") long modeId,
            @Query("description") String description,
            @Query("groupImage") String groupImage
    );
    @GET("group/memberInOneGroup")
    Call<ResponseModel<YourFriendModel>> getMembersInGroup(
            @Query("groupId") long groupId);

    @GET("group/listAcceptMemberGroup")
    Call<ResponseModel<AccountCardModel>> listAcceptMemberGroup(
            @Query("groupId") long groupId);

    @GET("group/postInOneGroup")
    Call<ResponseModel<PostCardModel>> getPostsInGroup(
            @Query("username") String username,
            @Query("groupId") long groupId);
    @GET("group/{groupId}")
    Call<SimpleResponse<GroupModel>> getGroupByGroupId(@Path("groupId") Long groupId);

    @POST("group/createPost")
    Call<PostCardModel> createPostInGroup(@Body PostCardModel postCardModel);
    //    Call API GROUP
    @GET("group/{username}/viewgroup/{groupId}")
    Call<ResponseModel<GroupModel>> viewGroupByUsernameAndGroupId(@Path("username") String username, @Path("groupId") long groupId);

    @GET("group/{username}/joingroup/{groupId}")
    Call<ResponseModel<String>> joinGroupByUsernameAndGroupId(@Path("username") String username, @Path("groupId") long groupId);

    @GET("group/{username}/unjoingroup/{groupId}")
    Call<ResponseModel<String>> unjoinGroupByUsernameAndGroupId(@Path("username") String username, @Path("groupId") long groupId);

    @GET("group/isAcceptGroup")
    Call<SimpleResponse<String>> isAcceptGroup(
            @Query("username") String username,
            @Query("groupId") long groupId);

    @GET("group/acceptMember")
    Call<SimpleResponse<String>> acceptMember(
            @Query("username") String username,
            @Query("groupId") long groupId);


    @GET("group/findOne")
    Call<SimpleResponse<GroupModel>> findOne(
            @Query("groupId") long groupId);
    //    Call API SEARCH
    @GET("search/{username}")
    Call<ResponseModel<SearchModel>> getSuggestFriend(@Path("username") String username);

    @GET("search/{username}/{keyword}")
    Call<ResponseModel<SearchModel>> search(@Path("username") String username, @Path("keyword") String keyword);

    //    Call API REPORT
    @Multipart
    @POST("report/{username}/report/{postId}")
    Call<ResponseModel<String>> reportPost(@Path("username") String username, @Path("postId") long postId, @Part("jsonBody") RequestBody jsonBody);

    //    Call API ADMIN
    //    Call API ACCOUNT
    @GET("v1/admin/banaccount")
    Call<ResponseModel<BanAccountModel>> getAllAccount();

    @POST("v1/admin/banaccount/ban/{username}")
    Call<ResponseModel<String>> banAccount(@Path("username") String username);

    @POST("v1/admin/banaccount/unban/{username}")
    Call<ResponseModel<String>> unbanAccount(@Path("username") String username);

    //    Call API POST
    @GET("v1/admin/procesreport")
    Call<ResponseModel<ReportPostModel>> getAllReports();

    @GET("v1/admin/procesreport/{postId}")
    Call<ResponseModel<ReportPostModel>> getAllReportsWithPostId(@Path("postId") long postId);

    @POST("v1/admin/procesreport/{postId}/delete")
    Call<ResponseModel<String>> deletePost(@Path("postId") long postId);

    @POST("v1/admin/procesreport/{postId}/ignore")
    Call<ResponseModel<String>> ignoreAllReportOfPost(@Path("postId") long postId);
}
