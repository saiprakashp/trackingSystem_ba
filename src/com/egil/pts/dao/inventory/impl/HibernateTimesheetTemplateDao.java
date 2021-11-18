package com.egil.pts.dao.inventory.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.FloatType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.TimesheetTemplate;
import com.egil.pts.dao.inventory.TimesheetTemplateDao;
import com.egil.pts.modal.TimesheetActivity;

@Repository("timesheetTemplateDao")
public class HibernateTimesheetTemplateDao extends HibernateGenericDao<TimesheetTemplate, Long>
		implements TimesheetTemplateDao {

	@Override
	public void removeFromTemplate(List<Long> removedIds, Long userId) {
		String qryStr = "delete from PTS_USER_TIMESHEET_TEMPLATE where user_id=:userId ";
		if (removedIds != null && removedIds.size() > 0) {
			qryStr = qryStr + " and t.id in (:id)";
		}
		Query query = getSession().createSQLQuery(qryStr);
		query.setLong("userId", userId);

		if (removedIds != null && removedIds.size() > 0) {
			query.setParameterList("id", removedIds);
		}
		query.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TimesheetActivity> getTimeTemplate(Long userId) {
		Query query = getSession().createSQLQuery(
				" select t.ID as templateId, t.NETWORK_CODE_ID as networkId, t.ACTIVITY_CODE_ID as activityCode,t.ACTIVITY_TYPE as activityType, t.TYPE as type from PTS_USER_TIMESHEET_TEMPLATE t where "
						+ "t.USER_ID=:userId  and type REGEXP '[a-z]'  order by NETWORK_CODE_ID asc")
				.addScalar("templateId", new LongType()).addScalar("networkId", new LongType())
				.addScalar("activityCode", new LongType()).addScalar("type", new StringType())
				.addScalar("activityType", new StringType());
		query.setLong("userId", userId);
		List<TimesheetActivity> activityList = query
				.setResultTransformer(Transformers.aliasToBean(TimesheetActivity.class)).list();
		return activityList;

	}

	@Override
	public void saveTemplate(List<TimesheetTemplate> activityList) {
		int count = 0;
		if (activityList != null && activityList.size() > 0) {
			for (TimesheetTemplate userSkill : activityList) {
				save(userSkill);
				if (count % JDBC_BATCH_SIZE == 0) {
					getSession().flush();
					getSession().clear();
				}
				count++;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TimesheetActivity> getActualChargedHoursByNW(List<Long> nwIds, String stream, String userId) {
		Query query = getSession().createSQLQuery(
				" select t.network_code_id 'networkId',sum(MON_HRS) 'monHrs',sum(TUE_HRS) 'tueHrs',sum(WED_HRS) 'wedHrs',sum(THU_HRS) 'thuHrs',sum(FRI_HRS) 'friHrs',sum(SAT_HRS) 'satHrs',sum(SUN_HRS) 'sunHrs' "
						+ " FROM PTS_USER_TIMESHEET t, PTS_NETWORK_CODES nc , PTS_USER u ,PTS_USER_SUPERVISOR SU WHERE u.id=t.USER_ID and t.WEEKENDING_DATE >=nc.START_DATE and nc.id=t.network_code_id "
						+ " AND SU.SUPERVISOR_ID=:supervId and nc.id in (:nwId) AND SU.USER_ID=u.ID  and u.STREAM= :stream  group by t.network_code_id")
				.addScalar("monHrs", new FloatType()).addScalar("tueHrs", new FloatType())
				.addScalar("wedHrs", new FloatType()).addScalar("thuHrs", new FloatType())
				.addScalar("satHrs", new FloatType()).addScalar("friHrs", new FloatType())
				.addScalar("sunHrs", new FloatType()).addScalar("networkId", new LongType());

		query.setParameterList("nwId", nwIds);
		query.setParameter("stream", Long.parseLong(stream));
		query.setParameter("supervId", Long.parseLong(userId));

		List<TimesheetActivity> activityList = query
				.setResultTransformer(Transformers.aliasToBean(TimesheetActivity.class)).list();
		return activityList;

	}

	@Override
	public List<TimesheetActivity> getTimeTemplateDuplicate(Long nwId, List<Long> actCodes, Long userId) {
		Query query = getSession().createSQLQuery(
				"SELECT ID templateId, USER_ID userId, NETWORK_CODE_ID networkId, ACTIVITY_CODE_ID activityCode, `TYPE`, ACTIVITY_TYPE activityType, MON_HRS monHrs, TUE_HRS tueHrs, WED_HRS wedHrs, THU_HRS thuHrs, FRI_HRS friHrs, SAT_HRS satHrs,"
						+ " SUN_HRS sunHrs, STATUS from PTS_USER_TIMESHEET_TEMPLATE t where  "
						+ " NETWORK_CODE_ID =:nwId and t.activity_code_id not in (:actCode) and USER_ID =:userId order by NETWORK_CODE_ID asc")
				.addScalar("monHrs", new FloatType()).addScalar("tueHrs", new FloatType())
				.addScalar("wedHrs", new FloatType()).addScalar("thuHrs", new FloatType())
				.addScalar("satHrs", new FloatType()).addScalar("friHrs", new FloatType())
				.addScalar("sunHrs", new FloatType()).addScalar("networkId", new LongType())
				.addScalar("activityCode", new LongType()).addScalar("type", new StringType())
				.addScalar("activityType", new StringType()).addScalar("userId", new LongType());
		;
		query.setLong("userId", userId);
		query.setLong("nwId", nwId);
		query.setParameterList("actCode", actCodes);

		List<TimesheetActivity> activityList = query
				.setResultTransformer(Transformers.aliasToBean(TimesheetActivity.class)).list();
		return activityList;

	}

	@Override
	public List<TimesheetActivity> getRemainingTimeTemplate(Set<Long> nws, Long userId,String type) {
		String sqlQuery=
				"SELECT ID templateId, USER_ID userId, NETWORK_CODE_ID networkId, ACTIVITY_CODE_ID activityCode, `TYPE`, ACTIVITY_TYPE activityType, MON_HRS monHrs, TUE_HRS tueHrs, WED_HRS wedHrs, THU_HRS thuHrs, FRI_HRS friHrs, SAT_HRS satHrs,"
						+ " SUN_HRS sunHrs, STATUS from PTS_USER_TIMESHEET_TEMPLATE t where  "
						+ " &nw USER_ID =:userId  &type order by NETWORK_CODE_ID asc";
		if(nws.size() <=0) {
			sqlQuery=sqlQuery.replaceAll("&nw",  " ");
		}else {
			sqlQuery=sqlQuery.replaceAll("&nw", "NETWORK_CODE_ID  not in(:nws)   and");
		}
		
		if(type==null || type !=null && type.equalsIgnoreCase("D")) {
			sqlQuery=sqlQuery.replaceAll("&type"," and type REGEXP '[a-z]+' ");
		}else {
			sqlQuery=sqlQuery.replaceAll("&type"," and type REGEXP '[0-9]+' ");
		}
		Query query = getSession().createSQLQuery(sqlQuery)
				.addScalar("monHrs", new FloatType()).addScalar("tueHrs", new FloatType())
				.addScalar("wedHrs", new FloatType()).addScalar("thuHrs", new FloatType())
				.addScalar("satHrs", new FloatType()).addScalar("friHrs", new FloatType())
				.addScalar("sunHrs", new FloatType()).addScalar("networkId", new LongType())
				.addScalar("activityCode", new LongType()).addScalar("type", new StringType())
				.addScalar("activityType", new StringType()).addScalar("userId", new LongType());
		query.setLong("userId", userId);
		if(nws.size() >0) query.setParameterList("nws", nws);

		List<TimesheetActivity> activityList = query
				.setResultTransformer(Transformers.aliasToBean(TimesheetActivity.class)).list();
		return activityList;

	}

}
