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
	var stable_voip = false;
	var stable_vcb = false;

	var stable_nsrs_mod = false;
	var stable_nsrs_core = false;

	var stable_iptf = false;
	var stable_ipivr_capacity = false;

	var stable_ipivr_applications = false;
	var stable_incp_sightline = false;

	var stable_hicr = false;
	var stable_emds_danProxy = false;

	var stable_cx_backOffice = false;
	var stable_conferencing = false;

	var stable_cmProxy = false;
	var stable_innovation = false;

	$.subscribe('tabchange', function(event, data) {

		var tab = event.originalEvent.ui.newTab.attr("id");

		if (tab === 'tab2') {
			if (document.getElementById("hiddenSupervisorId").value != document
					.getElementById("supervisorNameId_widget").value) {
				document.getElementById("ajaxGetSupervisorAccounts").click();
			}
		} else if (tab === 'tab3') {
			$('select.multiselect2 option').each(function() {
				this.selected = true;
			});
			document.getElementById("ajaxGetUserContributionId").click();
		} else if (tab === 'tab4') {
			document.getElementById("ajaxGetStableTeamId").click();
		}

	});

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
		var status = checkUserContribution();
		var status1 = checkStableTeamsContribution();
		if (status === true && status1 === true) {
			document.forms['createUserForm'].submit();
		} else {
			return false;
		}

		return false;
	}
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
<body>
	<s:form id="createUserForm" method="POST"
		onsubmit="checkUserContribution()" action="../user/addUser.action"
		theme="simple">
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
					<s:text name="pts.user.create" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.user.create" />
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

			<sj:a href="ajaxGetSupervisorAccounts" id="ajaxGetSupervisorAccounts"
				targets="ttwo" formIds="createUserForm"></sj:a>
		<%-- 	<sj:a href="ajaxGetUserContribution" id="ajaxGetUserContributionId"
				targets="tthree" formIds="createUserForm"></sj:a> --%>

			<DIV style="margin: 0 auto; width: 980px;">
				<table style="width: 99%;">
					<tr>
						<td align="center"><sj:tabbedpanel id="localtabs"
								cssClass="list" cssStyle="width:100%" onChangeTopics="tabchange">
								<sj:tab id="tab1" target="tone" label="Personal Information" />
								<sj:tab id="tab2" target="ttwo" label="Accounts" />
								<%-- <sj:tab id="tab3" target="tthree" label="Contribution" /> --%>
								<sj:tab id="tab4" target="tfour" label="Stable Team" />
								<sj:tab id="tab5" target="tfive" label="Skills" />
								<div id="tone">
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
													name="pts.user.location" />:</td>
											<td><sj:autocompleter list="locationMap" name="location"
													listKey="key" listValue="value" theme="simple"
													id="locationId" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
											<%-- <td class="ptslabel" align="right"><s:text
													name="pts.user.allocation" />:</td>
											<td><sj:autocompleter list="allocationoMap"
													name="allocation" listKey="key" listValue="value"
													theme="simple" id="allocation" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
 --%>
											<td  class="ptslabel" align="right"><s:text
													name="pts.user.linemanager" />:</td>
											<td align="left"><sj:autocompleter list="lineManagerMap"
													name="lineManagerId" listKey="key" listValue="value"
													theme="simple" id="lineManagerId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>

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
								<div id="ttwo">

									<jsp:include page="accountSelection.jsp"></jsp:include>
								</div>
								<%-- <div id="tthree">

									<jsp:include page="userContribution.jsp"></jsp:include>

								</div> --%>
								<div id="tfour">

									<jsp:include page="stableTeams.jsp"></jsp:include>

								</div>
								<div id="tfive">
									<table style="width: 100%;" cellspacing="10">
										<tr>
											<td class="ptslabel" align="right"><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>
												<s:text name="pts.user.primary.skill" />:</td>
											<td><s:select list="#session['technologiesMap']"
													id="primarySkillId" name="primarySkillId" theme="simple"
													cssStyle="width:205px;" headerKey="-1"
													headerValue="Please Select"
													onchange="removeOption(this.value, 'primarySkillId', 'selectedTechnologiesId')"></s:select></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.tech.score" />:</td>
											<td><s:textfield name="techScore" id="techScore"
													theme="simple" cssStyle="width:205px;"></s:textfield></td>
										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.secondary.skills" />:</td>
											<td><s:select list="#session['technologiesMap']"
													id="selectedTechnologiesId" name="selectedTechnologies"
													theme="simple" multiple="true"
													cssStyle="width:205px;height:75px;"></s:select></td>
										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.certificate" />:</td>
											<td><s:textarea name="certifications"
													id="certificatesId" theme="simple" rows="5"
													cssStyle="width:205px;"></s:textarea></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.comments" />:</td>
											<td><s:textarea name="comments" id="commentsId"
													theme="simple" rows="5" cssStyle="width:205px;"></s:textarea></td>
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
						<!-- td style="padding-left: 5px; text-align: left; display: none;"
							id="previd"><sj:a href="#" onClickTopics="movetoprevdiv"
								button="true">Prev</sj:a></td-->
						<td style="padding-left: 5px; text-align: left;" id="cancelid"><s:a
								href="../user/manageUser.action">
								<img src="../images/cancel.png" title="Cancel" />
							</s:a></td>
						<!-- td style="padding-right: 5px; text-align: right;" id="nextid"><sj:a
								href="#" onClickTopics="movetonextdiv" button="true">Next</sj:a></td-->
						<td style="padding-right: 5px; text-align: right;" id="saveid"><sj:submit
								onclick="validateUserForm()" src="../images/save.png"
								type="image" value="Save" /></td>
					</tr>
				</table>
			</div>

		</div>
	</s:form>
</body>
</html>
