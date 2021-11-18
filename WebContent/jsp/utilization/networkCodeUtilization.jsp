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
<script type="text/javascript">
	datePick = function(element) {
		$(element).datepicker();
	};
	
	

</script>
</head>
<body>
	<s:form id="manageUtilizationFormId" method="POST" theme="simple">
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
					<s:text name="pts.menu.utilization.report" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.utilization.report" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text name="pts.resource.name" />:</td>
							<td align="left" width="200"><s:textfield
									name="searchUserName" id="searchUserName" theme="simple"
									cssStyle="width:175px;" /> <s:hidden name="allReporteesFlag"
									id="allReporteesFlag" value="true" /></td>
							<td style="color: #2779aa;"><s:text name="pts.user.supervisor" />:</td>
							<td width="200"><sj:autocompleter list="supervisorMapForManage" 
													name="searchSupervisor" listKey="key" listValue="value"
													theme="simple" id="searchSupervisor" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;"><s:text name="pts.year" />:</td>
							<td align="left">
							<s:select list="yearList" name="selectedYear" id="selectedYear"></s:select>
							</td> <!-- <select id="tempSelectedYear"
								name="tempSelectedYear" onchange="setYear(this.value)"></select> -->
							<td style="padding-left: 20px;"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../utilization/manageNCUtilization.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="networkCodeUtilizationGrid.jsp" /></td>
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