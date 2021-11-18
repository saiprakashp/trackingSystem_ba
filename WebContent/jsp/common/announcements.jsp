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
	<s:form id="announcementFormId" method="POST" theme="simple" action="../projects/saveAnnouncement.action">
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
					<s:text name="pts.menu.announcements" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.announcements" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="errorDiv">
					<table style="margin: 0 auto;">
						<tr>
							<td align="center"><s:fielderror theme="simple" cssStyle="margin-left:10px;color: RED" /><s:actionerror
									theme="simple" cssStyle="margin-left:10px;color: RED" /> <s:actionmessage
									theme="simple" cssStyle="margin-left:10px;color: RED" /></td>
						</tr>
					</table>
				</div>
				<div>
					<table style="width: 100%;">
						<tr>
							<td align="center">
								<table>
									<tr>
										<td align="right" style="color: #2779aa;"><s:text
												name="pts.announcement.type" />*:</td>
										<td><s:textfield name="announcementType" id="announcementType"
												theme="simple" cssStyle="width:180px;"></s:textfield></td>
									</tr>
									<tr>
										<td align="right" style="color: #2779aa;"><s:text
												name="pts.announcement.subject" />*:</td>
										<td><s:textarea name="announcementSubject" id="announcementSubject"
												theme="simple" rows="2"></s:textarea></td>
									</tr>
									<tr>
										<td align="right" style="color: #2779aa;"><s:text
												name="pts.announcement.by" />*:</td>
										<td><s:textfield name="announcementBy" id="announcementBy"
												theme="simple" cssStyle="width:180px;"></s:textfield></td>
									</tr>
									<tr>
										<td align="right" style="color: #2779aa;"><s:text
												name="pts.announcement.date" />*:</td>
										<td><sj:datepicker displayFormat="mm/dd/yy"
													name="announcedDate" value="%{announcementDate}" changeYear="true"
													buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" cssStyle="width:180px;"/></td>
									</tr>
									<tr>
										<td align="right" style="color: #2779aa;"><s:text
												name="pts.announcement.expiry.date" />*:</td>
										<td><sj:datepicker displayFormat="mm/dd/yy"
													name="expiresOn" value="%{expiryDate}" changeYear="true"
													buttonImage="../images/DatePicker.png"
													buttonImageOnly="true" cssStyle="width:180px;"/></td>
									</tr>
									<tr>
										<td align="right" style="color: #2779aa;"><s:text
												name="pts.announcement.description" />*:</td>
										<td><s:textarea name="announcementDesc" id="announcementDescId"
													theme="simple" rows="5"  cssStyle="width:250px;"></s:textarea></td>
									</tr>
									
								</table>
							</td>
						</tr>
					</table>
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
	</s:form>
</body>
</html>