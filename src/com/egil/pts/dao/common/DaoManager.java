package com.egil.pts.dao.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.egil.pts.dao.inventory.ActivityCodesDao;
import com.egil.pts.dao.inventory.AnnouncementsDao;
import com.egil.pts.dao.inventory.CapacityPlanningDao;
import com.egil.pts.dao.inventory.CategoryDao;
import com.egil.pts.dao.inventory.CustomerAccountDao;
import com.egil.pts.dao.inventory.CustomerDao;
import com.egil.pts.dao.inventory.DummyTestDao;
import com.egil.pts.dao.inventory.ESSDetailsDao;
import com.egil.pts.dao.inventory.LocationDao;
import com.egil.pts.dao.inventory.NetworkCodesDao;
import com.egil.pts.dao.inventory.PTSWorkingDaysDao;
import com.egil.pts.dao.inventory.PillarDao;
import com.egil.pts.dao.inventory.PrivilegeDao;
import com.egil.pts.dao.inventory.ProductOwnerDao;
import com.egil.pts.dao.inventory.ProjectDao;
import com.egil.pts.dao.inventory.PtsQueryUIDao;
import com.egil.pts.dao.inventory.ReportQueryMapperDao;
import com.egil.pts.dao.inventory.RolesDao;
import com.egil.pts.dao.inventory.StreamsDao;
import com.egil.pts.dao.inventory.TechnologiesDao;
import com.egil.pts.dao.inventory.TimesheetTemplateDao;
import com.egil.pts.dao.inventory.UserAccountsDao;
import com.egil.pts.dao.inventory.UserAppCompetencyScoreDao;
import com.egil.pts.dao.inventory.UserDao;
import com.egil.pts.dao.inventory.UserNetworkCodeDao;
import com.egil.pts.dao.inventory.UserNetworkCodeEffortDao;
import com.egil.pts.dao.inventory.UserPlatformDao;
import com.egil.pts.dao.inventory.UserProjectsDao;
import com.egil.pts.dao.inventory.UserSkillScoreDao;
import com.egil.pts.dao.inventory.UserSkillsDao;
import com.egil.pts.dao.inventory.UserTypesDao;
import com.egil.pts.dao.inventory.UserUtilizationDao;
import com.egil.pts.dao.inventory.UserWeekOffDao;
import com.egil.pts.dao.inventory.UtilDao;
import com.egil.pts.dao.inventory.WBSDao;

@Repository("daoManager")
public class DaoManager {

	@Autowired(required = true)
	private RolesDao rolesDao;

	@Autowired(required = true)
	private StreamsDao streamsDao;

	@Autowired(required = true)
	private CategoryDao categoryDao;

	@Autowired(required = true)
	private WBSDao wbsDao;

	@Autowired(required = true)
	private UserTypesDao userTypesDao;

	@Autowired(required = true)
	private PrivilegeDao privilegeDao;

	@Autowired(required = true)
	private NetworkCodesDao networkCodesDao;

	@Autowired(required = true)
	private ActivityCodesDao activityCodesDao;

	@Autowired(required = true)
	private CustomerDao customerDao;

	@Autowired(required = true)
	private PillarDao pillarDao;

	@Autowired(required = true)
	private ProjectDao projectDao;

	@Autowired(required = true)
	private UserDao userDao;

	@Autowired(required = true)
	private UserNetworkCodeDao userNetworkCodeDao;

	@Autowired(required = true)
	private UserUtilizationDao userUtilizationDao;

	@Autowired(required = true)
	private TechnologiesDao technologiesDao;

	@Autowired(required = true)
	private UserSkillsDao userSkillsDao;

	@Autowired(required = true)
	private UserPlatformDao userPlatformDao;

	@Autowired(required = true)
	private UserProjectsDao userProjectsDao;

	@Autowired(required = true)
	private UserNetworkCodeEffortDao userNetworkCodeEffortDao;

	@Autowired(required = true)
	private TimesheetTemplateDao timesheetTemplateDao;

	@Autowired(required = true)
	private ProductOwnerDao productOwnerDao;

