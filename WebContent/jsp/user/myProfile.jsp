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
	<s:form id="myProfile" method="POST"
		action="../user/saveMyProfile.action" enctype="multipart/form-data"
		theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent"
			style="width: 100%;  height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.my.profile" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.my.profile" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div style="width: 100%;">
					<s:hidden name="id" id="userId" />
					<s:div cssStyle="margin: 0px auto;" id="errorDiv" theme="simple">
						<table style="margin: 0 auto;">
							<tr>
								<td align="center"><s:fielderror theme="simple"
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
									<table>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.signum" />:</td>
											<td><s:property value="userName" /><s:hidden name="userName" id="userName"/></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.name" />*:</td>
											<td><s:textfield name="name" id="name"
													theme="simple"></s:textfield></td>
										</tr>
										<!-- <tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.firstname" />:</td>
											<td><s:textfield name="firstName" id="firstName"
													theme="simple"></s:textfield></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.middlename" />:</td>
											<td><s:textfield name="middleName" id="middleName"
													theme="simple"></s:textfield></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.lastname" />:</td>
											<td><s:textfield name="lastName" id="lastName"
													theme="simple"></s:textfield></td>
										</tr> -->
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.vzid" />:</td>
											<td><s:textfield name="vzid" id="vzid"
													theme="simple"></s:textfield></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.email" />*:</td>
											<td><s:textfield name="email" id="email" theme="simple"></s:textfield></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.phoneno" />:</td>
											<td><s:textfield name="phoneNo" id="phoneNo"
													theme="simple"></s:textfield></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.personal.no" />:</td>
											<td><s:textfield name="personalNo" id="personalNo"
													theme="simple"></s:textfield></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.mobile" />:</td>
											<td><s:textfield name="mobileNo" id="mobileNo"
													theme="simple"></s:textfield></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.role" />:</td>
											<td><s:property value="role" /><s:hidden name="role" id="role"/></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.stream" />:</td>
											<td><s:property value="stream" /><s:hidden name="stream" id="streamId"/></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.privilege" />:</td>
											<td><s:property value="privilege" /><s:hidden name="privilege" id="privilege"/></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.type" />:</td>
											<td><s:property value="userType" /><s:hidden name="userType" id="userType"/></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.supervisor" />:</td>
											<td><s:property value="supervisorName" /><s:hidden name="supervisorName" id="supervisorName"/></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.primary.skill" />:</td>
											<td><s:hidden name="primarySkillId" id="primarySkillId"/><s:select list="#session['technologiesMap']"
													id="primarySkillId123" name="primarySkillId123" theme="simple" disabled="true" value="primarySkillId"
													cssClass="multiselect1" cssStyle="width:175px;" headerKey="-1" headerValue="Please Select"
													></s:select></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.skills" />:</td>
											<td><s:select list="#session['technologiesMap']"
													id="selectedTechnologiesId" name="selectedTechnologies"
													theme="simple" multiple="true" cssClass="multiselect1"
													cssStyle="width:175px;height:75px;"></s:select></td>
										</tr>
										<tr>
											<td style="color: #2779aa;" align="right"><s:text
													name="pts.user.photo" />:</td>
											<td><s:file name="photoUpload" theme="simple"></s:file>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div style="padding-right: 5px; text-align: right;">
					<sj:submit src="../images/save.png" type="image" value="Save" />
				</div>
				<div id="spacer10px">&nbsp;</div>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
		<script>
			removeOption(document.getElementById('primarySkillId').value,
					'primarySkillId123', 'selectedTechnologiesId');
		</script>
	</s:form>