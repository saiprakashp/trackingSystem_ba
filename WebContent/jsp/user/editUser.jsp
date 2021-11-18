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
	function validateUserForm() {
		var elems = document.getElementsByClassName('contributionList');
		var total = 0.0;
		if (elems.length == 0) {
			elems = document.getElementsByClassName('contributionList1');
		}
		for (var i = 0; i < elems.length; i++) {
			total += parseFloat(elems[i].value);
		}

		if (total.toFixed(2) > 1.01) {
			alert("Total Contribution can't be more than 1");
			return false;
		}
		var status = checkUserContribution();
		var status1 = checkStableTeamsContribution();
		if (status === true && status1 === true) {
			document.forms['editUserForm'].submit();
		} else {
			return false;
		}

		return false;
	}

	$.subscribe('tabchange', function(event, data) {

		var tab = event.originalEvent.ui.newTab.attr("id");

		/*if (tab === "tab1") {
			document.getElementById("previd").style.display = "none";
			document.getElementById("cancelid").style.display = "";
			document.getElementById("saveid").style.display = "none";
			document.getElementById("nextid").style.display = "";
		} else if (tab === 'tab4') {
			document.getElementById("saveid").style.display = "";
			document.getElementById("previd").style.display = "";
			document.getElementById("nextid").style.display = "none";
			document.getElementById("cancelid").style.display = "none";
		} else {
			document.getElementById("saveid").style.display = "none";
			document.getElementById("previd").style.display = "";
			document.getElementById("nextid").style.display = "";
			document.getElementById("cancelid").style.display = "none";*/
		if (tab === 'tab2') {
			if (document.getElementById("hiddenSupervisorId").value != document
					.getElementById("supervisorNameId_widget").value) {
				document.getElementById("ajaxGetSupervisorAccountsId").click();
			}
		} else if (tab === 'tab3') {
			$('select.multiselect2 option').each(function() {
				this.selected = true;
			});
			document.getElementById("ajaxGetUserContributionId").click();
		}
		//}

	});

	$(document).ready(function() {
		$.subscribe('movetonextdiv', function(event, data) {
			var el = $("#localtabs");
			var size = el.find(">ul >li").size();
			var active = el.tabs('option', 'active');
			/*if (active == (size - 1)) {
				document
						.getElementById("saveid").style.display = "";
				document
						.getElementById("previd").style.display = "";
				document
						.getElementById("nextid").style.display = "none";
				document
						.getElementById("cancelid").style.display = "none";
			} else {
				el.tabs('option', 'active',
						active + 1 >= size ? 0
								: active + 1);
				if (active == (size - 2)) {
					document
							.getElementById("saveid").style.display = "";
					document
							.getElementById("nextid").style.display = "none";
				} else {
					document
							.getElementById("saveid").style.display = "none";
					document
							.getElementById("nextid").style.display = "";
				}
				document
						.getElementById("previd").style.display = "";
				document
						.getElementById("cancelid").style.display = "none";
			}*/
		});
		$.subscribe('movetoprevdiv', function(event, data) {
			var el = $("#localtabs");
			var size = el.find(">ul >li").size();
			var active = el.tabs('option', 'active');
			/*if (active != 0) {
				el.tabs('option', 'active',
						active - 1 >= size ? 0
								: active - 1);
				if (active == 1) {
					document
							.getElementById("previd").style.display = "none";
					document
							.getElementById("cancelid").style.display = "";
				} else {
					document
							.getElementById("cancelid").style.display = "none";
					document
							.getElementById("previd").style.display = "";
				}
				document
						.getElementById("saveid").style.display = "none";
				document
						.getElementById("nextid").style.display = "";
			} else {
				document
						.getElementById("saveid").style.display = "none";
				document
						.getElementById("previd").style.display = "none";
				document
						.getElementById("nextid").style.display = "";
				document
						.getElementById("cancelid").style.display = "";
			}*/
		});
	});
	function checkUserContribution() {

		var temp = 0.0;
		$("input[id*='appContribution']").each(function(index, value) {
			var x = (parseInt(value.value));
			if (/\d/.test(x)) {
				temp += parseFloat(x);
			}
		});
		if ($('#primarySkillId').val() === '-1') {
			alert('Please Select Primary Skill');
			e.preventDefault();
			return false;
		}
		if (temp > parseFloat('1.0')) {
			alert('User Contribution Cannot be > 1');
			e.preventDefault();
			return false;
		}

		return true;
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
<body onload="setStableTeamValues(null);">
	<s:form id="editUserForm" method="POST"
		action="../user/modifyUser.action" theme="simple">
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
					<s:a href="../user/manageUser.action">
						<s:text name="pts.menu.manage.user" />
					</s:a>
					>
					<s:text name="pts.user.edit" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.user.edit" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div style="width: 100%;">
					<s:hidden name="id" id="userId" />
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
			<sj:a href="ajaxGetSupervisorAccounts"
				id="ajaxGetSupervisorAccountsId" targets="ttwo"
				formIds="editUserForm"></sj:a>
<%-- 			<sj:a href="ajaxGetUserContribution" id="ajaxGetUserContributionId"
				targets="tthree" formIds="editUserForm"></sj:a>
 --%>
			<DIV style="margin: 0 auto; width: 980px;">
				<table style="width: 99%;">
					<tr>
						<td align="center"><sj:tabbedpanel id="localtabs"
								cssClass="list" cssStyle="width:100%" onChangeTopics="tabchange">
								<sj:tab id="tab1" target="tone" label="Personal Information" />
								<sj:tab id="tab2" target="ttwo" label="Accounts" />
							<%-- 	<sj:tab id="tab3" target="tthree" label="Contribution" /> --%>
								<sj:tab id="tab4" target="tfour" label="Stable Team" />
								<sj:tab id="tab5" target="tfive" label="Skills" />
								<div id="tone" style="background-color: #FFF;">
									<table cellspacing="10" style="width: 100%;">

										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.name" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><s:textfield name="name" id="name" theme="simple"
													cssStyle="width:205px;"></s:textfield></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.signum" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><s:textfield name="userName" id="userName"
													theme="simple" cssStyle="width:205px;"></s:textfield></td>
										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.vzid" /><span
												style="color: RED; font-size: 15px; font-weight: bold;"></span>:</td>
											<td><s:textfield name="vzid" id="vzid" theme="simple"
													cssStyle="width:205px;"></s:textfield></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.email" />:</td>
											<td><s:textfield name="email" id="email" theme="simple"
													cssStyle="width:205px;"></s:textfield></td>
										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.supervisor" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><sj:autocompleter list="#session['supervisorMap']"
													name="supervisorId" listKey="key" listValue="value"
													theme="simple" id="supervisorNameId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.backfilloff" />:</td>
											<td><sj:autocompleter
													list="#session['userToBackFillMap']" name="backfilloffId"
													listKey="key" listValue="value" theme="simple"
													id="backfilloffNameId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.doj" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strDoj" value="%{doj}" changeYear="true"
													buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" cssStyle="width:205px;" /></td>

											<td class="ptslabel" align="right"><s:text
													name="pts.user.dateofbilling" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strDobilling" value="%{dobilling}" changeYear="true"
													buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" cssStyle="width:205px;" /></td>
										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.resignation.date" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strResignDate" value="%{resignDate}"
													changeYear="true" buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" cssStyle="width:205px;" /></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.release.date" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strReleaseDate" value="%{releaseDate}"
													changeYear="true" buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" cssStyle="width:205px;" /></td>
										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.location" />:</td>
											<td><sj:autocompleter list="locationMap" name="location"
													listKey="key" listValue="value" theme="simple"
													id="locationId" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.linemanager" />:</td>
											<td align="left"><sj:autocompleter
													list="#session['lineManagerMap']" name="lineManagerId"
													listKey="key" listValue="value" theme="simple"
													id="lineManagerId" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>

										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.eriproj.no" />:</td>
											<td><s:textfield name="eriProjNo" id="eriProjNo"
													theme="simple" cssStyle="width:205px;"></s:textfield></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.type" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><sj:autocompleter list="#session['userTypesMap']"
													name="userTypeId" listKey="key" listValue="value"
													theme="simple" id="userTypeId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
										</tr>

										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.job.stage" />:</td>
											<td><sj:autocompleter list="jobStageList"
													name="jobStage" theme="simple" id="jobStageId"
													selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.job.description" />:</td>
											<td><sj:autocompleter list="jobDescriptionMap"
													name="jobDescription" listKey="key" listValue="value"
													theme="simple" id="jobDescriptionId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
										</tr>

										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.stream" />:</td>
											<td><sj:autocompleter list="#session['streamsMap']"
													name="streamId" listKey="key" listValue="value"
													theme="simple" id="streamId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.role" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><sj:autocompleter list="#session['rolesMap']"
													name="roleId" listKey="key" listValue="value"
													theme="simple" id="roleId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.privilege" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><sj:autocompleter list="#session['privilegesMap']"
													name="privilegeId" listKey="key" listValue="value"
													theme="simple" id="privilegeId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.status" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><sj:autocompleter list="statusMap" name="status"
													listKey="key" listValue="value" theme="simple"
													id="statusId" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>

										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.mhr.category" />:</td>
											<td><sj:autocompleter list="mhrCategoryMap"
													name="mhrCategory" listKey="key" listValue="value"
													theme="simple" id="mhrCategoryId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.phoneno" />:</td>
											<td><s:textfield name="phoneNo" id="phoneNo"
													theme="simple" cssStyle="width:205px;"></s:textfield></td>

										</tr>
									</table>
								</div>
								<div id="ttwo" style="background-color: #FFF;">
									<jsp:include page="accountSelection.jsp"></jsp:include>

								</div>
								<%-- <div id="tthree" style="background-color: #FFF;">

									<jsp:include page="userContribution.jsp"></jsp:include>

								</div> --%>
								<div id="tfour">

									<jsp:include page="stableTeams.jsp"></jsp:include>

								</div>
								<div id="tfive" style="background-color: #FFF;">
									<table style="width: 100%;" cellspacing="10">
										<tr>
											<td class="ptslabel" align="right" valign="bottom"><s:url
													var="compscoreurl" action="goCompetencyScore.action"
													escapeAmp="false">
													<s:param name='id' value='id' />
													<s:param name='userPillarId' value='' />
												</s:url> <sj:dialog id="compScoreDialog" href="%{compscoreurl}"
													title="Competency Score" autoOpen="false" height="200" />
												<sj:a openDialog="compScoreDialog"
													cssStyle="cursor: pointer;text-decoration: underline;"
													onclick="clearCompScoreErrorDialogDiv()">
													<s:text name="pts.user.comp.score" />
												</sj:a> :</td>
											<td valign="bottom"><s:textfield name="competencyScore"
													id="competencyScore" theme="simple" cssStyle="width:173px;"
													disabled="true"></s:textfield></td>
										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.primary.skill" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><s:select list="#session['technologiesMap']"
													id="primarySkillId" name="primarySkillId" theme="simple"
													cssStyle="width:175px;" headerKey="-1"
													headerValue="Please Select"
													onchange="removeOption(this.value, 'primarySkillId', 'selectedTechnologiesId')"></s:select></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.secondary.skills" />:</td>
											<td><s:select list="#session['technologiesMap']"
													id="selectedTechnologiesId" name="selectedTechnologies"
													theme="simple" multiple="true"
													cssStyle="width:175px;height:75px;"></s:select></td>
											<td class="ptslabel" align="right"><s:url
													var="remoteurl" action="goTechScore.action"
													escapeAmp="false">
													<s:param name='id' value='id' />
													<s:param name='skillId' value='primarySkillId' />
												</s:url> <sj:dialog id="techScoreDialog" href="%{remoteurl}"
													title="Tech. Score" autoOpen="false" height="190" /> <sj:a
													openDialog="techScoreDialog"
													cssStyle="cursor: pointer;text-decoration: underline;"
													onclick="clearTechScoreErrorDialogDiv()">
													<s:text name="pts.user.tech.score" />
												</sj:a> :</td>
											<td><s:textfield name="techScore" id="techScore"
													theme="simple" cssStyle="width:173px;" disabled="true"></s:textfield></td>

										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.certificate" />:</td>
											<td><s:textarea name="certifications"
													id="certificatesId" theme="simple" rows="5"></s:textarea></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.comments" />:</td>
											<td><s:textarea name="comments" id="commentsId"
													theme="simple" rows="5"></s:textarea></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.release.comments" />:</td>
											<td><s:textarea name="releaseComments"
													id="releaseCommentsId" theme="simple" rows="5"></s:textarea></td>
										</tr>
									</table>

								</div>
							</sj:tabbedpanel></td>
					</tr>
				</table>
			</DIV>
			<div>
				<table style="width: 100%;">
					<tr>
						<!--td style="padding-left: 5px; text-align: left; display: none;"
							id="previd"><sj:a href="#" onClickTopics="movetoprevdiv"
								button="true">Prev</sj:a></td-->
						<td style="padding-left: 5px; text-align: left;" id="cancelid"><s:a
								href="../user/manageUser.action">
								<input class="ui-state-default" type="button" value="Cancel" />
							</s:a></td>
						<!-- td style="padding-right: 5px; text-align: right;" id="nextid"><sj:a
								href="#" onClickTopics="movetonextdiv" button="true">Next</sj:a></td-->
						<td style="padding-right: 5px; text-align: right;" id="saveid"><input
							class="ui-state-default" type="button"
							onclick="validateUserForm()" value="Save" /></td>
					</tr>
				</table>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
		<script>
			removeOption(document.getElementById('primarySkillId').value,
					'primarySkillId', 'selectedTechnologiesId');
			removeOption(document.getElementById('primaryAppId').value,
					'primaryAppId', 'selectedAppsId')
		</script>
	</s:form>