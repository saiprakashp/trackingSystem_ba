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
	$.subscribe('complete', function(event, data) {
		setTimeout($("#compScoreErrorDialogDiv").html(
				document.getElementById('messageId').value), 100);
		$(".ui-dialog-titlebar-close").click();
		window.parent.closeModalAndReload();

	});
	function clearCompScoreErrorDialogDiv() {
		$("#compScoreErrorDialogDiv").html("");
	}
</script>
</head>
<body>
	<s:form id="projectPhaseFormId" name="projectPhaseForm">
		<s:hidden name="id" id="id" theme="simple" />
		<table>
			<tr>
				<td colspan="2" align="center"><s:div
						cssStyle="margin: 0px auto;color: RED;"
						id="compScoreErrorDialogDiv" theme="simple">
					</s:div></td>
			</tr>
			<tr>
				<td style="color: #2779aa;"><s:text name="pts.netwok.code" />:</td>
				<td><s:property value="networkCode" /></td>
			</tr>
			<tr>
				<td style="color: #2779aa;" align="right"><s:text name="Phase" />:</td>
				<td><s:select
						list="#{'Completed':'Completed','Deployment':'Deployment','Development':'Development','Feasibility':'Feasibility', 'Hold':'Hold','System Test':'System Test'}"
						name="projectStage" theme="simple" id="projectStage"></s:select></td>
			</tr>
			<tr>
				<td colspan="2"><sj:div id="compScoreMessageDivId">
					</sj:div></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><sj:a
						href="../projects/saveProjectReleasePhase.action"
						formIds="projectPhaseFormId" targets="compScoreMessageDivId"
						onCompleteTopics="complete" indicator="compScoreIndicator"
						onclick="clearCompScoreErrorDialogDiv()"
						cssStyle="margin-top:3px;">
						<img src="../images/save.png">
					</sj:a> <img id="compScoreIndicator" src="../images/indicatorImg.gif"
					alt="Saving..." style="display: none" /></td>
			</tr>
		</table>
	</s:form>
</body>
</html>