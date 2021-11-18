package com.egil.pts.service;

import java.util.Date;
import java.util.List;

import com.egil.pts.modal.FeedBack;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.PtsHolidays;

public interface UtilService {

	public void manageHolidays(String oper, PtsHolidays ptsHolidays) throws Throwable;

	public void manangeFeedback(String title, String desc, String priority, String reporter, String name, Long userId)
			throws Throwable;

	public List<FeedBack> getFeedback(String title, String desc, Date date, Long userId, Pagination pagination)
			throws Throwable;

	public int updateFeedBackStatus(String id, String title, String description, String priority, String comments, String status);

	public void deleteFeedBackStatus(String id);

	public Integer getFeedbackCount(String title, String desc, Date date, Long userId, Pagination pagination);
}
