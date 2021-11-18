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
<style>
body {
	font-family: Arial;
}
.s2j-combobox-input{
	    width: 138px !important;
}
/* Style the tab */
.resourceTab {
	overflow: hidden;
	border: 1px solid #ccc;
	background-color: #f1f1f1;
}

.achBtn {
	display: block;
	width: 42px;
	height: 16px;
	background: #0082f0;
	padding: 10px;
	text-align: center;
	border-radius: 5px;
	color: white;
	font-weight: bold;
}

/* Style the buttons inside the tab */
.resourceTab button {
	background-color: inherit;
	float: left;
	border: none;
	outline: none;
	cursor: pointer;
	padding: 14px 16px;
	transition: 0.3s;
	font-size: 17px;
}

/* Change background color of buttons on hover */
.resourceTab button:hover {
	background-color: #ddd;
}

/* Create an active/current tablink class */
.resourceTab button.active {
	background-color: #ccc;
}

/* Style the tab content */
</style>
</head>
<body>

	<jsp:include page="../header.jsp" />
	<jsp:include page="../menu.jsp" />
	<s:hidden name="searchSupervisor" id="searchSupervisor" />
	<s:hidden name="searchStatus" id="searchStatus" />
	<s:hidden name="searchLocation" id="searchLocation" />
	<div id="breadcrumbDivParent"
		style="width: 100%;  height: 22px;">
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
				<div>
					<table style="width: 100%;">
						<tr>
							<td align="center">
								<div class="resourceTab">
									<button class="resourcelinks tablinks User" disable
										onclick="openTabs(event, 'User',true)" id="defaultOpen">User</button>
									<button class="tablinks Domain" disable
										onclick="openTabs(event, 'Domain',false)">Domain</button>
									<button class="tablinks Skillset" disable
										onclick="openTabs(event, 'Skillset',false)">Skillset</button>
								</div>
								<table cellspacing="10">

									<s:form id="myProfile" method="POST"
										action="../user/addUser.action" theme="simple">


										<div id="User" class="resourceContent">
											<table id="UserTab">
												<tr>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.vzid" /><span
														style="color: RED; font-size: 15px; font-weight: bold;"></span>:</td>
													<td><s:textfield name="vzid" id="vzid" theme="simple"
															cssStyle="width:173px;"></s:textfield></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.email" />:</td>
													<td><s:textfield name="email" id="email"
															theme="simple" cssStyle="width:173px;"></s:textfield></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.supervisor" /><span
														style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
													<td><sj:autocompleter list="#session['supervisorMap']"
															name="supervisorId" listKey="key" listValue="value"
															theme="simple" id="supervisorNameId" selectBox="true"
															selectBoxIcon="true"></sj:autocompleter></td>
												</tr>
												<tr>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.backfilloff" />:</td>
													<td><sj:autocompleter
															list="#session['userToBackFillMap']" name="backfilloffId"
															listKey="key" listValue="value" theme="simple"
															id="backfilloffNameId" selectBox="true"
															selectBoxIcon="true"></sj:autocompleter></td>
													<td class="ptslabel" align="right">&nbsp;&nbsp;<s:text
															name="pts.user.doj" />:
													</td>
													<td><sj:datepicker displayFormat="mm/dd/yy"
															name="strDoj" value="%{doj}" changeYear="true"
															buttonImage="../images/DatePicker.png"
															buttonImageOnly="true" /></td>

													<td class="ptslabel" align="right"><s:text
															name="pts.user.dateofbilling" />:</td>
													<td><sj:datepicker displayFormat="mm/dd/yy"
															name="strDobilling" value="%{dobilling}"
															changeYear="true" buttonImage="../images/DatePicker.png"
															buttonImageOnly="true" /></td>
												</tr>
												<tr>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.location" />:</td>
													<td><sj:autocompleter list="	" name="location"
															listKey="key" listValue="value" theme="simple"
															id="locationId" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.allocation" />:</td>
													<td><sj:autocompleter list="allocationoMap"
															name="allocation" listKey="key" listValue="value"
															theme="simple" id="allocation" selectBox="true"
															selectBoxIcon="true"></sj:autocompleter></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.eriproj.no" />:</td>
													<td><s:textfield name="eriProjNo" id="eriProjNo"
															theme="simple" cssStyle="width:173px;"></s:textfield></td>
												</tr>

												<tr>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.type" /><span
														style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
													<td><sj:autocompleter list="#session['userTypesMap']"
															name="userTypeId" listKey="key" listValue="value"
															theme="simple" id="userTypeId" selectBox="true"
															selectBoxIcon="true"></sj:autocompleter></td>
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
															name="pts.user.personal.no" />:</td>
													<td><s:textfield name="personalNo" id="personalNo"
															theme="simple" cssStyle="width:173px;"></s:textfield></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.mobile" />:</td>
													<td><s:textfield name="mobileNo" id="mobileNo"
															theme="simple" cssStyle="width:173px;"></s:textfield></td>
												</tr>

												<tr>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.stream" />:</td>
													<td><sj:autocompleter list="#session['streamsMap']"
															name="streamId" listKey="key" listValue="value"
															theme="simple" id="streamId" selectBox="true"
															selectBoxIcon="true"></sj:autocompleter></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.role" />:</td>
													<td><sj:autocompleter list="#session['rolesMap']"
															name="roleId" listKey="key" listValue="value"
															theme="simple" id="roleId" selectBox="true"
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
															name="pts.user.privilege" />:</td>
													<td><sj:autocompleter list="#session['privilegesMap']"
															name="privilegeId" listKey="key" listValue="value"
															theme="simple" id="privilegeId" selectBox="true"
															selectBoxIcon="true"></sj:autocompleter></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.mhr.category" />:</td>
													<td><sj:autocompleter list="mhrCategoryMap"
															name="mhrCategory" listKey="key" listValue="value"
															theme="simple" id="mhrCategoryId" selectBox="true"
															selectBoxIcon="true"></sj:autocompleter></td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
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
													<td>&nbsp;</td>
													<td>&nbsp;</td>
												</tr>
												<tr>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td><a class="achBtn"
														onclick="openTabs(event, 'Domain',true)">Next</a></td>
												</tr>
											</table>
										</div>

										<div id="Domain" class="resourceContent">
											<table id="DomainTab">
												<tr>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.platform" />:</td>
													<td><s:select list="#session['platformsMap']"
															name="selectedPlatforms" theme="simple" multiple="true"
															cssClass="multiselect1"
															cssStyle="width:175px;height:75px;"></s:select></td>
													<td colspan="4" valign="top" align="right">
														<table>
															<tr>
																<td colspan="3">
																	<div style="float: left;">
																		<label
																			style="display: block; font-size: 15px; font-weight: bold;">
																			Contribution and Domain[Score] </label>
																	</div>
																</td>
															</tr>
															<tr>
																<td>
																	<div style="float: left;">
																		<label for="Conf" style="display: block;"
																			class="ptslabel">Conf</label>
																		<s:textfield id="confContribution"
																			name="confContribution" style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="IPCOMMS" style="display: block;"
																			class="ptslabel">IPCOMMS</label>
																		<s:textfield id="voipContribution"
																			name="voipContribution" style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="IPCOMMS-NSRS" style="display: block;"
																			class="ptslabel">IPCOMMS-NSRS</label>
																		<s:textfield id="nsrsContribution"
																			name="nsrsContribution" style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="CX" style="display: block;"
																			class="ptslabel">EMDA</label>
																		<s:textfield id="emdaContribution"
																			name="emdaContribution" style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="OneTalk" style="display: block;"
																			class="ptslabel">OneTalk</label>
																		<s:textfield id="cmproxyContribution"
																			name="cmproxyContribution" style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">Admin</label>
																		<s:textfield id="adminContribution"
																			name="adminContribution" style="width:70px;" />
																	</div>
																</td>
															<tr>
																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">Kits</label>
																		<s:textfield id="KitsContribution"
																			name="KitsContribution" style="width:70px;" />
																	</div>
																</td>

																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">SE</label>
																		<s:textfield id="seContribution" name="seContribution"
																			style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">DEV</label>
																		<s:textfield id="devContribution"
																			name="devContribution" style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">SIT</label>
																		<s:textfield id="sitContribution"
																			name="sitContribution" style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">PM</label>
																		<s:textfield id="pmContribution" name="pmContribution"
																			style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">4th Level Support</label>
																		<s:textfield id="fourlvsContribution"
																			name="fourlvsContribution" style="width:70px;" />
																	</div>
																</td>
															</tr>
															<tr>
																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">PTL</label>
																		<s:textfield id="ptlContribution"
																			name="ptlContribution" style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">Hardware</label>
																		<s:textfield id="hardwareContribution"
																			name="hardwareContribution" style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">SysAdmin</label>
																		<s:textfield id="sysAdminContribution"
																			name="sysAdminContribution" style="width:70px;" />
																	</div>
																</td>
																<td>
																	<div style="float: left;">
																		<label for="Admin" style="display: block;"
																			class="ptslabel">Database</label>
																		<s:textfield id="databaseContribution"
																			name="databaseContribution" style="width:70px;" />
																	</div>
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<tr>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.primary.app" />:</td>
													<td><s:select list="#session['projectsMap']"
															id="primaryAppId" name="primaryProjectId" theme="simple"
															cssClass="multiselect1" cssStyle="width:175px;"
															headerKey="-1" headerValue="Please Select"
															onchange="removeOption(this.value, 'primaryAppId', 'selectedAppsId')"></s:select></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.comp.score" />:</td>
													<td><s:textfield name="competencyScore"
															id="competencyScore" theme="simple"
															cssStyle="width:173px;"></s:textfield></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.secondary.apps" />:</td>
													<td><s:select list="#session['projectsMap']"
															id="selectedAppsId" name="selectedProjects"
															theme="simple" multiple="true" cssClass="multiselect1"
															cssStyle="width:175px;height:75px;"></s:select></td>

												</tr>
												<tr>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td><a class="achBtn"
														onclick="openTabs(event, 'User',true)">Back</a></td>
													<td><a class="achBtn"
														onclick="openTabs(event, 'Skillset',true)">Next</a></td>
												</tr>
											</table>
										</div>
										<div id="Skillset" class="resourceContent">
											<table id="SkillsetTab">
												<tr>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.primary.skill" />:</td>
													<td><s:select list="#session['technologiesMap']"
															id="primarySkillId" name="primarySkillId" theme="simple"
															cssClass="multiselect1" cssStyle="width:175px;"
															headerKey="-1" headerValue="Please Select"
															onchange="removeOption(this.value, 'primarySkillId', 'selectedTechnologiesId')"></s:select></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.tech.score" />:</td>
													<td><s:textfield name="techScore" id="techScore"
															theme="simple" cssStyle="width:173px;"></s:textfield></td>
													<td class="ptslabel" align="right"><s:text
															name="pts.user.secondary.skills" />:</td>
													<td><s:select list="#session['technologiesMap']"
															id="selectedTechnologiesId" name="selectedTechnologies"
															theme="simple" multiple="true" cssClass="multiselect1"
															cssStyle="width:175px;height:75px;"></s:select></td>

												</tr>

												<tr>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td><a class="achBtn"
														onclick="openTabs(event, 'Domain',true)">Back</a></td>
													<td><a class="achBtn"
														onClick=" javascript:$('#myProfile').submit();"
														id="userSaveBtn">Save</a></td>
												</tr>
											</table>
										</div>
									</s:form>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</DIV>
	</div>



	<script>
		function openTabs(evt, tagName, flag) {
			if (flag) {
				var i, tabcontent, tablinks;
				tabcontent = document.getElementsByClassName("resourceContent");
				for (i = 0; i < tabcontent.length; i++) {
					tabcontent[i].style.display = "none";
				}
				tablinks = document.getElementsByClassName("resourcelinks");
				for (i = 0; i < tablinks.length; i++) {

					tablinks[i].className = tablinks[i].className.replace(
							"active", "");
				}
				document.getElementById(tagName).style.display = "block";
				if (tagName == 'Skillset') {
					$("#UserTab").hide();
					$("#DomainTab").hide();
					$("#SkillsetTab").show();
					$(".User").removeClass("active");
					$(".Domain").removeClass("active");
				}
				if (tagName == 'User') {
					$("#SkillsetTab").hide();
					$("#DomainTab").hide();

					$("#UserTab").show();
					$(".Skillset").removeClass("active");
					$(".Domain").removeClass("active");
				}
				if (tagName == 'Domain') {
					$("#UserTab").hide();
					$("#DomainTab").show();
					$("#SkillsetTab").hide();
					$(".User").removeClass("active");
					$(".Domain").removeClass("active");
				}

				$("." + tagName).addClass("active");
			} else {
				return;
			}
		}

		$('#myProfile_selectedPlatforms').find('option').each(function() {
			$(this).attr('selected', 'selected');
		});
		// Get the element with id="defaultOpen" and click on it
		document.getElementById("defaultOpen").click();

		$("#SkillsetTab").hide();
		$("#DomainTab").hide();
	</script>
</body>
</html>
