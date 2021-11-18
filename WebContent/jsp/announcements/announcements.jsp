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
	<s:form id="announcementFormId" method="POST" theme="simple"
		action="../announcements/saveAnnouncement.action">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="id" id="announcementId" theme="simple" />
		<s:hidden name="createdBy" id="createdBy" theme="simple" />
		<s:hidden name="createdDate" id="createdDate" theme="simple" />
		<div class='container-fluid main' style='padding-top: 1%;'>
			<div class="row">
				<div class='col'>
					<h3>Announcement</h3>
				</div></div>
			<div class="row">
				<div class='container-fluid paddL1' style="width: 100%;">
					<div class="row">
						<div id="errorDiv">
							<table style="margin: 0 auto;">
								<tr>
									<td align="center"><s:fielderror theme="simple"
											cssStyle="margin-left:10px;color: RED" /> <s:actionerror
											theme="simple" cssStyle="margin-left:10px;color: RED" /> <s:actionmessage
											theme="simple" cssStyle="margin-left:10px;color: RED" /></td>
								</tr>
							</table>
						</div>
					</div>
					<div class="row">
						<div class='container-fluid paddL1' style="width: 100%;">
							<div class="row">
								<div class='col ml-md-auto text-right'>
									<s:text name="pts.announcement.type" />
									*:
								</div>
								<div class='col ml-md-auto text-left'>
									<s:textfield name="announcementType" id="announcementType"
										theme="simple" cssStyle="width:180px;"></s:textfield>
								</div>
							</div>
							<div class="row">
								<div class='col ml-md-auto text-right'>
									<s:text name="pts.announcement.subject" />
									*:
								</div>
								<div class='col ml-md-auto text-Left'>
									<s:textarea name="announcementSubject" id="announcementSubject"
										theme="simple" rows="2" cssStyle="width:180px;"></s:textarea>
								</div>
							</div>
							<div class="row">
								<div class='col ml-md-auto text-right'>
									<s:text name="pts.announcement.by" />
									*:
								</div>
								<div class='col ml-md-auto text-Left'>
									<s:textfield name="announcementBy" id="announcementBy"
										theme="simple" cssStyle="width:180px;"></s:textfield>
								</div>
							</div>
							<div class="row">
								<div class='col ml-md-auto text-right'>
									<s:text name="pts.announcement.date" />
									*:
								</div>
								<div class='col ml-md-auto text-left'>
									<sj:datepicker displayFormat="mm/dd/3yy"
										name="announcementDate" value="%{announcedDate}"
										changeYear="true" buttonImage="../images/DatePicker.png"
										buttonImageOnly="true" cssStyle="width:180px;" />
								</div>
							</div>
							<div class="row">
								<div class='col ml-md-auto text-right'>
									<s:text name="pts.announcement.expiry.date" />
									*:
								</div>
								<div class='col ml-md-auto text-left'>
									<sj:datepicker displayFormat="mm/dd/yy" name="expiryDate"
										value="%{expirsOn}" changeYear="true"
										buttonImage="../images/DatePicker.png" buttonImageOnly="true"
										cssStyle="width:180px;" />
								</div>
							</div>
							<div class="row">
								<div class='col ml-md-auto text-right'>
									<s:text name="pts.announcement.description" />
									*:
								</div>
								<div class='col ml-md-auto text-left'>
									<s:textarea name="announcementDesc" id="announcementDescId"
										theme="simple" rows="5" cssStyle="width:180px;"></s:textarea>
								</div>
							</div>
						</div>
					</div>
					<div class="row" style='padding-top: 2%;'>
						<div class='col ml-md-auto text-left'>
							<s:a href="../announcements/manageAnnouncements.action">
								<img src="../images/cancel.png" title="Cancel" />
							</s:a>
						</div>
						<div class='col ml-md-auto text-right'>
							<sj:submit src="../images/save.png" type="image" value="Save" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class='container-fluid'>
			<jsp:include page="../footer.jsp"></jsp:include>
		</div>
	</s:form>
</body>
</html>
