package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import HCMUTE.SocialMedia.Activities.YourPersonalPageActivity;
import HCMUTE.SocialMedia.Consts.Const;
import HCMUTE.SocialMedia.Enums.TypeSearchEnum;
import HCMUTE.SocialMedia.Holders.SearchHolder;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.Models.GroupModel;
import HCMUTE.SocialMedia.Models.ResponseModel;
import HCMUTE.SocialMedia.Models.SearchModel;
import HCMUTE.SocialMedia.R;
import HCMUTE.SocialMedia.Retrofit.APIService;
import HCMUTE.SocialMedia.Retrofit.RetrofitClient;
import HCMUTE.SocialMedia.SharePreferances.PrefManager;
import HCMUTE.SocialMedia.Utils.ProcessTime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeachAdapter extends RecyclerView.Adapter<SearchHolder> {
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
        } else if (viewType == TypeSearchEnum.FRIEND_REQUEST.ordinal()) {
            return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.friend_request_view, parent, false));
        } else if (viewType == TypeSearchEnum.MAKE_FRIEND.ordinal() || viewType == TypeSearchEnum.MAKED_FRIEND.ordinal()) {
            return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.friend_search_view, parent, false));
        } else if (viewType == TypeSearchEnum.JOINED_GROUP.ordinal()) {
            return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.group_search_view, parent, false));
        } else if (viewType == TypeSearchEnum.JOIN_GROUP_PRIVATE.ordinal() || viewType == TypeSearchEnum.UNJOIN_GROUP_PRIVATE.ordinal()) {
            return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.group_private_search_view, parent, false));
        } else if (viewType == TypeSearchEnum.JOIN_GROUP_PUBLIC.ordinal() || viewType == TypeSearchEnum.UNJOIN_GROUP_PUBLIC.ordinal()) {
            return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.group_public_search_view, parent, false));
        }

        return new SearchHolder(LayoutInflater.from(this.context).inflate(R.layout.group_public_search_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        SearchModel searchModel = this.searchs.get(position);
        Glide.with(this.context).load(searchModel.getAvatar()).into(holder.avatar);
        holder.fullName.setText(searchModel.getFullName());

        if (searchModel.getViewType() == TypeSearchEnum.YOUR_FRIEND) {
            Button btViewProfile = (Button) holder.itemView.findViewById(R.id.btViewProfile);
            btViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "View profile", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, YourPersonalPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("YOUR_FRIEND_USERNAME", searchModel.getUsername());
                    context.startActivity(intent);
                }
            });

            Button btSendMessage = (Button) holder.itemView.findViewById(R.id.btSendMessage);
            btSendMessage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.getConversationWithFriend(PrefManager.getUsername(), searchModel.getUsername()).enqueue(new Callback<ResponseModel<ConversationCardModel>>() {
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
                    apiService.acceptFriend(PrefManager.getUsername(), searchModel.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    String[] fullname = searchModel.getFullName().split(" ");
                                    Toast.makeText(context, "You and " + fullname[fullname.length - 1] + " have become friends", Toast.LENGTH_SHORT).show();

                                    searchModel.setViewType(TypeSearchEnum.YOUR_FRIEND);
                                    notifyItemChanged(position);
                                }

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
                    apiService.declineFriend(PrefManager.getUsername(), searchModel.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    String[] fullname = searchModel.getFullName().split(" ");
                                    Toast.makeText(context, "You declined " + fullname[fullname.length - 1] + "'s friend request", Toast.LENGTH_SHORT).show();

                                    searchModel.setViewType(TypeSearchEnum.MAKE_FRIEND);
                                    notifyItemChanged(position);
                                }
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
            Button btViewProfile = (Button) holder.itemView.findViewById(R.id.btViewProfile);
            btViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, YourPersonalPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("YOUR_FRIEND_USERNAME", searchModel.getUsername());
                    context.startActivity(intent);
                }
            });

            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("Make Friend");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.makeFriend(PrefManager.getUsername(), searchModel.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    String[] fullname = searchModel.getFullName().split(" ");
                                    Toast.makeText(context, "You unmake friends " + fullname[fullname.length - 1], Toast.LENGTH_SHORT).show();

                                    btDoSomething.setText("Make Friend");
                                    searchModel.setViewType(TypeSearchEnum.MAKED_FRIEND);
                                    notifyItemChanged(position);
                                }
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
        else if (searchModel.getViewType() == TypeSearchEnum.MAKED_FRIEND) {
            Button btViewProfile = (Button) holder.itemView.findViewById(R.id.btViewProfile);
            btViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, YourPersonalPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("YOUR_FRIEND_USERNAME", searchModel.getUsername());
                    context.startActivity(intent);
                }
            });

            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("Unmake Friend");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.unmakeFriend(PrefManager.getUsername(), searchModel.getUsername()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    String[] fullname = searchModel.getFullName().split(" ");
                                    Toast.makeText(context, "You make friends " + fullname[fullname.length - 1], Toast.LENGTH_SHORT).show();

                                    btDoSomething.setText("Unmake Friend");
                                    searchModel.setViewType(TypeSearchEnum.MAKE_FRIEND);
                                    notifyItemChanged(position);
                                }
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
        } else if (searchModel.getViewType() == TypeSearchEnum.JOINED_GROUP) {
            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("View Group");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.viewGroupByUsernameAndGroupId(PrefManager.getUsername(), searchModel.getGroupdId()).enqueue(new Callback<ResponseModel<GroupModel>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<GroupModel>> call, Response<ResponseModel<GroupModel>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<GroupModel> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    List<GroupModel> groupModels = responseModel.getResult();
                                    Toast.makeText(context, "View group public " + groupModels.get(0).getGroupName(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                int statusCode = response.code();
                                // handle request errors depending on status code
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel<GroupModel>> call, Throwable t) {
                        }
                    });
                }
            });
        } else if (searchModel.getViewType() == TypeSearchEnum.JOIN_GROUP_PRIVATE) {
            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("Join Group");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.joinGroupByUsernameAndGroupId(PrefManager.getUsername(), searchModel.getGroupdId()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()) {
                                    Toast.makeText(context, "You join group ", Toast.LENGTH_SHORT).show();

                                    btDoSomething.setText("Unjoin Group");
                                    searchModel.setViewType(TypeSearchEnum.UNJOIN_GROUP_PRIVATE);
                                    notifyItemChanged(position);
                                }

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
        } else if (searchModel.getViewType() == TypeSearchEnum.JOIN_GROUP_PUBLIC) {
            Button btViewGroup = (Button) holder.itemView.findViewById(R.id.btViewGroup);
            btViewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "View group public", Toast.LENGTH_SHORT).show();
                }
            });

            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("Join Group");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.joinGroupByUsernameAndGroupId(PrefManager.getUsername(), searchModel.getGroupdId()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    Toast.makeText(context, "You join group ", Toast.LENGTH_SHORT).show();

                                    btDoSomething.setText("Unjoin Group");
                                    searchModel.setViewType(TypeSearchEnum.UNJOIN_GROUP_PUBLIC);
                                    notifyItemChanged(position);
                                }
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
        } else if (searchModel.getViewType() == TypeSearchEnum.UNJOIN_GROUP_PRIVATE) {
            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("Unjoin Group");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.unjoinGroupByUsernameAndGroupId(PrefManager.getUsername(), searchModel.getGroupdId()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    Toast.makeText(context, "You unjoin group ", Toast.LENGTH_SHORT).show();

                                    btDoSomething.setText("Join Group");
                                    searchModel.setViewType(TypeSearchEnum.JOIN_GROUP_PRIVATE);
                                    notifyItemChanged(position);
                                }
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
        } else if (searchModel.getViewType() == TypeSearchEnum.UNJOIN_GROUP_PUBLIC) {
            Button btViewGroup = (Button) holder.itemView.findViewById(R.id.btViewGroup);
            btViewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "View group public", Toast.LENGTH_SHORT).show();
                }
            });

            Button btDoSomething = (Button) holder.itemView.findViewById(R.id.btDoSomething);
            btDoSomething.setText("Unjoin Group");
            btDoSomething.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    APIService apiService = (APIService) RetrofitClient.getRetrofit().create(APIService.class);
                    apiService.unjoinGroupByUsernameAndGroupId(PrefManager.getUsername(), searchModel.getGroupdId()).enqueue(new Callback<ResponseModel<String>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                            if (response.isSuccessful()) {
                                ResponseModel<String> responseModel = response.body();
                                if (responseModel != null && responseModel.isSuccess()){
                                    Toast.makeText(context, "You unjoin group ", Toast.LENGTH_SHORT).show();

                                    btDoSomething.setText("Join Group");
                                    searchModel.setViewType(TypeSearchEnum.JOIN_GROUP_PUBLIC);
                                    notifyItemChanged(position);

                                }
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