	@Autowired(required = true)
	private DummyTestDao dummyTestDao;

	@Autowired(required = true)
	private CapacityPlanningDao capacityPlanningDao;

	@Autowired(required = true)
	private UserWeekOffDao userWeekOffDao;

	@Autowired(required = true)
	private LocationDao locationDao;

	@Autowired(required = true)
	private UserSkillScoreDao userSkillScoreDao;

	@Autowired(required = true)
	private UserAppCompetencyScoreDao userAppCompetencyScore;

	@Autowired(required = true)
	private ESSDetailsDao essDetailsDao;

	@Autowired(required = true)
	private PTSWorkingDaysDao workingDaysDao;

	@Autowired(required = true)
	private AnnouncementsDao announcementsDao;

	@Autowired(required = true)
	private CustomerAccountDao customerAccountDao;

	@Autowired(required = true)
	private UserAccountsDao userAccountsDao;

	@Autowired(required = true)
	private PtsQueryUIDao ptsQueryUIDao;

	@Autowired(required = true)
	private ReportQueryMapperDao ptsReportQueryMapperDao;


	@Autowired(required = true)
	private UtilDao utilDao;

	public RolesDao getRolesDao() {
		return rolesDao;
	}

	public void setRolesDao(RolesDao rolesDao) {
		this.rolesDao = rolesDao;
	}

	public PrivilegeDao getPrivilegeDao() {
		return privilegeDao;
	}

	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public NetworkCodesDao getNetworkCodesDao() {
		return networkCodesDao;
	}

	public void setNetworkCodesDao(NetworkCodesDao networkCodesDao) {
		this.networkCodesDao = networkCodesDao;
	}

	public ActivityCodesDao getActivityCodesDao() {
		return activityCodesDao;
	}

	public void setActivityCodesDao(ActivityCodesDao activityCodesDao) {
		this.activityCodesDao = activityCodesDao;
	}

	public DummyTestDao getDummyTestDao() {
		return dummyTestDao;
	}

	public void setDummyTestDao(DummyTestDao dummyTestDao) {
		this.dummyTestDao = dummyTestDao;
	}

	public UserUtilizationDao getUserUtilizationDao() {
		return userUtilizationDao;
	}

	public void setUserUtilizationDao(UserUtilizationDao userUtilizationDao) {
		this.userUtilizationDao = userUtilizationDao;
	}

	public UserNetworkCodeDao getUserNetworkCodeDao() {
		return userNetworkCodeDao;
	}

	public void setUserNetworkCodeDao(UserNetworkCodeDao userNetworkCodeDao) {
		this.userNetworkCodeDao = userNetworkCodeDao;
	}

	public UserTypesDao getUserTypesDao() {
		return userTypesDao;
	}

