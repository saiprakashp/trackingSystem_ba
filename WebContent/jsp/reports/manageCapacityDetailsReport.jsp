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
	<style>
	.myAltRowClass{ 
		border: 1px solid #dddddd !important;
		background:rgb(237, 235, 232) !important;
	
	 }
	</style>
<script type="text/javascript">

	datePick = function(element) {
		$(element).datepicker();
	};

	function myelem(value, options) {
		alert(options.id);
		var ret = jQuery("#subgridtable").jqGrid('getRowData', options.id);
		alert(ret.userId);
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
	$.subscribe('gridCompleteTopic', function (event, data) {
		/*var d = new Date();
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
        $grid.jqGrid("setGridWidth", 975, true);*/
	});
	$.subscribe('subgridComplete', function (event, data) {

		$.each(data.rows,function(i,item){
	            $("#" + data.rows[i].id).css("background", "#edd1ab");
	    });
		/*var d = new Date();
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
			$("th[id*=_"+month[i]+"]").css("display", "none");
			$("td[aria-describedby*=_"+month[i]+"]").css("display", "none");
		}*/
        
		
		});
	
	
	function colorFormatter(cellValue,opts,element) {

		if(!cellValue || cellValue == 0) {return "0";}
		if(cellValue > 0 && cellValue < 170)
			return '<span class="cellWithoutBackground" style="background-color: rgb(88,155,0);color:white;"><b>' + cellValue + '</b></span>';
        else if(cellValue > 170)
        	return '<span class="cellWithoutBackground" style="background-color: rgb(178,34,34);color:white;"><b>' + cellValue + '</b></span>';
        else 
        	return cellValue;
	}
</script>
</head>
<body>
	<s:form theme="simple" method="POST"
		id="manageCapacityDetailReportFormId">
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
					<s:text name="pts.menu.project.capacity.plannning.details.report" />
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
						<s:text name="pts.menu.project.capacity.plannning.details.report" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<!-- <td style="color: #2779aa;" align="right" nowrap="nowrap">Report
								Type:</td>
							<td align="left" width="200"><sj:autocompleter
									list="#{'Resource-Wise':'Resource-Wise','Project-Wise':'Project-Wise' }"
									selectBox="true" selectBoxIcon="true" name="reportType"
									id="reportType" theme="simple"></sj:autocompleter></td> -->
							<td style="color: #2779aa;"><s:text
									name="pts.user.supervisor" />:</td>
							<td width="200"><sj:autocompleter
									list="supervisorMapForManage" name="searchSupervisor"
									listKey="key" listValue="value" theme="simple"
									id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;" nowrap="nowrap"><s:text
									name="pts.resource.name" />:</td>
							<td align="left" width="200"><s:textfield
									name="searchUserName" id="searchUserName" theme="simple"
									cssStyle="width:175px;" /> <s:hidden name="allReporteesFlag"
									id="allReporteesFlag" value="true" /></td>
							<td style="color: #2779aa;"><s:text name="pts.year" />:</td>
							<td align="left"><s:select list="yearList"
									name="selectedYear" id="selectedYear"></s:select></td>
							<td style="padding-left: 20px;"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../reports/capacityPlanningDetailsReport.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><s:if test="reportType eq 'Resource-Wise'"><jsp:include
									page="capacityPlanningDetailReportGrid.jsp" /></s:if> <s:else><jsp:include
									page="capacityPlanningNCDetailReportGrid.jsp" /></s:else></td>
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