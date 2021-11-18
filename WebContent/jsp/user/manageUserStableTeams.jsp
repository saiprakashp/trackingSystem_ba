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
	$(document).ready(function() {
		$.subscribe('movetonextdiv', function(event, data) {
			var el = $("#localtabs");
			var size = el.find(">ul >li").size();
			var active = el.tabs('option', 'active');

		});
		$.subscribe('movetoprevdiv', function(event, data) {
			var el = $("#localtabs");
			var size = el.find(">ul >li").size();
			var active = el.tabs('option', 'active');

		});
	});
	function validateUserForm() {
		var status1 = checkStableTeamsContribution();
		if (status1 === true) {
			document.forms['saveUserStable'].submit();
		} else {
			return false;
		}

		return false;
	}

	function checkStableTeamsContribution() {
		var temp = 0.0;
		$("input[id*='stableId']").each(function(index, value) {
			var val = (value.value).split("%")[0];
			var x = (parseInt(val));
			if (/\d/.test(x)) {
				temp += parseFloat(x);
			}
		});
		if (temp > parseFloat('100.0')) {
			alert('User Stable Teams Cannot be > 1');
			return false;
		}
		return true;
	}

	function setStableTeamValues(event) {
		var temp = 0.0;
		$("input[id*='stableId']").each(function(index, value) {
			var val = (value.value).split("%")[0];
			var x = (parseInt(val));
			if (/\d/.test(x)) {
				temp += parseFloat(x);
			}
		});
		document.getElementById("stableTeamTotal").innerHTML = temp + "  %";
	}
	
	
</script>
</head>
<body onload="setStableTeamValues('')">
	<s:form id="saveUserStable" method="POST"
		onsubmit="return validateUserForm()"
		action="../user/saveUserStableTeamsData.action" theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="searchSupervisor" id="searchSupervisor" />
		<s:hidden name="searchStatus" id="searchStatus" />
		<s:hidden name="searchLocation" id="searchLocation" />
		<div id="breadcrumbDivParent"
			style="width: 100%; background: url('../images/header_bg_shadow.png') repeat-x; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.networkcode.user.stable.teams" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.networkcode.user.stable.teams" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div style="width: 100%;">
					<s:div cssStyle="margin: 0px auto;" id="errorDiv" theme="simple">
						<table style="margin: 0 auto;">
							<tr>
								<td align="left"><s:fielderror theme="simple"
										cssStyle="margin-left:10px;color: RED" /> <s:actionerror
										theme="simple" cssStyle="margin-left:10px;color: RED" /> <s:actionmessage
										theme="simple" cssStyle="margin-left:10px;color: RED" /></td>
							</tr>
						</table>
					</s:div>
				</div>
			</DIV>



			<DIV style="margin: 0 auto; width: 980px;">
				<table style="width: 99%;">
					<tr>
						<td align="center"><sj:tabbedpanel id="localtabs"
								cssClass="list" cssStyle="width:100%" onChangeTopics="tabchange">
								<sj:tab id="tab1" target="tone" label="Stable Team" />
								<div id="tone">

									<jsp:include page="userStableTeams.jsp"></jsp:include>

								</div>
							</sj:tabbedpanel></td>
					</tr>
				</table>
			</DIV>
			<div>
				<table style="width: 100%;">
					<tr>
						<!-- td style="padding-left: 5px; text-align: left; display: none;"
							id="previd"><sj:a href="#" onClickTopics="movetoprevdiv"
								button="true">Prev</sj:a></td-->
						<td style="padding-left: 5px; text-align: left;" id="cancelid"><s:a
								href="../login/showDashboard.action">
								<img src="../images/cancel.png" title="Cancel" />
							</s:a></td>
						<!-- td style="padding-right: 5px; text-align: right;" id="nextid"><sj:a
								href="#" onClickTopics="movetonextdiv" button="true">Next</sj:a></td-->
						<td style="padding-right: 5px; text-align: right;" id="saveid"><sj:submit
								src="../images/save.png" type="image" value="Save" /></td>
					</tr>
				</table>
			</div>

		</div>
	</s:form>
</body>
</html>
