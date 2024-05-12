package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class WaitingLoadingHolder extends RecyclerView.ViewHolder {
    public ProgressBar pbLoading;

    public WaitingLoadingHolder(@NonNull View itemView) {
        super(itemView);
        this.pbLoading = itemView.findViewById(R.id.pbLoading);
    }
}