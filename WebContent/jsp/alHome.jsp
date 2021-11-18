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
		$('#detailSubRes1').val(true);
		if ($("#employeeId_widget").val() === '-1') {
			$('#detailSubRes1').val(false);
		}
		setTimeout("getDashboardUtilizationHrefId()", 100);
	});
</script>
<style type="text/css">
#contentarea {
	width: 1569px;
}

#allmargin {
	width: 1200px;
}

#allbreadcrumbDiv {
	width: 1200px !important;
	background: white !important;
}

@media screen and (max-width: 750px) {
	#contentarea {
		width: 1120px !important;
	}
	#allmargin {
		width: 400px !important;
	}
	#allbreadcrumbDiv {
		width: 400px !important;
		background: white !important;
	}
	#breadcrumbDivParent {
		width: 400px !important;
	}
}
</style>
</head>
<body>
	<!-- onload="nceffortReport()" -->
	<s:form theme="simple" method="POST" id="homeFormId" name="homeFormId">
		<jsp:include page="header.jsp" />
		<jsp:include page="menu.jsp" />
		<div id="breadcrumbDivParent"
			style="width: 1200px !important; background: white; margin: auto; height: 22px;">
			<div id="allbreadcrumbDiv"
				style="margin: 0 auto; width: 1569px !important; background: white; height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:text name="pts.menu.home" />
				</div>
			</div>
		</div>
		<div id="contentarea" style="width: 1200px !important;">
			<DIV id='allmargin' style="margin: 0 auto; width: 1200px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">&nbsp;</s:div>
				</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="dashboard.jsp" /></td>
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