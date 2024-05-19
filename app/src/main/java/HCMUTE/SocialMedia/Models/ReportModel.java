package HCMUTE.SocialMedia.Models;

public class ReportModel {
	private long reportId;
	private String reportingTimeAt;
	private String text;
	private PostCardModel postModel;

	public ReportModel(long reportId, String reportingTimeAt, String text, PostCardModel postModel) {
		this.reportId = reportId;
		this.reportingTimeAt = reportingTimeAt;
		this.text = text;
		this.postModel = postModel;
	}

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getReportingTimeAt() {
		return reportingTimeAt;
	}

	public void setReportingTimeAt(String reportingTimeAt) {
		this.reportingTimeAt = reportingTimeAt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public PostCardModel getPostModel() {
		return postModel;
	}

	public void setPostModel(PostCardModel postModel) {
		this.postModel = postModel;
	}
}
