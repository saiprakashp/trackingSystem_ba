<%@ taglib prefix="s" uri="/struts-tags"%>
<div
	style="width: 100%; background: #0082f0; padding-bottom: 10px; _padding-bottom: 4px;">
	<!-- <div id="spacer5px" style="background: #FFF; height:10px; _height:4px;">&nbsp;</div> -->

	<div id="pagetopnew"
		style="height: 24px; width: 1000px; text-align: left; font-size: 18px; border-top: 1px dotted #cccccc;">
		<nav id="wrapper_menu" style="width: 950px;">
			<!--Main Menu (desktop and tablet)-->
			<ul class="globalnav">
				<li><s:a href="../login/showDashboard.action"
						cssClass="navlink" id="homeMenu">
						<s:text name="pts.menu.home" />
					</s:a></li>
				<s:if
					test="#session['role'] != null && (#session['role'] eq 'ADMIN' || #session['role'] eq 'LINE MANAGER' )   || #session['userStream'] == 5 || #session['userStream'] == 6">
					<li><a id="administratorMenu" class="drop navlink"
						href="../user/manageUser.action"><s:text
								name="pts.menu.administrator" /></a></li>
				</s:if>
				<s:if
					test="(#session['role'] != null && #session['role'] eq 'ADMIN') || (#session['role'] eq 'LINE MANAGER') || (#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6))">
					<li><a id="projectsMenu" class="drop navlink"
						href="javascript:void(0);"><s:text name="pts.menu.product" /></a>
						<span class="selected_link">&nbsp;</span>
						<div class="dropdown dropdown_2columns">
							<div class="col_1 firstcolumn">
								<ul class="link_list">
									<s:if
										test="#session['role'] != null && #session['role'] eq 'NA'">
										<li><s:a cssClass="sub"
												href="../projects/manageAccounts.action">
												<s:text name="pts.networkcode.account.names" />
											</s:a></li>
									</s:if>
									<s:if
										test="#session['role'] != null && #session['role'] eq 'ADMIN'">
										<li><s:a cssClass="sub"
												href="../projects/managePillars.action">
												<s:text name="pts.menu.manage.pillar" />
											</s:a></li>
										<li><s:a cssClass="sub"
												href="../projects/manageProjects.action">
												<s:text name="pts.menu.manage.projects" />
											</s:a></li>
										<li><s:a cssClass="sub"
												href="../projects/managePlannedUtilization.action">
												<s:text name="pts.menu.manage.user.utilization" />
											</s:a></li>
										<li><s:a cssClass="sub"
												href="../projects/initialProjectTFSMapper.action">
												<s:text name="Upload TFS Data" />
											</s:a></li>
									</s:if>
									<s:if
										test="(#session['role'] != null && #session['role'] eq 'ADMIN') || (#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6))">
										<li><s:a cssClass="sub"
												href="../projects/manageNetworkCodes.action">
												<s:text name="pts.menu.manage.network.codes" />
											</s:a></li>
									</s:if>
									

									<s:else>
										<li><s:a cssClass="sub"
												href="../projects/manageNetworkCodesView.action">
												<s:text name="pts.menu.manage.network.codes" />
											</s:a></li>
									</s:else>
									<s:if
										test="#session['role'] != null && #session['role'] eq 'ADMIN'">
										<li><a href="../projects/manageActivityCodes.action"><s:text
													name="pts.menu.manage.activity.codes" /></a></li>

									</s:if>
									<s:if
										test="(#session['role'] != null && #session['role'] eq 'ADMIN') || (#session['userStream'] != null  && #session['role'] eq 'LINE MANAGER') || (#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6))">
										<li><s:a cssClass="sub"
												href="../projects/manageUserNetworkContribution.action">
												<s:text name="pts.menu.manage.network.user.stable.codes" />
											</s:a></li>
									</s:if>
									<%--  <s:if
										test="#session['role'] != null && #session['role'] eq 'LINE MANAGER'">
										<li><a href="../projects/pmManageNetworkCodes.action"><s:text
													name="pts.menu.manage.network.codes" /></a></li>
									</s:if>  
									<s:if
										test="#session['role'] != null && #session['role'] neq 'USER'">
										<li><a href="../projects/assignResources.action"> <s:text
													name="pts.menu.assign.resources" /></a></li>
									</s:if>
									<s:if
										test="(#session['role'] != null && #session['role'] eq 'LINE MANAGER')  || (#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6))">
										<li><a href="../projects/mapNetworkCodes.action"> <s:text
													name="pts.menu.assign.network.codes" /></a></li>
									</s:if>
									--%>
									<!-- <li><a href="../projects/announcements.action"><s:text
												name="pts.menu.announcements" /></a></li> -->
								</ul>
							</div>
						</div></li>
				</s:if>
				<s:if test="#session['role'] != null">
					<li><a id="utilizationMenu" class="drop navlink"
						href="javascript:void(0);"><s:text
								name="pts.menu.resource.timesheet" /></a> <span
						class="selected_link">&nbsp;</span>
						<div class="dropdown dropdown_2columns">
							<div class="col_1 firstcolumn">
								<ul class="link_list">
									<s:if test="#session['role'] neq 'ADMIN'">
										<li><s:a cssClass="sub"
												href="../timesheet/logTimesheet.action">
												<s:text name="pts.menu.resource.record.timesheet" />
											</s:a></li>
										<%-- 	<li><s:a cssClass="sub"
												href="../timesheet/logTimesheetWFM.action">
												<s:text name="pts.menu.resource.record.timesheet.whm" />
											</s:a></li> --%>

									</s:if>

									<li><s:url var="userutilizationurl"
											action="../utilization/userUtilizationReport.action"
											escapeAmp="false">
											<s:param name="userFlag" value="true" />
										</s:url> <s:a href="%{userutilizationurl}">
											<s:text name="pts.menu.user.utilization.report" />
										</s:a></li>
									<s:if test="#session['role'] eq 'LINE MANAGER'">
										<li><s:a href="../timesheet/goApproveTimesheet.action">
												<s:text name="pts.menu.approve.timesheet" />
											</s:a></li>


									</s:if>

									<s:if
										test="#session['role'] eq 'LINE MANAGER'  || #session['userStream'] == 5 || #session['userStream'] == 6">
										<li><s:a
												href="../utilization/manageUserUtilization.action">
												<s:text name="pts.menu.reportee.utilization.report" />
											</s:a></li>
									</s:if>
									<!-- <li><s:a href="../timesheet/weekOff.action">
												<s:text name="pts.user.weekoff" />
											</s:a></li> -->
									<s:if
										test="(#session['role'] != null && #session['role'] eq 'ADMIN') || (#session['role'] eq 'LINE MANAGER') || (#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6))">
										<!--<li><s:a
												href="../utilization/networkCodeUtilization.action">
												<s:text name="pts.menu.project.wise.utilization" />
											</s:a></li> -->
										<li><s:a
												href="../utilization/ricoUtilizationReport.action">
												<s:text name="pts.menu.utilization.report" />
											</s:a></li>
									</s:if>
									<s:if test="#session['role'] eq 'ADMIN'">
										<li><s:a href="../timesheet/goUploadEssData.action">
												<s:text name="pts.menu.utilization.ess.data.feed" />
											</s:a></li>

									</s:if>
									<!-- <s:if
					test="#session['role'] != null && (#session['role'] eq 'ADMIN')">
					<li><s:a
												href="../utilization/ricoUtilizationReport.action">
												<s:text name="Upload ESS Info" />
											</s:a></li>
					</s:if>-->
								</ul>
							</div>
						</div></li>
				</s:if>
				<li><a id="profileMenu" class="drop navlink"
					href="javascript:void(0);"><s:text name="pts.menu.profile" /></a>
					<span class="selected_link">&nbsp;</span>
					<div class="dropdown dropdown_2columns">
						<div class="col_1 firstcolumn">
							<ul class="link_list">
								<li><s:a cssClass="sub" href="../user/viewMyProfile.action">
										<s:text name="pts.menu.my.profile" />
									</s:a></li>
								<li><s:a cssClass="sub" href="../user/resetPassword.action">
										 Change Password
									</s:a></li>
								<li><a id="userContribution" class="drop navlink"
									href="../user/getUserStableTeamsData.action"><s:text
											name="Contibutions" /></a></li>
							</ul>
						</div>
					</div></li>
				<%-- <s:if
					test="#session['role'] != null && (#session['role'] eq 'ADMIN' || #session['role'] eq 'LINE MANAGER' )">
					<li><a id="capacityMenu" class="drop navlink"
						href="javascript:void(0);"><s:text
								name="pts.menu.project.capacity.plannning" /></a> <span
						class="selected_link">&nbsp;</span>
						<div class="dropdown dropdown_2columns">
							<div class="col_1 firstcolumn">
								<ul class="link_list">
									<li><a id="profileMenu" class="drop navlink"
										href="../capacity/capacityPlanning.action"><s:text
												name="Capacity Planning By Resource" /></a></li>
									<li><a id="profileMenu" class="drop navlink"
										href="../capacity/capacityPlanningDetailsEditableReport.action"><s:text
												name="Capacity Planning By Project" /></a></li>
									<!--<li><a href="../reports/uploadCapacityImage.action"> <s:text
												name="pts.menu.project.capacity.plannning.upload" /></a></li>-->

								</ul>
							</div>
						</div></li>

				</s:if> --%>
				<s:if test=" #session['username'] eq 'madmin' ">
					<li><a id="supportMenu" class="drop navlink"
						href="javascript:void(0);"><s:text name="Master Admin only" /></a>
						<span class="selected_link">&nbsp;</span>
						<div class="dropdown dropdown_2columns">
							<div class="col_1 firstcolumn">
								<ul class="link_list">

									<li><s:a cssClass="sub"
											href="../user/gomasterResetPassword.action">
										Master Password Reset
									</s:a></li>
									<li><s:a cssClass="sub"
											href="../user/goGetPasswordmaster.action">
										Master Password View
									</s:a></li>
								</ul>
							</div>
						</div></li>

				</s:if>
				<s:if
					test=" #session['role'] eq  'ADMIN' || #session['role'] eq 'LINE MANAGER' ">
					<li><a id="supportMenu" class="drop navlink"
						href="javascript:void(0);"><s:text name="pts.menu.reports" /></a>
						<span class="selected_link">&nbsp;</span>
						<div class="dropdown dropdown_2columns">
							<div class="col_1 firstcolumn">
								<ul class="link_list">
									<!--<li><a href="../reports/userContributionReport.action">
											<s:text name="pts.menu.reports.user.contribution.report" />
									</a></li>
								 	<li><a id="profileMenu" class="drop navlink"
										href="../reports/capacityPlanningReport.action"><s:text
												name="pts.menu.project.capacity.plannning.report" /></a></li>
									<li><a id="profileMenu" class="drop navlink"
										href="../reports/capacityPlanningDetailsReport.action"><s:text
												name="pts.menu.project.capacity.plannning.details.report" /></a></li>
									<li><a id="profileMenu" class="drop navlink"
										href="../reports/userContributionDetailsReport.action"><s:text
												name="pts.menu.project.contribution.details.report" /></a></li>-->
									<s:if
										test="(#session['role'] != null && #session['role'] eq 'ADMIN') || (#session['role'] eq 'LINE MANAGER') || (#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6))">
										<li><a id="profileMenu" class="drop navlink"
											href="../reports/releaseTrainReport.action"><s:text
													name="pts.menu.release.train.report" /></a></li>
									</s:if>
									<!--		<li><a id="profileMenu" class="drop navlink"
										href="../capacity/capacityPlanningByProjectType.action"><s:text
												name="Capacity Planing By Project Type" /></a></li>-->
									<%-- 									<s:if
										test="#session['role'] != null && (#session['role'] eq 'ADMIN' || #session['role'] eq 'LINE MANAGER' )">
 --%>
									<li><s:a cssClass="sub" href="../user/utilization.action">
											<s:text name="pts.menu.resource.utilization" />
										</s:a></li>
									<%-- 				</s:if> --%>
									<!--<li><a href="../reports/techScoreReport.action"> <s:text
												name="pts.menu.tech.score.report" />
									</a></li>
									<li><a href="../reports/compScoreReport.action"> <s:text
												name="pts.menu.comp.score.report" />
									</a></li>  <s:if
										test="#session['role'] eq 'LINE MANAGER' || #session['role'] eq 'ADMIN'">
								 	<li><s:a href="../reports/userNWUtilization.action">
												<s:text name="pts.menu.network.utilization.report" />
											</s:a></li> 
										<li><s:a cssClass="sub"
												href="../reports/goGetReportforLM.action">
												<s:text name="pts.pm.report.download" />
											</s:a></li>-->
				</s:if>

			</ul>
	</div>
