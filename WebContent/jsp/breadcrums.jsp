<style type="text/css">
.breadcrumb {
	width: auto;
	height: auto;
	background: transparent;
}
</style>
<div class='container-fluid' id='breadcrumb'>
	<nav aria-label="breadcrumb">
		<ol class="breadcrumb">

		</ol>
	</nav>
</div>

<script>
	function addBreadcrums() {
		var locPath = window.location.pathname;
		if (locPath.indexOf('login.action') > 0
				|| locPath.indexOf('showDashboard.action') > 0) {
			$("#breadcrumb ol")
					.html(
							'<li class="breadcrumb-itema active" aria-current="page"><a href="#">Home</a></li>');
		} else if (locPath.indexOf('logTimesheet.action') > 0
				|| locPath.indexOf('userUtilizationReport.action') > 0
				|| locPath.indexOf('saveResourceEffortDetails.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							' <li class="breadcrumb-item active" aria-current="page">Timesheet</li>');
		} else if (locPath.indexOf('manageNetworkCodesView.action') > 0
				|| locPath.indexOf('manageNetworkCodes.action') > 0
				|| locPath.indexOf('pmManageNetworkCodes.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Manage Projects</li>');
		} else if (locPath.indexOf('manageUser.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Manage Resources</li>');
		} else if (locPath.indexOf('releaseTrainReport.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Release Train Report</li>');
		} else if (locPath.indexOf('goCreateUser.action') > 0
				|| locPath.indexOf('addUser.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="user/manageUser.action">Manage Resources</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Create User</li>');
		} else if (locPath.indexOf('goEditUser.action') > 0
				|| locPath.indexOf('modifyUser.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="user/manageUser.action">Manage Resources</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Edit User</li>');
		} else if (locPath.indexOf('viewMyProfile.action') > 0
				|| locPath.indexOf('saveMyProfile.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">My Profile</li>');
		} else if (locPath.indexOf('goResetPassword.action') > 0
				|| locPath.indexOf('resetPassword.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Reset Password</li>');
		} else if (locPath.indexOf('utilization.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Resource Utilization</li>');
		} else if (locPath.indexOf('userContributionReport.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Summary/ Contribution</li>');
		} else if (locPath.indexOf('addNetworkCode.action') > 0
				|| locPath.indexOf('goCreateNetworkCode.action') > 0
				|| locPath.indexOf('goCloneNetworkCode.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="projects/manageNetworkCodes.action">Manage Projects</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Create Project</li>');
		} else if (locPath.indexOf('goEditNetworkCode.action') > 0
				|| locPath.indexOf('modifyNetworkCode.action') > 0
				|| locPath.indexOf('goViewNetworkCode.action') > 0
				|| locPath.indexOf('updateProjectPhase.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="projects/manageNetworkCodes.action">Manage Projects</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Edit Project</li>');
		} else if (locPath.indexOf('managePillars.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Manage Pillars/ Products</li>');
		} else if (locPath.indexOf('manageProjects.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Manage Applications</li>');
		} else if (locPath.indexOf('manageActivityCodes.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Manage Activity Codes</li>');
		} else if (locPath.indexOf('assignResources.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Assign Resources</li>');
		} else if (locPath.indexOf('mapNetworkCodes.action') > 0
				|| locPath.indexOf('addNetworkCodesToUser.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Assign Projects</li>');
		} else if (locPath.indexOf('manageCustomers.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Manage Customers</li>');
		} else if (locPath.indexOf('goApproveTimesheet.action') > 0
				|| locPath.indexOf('approveTimesheet.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Approve Timesheets</li>');
		} else if (locPath.indexOf('weekOff.action') > 0
				|| locPath.indexOf('saveUserWeekOff.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Record Week off</li>');
		} else if (locPath.indexOf('goUploadEssData.action') > 0
				|| locPath.indexOf('uploadEssData.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">ESS Data Feed</li>');
		} else if (locPath.indexOf('capacityPlanningByProjectType.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Capacity Forecast</li>');
		} else if (locPath.indexOf('capacityPlanning.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Capacity Planning</li>');
		} else if (locPath
				.indexOf('capacityPlanningDetailsEditableReport.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Capacity Planning</li>');
		} else if (locPath
				.indexOf('capacityPlanningDetailsReport.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Capacity View</li>');
		} else if (locPath.indexOf('announcements.action') > 0
				|| locPath.indexOf('saveAnnouncement.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Announcements</li>');
		} else if (locPath
				.indexOf('networkCodeUtilization.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Project wise Utilization</li>');
		} else if (locPath
				.indexOf('uploadCapacityImage.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Capacity Planning</li>');
		} else if (locPath
				.indexOf('capacityPlanningReport.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Capacity Forecast</li>');
		} else if (locPath
				.indexOf('compScoreReport.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Domain Skills</li>');
		} else if (locPath
				.indexOf('techScoreReport.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Technical Skills</li>');
		} else if (locPath
				.indexOf('userContributionDetailsReport.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Contribution Detail</li>');
		} else if (locPath
				.indexOf('manageNCUtilization.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Utilization</li>');
		} else if (locPath
				.indexOf('ricoUtilizationReport.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Utilization</li>');
		} else if (locPath
				.indexOf('manageUserUtilization.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">Timesheets Summary</li>');
		} else if (locPath
				.indexOf('manageRicoTrainings.action') > 0) {
			$("#breadcrumb ol")
					.append(
							'<li class="breadcrumb-item" ><a href="login/showDashboard.action">Home</a></li>');
			$("#breadcrumb ol")
					.append(
							'  <li class="breadcrumb-item active" aria-current="page">RICO Trainings</li>');
		}

	}
	addBreadcrums();
</script>
