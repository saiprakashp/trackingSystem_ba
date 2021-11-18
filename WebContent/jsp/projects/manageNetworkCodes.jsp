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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/struts/js/base/jquery.ui.datepicker.js"></script>
<script type="text/javascript">
	datePick = function(element) {
		$(element).datepicker();
	};
</script>
</head>
<body>
	<s:form theme="simple" method="POST" id="manageNetworkCodeFormId">
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
					<s:text name="pts.menu.manage.network.codes" />
				</div>
			</div>
		</div>
		<s:hidden name="id" id="ncId" />
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.manage.network.codes" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<!-- <td style="color: #2779aa;"><s:text
									name="pts.networkcode.code" />:</td>
							<td><s:textfield name="searchNetworkId"
									id="searchReleaseId" theme="simple" cssStyle="width:130px;" /></td> -->
							<td style="color: #2779aa;">Release Id:</td>
							<td><s:textfield name="searchReleaseId" id="searchReleaseId"
									theme="simple" cssStyle="width:130px;" /></td>
							<td style="color: #2779aa;">Release Name:</td>
							<td><s:textfield name="searchReleaseName"
									id="searchReleaseName" theme="simple" cssStyle="width:130px;" /></td>
							<td style="color: #2779aa;"><s:text
									name="pts.networkcode.project.manager" />:</td>
							<td><s:textfield name="searchPM" id="searchPM"
									theme="simple" cssStyle="width:130px;" /></td>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.networkcode.status" />:</td>
							<td><sj:autocompleter list="statusMapObj"
									name="searchStatus" listKey="key" listValue="value"
									theme="simple" id="searchStatus" selectBox="true"
									selectBoxIcon="true" cssStyle="width:200px;">></sj:autocompleter></td>
							</tr>
						<tr>

							<td style="color: #2779aa;"><s:text
									name="pts.networkcode.no.of.TFSEpic" />:</td>
							<td><s:textfield name="TFSEpic" id="TFSEpic" theme="simple"
									cssStyle="width:130px;" /></td>
							<td style="color: #2779aa;"></td>
							<td style="color: #2779aa;"></td>
							<td style="color: #2779aa;"></td>
							<td style="color: #2779aa;"></td>
							<td style="color: #2779aa;"></td>
							<td style="color: #2779aa;"></td>
							<td style="padding-left: 20px;"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../projects/manageNetworkCodes.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="networkCodesGrid.jsp" /></td>
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