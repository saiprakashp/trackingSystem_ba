package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.UserProjects;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.SearchSortContainer;

public abstract interface UserProjectsDao extends
		GenericDao<UserProjects, Long> {
	public abstract void saveUserProjects(List<UserProjects> paramList)
			throws Throwable;

	public abstract int removeUserProjects(Long paramLong, List<Long> paramList)
			throws Throwable;

	public int getUserProjectsCount(SearchSortContainer searchSortContainer,
			boolean isAdmin);

	public List<com.egil.pts.modal.Project> getUserProjects(
			Pagination pagination, SearchSortContainer searchSortContainer,
			boolean isAdmin);

	public List<UserProjects> getUserProjects(Long userId, Long projectId)
			throws Throwable;
}
