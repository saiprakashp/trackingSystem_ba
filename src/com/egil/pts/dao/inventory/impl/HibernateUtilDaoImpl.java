package com.egil.pts.dao.inventory.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.inventory.UtilDao;
import com.egil.pts.modal.FeedBack;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.PtsHolidays;

@Repository("utilDao")
public class HibernateUtilDaoImpl extends HibernateGenericDao<FeedBack, Long> implements UtilDao {

	public void manangeFeedback(String title, String desc, Long userId) throws Throwable {
		Query q = getSession().createSQLQuery("");

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<FeedBack> getFeedback(String title, String desc, Long userId, Pagination pagination) throws Throwable {
		String sql = "select description, id, user_id userId, DATE_FORMAT(last_updated_date,'%Y-%M-%D')  updatedDate, title, status, feedback_by reporteBy, feedback_by name,  "
				+ "priority, created_date createdDate, comments from FEEDBACK_FORM where status <>'Completed' order by priority asc,created_date desc";
		if (title != null) {
			sql = "select description, id, user_id userId, last_updated_date updatedDate, title, status, feedback_by reporteBy, feedback_by name,  (case when (feedback_by =:title) then 1 else 0  end  )flag, "
					+ "priority, created_date createdDate, comments from FEEDBACK_FORM where status <>'Completed' &tit order by priority asc,created_date desc";
		}
		if (title.contains("Admin")) {
			sql = sql.replaceAll("&tit", " ");
		} else {
			sql = sql.replaceAll("&tit", " and feedback_by =:title ");
		}
		Query query = getSession().createSQLQuery(sql).addScalar("id", new LongType())
				.addScalar("userId", new LongType()).addScalar("description", new StringType())
				.addScalar("updatedDate", new StringType()).addScalar("title", new StringType())
				.addScalar("status", new StringType()).addScalar("comments", new StringType())
				.addScalar("priority", new StringType()).addScalar("reporteBy", new StringType())
				.addScalar("flag", new StringType()).addScalar("name", new StringType())
				.addScalar("createdDate", new DateType())
				.setResultTransformer(Transformers.aliasToBean(FeedBack.class));

		query.setString("title", title);

		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
		return query.list();

	}

	public Integer getFeedbackCount(String title, String desc, Long userId, Pagination pagination) {
		String sql = "select  count(*) from FEEDBACK_FORM where status <>'Completed' order by priority asc,created_date desc";
		if (title != null) {
			sql = "select  count(*)  from FEEDBACK_FORM where status <>'Completed' &tit ";
		}
		if (title.contains("Admin")) {
			sql = sql.replaceAll("&tit", " ");
		} else {
			sql = sql.replaceAll("&tit", " and feedback_by =:title ");
		}
		Query query = getSession().createSQLQuery(sql) ;

		if(!title.contains("Admin"))query.setString("title", title);
		int count = 0;
		BigInteger obj = (BigInteger) query.uniqueResult();
		if (obj != null) {
			count = obj.intValue();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void manangeFeedback(String title, String desc, String priority, String reporter, String name, Long userId)
			throws Throwable {
		getSession().createSQLQuery(
				"INSERT INTO FEEDBACK_FORM (description, user_id, last_updated_date, title, status,feedback_by, priority) "
						+ "VALUES(:desc, :userId, :date, :title,'Pending',:reporter,:priority) ")
				.setString("desc", desc).setLong("userId", userId).setDate("date", new Date()).setString("title", title)
				.setString("reporter", name).setString("priority", priority).executeUpdate();

	}

	@Override
	public void manageHolidays(String oper, PtsHolidays ptsHolidays) throws Throwable {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public int updateFeedBackStatus(String id, String title, String description, String priority, String comments, String status) {
		return getSession().createSQLQuery(
				"update FEEDBACK_FORM set priority=:priority,title=:title,description=:description,status=:status,comments=:comments where id=:id")
				.setLong("id", Long.parseLong(id)).setString("priority", priority).setString("comments", comments).setString("status", status)
				.setString("description", description).setString("title", title).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void deleteFeedBackStatus(String id) {
		getSession().createSQLQuery("delete from FEEDBACK_FORM   where id=:id").setParameterList("id", id.split(","))
				.executeUpdate();
	}

}