	public void setUserTypesDao(UserTypesDao userTypesDao) {
		this.userTypesDao = userTypesDao;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public WBSDao getWbsDao() {
		return wbsDao;
	}

	public void setWbsDao(WBSDao wbsDao) {
		this.wbsDao = wbsDao;
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public PillarDao getPillarDao() {
		return pillarDao;
	}

	public void setPillarDao(PillarDao pillarDao) {
		this.pillarDao = pillarDao;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public TechnologiesDao getTechnologiesDao() {
		return technologiesDao;
	}

	public void setTechnologiesDao(TechnologiesDao technologiesDao) {
		this.technologiesDao = technologiesDao;
	}

	public UserSkillsDao getUserSkillsDao() {
		return userSkillsDao;
	}

	public void setUserSkillsDao(UserSkillsDao userSkillsDao) {
		this.userSkillsDao = userSkillsDao;
	}

	public StreamsDao getStreamsDao() {
		return streamsDao;
	}

	public void setStreamsDao(StreamsDao streamsDao) {
		this.streamsDao = streamsDao;
	}

	public UserNetworkCodeEffortDao getUserNetworkCodeEffortDao() {
		return userNetworkCodeEffortDao;
	}

	public void setUserNetworkCodeEffortDao(UserNetworkCodeEffortDao userNetworkCodeEffortDao) {
		this.userNetworkCodeEffortDao = userNetworkCodeEffortDao;
	}

	public UserPlatformDao getUserPlatformDao() {
		return userPlatformDao;
	}

	public void setUserPlatformDao(UserPlatformDao userPlatformDao) {
		this.userPlatformDao = userPlatformDao;
	}

	public ProductOwnerDao getProductOwnerDao() {
		return productOwnerDao;
	}

	public void setProductOwnerDao(ProductOwnerDao productOwnerDao) {
		this.productOwnerDao = productOwnerDao;
	}

	public TimesheetTemplateDao getTimesheetTemplateDao() {
		return timesheetTemplateDao;
	}

	public void setTimesheetTemplateDao(TimesheetTemplateDao timesheetTemplateDao) {
		this.timesheetTemplateDao = timesheetTemplateDao;
	}

	public CapacityPlanningDao getCapacityPlanningDao() {
		return capacityPlanningDao;
	}

	public void setCapacityPlanningDao(CapacityPlanningDao capacityPlanningDao) {
		this.capacityPlanningDao = capacityPlanningDao;
	}

	public UserProjectsDao getUserProjectsDao() {
		return userProjectsDao;
	}

	public void setUserProjectsDao(UserProjectsDao userProjectsDao) {
		this.userProjectsDao = userProjectsDao;
	}

	public UserWeekOffDao getUserWeekOffDao() {
		return userWeekOffDao;
	}

	public void setUserWeekOffDao(UserWeekOffDao userWeekOffDao) {
		this.userWeekOffDao = userWeekOffDao;
	}

	public LocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public UserSkillScoreDao getUserSkillScoreDao() {
		return userSkillScoreDao;
	}

	public void setUserSkillScoreDao(UserSkillScoreDao userSkillScoreDao) {
		this.userSkillScoreDao = userSkillScoreDao;
	}

	public UserAppCompetencyScoreDao getUserAppCompetencyScore() {
		return userAppCompetencyScore;
	}

	public void setUserAppCompetencyScore(UserAppCompetencyScoreDao userAppCompetencyScore) {
		this.userAppCompetencyScore = userAppCompetencyScore;
	}

	public ESSDetailsDao getEssDetailsDao() {
		return essDetailsDao;
	}

	public void setEssDetailsDao(ESSDetailsDao essDetailsDao) {
		this.essDetailsDao = essDetailsDao;
	}

	public PTSWorkingDaysDao getWorkingDaysDao() {
		return workingDaysDao;
	}

	public void setWorkingDaysDao(PTSWorkingDaysDao workingDaysDao) {
		this.workingDaysDao = workingDaysDao;
	}

	public AnnouncementsDao getAnnouncementsDao() {
		return announcementsDao;
	}

	public void setAnnouncementsDao(AnnouncementsDao announcementsDao) {
		this.announcementsDao = announcementsDao;
	}

	public CustomerAccountDao getCustomerAccountDao() {
		return customerAccountDao;
	}

	public void setCustomerAccountDao(CustomerAccountDao customerAccountDao) {
		this.customerAccountDao = customerAccountDao;
	}

	public UserAccountsDao getUserAccountsDao() {
		return userAccountsDao;
	}

	public void setUserAccountsDao(UserAccountsDao userAccountsDao) {
		this.userAccountsDao = userAccountsDao;
	}

	public PtsQueryUIDao getPtsQueryUIDao() {
		return ptsQueryUIDao;
	}

	public void setPtsQueryUIDao(PtsQueryUIDao ptsQueryUIDao) {
		this.ptsQueryUIDao = ptsQueryUIDao;
	}

	public ReportQueryMapperDao getPtsReportQueryMapperDao() {
		return ptsReportQueryMapperDao;
	}

	public void setPtsReportQueryMapperDao(ReportQueryMapperDao ptsReportQueryMapperDao) {
		this.ptsReportQueryMapperDao = ptsReportQueryMapperDao;
	}

	public UtilDao getUtilDao() {
		return utilDao;
	}

	public void setUtilDao(UtilDao utilDao) {
		this.utilDao = utilDao;
	}

}
