<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<script>
	 document.getElementById("userCapacity").value='<s:property value="userCapacityByRelease"/>';
</script>
<s:div>

	<table style="width: 100%; padding-right: 40px;">
		<tr>
			<td style="color: #2779aa; font-weight: bold; font-size: 15px;"
				align="right"><s:text name="pts.user.weekoff.flag" /> : <s:checkbox
					name="weekOffFlag" id="weekOffFlag" theme="simple"></s:checkbox></td>
			<td style="color: #2779aa; font-weight: bold; font-size: 15px;"
				align="right"><s:text name="Settings" /> : <img
				id="loadingIndicator" onclick="showaddAditionalProjects()"
				src="../images/plus.jpg" style="height: 15px; width: 15px;" /></td>
		</tr>
	</table>
</s:div>
<div id="spacer10px">&nbsp;</div>
<div style="float: right">
	<table id="addAditionalProjects" style="display: none">
		<tbody>
			<tr>
				<td><s:iterator value="projectAssignement" var="item">
						<s:if test=' value.equalsIgnoreCase("Vacation")'>
						</s:if>
						<s:elseif test='value.equalsIgnoreCase("Training Ericsson") '></s:elseif>
						<s:elseif test='value.equalsIgnoreCase("Training Verizon")'></s:elseif>
						<s:elseif test='value.equalsIgnoreCase("VERIZON-COST")'>
							<s:if
								test="#session['username'] eq 'EMAJEMO' || #session['username'] eq 'emajemo'">
								<td id="<s:property     value='value.replace(" ", "_")' />_td"><label
									class="container"
									style="border: 1px solid black !important; border-radius: 17px; padding: 12px; height: auto;">
										<span style="margin-left: 10px;">Management </span> <input
										type="checkbox"
										id="<s:property value='value.replace(" ", "_")' />_check"
										onclick="changeStyle('<s:property
                                                                                value='value.replace(" ", "_")' />')" />
										<span style="margin-top: 10px" class="checkmark"></span>
								</label></td>
							</s:if>

						</s:elseif>
						<s:else>
							<td id="<s:property     value='value.replace(" ", "_")' />_td"><label
								class="container"
								style="border: 1px solid black !important; border-radius: 17px; padding: 12px; height: auto;">
									<span style="margin-left: 10px;"> <s:property
											value="value" />
								</span> <input type="checkbox"
									id="<s:property value='value.replace(" ", "_")' />_check"
									onclick="changeStyle('<s:property
                                                                                value='value.replace(" ", "_")' />')" />
									<span style="margin-top: 10px" class="checkmark"></span>
							</label></td>
						</s:else>
					</s:iterator>
			</tr>
		</tbody>

	</table>
