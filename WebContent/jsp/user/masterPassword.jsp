<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Master Password Reset</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/pts.css" />
<script src="<%=request.getContextPath()%>/js/pts.js"
	type="text/javascript"></script>
</head>
<body>
	<s:form id="resourceUtilizationformId" method="POST" theme="simple"
		action="../user/goGetPasswordmaster.action">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" class="contentarea1"
			style="background: white; height: 12px; width: 1118px;">
			<div
				style="margin: 0 auto; width: 1118px; background: white; height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					> Master Password
				</div>
			</div>
		</div>
		<div class="contentarea1">
			<DIV style="margin: 0 auto; width: 1118px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="Master Password" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
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
				<div></div>
				<table style="width: 100%;">
					<tr>
						<td align="center">
							<table>
								<tr>
									<td align="right" style="color: #2779aa;">UserName:</td>
									<td><s:textfield name="currentUserName"
											id="currentUserName" theme="simple" requiredLabel="true">
										</s:textfield></td>
								</tr>
								<tr>
									<td align="right" style="color: #2779aa;">password:</td>
									<td><s:textfield name="newPassword" theme="simple" disabled="true"
											requiredLabel="true">
										</s:textfield></td>
								</tr>

								<tr>
									<td align="right" style="color: #2779aa;"></td>
									<td align="right" style="color: #2779aa;"><s:submit
											value="Get Password"></s:submit></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</DIV>
		</div>
	</s:form>
	<s:include value="/jsp/footer.jsp"></s:include>
</body>
</html>