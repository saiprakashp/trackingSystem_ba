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
	
	$.subscribe('gridComplete', function (event, data) {
		$.each(jQuery(data).jqGrid('getRowData'), function (i, item) {
		        //console.log(item.janCapacity);
		    });
		var d = new Date();
		var month = new Array();
		 month[0] = "janCapacity";
		 month[1] = "febCapacity";
		 month[2] = "marCapacity";
		 month[3] = "aprCapacity";
		 month[4] = "mayCapacity";
		 month[5] = "junCapacity";
		 month[6] = "julCapacity";
		 month[7] = "augCapacity";
		 month[8] = "sepCapacity";
		 month[9] = "octCapacity";
		 month[10] = "novCapacity";
		 month[11] = "decCapacity";
		var n = month[d.getMonth()];
		for(i=0;i<d.getMonth();i++) {
			$("#gridmultitable").jqGrid('hideCol',[month[i]]);
		}
		$("#gridmultitable").jqGrid('columnChooser',{shrinkToFit: true});
		var $grid = $("#gridmultitable");
        $grid.jqGrid("setGridWidth", 975, true);

	});
	
	function colorFormatter(cellValue,opts,element) {
		if(!cellValue || cellValue == 0) {return "";}
		/*if(cellValue < 170)
            return '<div style="text-align:center;display:block;margin:auto;background-color: rgb(244, 4, 48);height:95%;font-size:12px;padding-top:10px;"><span style="margin-top:7px"><b>' + cellValue + '</b></div></span>';
        else*/
            return cellValue;
	}
	$.subscribe('/supervisorSelect', function(event, data) {
		setTimeout("getPillarsMap()", 10);
		setTimeout("getProjectsMap()", 100);
	});
	$.subscribe('/pillarSelect', function(event, data) {
		setTimeout("getProjectsMap()", 10);
	});
</script>
</head>
<body>
	<s:form theme="simple" method="POST" id="manageCapacityReportFormId">
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
					<s:text name="pts.menu.project.capacity.plannning.report" />
				</div>
			</div>
		</div>
		<s:url id="selecturl"
			action="getUserNetworkCodes.action?selectedYear=%{selectedYear}" />
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.project.capacity.plannning.report" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.user.supervisor" />:</td>
							<td align="left"><sj:autocompleter
									list="supervisorMapForManage" name="searchSupervisor"
									listKey="key" listValue="value" theme="simple"
									id="searchSupervisor" selectBox="true" selectBoxIcon="true"
									onSelectTopics="/supervisorSelect">
								</sj:autocompleter> <sj:a id="getPillarsHrefId"
									href="../reports/getPillarsMap.action"
									targets="pillarsDivId" formIds="manageCapacityReportFormId"
									indicator="pillarLoadingIndicator" /><img
								id="pillarLoadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.label.region" />:</td>
							<td align="left"><sj:autocompleter
									list="#{'ALL':'ALL','MANA':'MANA','EGIL':'EGIL'}"
									name="ricoLocation" theme="simple" id="ricoLocationId"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.user.status" />:</td>
							<td align="left"><sj:autocompleter list="statusMap"
									name="searchStatus" listKey="key" listValue="value"
									theme="simple" id="searchStatus" selectBox="true"
									selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.user.stream" />:</td>
							<td align="left"><sj:autocompleter list="streamsMap"
									name="searchStream" listKey="key" listValue="value"
									theme="simple" id="searchStream" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter></td>
							<td  colspan="2" align="right">&nbsp;</td>
						</tr>
						<tr>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.networkcode.pillar.name" />:</td>
							<td nowrap="nowrap"><sj:div id="pillarsDivId">
									<sj:autocompleter list="pillarsMapObj" name="pillar"
										listKey="key" listValue="value" theme="simple" id="pillarId"
										onSelectTopics="/pillarSelect" selectBox="true"
										selectBoxIcon="true"></sj:autocompleter>
								</sj:div> <sj:a id="getProjectsHrefId"
									href="../reports/getProjectsMap.action" targets="projectsDivId"
									formIds="manageCapacityReportFormId"
									indicator="projectLoadingIndicator" /> <img
								id="projectLoadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.networkcode.project" />:
							</td>
							<td align="left"><sj:div id="projectsDivId">
									<sj:autocompleter list="projectsMapObj" name="project"
										listKey="key" listValue="value" theme="simple" id="projectId"
										selectBox="true" selectBoxIcon="true"></sj:autocompleter>
								</sj:div></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.user.technology" />:</td>
							<td align="left"><sj:autocompleter list="technologiesMap"
									name="searchTechnology" listKey="key" listValue="value"
									theme="simple" id="searchTechnology" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.year" />:
							</td>
							<td align="left"><s:select list="yearList"
									name="selectedYear" id="selectedYear"></s:select></td>
							<td  colspan="2" align="right"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../reports/capacityPlanningReport.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="capacityPlanningReportGrid.jsp" /></td>
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