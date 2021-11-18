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
	$.subscribe('/employeeSelect', function(event, data) {
		setTimeout("getDashboardUtilizationHrefId()", 100);
	});
	function myvalue(elem, operation, value) {
		if (operation === 'get') {
			return $(elem).val();
		} else if (operation === 'set') {
			$('select', elem).val(value);
		}
	}
</script>
</head>
<body>
	<!-- onload="nceffortReport()" -->
	<s:form theme="simple" method="POST" id="homeFormId" name="homeFormId">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:text name="pts.menu.home" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">&nbsp;</s:div>
				</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="manageHolidaysGrid.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>