package com.egil.pts.dao.inventory;

import java.util.List;
import java.util.Set;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.TimesheetTemplate;
import com.egil.pts.modal.TimesheetActivity;

public interface TimesheetTemplateDao extends GenericDao<TimesheetTemplate, Long> {

	public void removeFromTemplate(List<Long> removedIds, Long userId) throws Throwable;

	public List<TimesheetActivity> getTimeTemplate(Long userId) throws Throwable;

	public void saveTemplate(List<TimesheetTemplate> activityList) throws Throwable;

	public List<TimesheetActivity> getActualChargedHoursByNW(List<Long> nwIds, String stream, String userId);

	public List<TimesheetActivity> getTimeTemplateDuplicate(Long nwId, List<Long> list,Long userId);

	public List<TimesheetActivity> getRemainingTimeTemplate(Set<Long> nws, Long userId,String type);

}
