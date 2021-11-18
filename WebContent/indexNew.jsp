<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title><s:text name="pts.project.title" /></title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/pts.css" />
<script src="<%=request.getContextPath()%>/js/pts.js"
	type="text/javascript"></script>
<style>
.buttonStandard_DefaultRed2 {
	font-weight: 700;
	font-size: 12px;
	background: #228BE8;
	cursor: pointer;
	color: #fff;
	font-family: Arial, sans-serif;
	height: 21px;
	border-style: none;
	padding: 3px 7px 4px 0;
}

.buttonStandard_DefaultRed {
	margin-top: -2px;
	display: inline-block;
	background: #228BE8;
	white-space: nowrap;
	height: 21px;
	padding: 4px 0 4px 7px;
}

.projectTitle {
	color: white;
}
</style>
<script type="text/javascript">
	function redirect() {
		window.open('http://129.192.10.77:31000/pts/', '_self');
	}
	//	redirect();
</script>
</head>
<body style="background: white !important;">
	<s:form action="/login/login.action" method="POST"
		cssStyle="background: white;">
		<input type="hidden" value="2" name="userType" />
		<div>
			<table style="width: 100%; background: #0082F0">
				<tr>
					<td align="center">
						<table style="width: 1000px;">
							<tr>
								<td width="36px;"><img
									src="<%=request.getContextPath()%>/images/elogo2.png"
									style="height: 36px;"></td>
								<td valign="bottom" style="padding-left: 10px;"><div
										class="projectTitle">
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
		<h4 style="color: black; text-align: center;">
			This in new portal. please reach out your <span style="">line
				manager</span> for <span style="color: red;">new password.</span>
		</h4>

		<div align="center"
			style="position: relative; margin-right: 10px; v-align: center;">
			<div
				style="border-top-right-radius: 15px; border: 1px solid black; border-bottom-left-radius: 15px; width: 300px; background: #fff;">
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
					<thead>
						<tr>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td align="left"><s:text name="pts.username" /></td>
							<td><s:textfield name="username" theme="simple"
									id="username" /></td>
						</tr>
						<tr>
							<td align="left"><s:text name="pts.password" /></td>
							<td><s:password name="password" theme="simple" /></td>
						</tr>
						<tr>
							<td></td>
							<td align="right"><span class="buttonStandard_DefaultRed"
								id="sButton"> <s:submit id="SignIn" theme="simple"
										name="SignIn" title="Sign In"
										cssClass="buttonStandard_DefaultRed2" value="Sign In"
										cssStyle="width:70px;" onclick="clearErrorDiv()"></s:submit>
							</span></td>
						</tr>
					</tbody>
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
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
<script>
	document.getElementById("username").focus();
</script>
</html>