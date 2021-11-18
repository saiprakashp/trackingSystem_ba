<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<script>
	function getClosedPI() {
		document.getElementById("showAllMyContributions").value = document
				.getElementById("showAllMyContributions1").checked;
		submitForm('../login/showDashboard.action')
	}
	function cancelButton() {
		$('#holidaydialog').dialog('close');
	};
	var ptsPercent = false;
	var essPercent = false;

	function showHrs(type) {
		if (type === 'pts') {
			if ($('#ptsCheck').is(":checked")) {

				$("#contentarea").css("width", "1200px");
				$("#allbreadcrumbDiv").css("width", "1200px");
				$("#allmargin").css("width", "1200px");
				$("#allbreadcrumbDiv").css("background", "white");
				$('.ptsPercent').show();
			} else {
				$("#contentarea").css("width", "1000px");
				$("#allbreadcrumbDiv").css("width", "1000px");
				$("#allmargin").css("width", "980px");
				$("#allbreadcrumbDiv").css("background", "white");
				$('.ptsPercent').hide();
			}
		}
		if (type === 'ess') {
			if ($('#essCheck').is(":checked")) {

				$("#contentarea").css("width", "1200px");
				$("#allmargin").css("width", "1200px");
				$("#allbreadcrumbDiv").css("width", "1200px");
				$("#allbreadcrumbDiv").css("background", "white");
				$('.essPercent').show();

			} else {
				$("#contentarea").css("width", "1000px");
				$("#allbreadcrumbDiv").css("width", "1000px");
				$("#allmargin").css("width", "980px");
				$("#allbreadcrumbDiv").css("background", "white");
				$('.essPercent').hide();
			}
		}
	}
