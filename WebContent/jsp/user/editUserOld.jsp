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
</head>
<body>
	<s:form id="myProfile" method="POST" action="../user/modifyUser.action"
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
					<div>
						<table style="width: 100%;">
							<tr>
								<td align="center">
									<table cellspacing="10">
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.name" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td colspan="3"><s:textfield name="name" id="name"
													theme="simple" cssStyle="width:310px;"></s:textfield></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.signum" /><span
												style="color: RED; font-size: 15px; font-weight: bold;">*</span>:</td>
											<td><s:textfield name="userName" id="userName"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>
										</tr>
										<!-- tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.firstname" /><span style="color: RED;font-size: 15px;font-weight: bold;">*</span>:</td>
											<td><s:textfield name="firstName" id="firstName"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>

											<td class="ptslabel" align="right"><s:text
													name="pts.user.middlename" />:</td>
											<td><s:textfield name="middleName" id="middleName"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>

											<td class="ptslabel" align="right"><s:text
													name="pts.user.lastname" /><span style="color: RED;font-size: 15px;font-weight: bold;">*</span>:</td>
											<td><s:textfield name="lastName" id="lastName"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>
										</tr-->
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.vzid" /><span
												style="color: RED; font-size: 15px; font-weight: bold;"></span>:</td>
											<td><s:textfield name="vzid" id="vzid" theme="simple"
													cssStyle="width:173px;"></s:textfield></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.email" />:</td>
											<td><s:textfield name="email" id="email" theme="simple"
													cssStyle="width:173px;"></s:textfield></td>
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
													list="#session['userToBackFillMap']" name="backfilloff"
													listKey="key" listValue="value" theme="simple"
													id="backfilloffNameId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.doj" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strDoj" value="%{doj}" changeYear="true"
													buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" /></td>

											<td class="ptslabel" align="right"><s:text
													name="pts.user.dateofbilling" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strDobilling" value="%{dobilling}" changeYear="true"
													buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" /></td>
										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.location" />:</td>
											<td><sj:autocompleter list="locationMap" name="location"
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
													name="pts.user.phoneno" />:</td>
											<td><s:textfield name="phoneNo" id="phoneNo"
													theme="simple" cssStyle="width:173px;"></s:textfield></td>
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
													name="pts.user.resignation.date" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strResignDate" value="%{resignDate}"
													changeYear="true" buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" /></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.release.date" />:</td>
											<td><sj:datepicker displayFormat="mm/dd/yy"
													name="strReleaseDate" value="%{releaseDate}"
													changeYear="true" buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" /></td>

										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.mhr.category" />:</td>
											<td><sj:autocompleter list="mhrCategoryMap"
													name="mhrCategory" listKey="key" listValue="value"
													theme="simple" id="mhrCategoryId" selectBox="true"
													selectBoxIcon="true"></sj:autocompleter></td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
										<tr>

											<td class="ptslabel" align="right"><s:text
													name="pts.user.platform" />:</td>
											<td valign="bottom" ><s:select list="#session['platformsMap']"
													name="selectedPlatforms" theme="simple" multiple="true"
													cssClass="multiselect1" cssStyle="width:175px;height:75px;"></s:select></td>
											<td class="ptslabel" align="right" valign="bottom" ><s:url
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
											<td valign="bottom" ><s:textfield name="competencyScore"
													id="competencyScore" theme="simple" cssStyle="width:173px;"
													disabled="true"></s:textfield></td>
											<td colspan="2" valign="bottom" align="right">
												<table>
													<tr>
														<td colspan="6" nowrap="nowrap">
															<div style="float: left;">
																<label
																	style="display: block; font-size: 15px; font-weight: bold;">
																	Contribution </label>
															</div>
														</td>
													</tr>
													<tr>
														<td>
															<div style="float: left;">
																<label for="CX" style="display: block;" class="ptslabel">CX</label>
																<s:textfield id="cxContribution" name="cxContribution"
																	style="width:50px;" />
															</div>
														</td>
														<td>
															<div style="float: left;">
																<label for="Conf" style="display: block;"
																	class="ptslabel">Conf</label>
																<s:textfield id="confContribution"
																	name="confContribution" style="width:50px;" />
															</div>
														</td>
														<td>
															<div style="float: left;">
																<label for="IPCOMMS" style="display: block;"
																	class="ptslabel">IPCOMMS</label>
																<s:textfield id="voipContribution"
																	name="voipContribution" style="width:50px;" />
															</div>
														</td>
														<td>
															<div style="float: left;">
																<label for="IPCOMMS-NSRS" style="display: block;"
																	class="ptslabel">IPCOMMS-NSRS</label>
																<s:textfield id="nsrsContribution"
																	name="nsrsContribution" style="width:50px;" />
															</div>
														</td>
														<td>
															<div style="float: left;">
																<label for="CX" style="display: block;" class="ptslabel">EMDA</label>
																<s:textfield id="emdaContribution"
																	name="emdaContribution" style="width:50px;" />
															</div>
														</td>
														<td>
															<div style="float: left;">
																<label for="OneTalk" style="display: block;"
																	class="ptslabel">OneTalk</label>
																<s:textfield id="cmproxyContribution"
																	name="cmproxyContribution" style="width:50px;" />
															</div>
														</td>
														<td>
															<div style="float: left;">
																<label for="Admin" style="display: block;"
																	class="ptslabel">Admin</label>
																<s:textfield id="adminContribution"
																	name="adminContribution" style="width:50px;" />
															</div>
														</td>
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
													name="pts.user.secondary.apps" />:</td>
											<td><s:select list="#session['projectsMap']"
													id="selectedAppsId" name="selectedProjects" theme="simple"
													multiple="true" cssClass="multiselect1"
													cssStyle="width:175px;height:75px;"></s:select></td>
											<td class="ptslabel" align="right">&nbsp;</td>
											<td>&nbsp;</td>

										</tr>
										<tr>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.primary.skill" />:</td>
											<td><s:select list="#session['technologiesMap']"
													id="primarySkillId" name="primarySkillId" theme="simple"
													cssClass="multiselect1" cssStyle="width:175px;"
													headerKey="-1" headerValue="Please Select"
													onchange="removeOption(this.value, 'primarySkillId', 'selectedTechnologiesId')"></s:select></td>
											<td class="ptslabel" align="right"><s:text
													name="pts.user.secondary.skills" />:</td>
											<td><s:select list="#session['technologiesMap']"
													id="selectedTechnologiesId" name="selectedTechnologies"
													theme="simple" multiple="true" cssClass="multiselect1"
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
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td style="padding-left: 5px; text-align: left;"><s:a
								href="../user/manageUser.action">
								<img src="../images/cancel.png" title="Cancel" />
							</s:a></td>
						<td style="padding-right: 5px; text-align: right;"><sj:submit
								src="../images/save.png" type="image" value="Save" /></td>
					</tr>
				</table>
				<div id="spacer10px">&nbsp;</div>
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