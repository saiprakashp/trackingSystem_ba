package com.egil.pts.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egil.pts.service.ActivityCodesService;
import com.egil.pts.service.AnnouncementService;
import com.egil.pts.service.CapacityPlanningService;
import com.egil.pts.service.CategoryService;
import com.egil.pts.service.CustomerService;
import com.egil.pts.service.LocationService;
import com.egil.pts.service.NetworkCodeReportService;
import com.egil.pts.service.NetworkCodesService;
import com.egil.pts.service.PTSReportsService;
import com.egil.pts.service.PillarService;
import com.egil.pts.service.PrivilegeService;
import com.egil.pts.service.ProjectService;
import com.egil.pts.service.RolesService;
import com.egil.pts.service.StreamsService;
import com.egil.pts.service.TechnologiesService;
import com.egil.pts.service.UserEffortService;
import com.egil.pts.service.UserService;
import com.egil.pts.service.UserTypesService;
import com.egil.pts.service.UserUtilizationService;
import com.egil.pts.service.UtilService;
import com.egil.pts.service.WBSService;

@Service("serviceManager")
public class ServiceManager {

	@Autowired(required = true)
	@Qualifier("userService")
	private UserService userService;

	@Autowired(required = true)
	@Qualifier("rolesService")
	private RolesService rolesService;

	@Autowired(required = true)
	@Qualifier("streamsService")
	private StreamsService streamsService;

	@Autowired(required = true)
	@Qualifier("categoryService")
	private CategoryService categoryService;

	@Autowired(required = true)
	@Qualifier("wbsService")
	private WBSService wbsService;

	@Autowired(required = true)
	@Qualifier("userTypesService")
	private UserTypesService userTypesService;

	@Autowired(required = true)
	@Qualifier("privilegeService")
	private PrivilegeService privilegeService;

	@Autowired(required = true)
	@Qualifier("networkCodesService")
	private NetworkCodesService networkCodesService;

	@Autowired(required = true)
	@Qualifier("activityCodesService")
	private ActivityCodesService activityCodesService;

	@Autowired(required = true)
	@Qualifier("userUtilizationService")
	private UserUtilizationService userUtilizationService;

	@Autowired(required = true)
	@Qualifier("customerService")
	private CustomerService customerService;

	@Autowired(required = true)
	@Qualifier("pillarService")
	private PillarService pillarService;

	@Autowired(required = true)
	@Qualifier("projectService")
	private ProjectService projectService;

	@Autowired(required = true)
	@Qualifier("technologiesService")
	private TechnologiesService technologiesService;

	@Autowired(required = true)
	@Qualifier("ptsReportsService")
	private PTSReportsService ptsReportsService;

	@Autowired(required = true)
	@Qualifier("userEffortService")
	private UserEffortService userEffortService;

	@Autowired(required = true)
	@Qualifier("networkCodeReportService")
	private NetworkCodeReportService networkCodeReportService;

	@Autowired(required = true)
	@Qualifier("capacityPlanningService")
	private CapacityPlanningService capacityPlanningService;

	@Autowired(required = true)
	@Qualifier("locationService")
	private LocationService locationService;

	@Autowired(required = true)
	@Qualifier("announcementService")
	private AnnouncementService announcementService;

	@Autowired(required = true)
	@Qualifier("utilService")
	private UtilService utilService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RolesService getRolesService() {
		return rolesService;
	}

	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}

	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public NetworkCodesService getNetworkCodesService() {
		return networkCodesService;
	}

	public void setNetworkCodesService(NetworkCodesService networkCodesService) {
		this.networkCodesService = networkCodesService;
	}

	public ActivityCodesService getActivityCodesService() {
		return activityCodesService;
	}

	public void setActivityCodesService(ActivityCodesService activityCodesService) {
		this.activityCodesService = activityCodesService;
	}

	public UserUtilizationService getUserUtilizationService() {
		return userUtilizationService;
	}

	public void setUserUtilizationService(UserUtilizationService userUtilizationService) {
		this.userUtilizationService = userUtilizationService;
	}

	public UserTypesService getUserTypesService() {
		return userTypesService;
	}

	public void setUserTypesService(UserTypesService userTypesService) {
		this.userTypesService = userTypesService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public WBSService getWbsService() {
		return wbsService;
	}

	public void setWbsService(WBSService wbsService) {
		this.wbsService = wbsService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public PillarService getPillarService() {
		return pillarService;
	}

	public void setPillarService(PillarService pillarService) {
		this.pillarService = pillarService;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public TechnologiesService getTechnologiesService() {
		return technologiesService;
	}

	public void setTechnologiesService(TechnologiesService technologiesService) {
		this.technologiesService = technologiesService;
	}

	public PTSReportsService getPtsReportsService() {
		return ptsReportsService;
	}

	public void setPtsReportsService(PTSReportsService ptsReportsService) {
		this.ptsReportsService = ptsReportsService;
	}

	public StreamsService getStreamsService() {
		return streamsService;
	}

	public void setStreamsService(StreamsService streamsService) {
		this.streamsService = streamsService;
	}

	public UserEffortService getUserEffortService() {
		return userEffortService;
	}

	public void setUserEffortService(UserEffortService userEffortService) {
		this.userEffortService = userEffortService;
	}

	public NetworkCodeReportService getNetworkCodeReportService() {
		return networkCodeReportService;
	}

	public void setNetworkCodeReportService(NetworkCodeReportService networkCodeReportService) {
		this.networkCodeReportService = networkCodeReportService;
	}

	public CapacityPlanningService getCapacityPlanningService() {
		return capacityPlanningService;
	}

	public void setCapacityPlanningService(CapacityPlanningService capacityPlanningService) {
		this.capacityPlanningService = capacityPlanningService;
	}

	public LocationService getLocationService() {
		return locationService;
	}

	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}

	public AnnouncementService getAnnouncementService() {
		return announcementService;
	}

	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	public UtilService getUtilService() {
		return utilService;
	}

	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

}