package HCMUTE.SocialMedia.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import HCMUTE.SocialMedia.R;

public class ReportPostHolder extends RecyclerView.ViewHolder {
    public TextView tvFullName;
    public TextView tvContent;
    public TextView tvReportingTimeAt;

    public ReportPostHolder(@NonNull View itemView) {
        super(itemView);
        this.tvFullName = itemView.findViewById(R.id.tvFullName);
        this.tvContent = itemView.findViewById(R.id.tvContent);
        this.tvReportingTimeAt = itemView.findViewById(R.id.tvReportingTimeAt);

    }
}
