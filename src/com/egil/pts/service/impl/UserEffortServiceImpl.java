package com.egil.pts.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.domain.ActivityCodes;
import com.egil.pts.dao.domain.EssDetails;
import com.egil.pts.dao.domain.NetworkCodes;
import com.egil.pts.dao.domain.TimesheetTemplate;
import com.egil.pts.dao.domain.User;
import com.egil.pts.dao.domain.UserTimesheet;
import com.egil.pts.modal.ActivityCodesNew;
import com.egil.pts.modal.Project;
import com.egil.pts.modal.TimesheetActivity;
import com.egil.pts.modal.TimesheetActivityWfm;
import com.egil.pts.service.UserEffortService;
import com.egil.pts.service.common.BaseUIService;
import com.egil.pts.util.GenericExcel;
import com.egil.pts.util.Utility;

@Service("userEffortService")
public class UserEffortServiceImpl extends BaseUIService implements UserEffortService {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveTimeSheet(List<TimesheetActivity> activityList, String assignedBy, Long assignedTo,
			Date weekendingDate, String removedIds) throws Throwable {
		List<UserTimesheet> userEffortList = new ArrayList<UserTimesheet>();
		List<Long> removedIdList = Utility.getListFromCommaSeparated(removedIds);
		convertModalToDomain(activityList, userEffortList, assignedBy, assignedTo, weekendingDate, removedIdList);
		if (removedIdList != null && removedIdList.size() > 0) {
			daoManager.getUserNetworkCodeEffortDao().delete(removedIdList);
		}
		daoManager.getUserNetworkCodeEffortDao().assignEffortToUser(userEffortList);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveTimeSheet(List<UserTimesheet> userEffortList, String removedIds) throws Throwable {
		List<Long> removedIdList = Utility.getListFromCommaSeparated(removedIds);
		if (removedIdList != null && removedIdList.size() > 0) {
			daoManager.getUserNetworkCodeEffortDao().delete(removedIdList);
			daoManager.getUserNetworkCodeEffortDao().clear();
		}
		daoManager.getUserNetworkCodeEffortDao().assignEffortToUser(userEffortList);
		daoManager.getUserNetworkCodeEffortDao().flush();
		daoManager.getUserNetworkCodeEffortDao().clear();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveTimeSheetWFM(List<TimesheetActivityWfm> activityList, Long userId, Long assignedTo,
			Date weekendingDate, String removedIds) throws Throwable {

		daoManager.getUserNetworkCodeEffortDao().assignEffortToUserWfm(activityList, userId, weekendingDate);
		daoManager.getUserNetworkCodeEffortDao().flush();
		daoManager.getUserNetworkCodeEffortDao().clear();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<TimesheetActivityWfm> getTimeSheetWFM(Long userId, Date weekendingDate) throws Throwable {
		List<TimesheetActivityWfm> a = daoManager.getUserNetworkCodeEffortDao().getEffortDetailsOfWeekWfm(userId,
				weekendingDate);
		daoManager.getUserNetworkCodeEffortDao().flush();
		daoManager.getUserNetworkCodeEffortDao().clear();
		return a;
	}

//used for aprrove time sheet
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void getEffortDetailsForWeek(List<TimesheetActivity> activityList, Date weekEndingFromDate,
			Date weekEndingToDate, Set<Long> userId, String approvalType) throws Throwable {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		boolean showOld = true;

		String weekEndFromDate = (weekEndingFromDate != null) ? format.format(weekEndingFromDate) : null;
		String weekEndToDate = weekEndingToDate != null ? format.format(weekEndingToDate) : null;
		List<UserTimesheet> userEffortList = daoManager.getUserNetworkCodeEffortDao()
				.getEffortDetailsOfWeek(weekEndFromDate, weekEndToDate, userId, approvalType, showOld);
		convertDomainToModal(activityList, userEffortList);
		addTotalstoList(activityList);
	}

	private void addTotalstoList(List<TimesheetActivity> activityList) {
		String prev = "";
		int i = 0;
		int tempi = 0;
		float tempTot = 0.0f;
		if (activityList.size() > 1) {
			for (TimesheetActivity p : activityList) {
				if (prev.equalsIgnoreCase(p.getUserName())) {
					i++;
					tempTot += p.getActivitySummation();
				} else {
					if (!prev.equalsIgnoreCase(p.getUserName()) && tempTot != 0) {
						activityList.get(tempi - i - 1).setTotalHrs(i + 1 + "|" + prev + "|" + tempTot);
					}
					prev = p.getUserName();
					tempTot = p.getActivitySummation();
					i = 0;
				}
				tempi++;
			}
			activityList.get(tempi - i - 1).setTotalHrs(i + 1 + "|" + prev + "|" + tempTot);
		} else if (activityList.size() == 1) {
			activityList.get(0).setTotalHrs(0 + "|" + prev + "|" + activityList.get(0).getActivitySummation());
		}
	}

	private void convertModalToDomain(List<TimesheetActivity> activityList, List<UserTimesheet> userEffortList,
			String assignedBy, Long assignedTo, Date weekendingDate, List<Long> removedIdList) throws Throwable {
		if (activityList != null && activityList.size() > 0) {
			UserTimesheet userEffort = null;
			for (TimesheetActivity activity : activityList) {
				if (activity != null && ((activity.getMonHrs() != null && activity.getMonHrs() > 0.0f)
						|| (activity.getTueHrs() != null && activity.getTueHrs() > 0.0f)
						|| (activity.getWedHrs() != null && activity.getWedHrs() > 0.0f)
						|| (activity.getThuHrs() != null && activity.getThuHrs() > 0.0f)
						|| (activity.getFriHrs() != null && activity.getFriHrs() > 0.0f)
						|| (activity.getSatHrs() != null && activity.getSatHrs() > 0.0f)
						|| (activity.getSunHrs() != null && activity.getSunHrs() > 0.0f))) {
					userEffort = new UserTimesheet();
					if (activity.getId() != null && activity.getId() != 0l) {
						userEffort.setId(activity.getId());
					}
					if (assignedTo != null && assignedTo != 0) {
						User user = new User();
						user.setId(assignedTo);
						userEffort.setUser(user);
					} else {
						if (activity.getUserId() != null && activity.getUserId() != 0l) {
							User user = new User();
							user.setId(activity.getUserId());
							userEffort.setUser(user);
						}
					}
					if (activity.getNetworkId() != null && activity.getNetworkId() != 0
							&& activity.getNetworkId() != -1) {
						NetworkCodes networkCode = new NetworkCodes();
						networkCode.setId(activity.getNetworkId());
						userEffort.setNetworkCode(networkCode);
					}
					if (activity.getActivityCode() != null && activity.getActivityCode() != 0
							&& activity.getActivityCode() != -1) {
						ActivityCodes activityCode = new ActivityCodes();
						activityCode.setId(activity.getActivityCode());
						userEffort.setActivityCode(activityCode);
					}
					if (assignedBy != null && !assignedBy.equals("")) {
						userEffort.setCreatedBy(assignedBy);
						userEffort.setUpdatedBy(assignedBy);
					}

					if (activity.getCreatedBy() != null && !activity.getCreatedBy().equals("")) {
						userEffort.setCreatedBy(activity.getCreatedBy());
					}

					if (activity.getCreatedDate() != null) {
						userEffort.setCreatedDate(activity.getCreatedDate());
					} else {
						userEffort.setCreatedDate(new Date());
					}
					userEffort.setUpdatedDate(new Date());

					if (activity.getWeekendingDate() != null) {
						userEffort.setWeekendingDate(activity.getWeekendingDate());
					} else {
						userEffort.setWeekendingDate(weekendingDate);
					}

					if (activity.getMonHrs() != null) {
						userEffort.setMonHrs(activity.getMonHrs());
					} else {
						userEffort.setMonHrs(0.0f);
					}
					if (activity.getTueHrs() != null) {
						userEffort.setTueHrs(activity.getTueHrs());
					} else {
						userEffort.setTueHrs(0.0f);
					}
					if (activity.getWedHrs() != null) {
						userEffort.setWedHrs(activity.getWedHrs());
					} else {
						userEffort.setWedHrs(0.0f);
					}
					if (activity.getThuHrs() != null) {
						userEffort.setThuHrs(activity.getThuHrs());
					} else {
						userEffort.setThuHrs(0.0f);
					}
					if (activity.getFriHrs() != null) {
						userEffort.setFriHrs(activity.getFriHrs());
					} else {
						userEffort.setFriHrs(0.0f);
					}
					if (activity.getSatHrs() != null) {
						userEffort.setSatHrs(activity.getSatHrs());
					} else {
						userEffort.setSatHrs(0.0f);
					}
					if (activity.getSunHrs() != null) {
						userEffort.setSunHrs(activity.getSunHrs());
					} else {
						userEffort.setSunHrs(0.0f);
					}

					if (activity.getType() != null && !activity.getType().equals("")) {
						userEffort.setType(activity.getType());
					} else {
						userEffort.setType("Vacation");
					}

				//	if (!activity.isDisableNw()) {

						if (activity.getApprovedStatusFlag()) {
							userEffort.setStatus("Approved");
						} else if (activity.getRejectedStatusFlag()) {
							userEffort.setStatus("Rejected");
						} else if (activity.getActivityType() != null
								&& (activity.getActivityType().equalsIgnoreCase("P")
										|| activity.getActivityType().equalsIgnoreCase("PTL"))
								&& activity.getTotal() <= 0.0d) {
							userEffort.setStatus("NA");
						} else {
							userEffort.setStatus("Pending");
						}
					//}else {
					//	userEffort.setStatus(activity.getApprovalStatus());
					//}

					userEffort.setActivityType((activity.getActivityType() == null) ? "D" : activity.getActivityType());

					userEffortList.add(userEffort);
				} else {
					if (activity != null && activity.getId() != null && activity.getId() != 0l) {
						if (removedIdList == null) {
							removedIdList = new ArrayList<Long>();
						}
						removedIdList.add(activity.getId());
					}
				}

			}
		} else {
			throw new Throwable("No records to Save");
		}
	}

	private void convertDomainToModal(List<TimesheetActivity> activityList, List<UserTimesheet> userEffortList)
			throws Throwable {
		if (userEffortList != null && userEffortList.size() > 0) {
			TimesheetActivity activity = null;

			for (UserTimesheet userEffort : userEffortList) {
				activity = new TimesheetActivity();
				if (userEffort.getId() != null && userEffort.getId() != 0l) {
					activity.setId(userEffort.getId());
				}

				if (userEffort.getUser() != null && userEffort.getUser().getId() != 0l) {
					activity.setUserId(userEffort.getUser().getId());
					activity.setUserName(userEffort.getUser().getPersonalInfo().getName());
				}

				activity.setActivityType(userEffort.getActivityType());

				if (userEffort.getNetworkCode() != null && userEffort.getNetworkCode().getId() != 0) {
					activity.setNetworkId(userEffort.getNetworkCode().getId());
					activity.setNetworkIdDesc((userEffort.getNetworkCode().getReleaseId() + " - "
							+ userEffort.getNetworkCode().getReleaseName()));
					switch (userEffort.getNetworkCode().getStatus()) {
					case ACTIVE:
						activity.setDisableNw(false);
						break;
					case Implemented:
						activity.setDisableNw(true);
						break;
					case Completed:
						activity.setDisableNw(true);
						break;
					case Execution:
						activity.setDisableNw(false);
						break;
					default:
						activity.setDisableNw(false);
						break;
					}

				}
				if (userEffort.getActivityCode() != null && userEffort.getActivityCode().getId() != 0) {
					activity.setActivityCode(userEffort.getActivityCode().getId());
					activity.setActivityDesc(userEffort.getActivityCode().getActivityCodeName());
				}
				if (userEffort.getType() != null && !userEffort.getType().equals("-1")) {
					activity.setType(userEffort.getType());
				}

				activity.setActivityType(userEffort.getActivityType());
				if (userEffort.getWeekendingDate() != null) {
					DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					activity.setWeekendingDate(format.parse(format.format(userEffort.getWeekendingDate())));
				}
				if (userEffort.getMonHrs() != null) {
					activity.setMonHrs(userEffort.getMonHrs());
				} else {
					activity.setMonHrs(0.0f);
				}
				if (userEffort.getTueHrs() != null) {
					activity.setTueHrs(userEffort.getTueHrs());
				} else {
					activity.setTueHrs(0.0f);
				}
				if (userEffort.getWedHrs() != null) {
					activity.setWedHrs(userEffort.getWedHrs());
				} else {
					activity.setWedHrs(0.0f);
				}
				if (userEffort.getThuHrs() != null) {
					activity.setThuHrs(userEffort.getThuHrs());
				} else {
					activity.setThuHrs(0.0f);
				}
				if (userEffort.getFriHrs() != null) {
					activity.setFriHrs(userEffort.getFriHrs());
				} else {
					activity.setFriHrs(0.0f);
				}
				if (userEffort.getSatHrs() != null) {
					activity.setSatHrs(userEffort.getSatHrs());
				} else {
					activity.setSatHrs(0.0f);
				}
				if (userEffort.getSunHrs() != null) {
					activity.setSunHrs(userEffort.getSunHrs());
				} else {
					activity.setSunHrs(0.0f);
				}

				if (userEffort.getType() != null) {
					activity.setType(userEffort.getType());
				}
				activity.setApprovalStatus(userEffort.getStatus());
				if (userEffort.getStatus() != null && userEffort.getStatus().equalsIgnoreCase("Approved")) {
					activity.setApprovedStatusFlag(true);
				} else if (userEffort.getStatus() != null && userEffort.getStatus().equalsIgnoreCase("Rejected")) {
					activity.setRejectedStatusFlag(true);
				} else {
					activity.setApprovedStatusFlag(false);
					activity.setRejectedStatusFlag(false);
				}

				if (userEffort.getCreatedBy() != null) {
					activity.setCreatedBy(userEffort.getCreatedBy());
				}

				if (userEffort.getCreatedDate() != null) {
					activity.setCreatedDate(userEffort.getCreatedDate());
				}

				if (userEffort.getActivityCode() != null && (userEffort.getActivityType().equalsIgnoreCase("P")
						|| userEffort.getActivityType().equalsIgnoreCase("PTL")
						|| userEffort.getActivityType().matches("[0-9]+"))) {

					if (userEffort.getProjectsNew() != null) {
						activity.setActivityCode(userEffort.getProjectsNew().getId());
						activity.setActivityDesc(userEffort.getProjectsNew().getName());
						activity.setActivityType(userEffort.getType());
					}
				}

				activityList.add(activity);
			}
		}
	}

	// @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void getTimeTemplate(List<TimesheetActivity> activityList,
			List<TimesheetActivity> activityListProductsToSave, Long userId) throws Throwable {

		Map<Long, List<Long>> userNwData = new LinkedHashMap<Long, List<Long>>();
		Set<Long> nwset = new HashSet<Long>();
		for (TimesheetActivity act : activityList) {
			if (userNwData.get(act.getNetworkId()) != null) {
				userNwData.get(act.getNetworkId()).add(act.getActivityCode());
			} else {
				List<Long> nw = new ArrayList<>();
				nw.add(act.getActivityCode());
				userNwData.put(act.getNetworkId(), nw);
			}
		}
		if (userNwData.size() > 0) {
			for (Long nwId : userNwData.keySet())
				activityList.addAll(daoManager.getTimesheetTemplateDao().getTimeTemplateDuplicate(nwId,
						userNwData.get(nwId), userId));
		}
		nwset.addAll(userNwData.keySet());
		activityList.addAll(daoManager.getTimesheetTemplateDao().getRemainingTimeTemplate(nwset, userId, "D"));
		userNwData.clear();
		nwset.clear();

		for (TimesheetActivity act : activityListProductsToSave) {
			if (act.getNetworkId() != null && act.getActivityCode() != null) {
				if (userNwData.get(act.getNetworkId()) != null) {
					userNwData.get(act.getNetworkId()).add(act.getActivityCode());
				} else {
					List<Long> nw = new ArrayList<>();
					nw.add(act.getActivityCode());
					userNwData.put(act.getNetworkId(), nw);
				}
			}
		}
		if (userNwData.size() > 0) {
			for (Long nwId : userNwData.keySet())
				activityListProductsToSave.addAll(daoManager.getTimesheetTemplateDao().getTimeTemplateDuplicate(nwId,
						userNwData.get(nwId), userId));
		}

		nwset.addAll(userNwData.keySet());

		activityListProductsToSave
				.addAll(daoManager.getTimesheetTemplateDao().getRemainingTimeTemplate(nwset, userId, "P"));
		userNwData.clear();
		nwset.clear();

		daoManager.getTimesheetTemplateDao().getTimeTemplate(userId);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void approveTimeSheet(List<TimesheetActivity> activityList, String updatedBy, Date weekendingDate)
			throws Throwable {
		List<UserTimesheet> userEffortList = new ArrayList<UserTimesheet>();
		convertModalToDomain(activityList, userEffortList, updatedBy, null, weekendingDate, null);
		daoManager.getUserNetworkCodeEffortDao().assignEffortToUserNew(userEffortList);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void approveTimeSheet(List<TimesheetActivity> activityList, String updatedBy, Date weekendingDate,
			boolean stats) throws Throwable {

		activityList.forEach(activity -> {
			try {
				if (activity.getApprovedStatusFlag()) {
					daoManager.getUserNetworkCodeEffortDao().assignEffortToUser(activity.getId(), "Approved", updatedBy,
							new Date());
				} else if (activity.getRejectedStatusFlag()) {
					daoManager.getUserNetworkCodeEffortDao().assignEffortToUser(activity.getId(), "Rejected", updatedBy,
							new Date());
				} else {
					daoManager.getUserNetworkCodeEffortDao().assignEffortToUser(activity.getId(), "Pending", updatedBy,
							new Date());
				}
				daoManager.getUserNetworkCodeEffortDao().flush();
				daoManager.getUserNetworkCodeEffortDao().clear();
			} catch (Throwable e) {
				e.printStackTrace();
			}

		});

	}

	@Override
	@Transactional
	public void uploadEssData(String uploadFileFileName) throws Throwable {

		List<EssDetails> essDetailsList = new ArrayList<EssDetails>();
		String filename = "/applications/tomcat/capacity/" + uploadFileFileName;
		GenericExcel excel = new GenericExcel(filename);
		String[][] excelContents = excel.readSheet(0);

		String[] excelHeaders = excelContents[0];
		String month = "", year = "";
		String rowdata = "";
		for (int row = 1; row < excelContents.length; row++) {
			EssDetails essDet = new EssDetails();
			rowdata = "";
			int col = 0;
			for (String colStr : excelHeaders) {

				if (colStr.equalsIgnoreCase("Signum")) {
					if (StringHelper.isNotEmpty(excelContents[row][col])) {
						essDet.setSignum(excelContents[row][col]);
						rowdata = rowdata + excelContents[row][col] + "\t";
					}
				} else if (colStr.contains("Target")) {
					if (StringHelper.isNotEmpty(excelContents[row][col])) {
						essDet.setTargetHrs(Float.parseFloat(excelContents[row][col]));
						rowdata = rowdata + excelContents[row][col] + "\t";
					}
				} else if (colStr.contains("Charged")) {
					if (StringHelper.isNotEmpty(excelContents[row][col])) {
						essDet.setChargedHrs(Float.parseFloat(excelContents[row][col]));
						rowdata = rowdata + excelContents[row][col] + "\t";
					}
				} else if (colStr.contains("Month")) {
					if (StringHelper.isNotEmpty(excelContents[row][col])) {
						month = excelContents[row][col];
						rowdata = rowdata + excelContents[row][col] + "\t";
					}
					essDet.setMonth(month);
				} else if (colStr.contains("Year")) {
					if (StringHelper.isNotEmpty(excelContents[row][col])) {
						year = excelContents[row][col];

						rowdata = rowdata + excelContents[row][col] + "\t";
					}
					essDet.setYear(Long.parseLong(year));
				}
				col++;
			}
			essDetailsList.add(essDet);
		}

		daoManager.getEssDetailsDao().setFlushModeToAuto();
		daoManager.getEssDetailsDao().removeESSDetails(Long.parseLong(year), month);
		daoManager.getEssDetailsDao().setFlushModeToCommit();

		if (essDetailsList != null && essDetailsList.size() > 0) {
			daoManager.getEssDetailsDao().saveESSDetails(essDetailsList);
		}
	}

	@Override
	public List<TimesheetActivity> getActualChargedHoursByNW(List<Long> nwIds, String userStream, String userId) {
		return daoManager.getTimesheetTemplateDao().getActualChargedHoursByNW(nwIds, userStream, userId);
	}

	@Override
	public List<TimesheetActivityWfm> getTimeSheetWFM(Long selectedEmployee, Date weekEnding, String showAllWfm,
			String showAll, Long stream) {
		return daoManager.getUserNetworkCodeEffortDao().getEffortDetailsOfWeekWfm(selectedEmployee, weekEnding,
				showAllWfm, showAll, stream);
	}

	// used for aprrove time sheet
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void getEffortDetailsForWeekForUsers(List<TimesheetActivity> activityList, Date weekEndingFromDate,
			Date weekEndingToDate, Set<Long> userId, String approvalType) throws Throwable {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		boolean showOld = false;
		if ((Utility.getWeekEndingDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date())))
				.before(weekEndingFromDate)) {
			showOld = true;
		}
		String weekEndFromDate = (weekEndingFromDate != null) ? format.format(weekEndingFromDate) : null;
		String weekEndToDate = weekEndingToDate != null ? format.format(weekEndingToDate) : null;
		List<UserTimesheet> userEffortList = daoManager.getUserNetworkCodeEffortDao()
				.getEffortDetailsOfWeek(weekEndFromDate, weekEndToDate, userId, approvalType, showOld);
		convertDomainToModal(activityList, userEffortList);
		addTotalstoList(activityList);
		// System.out.println(activityList);
	}

	// used for aprrove time sheet
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void getEffortDetailsForWeekForUsersProjects(List<TimesheetActivity> activityList, Date weekEndingFromDate,
			Date weekEndingToDate, Set<Long> userId, String approvalType) throws Throwable {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		boolean showOld = false;
		if ((Utility.getWeekEndingDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date())))
				.before(weekEndingFromDate)) {
			showOld = true;
		}
		String weekEndFromDate = (weekEndingFromDate != null) ? format.format(weekEndingFromDate) : null;
		String weekEndToDate = weekEndingToDate != null ? format.format(weekEndingToDate) : null;
		List<UserTimesheet> userEffortList = daoManager.getUserNetworkCodeEffortDao()
				.getEffortDetailsOfWeek(weekEndFromDate, weekEndToDate, userId, approvalType, showOld);
		convertDomainToModal(activityList, userEffortList);
		addTotalstoList(activityList);
	}

