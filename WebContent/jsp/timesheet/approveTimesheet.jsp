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
		$("#approveAll").click(function() {
			if (this.checked) {
				$(".approveAllClass").prop('checked', true);
				$(".rejectAllClass").prop('checked', false);
				$("#rejectAll").prop('checked', false);
			} else {
				$(".approveAllClass").prop('checked', false);
			}
		});

		$("#rejectAll").click(function() {
			if (this.checked) {
				$(".approveAllClass").prop('checked', false);
				$(".rejectAllClass").prop('checked', true);
				$("#approveAll").prop('checked', false);
			} else {
				$(".rejectAllClass").prop('checked', false);
			}
		});
	});

	function submitUserUtilizationForm() {
		//$("#showAllpen").prop("checked", false);
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
			document.forms[0].action = '../timesheet/goApproveTimesheet.action';
			document.forms[0].submit();
		}
	}

	function setFromToDate() {
		var d = new Date();
		let x = new Date().getDate() + (6 - new Date().getDay() - 1) - 6;
		let lastF = new Date();
		lastF.setDate(x);
		
		let x1 = new Date().getDate() + (6 - new Date().getDay() - 1) ;
		let lastF1 = new Date();
		lastF1.setDate(x1);
		
		
		var c = (d.getMonth() + 1);
		if (d.getMonth() < 10) {
			c = '0' + (d.getMonth() + 1)
		}
		var currDat = c + "/" + d.getDate() + "/" + d.getFullYear();
		if (currDat === $('#fromDate').val()) {
			$('#fromDate').val(
					(lastF.getMonth() + 1) + "/" + lastF.getDate() + "/"
							+ lastF.getFullYear());
		}
		/*if (currDat === $('#toDate').val()) {
			$('#toDate').val(
					(lastF1.getMonth() + 1) + "/" + lastF1.getDate() + "/"
							+ lastF1.getFullYear());
		}*/
	}
</script>
</head>
<body onload="setFromToDate()">
	<s:form id="resourceUtilizationformId" method="POST" theme="simple"
		action="../utilization/saveResourceEffortDetails.action">
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
					<s:text name="pts.menu.approve.timesheet" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.approve.timesheet" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text name="pts.resource.name" />:</td>
							<td align="left" width="200"><sj:autocompleter
									list="employeeList" name="selectedEmployee" theme="simple"
									id="employeeListId" selectBox="true" selectBoxIcon="true"></sj:autocompleter>
							</td>
							<td style="color: #2779aa;"><s:text
									name="pts.week.ending.from.date" />:</td>
							<td align="left"><sj:datepicker displayFormat="mm/dd/yy"
									id="fromDate" size="10" name="selectedDate" value="%{fromDate}"
									changeYear="true" buttonImage="../images/DatePicker.png"
									buttonImageOnly="true" /></td>
							<td style="color: #2779aa;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.week.ending.to.date" />:
							</td>
							<td align="left"><sj:datepicker displayFormat="mm/dd/yy"
									id="toDate" size="10" name="selectedToDate" value="%{toDate}"
									changeYear="true" buttonImage="../images/DatePicker.png"
									buttonImageOnly="true" /></td>
						</tr>
						<tr>
							<td style="color: #2779aa;"><s:text name="pts.user.status" />:</td>
							<td align="left"><s:select cssStyle="height:20px;"
									list="#{'All':'All','Pending':'Pending' }" name="approvalType"
									theme="simple" id="approvalTypeId"></s:select></td>
							<td style="color: #2779aa;"><s:text
									name="pts.approve.time.sheet.show.all" />:</td>
							<td align="left"><s:checkbox id="showAllpen"
									name="showAllPen"></s:checkbox></td>
							<td><s:submit theme="simple" src="../images/go.png"
									type="image" onclick="submitUserUtilizationForm();" /></td>

						</tr>
					</table>
				</div>
				<sj:div id="activityLogDivId">
					<jsp:include page="approveTimesheetSummary.jsp" />
				</sj:div>
				<div id="spacer10px">&nbsp;</div>
				<div style="padding-right: 5px; text-align: right;">
					<sj:submit src="../images/Submit.png" type="image"
						targets="activityLogDivId" indicator="loadingIndicator"
						href="../timesheet/approveTimesheet.action"
						formIds="resourceUtilizationformId" onclick="clearErrorDiv()" />
					<img id="loadingIndicator" src="../images/indicatorImg.gif"
						style="display: none" />
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