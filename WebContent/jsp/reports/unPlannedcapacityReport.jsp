<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<sj:head jqueryui="true" ajaxcache="false" compressed="false"
	jquerytheme="cupertino" />
<head>

<style>
.main {
	width: 80% !important;
	padding-right: 15px;
	padding-left: 15px;
	margin-right: auto;
	background-color: white;
	margin-left: auto;
	border-right: 2px solid darkgrey;
	border-left: 2px solid darkgrey;
	border-top: 2px solid darkgrey;
	margin-top: 13px;
}

.unpTab {
	margin-left: 12%;
	margin-top: 2%;
}
</style>
</head>
<body>
	<jsp:include page="../header.jsp" />
	<jsp:include page="../menu.jsp" />
	<div class='container-fluid main' style='padding-top: 1%;'>
		<div class=''>
			<h5>Unplanned Projects</h5>
		</div>
		<form id='getUnlannedDet' action='../reports/goGetUnPlannedCapacityReport.action'>
			<div class='row'>
			<div class='col-lg-1'></div>
				<!--   <div class='col-lg-1'  style="padding-top: 0.52%;">
					<label class="text-left">By Manage: </label>
					<s:checkbox name="bySupervisor" id="byManager" />
				</div> -->
				<div class='col-lg-3'>
					<s:textfield name="year" id="year" cssClass="text-left" />
					<img src='../images/go.png' class='img-thumbnail' alt="go" onclick="javascript:document.getElementById('getUnlannedDet').submit()" />
				</div>

			</div>
			
			<jsp:include page="unplannedCapGrid.jsp"></jsp:include>
			</form>
	</div>
	<div class='container-fluid'>
		<jsp:include page="../footer.jsp"></jsp:include>
	</div>
</body>
</html>