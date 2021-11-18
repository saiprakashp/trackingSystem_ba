<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%
	request.setAttribute("decorator", "none");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<div>
	<div class="hideProjBasedContrib">
		<s:div cssStyle="background: #FFF;">
			<h3>User Summary Report</h3>
		</s:div>
		<s:div cssStyle="background: #FFF;">
			<table>
				<tr>
					<td>
						<table class="activityTable">
							<tr style="background-color: #cdcdcd;">

								<td>&nbsp;</td>
								<th>Test&nbsp;</th>
								<th>Dev&nbsp;</th>
								<th>PM&nbsp;</th>
								<th>SE&nbsp;</th>
								<th>PTL&nbsp;</th>
								<th>Admin&nbsp;</th>
								<th>Total&nbsp;</th>
							</tr>
							<s:iterator value="userReportList" var="userReport">
								<tr>
									<th style="background-color: #cdcdcd;"><s:property
											value="location" />&nbsp;</th>
									<td style="text-align: center;"><s:if
											test="testUserCnt != 0">
											<s:property value="%{testUserCnt.intValue()}" />
										</s:if>&nbsp;</td>
									<td style="text-align: center;"><s:if
											test="devUserCnt != 0">
											<s:property value="%{devUserCnt.intValue()}" />
										</s:if>&nbsp;</td>
									<td style="text-align: center;"><s:if
											test="pmUserCnt != 0">
											<s:property value="%{pmUserCnt.intValue()}" />
										</s:if>&nbsp;</td>
									<td style="text-align: center;"><s:if
											test="seUserCnt != 0">
											<s:property value="%{seUserCnt.intValue()}" />
										</s:if>&nbsp;</td>
									<td style="text-align: center;"><s:if
											test="ptlUserCnt != 0">
											<s:property value="%{ptlUserCnt.intValue()}" />
										</s:if>&nbsp;</td>
									<td style="text-align: center;"><s:if
											test="adminUserCnt != 0">
											<s:property value="%{adminUserCnt.intValue()}" />
										</s:if>&nbsp;</td>
									<td style="text-align: center;"><s:if
											test="totalLocUserCnt != 0">
											<s:property value="%{totalLocUserCnt.intValue()}" />
										</s:if>&nbsp;</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
			</table>
		</s:div>
		<s:div cssStyle="background: #FFF; white-space:nowrap;">
			<h3>
				User Contribution Summary Report&nbsp;&nbsp;
				<s:if test="advancedSearch">
					<a id="showfileraId" href="#"
						style="display: none; text-decoration: none; color: #2779aa; font: 14px 'VerizonApexBook', arial; cursor: hand;"
						onclick="showAdvancedSearchOptions()"><img
						src="../images/add_filter.png" height="28" width="28" />&nbsp;Advanced
						Search</a>
					<a id="removefileraId" href="#"
						style="text-decoration: none; color: #2779aa; font: 14px 'VerizonApexBook', arial; cursor: hand;"
						onclick="removeAdvancedSearchOptions()"><img
						src="../images/remove_filter.png" height="28" width="28" />&nbsp;Remove
						Filter</a>
				</s:if>
				<s:else>
					<a id="showfileraId" href="#"
						style="text-decoration: none; color: #2779aa; font: 14px 'VerizonApexBook', arial; cursor: hand;"
						onclick="showAdvancedSearchOptions()"><img
						src="../images/add_filter.png" height="28" width="28" />&nbsp;Advanced
						Search</a>
					<a id="removefileraId" href="#"
						style="display: none; text-decoration: none; color: #2779aa; font: 14px 'VerizonApexBook', arial; cursor: hand;"
						onclick="removeAdvancedSearchOptions()"><img
						src="../images/remove_filter.png" height="28" width="28" />&nbsp;Remove
						Filter</a>
				</s:else>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
					href="#" onclick="goContributionDetailReport()"><s:text
						name="pts.menu.project.contribution.details.report" /></a>
			</h3>
		</s:div>
		<s:div cssStyle="background: #FFF; white-space:nowrap;">

		</s:div>
		<s:div cssStyle="background: #FFF; white-space:nowrap;">
			<s:if test="advancedSearch">
				<table id="advancedSearchTRId">
					<tr>
						<td style="color: #2779aa;" align="right"><s:text
								name="pts.user.supervisor" />:</td>
						<td width="200"><sj:autocompleter
								list="supervisorMapForManage" name="searchSupervisor"
								listKey="key" listValue="value" theme="simple"
								id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>

						<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
								name="pts.networkcode.pillar.name" />:
						</td>
						<td width="200" nowrap="nowrap"><sj:div id="pillarsDivId">
								<sj:autocompleter list="platformsMap" name="pillarId"
									listKey="key" listValue="value" theme="simple" id="pillarId"
									onSelectTopics="/pillarSelect" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter>
							</sj:div></td>

						<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
								name="pts.user.stream" />:
						</td>
						<td width="200"><sj:autocompleter list="streamsMap"
								name="searchStream" listKey="key" listValue="value"
								theme="simple" id="searchStream" selectBox="true"
								selectBoxIcon="true"></sj:autocompleter></td>

						<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
								name="pts.user.location" />:
						</td>
						<td width="200" align="left"><sj:autocompleter
								list="locationsMap" name="searchLocation" listKey="key"
								listValue="value" theme="simple" id="searchLocation"
								selectBox="true" selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>

						<td width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:submit
								theme="simple" src="../images/go.png" type="image"
								onclick="submitContributionReport()" /></td>
					</tr>
				</table>
			</s:if>
			<s:else>
				<table id="advancedSearchTRId" style="display: none">
					<tr>
						<td style="color: #2779aa;"><s:text
								name="pts.user.supervisor" />:</td>
						<td width="200"><sj:autocompleter
								list="supervisorMapForManage" name="searchSupervisor"
								listKey="key" listValue="value" theme="simple"
								id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>

						<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
								name="pts.networkcode.pillar.name" />:
						</td>
						<td width="200" nowrap="nowrap"><sj:div id="pillarsDivId">
								<sj:autocompleter list="platformsMap" name="pillarId"
									listKey="key" listValue="value" theme="simple" id="pillarId"
									onSelectTopics="/pillarSelect" selectBox="true"
									selectBoxIcon="true"></sj:autocompleter>
							</sj:div></td>

						<td style="color: #2779aa;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
								name="pts.user.stream" />:
						</td>
						<td width="200"><sj:autocompleter list="streamsMap"
								name="searchStream" listKey="key" listValue="value"
								theme="simple" id="searchStream" selectBox="true"
								selectBoxIcon="true"></sj:autocompleter></td>

						<td style="color: #2779aa;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
								name="pts.user.location" />:
						</td>
						<td width="200" align="left"><sj:autocompleter
								list="locationsMap" name="searchLocation" listKey="key"
								listValue="value" theme="simple" id="searchLocation"
								selectBox="true" selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>

						<td width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:submit
								theme="simple" src="../images/go.png" type="image"
								onclick="submitContributionReport()" /></td>
					</tr>
				</table>
			</s:else>
		</s:div>
		<s:div cssStyle="background: #FFF; white-space:nowrap;">

		</s:div>
	</div>
	<table>
		<tr>
			<td colspan="2" align="right">
				<h3 style="background: #FFF; color: black;">
					Project Wise <input checked id="projectBasedCheck" type="radio"
						onclick="swapFlagData('proj')" />
				</h3>
			</td>
			<td></td>
			<td></td>
			<td></td>
			<td colspan="2" align="right">
				<h3 style="background: #FFF; color: black;">
					Application Wise <input id="appBasedCheck" type="radio"
						onclick="swapFlagData('app')" />
				</h3>
			</td>
		</tr>
	</table>

	<sj:div id="compScoreMessageDivId" cssClass="hideProjBasedContrib">
		<div style="overflow-y: auto;">
			<table style="display: block;" class="projectTable">
				<tr>
					<td>
						<table class="activityTable">
							<s:iterator value="contributionReportMap"
								status="userReportindex">
								<s:if test="%{#userReportindex.index == 0}">
									<tr style="background-color: #cdcdcd;">
										<th style="background-color: #cdcdcd;">&nbsp;</th>

										<s:iterator value="value" var="streammapstat">
											<th style="background-color: #cdcdcd; text-align: center"><s:property
													value="key" /></th>
										</s:iterator>
										<th style="background-color: #cdcdcd; text-align: center">Total</th>
									</tr>
									<tr style="background-color: #cdcdcd;">
										<th style="background-color: #cdcdcd;">&nbsp;</th>

										<s:iterator value="value" var="streammapstat">
											<td style="text-align: center;"><s:if
													test="value.platform neq 'Admin'">
													<table style="width: 170px; table-layout: fixed;">
														<col width=40>
														<col width=30>
														<col width=25>
														<col width=25>
														<col width=25>
														<col width=25>
														<tr>
															<th
																style="background-color: #cdcdcd; text-align: center;">Test</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">Dev</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">PM</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">SE</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">PTL</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">#</th>
														</tr>
													</table>
												</s:if> <s:elseif test="value.platform eq 'Admin'">
													<table style="width: 60px; table-layout: fixed;">
														<col width=40>
														<col width=20>
														<tr>
															<th
																style="background-color: #cdcdcd; text-align: center;">Admin</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">#</th>
														</tr>
													</table>
												</s:elseif></td>
										</s:iterator>
										<th style="background-color: #cdcdcd; text-align: center">&nbsp;</th>
									</tr>
								</s:if>
							</s:iterator>
							<s:iterator value="contributionReportMap" var="userReportidx">
								<tr>
									<th style="background-color: #cdcdcd;"><s:property
											value="key" />&nbsp;</th>
									<s:set name="totLocUsers" value="0" />
									<s:iterator value="value" var="streammapstat">
										<td style="text-align: center;"><s:if
												test="value.platform neq 'Admin'">
												<table style="width: 170px; table-layout: fixed;">
													<col width=40>
													<col width=30>
													<col width=25>
													<col width=25>
													<col width=25>
													<col width=25>
													<tr>
														<td style="text-align: center;"><s:property
																value="%{value.testUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.devUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.pmUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.seUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.ptlUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.totalLocUserCnt}" /></td>
														<s:set name="totLocUsers"
															value="%{#totLocUsers + value.totalLocUserCnt}" />
													</tr>
												</table>
											</s:if> <s:elseif test="value.platform eq 'Admin'">
												<table style="width: 60px; table-layout: fixed;">
													<col width=40>
													<col width=20>
													<tr>
														<td style="text-align: center;"><s:property
																value="%{value.adminUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.adminUserCnt}" /></td>
														<s:set name="totLocUsers"
															value="%{#totLocUsers + value.adminUserCnt}" />
													</tr>
												</table>
											</s:elseif></td>
									</s:iterator>
									<td style="text-align: center;"><s:property
											value="%{(#totLocUsers).floatValue()}" /></td>
								</tr>
							</s:iterator>
							<tr>
								<th style="background-color: #cdcdcd;" nowrap="nowrap">Stream-HC</th>
								<s:set name="platformHCUsers" value="0" />
								<s:iterator value="platformUserContributionCnt"
									var="platformUserContributionCntstat">
									<s:set name="platformHCUsers"
										value="%{#platformHCUsers + value}" />
								</s:iterator>

								<s:iterator value="streamHCCnt" var="streamHCCntstat">

									<td style="text-align: center;"><s:if
											test="key neq 'Admin'">
											<table style="width: 170px; table-layout: fixed;">
												<col width=40>
												<col width=30>
												<col width=25>
												<col width=25>
												<col width=25>
												<col width=25>
												<tr>
													<s:iterator value="value" var="streamHCistat">
														<td style="text-align: center;"><s:property
																value="%{value}" /></td>
													</s:iterator>
												</tr>
											</table>
										</s:if> <s:elseif test="key eq 'Admin'">
											<table style="width: 60px; table-layout: fixed;">
												<col width=40>
												<col width=20>
												<tr>
													<s:iterator value="value" var="streamHCistat">
														<td style="text-align: center;"><s:property
																value="%{value}" /></td>
													</s:iterator>
												</tr>
											</table>
										</s:elseif></td>
								</s:iterator>
								<td style="text-align: center;"><s:property
										value="%{(#platformHCUsers).floatValue()}" /></td>
							</tr>
							<tr>
								<th style="background-color: #cdcdcd;" nowrap="nowrap">Platform-HC</th>

								<s:iterator value="platformUserContributionCnt"
									var="platformUserContributionCntstat">
									<td style="text-align: center;"><s:property value="value" /></td>

								</s:iterator>
								<td style="text-align: center;"><s:property
										value="%{(#platformHCUsers).floatValue()}" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<!-- Application Based -->
			<table class="applicationTable" style="display: none;">
				<tr>
					<td>
						<table class="activityTable">
							<tr style="background-color: #cdcdcd;">
								<th style="background-color: #cdcdcd;"></th>
								<s:iterator value="streamsAppLinkMapApp">
									<th style="background-color: #cdcdcd; text-align: center"
										colspan='<s:property value="value" />'><s:property
											value="key" /></th>
								</s:iterator>
							</tr>
							<s:iterator value="contributionReportMapApp"
								status="userReportindex">
								<s:if test="%{#userReportindex.index == 0}">
									<tr style="background-color: #cdcdcd;">
										<th style="background-color: #cdcdcd;"></th>

										<s:iterator value="value" var="streammapstat">
											<th style="background-color: #cdcdcd; text-align: center"><s:property
													value="key" /></th>
										</s:iterator>
										<th style="background-color: #cdcdcd; text-align: center">Total</th>
									</tr>
									<tr style="background-color: #cdcdcd;">
										<th style="background-color: #cdcdcd;"></th>

										<s:iterator value="value" var="streammapstat">
											<td style="text-align: center;"><s:if
													test="value.platform neq 'Admin'">
													<table style="width: 170px; table-layout: fixed;">
														<col width=40>
														<col width=30>
														<col width=25>
														<col width=25>
														<col width=25>
														<col width=25>
														<tr>
															<th
																style="background-color: #cdcdcd; text-align: center;">Test</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">Dev</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">PM</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">SE</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">PTL</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">#</th>
														</tr>
													</table>
												</s:if> <s:elseif test="value.platform eq 'Admin'">
													<table style="width: 60px; table-layout: fixed;">
														<col width=40>
														<col width=20>
														<tr>
															<th
																style="background-color: #cdcdcd; text-align: center;">Admin</th>
															<th
																style="background-color: #cdcdcd; text-align: center;">#</th>
														</tr>
													</table>
												</s:elseif></td>
										</s:iterator>
										<th style="background-color: #cdcdcd; text-align: center"></th>
									</tr>
								</s:if>
							</s:iterator>
							<s:iterator value="contributionReportMapApp" var="userReportidx">
								<tr>
									<th style="background-color: #cdcdcd;"><s:property
											value="key" /></th>
									<s:set name="totLocUsers" value="0" />
									<s:iterator value="value" var="streammapstat">
										<td style="text-align: center;"><s:if
												test="value.platform neq 'Admin'">
												<table style="width: 170px; table-layout: fixed;">
													<col width=40>
													<col width=30>
													<col width=25>
													<col width=25>
													<col width=25>
													<col width=25>
													<tr>
														<td style="text-align: center;"><s:property
																value="%{value.testUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.devUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.pmUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.seUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.ptlUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.totalLocUserCnt}" /></td>
														<s:set name="totLocUsers"
															value="%{#totLocUsers + value.totalLocUserCnt}" />
													</tr>
												</table>
											</s:if> <s:elseif test="value.platform eq 'Admin'">
												<table style="width: 60px; table-layout: fixed;">
													<col width=40>
													<col width=20>
													<tr>
														<td style="text-align: center;"><s:property
																value="%{value.adminUserCnt}" /></td>
														<td style="text-align: center;"><s:property
																value="%{value.adminUserCnt}" /></td>
														<s:set name="totLocUsers"
															value="%{#totLocUsers + value.adminUserCnt}" />
													</tr>
												</table>
											</s:elseif></td>
									</s:iterator>
									<td style="text-align: center;"><s:property
											value="%{(#totLocUsers).floatValue()}" /></td>
								</tr>
							</s:iterator>
							<tr>
								<th style="background-color: #cdcdcd;" nowrap="nowrap">Stream-HC</th>

								<s:iterator value="streamCountMapApp">

									<td style="text-align: center;"><s:if
											test="key neq 'Admin'">
											<table style="width: 170px; table-layout: fixed;">
												<col width=40>
												<col width=30>
												<col width=25>
												<col width=25>
												<col width=25>
												<col width=25>
												<tr>
													<s:iterator value="value" var="id1">
														<td style="text-align: center;"><s:property
																value="#id1" /></td>
													</s:iterator>
												</tr>
											</table>
										</s:if> <s:elseif test="key eq 'Admin'">
											<table style="width: 60px; table-layout: fixed;">
												<col width=40>
												<col width=20>
												<tr>
													<td style="text-align: center;"><s:property
															value="value[0]" /></td>
													<td style="text-align: center;"><s:property
															value="value[1]" /></td>
												</tr>
											</table>
										</s:elseif></td>
								</s:iterator>
								<td style="text-align: center;"><s:property
										value="Math.round(%{(#platformHCUsers).floatValue()} * 100.0) / 100.0)" /></td>
							</tr>
							<tr>
								<th style="background-color: #cdcdcd;" nowrap="nowrap">Platform-HC</th>

								<s:iterator value="platformCountMapApp">
									<td style="text-align: center;"><s:property value="value" /></td>

								</s:iterator>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</sj:div>
</div>
</div>