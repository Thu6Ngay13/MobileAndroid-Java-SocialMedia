package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Enums.TypeMessageEnum;
import HCMUTE.SocialMedia.Holders.MessageHolder;
import HCMUTE.SocialMedia.Models.MessageModel;
import HCMUTE.SocialMedia.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder>{

    private Context context;
    private List<MessageModel> messages;

    public MessageAdapter(Context context, List<MessageModel> messageCards) {
        this.context = context;
        this.messages = messageCards;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TypeMessageEnum.SENDER_MESSAGE.ordinal())
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.you_message_card_view, parent, false));
        else if(viewType == TypeMessageEnum.SENDER_MEDIA.ordinal())
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.you_message_media_card_view, parent, false));
        else if(viewType == TypeMessageEnum.RECEIVER_MESSAGE.ordinal())
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.friend_message_card_view, parent, false));
        else
            return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.friend_message_media_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        MessageModel messageCardModel = messages.get(position);
        holder.messageSendingAt.setText(messageCardModel.getMessageSendingAt());

        if(messageCardModel.getViewType() == TypeMessageEnum.SENDER_MESSAGE){
            holder.textMessage.setText(messageCardModel.getText());
        }
        else if(messageCardModel.getViewType() == TypeMessageEnum.SENDER_MEDIA && messageCardModel.getMedia() != null){
            holder.mediaMessage.post(new Runnable() {
                @Override
                public void run() {
                    holder.mediaMessage.requestLayout();
                    Bitmap imageResourceId = messageCardModel.getMedia();

                    int img_width = imageResourceId.getWidth();
                    int img_height = imageResourceId.getHeight();

                    int width = holder.mediaMessage.getWidth();
                    int height = width * img_height / img_width;
                    holder.mediaMessage.getLayoutParams().height = height;
                    holder.mediaMessage.requestLayout();

                    holder.mediaMessage.setImageBitmap(imageResourceId);
                    holder.mediaMessage.getLayoutParams().height = height;
                }
            });
        }
        else if(messageCardModel.getViewType() == TypeMessageEnum.RECEIVER_MESSAGE){
            holder.textMessage.setText(messageCardModel.getText());
        }
        else if(messageCardModel.getViewType() == TypeMessageEnum.RECEIVER_MEDIA && messageCardModel.getMedia() != null){
            holder.mediaMessage.post(new Runnable() {
                @Override
                public void run() {
                    holder.mediaMessage.requestLayout();
                    Bitmap imageResourceId = messageCardModel.getMedia();

                    int img_width = imageResourceId.getWidth();
                    int img_height = imageResourceId.getHeight();

                    int width = holder.mediaMessage.getWidth();
                    int height = width * img_height / img_width;
                    holder.mediaMessage.getLayoutParams().height = height;
                    holder.mediaMessage.requestLayout();

                    holder.mediaMessage.setImageBitmap(imageResourceId);
                    holder.mediaMessage.getLayoutParams().height = height;
                }
            });
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

    public void addItems(List<MessageModel> messageModels, RecyclerView recyclerView){
        if (messageModels.isEmpty()) return;

        AsyncTaskUI asyncTaskUI = new AsyncTaskUI(messageModels, recyclerView);
        asyncTaskUI.execute();
    }

    private class AsyncTaskUI extends AsyncTask<Void, Integer, Void> {
        private List<MessageModel> messageModels;
        private RecyclerView recyclerView;

        public AsyncTaskUI(List<MessageModel> messageModels, RecyclerView recyclerView) {
            this.messageModels = messageModels;
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
            messages.addAll(messageModels);
            notifyItemInserted(getItemCount() - 1);
            recyclerView.scrollToPosition(getItemCount() - 1);
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }

}


