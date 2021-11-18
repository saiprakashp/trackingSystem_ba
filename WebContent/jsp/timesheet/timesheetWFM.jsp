<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
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
<script src="<%=request.getContextPath()%>/js/timer.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function loadTotalCap() {
	}

	$.subscribe('datechange', function(event, data) {
		clearErrorDiv();
		document.getElementById("loadingIndicator").style.display = "";
		submitForm('../timesheet/logTimesheetWFM.action')
	});
</script>
<style type="text/css">
#capTab {
	width: 99%;
	padding-left: 1%;
}

.dateTimePicker>button {
	display: none !important;
}

.capacityH {
	color: #2779aa;
	font-style: normal;
	font-weight: 600;
	font-family: Arial, San-Serif;
	font-size: 12px;
	background-color: #D2D2D2;
	text-align: center;
	height: 20px;
	padding-left: 5px;
}

.capacityDiv {
	color: #2779aa;
	font-style: normal;
	font-weight: 600;
	font-family: Arial, San-Serif;
	font-size: 14px;
	height: 20px;
	padding-left: 5px;
}

.capVal {
	font-size: 11px;
	color: black;
	display: table-cell;
	vertical-align: inherit;
}

.contentarea1 {
	text-align: 0;
	clear: both;
	width: 1118px;
	background: white;
	margin: 0 auto;
	text-align: left;
	border-radius: 5px;
	margin: 0 auto;
}

.ui-jqgrid .ui-jqgrid-btable {
	table-layout: fixed;
	margin: 0;
	outline-style: none;
}

.ui-jqgrid .ui-jqgrid-bdiv {
	position: relative;
	margin: 0;
	padding: 0;
	overflow: auto;
	text-align: left;
}

.ui-jqgrid .ui-jqgrid-hdiv {
	position: relative;
	margin: 0;
	padding: 0;
	overflow-x: hidden;
	border-left: 0 none !important;
	border-top: 0 none !important;
	border-right: 0 none !important;
}

.ui-state-default, .ui-widget-content .ui-state-default,
	.ui-widget-header .ui-state-default {
	border: 1px solid #aed0ea;
	background: #d7ebf9 url(images/ui-bg_glass_80_d7ebf9_1x400.png) 50% 50%
		repeat-x;
	font-weight: bold;
	color: #2779aa;
}

.ui-jqgrid {
	position: relative;
}

.ui-corner-all, .ui-corner-bottom, .ui-corner-right, .ui-corner-br {
	border-bottom-right-radius: 6px;
}

.ui-corner-all, .ui-corner-bottom, .ui-corner-left, .ui-corner-bl {
	border-bottom-left-radius: 6px;
}

.ui-corner-all, .ui-corner-top, .ui-corner-right, .ui-corner-tr {
	border-top-right-radius: 6px;
}

.ui-corner-all, .ui-corner-top, .ui-corner-left, .ui-corner-tl {
	border-top-left-radius: 6px;
}

.ui-widget-content {
	border: 1px solid #dddddd;
	background: #f2f5f7
		url(images/ui-bg_highlight-hard_100_f2f5f7_1x100.png) 50% top repeat-x;
	color: #362b36;
	width: 100%;
	overflow: scroll;
}

.ui-widget {
	font-family: Lucida Grande, Lucida Sans, Arial, sans-serif;
	font-size: 1.1em;
}

.ui-th-ltr, .ui-jqgrid .ui-jqgrid-htable th.ui-th-ltr {
	border-left: 0 none;
}

.ui-th-column, .ui-jqgrid .ui-jqgrid-htable th.ui-th-column {
	overflow: hidden;
	white-space: nowrap;
	text-align: center;
	border-top: 0 none;
	border-bottom: 0 none;
}

.ui-jqgrid .ui-jqgrid-htable th {
	height: 22px;
	padding: 0 2px 0 2px;
}

.ui-state-default, .ui-widget-content .ui-state-default,
	.ui-widget-header .ui-state-default {
	border: 1px solid #aed0ea;
	background: #d7ebf9 url(images/ui-bg_glass_80_d7ebf9_1x400.png) 50% 50%
		repeat-x;
	font-weight: bold;
	color: #2779aa;
}

.ui-jqgrid tr.ui-row-ltr td {
	text-align: left;
	border-right-width: 1px;
	border-right-color: inherit;
	border-right-style: solid;
}

.ui-jqgrid tr.jqgrow td {
	font-weight: normal;
	overflow: hidden;
	white-space: pre;
	height: 22px;
	padding: 0 2px 0 2px;
	border-bottom-width: 1px;
	border-bottom-color: inherit;
	border-bottom-style: solid;
}

#ui-datepicker-div {
	width: 239px !important;
	top: 289px;
	left: 425px;
	z-index: 1;
	display: block;
}
</style>
</head>
<body onload="loadTotalCap">

	<s:form id="resourceUtilizationformId" method="POST" theme="simple"
		action="../utilization/saveUserActivityWFM.action">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" class="contentarea1"
			style="background: white; height: 12px; width: 1118px;">
			<div
				style="margin: 0 auto; width: 1118px; background: white; height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.resource.timesheet.wfm" />
				</div>
			</div>
		</div>
		<div class="contentarea1">
			<DIV style="margin: 0 auto; width: 1118px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.resource.timesheet.wfm" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text name="pts.resource.name" />:</td>
							<td align="left" width="200">
								<!--<sj:autocompleter
									list="employeeList" name="selectedEmployee"  theme="simple" id="employeeListId"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter>--> <s:property
									value="#session['fullName']" /> <s:hidden
									name="selectedEmployee" id="selectedEmployee" />
							</td>

							<td style="color: #2779aa;"><s:text
									name="pts.week.ending.date" />:</td>
							<td align="left"><sj:datepicker displayFormat="mm/dd/yy"
									name="selectedDate" value="%{fromDate}" changeYear="true"
									buttonImage="../images/DatePicker.png" buttonImageOnly="true"
									onChangeTopics="datechange" /></td>
							<td style="padding-left: 20px;"><img id="loadingIndicator"
								src="../images/indicatorImg.gif" style="display: none" /></td>

						</tr>
					</table>
				</div>
				<s:hidden id="removedIds" name="removedIds" />
				<s:hidden id="removedTemplateIds" name="removedTemplateIds" />
				<s:hidden id="tempActivityCode" name="tempActivityCode" />
				<s:hidden id="tempType" name="tempType" />
				<s:hidden id="selectedHiddenDate" name="selectedHiddenDate" />
				<sj:div id="activityLogDivId">
					<jsp:include page="timesheetSummaryWfm.jsp" />
				</sj:div>
				<div id="spacer10px">&nbsp;</div>
				<div style="padding-right: 5px; text-align: right;">
					<s:submit src="../images/Submit.png" type="image"
						id="submitTimeSheet"
						onclick="clearErrorDiv();submitForm('../timesheet/saveUserActivityWFM.action')" />
				</div>
				<div id="spacer10px">&nbsp;</div>
			</div>
			<div id="spacer10px">&nbsp;</div>

			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>