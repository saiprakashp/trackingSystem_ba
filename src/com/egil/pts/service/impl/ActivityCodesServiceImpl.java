package com.egil.pts.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.modal.ActivityCodes;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Project;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.Status;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.service.ActivityCodesService;
import com.egil.pts.service.common.BaseUIService;

@Service("activityCodesService")
public class ActivityCodesServiceImpl extends BaseUIService implements ActivityCodesService {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public SummaryResponse<ActivityCodes> getActivityCodesSummary(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable {
		SummaryResponse<ActivityCodes> summary = new SummaryResponse<ActivityCodes>();
		summary.setTotalRecords(daoManager.getActivityCodesDao().getActivityCodesCount(searchSortObj));
		summary.setEnitities(getActivityCodes(pagination, searchSortObj));
		return summary;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<ActivityCodes> getActivityCodes(Pagination pagination, SearchSortContainer searchSortContainer)
			throws Throwable {
		List<ActivityCodes> modalActivityCodesList = new ArrayList<ActivityCodes>();
		List<com.egil.pts.dao.domain.ActivityCodes> domainActivityCodeList = daoManager.getActivityCodesDao()
				.getActivityCodes(pagination, searchSortContainer);
		ActivityCodes modalActivityCode = null;
		for (com.egil.pts.dao.domain.ActivityCodes domainActivityCode : domainActivityCodeList) {
			modalActivityCode = new ActivityCodes();
			convertDomainToModal(modalActivityCode, domainActivityCode);
			modalActivityCodesList.add(modalActivityCode);
		}
		return modalActivityCodesList;
	}

	@Override
	@Transactional
	public void createActivityCode(ActivityCodes modalActivityCode) throws Throwable {
		com.egil.pts.dao.domain.ActivityCodes domainActivityCode = new com.egil.pts.dao.domain.ActivityCodes();
		convertModalToDomain(modalActivityCode, domainActivityCode);
		daoManager.getActivityCodesDao().save(domainActivityCode);
	}

	@Override
	@Transactional
	public void modifyActivityCode(ActivityCodes modalActivityCode) throws Throwable {
		com.egil.pts.dao.domain.ActivityCodes domainActivityCode = new com.egil.pts.dao.domain.ActivityCodes();
		convertModalToDomain(modalActivityCode, domainActivityCode);
		daoManager.getActivityCodesDao().save(domainActivityCode);
		daoManager.getActivityCodesDao().flush();

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer deleteActivityCodes(List<Long> activityCodeIdList) throws Throwable {
		return daoManager.getActivityCodesDao().deleteActivityCodes(activityCodeIdList);
	}

	private void convertModalToDomain(ActivityCodes modalActivityCode,
			com.egil.pts.dao.domain.ActivityCodes domainActivityCode) {
		if (modalActivityCode != null && domainActivityCode != null) {
			if (modalActivityCode.getId() != null) {
				domainActivityCode.setId(modalActivityCode.getId());
			}
			if (modalActivityCode.getActivityCode() != null) {
				domainActivityCode.setActivityCodeId(modalActivityCode.getActivityCode());
			}
			if (modalActivityCode.getActivity() != null) {
				domainActivityCode.setActivityCodeName(modalActivityCode.getActivity());
			}
			if (modalActivityCode.getDescription() != null) {
				domainActivityCode.setDescription(modalActivityCode.getDescription());
			}
			if (modalActivityCode.getStatus() != null) {
				domainActivityCode.setStatus(Status.getBooleanValueOf(modalActivityCode.getStatus()));
			}
			if (modalActivityCode.getCreatedBy() != null) {
				domainActivityCode.setCreatedBy(modalActivityCode.getCreatedBy());
			}

			if (modalActivityCode.getCreatedDate() != null) {
				domainActivityCode.setCreatedDate(modalActivityCode.getCreatedDate());
			}

			if (modalActivityCode.getUpdatedBy() != null) {
				domainActivityCode.setUpdatedBy(modalActivityCode.getUpdatedBy());
			}
			if (modalActivityCode.getUpdatedDate() != null) {
				domainActivityCode.setUpdatedDate(modalActivityCode.getUpdatedDate());
			}
		}
	}

	private void convertDomainToModal(ActivityCodes modalActivityCode,
			com.egil.pts.dao.domain.ActivityCodes domainActivityCode) {
		if (modalActivityCode != null && domainActivityCode != null) {
			if (domainActivityCode.getId() != null) {
				modalActivityCode.setId(domainActivityCode.getId());
			}
			if (domainActivityCode.getType() != null) {
				modalActivityCode.setType(domainActivityCode.getType());
			}
			if (domainActivityCode.getActivityCodeId() != null) {
				modalActivityCode.setActivityCode(domainActivityCode.getActivityCodeId());
			}
			if (domainActivityCode.getActivityCodeName() != null) {
				modalActivityCode.setActivity(domainActivityCode.getActivityCodeName());
			}
			if (domainActivityCode.getDescription() != null) {
				modalActivityCode.setDescription(domainActivityCode.getDescription());
			}

			if (domainActivityCode.getCreatedBy() != null) {
				modalActivityCode.setCreatedBy(domainActivityCode.getCreatedBy());
			}

			if (domainActivityCode.getCreatedDate() != null) {
				modalActivityCode.setCreatedDate(domainActivityCode.getCreatedDate());
			}

			if (domainActivityCode.getUpdatedBy() != null) {
				modalActivityCode.setUpdatedBy(domainActivityCode.getUpdatedBy());
			}
			if (domainActivityCode.getUpdatedDate() != null) {
				modalActivityCode.setUpdatedDate(domainActivityCode.getUpdatedDate());
			}
		}
	}

	@Override
	public void getActivityCodesMap(Map<Long, String> activityCodesMap) throws Throwable {
		for (ActivityCodes activityCode : getActivityCodes(null, null)) {
			activityCodesMap.put(activityCode.getId(), activityCode.getActivity());
		}
	}

	@Override
	public void getActivityCodesMapPtl(Map<Long, String> activityCodesPtlMap) throws Throwable {
		for (ActivityCodes activityCode : getActivityCodesPtl(null, null)) {
			activityCodesPtlMap.put(activityCode.getId(), activityCode.getActivity());
		}

	}

	@Override
	public void getActivityCodesMapProject(Map<Long, String> activityCodesProjectMap) throws Throwable {
		for (ActivityCodes activityCode : getActivityCodesProject(null, null)) {
			activityCodesProjectMap.put(activityCode.getId(), activityCode.getActivity());
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<ActivityCodes> getActivityCodesPtl(Pagination pagination, SearchSortContainer searchSortContainer)
			throws Throwable {
		searchSortContainer = new SearchSortContainer();
		searchSortContainer.setType("PTL");
		List<ActivityCodes> modalActivityCodesList = new ArrayList<ActivityCodes>();
		List<com.egil.pts.dao.domain.ActivityCodes> domainActivityCodeList = daoManager.getActivityCodesDao()
				.getActivityCodes(pagination, searchSortContainer);
		ActivityCodes modalActivityCode = null;
		for (com.egil.pts.dao.domain.ActivityCodes domainActivityCode : domainActivityCodeList) {
			modalActivityCode = new ActivityCodes();
			convertDomainToModal(modalActivityCode, domainActivityCode);
			modalActivityCodesList.add(modalActivityCode);
		}
		return modalActivityCodesList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<ActivityCodes> getActivityCodesProject(Pagination pagination, SearchSortContainer searchSortContainer)
			throws Throwable {
		searchSortContainer = new SearchSortContainer();
		searchSortContainer.setType("P");
		List<ActivityCodes> modalActivityCodesList = new ArrayList<ActivityCodes>();
		List<com.egil.pts.dao.domain.ActivityCodes> domainActivityCodeList = daoManager.getActivityCodesDao()
				.getActivityCodes(pagination, searchSortContainer);
		ActivityCodes modalActivityCode = null;
		for (com.egil.pts.dao.domain.ActivityCodes domainActivityCode : domainActivityCodeList) {
			modalActivityCode = new ActivityCodes();
			convertDomainToModal(modalActivityCode, domainActivityCode);
			modalActivityCodesList.add(modalActivityCode);
		}
		return modalActivityCodesList;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<ActivityCodes> getActivityCodesManagement(Pagination pagination, SearchSortContainer searchSortContainer)
			throws Throwable {
		searchSortContainer = new SearchSortContainer();
		searchSortContainer.setType("M");
		List<ActivityCodes> modalActivityCodesList = new ArrayList<ActivityCodes>();
		List<com.egil.pts.dao.domain.ActivityCodes> domainActivityCodeList = daoManager.getActivityCodesDao()
				.getActivityCodes(pagination, searchSortContainer);
		ActivityCodes modalActivityCode = null;
		for (com.egil.pts.dao.domain.ActivityCodes domainActivityCode : domainActivityCodeList) {
			modalActivityCode = new ActivityCodes();
			convertDomainToModal(modalActivityCode, domainActivityCode);
			modalActivityCodesList.add(modalActivityCode);
		}
		return modalActivityCodesList;
	}
	
	@Override
	public void getActivityCodesManagement(Map<Long, String> activityCodesManagementMap) throws Throwable {
		for (ActivityCodes activityCode : getActivityCodesManagement(null, null)) {
			activityCodesManagementMap.put(activityCode.getId(), activityCode.getActivity());
		}
	}
	
	@Override
	public void getProjectMapNew(Map<Long, String> projectAssignement) {
		List<Object[]> data = daoManager.getActivityCodesDao().getProjectActivityDao();
		if (data.size() > 0) {
			for (Object[] d : data) {
				projectAssignement.put((Long) d[0], d[1] + "");
			}
		}

	}

	@Override
	public List<Project> getProjectMapList() {
		List<Project> project = new ArrayList<Project>();
		List<Object[]> data = daoManager.getActivityCodesDao().getProjectActivityDao();
		if (data.size() > 0) {
			for (Object[] d : data) {
				Project p = new Project();
				p.setProjectId((Long) d[0]);
				p.setProjectName(d[1] + "");
				p.setOrder((Long) d[2]);
				p.setType(d[3] + "");
				project.add(p);
			}
		}
		return project;
	}

	@Override
	public Long getActivityCodeIds(String type, String activityType) {
		return (Long) daoManager.getActivityCodesDao().getActivityCodes(type, activityType);
	}

}
