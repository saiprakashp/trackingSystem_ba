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
<script src="<%=request.getContextPath()%>/js/table.min.js"
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
	max-width: 96%;
	width: max-content;
	margin-left: 6%;
	max-width: 96%;
	height: auto;
	max-height: 300px;
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

#contentarea {
	text-align: 0;
	clear: both;
	width: 1215px;
	background: white;
	margin: 0 auto;
	text-align: left;
	border-radius: 5px;
	margin: 0 auto;
}
</style>
</head>
<body onload="getDisabledStatus()">
	<s:form id="doaQueryProcess" method="POST" theme="simple"
		action="../reports/getCustomDaoResults.action">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					> Dao Query Process
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						  Report View / Generator 
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
			<s:div cssStyle="" theme="simple" id="queryDiv">

				<table class="commonStylerTable queryTable"
					style="margin: auto; margin-top: auto; margin-bottom: auto; background-color: lightgray; margin-top: 1%; border-radius: 5px; color: black; padding: .5em; text-decoration: none; margin-bottom: 1%;">
					<thead>
						<tr>
							<th><span
								style="color: black; font-weight: bold; margin-bottom: 15px">
									Select Report To Generate </span></th>
						</tr>
						<tr>
							<th colspan="2">Manage Name</th>
							<th colspan="2">Report Name</th>
							<th colspan="2"></th>
						</tr>
					</thead>
					<tbody>
						<tr>

							<td colspan="2" style="width: 183px;"><sj:div
									id="projectManagersDivId" cssStyle=" margin-bottom: 10px;">
									<sj:autocompleter list="projectManagersMapObj"
										name="projectManager" listKey="key" listValue="value"
										theme="simple" id="projectManagerId" selectBox="true"
										selectBoxIcon="true"></sj:autocompleter>
								</sj:div></td>
							<s:if test="reportQueries!=null">
								<td>
									<table>
										<tbody>
											<s:iterator value="reportQueries" status="stat">
												<s:if test="#stat.count %2 ==0">
													<td><input type="radio" name="selectedReport"
														value='<s:property value="%{key}" />' /> <s:property
															value="%{value.description}" /></td>
												</s:if>
												<s:else>
													<tr>
														<td><input type="radio" name="selectedReport"
															value='<s:property value="%{key}" />' /> <s:property
																value="%{value.description}" /></td>
													</tr>
												</s:else>
											</s:iterator>
										</tbody>
									</table>
								</td>
							</s:if>
							<s:else>
								<td>No Predefined Query Available</td>
							</s:else>
						</tr>
						<tr>
							<td><s:submit value="Get Data" /></td>
						</tr>

					</tbody>

				</table>
			</s:div>
			<table
				style="margin: auto; margin-top: auto; margin-bottom: auto; cursor: pointer; background-color: lightgray; margin-top: 1%; border-radius: 5px; color: black; padding: .5em; text-decoration: none; margin-bottom: 1%;">
				<tr>
					<td colspan="2" align="right"><a class="showHideQueryTab">Show/
							Hide Queries</a></td>
				</tr>
			</table>

			<table class="commonStylerTable reportTable"
				style="margin-left: 6%; border: 1px solid black; margin-bottom: 2%; border-end-end-radius: 5px;">
				<thead>
					<tr>
						<th colspan="4">Custom Query based Report generator</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="checkbox" value="fileBased" name="fileBased"
							onclick="enableReportFields()">Download File</input></td>
						<td><input type="checkbox" name="queryNeeded"
							checked="checked">View File</input></td>



						<td style="padding-left: 8px;"><s:label>Please Enter FileName:</s:label>
							<s:textfield id="fileInputName" name="fileInputName"
								value="REPORT" requiredLabel="true"></s:textfield> <s:label>Please Enter Sheet Name</s:label>
							<s:textfield id="gridName" name="gridName" value="REPORT"
								requiredLabel="true"></s:textfield></td>
						<!-- <td><input type="checkbox" checked value="viewBased"
							name="viewBased">Show in View</input></td> -->
						<td><input type="button" onclick="tableToExcel()"
							value="Export to Excel"></td>
					</tr>

					<tr>
						<td colspan="2"><s:textarea cols="80" rows="10"
								id="queryNeeded" name="queryNeeded"></s:textarea></td>
					</tr>
					<tr>
						<td></td>
						<td><s:submit style="float: right;"
								onclick="getDetails();clearErrorDiv();" value="Get Details"></s:submit></td>
					</tr>
				</tbody>

			</table>

			<table
				style="margin: auto; background-color: lightgray; cursor: pointer; margin-top: 2%; border-radius: 5px; color: black; padding: .5em; text-decoration: none;">
				<tr>
					<td colspan="2" align="right"><a class="showHideReportTab">Show/
							Hide Reports</a></td>
				</tr>
			</table>

			<table
				style="margin-left: 6%; border: 1px solid black; border-end-end-radius: 5px;">
				<tr>
					<th>Result:</th>
				</tr>
				<!-- <thead>
						<tr>
							<th><input type="button" onclick="tableToExcel()"
								value="Export to Excel"></th>
						</tr>
					</thead> -->
			</table>
			<br />
			<table border="1" id="subTable"
				class="subTable commonStylerTable content">
				<thead>

					<tr>
						<s:iterator value="headers">
							<th><s:property /></th>
						</s:iterator>
					</tr>


				</thead>
				<tbody>
					<s:iterator value="queryReturnResult" status="stat">
						<tr>
							<s:iterator>
								<td><s:if test="%{value == null}">NA </s:if> <s:else>
										<s:property value="value" />
									</s:else></td>
							</s:iterator>
						</tr>
					</s:iterator>
				</tbody>
			</table>


			<div id="spacer10px">&nbsp;</div>
		</div>
		<div id="spacer10px">&nbsp;</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
<script>
	$(document).ready(function() {

		function clearErrorDiv() {
			$("#errorDiv").html("");
		}
		var reportflag = false;
		var queryflag = false;
		$(".showHideQueryTab").click(function() {
			if (queryflag) {
				$(".queryTable").hide();
			} else {
				$(".queryTable").show();
			}
			queryflag = !queryflag;
		});
	
		$(".showHideReportTab").click(function() {
			if (reportflag) {
				$(".reportTable").hide();
			} else {
				$(".reportTable").show();
			}
			reportflag = !reportflag;
		});

	 
	});
	var disabledReportName = true;
	function getDisabledStatus() {
		let showDisabled = <s:property value="showQueryEdit"/>;
		document.getElementById("queryNeeded").disabled = !showDisabled;
		document.getElementById("fileInputName").disabled =disabledReportName ;
		document.getElementById("gridName").disabled = disabledReportName;
	}
	var show = true;
	
	function showHideQuery() {
		if (show) {
			$("#queryDiv").slideUp("slow");
		} else {
			$("#queryDiv").slideDown("slow");
		}
		show = !show;
	}
	function enableReportFields(){
		document.getElementById("fileInputName").disabled =!disabledReportName;
		document.getElementById("gridName").disabled =!disabledReportName;
		disabledReportName=!disabledReportName;
		
	}
	function tableToExcel() {
		var gridName = document.getElementById('gridName').value;
		var fileNmae = document.getElementById('fileInputName').value;
		$("#subTable").table2excel({
			filename : fileNmae + ".xlsx",
			sheetName : 'REPORT'
		});
		//
		//

		document.forms[0].method = "post";
		document.forms[0].action =  '../reports/downloadDaoQueryData.action',
		document.forms[0].submit();
	}
</script>
</html>