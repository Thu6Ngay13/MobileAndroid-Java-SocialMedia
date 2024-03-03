package HCMUTE.SocialMedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import HCMUTE.SocialMedia.Adapters.PolicyAdapter;
import HCMUTE.SocialMedia.Models.PolicyGroupModel;
import HCMUTE.SocialMedia.Models.PolicyModel;
import HCMUTE.SocialMedia.R;

public class PolicyDetailsActivity extends AppCompatActivity {
    private ExpandableListView elvPolicies;
    private List<PolicyGroupModel> listGroup;
    private Map<PolicyGroupModel, List<PolicyModel>> listPolicy;
    private PolicyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_details);
        elvPolicies = (ExpandableListView) findViewById(R.id.elvPolicies);
        String data = getIntent().getStringExtra("policy");
        if (data.equals("dulieucanhan")){
            listPolicy = getData1();
        } else if (data.equals("baidang")) {
            listPolicy = getData2();
        } else {
            listPolicy = getData3();
            Log.e("MyTag", data);
        }
        listGroup = new ArrayList<>(listPolicy.keySet());
        adapter = new PolicyAdapter(listGroup, listPolicy);
        elvPolicies.setAdapter(adapter);
    }
    private Map<PolicyGroupModel, List<PolicyModel>> getData1(){
        Map<PolicyGroupModel, List<PolicyModel>> listMap = new HashMap<>();
        PolicyGroupModel gr1 = new PolicyGroupModel(1, "Thu thập dữ liệu");
        PolicyGroupModel gr2 = new PolicyGroupModel(2, "Sử dụng dữ liệu");
        PolicyGroupModel gr3 = new PolicyGroupModel(3, "Chia sẻ dữ liệu");
        PolicyGroupModel gr4 = new PolicyGroupModel(4, "Bảo mật dữ liệu");
        PolicyGroupModel gr5 = new PolicyGroupModel(5, "Quyền lợi và lựa chọn");

        List<PolicyModel> list1 = new ArrayList<>();
        list1.add(new PolicyModel(1, "Chúng tôi thu thập các loại thông tin cá nhân từ người dùng của ứng dụng mạng xã hội này nhằm cung cấp trải nghiệm tốt nhất và cải thiện dịch vụ của chúng tôi. Các loại thông tin này bao gồm nhưng không giới hạn:\n" +
                "•\tThông tin cá nhân: tên, địa chỉ email, số điện thoại, địa chỉ IP.\n" +
                "•\tThông tin hồ sơ: hình ảnh, giới tính, ngày sinh, quốc gia, sở thích, v.v.\n" +
                "•\tDữ liệu hoạt động: bài viết, bình luận, lượt thích, tương tác với nội dung, thời gian truy cập, v.v."));

        List<PolicyModel> list2 = new ArrayList<>();
        list2.add(new PolicyModel(1, "Chúng tôi sử dụng dữ liệu cá nhân của bạn để:\n" +
                "•\tCung cấp và duy trì dịch vụ mạng xã hội của chúng tôi.\n" +
                "•\tCải thiện và tùy chỉnh trải nghiệm người dùng.\n" +
                "•\tPhản hồi và hỗ trợ người dùng.\n" +
                "•\tPhát triển và nâng cao tính năng của ứng dụng.\n" +
                "•\tTiếp thị và quảng cáo.\n"));

        List<PolicyModel> list3 = new ArrayList<>();
        list3.add(new PolicyModel(1, "Chúng tôi có thể chia sẻ thông tin cá nhân của bạn với các bên thứ ba trong các trường hợp sau:\n" +
                "•\tVới các đối tác cung cấp dịch vụ: để cung cấp các dịch vụ hỗ trợ và tích hợp.\n" +
                "•\tTheo yêu cầu pháp lý: để tuân thủ các quy định pháp luật hoặc yêu cầu từ cơ quan thẩm quyền.\n" +
                "•\tTrong các giao dịch doanh nghiệp: trong trường hợp tái tổ chức, sáp nhập, bán hoặc mua lại toàn bộ hoặc một phần của công ty.\n" +
                "•\tVới sự đồng ý của người dùng: trong các trường hợp khác không được mô tả ở đây, chúng tôi sẽ thông báo và yêu cầu sự đồng ý của bạn trước khi chia sẻ thông tin cá nhân.\n"));

        List<PolicyModel> list4 = new ArrayList<>();
        list4.add(new PolicyModel(1, "Chúng tôi cam kết bảo vệ thông tin cá nhân của bạn và áp dụng các biện pháp bảo mật phù hợp để ngăn chặn truy cập trái phép, sử dụng sai mục đích, hoặc tiết lộ thông tin cá nhân của bạn."));

        List<PolicyModel> list5 = new ArrayList<>();
        list5.add(new PolicyModel(1, "Người dùng có quyền:\n" +
                "•\tTruy cập, chỉnh sửa hoặc xóa thông tin cá nhân của mình.\n" +
                "•\tRút lại sự đồng ý cho việc thu thập hoặc sử dụng thông tin cá nhân.\n" +
                "•\tYêu cầu hỗ trợ hoặc có câu hỏi liên quan đến chính sách bảo mật và dữ liệu cá nhân.\n"));
        listMap.put(gr1, list1);
        listMap.put(gr2, list2);
        listMap.put(gr3, list3);
        listMap.put(gr4, list4);
        listMap.put(gr5, list5);
        return listMap;
    }
    private Map<PolicyGroupModel, List<PolicyModel>> getData2(){
        Map<PolicyGroupModel, List<PolicyModel>> listMap = new HashMap<>();
        PolicyGroupModel gr1 = new PolicyGroupModel(1, "Quy định chung");
        PolicyGroupModel gr2 = new PolicyGroupModel(2, "Quy trình xử lý");
        PolicyGroupModel gr3 = new PolicyGroupModel(3, "Phản hồi và khiếu nại");

        List<PolicyModel> list1 = new ArrayList<>();
        list1.add(new PolicyModel(1, "\tBạo Lực và Kích Động:\n" +
                "\t•\tKhông được phép chia sẻ hoặc tuyên truyền bất kỳ hình ảnh hoặc nội dung nào liên quan đến bạo lực, kích động, hoặc kích thích hành vi bạo lực.\n" +
                "\t•\tTất cả các hình ảnh hoặc video liên quan đến bạo lực phải được cảnh báo và chỉ dành cho người dùng trưởng thành.\n" +
                "\tKhiêu Dâm và Nudity:\n" +
                "\t•\tChúng tôi không chấp nhận bất kỳ hình ảnh, video hoặc nội dung nào liên quan đến khiêu dâm hoặc nudity.\n" +
                "\t•\tHình ảnh về nudity sẽ bị xóa ngay lập tức và người dùng có thể bị cấm hoạt động trên ứng dụng.\n" +
                "\tKích Thích Tình Dục và Bạo Lực:\n" +
                "\t•\tMọi hình ảnh hoặc nội dung khuyến khích hoặc mô tả hành vi tình dục hoặc bạo lực đều bị cấm.\n" +
                "\t•\tNội dung về hành vi tình dục chỉ được chấp nhận khi được đồng ý và chỉ dành cho người trưởng thành.\n" +
                "\tQuấy Rối và Kỳ Thị:\n" +
                "\t•\tQuấy rối, kỳ thị hoặc bất kỳ hành vi nào gây tổn hại đến danh dự, uy tín hoặc an ninh của người khác đều không được chấp nhận.\n" +
                "\t•\tNgười dùng có thể bị xóa hoặc cấm sử dụng ứng dụng nếu vi phạm quy định này.\n" +
                "\tBản Quyền và Chia Sẻ Thông Tin Cá Nhân:\n" +
                "\t•\tNgười dùng phải tuân thủ luật bản quyền và không chia sẻ thông tin cá nhân của người khác mà không có sự cho phép của họ.\n" +
                "\t•\tChúng tôi sẽ xóa bất kỳ nội dung vi phạm quy định bản quyền mà không cần thông báo trước. \n"));

        List<PolicyModel> list2 = new ArrayList<>();
        list2.add(new PolicyModel(1, "•\tMọi bài đăng được kiểm duyệt bởi hệ thống và cộng đồng người dùng.\n" +
                "•\tBất kỳ nội dung vi phạm chính sách sẽ bị xóa ngay lập tức hoặc bị ẩn đi.\n" +
                "•\tNgười dùng có thể báo cáo bất kỳ nội dung nào họ cho là vi phạm chính sách.\n" +
                "•\tNgười dùng vi phạm chính sách có thể bị xóa bài đăng, tạm ngưng tài khoản hoặc bị cấm sử dụng ứng dụng tùy thuộc vào mức độ vi phạm.\n"));

        List<PolicyModel> list3 = new ArrayList<>();
        list3.add(new PolicyModel(1, "•\tChúng tôi luôn sẵn lòng lắng nghe phản hồi và khiếu nại từ cộng đồng người dùng.\n" +
                "•\tNgười dùng có thể liên hệ với chúng tôi thông qua kênh hỗ trợ để báo cáo nội dung vi phạm hoặc yêu cầu giải đáp thắc mắc liên quan đến chính sách này.\n"));
        listMap.put(gr1, list1);
        listMap.put(gr2, list2);
        listMap.put(gr3, list3);
        return listMap;
    }
    private Map<PolicyGroupModel, List<PolicyModel>> getData3(){
        Map<PolicyGroupModel, List<PolicyModel>> listMap = new HashMap<>();
        PolicyGroupModel gr1 = new PolicyGroupModel(1, "Thay đổi chính sách");

        List<PolicyModel> list1 = new ArrayList<>();
        list1.add(new PolicyModel(1, "Chính sách này có hiệu lực ngay lập tức sau khi công bố và có thể được cập nhật hoặc sửa đổi theo yêu cầu hoặc thay đổi trong luật pháp và xu hướng xã hội.\n" +
                "\tChúng tôi có thể cập nhật Chính sách về Dữ liệu Cá nhân này theo thời gian mà không cần thông báo trước. Bằng cách sử dụng ứng dụng của chúng tôi, bạn đồng ý với các thay đổi trong Chính sách này.\n"));
        listMap.put(gr1, list1);
        return listMap;
    }
}