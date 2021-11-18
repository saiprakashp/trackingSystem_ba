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
	$.subscribe('/ricoLocationSelected', function(event, data) {
		setTimeout("getUserContributionReport()", 100);
	});
	function showAdvancedSearchOptions() {
		document.getElementById("advancedSearchTRId").style.display = "";
		document.getElementById("showfileraId").style.display = "none";
		document.getElementById("removefileraId").style.display = "";
	}
	function removeAdvancedSearchOptions() {
		document.getElementById("advancedSearchTRId").style.display = "none";

		document.getElementById("removefileraId").style.display = "none";

		if (document.getElementById('searchSupervisor_widget')) {
			document.getElementById('searchSupervisor_widget').value = '';
			if (document.getElementById('searchSupervisor')) {
				document.getElementById('searchSupervisor').value = "";
			}
		}
		if (document.getElementById('searchStream_widget')) {
			document.getElementById('searchStream_widget').value = '';
			if (document.getElementById('searchStream')) {
				document.getElementById('searchStream').value = "";
			}
		}
		if (document.getElementById('pillarId_widget')) {
			document.getElementById('pillarId_widget').value = '';
			if (document.getElementById('pillarId')) {
				document.getElementById('pillarId').value = "";
			}
		}
		if (document.getElementById('searchLocation_widget')) {
			document.getElementById('searchLocation_widget').value = '';
			if (document.getElementById('searchLocation')) {
				document.getElementById('searchLocation').value = "";
			}
		}
		document.getElementById("showfileraId").style.display = "";
		if (document.getElementById("advancedSearch").value == 'true') {
			document.getElementById("advancedSearch").value = "false";
			setTimeout("getUserContributionReport()", 100);
		}
	}

	function goContributionDetailReport() {
		document.forms[0].method = "post";
		document.forms[0].action = '../reports/userContributionDetailsReport.action';
		document.forms[0].submit();
	}

	function submitContributionReport() {
		document.getElementById("advancedSearch").value = "true";
		submitForm('../reports/userContributionReport.action');
	}

	function swapFlagData(type) {
		if (type === 'app') {
			$("#projectBasedCheck").prop("checked", false);
			$(".projectTable").hide();
			$(".applicationTable").show();

		} else {
			$("#appBasedCheck").prop("checked", false);
			$(".applicationTable").hide();
			$(".projectTable").show();
		}
	}
</script>
</head>
<body>
	<s:form id="userContributionReportformId" method="POST" theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="advancedSearch" id="advancedSearch" />
		<div id="breadcrumbDivParent"
			style="width: 100%; background: url('../images/header_bg_shadow.png') repeat-x; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.reports.user.contribution.report" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.reports.user.contribution.report" />
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
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.label.region" />:</td>
							<td align="left" width="200"><sj:autocompleter
									list="#{'ALL':'ALL','MANA':'MANA','EGIL':'EGIL'}"
									name="ricoLocation" theme="simple" id="ricoLocationId"
									onSelectTopics="/ricoLocationSelected" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter> <sj:a
									id="getUserContributionReportHrefId"
									href="../reports/ajaxUserContributionReport.action"
									targets="userContributionReportDivId"
									formIds="userContributionReportformId"
									indicator="reportLoadingIndicator" /></td>
							<td align="left">&nbsp;&nbsp;<img
								id="reportLoadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>
						</tr>
					</table>
				</div>
				<sj:div id="userContributionReportDivId">
					<s:include value="/jsp/reports/ajaxUserContributionReport.jsp"></s:include>
				</sj:div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>