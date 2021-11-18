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
	$
			.subscribe(
					'subgridExpanded',
					function(event, data) {
						var rowId = event.originalEvent.row_id;
						var ret = jQuery("#gridmultitable").jqGrid(
								'getRowData', rowId);
						document.getElementById('selectedWeekEndingDate').value = ret.weekEndingDate;
						document.getElementById('supervisorId').value = ret.supervisorId;
						document.getElementById('managerId').value = ret.supervisorId;
					});
	function submitUserUtilizationForm() {

		var fromDate = new Date();
		var toDate = new Date();

		if (document.getElementById('fromDate').value != '') {
			var tempFromDate = document.getElementById('fromDate').value;
			var from = tempFromDate.split("/");
			fromDate.setFullYear(from[2], parseInt(parseInt(from[0]) - 1),
					from[1]);
		}

		if (document.getElementById('fromDate').value != '') {
			var tempToDate = document.getElementById('toDate').value;
			var to = tempToDate.split("/");
			toDate.setFullYear(to[2], parseInt(parseInt(to[0]) - 1), to[1]);
		}
		if (document.getElementById('employeeListId_widget')
				&& document.getElementById('employeeListId_widget').value == '') {
			if (document.getElementById('employeeListId')) {
				document.getElementById('employeeListId').value = "";
			}
		}
		if (fromDate > toDate) {
			alert("From date should be after To date");
			return false;
		} else {
			document.forms[0].method = "post";
			document.forms[0].action = '../utilization/manageUserUtilization.action';
			document.forms[0].submit();
		}
	}

	$.subscribe('complete', function(event, data) {
		setTimeout(alert(document.getElementById('messageId').value), 100);

	});
</script>
<style type="text/css">
#contentarea {
	text-align: 0;
	clear: both;
	width: 1200px !important;
	background: white;
	margin: 0 auto;
	text-align: left;
	border-radius: 5px;
	margin: 0 auto;
}