	@Override
	public void saveActivityTimeSheet(boolean weekoff, List<ActivityCodesNew> activityNewToSave, Long userId,
			String name, Date date) {
		for (ActivityCodesNew data : activityNewToSave) {
			daoManager.getUserNetworkCodeEffortDao().saveActivityEffort(weekoff, data, userId, name, date);
		}

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	@Override
	public void saveTemplate(List<TimesheetActivity> activityList, Long userId, String additionalProjects,
			String removeAdditional, List<Project> projects, List<com.egil.pts.modal.NetworkCodes> network)
			throws Throwable {
		daoManager.getTimesheetTemplateDao().removeFromTemplate(null, userId);
		daoManager.getTimesheetTemplateDao().flush();

		List<TimesheetTemplate> timeTemplateList = new ArrayList<TimesheetTemplate>();
		TimesheetTemplate timeTemplate = null;
		List<String> tmpProjects = new ArrayList<String>();

		additionalProjects = additionalProjects.replaceAll("_check", "");
		additionalProjects = additionalProjects.replaceAll("_", " ");
		if (additionalProjects != null && additionalProjects.length() > 0) {
			tmpProjects.addAll(Arrays.asList(additionalProjects.split(",")));
		}

		for (TimesheetActivity activity : activityList) {
			timeTemplate = new TimesheetTemplate();
			if (activity != null && activity.getNetworkId() != null && activity.getNetworkId() != -1) {

				if (activity.getActivityCode() == null
						|| (activity.getActivityCode() != null && activity.getActivityCode() == -1L))
					continue;

				if (activity.getType() != null && activity.getType().equalsIgnoreCase("-1L"))
					continue;

				if (activity.getNetworkId() == null)
					continue;
				/*
				 * timeTemplate.setMonHrs(activity.getMonHrs());
				 * timeTemplate.setTueHrs(activity.getTueHrs());
				 * timeTemplate.setWedHrs(activity.getWedHrs());
				 * timeTemplate.setThuHrs(activity.getThuHrs());
				 * timeTemplate.setFriHrs(activity.getFriHrs());
				 * timeTemplate.setSatHrs(activity.getSatHrs());
				 * timeTemplate.setSunHrs(activity.getSunHrs());
				 */
				timeTemplate.setActivityType((activity.getActivityType() == null) ? "D" : activity.getActivityType());
				timeTemplate.setUserId(userId);
				timeTemplate.setNetworkCodeId(activity.getNetworkId());
				timeTemplate.setActivityCodeId(activity.getActivityCode());
				timeTemplate.setType(activity.getType());
				timeTemplateList.add(timeTemplate);
			}
		}
		if (timeTemplateList.size() > 0) {
			daoManager.getTimesheetTemplateDao().saveTemplate(timeTemplateList);
			daoManager.getTimesheetTemplateDao().flush();
			daoManager.getTimesheetTemplateDao().clear();
		}
	}

}
