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
<script src="<%=request.getContextPath()%>/js/jqueryui.js"></script>
<script type="text/javascript">
	$(function() {
		$("#accordion").accordion();

	});
	function insertReportValue(type) {
		/* if (type = 'userJobStages') {
			document.getElementById('selectedReport').value = 'userJobStages';
		} */
	}
	function updateSelector(type) {
		if (type == 'pillar') {
			$.post("../reports/goProjectsMapNew.action", {
				pillar : document.getElementById("pillar").value
			}, function(data, status) {
				document.getElementById("projectId").options.length = 0;
				var daySelect = document.getElementById("projectId");
				for ( var key in data.projectsMapObj) {
					var myOption = document.createElement("option");
					myOption.id = key;
					myOption.text = data.projectsMapObj[key];
					myOption.value = key;
					daySelect.add(myOption);
				}
				;

			}).fail(function() {
				alert("We are unable to get Project details");
			});
		} else if (type == 'release') {
			$.post("../utilization/goReleaseMapNew.action", {
				pillar : document.getElementById("pillar").value,
				project : document.getElementById("projectId").value,
			}, function(data, status) {
				document.getElementById("releaseId").options.length = 0;
				var daySelect = document.getElementById("releaseId");
				for ( var key in data.releasesMapObj) {
					var myOption = document.createElement("option");
					myOption.id = key;
					myOption.text = data.releasesMapObj[key];
					myOption.value = key;
					daySelect.add(myOption);
				}
				;

			});
		}

	}
	$.subscribe('/supervisorSelect', function(event, data) {
		setTimeout("getPillarsMap()", 10);
		setTimeout("getProjectsMap()", 100);
		setTimeout("getReleaseMap()", 100);
	});
	$.subscribe('/pillarSelect', function(event, data) {
		setTimeout("getProjectsMap()", 10);
		setTimeout("getReleaseMap()", 100);
	});
	$.subscribe('/statusSelect', function(event, data) {
		setTimeout("getReleaseMap()", 100);
	});
	$.subscribe('/projectSelect', function(event, data) {
		setTimeout("getReleaseMap()", 10);
	});
