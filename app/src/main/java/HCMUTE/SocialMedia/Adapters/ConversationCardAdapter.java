package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import HCMUTE.SocialMedia.Activities.MessageActivity;
import HCMUTE.SocialMedia.Holders.ConversationCardHolder;
import HCMUTE.SocialMedia.Models.ConversationCardModel;
import HCMUTE.SocialMedia.R;

public class ConversationCardAdapter extends RecyclerView.Adapter<ConversationCardHolder> {
    private Context context;
    private List<ConversationCardModel> conversationCards;

    public ConversationCardAdapter(Context context, List<ConversationCardModel> conversationCards) {
        this.context = context;
        this.conversationCards = conversationCards;
    }

    @NonNull
    @Override
    public ConversationCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationCardHolder(LayoutInflater.from(context).inflate(R.layout.conversation_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationCardHolder holder, int position) {
        ConversationCardModel conversationCardModel = conversationCards.get(position);
        Glide.with(this.context).load(conversationCardModel.getConversationAvatar()).into(holder.avatar);
        holder.fullname.setText(conversationCardModel.getConversationName());

        holder.itemView.setOnClickListener((o) -> {
            Intent intent = new Intent(context, MessageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle bundle = new Bundle();
            bundle.putLong("conversationId", conversationCardModel.getConversationId());
            intent.putExtras(bundle);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (conversationCards != null)
            return conversationCards.size();
        return 0;
    }
}
