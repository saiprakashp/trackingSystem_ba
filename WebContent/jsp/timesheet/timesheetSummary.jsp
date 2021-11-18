<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<script>
	var activityString = '<tr id="activityTrId@@listIndex@@">'
			+ '<td align="center">'
			+ '<table style="width:98%;"><tr style="background: rgb(173, 214, 181);">'
			+ '<th align="left">@@netowrkCode@@</th>'
			+ '<th align="left">@@activityCode@@</th>'
			+ '<th align="left">@@timesheetType@@</th></tr>'
			+ '<tr><td colspan="3" align="right"><table style="width:100%;"><tr id="activityInnerTRID@@listIndex@@" style="background: rgb(222, 239, 222);" onmousedown="selectedRowValue(this,'
			+ "'activityTrId@@listIndex@@', '0'"
			+ ')" >'
			+ '<td align="center">@@monHrs@@</td>'
			+ '<td align="center">@@tueHrs@@</td>'
			+ '<td align="center">@@wedHrs@@</td>'
			+ '<td align="center">@@thuHrs@@</td>'
			+ '<td align="center">@@friHrs@@</td>'
			+ '<td align="center">@@satHrs@@</td>'
			+ '<td align="center">@@sunHrs@@</td>'
			+ '<td align="center" style="background: rgb(198, 198, 198);">@@activitySummation@@'
			+ '<input type="hidden" name="activityListToSave[@@listIndex@@].networkId" id="activityListToSave[@@listIndex@@].networkId" value="@@netowrkCodeId@@"/>'
			+ '<input type="hidden" name="activityListToSave[@@listIndex@@].activityCode" id="activityListToSave[@@listIndex@@].activityCode" value="@@activityCodeId@@"/>'
			+ '<input type="hidden" name="activityListToSave[@@listIndex@@].type" id="activityListToSave[@@listIndex@@].type" value="@@timeSheetTypeId@@"/>'
			+ '<input type="hidden" name="activityListToSave[@@listIndex@@].monHrs" id="activityListToSave[@@listIndex@@].monHrs" value="@@monHrs@@"/>'
			+ '<input type="hidden" name="activityListToSave[@@listIndex@@].tueHrs" id="activityListToSave[@@listIndex@@].tueHrs" value="@@tueHrs@@"/>'
			+ '<input type="hidden" name="activityListToSave[@@listIndex@@].wedHrs" id="activityListToSave[@@listIndex@@].wedHrs" value="@@wedHrs@@"/>'
			+ '<input type="hidden" name="activityListToSave[@@listIndex@@].thuHrs" id="activityListToSave[@@listIndex@@].thuHrs" value="@@thuHrs@@"/>'
			+ '<input type="hidden" name="activityListToSave[@@listIndex@@].friHrs" id="activityListToSave[@@listIndex@@].friHrs" value="@@friHrs@@"/>'
			+ '<input type="hidden" name="activityListToSave[@@listIndex@@].satHrs" id="activityListToSave[@@listIndex@@].satHrs" value="@@satHrs@@"/>'
			+ '<input type="hidden" name="activityListToSave[@@listIndex@@].sunHrs" id="activityListToSave[@@listIndex@@].sunHrs" value="@@sunHrs@@"/>'
			+ '</td></tr></table></td></tr></table>' + '</td></tr>';
	var selectedTR = "";
	var selectedId = "";
	function selectedRowValue(currTR, obj, selectedIdTodelete) {
		selectedTR = obj;
		selectedId = selectedIdTodelete;
		$("tr[id*='activityInnerTRID']").each(function() {
			$(this).css({
				"background" : "rgb(222, 239, 222)"
			});
		});
		currTR.style.backgroundColor = "#F7FF96";
	}
	function deleteRow() {
		$('#activityTable tr#' + selectedTR).remove();
		$('#removedIds').val($('#removedIds').val() + ', ' + selectedId);
		var i = 0;
		$("tr[id*='activityInnerTRID']")
				.each(
						function() {

							$(this)
									.find("input[type=hidden]")
									.each(
											function() {
												$(this)
														.attr(
																{
																	'id' : function(
																			_,
																			id) {
																		return (id
																				.substr(
																						0,
																						id
																								.indexOf('[') + 1)
																				+ (i) + id
																				.substr(id
																						.indexOf(']')));
																	},
																	'name' : function(
																			_,
																			name) {
																		return (name
																				.substr(
																						0,
																						name
																								.indexOf('[') + 1)
																				+ (i) + name
																				.substr(name
																						.indexOf(']')));
																	}
																});
											});
							i++;
						});
		calculateSummation();
	}
	function addRow() {
		var networkCodeId = $('#selectedNetworkCodesMapId option:selected')
				.val();
		var networkCodeDesc = $("#selectedNetworkCodesMapId option:selected")
				.text();
		var activityCodeId = $('#activityCodesMapdId option:selected').val();
		var activityCodeDesc = $('#activityCodesMapdId option:selected').text();

		var timeSheetTypeId = $('#timeSheetTypeMapId option:selected').val();
		var timeSheetTypeDesc = $('#timeSheetTypeMapId option:selected').text();
		var monHrs = $('#Mon').val();
		var tueHrs = $('#Tue').val();
		var wedHrs = $('#Wed').val();
		var thuHrs = $('#Thu').val();
		var friHrs = $('#Fri').val();
		var satHrs = $('#Sat').val();
		var sunHrs = $('#Sun').val();
		var activityTRString = activityString;
		var summation = 0.0;
		if (monHrs == '') {
			monHrs = 0.0;
		}
		if (tueHrs == '') {
			tueHrs = 0.0;
		}
		if (wedHrs == '') {
			wedHrs = 0.0;
		}
		if (thuHrs == '') {
			thuHrs = 0.0;
		}
		if (friHrs == '') {
			friHrs = 0.0;
		}
		if (satHrs == '') {
			satHrs = 0.0;
		}
		if (sunHrs == '') {
			sunHrs = 0.0;
		}
		summation = parseFloat(monHrs) + parseFloat(tueHrs)
				+ parseFloat(wedHrs) + parseFloat(thuHrs) + parseFloat(friHrs)
				+ parseFloat(satHrs) + parseFloat(sunHrs);

		var alertMsg = '';
		if (networkCodeId == '') {
			networkCodeDesc = '';
		}
		if (activityCodeId == '') {
			activityCodeDesc = '';
		}

		if (timeSheetTypeId == '') {
			timeSheetTypeDesc = '';
		}

		if (timeSheetTypeId == ''
				|| (timeSheetTypeId != 'Leave' && timeSheetTypeId != 'Holiday')) {
			if (networkCodeId == '') {
				alertMsg = 'Please select Network Code';
			}
			if (activityCodeId == '') {
				if (alertMsg.length > 1) {
					alertMsg = alertMsg + '\nPlease select Activity Code';
				} else {
					alertMsg = 'Please select Activity Code';
				}
			}
			if (timeSheetTypeId == '') {
				if (alertMsg.length > 1) {
					alertMsg = alertMsg + '\nPlease select Type';
				} else {
					alertMsg = 'Please select Type';
				}
			}
		}
		if (timeSheetTypeId != ''
				&& (timeSheetTypeId == 'Leave' || timeSheetTypeId == 'Holiday')) {
			networkCodeDesc = timeSheetTypeId;
		}
		if (alertMsg.length > 1) {
			alert(alertMsg);
			return false;
		}
		var i = $('#activityTable > tbody > tr').length - 1;

		activityTRString = activityTRString.replace("@@netowrkCode@@",
				networkCodeDesc).replace("@@activityCode@@", activityCodeDesc);
		activityTRString = activityTRString.replace("@@netowrkCodeId@@",
				networkCodeId).replace("@@activityCodeId@@", activityCodeId);
		if (timeSheetTypeId != '' && timeSheetTypeId != 'Leave'
				&& timeSheetTypeId != 'Holiday') {
			activityTRString = activityTRString.replace("@@timesheetType@@",
					timeSheetTypeDesc);
		} else {
			activityTRString = activityTRString
					.replace("@@timesheetType@@", "");
		}
		activityTRString = activityTRString.replace("@@timeSheetTypeId@@",
				timeSheetTypeId);
		activityTRString = activityTRString.replace(/@@monHrs@@/g, monHrs)
				.replace(/@@tueHrs@@/g, tueHrs).replace(/@@wedHrs@@/g, wedHrs);
		activityTRString = activityTRString.replace(/@@thuHrs@@/g, thuHrs)
				.replace(/@@friHrs@@/g, friHrs).replace(/@@satHrs@@/g, satHrs)
				.replace(/@@sunHrs@@/g, sunHrs);
		activityTRString = activityTRString.replace("@@activitySummation@@",
				summation);
		activityTRString = activityTRString.replace(/@@listIndex@@/g, i);
		$('#activityTable tr.summationId').before(activityTRString);
		calculateSummation();
	}
	function calculateSummation() {
		var weekSummation = '0.0';
		var monSummation = '0.0';
		var tueSummation = '0.0';
		var wedSummation = '0.0';
		var thuSummation = '0.0';
		var friSummation = '0.0';
		var satSummation = '0.0';
		var sunSummation = '0.0';
		$('#activityTable > tbody > tr').each(function() {
			$(this).find("input[id$='.monHrs']").each(function() {
				var monVal = $(this).val();
				monSummation = parseFloat(monSummation) + parseFloat(monVal);
			});
			$(this).find("input[id$='.tueHrs']").each(function() {
				var tueVal = $(this).val();
				tueSummation = parseFloat(tueSummation) + parseFloat(tueVal);
			});
			$(this).find("input[id$='.wedHrs']").each(function() {
				var wedVal = $(this).val();
				wedSummation = parseFloat(wedSummation) + parseFloat(wedVal);
			});
			$(this).find("input[id$='.thuHrs']").each(function() {
				var thuVal = $(this).val();
				thuSummation = parseFloat(thuSummation) + parseFloat(thuVal);
			});
			$(this).find("input[id$='.friHrs']").each(function() {
				var friVal = $(this).val();
				friSummation = parseFloat(friSummation) + parseFloat(friVal);
			});
			$(this).find("input[id$='.satHrs']").each(function() {
				var satVal = $(this).val();
				satSummation = parseFloat(satSummation) + parseFloat(satVal);
			});
			$(this).find("input[id$='.sunHrs']").each(function() {
				var sunVal = $(this).val();
				sunSummation = parseFloat(sunSummation) + parseFloat(sunVal);
			});
		});
		$('#monSummation').html(monSummation);
		$('#tueSummation').html(tueSummation);
		$('#wedSummation').html(wedSummation);
		$('#thuSummation').html(thuSummation);
		$('#friSummation').html(friSummation);
		$('#satSummation').html(satSummation);
		$('#sunSummation').html(sunSummation);
		weekSummation = parseFloat(monSummation) + parseFloat(tueSummation)
				+ parseFloat(wedSummation) + parseFloat(thuSummation)
				+ parseFloat(friSummation) + parseFloat(satSummation)
				+ parseFloat(sunSummation);
		$('#weekSummation').html(weekSummation);
	}
