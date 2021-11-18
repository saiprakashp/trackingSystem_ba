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
</head>
<body>
	<s:form theme="simple" method="POST" id="techScoreReportFormId">
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
					<s:text name="pts.menu.tech.score.report" />
				</div>
			</div>
		</div>
		<s:hidden name="id" id="ncId" />
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.tech.score.report" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td class="ptslabel" align="right"><s:text
									name="pts.label.region" />:</td>
							<td align="left" width="200"><sj:autocompleter
									list="#{'ALL':'ALL','MANA':'MANA','EGIL':'EGIL'}"
									name="ricoLocation" theme="simple" id="ricoLocationId"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td class="ptslabel" align="right"><s:text
									name="pts.user.projects" />:</td>
							<td width="200"><sj:autocompleter
									list="#session['projectsMap']" name="projectId" listKey="key"
									listValue="value" theme="simple" id="projectId"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>

							<td class="ptslabel" align="right"><s:text name="pts.year" />:</td>
							<td nowrap="nowrap"><s:select list="reportYearList"
									name="selectedYear" id="selectedYear" theme="simple"
									onchange="getUserCompScore()"></s:select> &nbsp;&nbsp;<s:select
									list="#{'1H':'1st Half','2H':'2nd Half'}" name="halfYear"
									id="halfYear" theme="simple"></s:select></td>
						</tr>
						<tr>
							<td class="ptslabel" align="right"><s:text
									name="pts.user.skills" />:</td>
							<td width="200"><sj:autocompleter
									list="#session['technologiesMap']" name="skillId" listKey="key"
									listValue="value" theme="simple" id="skillId" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter></td>
							<td>&nbsp</td>
							<td align="left" width="200"><sj:autocompleter
									list="#{'ALL':'ALL','Primary':'Primary','Secondary':'Secondary'}"
									name="primaryFlag" theme="simple" id="primaryFlag"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>

							<td style="padding-left: 20px;" colspan="2" align="right"><s:submit
									theme="simple" src="../images/go.png" type="image"
									onclick="submitForm('../reports/techScoreReport.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="techScoreReportGrid.jsp" /></td>
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