</div>
</li>
</s:if>
<s:if
	test="#session['username'] != null && #session['username'] eq 'madmin' || #session['role'] != null && #session['role'] eq 'ADMIN'">
	<li><a id="supportMenu" class="drop navlink"
		href="javascript:void(0);">Report Generator</a> <span
		class="selected_link">&nbsp;</span>
		<div class="dropdown dropdown_2columns">
			<div class="col_1 firstcolumn">
				<ul class="link_list">
					<li><a href="../reports/getCustomDaoResults.action">
							Generate Selected Reports </a></li>
					<s:if
						test="#session['username'] != null && #session['username'] eq 'madmin'">
						<li><a href="../reports/managerCustomDaoResults.action">
								Add New Reports </a></li>
					</s:if>
					<s:if
						test="#session['username'] != null && #session['username'] eq 'madmin'">
						<li><a
							href="../reports/executeManagerUtilDataMonthNow.action">
								Append Manager Utils Next Month</a></li>
					</s:if>

				</ul>
			</div>
		</div></li>
</s:if>
<li><s:if
		test=" #session['role'] eq  'ADMIN' || #session['role'] eq 'LINE MANAGER' ">
		<a id="supportMenu" class="drop navlink"
			href="../others/goGetfeedback.action"> <s:text name="Feedback" />
		</a>
		<%-- 	<span class="selected_link">&nbsp;</span>
		<div class="dropdown dropdown_2columns">
			<div class="col_1 firstcolumn">
				<ul class="link_list">
					<li><a href="../others/feedback.action"> <s:text
								name="pts.menu.resource.feedback" />
					</a></li>
					
					<li><a href="../others/goGetfeedback.action"> <s:text
								name="Manage Feedback" />
					</a></li>
				</ul>
			</div>
		</div> --%>
	</s:if></li>
</ul>
</nav>
</div>
</div>
