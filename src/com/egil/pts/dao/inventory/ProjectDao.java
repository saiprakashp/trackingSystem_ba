package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.Project;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.UserContribution;

public interface ProjectDao extends GenericDao<Project, Long> {
	public List<Project> getProjects(Pagination pagination,
			SearchSortContainer searchSortObj) throws Throwable;

	public int getProjectsCount(SearchSortContainer searchSortObj)
			throws Throwable;

	public Integer deleteProjects(List<Long> projectIdList) throws Throwable;

	public List<UserContribution> getProjectsByAccount(List<Long> selectedAccountIds, Long userId) throws Throwable;

	public Long getPillarByProject(Long i);

}
