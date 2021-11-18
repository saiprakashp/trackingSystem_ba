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
$(document).ready(function() {
   $('#selectedNetworkCodesMapId').change(function(event) {
      var selectedNetworkCode = $("select#selectedNetworkCodesMapId").val();
      $.getJSON('getRemainingHrsForNetworkCode', {
    	  selectedNetworkCodeId : selectedNetworkCode
      }, function(jsonResponse) {
        $('#remainingHrsDiv').text(jsonResponse.remainingHrs);
       
      });
      });
});
</script>	
</head>
<body>
	<s:form id="resourceUtilizationformId" method="POST" theme="simple"
		action="../utilization/saveResourceEffortDetails.action">
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
					<s:text name="pts.menu.resource.timesheet" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.resource.timesheet" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text name="pts.resource.name" />:</td>
							<td align="left" width="200">
							<!--<sj:autocompleter
									list="employeeList" name="selectedEmployee"  theme="simple" id="employeeListId"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter>-->
									<s:property value="#session['fullName']"/>
									<s:hidden name="selectedEmployee" id="selectedEmployee"/>
									</td>
							<td style="color: #2779aa;"><s:text
									name="pts.week.ending.date" />:</td>
							<td align="left"><sj:datepicker displayFormat="mm/dd/yy"
									name="selectedDate" value="%{fromDate}" changeYear="true"
									buttonImage="../images/DatePicker.png" buttonImageOnly="true" /></td>
							<td style="padding-left: 20px;"><sj:submit
									src="../images/go.png" type="image" targets="activityLogDivId"
									href="../timesheet/goUserActivity.action"
									formIds="resourceUtilizationformId" onclick="clearErrorDiv()"
									indicator="loadingIndicator" /><img id="loadingIndicator"
								src="../images/indicatorImg.gif" style="display: none" /></td>
						</tr>
					</table>
				</div>
				<sj:div id="activityLogDivId">
					<jsp:include page="timesheetSummary.jsp" />
				</sj:div>
				<div id="spacer10px">&nbsp;</div>
				<div style="padding-right: 5px; text-align: right;">
					<sj:submit src="../images/save.png" type="image"
						targets="activityLogDivId"
						href="../timesheet/saveUserActivity.action"
						formIds="resourceUtilizationformId" onclick="clearErrorDiv()" />
				</div>
				<div id="spacer10px">&nbsp;</div>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>