.css-button {
	border: 1px solid #aed0ea;
	background: #d7ebf9 url(images/ui-bg_glass_80_d7ebf9_1x400.png) 50% 50%
		repeat-x;
	text-decoration: none;
	padding: 3px;
	font-size: 13px;
	font-weight: bold;
	color: darkslategrey;
}
</style>
</head>
<body>
	<form id="manageUtilizationFormId" method="POST">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1200px; background: white; height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.reportee.utilization.report" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.reportee.utilization.report" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<s:if
						test="(#session['userStream'] != null && (#session['userStream'] == 5 || #session['userStream'] == 6)) || 
						(#session['role'] != null && #session['role'] neq 'USER')">
						<table>
							<tr>
								<td style="color: #2779aa;" nowrap="nowrap"><s:text
										name="pts.resource.name" />:</td>
								<td width="200"><sj:autocompleter list="employeeList"
										name="selectedEmployee" theme="simple" id="employeeListId"
										selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
								<%-- <td style="color: #2779aa;" nowrap="nowrap">Reportee Type:</td>
								<td style="color: #2779aa;" width="200"><sj:autocompleter
										list="#{'DR':'Direct Reportees','AR':'All Reportees'}"
										name="reporteeType" theme="simple" id="reporteeType"
										selectBox="true" selectBoxIcon="true"></sj:autocompleter></td> --%>
								<td style="color: #2779aa;" nowrap="nowrap"><s:text
										name="My Resources Only" />:</td>
								<td align="left"><s:checkbox theme="simple"
										name="showAllRes"></s:checkbox></td>

								<td style="color: #2779aa;" nowrap="nowrap">Delinquent
									Entries:</td>
								<td style="color: #2779aa;" width="200"><s:checkbox
										name="delinquentEntries" id="delinquentEntries" theme="simple" /></td>
							</tr>
							<tr>
								<td style="color: #2779aa;" nowrap="nowrap"><s:text
										name="pts.from.date" />:</td>
								<td width="200"><sj:datepicker displayFormat="mm/dd/yy"
										id="fromDate" name="selectedDate" value="%{fromDate}"
										changeYear="true" cssStyle="width:150px"
										buttonImage="../images/DatePicker.png" buttonImageOnly="true" /></td>

								<td style="color: #2779aa;" nowrap="nowrap"><s:text
										name="pts.to.date" />:</td>
								<td width="200"><sj:datepicker displayFormat="mm/dd/yy"
										id="toDate" name="selectedToDate" value="%{toDate}"
										changeYear="true" cssStyle="width:150px"
										buttonImage="../images/DatePicker.png" buttonImageOnly="true" /></td>
								<td style="color: #2779aa;" nowrap="nowrap"><s:text
										name="pts.networkcode.stable.teams" />:</td>
								<td align="left"><sj:autocompleter list="stableTeamsmap"
										name="stableTeamid" listKey="key" listValue="value"
										theme="simple" id="stableTeamid" selectBox="true"
										selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>



								<s:hidden name="selectedWeekEndingDate"
									id="selectedWeekEndingDate" />
								<s:hidden name="supervisorId" id="supervisorId" />
								<s:hidden name="managerId" id="managerId" />
							</tr>
							<tr>


								<td style="color: #2779aa;" nowrap="nowrap"><s:text
										name="pts.netwok.code" />:</td>
								<td align="left"><sj:autocompleter list="networkCodesMap"
										name="networkCodeId" listKey="key" listValue="value"
										theme="simple" id="networkCodeId" selectBox="true"
										selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>



								<td style="color: #2779aa;" nowrap="nowrap"><s:text
										name="Project Managers" />:</td>
								<td align="left"><sj:autocompleter list="projectManagers"
										name="projectManagerId" listKey="key" listValue="value"
										theme="simple" id="projectManagerId" selectBox="true"
										selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>





								<td style="padding-left: 20px;" colspan="2"><s:a
										href="javascript:void(0)"
										onclick="submitUserUtilizationForm()">
										<img src="../images/go.png">
									</s:a></td>
							</tr>
						</table>
					</s:if>
					<s:else>
						<table>
							<tr>
								<td style="color: #2779aa;" nowrap="nowrap"><s:text
										name="pts.resource.name" />:</td>
								<td width="200"><sj:autocompleter list="employeeList"
										name="selectedEmployee" theme="simple" id="employeeListId"
										selectBox="true" selectBoxIcon="true"></sj:autocompleter>
								<td style="color: #2779aa;" nowrap="nowrap"><s:text
										name="pts.from.date" />:</td>
								<td width="200"><sj:datepicker displayFormat="mm/dd/yy"
										id="fromDate" name="selectedDate" value="%{fromDate}"
										changeYear="true" cssStyle="width:150px"
										buttonImage="../images/DatePicker.png" buttonImageOnly="true" /></td>

								<td style="color: #2779aa;" nowrap="nowrap"><s:text
										name="pts.to.date" />:</td>
								<td width="200"><sj:datepicker displayFormat="mm/dd/yy"
										id="toDate" name="selectedToDate" value="%{toDate}"
										changeYear="true" cssStyle="width:150px"
										buttonImage="../images/DatePicker.png" buttonImageOnly="true" /></td>

								<td style="padding-left: 20px;" colspan="2"><s:a
										href="javascript:void(0)"
										onclick="submitUserUtilizationForm()">
										<img src="../images/go.png">
									</s:a></td>
								<s:hidden name="selectedWeekEndingDate"
									id="selectedWeekEndingDate" />
								<s:hidden name="supervisorId" id="supervisorId" />
								<s:hidden name="managerId" id="managerId" />
							</tr>
						</table>
					</s:else>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="utilizationGrid.jsp" /></td>
					</tr>
				</table>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div style="padding-right: 3%; text-align: right;">

				<s:submit theme="simple" value="Download All Pages"
					class="css-button"
					onclick="submitForm('../utilization/exportAllResourceEffortDetails.action')" />
				<%-- 	<s:if test="#session['role'] eq 'LINE MANAGER'">
					<sj:a href="../utilization/sendMail.action"
						formIds="manageUtilizationFormId" targets="mailDivId"
						onCompleteTopics="complete" indicator="indicator"
						onclick="clearErrorDiv()" cssStyle="margin-top:3px;">
						<img src="../images/send_mail.png">
					</sj:a>
					<img id="indicator" src="../images/indicatorImg.gif"
						alt="Loading..." style="display: none" />
				</s:if> --%>

				<s:submit theme="simple" value="Download This Page"
					class="css-button"
					onclick="submitForm('../utilization/exportResourceEffortDetails.action')" />
			</div>



			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
			<sj:div id="mailDivId">
			</sj:div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</form>
</body>
</html>