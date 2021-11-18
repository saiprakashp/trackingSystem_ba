<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
</style>
<nav class="navbar navbar-expand-lg navbar-light  "
	style="background: #0082f0;" class="mb-1">
	<div class="container-fluid">
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#ptsNav" aria-controls="ptsNav" aria-expanded="false"
			aria-label="ptsNav">
			<span class="line"></span> <span class="line"></span> <span
				class="line"></span>
		</button>
		<div class="collapse navbar-collapse" id="ptsNav">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">

				<li class=" nav-item navHead"><a class="nav-link text-white "
					href="../login/showDashboard.action"><s:text
							name="pts.menu.home" /></a></li>

				<s:if
					test="#session['role'] != null && (#session['role'] eq 'ADMIN' || #session['role'] eq 'LINE MANAGER' )   || #session['userStream'] == 5 || #session['userStream'] == 6">
					<li class=" nav-item navHead"><a class="nav-link text-white "
						href="../user/manageUser.action"><s:text
								name="pts.menu.administrator" /></a></li>
				</s:if>


				<s:if
					test="(#session['role'] != null && #session['role'] eq 'ADMIN') || (#session['role'] eq 'LINE MANAGER') || (#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6))">

					<li class="nav-item navHead dropdown"><a
						class="nav-link text-white  dropdown-toggle" href="#"
						id="projectNavId" role="button" data-bs-toggle="dropdown"
						aria-expanded="false"><s:text name="pts.menu.product" /></a>
						<ul class="dropdown-menu" style="background: #F5F5F5 !important;">
							<s:if test="#session['role'] != null && #session['role'] eq 'NA'">
								<li><a class="dropdown-item"
									href="../projects/manageAccounts.action"> <s:text
											name="pts.networkcode.account.names" /></a></li>
							</s:if>
							<s:if
								test="#session['role'] != null && #session['role'] eq 'ADMIN'">
								<li><a class="dropdown-item"
									href="../projects/managePillars.action"> <s:text
											name="pts.menu.manage.pillar" /></a></li>

								<li><a class="dropdown-item"
									href="../projects/manageProjects.action"> <s:text
											name="pts.menu.manage.projects" /></a></li>

								<li><a class="dropdown-item"
									href="../projects/managePlannedUtilization.action"> <s:text
											name="pts.menu.manage.user.utilization" /></a></li>

								<li><a class="dropdown-item"
									href="../projects/initialProjectTFSMapper.action"> <s:text
											name="Upload TFS Data" /></a></li>
							</s:if>
							<s:if
								test="(#session['role'] != null && #session['role'] eq 'ADMIN') || (#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6))">
								<li><a class="dropdown-item"
									href="../projects/manageNetworkCodes.action"> <s:text
											name="pts.menu.manage.network.codes" /></a></li>
							</s:if>
							<s:else>
								<li><a class="dropdown-item"
									href="../projects/manageNetworkCodesView.action"> <s:text
											name="pts.menu.manage.network.codes" /></a></li>
							</s:else>
							<s:if
								test="#session['role'] != null && #session['role'] eq 'ADMIN'">
								<li><a class="dropdown-item"
									href="../projects/manageActivityCodes.action"><s:text
											name="pts.menu.manage.activity.codes" /></a></li>
							</s:if>
						</ul></li>
				</s:if>

				<s:if test="#session['role'] != null">
					<li class="nav-item navHead dropdown"><a
						class="nav-link text-white  dropdown-toggle" href="#"
						id="timeSheetNavId" role="button" data-bs-toggle="dropdown"
						aria-expanded="false"><s:text
								name="pts.menu.resource.timesheet" /></a>
						<ul class="dropdown-menu" style="background: #F5F5F5 !important;">

							<s:if test="#session['role'] neq 'ADMIN'">
								<li><a class="dropdown-item"
									href="../timesheet/logTimesheet.action"> <s:text
											name="pts.menu.resource.record.timesheet" /></a></li>

								<li><a class="dropdown-item"
									href="../utilization/userUtilizationReport.action"> <s:text
											name="pts.menu.user.utilization.report" />
								</A></li>
								<s:if test="#session['role'] eq 'LINE MANAGER'">
									<li><a class="dropdown-item"
										href="../timesheet/goApproveTimesheet.action"> <s:text
												name="pts.menu.approve.timesheet" />
									</a></li>
								</s:if>


								<s:if
									test="#session['role'] eq 'LINE MANAGER'  || #session['userStream'] == 5 || #session['userStream'] == 6">
									<li><a class="dropdown-item"
										href="../utilization/manageUserUtilization.action"> <s:text
												name="pts.menu.reportee.utilization.report" />
									</a></li>
								</s:if>

								<s:if
									test="(#session['role'] != null && #session['role'] eq 'ADMIN') || (#session['role'] eq 'LINE MANAGER') || (#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6))">

									<li><a class="dropdown-item"
										href="../utilization/ricoUtilizationReport.action"> <s:text
												name="pts.menu.utilization.report" />
									</a></li>
								</s:if>
								<s:if test="#session['role'] eq 'ADMIN'">
									<li><a class="dropdown-item"
										href="../timesheet/goUploadEssData.action"> <s:text
												name="pts.menu.utilization.ess.data.feed" />
									</a></li>

								</s:if>
							</s:if>
						</ul></li>
				</s:if>

				<li class="nav-item navHead dropdown"><a
					class="nav-link text-white  dropdown-toggle" href="#"
					id="profileMenuId" role="button" data-bs-toggle="dropdown"
					aria-expanded="false"><s:text name="pts.menu.profile" /></a>
					<ul class="dropdown-menu" style="background: #F5F5F5 !important;">
						<li><a class="dropdown-item"
							href="../user/viewMyProfile.action"> <s:text
									name="pts.menu.my.profile" /></a></li>
						<li><a class="dropdown-item"
							href="../user/resetPassword.action"> Change Password</a></li>
						<li><a class="dropdown-item"
							href="../user/getUserStableTeamsData.action"> <s:text
									name="Contibutions" /></a></li>
					</ul></li>

				<s:if test=" #session['username'] eq 'madmin' ">
					<li class="nav-item navHead dropdown"><a
						class="nav-link text-white  dropdown-toggle" href="#"
						id="profileMenuId" role="button" data-bs-toggle="dropdown"
						aria-expanded="false"><s:text name="Master Admin only" /></a>
						<ul class="dropdown-menu" style="background: #F5F5F5 !important;">
							<li><a class="dropdown-item"
								href="../user/gomasterResetPassword.action"> Master Password
									Reset</a></li>
							<li><a class="dropdown-item"
								href="../user/resetPassword.action"> Change Password</a></li>
							<li><a class="dropdown-item"
								href="../user/goGetPasswordmaster"> <s:text
										name="Master Password View" />
							</a></li>
						</ul></li>
				</s:if>



				<s:if
					test=" #session['role'] eq  'ADMIN' || #session['role'] eq 'LINE MANAGER' ">

					<li class="nav-item navHead dropdown"><a
						class="nav-link text-white  dropdown-toggle" href="#"
						id="profileMenuId" role="button" data-bs-toggle="dropdown"
						aria-expanded="false"><s:text name="pts.menu.reports" /></a>
						<ul class="dropdown-menu" style="background: #F5F5F5 !important;">
							<s:if
								test="(#session['role'] != null && #session['role'] eq 'ADMIN') || (#session['role'] eq 'LINE MANAGER') || (#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6))">
								<li><a class="dropdown-item"
									href="../reports/releaseTrainReport.action"> <s:text
											name="pts.menu.release.train.report" /></a></li>
							</s:if>
							<li><a class="dropdown-item"
								href="../user/utilization.action"> <s:text
										name="pts.menu.resource.utilization" />
							</a></li>
						</ul></li>

				</s:if>

				<s:if
					test="#session['username'] != null && #session['username'] eq 'madmin' || #session['role'] != null && #session['role'] eq 'ADMIN'">
					<li class="nav-item navHead dropdown"><a
						class="nav-link text-white  dropdown-toggle" href="#"
						id="profileMenuId" role="button" data-bs-toggle="dropdown"
						aria-expanded="false"><s:text name="Report Generator" /></a>
						<ul class="dropdown-menu" style="background: #F5F5F5 !important;">
							<li><a class="dropdown-item"
								href="../reports/getCustomDaoResults.action"> <s:text
										name="Generate Selected Reports" /></a></li>
							<s:if
								test="#session['username'] != null && #session['username'] eq 'madmin'">
								<li><a class="dropdown-item"
									href="../reports/managerCustomDaoResults.action"> <s:text
											name="Add New Reports" />
								</a></li>

								<li><a class="dropdown-item"
									href="../reports/executeManagerUtilDataMonthNow.action">
										Append Manager Utils Next Month</a></li>
							</s:if>
						</ul></li>

				</s:if>

				<s:if
					test=" #session['role'] eq  'ADMIN' || #session['role'] eq 'LINE MANAGER' ">
					<li class="nav-item navHead  "><a
						class="nav-link text-white   "
						href="../others/goGetfeedback.action" id="profileMenuId"
						role="button" aria-expanded="false"><s:text name="Feedback" /></a></li>

				</s:if>


			</ul>

		</div>
	</div>
</nav>
