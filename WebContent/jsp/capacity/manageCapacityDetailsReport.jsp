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
.myAltRowClass {
	border: 1px solid #dddddd !important;
	background: rgb(237, 235, 232) !important;
}
</style>
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

	function colorFormatter(cellValue, opts, element) {

		if (!cellValue || cellValue == 0) {
			return "";
		}
		if (cellValue < 170)
			return '<span class="cellWithoutBackground" style="background-color: rgb(244, 4, 48);">'
					+ cellValue + '</span>';
		else
			return cellValue;
	}
	$.subscribe('selecRow', function(event, data) {
		if (Number(event.originalEvent.id) != null
				&& Number(event.originalEvent.id) == 99999) {
			$('#' + data.id).jqGrid('resetSelection');
		}/* else
						$('#' + data.id).jqGrid('resetSelection');
						if(data.id=='subgridtable'){
							document.getElementById("parentId").innerHTML=data.id;
							console.log('Parent Id: '+$("#parentId").val());
						}
						var table = document.getElementById(data.id);
						var eventId = Number(event.originalEvent.id);
						console.log(table.rows[eventId]);
						console.log(table.rows[eventId].cells[3].innerHTML == 'Total: ');
						if (table.rows[eventId].cells[3].innerHTML != null
								&& table.rows[eventId].cells[3].innerHTML == 'Total: ') {
							$('#' + data.id).jqGrid('resetSelection');
						} */
	});

	$.subscribe('gridComplete', function(event, data) {
		$.each(data.rows, function(i, item) {
			$("#" + data.rows[i].id).css("background", "#edd1ab");
		});
		var $grid = $('#subgridtable');
		$grid.jqGrid('footerData', 'set', {
			'userName' : 'Total'
		});

		var janCapacity = $grid.jqGrid('getCol', 'janCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'janCapacity' : janCapacity
		});

		/*  var febCapacity = $grid.jqGrid('getCol', 'febCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'febCapacity': febCapacity });
		  
		  var marCapacity = $grid.jqGrid('getCol', 'marCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'marCapacity': marCapacity });
		  
		  var aprCapacity = $grid.jqGrid('getCol', 'aprCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'aprCapacity': aprCapacity });
		  
		  var mayCapacity = $grid.jqGrid('getCol', 'mayCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'mayCapacity': mayCapacity });
		  
		  var junCapacity = $grid.jqGrid('getCol', 'junCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'junCapacity': junCapacity });

		  var julCapacity = $grid.jqGrid('getCol', 'julCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'julCapacity': julCapacity });
		  
		  var augCapacity = $grid.jqGrid('getCol', 'augCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'augCapacity': augCapacity });
		  
		  var sepCapacity = $grid.jqGrid('getCol', 'sepCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'sepCapacity': sepCapacity });
		  
		  var octCapacity = $grid.jqGrid('getCol', 'octCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'octCapacity': octCapacity });
		  
		  var novCapacity = $grid.jqGrid('getCol', 'novCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'novCapacity': novCapacity });
		  
		  var decCapacity = $grid.jqGrid('getCol', 'decCapacity', false, 'sum');
		  $grid.jqGrid('footerData', 'set', { 'decCapacity': decCapacity });  */

	});
	$.subscribe('reloadParent', function(event, data) {
		$("#gridmultitable").trigger("reloadGrid", [ {
			current : true
		} ]);
	});
</script>
</head>
<body>
	<s:form theme="simple" method="POST"
		id="manageCapacityDetailReportFormId">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.project.capacity.plannning" />
				</div>
			</div>
		</div>
		<s:url id="selecturl"
			action="getUsers.action?selectedYear=%{selectedYear}" />
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.project.capacity.plannning" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table border="0">
						<tr>
							<td style="color: #2779aa;" align="right" nowrap="nowrap">Project:</td>
							<td align="left"><s:textfield name="searchProject"
									tooltip="Release Name"></s:textfield></td>
							<td style="color: #2779aa;" align="right" nowrap="nowrap"><s:text
									name="pts.user.supervisor" />:</td>
							<td align="left"><sj:autocompleter
									list="supervisorMapForManage" name="searchSupervisor"
									listKey="key" listValue="value" theme="simple"
									id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;" align="right" nowrap="nowrap">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Application
								Name:</td>
							<td align="left"><s:textfield name="searchProjectName"
									id="searchProjectName" theme="simple" tooltip="Application" />
								<s:hidden name="allReporteesFlag" id="allReporteesFlag"
									value="true" /></td>
							<td style="color: #2779aa;" align="right" nowrap="nowrap"><s:text
									name="pts.year" />:</td>
							<td align="left"><sj:autocompleter list="yearList"
									name="selectedYear" theme="simple" id="selectedYear"
									selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td align="left">&nbsp;</td>
						</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td align="left">&nbsp;</td>
							<td align="right">&nbsp;</td>
							<td align="left">&nbsp;</td>
							<td align="right">&nbsp;</td>
							<td align="left">&nbsp;</td>
							<td style="color: #2779aa;" align="right" nowrap="nowrap"
								colspan="2">Active Projects:<s:checkbox name="activeFlag"
									id="activeFlag" theme="simple" /></td>
							<td align="right"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../capacity/capacityPlanningDetailsEditableReport.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include
								page="capacityPlanningNCDetailEditableReportGrid.jsp" /></td>
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