</div>
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
	<s:iterator value="activityCodesMap">
		<s:hidden name="activityCodesMap[%{key}]" value="%{value}" />
	</s:iterator>
	<s:iterator value="selectedNetworkCodesMap">
		<s:hidden name="selectedNetworkCodesMap[%{key}]" value="%{value}" />
	</s:iterator>
	<div id="dvDiv"
		style="display: none; position: absolute; padding: 1px; border: 1px solid #333333; background-color: #fffedf; font-size: smaller; z-index: 999;"></div>
	<s:div cssStyle="background: #FFF;">
		<table>
			<tr>
				<td>
					<table id="activityTable0" class="activityTable">
						<thead>
							<tr style="background-color: #cdcdcd;">
								<td style="background: #FFF;">&nbsp;</td>
								<th>Project/ Assignment</th>
								<th>Activity</th>
								<th>Type</th>
								<s:iterator value="daysinAWeek">
									<th><s:property /></th>
								</s:iterator>
								<th>&nbsp;</th>
								<th>Remaining hours</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<s:if test="activityList.size() > 0 ">
								<s:iterator value="activityList" status="actstat">
									<tr
										id='activityList_tr_<s:property value="%{#actstat.index}"/>'>
										<td><img src="../images/delete_red.png"
											title="Delete Row"
											onclick="deleteRow(this,0, '<s:property value='%{activityList[#actstat.index].id}'/>', '<s:property value='%{activityList[#actstat.index].templateId}'/>' )" />
											<s:hidden theme="simple"
												name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].id'}"
												id="%{'activityList['+#actstat.index+'].id'}"
												value="%{activityList[#actstat.index].id}" /> <s:hidden
												theme="simple"
												name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].templateId'}"
												id="%{'activityList['+#actstat.index+'].templateId'}"
												value="%{activityList[#actstat.index].templateId}" /> <s:hidden
												theme="simple"
												name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].createdBy'}"
												id="%{'activityList['+#actstat.index+'].createdBy'}"
												value="%{activityList[#actstat.index].createdBy}" /> <s:hidden
												theme="simple"
												name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].createdDate'}"
												id="%{'activityList['+#actstat.index+'].createdDate'}"
												value="%{activityList[#actstat.index].createdDate}" /> <s:hidden
												theme="simple"
												name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].disableNw'}"
												id="%{'activityList['+#actstat.index+'].disableNw'}"
												value="%{activityList[#actstat.index].disableNw}" /> <s:hidden
												theme="simple"
												name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].approvalStatus'}"
												id="%{'activityList['+#actstat.index+'].approvalStatus'}"
												value="%{activityList[#actstat.index].approvalStatus}" /></td>
										<td><s:if test="disableNw">
												<s:select class="unselectable"
													list="selectedNetworkCodesMapDeleted"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].networkId'}"
													id="%{'activityList['+#actstat.index+'].networkId'}"
													value="%{activityList[#actstat.index].networkId}"
													onmouseover="onchangeS(this);" theme="simple"
													headerKey="-1" headerValue="Please Select"
													cssStyle="width:328px;"
													td_id='<s:property value="%{#actstat.index}"/>'
													onchange="validateActivityGridNew(this, 0, 'networkId');onchangeS(this);updateUserCapacity(this);"></s:select>
											</s:if> <s:else>
												<s:select list="selectedNetworkCodesMap"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].networkId'}"
													id="%{'activityList['+#actstat.index+'].networkId'}"
													value="%{activityList[#actstat.index].networkId}"
													onmouseover="onchangeS(this);" theme="simple"
													headerKey="-1" headerValue="Please Select"
													cssStyle="width:328px;"
													td_id='<s:property value="%{#actstat.index}"/>'
													onchange="updateUserCapacity(this);validateActivityGridNew(this, 0, 'networkId');onchangeS(this);"></s:select>
											</s:else></td>
										<td><s:if test="disableNw">
												<s:select list="activityCodesMap" class="unselectable"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].activityCode'}"
													id="%{'activityList['+#actstat.index+'].activityCode'}"
													value="%{activityList[#actstat.index].activityCode}"
													onmouseover="onchangeS(this);" theme="simple"
													headerKey="-1" headerValue="Please Select"
													cssStyle="width:120px;"
													td_id='<s:property value="%{#actstat.index}"/>'
													onchange="updateUserCapacity(this);validateActivityGridNew(this, 0, 'activityCode');onchangeS(this);"></s:select>
											</s:if> <s:else>
												<s:select list="activityCodesMap"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].activityCode'}"
													id="%{'activityList['+#actstat.index+'].activityCode'}"
													value="%{activityList[#actstat.index].activityCode}"
													onmouseover="onchangeS(this);" theme="simple"
													headerKey="-1" headerValue="Please Select"
													cssStyle="width:120px;"
													td_id='<s:property value="%{#actstat.index}"/>'
													onchange="updateUserCapacity(this);validateActivityGridNew(this, 0, 'activityCode');onchangeS(this);"></s:select>
											</s:else></td>
										<td><s:if test="disableNw">
												<s:select list="timeSheetTypeMap" headerKey="-1"
													headerValue="Please Select" class="unselectable"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].type'}"
													id="%{'activityList['+#actstat.index+'].type'}"
													value="%{activityList[#actstat.index].type}" theme="simple"
													cssStyle="width:90px;"
													td_id='<s:property value="%{#actstat.index}"/>'
													onchange="updateUserCapacity(this);validateActivityGridNew(this, 0, 'type')"></s:select>
											</s:if> <s:else>
												<s:select list="timeSheetTypeMap" headerKey="-1"
													headerValue="Please Select"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].type'}"
													id="%{'activityList['+#actstat.index+'].type'}"
													value="%{activityList[#actstat.index].type}" theme="simple"
													cssStyle="width:90px;"
													td_id='<s:property value="%{#actstat.index}"/>'
													onchange="updateUserCapacity(this);validateActivityGridNew(this, 0, 'type')"></s:select>
											</s:else></td>

										<td align="center"><s:if test="disableNw">
												<s:textfield class="unselectable"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].monHrs'}"
													id="%{'activityList['+#actstat.index+'].monHrs'}"
													value="%{activityList[#actstat.index].monHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:if> <s:else>
												<s:textfield
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].monHrs'}"
													id="%{'activityList['+#actstat.index+'].monHrs'}"
													value="%{activityList[#actstat.index].monHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:else></td>


										<td align="center"><s:if test="disableNw">
												<s:textfield class="unselectable"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].tueHrs'}"
													id="%{'activityList['+#actstat.index+'].tueHrs'}"
													value="%{activityList[#actstat.index].tueHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:if> <s:else>
												<s:textfield
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].tueHrs'}"
													id="%{'activityList['+#actstat.index+'].tueHrs'}"
													value="%{activityList[#actstat.index].tueHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:else></td>

										<td align="center"><s:if test="disableNw">
												<s:textfield class="unselectable"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].wedHrs'}"
													id="%{'activityList['+#actstat.index+'].wedHrs'}"
													value="%{activityList[#actstat.index].wedHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:if> <s:else>
												<s:textfield
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].wedHrs'}"
													id="%{'activityList['+#actstat.index+'].wedHrs'}"
													value="%{activityList[#actstat.index].wedHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:else></td>

										<td align="center"><s:if test="disableNw">
												<s:textfield class="unselectable"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].thuHrs'}"
													id="%{'activityList['+#actstat.index+'].thuHrs'}"
													value="%{activityList[#actstat.index].thuHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:if> <s:else>
												<s:textfield
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].thuHrs'}"
													id="%{'activityList['+#actstat.index+'].thuHrs'}"
													value="%{activityList[#actstat.index].thuHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:else></td>

										<td align="center"><s:if test="disableNw">
												<s:textfield class="unselectable"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].friHrs'}"
													id="%{'activityList['+#actstat.index+'].friHrs'}"
													value="%{activityList[#actstat.index].friHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:if> <s:else>
												<s:textfield
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].friHrs'}"
													id="%{'activityList['+#actstat.index+'].friHrs'}"
													value="%{activityList[#actstat.index].friHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:else></td>

										<td align="center"><s:if test="disableNw">
												<s:textfield class="unselectable"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].satHrs'}"
													id="%{'activityList['+#actstat.index+'].satHrs'}"
													value="%{activityList[#actstat.index].satHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:if> <s:else>
												<s:textfield
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].satHrs'}"
													id="%{'activityList['+#actstat.index+'].satHrs'}"
													value="%{activityList[#actstat.index].satHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:else></td>

										<td align="center"><s:if test="disableNw">
												<s:textfield class="unselectable"
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].sunHrs'}"
													id="%{'activityList['+#actstat.index+'].sunHrs'}"
													value="%{activityList[#actstat.index].sunHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:if> <s:else>
												<s:textfield
													name="%{'timeSheetRecord.activityListToSave['+#actstat.index+'].sunHrs'}"
													id="%{'activityList['+#actstat.index+'].sunHrs'}"
													value="%{activityList[#actstat.index].sunHrs}"
													theme="simple" cssStyle="width:60px;"
													onkeypress="return validateFloatKeyPress(this,event);"
													onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" />
											</s:else></td>

										<td align="center" style="background: rgb(198, 198, 198);">
											<span
											id="summationtdid<s:property value='%{#actstat.index}'/>"
											style="width: 30px !important;"><s:property
													value="%{activityList[#actstat.index].activitySummation}" /></span>
										</td>
										<td align="center" id="userCapacity_td_<s:property value='%{#actstat.index}'/>"  style="text-align: center;background: rgb(198, 198, 198);">
										<span
											style="text-align: center;"
											id="userCapacity_<s:property value='%{#actstat.index}'/>" /></span></td>

										<td align="center" style="background: rgb(198, 198, 198);"><span
											id="approvalstatusid<s:property value='%{#actstat.index}'/>"><s:property
													value="%{activityList[#actstat.index].approvalStatus}" /></span></td>
										<td valign="bottom" style="width: 15px;"></td>
										<td valign="bottom"><img src="../images/add_row.png"
											trId=" tr_<s:property value="%{#actstat.index}"/>"
											height="18px" title="Add Row" onclick="addRow(0)" /></td>
										<td><input type="checkbox"
											id=" enableClosedPI_<s:property value="%{#actstat.index}"/>"
											name=" enableClosedPI"
											onclick="enableThisRow(this,<s:property value='%{#actstat.index}'/>)"
											title="Click to enable this row"
											placeholder="Click to enable this row" /></td>

									</tr>
								</s:iterator>

							</s:if>
							<s:else>
								<tr id="activityList_tr_0">
									<td><img src="../images/delete_red.png" title="Delete Row"
										onclick="deleteRow(this, 0,'','')" /></td>
									<td><s:select list="%{selectedNetworkCodesMap}"
											name="%{'timeSheetRecord.activityListToSave[0].networkId'}"
											id="%{'activityList[0].networkId'}" theme="simple"
											headerKey="-1" headerValue="Please Select"
											cssStyle="width:328px !important;"
											onchange="validateActivityGridNew(this, 0, 'networkId')"></s:select></td>
									<td><s:select list="activityCodesMap"
											name="%{'timeSheetRecord.activityListToSave[0].activityCode'}"
											id="%{'activityList[0].activityCode'}" theme="simple"
											value="tempActivityCode" headerKey="-1"
											headerValue="Please Select" cssStyle="width:120px;"
											onchange="validateActivityGridNew(this, 0, 'activityCode')"></s:select></td>
									<td><s:select list="timeSheetTypeMap" headerKey="-1"
											headerValue="Please Select"
											name="%{'timeSheetRecord.activityListToSave[0].type'}"
											id="%{'activityList[0].type'}" theme="simple"
											value="vacationType" cssStyle="    width: 120px;   "
											onchange="validateActivityGridNew(this, 0, 'type')"></s:select></td>
									<td align="center"><s:textfield
											name="%{'timeSheetRecord.activityListToSave[0].monHrs'}"
											id="%{'activityList[0].monHrs'}" theme="simple"
											cssStyle="width:60px;"
											onkeypress="return validateFloatKeyPress(this,event);"
											onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
									<td align="center"><s:textfield
											name="%{'timeSheetRecord.activityListToSave[0].tueHrs'}"
											id="%{'activityList[0].tueHrs'}" theme="simple"
											cssStyle="width:60px;"
											onkeypress="return validateFloatKeyPress(this,event);"
											onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
									<td align="center"><s:textfield
											name="%{'timeSheetRecord.activityListToSave[0].wedHrs'}"
											id="%{'activityList[0].wedHrs'}" theme="simple"
											cssStyle="width:60px;"
											onkeypress="return validateFloatKeyPress(this,event);"
											onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
									<td align="center"><s:textfield
											name="%{'timeSheetRecord.activityListToSave[0].thuHrs'}"
											id="%{'activityList[0].thuHrs'}" theme="simple"
											cssStyle="width:60px;"
											onkeypress="return validateFloatKeyPress(this,event);"
											onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
									<td align="center"><s:textfield
											name="%{'timeSheetRecord.activityListToSave[0].friHrs'}"
											id="%{'activityList[0].friHrs'}" theme="simple"
											cssStyle="width:60px;"
											onkeypress="return validateFloatKeyPress(this,event);"
											onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
									<td align="center"><s:textfield
											name="%{'timeSheetRecord.activityListToSave[0].satHrs'}"
											id="%{'activityList[0].satHrs'}" theme="simple"
											cssStyle="width:60px;"
											onkeypress="return validateFloatKeyPress(this,event);"
											onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
									<td align="center"><s:textfield
											name="%{'timeSheetRecord.activityListToSave[0].sunHrs'}"
											id="%{'activityList[0].sunHrs'}" theme="simple"
											cssStyle="width:60px;"
											onkeypress="return validateFloatKeyPress(this,event);"
											onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
									<td align="center" style="background: rgb(198, 198, 198);"><span
										id="summationtdid<s:property value='%{#actstat.index}'/>"><s:property
												value="%{activityList[0].activitySummation}" /></span></td>
									<td align="center" id="userCapacity_td_<s:property value='%{#actstat.index}'/>" style="text-align: center;background: rgb(198, 198, 198);"><span
										style="text-align: center;"
										id="userCapacity_<s:property value='%{#actstat.index}'/>" /></span></td>

									<td valign="bottom" style="width: 15px;"><span
										id="approvalstatusid<s:property value='%{#actstat.index}'/>"><s:property
												value="%{activityList[0].approvalStatus}" /></span></td>
									<td valign="bottom"><img src="../images/add_row.png"
										trId=" tr_<s:property value="%{#actstat.index}"/>"
										height="18px" title="Add Row" onclick="addRow(0)" /></td>
									<td><input type="checkbox"
										id=" enableClosedPI_<s:property value="%{#actstat.index}"/>"
										name=" enableClosedPI"
										onclick="enableThisRow(this,<s:property value='%{#actstat.index}'/>)"
										title="Click to enable this row"
										placeholder="Click to enable this row" /></td>
								</tr>
							</s:else>
						</tbody>
					</table>

				</td>


			</tr>


			<tr>

			</tr>
			<tr></tr>
			<tr>
				<td><table id="activityTable1" style="width: 1088px;"
						class="activityTable1">
						<s:iterator value="activityListProjects" status="actstat">

							<s:if test="%{activitySummation >0}">
								<tr
									id="<s:text name='type.replace(" ", "_")' />_tr_<s:property value="%{#actstat.index}"/>"
									class="<s:text name='type.replace(" ", "_")' />_tr"
									style="display: table-row">
							</s:if>
							<s:else>
								<s:if
									test="projectAssignmentId eq 6 || projectAssignmentId eq 7 || projectAssignmentId eq 8 ">
									<tr
										id="<s:text name='type.replace(" ", "_")' />_tr_<s:property value="%{#actstat.index}"/>"
										class="<s:text name='type.replace(" ", "_")' />_tr"
										style="display: table-row">
								</s:if>
								<s:elseif test="activityCode > 0">
									<tr
										id="<s:text name='type.replace(" ", "_")' />_tr_<s:property value="%{#actstat.index}"/>"
										class="<s:text name='type.replace(" ", "_")' />_tr"
										style="display: table-row">
								</s:elseif>
								<s:else>
									<tr
										id="<s:text name='type.replace(" ", "_")' />_tr_<s:property value="%{#actstat.index}"/>"
										class="<s:text name='type.replace(" ", "_")' />_tr"
										style="display: none">
								</s:else>

							</s:else>

							<td style="width: 15px;"><s:hidden
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].id'}"
									id="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].id'}"
									theme="simple" value="%{id}" /> <s:hidden
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityDesc'}"
									id="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityDesc'}"
									theme="simple" value="%{type}" /> <s:hidden
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityType'}"
									id="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityType'}"
									theme="simple" value="%{activityType}" /> <s:hidden
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].type'}"
									id="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].type'}"
									theme="simple" value="%{projectAssignmentId}" /> <s:hidden
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].networkId'}"
									id="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].networkId'}"
									theme="simple" value="%{networkId}" /></td>
							<td><s:select list="projectAssignement"
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].type'}"
									id="%{#actstat.index}_type" theme="simple" disabled="true"
									value="projectAssignmentId" headerKey="-1"
									headerValue="Please Select" cssStyle="width:344px !important;"></s:select>
							</td>

							<s:if test="%{activityType eq 'PTL'}">
								<td colspan="1"><s:select list="activityCodesProjectMap"
										name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityCode'}"
										id="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityCode'}type"
										theme="simple" value="activityCode" headerKey="-1"
										headerValue="Please Select" cssStyle="width:210px;float:left;"></s:select></td>
							</s:if>
							<s:else>
								<s:if
									test="projectAssignmentId eq 6 || projectAssignmentId eq 7 || projectAssignmentId eq 8 ">
									<td colspan="1"><s:select list="activityCodesPtlMap"
											name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityCode'}"
											id="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityCode'}type"
											theme="simple" value="activityCode" headerKey="-1"
											disabled="true" headerValue="Please Select"
											cssStyle="width:210px;float:left;"></s:select></td>
								</s:if>
								<s:elseif test="projectAssignmentId eq 15">
									<td colspan="1"><s:select
											list="activityCodesManagementMap"
											name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityCode'}"
											id="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityCode'}type"
											theme="simple" value="activityCode" headerKey="-1"
											headerValue="Please Select"
											cssStyle="width:210px;float:left;"></s:select></td>

									<%-- <td colspan="1"><s:select name=""
											list="#{'Project':'Project'}" theme="simple"
											value="activityCode" cssStyle="width:249px;float:left;"></s:select></td> --%>
								</s:elseif>
								<s:else>
									<td colspan="1"><s:select list="activityCodesPtlMap"
											name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityCode'}"
											id="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].activityCode'}type"
											theme="simple" value="activityCode" headerKey="-1"
											headerValue="Please Select"
											cssStyle="width:210px;float:left;"></s:select></td>
								</s:else>
							</s:else>


							<td align="center"><s:textfield
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].monHrs'}"
									id="%{'activityListProjects['+#actstat.index+'].monHrs'}"
									theme="simple" cssStyle="width:60px;" value="%{monHrs}"
									onkeypress="return validateFloatKeyPress(this,event);"
									onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
							<td align="center"><s:textfield
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].tueHrs'}"
									id="%{'activityListProjects['+#actstat.index+'].tueHrs'}"
									theme="simple" cssStyle="width:60px;" value="%{tueHrs}"
									onkeypress="return validateFloatKeyPress(this,event);"
									onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>

							<td align="center"><s:textfield
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].wedHrs'}"
									id="%{'activityListProjects['+#actstat.index+'].wedHrs'}"
									theme="simple" cssStyle="width:60px;" value="%{wedHrs}"
									onkeypress="return validateFloatKeyPress(this,event);"
									onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
							<td align="center"><s:textfield
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].thuHrs'}"
									id="%{'activityListProjects['+#actstat.index+'].thuHrs'}"
									theme="simple" cssStyle="width:60px;" value="%{thuHrs}"
									onkeypress="return validateFloatKeyPress(this,event);"
									onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
							<td align="center"><s:textfield
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].friHrs'}"
									id="%{'activityListProjects['+#actstat.index+'].friHrs'}"
									theme="simple" cssStyle="width:60px;" value="%{friHrs}"
									onkeypress="return validateFloatKeyPress(this,event);"
									onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
							<td align="center"><s:textfield
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].satHrs'}"
									id="%{'activityListProjects['+#actstat.index+'].satHrs'}"
									theme="simple" cssStyle="width:60px;" value="%{satHrs}"
									onkeypress="return validateFloatKeyPress(this,event);"
									onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
							<td align="center"><s:textfield
									name="%{'timeSheetRecord.activityListProductsToSave['+#actstat.index+'].sunHrs'}"
									id="%{'activityListProjects['+#actstat.index+'].sunHrs'}"
									theme="simple" cssStyle="width:60px;" value="%{sunHrs}"
									onkeypress="return validateFloatKeyPress(this,event);"
									onkeyup="calculateSummationNew1(this);updateUserCapacity(this);" /></td>
							<td align="center" style="background: rgb(198, 198, 198);"><span
								id="psummationtdid<s:property value='%{#actstat.index}'/>"><s:property
										value="%{activitySummation}" /></span></td>
							<s:if test="%{approvalStatus eq 'NA'}">
								<td align="center" style="background: rgb(198, 198, 198);"><span
									id="papprovalstatusid<s:property value='%{#actstat.index}'/>">
								</span></td>
							</s:if>
							<s:elseif test="%{approvalStatus neq ''}">
								<td align="center" style="background: rgb(198, 198, 198);"><span
									id="papprovalstatusid<s:property value='%{#actstat.index}'/>"><s:property
											value="%{approvalStatus}" /></span></td>
							</s:elseif>
							<s:else>
								<td align="center"
									style="width: 50px; background: rgb(198, 198, 198);"><span
									id="papprovalstatusid<s:property value='%{#actstat.index}'/>"><s:property
											value="%{approvalStatus}" /></span></td>
							</s:else>
							<td><s:if test=' projectAssignmentId eq 6'>
								</s:if> <s:elseif test='projectAssignmentId eq 7 '></s:elseif> <s:elseif
									test='projectAssignmentId eq 8'></s:elseif> <s:else>
									<img src="../images/delete_red.png" title="Delete Row"
										onclick="deleteactivityrow(this,1, '<s:text name='type.replace(" ", "_")' />_tr_<s:property value="%{#actstat.index}"/>'  )" />
								</s:else></td>
							<td valign="bottom"><s:if test=' projectAssignmentId eq 6'>
								</s:if> <s:elseif test='projectAssignmentId eq 7 '></s:elseif> <s:elseif
									test='projectAssignmentId eq 8'></s:elseif> <s:else>
									<img src="../images/add_row.png" height="18px" title="Add Row"
										trId="<s:text name='type.replace(" ", "_")' />_tr_<s:property value="%{#actstat.index}"/>"
										onclick="addRow_new(this,event)" />
								</s:else></td>

							<script type="text/javascript">
						if(<s:text name="addCheckFlag" /> == true){
							var id="<s:text name='type.replace(" ", "_")' />";
				
							if(document.getElementById(id+ '_check'))document.getElementById(id+ '_check').checked = true;
							var id2="<s:text name='type.replace(" ", "_")' />_tr_<s:property value="%{#actstat.index}"/>";
						$("#"+id2).css({
							'display' : 'table-row'
						});
					}
						 </script>
						</s:iterator>



					</table></td>
			</tr>

			<tr>
				<td><table id="activityTable1" style="width: 1088px;"
						class="activityTable1">

						<tr id="summationId1" class="summationId">
							<td style="width: 15px;"></td>
							<td><p style="width: 538px;"></p></td>
							<td align="left"
								style="background: rgb(198, 198, 198); width: 62px; padding-left: 2px;"
								id="monSummation"><s:property value="totMonSum" /></td>
							<td align="left"
								style="background: rgb(198, 198, 198); width: 62px; padding-left: 2px;"
								id="tueSummation"><s:property value="totTueSum" /></td>
							<td align="left"
								style="background: rgb(198, 198, 198); width: 62px; padding-left: 2px;"
								id="wedSummation"><s:property value="totWedSum" /></td>
							<td align="left"
								style="background: rgb(198, 198, 198); width: 62px; padding-left: 2px;"
								id="thuSummation"><s:property value="totThuSum" /></td>
							<td align="left"
								style="background: rgb(198, 198, 198); width: 62px; padding-left: 2px;"
								id="friSummation"><s:property value="totFriSum" /></td>
							<td align="left"
								style="background: rgb(198, 198, 198); width: 62px; padding-left: 2px;"
								id="satSummation"><s:property value="totSatSum" /></td>
							<td align="left"
								style="background: rgb(198, 198, 198); width: 62px; padding-left: 2px;"
								id="sunSummation"><s:property value="totSunSum" /></td>
							<td align="center" bgcolor="#ce9c00"
								style="width: 24px; padding-left: 2px;" id="weekSummation"><s:property
									value="totSummation" /></td>
							<td align="center"
								style="width: 8px; background: rgb(198, 198, 198);"><p></p>
							</td>
							<td align="center"
								style="width: 8px; background: rgb(198, 198, 198);"><p></p>
							</td>

						</tr>
					</table></td>
			</tr>
		</table>
	</s:div>
</div>
	