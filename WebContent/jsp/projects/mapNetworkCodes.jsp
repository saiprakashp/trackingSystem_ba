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
<style>
.s2j-combobox-input {
	width: 138px;
}
</style>
<script>
	$.subscribe('/autocompleteSelect', function(event, data) {
		setTimeout("goAssignNetworkCodes()", 100);
	});
</script>
</head>

<body>
	<s:form id="mapNetworkCodeFormId" method="POST" theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent"
			style="width: 100%;  height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.assign.network.codes" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.assign.network.codes" />
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
					<s:hidden name="selectedEmployee" value="%{#session.userId}" id="selectedEmployee"/><!--<table style="padding-left: 60px; width: 100%;">
						<tr>
							<td style="color: #2779aa; width: 100px;" align="right"><s:text
									name="pts.resource.name" />:</td>
							<td align="left" width="270px"><s:property
											value="%{#session.fullName}" />
											<sj:autocompleter
									list="employeeList" name="selectedEmployee" theme="simple"
									id="employeeListId" onSelectTopics="/autocompleteSelect"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter>
								&nbsp;</td>
							<td><sj:a id="goAssignNetworkCodesHrefId"
									href="../projects/goAssignNetworkCodes.action"
									targets="assignNetworkCodesDivId"
									formIds="mapNetworkCodeFormId" indicator="loadingIndicator" /><img
								id="loadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>
						</tr>
					</table>-->
					<div id="spacer10px">&nbsp;</div>
					<div
						style="border: 1px solid #2779AA; border-radius: 5px; width: 85%; margin-left: 75px;">
						<div id="spacer10px">&nbsp;</div>
						<table style="width: 100%;">
							<tr>
								<td style="color: #2779aa; width: 100px;" align="center"><s:text
										name="pts.netwok.code" />:</td>
								<td align="left" style="width: 100px;"><s:textfield
										name="searchNetworkCode" theme="simple"
										id="searchNetworkCodeId" size="38" cssStyle="height:22px;" /></td>
								<td align="left"><sj:submit
										href="../projects/goAssignNetworkCodes.action"
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
										<jsp:include page="assignNetworkCodes.jsp" />
									</sj:div></td>
							</tr>
						</table>
					</div>
				</div>
				<div id="spacer10px">&nbsp;</div>



				<div id="spacer10px">&nbsp;</div>
				<div style="padding-right: 5px; text-align: right;">
					<sj:submit
						onclick="submitForm('../projects/addNetworkCodesToUser.action')"
						src="../images/save.png" type="image"
						formIds="mapNetworkCodeFormId">
					</sj:submit>
				</div>
			</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>
