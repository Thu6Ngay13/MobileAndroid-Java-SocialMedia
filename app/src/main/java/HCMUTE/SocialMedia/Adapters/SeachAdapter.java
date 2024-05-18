package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Activities.MessageActivity;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Enums.TypeSearchEnum;
import HCMUTE.SocialMedia.Holders.SearchHolder;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Models.SearchModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.Utils.ProcessTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeachAdapter extends RecyclerView.Adapter<SearchHolder>{
    private Context context;
    private List<SearchModel> searchs;

    public SeachAdapter(Context context, List<SearchModel> searchs) {
        this.context = context;
        this.searchs = searchs;
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TypeSearchEnum.YOUR_FRIEND.ordinal()) {
            return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.your_friend_view, parent, false));
        }
        else if (viewType == TypeSearchEnum.FRIEND_REQUEST.ordinal()) {
            return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.friend_request_view, parent, false));
        }
        else if (viewType == TypeSearchEnum.MAKE_FRIEND.ordinal() || viewType == TypeSearchEnum.MAKED_FRIEND.ordinal()) {
            return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.friend_search_view, parent, false));
        }
        else if (viewType == TypeSearchEnum.JOIN_GROUP.ordinal() || viewType == TypeSearchEnum.UNJOIN_GROUP.ordinal() || viewType == TypeSearchEnum.JOINED_GROUP.ordinal()) {
            return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.group_search_view, parent, false));
        }

        return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.group_search_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        SearchModel searchModel = this.searchs.get(position);
        Glide.with(this.context).load(searchModel.getAvatar()).into(holder.avatar);
        holder.fullName.setText(searchModel.getFullName());

        if (searchModel.getViewType() == TypeSearchEnum.YOUR_FRIEND) {
            Button btViewProfile = (Button) holder.itemView.findViewById(R.id.btViewProfile);
            Button btSendMessage = (Button) holder.itemView.findViewById(R.id.btSendMessage);
            btViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            btSendMessage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.getConversationWithFriend(Const.USERNAME, searchModel.getUsername()).enqueue(new Callback<ResponseModel<ConversationCardModel>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<ConversationCardModel>> call, Response<ResponseModel<ConversationCardModel>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<ConversationCardModel> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()) {
                                    List<ConversationCardModel> responseModelResult = responseModel.getResult();
                                    ConversationCardModel conversationCardModel = responseModelResult.get(0);

                                    Intent intent = new Intent(context, MessageActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    Bundle bundle = new Bundle();
                                    bundle.putLong("conversationId", conversationCardModel.getConversationId());
                                    bundle.putString("conversationAvatar", conversationCardModel.getConversationAvatar());
                                    bundle.putString("conversationName", conversationCardModel.getConversationName());
                                    intent.putExtras(bundle);

                                    context.startActivity(intent);
                                }
                            } else {
                                int statusCode = response.code();

                                // handle request errors depending on status code
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel<ConversationCardModel>> call, Throwable t) {
                        }
                    });
                }
            });
        }
        else if (searchModel.getViewType() == TypeSearchEnum.FRIEND_REQUEST) {
            String timeInput = searchModel.getRequestTimeAt();
            List<String> timeResult = ProcessTime.getTimeFromString(timeInput);
            String timeShow = timeResult.get(0) + "-" + timeResult.get(1) + "-" + timeResult.get(2) + " " + timeResult.get(3) + ":" + timeResult.get(4);
            holder.requestTimeAt.setText(timeShow);

            Button btAccept = (Button) holder.itemView.findViewById(R.id.btAccept);
            Button btDecline = (Button) holder.itemView.findViewById(R.id.btDecline);
            btAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.acceptFriend(Const.USERNAME, searchModel.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();

                                String[] fullname = searchModel.getFullName().split(" ");
                                Toast.makeText(context, "You and " + fullname[fullname.length -1] + " have become friends", Toast.LENGTH_SHORT).show();

                                removeItem(searchModel, null);
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
            });
            btDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.declineFriend(Const.USERNAME, searchModel.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();

                                String[] fullname = searchModel.getFullName().split(" ");
                                Toast.makeText(context, "You declined " + fullname[fullname.length -1] + "'s friend request", Toast.LENGTH_SHORT).show();

                                removeItem(searchModel, null);
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
            });
        }
        else if (searchModel.getViewType() == TypeSearchEnum.MAKE_FRIEND) {
            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("Make Friend");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);

                }
            });
        }
        else if (searchModel.getViewType() == TypeSearchEnum.MAKED_FRIEND) {
            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("Unmake Friend");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);

                }
            });
        }
        else if (searchModel.getViewType() == TypeSearchEnum.JOINED_GROUP) {
            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("View Group");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);

                }
            });
        }
        else if (searchModel.getViewType() == TypeSearchEnum.JOIN_GROUP) {
            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("Join Group");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);

                }
            });
        }
        else if (searchModel.getViewType() == TypeSearchEnum.UNJOIN_GROUP) {
            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("Unjoin Group");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (searchs != null)
            return searchs.size();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return this.searchs.get(position).getViewType().ordinal();
    }


    public void removeItem(SearchModel searchModel, RecyclerView recyclerView) {
        if (searchModel == null) return;

        SeachAdapter.AsyncTaskUI asyncTaskUI = new AsyncTaskUI(searchModel, recyclerView);
        asyncTaskUI.execute();
    }

    private class AsyncTaskUI extends AsyncTask<Void, Integer, Void> {
        private SearchModel searchModel;
        private RecyclerView recyclerView;

        public AsyncTaskUI(SearchModel searchModel, RecyclerView recyclerView) {
            this.searchModel = searchModel;
            this.recyclerView = recyclerView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int idx = searchs.indexOf(searchModel);
            searchs.remove(searchModel);
            notifyItemRemoved(idx);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }
}