</script>
<s:if
	test="menuClick == null || (menuClick != null && menuClick neq 'view')">
	<div style="border-bottom: solid 1px black;">
		&nbsp;<b>Time item entry</b>
		<div id="spacer1px">&nbsp;</div>
		<table style="width: 100%;">
			<tr style="background-color: #cdcdcd;">
				<th>Network/ Other assignment</th>
				<th>Activity</th>
				<th>Type</th>
				<!-- <th>Remaining Hours (+ 5%)</th>-->
				<s:iterator value="daysinAWeek">
					<th><s:property /></th>
				</s:iterator>
			</tr>
			<tr>
				<td><s:select list="selectedNetworkCodesMap"
						name="selectedNetworkCodeId" id="selectedNetworkCodesMapId"
						cssStyle="width:170px;" theme="simple" headerKey=""
						headerValue="Please Select"></s:select> <s:url id="remoteurl"
						action="getRemainingHrsForNetworkCode" /></td>
				<td><s:select list="activityCodesMap" id="activityCodesMapdId"
						cssStyle="width:120px;" theme="simple" headerKey=""
						headerValue="Please Select"></s:select></td>
				<td><s:select list="timeSheetTypeMap" id="timeSheetTypeMapId"
						cssStyle="width:120px;" theme="simple" headerKey=""
						headerValue="Please Select"></s:select></td>
				<!-- <td>
