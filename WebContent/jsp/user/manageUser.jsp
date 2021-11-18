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
<style type="text/css">
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
	<s:form theme="simple" method="POST" id="manageUserFormId">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:url id="selectmanagersurl"
			action="../user/loadSupervisorsMap.action" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.manage.user" />
				</div>
			</div>
		</div>
		<s:hidden name="id" id="userId" />
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.manage.user" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.name" />:</td>
							<td align="left"><s:textfield name="searchUserName"
									id="searchUserName" theme="simple" cssStyle="width:175px;" /></td>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.supervisor" />:</td>
							<td align="left"><sj:autocompleter
									list="supervisorMapForManage" name="searchSupervisor"
									listKey="key" listValue="value" theme="simple"
									id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.linemanager" />:</td>
							<td align="left"><sj:autocompleter list="lineManagerMap"
									name="lineManagerId" listKey="key" listValue="value"
									theme="simple" id="lineManagerId" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter></td>
						</tr>
						<tr>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.type" />:</td>
							<td align="left"><sj:autocompleter list="userTypesMap"
									name="searchUserType" listKey="value" listValue="value"
									theme="simple" id="searchUserType" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.stream" />:</td>
							<td align="left"><sj:autocompleter list="streamsMap"
									name="searchStream" listKey="value" listValue="value"
									theme="simple" id="searchStream" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.status" />:</td>
							<td align="left"><sj:autocompleter list="statusMap"
									name="searchStatus" listKey="key" listValue="value"
									theme="simple" id="searchStatus" selectBox="true"
									selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>
						</tr>
						<tr>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.technology" />:</td>
							<td align="left"><sj:autocompleter list="technologiesMap"
									name="searchTechnology" listKey="value" listValue="value"
									theme="simple" id="searchTechnology" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter></td>

							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.location" />:</td>
							<td align="left"><sj:autocompleter list="locationMap"
									name="searchLocation" listKey="key" listValue="value"
									theme="simple" id="searchLocation" selectBox="true"
									selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.doj" /> (yyyy-mm-dd):</td>
							<td align="left"><s:textfield name="searchDOj"
									id="searchDOj" theme="simple" cssStyle="width:175px;" /></td>
						</tr>
						<tr>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.dateofbilling" /> (yyyy-mm-dd):</td>
							<td align="left"><s:textfield name="searchDOBilling"
									id="searchDOBilling" theme="simple" cssStyle="width:175px;" /></td>

							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.applications" />:</td>
							<td align="left"><sj:autocompleter
									list="%{#session.customerContracts}" name="appId" listKey="key"
									listValue="value" theme="simple" id="appId" selectBox="true"
									selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>
							<td align="right" style="color: #2779aa;"><s:text
									name="pts.networkcode.stable.teams" />:</td>
							<td align="left"><sj:autocompleter list="stableTeamsmap"
									name="stableTeamid" listKey="key" listValue="value"
									theme="simple" id="stableTeamid" selectBox="true"
									selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>
							<td align="right">&nbsp;</td>
						</tr>
						<tr>
							<td style="color: #2779aa;" align="right">All Stable
								Teams:&nbsp;<s:checkbox name="showAllStableTeams"
									id="showAllStableTeams" theme="simple" />
							</td>


							<td style="color: #2779aa;" align="right">All
								Resources:&nbsp;<s:checkbox name="allReporteesFlag"
									id="allReporteesFlag" theme="simple" />
							</td>
							<td style="padding-left: 20px;"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../user/manageUser.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td align="center"><s:if
								test="stableTeamid > 0 || showAllStableTeams eq true"><jsp:include
									page="userGrid.jsp" /></s:if> <s:else><jsp:include
									page="userGridNonStable.jsp" /></s:else></td>
					</tr>


				</table>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div style="padding-right: 3%; text-align: right;">

				<s:submit theme="simple" value="Download All Pages"
					class="css-button"
					onclick="submitForm('../user/exportAllResourceStableDetails.action')" />


				<s:submit theme="simple" value="Download This Page"
					class="css-button"
					onclick="submitForm('../user/exportResourceStableDetails.action')" />
			</div>

			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>