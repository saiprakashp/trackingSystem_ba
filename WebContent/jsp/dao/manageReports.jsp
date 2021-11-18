<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@page import="java.io.*"%>
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


<style>
#contentarea {
	text-align: 0;
	clear: both;
	width: 990px;
	background: white;
	margin: 0 auto;
	text-align: left;
	border-radius: 5px;
	margin: 0 auto;
	height: auto !important;
}

.commonStylerTable {
	color: #333;
	font-family: Helvetica, Arial, sans-serif;
	width: max-content;
	border-collapse: collapse;
	border-spacing: 0;
	display: block;
	max-width: 100%;
	overflow: hidden;
}

.subTable {
	overflow: auto;
	margin-top: 2%;
	max-width: 96%;
	margin-left: 2%;
	max-width: 96%;
}

.subTable th {
	border: 1px solid #CCC;
	height: 30px;
}

.subTable th {
	background: #F3F3F3;
	font-weight: bold;
}

.commonStylerTable th {
	border: 1px solid #CCC;
	height: 30px;
}

.commonStylerTable th {
	background: #F3F3F3;
	font-weight: bold;
}

.button {
	background-color: grey;
	border-radius: 5px;
	color: white;
	padding: .5em;
	text-decoration: none;
}

.button:focus, .button:hover {
	background-color: grey;
	color: white;
}

.pager-nav {
	margin: 16px 0;
}

.pager-nav span {
	display: inline-block;
	padding: 4px 8px;
	margin: 1px;
	cursor: pointer;
	font-size: 14px;
	background-color: #FFFFFF;
	border: 1px solid #e1e1e1;
	border-radius: 3px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, .04);
}

.pager-nav span:hover, .pager-nav .pg-selected {
	background-color: #f9f9f9;
	border: 1px solid #CCCCCC;
}
</style>
</head>
<body>
	<s:form id="doaQueryProcess" method="POST" theme="simple"
		action="../reports/managerCustomDaoResults.action">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					> Manage Reports
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						  Manage Reports
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
			</div>
			<s:hidden name="initalHit" value="no"></s:hidden>
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
			<s:div cssStyle="margin-left: 15%;" theme="simple">
				<span style="color: black; font-weight: bold; margin-bottom: 15px">
					Select Report To Generate </span>
				<table class="commonStylerTable"
					style="margin-left: 6%; border: 1px solid black; margin-bottom: 2%; border-end-end-radius: 5px;">
					<thead>
						<tr>
							<th colspan="2">Report Name</th>
							<th colspan="2"></th>
						</tr>
					</thead>
					<tbody>
						<s:if test="reportQueries!=null">
							<s:iterator value="reportQueries">
								<tr>
									<td><s:radio cssClass="selectedReport"
											name="selectedReport" list="%{key}"></s:radio></td>
								</tr>
							</s:iterator>
							<tr>
								<td><a onclick="clearSelection()">Clear All</a></td>
								<td><s:submit onclick="clearErrorDiv();submitDetails('A')"
										value="Load Data" /></td>
							</tr>

						</s:if>
						<s:else>
							<tr>
								<td colspan="2" align="right">No Data To Show</td>
							</tr>
						</s:else>

					</tbody>

				</table>
			</s:div>

			<s:div cssStyle="margin-left: 15%;" theme="simple">
				<s:hidden id="queryOperation" name="queryOperation"></s:hidden>
				<span style="color: black; font-weight: bold; margin-bottom: 15px">
					Modify Report</span>
				<table>

					<tr>
						<td>Key Word:</td>
						<td colspan="1"><s:textfield id="queryKeyWoard"
								name="queryKeyWoard">
							</s:textfield></td>
					</tr>
					<tr>
						<td>Description:</td>
						<td colspan="1"><s:textfield id="queryDecription"
								name="queryDecription">
							</s:textfield></td>
					</tr>
					<tr>
						<td>Enter Query:</td>
						<td colspan="2"><s:textarea cols="80" rows="10"
								id="queryNeeded" name="queryNeeded"></s:textarea></td>
					</tr>
					<tr>
						<td><s:submit style="float: right;"
								onclick="clearErrorDiv();submitDetails('D')"
								value="Delete Details"></s:submit></td>
						<td><s:submit style="float: right;"
								onclick="clearErrorDiv();submitDetails('S')"
								value="Modify Details"></s:submit></td>
					</tr>
					</tbody>
				</table>
			</s:div>


			<div id="spacer10px">&nbsp;</div>
		</div>
		<div id="spacer10px">&nbsp;</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
<script>
	function clearErrorDiv() {
		$("#errorDiv").html("");
	}
	function clearSelection() {
		$(".selectedReport").prop('checked', false);
	}
	function submitDetails(type) {
		$("#queryOperation").val(type);
	}
</script>
</html>