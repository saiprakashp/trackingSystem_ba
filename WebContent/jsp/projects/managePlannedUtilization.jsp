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
	function serachPlannedUtilization() {
		clearError();
		var supervisor = document.getElementById("searchSupervisor").value;
		if (supervisor == null || (supervisor != null && supervisor == '')) {
			$("#errorDiv").append("Please Select Supervisor.");
		} else {
			$.post("../projects/ajaxPlannedUserUtilization.action", {
				selectedSupervisor : supervisor,
				selectedYear : $("#selectedYear").val(),
				selectedMonth : $("#selectedMonth").val()
			})
					.done(
							function(result) {
								if (result.total == 0) {
									$("#errorDiv").append("No Records Found.");
								}else{
								$("#successDiv").append("Success.");}
								$("#Bangloreworkingdays").val(
										result.bangloreworkingdays);
								$("#BangloreresourceCount").val(
										result.bangloreresourceCount);
								$("#ChennairesourceCount").val(
										result.chennairesourceCount);
								$("#Chennaiworkingdays").val(
										result.chennaiworkingdays);
								$("#HyderabadresourceCount").val(
										result.hyderabadresourceCount);
								$("#Hyderabadworkingdays").val(
										result.hyderabadworkingdays);
								$("#Manaworkingdays").val(
										result.manaworkingdays);
								$("#ManaresourceCount").val(
										result.manaresourceCount);

								$("#manaWorkingHours").val(
										result.manaWorkingHours);
								$("#egiWorkingHours").val(
										result.egiWorkingHours);

							}).fail(function() {
						$("#errorDiv").append("Please Try again Later.");
					});
		}
	}
	function callSaveUpdateData() {
		clearError();
		var supervisor = document.getElementById("searchSupervisor").value;
		if (supervisor == null || (supervisor != null && supervisor == '')) {
			$("#errorDiv").append("Please Select Supervisor.");
		} else {
			$("#selectedMonthVal").val($("#selectedMonth").val());
			$.post("../projects/updatePlannedUtilization.action", {
				selectedSupervisor : supervisor,
				selectedYear : $("#selectedYear").val(),
				selectedMonth : $("#selectedMonth").val(),
				bangloreworkingdays : $("#Bangloreworkingdays").val(),
				bangloreresourceCount : $("#BangloreresourceCount").val(),
				chennairesourceCount : $("#ChennairesourceCount").val(),
				chennaiworkingdays : $("#Chennaiworkingdays").val(),
				hyderabadresourceCount : $("#HyderabadresourceCount").val(),
				hyderabadworkingdays : $("#Hyderabadworkingdays").val(),
				manaworkingdays : $("#Manaworkingdays").val(),
				manaresourceCount : $("#ManaresourceCount").val(),
				manaWorkingHours : $("#manaWorkingHours").val(),
				egiWorkingHours : $("#egiWorkingHours").val(),
			
				manaTargetHrs : $("#manaTargetHrs").val(),
				chenTargetHrs : $("#chenTargetHrs").val(),
				bangTargetHrs : $("#bangTargetHrs").val(),
				hydTargetHrs : $("#hydTargetHrs").val(),
				
			}).done(function(result) {
				$("#successDiv").append("Saved success.");
			}).fail(function() {
				$("#errorDiv").append("Please Try again Later.");
			});
		}
	}
	function clearError() {
		$("#errorDiv").empty();
		$("#successDiv").empty();
	}
	function changeEventData(location) {

		if (location === 'Hyderabad') {
			$("#HyderabadInsert").val(true);
		} else if (location === 'Chennai') {
			$("#ChennaiInsert").val(true);
		} else if (location === 'Bangalore') {
			$("#BangloreInsert").val(true);
		} else if (location === 'Mana') {
			$("#ManaInsert").val(true);
		}

	}
