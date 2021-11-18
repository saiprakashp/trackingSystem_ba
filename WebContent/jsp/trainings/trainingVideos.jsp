<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjt" uri="/struts-jquery-tree-tags"%>
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
$.subscribe('treeClicked', function(event, data) {
	var item = event.originalEvent.data.rslt.obj;
	var fileToDownload=item.attr("id");
	var itemclass=item.attr("class");
	var fileName=item.text();
	if(fileToDownload.indexOf(' -- ') != -1 && fileToDownload.split(' -- ')[0] == 'File'){
		window.location.href="<%=request.getContextPath()%>
	/RICOTrainings/download.action?filename="
									+ fileToDownload.split(' -- ')[1];
						} else {
							$('#directoryStructure').val(
									fileToDownload.split(' -- ')[1]);
						}
					});
</script>
</head>
<body>
	<s:form name="tree" theme="simple" method="POST"
		action="/RICOTrainings/uploadFile" enctype="multipart/form-data">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent"
			style="width: 100%;  height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.rico.trainings" />
					</s:a>
					>
					<s:text name="pts.menu.rico.trainings" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.rico.trainings" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div
					style="border: solid 1px black; border-radius: 5px; height: 700px;">
					<table style="width: 100%;">
						<tr>
							<td>
								<div style="overflow-x: auto; height: 690px;">
									<s:url id="treeDataUrl"
										action="../RICOTrainings/getTrainingVideos" />
									<sjt:tree id="jsonTree" href="%{treeDataUrl}"
										onClickTopics="treeClicked" jstreetheme="default" />
								</div>
							</td>
							<td valign="bottom" align="right">
								<table>
									<tr>
										<td align="right">Directory to upload:</td>
										<td><s:textfield name="directoryStructure"
												id="directoryStructure" theme="simple"
												cssStyle="width:200px;"></s:textfield></td>
									</tr>
									<tr>
										<td align="right">File to upload:</td>
										<td><s:file name="upload" theme="simple"
												multiple="multiple" cssStyle="width:290px;" /></td>
									</tr>
									<tr>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td colspan="2"></td>
									</tr>
									<tr>
										<td colspan="2" align="right"><s:submit></s:submit></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>