</script>
<style type="text/css">
#contentarea {
	text-align: 0;
	clear: both;
	width: 85% !important;
	background: white;
	margin: 0 auto;
	text-align: left;
	border-radius: 5px;
	margin: 0 auto;
}
</style>
</head>
<body>
	<s:form id="pmReportGenerator" method="POST"
		action="../reports/goDownloadReportforLM.action" theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="searchSupervisor" id="searchSupervisor" />
		<s:hidden name="searchStatus" id="searchStatus" />
		<s:hidden name="searchLocation" id="searchLocation" />

		<div id="breadcrumbDivParent"
			style="width: 100%; background: url('../images/header_bg_shadow.png') repeat-x; height: 22px;">
			<div
				style="margin: 0 auto; background: white !important; width: 85%; height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.pm.report.download" />
					>
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV
				style="margin: 0 auto; background: white !important; width: 100% !important;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.pm.report.download" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<form>
					<div
						style="background: white !important; width: 85% !important; padding: 2% !important; border: 1px solid black;">
						<div id="accordion">
							<h3>Effort Generator</h3>
							<div>

								<ul style="list-style: none;">
									<li><label style="float: left">List of User Effort
											Till Date stages </label><input name="selectedReport" type="radio"
										id="userJobStage" value="EFFORT_TOTAL" /></li>
									<li>
										<table>
											<tbody>
												<tr>
													<td style="color: #2779aa;" align="right"><s:label
															value="By Type" />:</td>
													<td align="left"><s:select
															list="#{'DEFAULT':'All','MONTH':'MONTH','YEAR':'YEAR'}"
															name="type" theme="simple" id="typeId" selectBox="true"
															selectBoxIcon="true"></s:select></td>

													<td style="color: #2779aa;" align="right"><s:label
															value="By User" />:</td>
													<td align="left"><s:select list="yearMap"
															headerKey="DEFAULT" headerValue="All" name="yearType"
															theme="simple" id="yearTypetTypeId" selectBox="true"
															selectBoxIcon="true"></s:select></td>
												</tr>
											</tbody>
										</table>
									</li>
								</ul>
							</div>
							<h3>User Report</h3>
							<div>

								<ul style="list-style: none;">
									<li><label style="float: left">List of user job
											stages </label><input name="selectedReport" type="radio"
										id="userJobStage" value="ALL_JOB_STAGES" /></li>
								</ul>


							</div>
							<h3>Network Based Report</h3>
							<div>
								<p>
									<b>This contains > 2020 year data only</b>
								</p>

								<ul style="list-style: none;">
									<li><label style="float: left">List of user effort
											activity data</label><input name="selectedReport" type="radio"
										value="USER_UTILIZATION" id="USER_UTILIZATION" /></li>
									<li><label style="float: left">List of MANA
											activity data</label><input name="selectedReport" type="radio"
										value="MANA_ACTIVITY_EFFORT" id="MANA_ACTIVITY_LIST" /></li>
									<li><label style="float: left">List of EGIL
											activity data </label><input name="selectedReport" type="radio"
										value="EGIL_AVTIVITY_EFFORT" id="EGIL_ACTIVITY_LIST" /></li>

								</ul>
								<hr />
								<p>
									<b>This contains > 2020 year data only</b>
								</p>
								<table>
									<tr>
										<td style="color: #2779aa;" align="right"><s:text
												name="pts.networkcode.pillar.name" />:</td>
										<td width="200" nowrap="nowrap"><s:select
												list="pillarsMapObj" name="pillar" id="pillar" listKey="key"
												onChange="updateSelector('pillar')" listValue="value"
												theme="simple"></s:select>
										<td style="color: #2779aa;" align="right"><s:text
												name="pts.networkcode.pillar.name" />:</td>
										<td width="200" nowrap="nowrap"><select name="project"
											onchange="updateSelector('release')" id="projectId"></select></td>
									</tr>
									<tr>
										<td style="color: #2779aa;"><s:text
												name="pts.week.ending.from.date" />:</td>
										<td align="left"><sj:datepicker displayFormat="mm/dd/yy"
												id="fromDate" size="10" name="selectedDate"
												value="%{fromDate}" changeYear="true" yearRange="2020:2199"
												buttonImage="../images/DatePicker.png"
												buttonImageOnly="true" /></td>
										<td style="color: #2779aa;"><s:text
												name="pts.week.ending.to.date" />:</td>
										<td align="left"><sj:datepicker displayFormat="mm/dd/yy"
												id="toDate" size="10" name="selectedToDate"
												value="%{toDate}" changeYear="true" yearRange="2020:2199"
												buttonImage="../images/DatePicker.png"
												buttonImageOnly="true" /></td>

										<!-- <td style="color: #2779aa;"><s:text name="pts.year" />:</td>
							<td align="left"><s:select list="reportYearList"
									name="selectedYear" id="selectedYear" headerKey="0" headerValue="All"></s:select></td> -->
										<%-- <td style="color: #2779aa;" align="right"><s:label
									value="Status" />:</td>
							<td width="200"><sj:div id="networkStatusDivId">
									<sj:autocompleter list="networkStatusMap" name="status"
										listKey="key" listValue="value" theme="simple"
										id="networkStatusMapeId" selectBox="true" selectBoxIcon="true"></sj:autocompleter>
								</sj:div></td> --%>
										<td style="color: #2779aa;" align="right"><s:text
												name="pts.netwok.code" />:</td>
										<td width="200"><select name="release" id="releaseId"></select>

										</td>
									</tr>
									<tr>
										<td style="color: #2779aa;" align="right"><s:label
												value="By Type" />:</td>
										<td align="left"><s:select
												list="#{'DEFAULT':'Please Select','MONTH':'MONTH','WEEKEND_DATE':'WEEKEND DATE'}"
												name="type" theme="simple" id="typeId" selectBox="true"
												selectBoxIcon="true"></s:select></td>

										<td style="color: #2779aa;" align="right"><s:label
												value="By User" />:</td>
										<td align="left"><s:select
												list="#{'DEFAULT':'Please Select','USER':'USER'}"
												name="reportType" theme="simple" id="reportTypeId"
												selectBox="true" selectBoxIcon="true"></s:select></td>
									</tr>
								</table>
								<ul style="list-style: none;">

									<li><label style="float: left">User Effort</label><input
										name="selectedReport" type="radio" value="USER_NETWORK_EFFORT"
										id="USER_NETWORK_EFFORT" /></li>

								</ul>
								<hr />
								<ul style="list-style: none;">
									<li><label style="float: left">List of activity
											data by Month</label><input name="selectedReport" type="radio"
										value="ACTIVITY_EFFORT_BY_MONTH" id="ACTIVITY_EFFORT_BY_MONTH" /></li>
								</ul>
								<hr />
							</div>

						</div>


					</div>
					<button
						style="margin-top: 2px; float: right; border: 1px solid #aed0ea; background: #d7ebf9 url(images/ui-bg_glass_80_d7ebf9_1x400.png) 50% 50% repeat-x; font-weight: bold; color: #2779aa;">Submit</button>
				</form>
			</DIV>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>