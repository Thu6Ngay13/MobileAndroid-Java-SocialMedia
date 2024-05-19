package HCMUTE.SocialMedia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import HCMUTE.SocialMedia.Models.PolicyGroupModel;
import HCMUTE.SocialMedia.Models.PolicyModel;
import HCMUTE.SocialMedia.R;

public class PolicyAdapter extends BaseExpandableListAdapter {
    private List<PolicyGroupModel> listGroup;
    private Map<PolicyGroupModel, List<PolicyModel>> listPolicy;

    public PolicyAdapter(List<PolicyGroupModel> listGroup, Map<PolicyGroupModel, List<PolicyModel>> listPolicy) {
        this.listGroup = listGroup;
        this.listPolicy = listPolicy;
    }

    @Override
    public int getGroupCount() {
        return listGroup != null ? listGroup.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listGroup != null && listPolicy != null ? listPolicy.get(listGroup.get(groupPosition)).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listPolicy.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return listGroup.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return listPolicy.get(listGroup.get(groupPosition)).get(childPosition).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_policy_group, parent, false);
            holder = new GroupViewHolder();
            holder.tvGroup = convertView.findViewById(R.id.tvPolicyName);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        PolicyGroupModel group = (PolicyGroupModel) getGroup(groupPosition);
        holder.tvGroup.setText(group.getGroupName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_policy_detail, parent, false);
            holder = new ChildViewHolder();
            holder.tvDetail = convertView.findViewById(R.id.tvPolicyDetail);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        PolicyModel item = (PolicyModel) getChild(groupPosition, childPosition);
        holder.tvDetail.setText(item.getDetail());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvGroup;
    }

    static class ChildViewHolder {
        TextView tvDetail;
    }
}
