package com.pcwk.ehr.calendar.domain;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CalendarVO {
     int scheduleId;
     int scheduleUserId;
     String scheduleStartDate;
     String scheduleEndDate;
     String scheduleContent;
     String scheduleTitle;
     int scheduleStatus;
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	public int getScheduleUserId() {
		return scheduleUserId;
	}
	public void setScheduleUserId(int scheduleUserId) {
		this.scheduleUserId = scheduleUserId;
	}
	public String getScheduleStartDate() {
		return scheduleStartDate;
	}
	public void setScheduleStartDate(String scheduleStartDate) {
		this.scheduleStartDate = scheduleStartDate;
	}
	public String getScheduleEndDate() {
		return scheduleEndDate;
	}
	public void setScheduleEndDate(String scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
	}
	public String getScheduleContent() {
		return scheduleContent;
	}
	public void setScheduleContent(String scheduleContent) {
		this.scheduleContent = scheduleContent;
	}
	public String getScheduleTitle() {
		return scheduleTitle;
	}
	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}
	public int getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(int scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}



}
