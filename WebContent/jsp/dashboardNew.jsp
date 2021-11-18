<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<script>
	function getClosedPI() {
		document.getElementById("showAllMyContributions1").value = document
				.getElementById("showAllMyContributions1").checked;
		submitForm('../login/showDashboard.action')
	}
</script>
<div style="border: 1px solid; border-radius: 5px; v-align: top;">
	<!-- Modal -->
	<div class="modal fade" id="holidayModal" tabindex="-1"
		aria-labelledby="holidayModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<nav>
					<div class="nav nav-tabs" id="nav-tab" role="tablist">
						<button class="nav-link active" id="nav-home-tab"
							data-bs-toggle="tab" data-bs-target="#nav-home" type="button"
							role="tab" aria-controls="nav-home" aria-selected="true">EGI
							Holiday</button>
						<button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab"
							data-bs-target="#nav-profile" type="button" role="tab"
							aria-controls="nav-profile" aria-selected="false">Mana
							Holiday</button>
					</div>
				</nav>
				<div class="tab-content" id="nav-tabContent">
					<div class="tab-pane fade show active" id="nav-home"
						role="tabpanel" aria-labelledby="nav-home-tab">
						<div class="modal-body">
							<img alt="RICO EGI Holiday List" style="width: 90%;"
								src="../images/RICO_EGI_Holidays.png" />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"
								data-bs-dismiss="modal">Close</button>
						</div>
					</div>
					<div class="tab-pane fade" id="nav-profile" role="tabpanel"
						aria-labelledby="nav-profile-tab">
						<div class="modal-body">
							<img alt="RICO EGI Holiday List" style="width: 90%;"
								src="../images/RICO_EGI_Holidays.png" />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"
								data-bs-dismiss="modal">Close</button>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<div style="width: 100%;">
		<div class="d-flex ">
			<div class="p-2 " style="width: 8%;">
				<s:if test="#session['role'] neq 'ADMIN'">
					<a href='../timesheet/logTimesheet.action' title="Record Timesheet">
						<img alt="Record Timesheet" src="../images/forbidden_php.png"
						height="75" width="75">
					</a>
				</s:if>
			</div>

			<div style="width: 68%;">
				<div class="p-2 ">
					<s:if
						test="#session['role'] != null && #session['role'] eq 'LINE MANAGER'">
						<div class="card   float-start text-center w-75  ">
							<div class="card-body border border-secondary">
								<p align="left" class="mb-2 header-1 card-title text-center"
									style="">
									Head Count (
									<s:property value="totalHeadCnt" />
									)
								</p>
								<div class="row ">
									<div class="col-sm">Onboard</div>
									<div class="col-sm">
										<s:property value="onboardCnt" />
									</div>
									<div class="col-sm">Selected</div>
									<div class="col-sm">
										<s:property value="selectedCnt" />
									</div>
									<div class="col-sm">LTA</div>
									<div class="col-sm">
										<s:property value="ltaCnt" />
									</div>
									<div class="col-sm">Interns</div>
									<div class="col-sm">
										<s:property value="Interns" />
									</div>
								</div>
								<div class="row">
									<div class="col-sm">Induction</div>
									<div class="col-sm">
										<s:property value="inductionCnt" />
									</div>
									<div class="col-sm">Open</div>
									<div class="col-sm">
										<s:property value="openCnt" />
									</div>
									<div class="col-sm">Notice Period</div>
									<div class="col-sm">
										<s:property value="noticePeriodCnt" />
									</div>
									<div class="col"></div>
									<div class="col"></div>
								</div>
							</div>
						</div>

						<div class="card-body border border-secondary">
							<div class="row ">
								<div class="col-sm mb-2 header-1 card-title text-center">Un-Approved
									Hours</div>
							</div>
							<div class="row ">
								<div class="col-sm text-center">
									<a class="fw-bold card-link "
										href="../timesheet/goApproveTimesheet.action"> <s:property
											value="unapprovedHrs" />
									</a>
								</div>
							</div>
							<div class="row ">
								<div class="col"></div>
							</div>
						</div>
					</s:if>
				</div>
			</div>
			<div class="p-2 " style="width: 8%;">
				<a href='../others/goGetfeedback.action' title="FeedBack Form">
					<img alt="FeedBack Form" class="rounded"
					src="../images/feedback.png" style="width: 95px; height: 92px;">
				</a>
			</div>

			<div class="p-2 " style="width: 8%;">
				<s:if test="#session['role'] neq 'ADMIN'">
					<a href='../matrix/RICO-Escalation-Matrix-2021.xlsx'
						title="Escalation Matrix"> <img alt="Escalation Matrix"
						class="rounded" src="../images/escalationicon.jpg" height="75"
						width="90">
					</a>
				</s:if>
			</div>

			<div class="p-2 " style="width: 8%;">
				<img alt="Holiday List" src="../images/holiday_calendar.png"
					data-bs-toggle="modal" data-bs-target="#holidayModal" height="75"
					width="75">
			</div>
		</div>
	</div>

	<div style="width: 100%;">
		<div class="d-flex dashTab mt-2">
			<s:if
				test="dashboardUserNCLOEList != null && dashboardUserNCLOEList.size() > 0">
				<div class="card border-0 w-50"
					style="overflow-x: hidden; overflow-y: scroll;">
					<div class="card-body">
						<div class="p-1  border border-secondary ">

							<p align="left" class="mb-2 header-1 text-center" style="">PI
								utilization report</p>
							<p align="left" class="mb-2 header-1 text-center" style="">
								<s:checkbox onclick="getClosedPI()" class="me-1"
									id="showAllMyContributions1" name="showAllMyContributions" />
								Include Closed PI
							</p>
							<table class="table dashboardtable "
								style="overflow-y: auto; ">
								<thead class="table-active">
									<tr>
										<th style="text-align: center;" class="border border-white">PI</th>
										<th style="text-align: center; width: 73px !important;"
											class="border border-white">Total Loe</th>
										<th colspan="2" class="border border-white"
											style="text-align: center; width: 93px !important;">Charged
											hours</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="dashboardUserNCLOEList">
										<s:if
											test="TOTALLOE eq 0 || (TOTALSUMMATION/TOTALLOE * 100)>110">
											<tr style="background: #dc3545 !important;">
												<td style="text-align: left;" class="border border-white"><s:property
														value="NETWORKCODE" /></td>
												<td style="text-align: left;" class="border border-white"><s:property
														value="TOTALLOE" /></td>
												<td colspan="2" style="text-align: left;"
													class="border border-white"><s:property
														value="TOTALSUMMATION" /></td>
											</tr>
										</s:if>
										<s:elseif
											test="((TOTALSUMMATION/TOTALLOE * 100)>=100 && (TOTALSUMMATION/TOTALLOE * 100) <=110) ">
											<tr style="background: orange;">
												<td style="text-align: left;" class="border border-white"><s:property
														value="NETWORKCODE" /></td>
												<td style="text-align: left;" class="border border-white"><s:property
														value="TOTALLOE" /></td>
												<td colspan="2" style="text-align: left;"
													class="border border-white"><s:property
														value="TOTALSUMMATION" /></td>
											</tr>
										</s:elseif>
										<s:else>
											<tr style="background: #d4edda;">
												<td style="text-align: left;" class="border border-white"><s:property
														value="NETWORKCODE" /></td>
												<td style="text-align: left;" class="border border-white"><s:property
														value="TOTALLOE" /></td>
												<td colspan="2" style="text-align: left;"
													class="border border-white"><s:property
														value="TOTALSUMMATION" /></td>
											</tr>
										</s:else>
									</s:iterator>
								</tbody>
							</table>

						</div>
					</div>
				</div>
			</s:if>
			<s:else>
				<div class="card border-0 w-50">
					<div class="card-body">
						<div class="p-1  border border-secondary ">

							<p align="left" class="mb-2 header-1 text-center" style="">PI
								utilization report</p>
						</div>
					</div>
				</div>
			</s:else>
			<div class="card border-0 w-50">
				<div class="card-body">
					<div class="p-1  border border-secondary  ">
						<p align="left" class="mb-2 header-1 text-center" style="">Utilization</p>
						<s:if
							test="#session['role'] != null && #session['role'] eq 'LINE MANAGER'">
							<div class="mb-2">
								<s:hidden name="detailSubRes" id="detailSubRes1"></s:hidden>
								<sj:autocompleter list="employeeList" cssStyle="width:150px;"
									name="employeeId" theme="simple" id="employeeId"
									onSelectTopics="/employeeSelect" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter>
								<sj:a id="getDashboardUtilizationHrefId"
									href="../login/getDashboardUtilizationHrefId.action"
									targets="dashboardUtilizationDivId" formIds="homeFormId"
									indicator="dashboardUtilizationLoadingIndicator" />
								<img id="dashboardUtilizationLoadingIndicator"
									src="../images/indicatorImg.gif" style="display: none" />
							</div>
						</s:if>
						<div id="dashboardUtilizationDivId" style="border: 1px solid"><jsp:include
								page="dashboardUtilizationDetails.jsp" /></div>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>