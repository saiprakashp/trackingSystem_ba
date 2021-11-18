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

<script>
	datePick = function(element) {
		$(element).datepicker();
	};
	function hideloader() {
		$("#loaderInd").hide();
	}
	$.subscribe('/supervisorSelect', function(event, data) {
		if (document.getElementById('releaseId_widget')) {
			if (document.getElementById('releaseId')) {
				document.getElementById('releaseId_widget').value == ''
				document.getElementById('releaseId').value = "";
			}
		}
		$("#getReleaseHrefId").click();
	});
	$.subscribe('/pillarSelect', function(event, data) {
		document.getElementById('projectId_widget').value == '';
		document.getElementById('projectId').value = "";
		$("#getProjectsHrefId").click();

	});
	$.subscribe('/projectSelect', function(event, data) {

	});

	function cancelButton() {
		$('#ProjectPhaseDialog').dialog('close');
	}
	function clearAllErrors() {
		$("#dataSubmitError").empty();
	}

	function submitForm(s) {
		document.forms[0].method = "post";
		document.forms[0].action = s;
		document.forms[0].submit();
	}
</script>

</head>
<body onload="clearAllErrors();hideloader();">
	<s:form id="supervisorNwListId" method="POST" theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="id" id="networkCodeId" theme="simple" />
		<s:hidden name="projectStage" id="projectStageId" theme="simple" />
		<s:hidden name="comments" id="comments" theme="simple" />
		<s:hidden name="description" id="description" theme="simple" />
		<s:hidden name="projectColor" id="projCol" theme="simple" />
		<div id="breadcrumbDivParent"
			style="width: 100%; background: url('../images/header_bg_shadow.png') repeat-x; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.network.utilization.report" />
					Report
				</div>
			</div>
		</div>


		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.network.utilization.report" /> Report
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
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
									targets="pillarsDivId" formIds="supervisorNwListId"
									indicator="pillarLoadingIndicator" /><img
								id="pillarLoadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>

							<td style="color: #2779aa;" align="left"><s:text
									name="pts.networkcode.pillar.name" />:</td>
							<td width="200" nowrap="nowrap"><sj:autocompleter
									list="pillarsMapObj" name="pillar" listKey="key"
									listValue="value" theme="simple" id="pillarId" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter></td>

							<td style="color: #2779aa;" align="left">YEAR:</td>
							<td align="left"><s:select list="yearMap"
									name="selectedYear" id="selectedYear"></s:select></td>
						</tr>
						<tr>
							<td style="color: #2779aa;" align="left">Report Type:</td>
							<td align="left"><sj:autocompleter
									list="#{'MONTHLY':'MONTHLY','YEARLY':'YEARLY' }" listKey="key"
									theme="simple" selectBox="true" selectBoxIcon="true"
									listValue="value" name="reportType" id="reportType">
								</sj:autocompleter></td>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.month" />:</td>
							<td align="left"><sj:autocompleter list="monthMap"
									name="monthSelected" listKey="key" listValue="value"
									theme="simple" id="selectedMonth" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter>
							<td style="padding-left: 50px;"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../reports/userNWUtilization.action')" /></td>

							<td style="padding-left: 20px;"><s:submit theme="simple"
									src="../images/download.jpg" type="image"
									style="width: 20px;height: 20px;float:right;"
									onclick="submitForm('../reports/downloadUserNWUtilization.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<span style="color: red;" id="dataSubmitError"></span>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="networkUtilizationForManagerGrid.jsp" /></td>
					</tr>
				</table>
			</div>

			<s:div id="spacer10px">&nbsp;</s:div>
			<s:div id="spacer10px">&nbsp;</s:div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>