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
<script type="text/javascript">
	datePick = function(element) {
		$(element).datepicker();
	};

	function myelem(value, options) {
		var ret = jQuery("#subgridtable").jqGrid('getRowData', options.id);
		var el = document.createElement("select");
		el.type = "select";
		//el.value = value;
		$("#networkCodeDiv").load(
				"../capacity/getUserNetworkCodes.action?selectedYear="
						+ document.getElementById('selectedYear').value);
		return el;
	}

	function myvalue(elem, operation, value) {
		if (operation === 'get') {
			return $(elem).val();
		} else if (operation === 'set') {
			$('select', elem).val(value);
		}
	}
</script>
</head>
<body>
	<s:form theme="simple" method="POST"
		id="contributionDetailReportFormId">
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
					<s:text name="pts.menu.project.contribution.details.report" />
				</div>
			</div>
		</div>
		<s:hidden name="id" id="userId" />
		<s:url id="selecturl"
			action="getUserNetworkCodes.action?selectedYear=%{selectedYear}" />
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.project.contribution.details.report" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text name="pts.label.region" />:</td>
							<td align="left" width="200"><sj:autocompleter
									list="#{'ALL':'ALL','MANA':'MANA','EGIL':'EGIL'}"
									name="ricoLocation" theme="simple" id="ricoLocationId" 
									selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.user.location" />:
							</td>
							<td width="200" align="left"><sj:autocompleter
									list="locationsMap" name="searchLocation" listKey="key"
									listValue="value" theme="simple" id="searchLocation"
									selectBox="true" selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.networkcode.pillar.name" />:
							</td>
							<td width="200" nowrap="nowrap"><sj:div id="pillarsDivId">
									<sj:autocompleter list="platformsMap" name="pillarId"
										listKey="key" listValue="value" theme="simple" id="pillarId"
										onSelectTopics="/pillarSelect" selectBox="true"
										selectBoxIcon="true"></sj:autocompleter>
								</sj:div>
							</td>
						</tr>
						<tr>
							<td style="color: #2779aa;"><s:text
									name="pts.user.supervisor" />:</td>
							<td width="200"><sj:autocompleter
									list="supervisorMapForManage" name="searchSupervisor"
									listKey="key" listValue="value" theme="simple" 
									id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.user.stream" />:
							</td>
							<td width="200"><sj:autocompleter list="streamsMap"
									name="searchStream" listKey="key" listValue="value"
									theme="simple" id="searchStream" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter></td>
							<td  style="color: #2779aa;">&nbsp;</td>
							<td  width="200"><s:submit
									theme="simple" src="../images/go.png" type="image"
									onclick="submitForm('../reports/userContributionDetailsReport.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="userContributionDetailReportGrid.jsp" /></td>
					</tr>
				</table>
			</div>
			<s:div id="spacer10px">&nbsp;</s:div>
			<s:div id="spacer10px">&nbsp;</s:div>
			<div id="networkCodeDiv"></div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>