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
	function showAdvancedSearchOptionsApp() {console.log('->  '+document.getElementById('searchStream').value);
		if (document.getElementById('searchStream').value.match(/^\s*$/g)) {
			document.getElementById('searchStream').value = "";
		}
		if (document.getElementById('searchLocation').value.match(/^\s*$/g)) {
			document.getElementById('searchLocation').value = "";
		}
		if (document.getElementById('pillarId').value.match(/^\s*$/g)) {
			document.getElementById('pillarId').value = "";
		}
		document.getElementById("advancedSearchTRIdApp").style.display = "";
		document.getElementById("showfileraIdApp").style.display = "none";
		document.getElementById("removefileraIdApp").style.display = "";
	}
	function removeAdvancedSearchOptionsApp() {
		document.getElementById("advancedSearchTRIdApp").style.display = "none";

		document.getElementById("removefileraIdApp").style.display = "none";

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
		document.getElementById("showfileraIdApp").style.display = "";
		if (document.getElementById("advancedSearch").value == 'true') {
			document.getElementById("advancedSearch").value = "false";
			setTimeout("getUserContributionReportApp()", 100);
		}
	}

	function goContributionDetailReportApp() {
		document.forms[0].method = "post";
		document.forms[0].action = '../reports/userContributionDetailsReport.action';
		document.forms[0].submit();
	}

	function submitContributionReportApp() {
		document.getElementById("advancedSearchApp").value = "true";
		submitForm('../reports/userContributionReportApp.action');
	}
</script>
</head>
<body>
	<s:form id="userContributionReportformIdApp" method="POST"
		theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="advancedSearch" id="advancedSearch" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
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
					<s:include value="/jsp/reports/ajaxUserContributionReportApp.jsp"></s:include>
				</div>
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