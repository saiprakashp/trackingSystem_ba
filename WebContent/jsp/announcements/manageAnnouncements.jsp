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
<script type="text/javaScript"
	src="<%=request.getContextPath()%>/struts/js/base/jqurey.ui.datepicker.js"></script>
<script>
	datePick = function(element) {
		$(element).datepicker();
	};
</script>
</head>
<body>
	<s:form theme="simple" method="POST" id="announcementsFormId">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="id" id="announcementId" />
		<div class='container-fluid main' style='padding»top: 1%;'>
			<div class="row">
				<div class='col text-left'>
					<h5>Manage Announcements</h5>
				</div>
			</div>
			<div class='container-fluid'>
				<table class="table table-borderless text-center">
					<tr>
						<td style="color: #2779aa;">Type:</td>
						<td><s:textfield name="searchType" id="searchType"
								theme="simple" cssStyle="width:130px;" /></td>
						<td style="color: #2779aa;">Subject:</td>
						<td><s:textfield name="searchSubject" id="searchSubject"
								theme="simple" cssStyle="width:130px;" /></td>
						<td style="color: #2779aa;">Announced By:</td>
						<td><s:textfield name="searchAnnouncedBy"
								id="searchAnnouncedBy" theme="simple" cssStyle="width:130px;" /></td>
						<td align="center"><s:submit theme="simple"
								src="../images/go.png" type="image"
								onclick="submitForm('../announcements/manageAnnouncements.action')" /></td>
					</tr>
				</table>
			</div>
			<div class='container-fluid'><jsp:include
					page="announcementsGrid.jsp" />
			</div>
		</div>
		<div class='container-fluid'>
			<s:include value="/jsp/footer.jsp"></s:include>
		</div>
	</s:form>


</body>
</html>