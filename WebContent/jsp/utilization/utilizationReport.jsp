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
			fromDate.setFullYear(from[2], parseInt(parseInt(from[1]) - 1),
					from[0]);
		}

		if (document.getElementById('fromDate').value != '') {
			var tempToDate = document.getElementById('toDate').value;
			var to = tempToDate.split("/");
			toDate.setFullYear(to[2], parseInt(parseInt(to[1]) - 1), to[0]);
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
			document.forms[0].action = '../utilization/manageUserUtilization.action';
			document.forms[0].submit();
		}
	}
</script>
</head>
<body>
	<form id="manageUtilizationReportFormId" method="POST">
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
							<td style="color: #2779aa;" nowrap="nowrap"><s:text
									name="pts.resource.name" />:</td>
							<td width="200"><sj:autocompleter list="employeeList"
									name="selectedEmployee" theme="simple" id="employeeListId"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;" nowrap="nowrap">Reportee Type:</td>
							<td style="color: #2779aa;" width="200"><sj:autocompleter
									list="#{'DR':'Direct Reportees','AR':'All Reportees'}"
									name="reporteeType" theme="simple" id="reporteeType"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;" nowrap="nowrap">Delinquent
								Entries:</td>
							<td style="color: #2779aa;" width="200"><s:checkbox
									name="delinquentEntries" id="delinquentEntries" theme="simple" /></td>

						</tr>
						<tr>
							<td style="color: #2779aa;" nowrap="nowrap"><s:text
									name="pts.from.date" />:</td>
							<td width="200"><sj:datepicker displayFormat="mm/dd/yy"
									id="fromDate" name="selectedDate" value="%{fromDate}"
									changeYear="true" cssStyle="width:150px"
									buttonImage="../images/DatePicker.png" buttonImageOnly="true" /></td>

							<td style="color: #2779aa;" nowrap="nowrap"><s:text
									name="pts.to.date" />:</td>
							<td width="200"><sj:datepicker displayFormat="mm/dd/yy"
									id="toDate" name="selectedToDate" value="%{toDate}"
									changeYear="true" cssStyle="width:150px"
									buttonImage="../images/DatePicker.png" buttonImageOnly="true" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div style="padding-right: 8px; text-align: right;">

					<s:submit theme="simple" type="image"
						src="../images/export_excel.png"
						onclick="submitForm('../utilization/exportUtilizationReport.action')" />


				</div>
			</div>

			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</form>
</body>
</html>