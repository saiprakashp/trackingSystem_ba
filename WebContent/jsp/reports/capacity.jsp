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
	$.subscribe('/capacityStreamSelect', function(event, data) {
		setTimeout("getCapacityStreamImage()", 100);
	});
</script>
</head>
<body>
	<s:form id="capacityformId" method="POST" theme="simple"
		action="../reports/uploadCapacityImage.action" enctype="multipart/form-data">
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
					<s:text name="pts.menu.project.capacity.plannning" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.project.capacity.plannning" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div style="width: 100%;">
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
					</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text name="pts.resource.name" />:</td>
							<td align="left" width="200"><sj:autocompleter
									list="capacityStreamMap" name="capacityStream" theme="simple"
									id="capacityStreamId" onSelectTopics="/capacityStreamSelect"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter> <sj:a
									id="getCapacityImageHrefId"
									href="../reports/getCapacityImage.action"
									targets="capacityImageDivId" formIds="capacityformId"
									indicator="getCapacityImageLoadingIndicator" /><img
								id="getCapacityImageLoadingIndicator"
								src="../images/indicatorImg.gif" style="display: none" /></td>
								
							<td style="color: #2779aa;" align="right"><s:text
													name="pts.menu.project.capacity.plannning" />:</td>
											<td><s:file name="photoUpload" theme="simple"></s:file>
											</td>
											<td> <div style="padding-right: 5px; text-align: right;">
					<sj:submit   value="Submit" />
				</div></td>
											

						</tr>
					</table>
				</div>
				<sj:div id="capacityImageDivId">
					<s:include value="/jsp/reports/ajaxCapacity.jsp"></s:include>
				</sj:div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>