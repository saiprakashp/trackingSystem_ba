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

	$.subscribe('gridComplete', function(event, data) {
		$.each(jQuery(data).jqGrid('getRowData'), function(i, item) {
			//console.log(item.janCapacity);
		});
	});

	function colorFormatter(cellValue, opts, element) {
		if (!cellValue || cellValue == 0) {
			return "";
		}
		/*if(cellValue < 170)
		    return '<div style="text-align:center;display:block;margin:auto;background-color: rgb(244, 4, 48);height:95%;font-size:12px;padding-top:10px;"><span style="margin-top:7px"><b>' + cellValue + '</b></div></span>';
		else*/
		return cellValue;
	}
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
							<td width="200"><sj:autocompleter
									list="supervisorMapForManage" name="searchSupervisor"
									listKey="key" listValue="value" theme="simple"
									id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.user.status" />:
							</td>
							<td align="left"><sj:autocompleter list="statusMap"
									name="searchStatus" listKey="key" listValue="value"
									theme="simple" id="searchStatus" selectBox="true"
									selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.year" />:
							</td>
							<td align="left"><s:select list="yearList"
									name="selectedYear" id="selectedYear"></s:select></td>
							<td style="padding-left: 20px;"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../capacity/capacityPlanningByProjectType.action')" /></td>
						</tr>
						<!-- <tr>
							<td style="color: #2779aa;" align="right">Report Type:</td>
							<td align="left" width="200"><s:select
									list="#{'Resource-Wise':'Resource-Wise','Project-Wise':'Project-Wise' }"
									name="reportType" id="reportType" cssStyle="width:175px;"></s:select>
							</td>
							<td style="color: #2779aa;" align="right">Capacity:</td>
							<td align="left" width="200"><s:select
									list="#{'Allocated':'Allocated','Consumed':'Consumed','Available':'Available' }"
									name="capacityType" id="capacityType" cssStyle="width:175px;"></s:select>
							</td>


							
						</tr> -->
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="capacityPlanningByProjectTypeGrid.jsp" /></td>
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