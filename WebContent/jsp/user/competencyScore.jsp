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
	function getUserCompScore() {
		clearCompScoreErrorDialogDiv();
		$("#getUserCompScoreHrefId").click();
	}
	$.subscribe('complete', function(event, data) {
		setTimeout($("#compScoreErrorDialogDiv").html(
				document.getElementById('messageId').value), 100);
	});
	function clearCompScoreErrorDialogDiv() {
		$("#compScoreErrorDialogDiv").html("");
	}
</script>
</head>
<body>
	<s:form id="compScoreFormId" name="compScoreForm">
		<s:hidden name="id" id="userId" theme="simple" />
		<table>
			<tr>
				<td colspan="3" align="center"><s:div
						cssStyle="margin: 0px auto;color: RED;"
						id="compScoreErrorDialogDiv" theme="simple">
					</s:div></td>
			</tr>
			<tr>
				<td style="color: #2779aa;"><s:text name="pts.user.platform" />:</td>
				<td colspan="2"><s:select list="#session['userPlatformsSet']"
						id="userProjectId" name="userPillarId" theme="simple"
						cssClass="multiselect1" cssStyle="width:175px;" headerKey="-1"
						headerValue="Please Select" onchange="getUserCompScore()"></s:select></td>
			</tr>
			<tr>
				<td style="color: #2779aa;"><s:text name="pts.year" />:</td>
				<td align="left" colspan="2"><s:select list="yearList"
						name="selectedYear" id="selectedYear" theme="simple"
						onchange="getUserCompScore()"></s:select>&nbsp;&nbsp;<s:select
						list="#{'1H':'1st Half','2H':'2nd Half'}" name="halfYear"
						id="halfYear" theme="simple" onchange="getUserCompScore()"></s:select>
					<sj:a id="getUserCompScoreHrefId"
						href="../user/goGetUserAppCompScore.action"
						targets="compScoreDivId" formIds="compScoreFormId"
						indicator="compScoreLoadingIndicator" /></td>
			</tr>
			<tr>
				<td style="color: #2779aa;"><s:text name="pts.user.comp.score" />:</td>
				<td align="left" nowrap="nowrap"><sj:div id="compScoreDivId">
						<s:hidden name="userAppCompScoreId" id="userAppCompScoreId"
							theme="simple" />
						<s:textfield name="competencyScore" id="competencyScore"
							theme="simple" cssStyle="width:173px;"></s:textfield>
					</sj:div></td>
				<td align="left"><img id="compScoreLoadingIndicator"
					src="../images/indicatorImg.gif" style="display: none" /></td>
			</tr>
			<tr>
				<td colspan="3"><sj:div id="compScoreMessageDivId">
					</sj:div></td>
			</tr>
			<tr>
				<td colspan="3" align="right"><sj:a
						href="saveCompetencyScore.action" formIds="compScoreFormId"
						targets="compScoreMessageDivId" onCompleteTopics="complete"
						indicator="compScoreIndicator"
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