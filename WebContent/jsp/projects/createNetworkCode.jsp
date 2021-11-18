<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<sj:head jqueryui="true" ajaxcache="false" compressed="false"
	jquerytheme="cupertino" />
<title><s:text name="pts.project.title" /></title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/pts.css" />
<script src="<%=request.getContextPath()%>/js/pts.js"
	type="text/javascript"></script>
<script>
	$.subscribe('/pillarSelect', function(event, data) {
		setTimeout("getProjectsMap()", 10);
		setTimeout("getProgramManagersMap()", 10);
		setTimeout("getProjectManagersMap()", 100);

	});
	function settotalLOE() {
		if (document.getElementById("stableTeamId").value === ''
			|| document.getElementById("stableTeamId").value === null) {
		alert("Please Select Stable Teams")
		return false;

	}
		if (document.getElementById("projectId_widget").value === ''
			|| document.getElementById("projectId_widget").value === null) {
		alert("Please Select Application")
		return false;

	}

		
		
		document.getElementById("isForinterns1").value = !document
				.getElementById("isForinterns").checked;
		return true;
	}
</script>
</head>
<body onload="calculateOriginalLOE()">
	<s:form id="createNetworkCodeFormId" method="POST"
		onsubmit="return settotalLOE()"
		action="../projects/addNetworkCode.action" theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="customer" id="customerId" />
		<s:hidden name="isForinterns" id="isForinterns1" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:a href="../projects/manageNetworkCodes.action">
						<s:text name="pts.menu.manage.network.codes" />
					</s:a>
					>
					<s:text name="pts.networkcode.create" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.networkcode.create" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div style="width: 100%;">
					<s:div cssStyle="margin: 0px auto;" id="errorDiv" theme="simple">
						<table style="margin: 0 auto;">
							<tr>
								<td align="center"><s:fielderror theme="simple"
										cssStyle="margin-left:10px;color: RED" /> <s:actionerror
										theme="simple" cssStyle="margin-left:10px;color: RED" /> <s:actionmessage
										theme="simple" cssStyle="margin-left:10px;color: RED" /></td>
							</tr>
						</table>
					</s:div>
					<div>
						<table style="width: 100%;">
							<tr>
								<td align="center">
									<table cellspacing="10">
										<tr>
											<!-- <td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.code" />:</td>
											<td><s:textfield name="networkCodeId" id="networkCodeId"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>

											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.id" />:</td>
											<td><s:textfield name="networkCode" id="networkCode"
													theme="simple" cssStyle="width:173px;"></s:textfield></td> -->
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.release.id" />:</td>
											<td><s:textfield name="releaseId" id="releaseId"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>

											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.release.name" />:</td>
											<td><s:textfield name="releaseName" id="releaseName"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.priority" />:</td>
											<td><s:textfield name="priority" id="priority"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>


										</tr>
										<tr>
											<!--<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.customer.name" />:</td>
											<td nowrap="nowrap">
												<sj:autocompleter
													list="customerMapObj" name="customer" theme="simple"
													id="customerId" onSelectTopics="/customerSelect"
													selectBox="true" selectBoxIcon="true"></sj:autocompleter> <sj:a
													id="getPillarsHrefId"
													href="../projects/getPillarsMap.action"
													targets="pillarsDivId" formIds="createNetworkCodeFormId"
													indicator="pillarLoadingIndicator" /><img
												id="pillarLoadingIndicator" src="../images/indicatorImg.gif"
												style="display: none" /> <s:property value="customerName" />
											</td>-->
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.status" />:</td>
											<!-- <td><sj:autocompleter list="statusMapObj" name="status"
													listKey="key" listValue="value" theme="simple"
													id="statusId" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
											 -->
											<td><sj:autocompleter
													list="#{'EarlyStart':'Early Start','Execution':'Execution','Warranty':'Warranty'}"
													name="status" theme="simple" id="statusId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>

											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.pillar.name" />:</td>
											<td nowrap="nowrap"><sj:div id="pillarsDivId">
													<sj:autocompleter list="pillarsMapObj" name="pillar"
														listKey="key" listValue="value" theme="simple"
														id="pillarId" onSelectTopics="/pillarSelect"
														selectBox="true" selectBoxIcon="true"></sj:autocompleter>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img
														id="projectLoadingIndicator"
														src="../images/indicatorImg.gif" style="display: none" />
												</sj:div> <sj:a id="getProjectsHrefId"
													href="../projects/getProjectsMap.action"
													targets="projectsDivId" formIds="createNetworkCodeFormId"
													indicator="projectLoadingIndicator" /> <sj:a
													id="getProgramManagersHrefId"
													href="../projects/getProgramManagersMap.action"
													targets="programManagersDivId"
													formIds="createNetworkCodeFormId"
													indicator="projectLoadingIndicator" /> <sj:a
													id="getProjectManagersHrefId"
													href="../projects/getProjectManagersMap.action"
													targets="projectManagersDivId"
													formIds="createNetworkCodeFormId"
													indicator="projectLoadingIndicator" /></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.project" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><sj:div id="projectsDivId">
													<sj:autocompleter list="projectsMapObj" name="project"
														listKey="key" listValue="value" theme="simple"
														id="projectId" selectBox="true" selectBoxIcon="true"></sj:autocompleter>
												</sj:div></td>

										</tr>


										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.release.type" />:</td>
											<td><sj:autocompleter
													list="#{'WaterFall':getText('pts.networkcode.release.type.waterfall'),'Agile':getText('pts.networkcode.release.type.agile'),'Ad-hoc':getText('pts.networkcode.release.type.adhoc')}"
													name="releaseType" theme="simple" id="releaseType"
													selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.project.category" />:</td>
											<td><sj:autocompleter list="projectCategoryMapObj"
													name="projectCategory" listKey="key" listValue="value"
													theme="simple" id="projectCategoryId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>

											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.project.sub.category" />:</td>
											<td><sj:autocompleter list="projectSubCategoryMapObj"
													name="projectSubCategory" listKey="key" listValue="value"
													theme="simple" id="projectSubCategoryId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>



										</tr>

										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.product.owner" />:</td>
											<td><sj:autocompleter list="productOwnerMapObj"
													name="productOwner" listKey="key" listValue="value"
													theme="simple" id="productOwnerId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>

											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.program.manager" />:</td>
											<td><sj:div id="programManagersDivId">
													<sj:autocompleter list="programManagersMapObj"
														name="programManager" listKey="key" listValue="value"
														theme="simple" id="programManagerId" selectBox="true"
														selectBoxIcon="true"></sj:autocompleter>
												</sj:div></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.project.manager" />:</td>
											<td><sj:div id="projectManagersDivId">
													<sj:autocompleter list="projectManagersMapObj"
														name="projectManager" listKey="key" listValue="value"
														theme="simple" id="projectManagerId" selectBox="true"
														selectBoxIcon="true"></sj:autocompleter>
												</sj:div></td>

										</tr>

										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.start.date" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strStartDate" value="%{startDate}" changeYear="true"
													buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" /></td>

											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.end.date" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strEndDate" value="%{endDate}" changeYear="true"
													buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" /></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.scope.close.date" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strScopeCloseDate" value="%{scopeCloseDate}"
													changeYear="true" buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" /></td>
										</tr>

										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.no.of.sreqs" />:</td>
											<td><s:textfield name="sreqNo" id="sreqNo"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="Phase" />:</td>
											<td><sj:autocompleter
													list="#{'Completed':'Completed','Deployment':'Deployment','Development':'Development','Feasibility':'Feasibility', 'Hold':'Hold','System Test':'System Test'}"
													name="projectStage" theme="simple" id="projectStage"
													selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.no.of.TFSEpic" />:</td>
											<td><s:textfield name="TFSEpic" id="TFSEpic"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>


											<!--<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.change.control" />:</td>
											<td><sj:autocompleter
													list="#{'Y':getText('pts.label.yes'),'N':getText('pts.label.no')}"
													name="changeControl" theme="simple" id="changeControl"
													selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.change.control.number" />:</td>
											<td><s:textfield name="changeControlNo"
													id="changeControlNo" theme="simple" cssStyle="width:173px;"></s:textfield></td>-->
										</tr>
										<!--<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.schedule.variance" />:</td>
											<td><s:textfield name="scheduleVariance"
													id="scheduleVariance" theme="simple"
													cssStyle="width:173px;"></s:textfield></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.rollback.volume" />:</td>
											<td><s:textfield name="rollbackVolume"
													id="rollbackVolume" theme="simple" cssStyle="width:173px;"></s:textfield></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.dev.test.effectiveness" />:</td>
											<td><s:textfield name="devTestEffectiveness"
													id="devTestEffectiveness" theme="simple"
													cssStyle="width:173px;"></s:textfield></td>
										</tr>-->


										<!-- <tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.change.control.status" />:</td>
											<td><sj:autocompleter
													list="#{'Pending':getText('pts.label.pending'),'Approved':getText('pts.label.approved')}"
													name="changeControlStatus" theme="simple"
													id="changeControlStatus" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.change.control.date" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strChangeControlDate" value="%{changeControlDate}"
													changeYear="true" buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" /></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.change.control.impact" />:</td>
											<td><s:textfield name="changeControlImpact"
													id="changeControlImpact" theme="simple"
													cssStyle="width:173px;"></s:textfield></td>
										</tr>

										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.rollback" />:</td>
											<td><sj:autocompleter
													list="#{'Y':getText('pts.label.yes'),'N':getText('pts.label.no')}"
													name="rollback" theme="simple" id="rollback"
													selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.rollback.number" />:</td>
											<td><s:textfield name="rollbackNo" id="rollbackNo"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.rollback.date" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strRollbackDate" value="%{rollbackDate}"
													changeYear="true" buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" /></td>
										</tr>

										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.rollback.reason" />:</td>
											<td><s:textarea name="rollbackReason"
													id="rollbackReason" theme="simple" rows="5"></s:textarea></td>
											<td style="color: #2779aa;" align="right">&nbsp;</td>
											<td>&nbsp;</td>
											<td style="color: #2779aa;" align="right">&nbsp;</td>
											<td>&nbsp;</td>
										</tr> -->
										<tr>
											<td style="color: #2779aa;" align="right">For intern:</td>
											<td><input type="checkbox" name="isForinterns"
												id="isForinterns" /></td>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.networkcode.stable.teams" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><sj:div id="getStableTeamsHrefIdDivId">
													<sj:autocompleter list="stableTeamsmap" name="stableTeam"
														theme="simple" id="stableTeamId" selectBox="true"
														selectBoxIcon="true"></sj:autocompleter>
												</sj:div></td>
										</tr>
										<tr>
											<td colspan="6">
												<div
													style="border: 1px solid blue; width: 100%; border-radius: 1em;">
													<table style="width: 100%;" cellspacing="10">
														<tr>
															<td colspan="6"><h3>
																	<s:text name="pts.networkcode.planned.dates" />
																</h3></td>
														</tr>
														<tr>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.design.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strPlannedDesignDate"
																	value="%{plannedDesignDate}" changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>

															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.dev.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strPlannedDevDate" value="%{plannedDevDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.testing.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strPlannedTestDate" value="%{plannedTestDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>

														</tr>
														<tr>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.impl.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strPlannedImplDate" value="%{plannedImplDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>

															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.opr.handoff.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strPlannedOprHandoffDate"
																	value="%{plannedOprHandoffDate}" changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>

														</tr>
														<tr>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.warranty.completion.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strPlannedWarrantyCompletionDate"
																	value="%{plannedWarrantyCompletionDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
														</tr>
													</table>
													<table style="width: 100%;" cellspacing="10">
														<tr>
															<td colspan="6"><h3>
																	<s:text name="pts.networkcode.actual.dates" />
																</h3></td>
														</tr>
														<tr>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.design.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strActualDesignDate" value="%{actualDesignDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>

															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.dev.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strActualDevDate" value="%{actualDevDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.testing.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strActualTestDate" value="%{actualTestDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>

														</tr>
														<tr>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.impl.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strActualImplDate" value="%{actualImplDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>

															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.opr.handoff.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strActualOprHandoffDate"
																	value="%{actualOprHandoffDate}" changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>

														</tr>
													</table>
													<!-- <table style="width: 100%;" cellspacing="10">
														<tr>
															<td colspan="6"><h3>
																	<s:text name="pts.networkcode.original.dates" />
																</h3></td>
														</tr>
														<tr>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.design.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strOriginalDesignDate"
																	value="%{originalDesignDate}" changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>

															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.dev.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strOriginalDevDate" value="%{originalDevDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.testing.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strOriginalTestDate" value="%{originalTestDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>

														</tr>
														<tr>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.impl.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy" 
																	name="strOriginalImplDate" value="%{originalImplDate}"
																	changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>

															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.opr.handoff.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strOriginalOprHandoffDate"
																	value="%{originalOprHandoffDate}" changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
														</tr>
														<tr>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.warranty.completion.date" />:</td>
															<td><sj:datepicker displayFormat="mm/dd/yy"
																	name="strOriginalWarrantyCompletionDate"
																	value="%{originalWarrantyCompletionDate}" changeYear="true"
																	buttonImage="../images/DatePicker.png"
																	buttonImageOnly="true" /></td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
														</tr>
														<tr>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
														</tr>
													</table> -->
												</div>
											</td>
										</tr>


										<tr>
											<td colspan="6">
												<div
													style="border: 1px solid blue; width: 100%; border-radius: 1em;">
													 
													<table style="width: 100%;" cellspacing="10">
														<tr>
															<td colspan="6"><h3>
																	<s:text name="pts.networkcode.total.loe" />
																</h3></td>
														</tr>
														<tr>
															<td style="color: #2779aa;" align="right"><s:text
																	name="pts.networkcode.total.loe" />:</td>
															<td><s:textfield name="totalLOE" id="totalLOE" theme="simple"
																	cssStyle="width:173px;"></s:textfield></td>


														</tr>

														<tr>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
															<td style="color: #2779aa;" align="right">&nbsp;</td>
															<td>&nbsp;</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>


									</table>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td style="padding-left: 5px; text-align: left;"><s:a
								href="../projects/manageNetworkCodes.action">
								<img src="../images/cancel.png" title="Cancel" />
							</s:a></td>
						<td style="padding-right: 5px; text-align: right;"><sj:submit
								src="../images/save.png" type="image" value="Save" /></td>
					</tr>
				</table>
				<div id="spacer10px">&nbsp;</div>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>