</script>
</head>
<body onload="clearError()">
	<s:form theme="simple" method="POST" id="manageActivityCodeFormId">
		<s:hidden name="HyderabadInsert" id="HyderabadInsert"></s:hidden>
		<s:hidden name="BangloreInsert" id="BangloreInsert"></s:hidden>
		<s:hidden name="ChennaiInsert" id="ChennaiInsert"></s:hidden>
		<s:hidden name="ManaInsert" id="ManaInsert"></s:hidden>
		<s:hidden name="selectedMonth" id="selectedMonthVal"></s:hidden>

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
					<s:text name="pts.menu.manage.user.utilization" />
				</div>
			</div>
		</div>
		<s:hidden name="selectedSupervisor" id="userId" />
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.manage.user.utilization" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.supervisor" />:</td>
							<td align="left"><sj:autocompleter list="supervisorMap"
									name="selectedSupervisor" listKey="key" listValue="value"
									theme="simple" id="searchSupervisor" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter>

								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>

							<td style="color: #2779aa;" align="right"><s:text
									name="pts.year" />:</td>
							<td align="left"><sj:autocompleter list="yearMap"
									name="selectedYear" listKey="key" listValue="value"
									theme="simple" id="selectedYear" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

							</td>

							<td style="color: #2779aa;" align="right"><s:text
									name="pts.month" />:</td>
							<td align="left"><sj:autocompleter list="monthMap"
									name="selectedMonth" listKey="key" listValue="value"
									theme="simple" id="selectedMonth" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</td>

							<td style="padding-left: 20px;"><a
								onclick="serachPlannedUtilization()"> <img
									src="../images/go.png" width="30" height="30" />
							</a></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td colspan="3" style="text-align: center;"><span
							id="errorDiv"
							style="color: red; font-size: 12px; text-align: center;"></span><span
							id="successDiv"
							style="color: #0082f0; font-size: 12px; text-align: center;"></span></td>
					</tr>
				</table>
				<form id="utilizationData">
					<div id="plannedUtil">

						<table style="width: 100%; border: 1px solid;">
							<thead>
								<tr>
									<th style="color: #2779aa;" align="center">Location Name:</th>
									<th style="color: #2779aa;" align="center">Head Count:</th>
									<th style="color: #2779aa;" align="center">Working Days:</th>
									<th style="color: #2779aa;" align="center">Targeted Hours:</th>
								</tr>
							</thead>
							<tbody align="center">
								<tr>
									<td><s:textfield name="Hyderabadlocation" disabled="true"
											value="Hyderabad" id="Hyderabadlocation" /></td>
									<td><s:textfield name="HyderabadresourceCount"
											onblur="changeEventData('Hyderabad')"
											id="HyderabadresourceCount" value="0" /></td>
									<td><s:textfield name="Hyderabadworkingdays" value="0"
											onblur="changeEventData('Hyderabad')"
											id="Hyderabadworkingdays" /></td>
									<td><s:textfield name="hydTargetHrs" value="0"
											onblur="changeEventData('Hyderabad')" id="hydTargetHrs" /></td>
								</tr>
								<tr>
									<td><s:textfield name="Chennailocation" disabled="true"
											value="Chennai" id="Chennailocation" /></td>
									<td><s:textfield name="ChennairesourceCount" value="0"
											onblur="changeEventData('Chennai')" id="ChennairesourceCount" /></td>
									<td><s:textfield name="Chennaiworkingdays" value="0"
											onblur="changeEventData('Chennai')" id="Chennaiworkingdays" /></td>
									<td><s:textfield name="chenTargetHrs" value="0"
											onblur="changeEventData('Chennai')" id="chenTargetHrs" /></td>
								</tr>
								<tr>
									<td><s:textfield name="Banglorelocation" disabled="true"
											value="Bangalore" id="Banglorelocation" /></td>
									<td><s:textfield name="BangloreresourceCount" value="0"
											onblur="changeEventData('Bangalore')"
											id="BangloreresourceCount" /></td>
									<td><s:textfield name="Bangloreworkingdays" value="0"
											onblur="changeEventData('Bangalore')"
											id="Bangloreworkingdays" /></td>
									<td><s:textfield name="bangTargetHrs" value="0"
											onblur="changeEventData('Bangalore')" id="bangTargetHrs" /></td>
								</tr>
								<tr>
									<td><s:textfield name="Manalocation" disabled="true"
											value="Mana" id="Manalocation" /></td>
									<td><s:textfield name="ManaresourceCount" value="0"
											onblur="changeEventData('Mana')" id="ManaresourceCount" /></td>
									<td><s:textfield name="Manaworkingdays" value="0"
											onblur="changeEventData('Mana')" id="Manaworkingdays" /></td>
									<td><s:textfield name="manaTargetHrs" value="0"
											onblur="changeEventData('Mana')" id="manaTargetHrs" /></td>
								</tr>

							</tbody>
						</table>
						<table style="width: 100%; border: 1px solid;">
							<thead>
								<tr>
									<th style="color: #2779aa;" align="center">Location:</th>
									<th style="color: #2779aa;" align="center">No Of Hours Per
										Day:</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td style="color: #2779aa;" align="center">EGI</td>
									<td align="center"><s:textfield name="egiWorkingHours"
											value="0" id="egiWorkingHours" /></td>
								</tr>
								<tr>
									<td style="color: #2779aa;" align="center">MANA:</td>
									<td align="center"><s:textfield name="manaWorkingHours"
											value="0" id="manaWorkingHours" /></td>
								</tr>
							</tbody>
						</table>
						<a style="float: right;" onclick="callSaveUpdateData()"> <img
							src="../images/save.png" height="25" width="40" />
						</a>
					</div>
				</form>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>