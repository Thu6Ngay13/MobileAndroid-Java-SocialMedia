package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class SettingCardHolder extends RecyclerView.ViewHolder {
    public ImageView imageFunction;
    public TextView functionName;

    public SettingCardHolder(@NonNull View itemView) {
        super(itemView);
        this.imageFunction = itemView.findViewById(R.id.ibImageFunction);
        this.functionName = itemView.findViewById(R.id.tvFunctionName);
    }
}
