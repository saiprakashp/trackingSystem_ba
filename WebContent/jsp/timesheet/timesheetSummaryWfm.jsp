<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:div cssStyle="margin: 0px auto;" id="errorDiv" theme="simple">
	<table style="margin: 0 auto;">
		<tr>
			<td align="center" class="dateTimePicker"><s:fielderror
					theme="simple" cssStyle="margin-left:10px;color: RED" /> <s:actionerror
					theme="simple" cssStyle="margin-left:10px;color: RED" /> <s:actionmessage
					theme="simple" cssStyle="margin-left:10px;color: RED" /></td>
		</tr>
	</table>
</s:div>
<div>

	<div id="dvDiv"
		style="overflow-x: auto; display: none; position: absolute; padding: 1px; border: 1px solid #333333;; background-color: #fffedf; font-size: smaller; z-index: 999; border-collapse: collapse; border-spacing: 0; width: 100%; border: 1px solid #ddd;"></div>

	<s:div cssStyle="background: #FFF; overflow-x:auto;">
		<table style="margin: auto; overflow-x: auto; background: white;"
			class="">
			<tr>
				<th>Please Insert Log in and Log Out time</th>
			</tr>
			<tr>
				<th></th>
			</tr>
			<tr>
				<td>
					<table id="activityTable0"
						class="  ui-jqgrid ui-widget ui-widget-content ui-corner-all"
						style="border-collapse: collapse; border-spacing: 0; width: 100%;">
						<tr style="background-color: #cdcdcd;"
							class="ui-jqgrid-labels ui-sortable">
							<th class="ui-state-default ui-th-column ui-th-ltr">&nbsp;</th>
							<s:iterator value="daysinAWeekWFM">
								<th class="ui-state-default ui-th-column ui-th-ltr"><s:property /></th>
							</s:iterator>
							<th class="ui-state-default ui-th-column ui-th-ltr">&nbsp;</th>
						</tr>
						<tr class="ui-widget-content jqgrow ui-row-ltr ">
							<td></td>
							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"  displayFormat="h:mm TT"
									timepickerShowHour="true" timepickerShowMinute="true"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].monHrsOn'}" /></td>

							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT" displayFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].monHrsOff'}" /></td>

							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].tueHrsOn'}" /></td>
							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].tueHrsOff'}" /></td>

							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].wedHrsOn'}" /></td>
							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].wedHrsOff'}" /></td>

							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].thuHrsOn'}" /></td>
							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].thuHrsOff'}" /></td>

							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].friHrsOn'}" /></td>
							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].friHrsOff'}" /></td>

							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].satHrsOn'}" /></td>
							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].satHrsOff'}" /></td>

							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].sunHrsOn'}" /></td>
							<td align="center" class="dateTimePicker"><sj:datepicker
									timepicker="true" onAlwaysTopics="" timepickerStepMinute="1"
									timepickerOnly="true" timepickerFormat="h:mm TT"
									timepickerAmPm="true" buttonImageOnly="false"
									cssStyle="width:58px;"
									name="%{'activityListToSaveWfm[0].sunHrsOff'}" /></td>


						</tr>
					</table>
				</td>

			</tr>

		</table>
	</s:div>
</div>