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
<link
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet"
	integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN"
	crossorigin="anonymous">
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

#Login:hover {
	text-decoration: underline;
}

.buttonStandard_DefaultRed {
	margin-top: 5px;
	display: inline-block;
	background: #228BE8;
	white-space: nowrap;
	height: 19px;
	padding: 7px 2px 5px 7px;
	margin-left: 1%;
	background: #228BE8;
	white-space: nowrap;
	height: 19px;
	padding: 7px 2px 5px 7px;
	margin-left: 1%;
	width: auto;
}

.projectTitle {
	color: white;
}
</style>
<script>
	function submitData() {
		if (window.location.pathname.search('login')) {
			document.getElementById("loginForm").action = '/pts/user/changePassword.action';
		} else {
			document.getElementById("loginForm").action = 'user/changePassword.action';
		}

		document.getElementById("loginForm").submit();
	}
	function submitLogin() {
		document.getElementById("loginForm").action = '';
		document.getElementById("loginForm").action = '/pts';
		document.getElementById("loginForm").submit();
	}
</script>
</head>
<body>
	<s:form action="/user/changePassword.action" method="POST"
		id="loginForm">
		<div>
			<table style="width: 100%; background: #0082F0">
				<tr>
					<td align="center">
						<table style="width: 1000px;">
							<tr>
								<td width="36px;"><img
									src="<%=request.getContextPath()%>/images/elogo2.png"
									style="height: 36px;"></td>
								<td valign="bottom"
									style="font-size: 20px; color: white; font-weight: 700; text-align: left; padding: 9px;"><div>
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
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div id="spacer10px">&nbsp;</div>
		<div
			style="border-top-right-radius: 15px; border: 1px solid black; border-bottom-left-radius: 15px; width: 275px; background: #fff; margin: auto;">

			<div
				style="border-top-right-radius: 15px; border-bottom-left-radius: 15px; width: auto; background: #fff;">
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
						<td align=" " colspan="2"><s:text name="pts.username" /></td>
						<td><s:textfield name="userName" theme="simple" id="username" /></td>
					</tr>
					<tr>
						<td align=" " colspan="2"><s:text name="pts.user.email" /></td>
						<td><s:textfield name="email" theme="simple" id="email" /></td>
					</tr>
					<tr>
						<td align="right"></td>
						<td align="right"></td>
						<td align="right"><span class="buttonStandard_DefaultRed"
							id="sButton"> <a id="Save" title="Save"
								class="buttonStandard_DefaultRed2"
								value="Set Temporary Password" style="width: 150px;"
								onclick="submitData()">Reset Password</a>

						</span></td>
						<td align="right"><a id="Login" title="Login" class=""
							style="width: 70px; text-decoration: none; color: blue; cursor: pointer;"
							onclick="submitLogin()"><i
								style="color: blue; font-size: 20px;" class="fa fa-angle-left"></i><span
								style="color: blue; font-size: 13px; padding-left: 5px;">Go
									Back</span></a></td>
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
</html>