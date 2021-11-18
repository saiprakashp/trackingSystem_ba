package com.egil.pts.service;

import java.util.List;
import java.util.Map;

import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.Project;
import com.egil.pts.modal.SearchSortContainer;
import com.egil.pts.modal.SummaryResponse;
import com.egil.pts.modal.UserAcctPillarAppContribution;

public interface ProjectService {

	public SummaryResponse<Project> getProjectsSummary(Pagination pagination, SearchSortContainer searchSortContainer)
			throws Throwable;

	public List<Project> getProjects(Pagination pagination, SearchSortContainer searchSortContainer) throws Throwable;

	public void createProject(Project project) throws Throwable;

	public void modifyProject(Project project) throws Throwable;

	public Integer deleteProjects(List<Long> projectIdList) throws Throwable;

	public void getProjectsMap(Map<Long, String> projectsMap, String pillarId) throws Throwable;

	public String getJSONStringOfProjects() throws Throwable;

	public String loadProjects(String pillarId) throws Throwable;

	public SummaryResponse<Project> getUserProjects(Pagination pagination, SearchSortContainer searchSortContainer,
			boolean isAdmin) throws Throwable;

	public List<UserAcctPillarAppContribution> getProjectsByAccount(List<Long> selectedAccountIds, Long userId)
			throws Throwable;
}
