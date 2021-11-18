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
	$.subscribe('/beforeSave', function(event, data) {
		if ($('#newPassword').val() != $('#confirmPassword').val()) {
			alert('New password and confirm password are not same...');
			event.originalEvent.options.submit = false;
			return false;
		}
	});

	function submitform() {
		clearErrorDiv();
		if ($('#newPassword').val() == '' || $('#confirmPassword').val() == '') {
			alert('Plese enter password...');
			event.originalEvent.options.submit = false;
			document.forms[0].onsubmit = null;
			return false;
		} else if ($('#newPassword').val() != $('#confirmPassword').val()) {
			alert('New password and confirm password are not same...');
			event.originalEvent.options.submit = false;
			document.forms[0].onsubmit = null;
			return false;
		} else {
			document.forms[0].method = "POST";
			document.forms[0].action = "../user/resetPassword.action";
			document.forms[0].submit();
		}

	}
</script>
</head>
<body>
	<s:form id="resetPasswordFormId" method="POST" theme="simple">
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
					<s:text name="pts.menu.reset.password" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.reset.password" />
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
												name="pts.user.old.password" />*:</td>
										<td><s:password name="oldPassword" id="oldPassword"
												theme="simple"></s:password></td>
									</tr>
									<tr>
										<td align="right" style="color: #2779aa;"><s:text
												name="pts.user.new.password" />*:</td>
										<td><s:password name="newPassword" id="newPassword"
												theme="simple"></s:password></td>
									</tr>
									<tr>
										<td align="right" style="color: #2779aa;"><s:text
												name="pts.user.confirm.password" />*:</td>
										<td><s:password name="confirmPassword"
												id="confirmPassword" theme="simple"></s:password></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div style="padding-right: 5px; text-align: right;">
					<s:a href="javascript:void(0);" value="Save" onclick="submitform()">
						<img src="../images/save.png" />
					</s:a>
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