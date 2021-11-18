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
	$.subscribe('/autocompleteSelect', function(event, data) {
		setTimeout("goAssignNetworkCodes()", 100);
	});

	$.subscribe('/pillarSelect', function(event, data) {
		setTimeout("getProjectsMap()", 10);
		setTimeout("getReleaseMap()", 100);
	});
	$.subscribe('/projectSelect', function(event, data) {
		setTimeout("getReleaseMap()", 10);
	});
	function showAllResourcesClick(){
		//document.getElementById("showAllResources").value=document.getElementById("showAllResources").checked;
		document.forms[0].method = "post";
		document.forms[0].action = "../projects/assignResources.action";
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<s:form id="mapNetworkCodeFormId" method="POST" theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="searchSupervisor" id="searchSupervisor" />
	 
		
		
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.assign.resources" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.assign.resources" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
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
				<div>
					<table style="padding-left: 60px;">
						<tr>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.networkcode.pillar.name" />:
							</td>
							<td width="200" nowrap="nowrap"><sj:autocompleter
									list="pillarsMapObj" name="pillar" listKey="key"
									listValue="value" theme="simple" id="pillarId"
									onSelectTopics="/pillarSelect" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter> <sj:a
									id="getProjectsHrefId"
									href="../utilization/getProjectsMap.action"
									targets="projectsDivId" formIds="mapNetworkCodeFormId"
									indicator="projectLoadingIndicator" /></td>
							<td><img id="projectLoadingIndicator"
								src="../images/indicatorImg.gif" style="display: none" /></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.networkcode.project" />:
							</td>
							<td width="200"><sj:div id="projectsDivId">
									<sj:autocompleter list="projectsMapObj" name="project"
										listKey="key" listValue="value" theme="simple" id="projectId"
										selectBox="true" selectBoxIcon="true"
										onSelectTopics="/projectSelect"></sj:autocompleter>
								</sj:div> <sj:a id="getReleaseHrefId"
									href="../utilization/getNetworkCodesMap.action"
									targets="releaseDivId" formIds="mapNetworkCodeFormId"
									indicator="releaseLoadingIndicator" /></td>
							<td><img id="releaseLoadingIndicator"
								src="../images/indicatorImg.gif" style="display: none" /></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.netwok.code" />:
							</td>
							<td width="200"><sj:div id="releaseDivId">
									<sj:autocompleter list="releasesMapObj"
										name="selectedNetworkCodeId" theme="simple"
										id="networkCodesListId" onSelectTopics="/autocompleteSelect"
										selectBox="true" selectBoxIcon="true"></sj:autocompleter>
								</sj:div></td>

							<td><sj:a id="goAssignNetworkCodesHrefId"
									href="../projects/goAssignedResources.action"
									targets="assignNetworkCodesDivId"
									formIds="mapNetworkCodeFormId" indicator="loadingIndicator" />
								<img id="loadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>


							<td><s:text name="pts.show.all.resources"></s:text></td>
							<td><s:checkbox name="showAllResources"  onclick="showAllResourcesClick()" ></s:checkbox>   </td>

						</tr>
					</table>
					<div id="spacer10px">&nbsp;</div>
					<div
						style="border: 1px solid #2779AA; border-radius: 5px; width: 85%; margin-left: 75px;">
						<div id="spacer10px">&nbsp;</div>
						<table style="width: 100%;">
							<tr>
								<td style="color: #2779aa; width: 100px;" align="center"><s:text
										name="pts.resource.name" />:</td>
								<td align="left" style="width: 100px;"><s:textfield
										name="selectedEmployee" theme="simple" id="selectedEmployeeId"
										size="38" cssStyle="height:22px;" /></td>
								<td align="left"><sj:submit
										href="../projects/goAssignedResources.action"
										formIds="mapNetworkCodeFormId"
										targets="assignNetworkCodesDivId"
										indicator="searchLoadingIndicator" onclick="clearErrorDiv()"
										src="../images/search.png" cssStyle="margin-top:3px;"
										type="image">
									</sj:submit> <img id="searchLoadingIndicator"
									src="../images/indicatorImg.gif" style="display: none" /></td>
							</tr>
							<tr>
								<td colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td align="center" colspan="3"><sj:div
										id="assignNetworkCodesDivId">
										<jsp:include page="assignResources.jsp" />
									</sj:div></td>
							</tr>
						</table>
					</div>
				</div>
				<div id="spacer10px">&nbsp;</div>



				<div id="spacer10px">&nbsp;</div>
				<div style="padding-right: 5px; text-align: right;">
					<sj:submit
						onclick="submitForm('../projects/addResourcesToNetworkCodes.action')"
						src="../images/save.png" formIds="mapNetworkCodeFormId"
						type="image">
					</sj:submit>
				</div>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
<footer> </footer>
</html>
