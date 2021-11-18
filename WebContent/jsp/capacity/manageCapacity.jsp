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
	var showCaoError = false;

	var region = 'EGIL';

	var totCap = '';
	$.subscribe('subgridExpanded', function(event, data) {
		var rowId = event.originalEvent.row_id;
		var ret = jQuery("#gridmultitable").jqGrid('getRowData', rowId);
		if (ret.region == 'EGIL') {
			totCap = <s:property value="egilTotCapLimit"/>;
		} else {
			totCap = <s:property value="manaTotCapLimit"/>;
		}
	});

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

	$.subscribe('subgridComplete', function(event, data) {
		var $grid = $('#' + data.id);
		$grid.jqGrid('footerData', 'set', {
			'networkCode' : 'Planned Capacity:'
		});

		var janHours = $grid.jqGrid('getCol', 'janCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'janCapacity' : janHours
		});

		var febHours = $grid.jqGrid('getCol', 'febCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'febCapacity' : febHours
		});

		var MarHours = $grid.jqGrid('getCol', 'marCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'marCapacity' : MarHours
		});

		var AprHours = $grid.jqGrid('getCol', 'aprCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'aprCapacity' : AprHours
		});

		var MayHours = $grid.jqGrid('getCol', 'mayCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'mayCapacity' : MayHours
		});

		var JunHours = $grid.jqGrid('getCol', 'junCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'junCapacity' : JunHours
		});

		var JulHours = $grid.jqGrid('getCol', 'julCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'julCapacity' : JulHours
		});

		var AugHours = $grid.jqGrid('getCol', 'augCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'augCapacity' : AugHours
		});

		var SepHours = $grid.jqGrid('getCol', 'sepCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'sepCapacity' : SepHours
		});

		var OctHours = $grid.jqGrid('getCol', 'octCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'octCapacity' : OctHours
		});

		var NovHours = $grid.jqGrid('getCol', 'novCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'novCapacity' : NovHours
		});

		var DecHours = $grid.jqGrid('getCol', 'decCapacity', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'decCapacity' : DecHours
		});

		$(".ui-jqgrid-ftable").find('tr').each(function(n, data) {
			$(this).find('td').each(function(i, el) {
				if ($(this).attr("aria-describedby").indexOf("Capacity") > 0) {
					if ($(this).text() > totCap) {
						$(this).css('background-color', 'red');
						$(this).css('color', 'white');
						showCaoError = true;
					} else {
						$(this).css('background-color', 'green');
						$(this).css('color', 'white');
					}

				}
			});
		});
		if (showCaoError) {
			alert('Given Capacity Claimed is more than ' + totCap);
		}
	});
</script>
</head>
<body>

	<s:form theme="simple" method="POST" id="manageCapacityFormId">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent"
			style="width: 100%; background: url('../images/header_bg_shadow.png') repeat-x; height: 22px;">
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
			action="getUserNetworkCodes.action?selectedYear=%{selectedYear}" />
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
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text name="pts.resource.name" />:</td>
							<td align="left" width="200"><s:textfield
									name="searchUserName" id="searchUserName" theme="simple"
									cssStyle="width:175px;" /> <s:hidden name="allReporteesFlag"
									id="allReporteesFlag" value="true" /></td>
							<td style="color: #2779aa;"><s:text
									name="pts.user.supervisor" />:</td>
							<td width="200"><sj:autocompleter
									list="supervisorMapForManage" name="searchSupervisor"
									listKey="key" listValue="value" theme="simple"
									id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa;"><s:text name="pts.year" />:</td>
							<td align="left"><s:select list="yearList"
									name="selectedYear" id="selectedYear"></s:select></td>
							<!-- <select id="tempSelectedYear"
								name="tempSelectedYear" onchange="setYear(this.value)"></select> -->
							<td style="padding-left: 20px;"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../capacity/capacityPlanning.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="capacityGrid.jsp" /></td>
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
