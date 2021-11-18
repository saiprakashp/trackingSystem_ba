package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.modal.FeedBack;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.PtsHolidays;

public interface UtilDao extends GenericDao<FeedBack, Long> {

	public void manageHolidays(String oper, PtsHolidays ptsHolidays) throws Throwable;

	public void manangeFeedback(String title, String desc, String priority, String reporter, String name, Long userId) throws Throwable;

	public List<FeedBack> getFeedback(String title, String desc, Long userId, Pagination pagination) throws Throwable;

	public int updateFeedBackStatus(String id, String title, String description, String priority, String comments, String status);

	public void deleteFeedBackStatus(String id);

	public Integer getFeedbackCount(String title, String desc, Long userId, Pagination pagination);

}
