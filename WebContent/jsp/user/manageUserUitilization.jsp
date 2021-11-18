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
<style type="text/css">
.buttonDownload {
	border: 1px solid #aed0ea;
	background: #d7ebf9 url(images/ui-bg_glass_80_d7ebf9_1x400.png) 50% 50%
		repeat-x;
	text-decoration: none;
	padding: 3px;
	font-size: 13px;
	font-weight: bold;
	color: darkslategrey;
}
</style>
<script type="text/javascript">
	datePick = function(element) {
		$(element).datepicker();
	};
	$.subscribe('complete', function(event, data) {
		setTimeout(alert(document.getElementById('messageId').value), 100);

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

	$.subscribe('gridComplete', function(event, data) {
		var $grid = $('#gridmultitable');
		$grid.jqGrid('footerData', 'set', {
			'supervisorName' : 'Total'
		});

		var targetHours = $grid.jqGrid('getCol', 'targetHours', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'targetHours' : parseFloat(targetHours).toFixed(2)
		});

		var essHrs = $grid.jqGrid('getCol', 'essHrs', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'essHrs' : essHrs
		});

		var actualHours = $grid.jqGrid('getCol', 'actualHours', false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'actualHours' : actualHours
		});

		var differenceHrs = $grid.jqGrid('getCol', 'differenceHrs', false,
				'sum');
		$grid.jqGrid('footerData', 'set', {
			'differenceHrs' : differenceHrs
		});

		var differencePtsHrs = $grid.jqGrid('getCol', 'differencePtsHrs',
				false, 'sum');
		$grid.jqGrid('footerData', 'set', {
			'differencePtsHrs' : differencePtsHrs
		});

		var avg = $grid.jqGrid('getCol', 'percentage', false, 'avg');
		$grid.jqGrid('footerData', 'set', {
			'percentage' : parseFloat(avg).toFixed(2)
		});

	});

	function exportTableToExcel(id,fileName=''){
	    var downloadLink;
	    var dataType = 'application/vnd.ms-excel';
	    var tableSelect = document.getElementById(id);
	    var tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');;
	    // Specify file name
	    filename = fileName+'_'+new Date().getDate()+"_"+new Date().getMonth()+"_"+new Date().getYear()+"_"+new Date().getMinutes()+'.xls';
	    
	    // Create download link element
	    downloadLink = document.createElement("a");
	    
	    document.body.appendChild(downloadLink);
	    
	    if(navigator.msSaveOrOpenBlob){
	        var blob = new Blob(['\ufeff', tableHTML], {
	            type: dataType
	        });
	        navigator.msSaveOrOpenBlob( blob, filename);
	    }else{
	        // Create a link to the file
	        downloadLink.href = 'data:' + dataType + ', ' + tableHTML;
	    
	        // Setting the file name
	        downloadLink.download = filename;
	        
	        // triggering the function
	        downloadLink.click();
	    }
	}
	
	
</script>
</head>
<body>
	<s:form theme="simple" method="POST" id="manageUserUtilizationFormId">
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
					<s:text name="pts.menu.resource.utilization" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.resource.utilization" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text
									name="pts.user.supervisor" />:</td>
							<td width="200"><sj:autocompleter
									list="allSupervisorMapForManage" name="searchSupervisor"
									listKey="key" listValue="value" theme="simple"
									id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>
							<td style="color: #2779aa; padding-left: 20px;"><s:text
									name="pts.year" />:</td>
							<td align="left" width="200"><sj:autocompleter list="yearList"
									name="selectedYear" id="selectedYear" theme="simple" selectBox="true" selectBoxIcon="true"></sj:autocompleter>
							</td>
							<td style="color: #2779aa; padding-left: 20px;"><s:text
									name="pts.month" />:</td>
							<td align="left" width="200"><sj:autocompleter list="monthList"
									name="selectedMonth" id="selectedMonth" theme="simple" selectBox="true" selectBoxIcon="true"></sj:autocompleter>
							</td>
							<td align="left" style="padding-left: 20px;"><s:checkbox
									name="descrepencyFlag" id="descrepencyFlag" theme="simple"></s:checkbox>
							</td>
							<td style="color: #2779aa;" align="right">Discrepancy</td>
							<td style="padding-left: 20px;"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../user/utilization.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="userUtilizationGrid.jsp" /></td>
					</tr>
				</table>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div style="padding-right: 8px; text-align: right;">

				<s:if test="#session['role'] eq 'LINE MANAGER'">
					<a onclick="exportTableToExcel('gview_gridmultitable','Resource_Utilization')" class="buttonDownload">Download</a>

					<sj:a href="../user/sendMailResourceUtilization.action"
						cssClass="buttonDownload" formIds="manageUserUtilizationFormId"
						targets="mailDivId" onCompleteTopics="complete"
						indicator="indicator" onclick="clearErrorDiv()"
						cssStyle="margin-top:3px;">
						 Send Email
					</sj:a>
					<img id="indicator" src="../images/indicatorImg.gif" alt=""
						style="display: none" />
				</s:if>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
			<sj:div id="mailDivId">
			</sj:div>
			<div id="networkCodeDiv"></div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>