package com.semgt.model;

public class Series {

	private Integer seriesId;

	private Integer userId;

	private String seriesNameCN;

	private String seriesNickName;

	private String seriesNameOrg;

	private Byte headSeason;

	private Byte currentSeason;

	private Integer episode;

	private String updateWeekday;

	private String comingDate;

	private String fuzzyDate;

	private String isEnd;

	private Integer isAbandoned;

	private String jnlDate;

	private Integer priority;

	private String urlType;

	private String downloadUrl;

	private String customUrl;

	private String rating;

	// getters and setters
	public String getComingDate() {
		return comingDate;
	}

	public Byte getCurrentSeason() {
		return currentSeason;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public Integer getEpisode() {
		return episode;
	}

	public String getFuzzyDate() {
		return fuzzyDate;
	}

	public Byte getHeadSeason() {
		return headSeason;
	}

	public Integer getIsAbandoned() {
		return isAbandoned;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public String getJnlDate() {
		return jnlDate;
	}

	public Integer getPriority() {
		return priority;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public String getSeriesNameCN() {
		return seriesNameCN;
	}

	public String getSeriesNameOrg() {
		return seriesNameOrg;
	}

	public String getSeriesNickName() {
		return seriesNickName;
	}

	public String getUpdateWeekday() {
		return updateWeekday;
	}

	public String getUrlType() {
		return urlType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setComingDate(String comingDate) {
		this.comingDate = comingDate;
	}

	public void setCurrentSeason(Byte currentSeason) {
		this.currentSeason = currentSeason;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
	}

	public void setEpisode(Integer episode) {
		this.episode = episode;
	}

	public void setFuzzyDate(String fuzzyDate) {
		this.fuzzyDate = fuzzyDate == null ? null : fuzzyDate.trim();
	}

	public void setHeadSeason(Byte headSeason) {
		this.headSeason = headSeason;
	}

	public void setIsAbandoned(Integer isAbandoned) {
		this.isAbandoned = isAbandoned;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd == null ? null : isEnd.trim();
	}

	public void setJnlDate(String jnlDate) {
		this.jnlDate = jnlDate;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public void setSeriesNameCN(String seriesNameCN) {
		this.seriesNameCN = seriesNameCN == null ? null : seriesNameCN.trim();
	}

	public void setSeriesNameOrg(String seriesNameOrg) {
		this.seriesNameOrg = seriesNameOrg == null ? null : seriesNameOrg
				.trim();
	}

	public void setSeriesNickName(String seriesNickName) {
		this.seriesNickName = seriesNickName == null ? null : seriesNickName
				.trim();
	}

	public void setUpdateWeekday(String updateWeekday) {
		this.updateWeekday = updateWeekday;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCustomUrl() {
		return customUrl;
	}

	public void setCustomUrl(String customUrl) {
		this.customUrl = customUrl;
	}
}