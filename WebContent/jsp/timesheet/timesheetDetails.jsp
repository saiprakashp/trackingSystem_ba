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
	function submitUserUtilizationForm() {

		var fromDate = new Date();
		var toDate = new Date();

		if (document.getElementById('fromDate').value != '') {
			var tempFromDate = document.getElementById('fromDate').value;
			var from = tempFromDate.split("/");
			fromDate.setFullYear(from[2], parseInt(parseInt(from[0]) - 1),
					from[1]);
		}

		if (document.getElementById('fromDate').value != '') {
			var tempToDate = document.getElementById('toDate').value;
			var to = tempToDate.split("/");
			toDate.setFullYear(to[2], parseInt(parseInt(to[0]) - 1), to[1]);
		}
		if (document.getElementById('employeeListId_widget')
				&& document.getElementById('employeeListId_widget').value == '') {
			if (document.getElementById('employeeListId')) {
				document.getElementById('employeeListId').value = "";
			}
		}
		if (fromDate > toDate) {
			alert("From date should be after To date");
			return false;
		} else {
			document.forms[0].method = "post";
			document.forms[0].action = '../utilization/userUtilizationReport.action';
			document.forms[0].submit();
		}
	}
</script>
</head>
<body>
	<form id="manageRICOUtilizationReportFormId" method="POST">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="userFlag" id="userFlag"></s:hidden>
		<div id="breadcrumbDivParent"
			style="width: 100%;  height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.user.utilization.report" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.user.utilization.report" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;" nowrap="nowrap"><s:text
									name="pts.from.date" />:</td>
							<td width="200"><sj:datepicker displayFormat="mm/dd/yy"
									id="fromDate" name="selectedDate" value="%{startDateOfWeek}"
									changeYear="true" cssStyle="width:150px"
									buttonImage="../images/DatePicker.png" buttonImageOnly="true" /></td>

							<td style="color: #2779aa;" nowrap="nowrap"><s:text
									name="pts.to.date" />:</td>
							<td width="200"><sj:datepicker displayFormat="mm/dd/yy"
									id="toDate" name="selectedToDate" value="%{endDateOfWeek}"
									changeYear="true" cssStyle="width:150px"
									buttonImage="../images/DatePicker.png" buttonImageOnly="true" /></td>
							<td style="padding-left: 20px;" colspan="2">
							<s:a href="javascript:void(0)" onclick="submitUserUtilizationForm()"><img src="../images/go.png" ></s:a></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="timesheetDetailsGrid.jsp" /></td>
					</tr>
				</table>
				</DIV>
				<div id="spacer10px">&nbsp;</div>
				<div style="padding-right: 8px; text-align: right;">

					<s:submit theme="simple" type="image"
						src="../images/export_excel.png"
						onclick="submitForm('../utilization/exportRICOResourceEffortDetails.action')" />


				</div>

			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</form>
</body>
</html>