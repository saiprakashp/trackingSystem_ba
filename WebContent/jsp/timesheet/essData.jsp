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
	
</script>
</head>
<body>
	<form id="manageRICOUtilizationReportFormId" method="POST">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="userFlag" id="userFlag"></s:hidden>
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.user.ess.report" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.user.ess.report" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<s:form action="../timesheet/showEssData.action" method="post"
						id="essFeedModalFormId">
						<table>
							<thead>
								<tr>
									<th>Month</th>
									<th colspan="2"><s:select list="monthlist" name="selectedMonth"></s:select>
									</th>
								</tr>
							</thead>

						</table>
					</s:form>
				</div>
				<div id="spacer10px">&nbsp;</div>

			</DIV>
			<div id="spacer10px">&nbsp;</div>
			<div style="padding-right: 8px; text-align: right;"></div>

			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</form>
</body>
</html>