package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Enums.MessageEnum;
import HCMUTE.SocialMedia.Holders.MessageHolder;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

    private Context context;
    private List<MessageModel> messages;

    public MessageAdapter(Context context, List<MessageModel> messageCards) {
        this.context = context;
        this.messages = messageCards;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MessageEnum.SEND.ordinal())
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.you_message_card_view, parent, false));
        else
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.friend_message_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        MessageModel messageCardModel = messages.get(position);
        if(messageCardModel.getViewType() == MessageEnum.SEND){
            holder.avatar.setImageResource(messageCardModel.getAvatar());
            holder.messageSendingAt.setText(messageCardModel.getMessageSendingAt());
            holder.textMessage.setText(messageCardModel.getText());
            holder.mediaMessage.setImageResource(messageCardModel.getMedia());
        }
        else {
            holder.avatar.setImageResource(messageCardModel.getAvatar());
            holder.fullname.setText(messageCardModel.getFullname());
            holder.messageSendingAt.setText(messageCardModel.getMessageSendingAt());
            holder.textMessage.setText(messageCardModel.getText());
            holder.mediaMessage.setImageResource(messageCardModel.getMedia());
        }
    }

    @Override
    public int getItemViewType(int position){
        return messages.get(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        if (messages != null)
            return messages.size();
        return 0;
    }
}
