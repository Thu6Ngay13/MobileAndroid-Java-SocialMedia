package HCMUTE.SocialMedia.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import HCMUTE.SocialMedia.Adapters.FriendRequestAdapter;
import HCMUTE.SocialMedia.Adapters.YourFriendAdapter;
import HCMUTE.SocialMedia.Models.FriendRequestModel;
import HCMUTE.SocialMedia.Models.YourFriendModel;
import HCMUTE.SocialMedia.R;

public class FriendFragment extends Fragment {
    private boolean yourFriendNow;
    private Button btYourFriend;
    private Button btRequestedFriend;

    public FriendFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btYourFriend = view.findViewById(R.id.btYourFriend);
        btRequestedFriend = view.findViewById(R.id.btRequestedFriend);
        onCickYourFriend(view, getContext());

        btYourFriend.setOnClickListener(v -> {
            if(yourFriendNow) return;
            onCickYourFriend(view, getContext());
        });

        btRequestedFriend.setOnClickListener(v -> {
            if(!yourFriendNow) return;
            onCickFriendRequest(view, getContext());
        });
    }

    private void onCickYourFriend(View view, Context context){
        btYourFriend.setTextColor(ContextCompat.getColor(context, R.color.white));
        btYourFriend.setBackgroundColor(ContextCompat.getColor(context, R.color.green_dark));

        btRequestedFriend.setTextColor(ContextCompat.getColor(context, R.color.green_dark));
        btRequestedFriend.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        yourFriendNow = true;
        List<YourFriendModel> yourFriendModels1 = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            yourFriendModels1.add(new YourFriendModel(
                    R.mipmap.ic_user_72_dark,
                    "Jonhny Deep"
            ));
        }

        RecyclerView recyclerView1 = view.findViewById(R.id.rvFriendArea);
        recyclerView1.setLayoutManager(new LinearLayoutManager(context));
        recyclerView1.setAdapter(new YourFriendAdapter(context, yourFriendModels1));
    }

    private void onCickFriendRequest(View view, Context context){
        btYourFriend.setTextColor(ContextCompat.getColor(context, R.color.green_dark));
        btYourFriend.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        btRequestedFriend.setTextColor(ContextCompat.getColor(context, R.color.white));
        btRequestedFriend.setBackgroundColor(ContextCompat.getColor(context, R.color.green_dark));

        yourFriendNow = false;
        List<FriendRequestModel> friendRequestModels = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            friendRequestModels.add(new FriendRequestModel(
                    R.mipmap.ic_user_72_dark,
                    "Jonhny Deep",
                    "23:59 25-02-2024"
            ));
        }

        RecyclerView recyclerView12 = view.findViewById(R.id.rvFriendArea);
        recyclerView12.setLayoutManager(new LinearLayoutManager(context));
        recyclerView12.setAdapter(new FriendRequestAdapter(context, friendRequestModels));
    }
}