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
        if (listGroup != null)
            return listGroup.size();
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (listGroup!= null && listPolicy != null)
            return listPolicy.get(listGroup.get(groupPosition)).size();
        return 0;
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
        PolicyGroupModel group = listGroup.get(groupPosition);
        return group.getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        PolicyModel policy = listPolicy.get(listGroup.get(groupPosition)).get(childPosition);
        return policy.getId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_policy_group, parent, false);
            TextView tvGroup = (TextView) convertView.findViewById(R.id.tvPolicyName);
            PolicyGroupModel group = listGroup.get(groupPosition);
            tvGroup.setText(group.getGroupName());
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_policy_detail, parent, false);
            TextView tvDetail = (TextView) convertView.findViewById(R.id.tvPolicyDetail);
            PolicyModel item = listPolicy.get(listGroup.get(groupPosition)).get(childPosition);
            tvDetail.setText(item.getDetail());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
