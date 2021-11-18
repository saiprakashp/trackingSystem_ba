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
	function getUserTechScore() {
		clearTechScoreErrorDialogDiv();
		$("#getUserTechScoreHrefId").click();
	}
	$.subscribe('completeTechScore', function(event, data) {
		setTimeout($("#techScoreErrorDialogDiv").html(
				document.getElementById('messageId').value), 100);
	});
	function clearTechScoreErrorDialogDiv() {
		$("#techScoreErrorDialogDiv").html("");
	}
</script>
</head>
<body>
	<s:form id="techScoreFormId" name="techScoreForm">
		<s:hidden name="id" id="userId" theme="simple" />
		<table>
			<tr>
				<td colspan="3" align="center"><s:div
						cssStyle="margin: 0px auto;color: RED;"
						id="techScoreErrorDialogDiv" theme="simple">
					</s:div></td>
			</tr>
			<tr>
				<td style="color: #2779aa;"><s:text name="pts.user.skills" />:</td>
				<td colspan="2"><s:select list="#session['userSkillSet']"
						id="skillId" name="skillId" theme="simple" cssClass="multiselect1"
						cssStyle="width:175px;" headerKey="-1" headerValue="Please Select"
						onchange="getUserTechScore()"></s:select></td>
			</tr>
			<tr>
				<td style="color: #2779aa;"><s:text name="pts.year" />:</td>
				<td align="left" colspan="2"><s:select list="yearList"
						name="selectedYear" id="selectedYear" theme="simple"
						onchange="getUserTechScore()"></s:select>&nbsp;&nbsp;<s:select
						list="#{'1H':'1st Half','2H':'2nd Half'}" name="halfYear"
						id="halfYear" theme="simple" onchange="getUserTechScore()"></s:select>
					<sj:a id="getUserTechScoreHrefId"
						href="../user/goGetUserTechScore.action" targets="techScoreDivId"
						formIds="techScoreFormId" indicator="techScoreLoadingIndicator" /></td>
			</tr>
			<tr>
				<td style="color: #2779aa;"><s:text name="pts.user.tech.score" />:</td>
				<td align="left"><sj:div id="techScoreDivId">
						<s:hidden name="userTechScoreId" id="userTechScoreId"
							theme="simple" />
						<s:textfield name="techScore" id="techScore" theme="simple"
							cssStyle="width:173px;"></s:textfield>
					</sj:div></td>
				<td align="left"><img id="techScoreLoadingIndicator"
					src="../images/indicatorImg.gif" style="display: none" /></td>
			</tr>
			<tr>
				<td colspan="3"><sj:div id="techScoremessageDivId">
					</sj:div></td>
			</tr>
			<tr>
				<td colspan="3" align="right"><sj:a href="saveTechScore.action"
						formIds="techScoreFormId" targets="techScoremessageDivId"
						onCompleteTopics="completeTechScore"
						indicator="techScoreindicator"
						onclick="clearTechScoreErrorDialogDiv()"
						cssStyle="margin-top:3px;">
						<img src="../images/save.png">
					</sj:a> <img id="techScoreindicator" src="../images/indicatorImg.gif"
					alt="Saving..." style="display: none" /></td>
			</tr>
		</table>
	</s:form>
</body>
</html>