<s:div id="remainingHrsDiv"><s:property value="remainingHrs"/> </s:div>
</td> -->
				<s:iterator value="daysinAWeek" status="stat" var="day">
					<td><s:textfield name='%{#day.substring(0,3)}'
							id="%{#day.substring(0,3)}" cssStyle="width:40px" theme="simple"
							onkeypress="return validateFloatKeyPress(this,event);" /></td>
				</s:iterator>
			</tr>
			<tr>
			</tr>
			<tr>
				<td colspan="9" align="right" style="padding-right: 10px;"><span
					class="buttonStandard_Secondary"><input type="button"
						class="buttonStandard_Secondary2" value="Add To Timesheet"
						id="add" onclick="addRow()" /></span>&nbsp; <span
					class="buttonStandard_Secondary"><input type="button"
						class="buttonStandard_Secondary2" value="Remove from Timesheet"
						id="add" onclick="deleteRow()" /></span></td>
			</tr>
		</table>
		<s:hidden id="removedIds" name="removedIds" />
	</div>
</s:if>
<div id="spacer10px">&nbsp;</div>
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
<div>
	&nbsp;<b>Timesheet for period <s:property value="periodOfWeek" /></b>
</div>
<div>
	<table style="width: 100%;" id="activityTable">
		<s:iterator value="%{new int[activityListCount]}" status="stat">
			<tr id="<s:property value='%{\"activityTrId\"+#stat.index}'/>">
				<td align="center">
					<table style="width: 98%;">
						<tr style="background: rgb(173, 214, 181);">
							<s:if
								test="activityList[#stat.index].networkIdDesc == null || activityList[#stat.index].networkIdDesc == ''">
								<th align="left"><s:property
										value="%{activityList[#stat.index].type}" /></th>
							</s:if>
							<s:else>
								<th align="left"><s:property
										value="%{activityList[#stat.index].networkIdDesc}" /></th>
								<th align="left"><s:property
										value="%{activityList[#stat.index].activityDesc}" /></th>
								<th align="left"><s:property
										value="%{activityList[#stat.index].type}" /></th>
							</s:else>
						</tr>
						<tr>
							<td colspan="3" align="right">
								<table style="width: 100%;">
									<tr
										id="<s:property value='%{\"activityInnerTRID\"+#stat.index}'/>"
										style="background: rgb(222, 239, 222);"
										onmousedown='selectedRowValue(this,"<s:property value='%{\"activityTrId\"+#stat.index}'/>","<s:property value='%{activityList[#stat.index].id}'/>")'>
										<td align="center"><s:property
												value="%{activityList[#stat.index].monHrs}" /></td>
										<td align="center"><s:property
												value="%{activityList[#stat.index].tueHrs}" /></td>
										<td align="center"><s:property
												value="%{activityList[#stat.index].wedHrs}" /></td>
										<td align="center"><s:property
												value="%{activityList[#stat.index].thuHrs}" /></td>
										<td align="center"><s:property
												value="%{activityList[#stat.index].friHrs}" /></td>
										<td align="center"><s:property
												value="%{activityList[#stat.index].satHrs}" /></td>
										<td align="center"><s:property
												value="%{activityList[#stat.index].sunHrs}" /></td>
										<td align="center" style="background: rgb(198, 198, 198);"><s:property
												value="%{activityList[#stat.index].activitySummation}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].id'}"
												id="%{'activityListToSave['+#stat.index+'].id'}"
												value="%{activityList[#stat.index].id}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].networkId'}"
												id="%{'activityListToSave['+#stat.index+'].networkId'}"
												value="%{activityList[#stat.index].networkId}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].activityCode'}"
												id="%{'activityListToSave['+#stat.index+'].activityCode'}"
												value="%{activityList[#stat.index].activityCode}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].type'}"
												id="%{'activityListToSave['+#stat.index+'].type'}"
												value="%{activityList[#stat.index].type}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].monHrs'}"
												id="%{'activityListToSave['+#stat.index+'].monHrs'}"
												value="%{activityList[#stat.index].monHrs}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].tueHrs'}"
												id="%{'activityListToSave['+#stat.index+'].tueHrs'}"
												value="%{activityList[#stat.index].tueHrs}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].wedHrs'}"
												id="%{'activityListToSave['+#stat.index+'].wedHrs'}"
												value="%{activityList[#stat.index].wedHrs}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].thuHrs'}"
												id="%{'activityListToSave['+#stat.index+'].thuHrs'}"
												value="%{activityList[#stat.index].thuHrs}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].friHrs'}"
												id="%{'activityListToSave['+#stat.index+'].friHrs'}"
												value="%{activityList[#stat.index].friHrs}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].satHrs'}"
												id="%{'activityListToSave['+#stat.index+'].satHrs'}"
												value="%{activityList[#stat.index].satHrs}" /> <s:hidden
												theme="simple"
												name="%{'activityListToSave['+#stat.index+'].sunHrs'}"
												id="%{'activityListToSave['+#stat.index+'].sunHrs'}"
												value="%{activityList[#stat.index].sunHrs}" /></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</s:iterator>

		<tr id="summationId" class="summationId">
			<td align="center">
				<table style="width: 98%;">
					<tr>
						<td colspan="2" align="right">
							<table style="width: 100%;">
								<tr style="background: rgb(198, 198, 198);">
									<td align="center" id="monSummation"><s:property
											value="totMonSum" /></td>
									<td align="center" id="tueSummation"><s:property
											value="totTueSum" /></td>
									<td align="center" id="wedSummation"><s:property
											value="totWedSum" /></td>
									<td align="center" id="thuSummation"><s:property
											value="totThuSum" /></td>
									<td align="center" id="friSummation"><s:property
											value="totFriSum" /></td>
									<td align="center" id="satSummation"><s:property
											value="totSatSum" /></td>
									<td align="center" id="sunSummation"><s:property
											value="totSunSum" /></td>
									<td align="center" bgcolor="#ce9c00" id="weekSummation"><s:property
											value="totSummation" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>

	</table>
</div>