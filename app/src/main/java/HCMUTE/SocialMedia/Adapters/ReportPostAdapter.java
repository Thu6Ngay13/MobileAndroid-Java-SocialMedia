package HCMUTE.SocialMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import HCMUTE.SocialMedia.Activities.AdminViewReportPostActivity;
import HCMUTE.SocialMedia.Enums.TypeReportPostEnum;
import HCMUTE.SocialMedia.Holders.ReportPostHolder;
import HCMUTE.SocialMedia.Models.ReportPostModel;
import HCMUTE.SocialMedia.R;

public class ReportPostAdapter extends RecyclerView.Adapter<ReportPostHolder> {
    private Context context;
    private List<ReportPostModel> reportPosts;

    public ReportPostAdapter(Context context, List<ReportPostModel> reportPosts) {
        this.context = context;
        this.reportPosts = reportPosts;
    }

    @NonNull
    @Override
    public ReportPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TypeReportPostEnum.PREVIEW.ordinal())
            return new ReportPostHolder(LayoutInflater.from(context).inflate(R.layout.report_post_1_view, parent, false));
        return new ReportPostHolder(LayoutInflater.from(context).inflate(R.layout.report_post_2_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReportPostHolder holder, int position) {
        ReportPostModel reportPostModel = reportPosts.get(position);
        String heading1 = "Reporter: ";
        String heading2 = "Content: ";

        String fullName = reportPostModel.getFullname();
        String content = reportPostModel.getContent();
        SpannableString headingAndFullName = new SpannableString(heading1 + fullName);
        SpannableString headingAndContent = new SpannableString(heading2 + content);

        //      Heading
        headingAndFullName.setSpan(new StyleSpan(Typeface.BOLD), 0, heading1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        headingAndFullName.setSpan(new TextAppearanceSpan(context, R.style.notify_fullname), 0, heading1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //      Fullname
        headingAndFullName.setSpan(new TextAppearanceSpan(context, R.style.notify_fullname), heading1.length() + 1, headingAndFullName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //      Heading
        headingAndContent.setSpan(new StyleSpan(Typeface.BOLD), 0, heading2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        headingAndContent.setSpan(new TextAppearanceSpan(context, R.style.notify_content), 0, heading2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //      Content
        headingAndContent.setSpan(new TextAppearanceSpan(context, R.style.notify_content), heading2.length() + 1, headingAndContent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.tvFullName.setText(headingAndFullName);
        holder.tvContent.setText(headingAndContent);
        holder.tvReportingTimeAt.setText(reportPostModel.getReportingTimeAt());

        if (reportPostModel.getViewType() == TypeReportPostEnum.PREVIEW){
            Button btViewPost = (Button) holder.itemView.findViewById(R.id.btViewPost);
            btViewPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AdminViewReportPostActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    Bundle bundle = new Bundle();
                    bundle.putLong("postId", reportPostModel.getPostModel().getPostId());
                    bundle.putLong("groupId", reportPostModel.getPostModel().getGroupId());
                    bundle.putLong("mode", reportPostModel.getPostModel().getMode());

                    bundle.putString("avatar", reportPostModel.getPostModel().getAvatar());
                    bundle.putString("username", reportPostModel.getPostModel().getUsername());
                    bundle.putString("fullname", reportPostModel.getPostModel().getFullName());
                    bundle.putString("postingTimeAt", reportPostModel.getPostModel().getPostingTimeAt());
                    bundle.putString("postText", reportPostModel.getPostModel().getPostText());
                    bundle.putString("postMedia", reportPostModel.getPostModel().getPostMedia());

                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return reportPosts.get(position).getViewType().ordinal();
    }

    @Override
    public int getItemCount() {
        if (reportPosts == null)
            return 0;
        return reportPosts.size();
    }
}
