package HCMUTE.SocialMedia.Models;

import HCMUTE.SocialMedia.Enums.TypeReportPostEnum;

public class ReportPostModel {

	private TypeReportPostEnum viewType;
	private long reportId;
	private String fullname;
	private String reportingTimeAt;
	private String content;
	private PostCardModel postModel;

	public ReportPostModel(TypeReportPostEnum viewType, long reportId, String fullname, String reportingTimeAt, String content, PostCardModel postModel) {
		this.viewType = viewType;
		this.reportId = reportId;
		this.fullname = fullname;
		this.reportingTimeAt = reportingTimeAt;
		this.content = content;
		this.postModel = postModel;
	}

	public TypeReportPostEnum getViewType() {
		return viewType;
	}

	public void setViewType(TypeReportPostEnum viewType) {
		this.viewType = viewType;
	}

	public long getReportId() {
		return reportId;
	}

	public void setReportId(long reportId) {
		this.reportId = reportId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getReportingTimeAt() {
		return reportingTimeAt;
	}

	public void setReportingTimeAt(String reportingTimeAt) {
		this.reportingTimeAt = reportingTimeAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PostCardModel getPostModel() {
		return postModel;
	}

	public void setPostModel(PostCardModel postModel) {
		this.postModel = postModel;
	}
}
