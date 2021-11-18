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
<script type="text/javascript">
	datePick = function(element) {
		$(element).datepicker();
	};
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
	function submitNCU() {
		document.getElementById('projecttemp').value = $('#pillarId').val();
		document.getElementById('releasetemp').value = $('#releaseId').val();
		document.getElementById('projectIdtemp').value = $('#projectId').val();

		submitForm('../utilization/networkCodeUtilization.action')
	}
	function setDefaults() {
		document.getElementById('download').value = 'false';
	}
</script>
</head>
<body onload="setDefaults()">
	<s:form id="manageUtilizationFormId" method="POST" theme="simple">
		<s:hidden id="projecttemp" name="projectNew"></s:hidden>
		<s:hidden id="releasetemp" name="releaseNew"></s:hidden>
		<s:hidden id="projectIdtemp" name="projectId"></s:hidden>
		<s:hidden id="download" name="download"></s:hidden>

		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.project.wise.utilization" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px></DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.project.wise.utilization" />
					</s:div>
				</div>
				<div id="spacer10px"></div>
				<div id="spacer10px"></div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text
									name="pts.user.supervisor" />:</td>
							<td width="200" nowrap="nowrap"><sj:autocompleter
									list="supervisorMapForManage" name="searchSupervisor"
									listKey="key" listValue="value" theme="simple"
									id="searchSupervisor" selectBox="true" selectBoxIcon="true"
									onSelectTopics="/supervisorSelect">
								</sj:autocompleter> <sj:a id="getPillarsHrefId"
									href="../utilization/getPillarsMap.action"
									targets="pillarsDivId" formIds="manageUtilizationFormId"
									indicator="pillarLoadingIndicator" /><img
								id="pillarLoadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.networkcode.pillar.name" />:</td>
							<td width="200" nowrap="nowrap"><sj:div id="pillarsDivId">
									<sj:autocompleter list="pillarsMapObj" name="pillar"
										listKey="key" listValue="value" theme="simple" id="pillarId"
										onSelectTopics="/pillarSelect" selectBox="true"
										selectBoxIcon="true"></sj:autocompleter>
								</sj:div> <sj:a id="getProjectsHrefId"
									href="../utilization/getProjectsMap.action"
									targets="projectsDivId" formIds="manageUtilizationFormId"
									indicator="projectLoadingIndicator" /> <img
								id="projectLoadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.networkcode.project" />:</td>
							<td width="200"><sj:div id="projectsDivId">
									<sj:autocompleter list="projectsMapObj" name="project"
										listKey="key" listValue="value" theme="simple" id="projectId"
										selectBox="true" selectBoxIcon="true"
										onchange="changeProjectData()" onSelectTopics="/projectSelect"></sj:autocompleter>
								</sj:div> <sj:a id="getReleaseHrefId"
									href="../utilization/getReleaseMap.action"
									targets="releaseDivId" formIds="manageUtilizationFormId"
									indicator="releaseLoadingIndicator" /> <img
								id="releaseLoadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>
						</tr>
						<tr>
							<td style="color: #2779aa;"><s:text
									name="pts.week.ending.from.date" />:</td>
							<td align="left"><sj:datepicker displayFormat="mm/dd/yy"
									id="fromDate" size="10" name="selectedDate" value="%{fromDate}"
									changeYear="true" buttonImage="../images/DatePicker.png"
									buttonImageOnly="true" /></td>
							<td style="color: #2779aa;"><s:text
									name="pts.week.ending.to.date" />:</td>
							<td align="left"><sj:datepicker displayFormat="mm/dd/yy"
									id="toDate" size="10" name="selectedToDate" value="%{toDate}"
									changeYear="true" buttonImage="../images/DatePicker.png"
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
							<td width="200"><sj:div id="releaseDivId">
									<sj:autocompleter list="releasesMapObj" name="release"
										value="ALL" listKey="key" listValue="value" theme="simple"
										id="releaseId" selectBox="true" selectBoxIcon="true"></sj:autocompleter>
								</sj:div></td>
						</tr>
						<tr>
							<td style="color: #2779aa;" align="right"><s:label
									value="By Type" />:</td>
							<td align="left"><sj:autocompleter
									list="#{'DEFAULT':'Please Select','MONTH':'MONTH','WEEKEND_DATE':'WEEKEND DATE'}"
									name="type" theme="simple" id="typeId" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter></td>

							<td style="color: #2779aa;" align="right"><s:label
									value="By User" />:</td>
							<td align="left"><sj:autocompleter
									list="#{'DEFAULT':'Please Select','USER':'USER'}"
									name="reportType" theme="simple" id="reportTypeId"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>

							<s:hidden name="selectedYear" id="selectedYear" theme="simple"></s:hidden>
							<td style="padding-left: 20px;"><a onclick="submitNCU()"><img
									src="../images/go.png" /></a></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px"></div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="networkCodeUtilizationGrid_new.jsp" /></td>
					</tr>
				</table>
			</div>
			<s:div id="spacer10px"></s:div>
			<s:div id="spacer10px"></s:div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>