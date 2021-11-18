package com.egil.pts.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.egil.pts.modal.FeedBack;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.PtsHolidays;
import com.egil.pts.service.UtilService;
import com.egil.pts.service.common.BaseUIService;

@Service("utilService")
public class UtilServiceImpl extends BaseUIService implements UtilService {
	@Transactional
	@Override
	public void manageHolidays(String oper, PtsHolidays ptsHolidays) throws Throwable {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public void manangeFeedback(String title, String desc, String priority, String reporter, String name, Long userId)
			throws Throwable {

		daoManager.getUtilDao().manangeFeedback(title, desc, priority, reporter, name, userId);

	}

	@Transactional
	@Override
	public List<FeedBack> getFeedback(String title, String desc, Date date, Long userId, Pagination pagination)
			throws Throwable {

		return daoManager.getUtilDao().getFeedback(title, desc, userId, pagination);

	}

	@Transactional
	@Override
	public int updateFeedBackStatus(String id, String title, String description, String priority, String comments,String status) {
		return daoManager.getUtilDao().updateFeedBackStatus(id, title,description,priority, comments,status);
	}

	@Transactional
	@Override
	public void deleteFeedBackStatus(String id) {
		daoManager.getUtilDao().deleteFeedBackStatus(id);
	}


	public Integer getFeedbackCount(String title, String desc, Date date, Long userId, Pagination pagination) {
		return daoManager.getUtilDao().getFeedbackCount(title, desc, userId, pagination);
	}
}