</script>
<div style="border: 1px solid; border-radius: 5px; v-align: top;">
	<sj:dialog id="holidaydialog" autoOpen="false" modal="true"
		cssStyle="valign:top;" disabled="true" width="auto"
		buttons="{'Close':function() { cancelButton(); }
			}"
		title="RICO Holiday List">
		<sj:tabbedpanel id="localtabs">
			<sj:tab id="tab1" target="manaHolid" label="US" cssStyle="width:48%;" />
			<sj:tab id="tab2" target="egilHolid" label="EGI"
				cssStyle="width:48%;" />
			<div id="manaHolid" hidden="false">
				<img alt="RICO US Holiday List" style="width: 615px;"
					src="../images/RICO_US_Holidays.png" />
			</div>
			<div id="egilHolid" hidden="true">
				<img alt="RICO EGI Holiday List" style="width: 615px;"
					src="../images/RICO_EGI_Holidays.png" />
			</div>
		</sj:tabbedpanel>
	</sj:dialog>
	<div style="width: 100%;">
		<table style="width: 100%;">
			<tr>
				<td align="left" style="width: 40px;"><s:if
						test="#session['role'] neq 'ADMIN'">
						<a href='../timesheet/logTimesheet.action'
							title="Record Timesheet"> <img alt="Record Timesheet"
							src="../images/forbidden_php.png" height="75" width="75">
						</a>
					</s:if></td>
				<s:if
					test="#session['role'] != null && #session['role'] eq 'LINE MANAGER'">
					<td align="right" width="380px">
						<table id="dashboardtable">
							<thead>
								<tr>
									<th align="center" colspan="8">Head Count (<s:property
											value="totalHeadCnt" />)
									</th>

								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Onboard</td>
									<td><s:property value="onboardCnt" /></td>
									<td>Selected</td>
									<td><s:property value="selectedCnt" /></td>
									<td>LTA</td>
									<td><s:property value="ltaCnt" /></td>
									<td>Interns</td>
									<td><s:property value="Interns" /></td>
								</tr>
								<tr>
									<td>Induction</td>
									<td><s:property value="inductionCnt" /></td>
									<td>Open</td>
									<td><s:property value="openCnt" /></td>
									<td>Notice Period</td>
									<td><s:property value="noticePeriodCnt" /></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</td>
					<td align="left" width="250px">
						<table id="dashboardtable">
							<thead>
								<tr>
									<th align="center">Un-Approved Hours</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td align="center"><s:a
											href="../timesheet/goApproveTimesheet.action">
											<s:property value="unapprovedHrs" />
										</s:a></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</tbody>
						</table>
					</td>
				</s:if>
				<s:else>
					<td align="left" width="250px">
						<table id=" ">
							<tbody>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</tbody>
						</table>
				</s:else>

				<td style="width: 40px;" align="right"><s:if
						test="#session['role'] neq 'ADMIN'">
						<a href='../others/goGetfeedback.action' title="FeedBack Form"> <img
							alt="FeedBack Form" class="rounded" src="../images/feedback.png"
							style="width: 95px; height: 92px;">
						</a>
					</s:if></td>

				<td style="width: 40px;" align="right"><s:if
						test="#session['role'] neq 'ADMIN'">
						<a href='../matrix/RICO-Escalation-Matrix-2021.xlsx'
							title="Escalation Matrix"> <img alt="Escalation Matrix"
							class="rounded" src="../images/escalationicon.jpg" height="75"
							width="90">
						</a>
					</s:if></td>

				<td style="width: 40px;" align="right"><sj:a
						title="Holiday List"
						cssStyle="cursor:pointer;color:red;text-decoration: underline;"
						openDialog="holidaydialog" button="false">
						<img alt="Holiday List" src="../images/holiday_calendar.png"
							height="75" width="75">
					</sj:a></td>
			</tr>
		</table>
	</div>

	<div class="row">
		<div class="column">
			<table>
				<%-- 
				<tr>
					<td valign="top"><s:if
							test="#session['role'] != null && !(#session['role'] eq 'ADMIN') ">
							<s:if
								test="dashboardUserNCList != null && dashboardUserNCList.size() > 0">
								<div style="width: 473px; overflow-y: scroll; height: 158px;">
									<table id="dashboardtable"
										style="width: 459px; overflow-y: scroll; height: 158px;">
										<thead>
											<tr>
												<th align="center" colspan="3">Recently charged
													Projects</th>

											</tr>
										</thead>
										<tr>
											<th style="text-align: center;">Project</th>
											<th style="text-align: center;">Activity</th>
											<th style="text-align: center;">Effort</th>

										</tr>

										<s:iterator value="dashboardUserNCList">
											<tr>
												<td style="text-align: center;"><s:property
														value="NETWORKCODE" /></td>
												<td style="text-align: center;"><s:property
														value="ACTIVITYCODE" /></td>
												<td style="text-align: center;"><s:if
														test="SUMMATION eq null || SUMMATION eq ''">0.0</s:if> <s:else>
														<s:property value="SUMMATION" />
													</s:else></td>
											</tr>
										</s:iterator>

									</table>
								</div>
							</s:if>

						</s:if></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr> --%>
				<tr>
					<td valign="top"><s:if
							test="dashboardUserNCLOEList != null && dashboardUserNCLOEList.size() > 0">
							<div
								style="width: 578px !important; overflow-y: auto; height: 506px !important; min-height: 50px !important;">
								<table id="dashboardtable"
									style="width: 561px !important; overflow-y: auto; height: auto !important;">
									<thead>
										<tr>
											<th align="center" colspan="4">PI utilization report</th>
										</tr>
										<!-- 	<tr>
											<th align="center" colspan="2"><label
												for="showAllMyContributions">Show All My
													Contributions</label> <input type="checkbox"
												id="showAllMyContributions"
												placeholder="showAllMyContributions"
												name="showAllMyContributions" /></th>
											<th align="center" colspan="2"><label
												for="showImplemented">Show Implemented</label> <input
												type="checkbox" id="showImplemented"
												placeholder="showImplemented" name="showImplemented" /></th>
										</tr> -->
										<tr>

											<th align="center" colspan="4"><input type="hidden"
												name="showAllMyContributions" id="showAllMyContributions" />
												<s:if test="showAllMyContributions">
													<input type="checkbox" onclick="getClosedPI()"
														checked="checked" id="showAllMyContributions1"
														name="showAllMyContributions1"><span>Include Closed PI</span></input>



												</s:if> <s:else>
													<input type="checkbox" onclick="getClosedPI()"
														id="showAllMyContributions1"
														name="showAllMyContributions1">
													<span>Include Closed PI</span>
													</input>
												</s:else></th>
											<!-- <th colspan="3">
												<div>
													<div
														style="width: 19px; height: 19px; background: #d4edda; float: left; padding-right: 6px;"
														id="underburning"></div>
													<label for="underburning">Under Burning</label>
												</div> <br />
												<div>
													<div
														style="width: 19px; height: 19px; background: #dc3545; float: left; padding-right: 6px;"
														id="overburning"></div>
													<label for="overburning">Over Burning</label>
												</div> <br />
												<div>
													<div
														style="width: 19px; height: 19px; background: orange; float: left; padding-right: 6px;"
														id="streach"></div>
													<label for="stretch">Stretch</label>
												</div>
											</th> -->

										</tr>
										<tr>

											<th style="text-align: center;">PI</th>
											<th style="text-align: center; width: 73px !important;">Total
												Loe</th>
											<th colspan="2"
												style="text-align: center; width: 93px !important;">Charged
												hours</th>


										</tr>
									</thead>
									<tbody>
										<s:iterator value="dashboardUserNCLOEList">
										<%-- 	<tr>
												<td style="text-align: left;"><s:property
														value="NETWORKCODE" /></td>
												<td style="text-align: left;"><s:property
														value="TOTALLOE" /></td>
												<td colspan="2" style="text-align: left;"><s:property
														value="TOTALSUMMATION" /></td>
											</tr> --%>
											 <s:if
												test="TOTALLOE eq 0 || (TOTALSUMMATION/TOTALLOE * 100)>110">
												<tr style="background: #dc3545 !important;">
													<td style="text-align: left;"><s:property
															value="NETWORKCODE" /></td>
													<td style="text-align: left;"><s:property
															value="TOTALLOE" /></td>
													<td colspan="2" style="text-align: left;"><s:property
															value="TOTALSUMMATION" /></td>
												</tr>
											</s:if>
											<s:elseif
												test="((TOTALSUMMATION/TOTALLOE * 100)>=100 && (TOTALSUMMATION/TOTALLOE * 100) <=110) ">
												<tr style="background: orange;">
													<td style="text-align: left;"><s:property
															value="NETWORKCODE" /></td>
													<td style="text-align: left;"><s:property
															value="TOTALLOE" /></td>
													<td colspan="2" style="text-align: left;"><s:property
															value="TOTALSUMMATION" /></td>
												</tr>
											</s:elseif>
											<s:else>
												<tr style="background: #d4edda;">
													<td style="text-align: left;"><s:property
															value="NETWORKCODE" /></td>
													<td style="text-align: left;"><s:property
															value="TOTALLOE" /></td>
													<td colspan="2" style="text-align: left;"><s:property
															value="TOTALSUMMATION" /></td>
												</tr>
											</s:else>  


										</s:iterator>
									</tbody>
								</table>
							</div>
						</s:if></td>
				</tr>
			</table>
		</div>
		<s:if test="#session['role'] != null && #session['role'] neq 'ADMIN'">
			<div class="column">
				<table>
					<tr>
						<td valign="top">
							<table id="dashboardtable" style="width: 465px;">
								<thead>
									<tr>
										<th align="center" colspan="2">Utilization</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><s:if
												test="#session['role'] != null && #session['role'] eq 'LINE MANAGER'">
												<s:hidden name="detailSubRes" id="detailSubRes1"></s:hidden>
												<sj:autocompleter list="employeeList"
													cssStyle="width:150px;" name="employeeId" theme="simple"
													id="employeeId" onSelectTopics="/employeeSelect"
													selectBox="true" selectBoxIcon="true"></sj:autocompleter>
												<sj:a id="getDashboardUtilizationHrefId"
													href="../login/getDashboardUtilizationHrefId.action"
													targets="dashboardUtilizationDivId" formIds="homeFormId"
													indicator="dashboardUtilizationLoadingIndicator" />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<img id="dashboardUtilizationLoadingIndicator"
													src="../images/indicatorImg.gif" style="display: none" />
											</s:if> <%--
											<span> Show Pts % <input type="checkbox"
												id='ptsCheck' onclick="showHrs('pts')" />
										</span> <span>Show ESS % <input type="checkbox" id='essCheck'
												onclick="showHrs('ess')" /></span>
											--%></td>

									</tr>
									<tr>
										<td><sj:div id="dashboardUtilizationDivId"><jsp:include
													page="dashboardUtilizationDetails.jsp" /></sj:div></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</s:if>
	</div>
</div>