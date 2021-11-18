<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<html>
<head>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title><s:text name="pts.project.title" /></title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/pts.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/fontawesome.min.css" />
<script>
	function clearErrorDiv() {
		$("#errorDiv").html("");
	}
	function loadPts() {
		window.location.pathname = 'pts';
	}

	setInterval(loadPts, 5000);
</script>
</head>
<body>
	<s:form action="/user/changePassword.action" method="POST">
		<div>
			<table style="width: 100%;">
				<tr>
					<td align="center">
						<table style="width: 1000px;">
							<tr>
								<td width="36px;"><img
									src="<%=request.getContextPath()%>/images/logo.gif"></td>
								<td valign="bottom"><div class="projectTitle">
										<s:text name="pts.project.title" />
									</div></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<DIV id=spacer5px
			style="background-color: #a3a3a3; clear: both; vertical-align: bottom; line-height: 1px;">&nbsp;</DIV>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div align="center"
			style="position: relative; margin-right: 10px; v-align: center;">
			<div
				style="border-top-right-radius: 15px; border-bottom-left-radius: 15px; width: 900px; background: #fff;">
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
				<div id="spacer10px">&nbsp;</div>
				<table>
					<tr>
						<!-- <td align="left"><s:text name="pts.username" /></td>
						<td><s:textfield name="userName" theme="simple" id="userName"/></td>
					</tr>
					<tr>
										<td align="left"><s:text
												name="pts.user.new.password" />:</td>
										<td><s:password name="newPassword" id="newPassword"
												theme="simple"></s:password></td>-->
						<td><p>
							<h3>Your Password is reverted to default please log in with the default password</h3> Please reset your password
							after log in with temporary password.</td>


					</tr>
					<tr>
						<td><a style="font-size: 16px;"
							href="<%=request.getContextPath()%>/index.jsp">Go Back</a> You
							will be redirect back shortly!!</td>

						<!-- <td align="right"><span class="buttonStandard_DefaultRed"
							id="sButton"> <s:submit id="Save" theme="simple"
									name="Save" title="Save"
									cssClass="buttonStandard_DefaultRed2" value="Save"
									cssStyle="width:70px;" onclick="clearErrorDiv()"></s:submit>
						</span></td>-->
					</tr>
				</table>

			</div>
		</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
<script>
	document.getElementById("username").focus();
</script>
</ html>