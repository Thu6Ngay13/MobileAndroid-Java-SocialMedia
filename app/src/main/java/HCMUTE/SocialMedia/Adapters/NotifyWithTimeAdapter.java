package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Holders.NotifyWithTimeHolder;
import HCMUTE.SocialMedia.Models.NotifyWithTimeModel;
import HCMUTE.SocialMedia.R;

public class NotifyWithTimeAdapter extends RecyclerView.Adapter<NotifyWithTimeHolder> {
    private Context context;
    private List<NotifyWithTimeModel> notifies;

    public NotifyWithTimeAdapter(Context context, List<NotifyWithTimeModel> notifies) {
        this.context = context;
        this.notifies = notifies;
    }

    @NonNull
    @Override
    public NotifyWithTimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotifyWithTimeHolder(LayoutInflater.from(context).inflate(R.layout.notify_with_time_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyWithTimeHolder holder, int position) {
        NotifyWithTimeModel notifyWithTimeModel = notifies.get(position);
        holder.notifyTime.setText(notifyWithTimeModel.getNotifyTime());

        RecyclerView recyclerView = holder.itemView.findViewById(R.id.rvNotifyWithTime);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new NotifyCardAdapter(context.getApplicationContext(), notifyWithTimeModel.getNotifyCardModels()));
    }

    @Override
    public int getItemCount() {
        if (notifies != null)
            return notifies.size();
        return 0;
    }
}
