<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ page import="java.lang.Math"%>
<%
	request.setAttribute("decorator", "none");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<div>
	<s:div cssStyle="background: #FFF; white-space:nowrap;">
		<h3>
			<table>
				<tr>
					<td>User Contribution Summary Report <s:if
							test="advancedSearchApp">
							<a id="showfileraIdApp" href="#"
								style="display: none; text-decoration: none; color: #2779aa; font: 14px 'VerizonApexBook', arial; cursor: hand;"
								onclick="showAdvancedSearchOptionsApp()"><img
								src="../images/add_filter.png" height="28" width="28" />Advanced
								Search</a>
							<a id="removefileraIdApp" href="#"
								style="text-decoration: none; color: #2779aa; font: 14px 'VerizonApexBook', arial; cursor: hand;"
								onclick="removeAdvancedSearchOptionsApp()"><img
								src="../images/remove_filter.png" height="28" width="28" />Remove
								Filter</a>
							<input type="radio" value="All" id="allContribution"
								onclick="changeContribution('all')">By Project</input>
							<input type="radio" value="App" id="appContribution" checked
								onclick="changeContribution('app')">By Application</input>
						</s:if></td>
					<td><s:else>
							<a id="showfileraIdApp" href="#"
								style="text-decoration: none; color: #2779aa; font: 14px 'VerizonApexBook', arial; cursor: hand;"
								onclick="showAdvancedSearchOptionsApp()"><img
								src="../images/add_filter.png" height="28" width="28" />Advanced
								Search</a>
							<a id="removefileraIdApp" href="#"
								style="display: none; text-decoration: none; color: #2779aa; font: 14px 'VerizonApexBook', arial; cursor: hand;"
								onclick="removeAdvancedSearchOptions()"><img
								src="../images/remove_filter.png" height="28" width="28" />Remove
								Filter</a>
							<input type="radio" value="All" id="allContribution"
								onclick="changeContribution('all')">By Project</input>
							<input type="radio" value="App" id="appContribution" checked
								onclick="changeContribution('app')">By Application</input>
						</s:else></td>
					<td><a href="../reports/userContributionDetailsReport.action"
						onclick="" style="float: right;"><s:text
								name="pts.menu.project.contribution.details.report" /></a></td>
				</tr>
			</table>
		</h3>
	</s:div>
	<s:div cssStyle="background: #FFF; white-space:nowrap;">

	</s:div>
	<s:div cssStyle="background: #FFF; white-space:nowrap;">
		<s:if test="advancedSearchApp">
			<table id="advancedSearchTRIdApp">
				<tr>
					<td style="color: #2779aa;" align="left"><s:text
							name="pts.user.supervisor" />:</td>
					<td width="200"><sj:autocompleter
							list="supervisorMapForManage" name="searchSupervisor"
							listKey="key" listValue="value" theme="simple"
							id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>

					<td style="color: #2779aa;" align="left"><s:text
							name="pts.networkcode.pillar.name" />:</td>
					<td width="200" nowrap="nowrap"><sj:div id="pillarsDivId">
							<sj:autocompleter list="platformsMap" name="pillarId"
								listKey="key" listValue="value" theme="simple" id="pillarId"
								onSelectTopics="/pillarSelect" selectBox="true"
								selectBoxIcon="true"></sj:autocompleter>
						</sj:div></td>
				</tr>
				<tr>
					<td style="color: #2779aa;" align="left"><s:text
							name="pts.user.stream" />:</td>

					<td width="200"><sj:autocompleter list="streamsMap"
							name="searchStream" listKey="key" listValue="value"
							theme="simple" id="searchStream" selectBox="true"
							selectBoxIcon="true"></sj:autocompleter></td>

					<td style="color: #2779aa;" align="left"><s:text
							name="pts.user.location" />:</td>
					<td width="200" align="left"><sj:autocompleter
							list="locationsMap" name="searchLocation" listKey="key"
							listValue="value" theme="simple" id="searchLocation"
							selectBox="true" selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>

					<td width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
						onclick="submitContributionReportApp()" /><img
						src="../images/go.png" width="25" height="25" /></a></td>
				</tr>
			</table>
		</s:if>
		<s:else>
			<table id="advancedSearchTRIdApp" style="display: none">
				<tr>
					<td style="color: #2779aa;"><s:text name="pts.user.supervisor" />:</td>
					<td width="200"><sj:autocompleter
							list="supervisorMapForManage" name="searchSupervisor"
							listKey="key" listValue="value" theme="simple"
							id="searchSupervisor" selectBox="true" selectBoxIcon="true"></sj:autocompleter></td>

					<td style="color: #2779aa;" align="justify"><s:text
							name="pts.networkcode.pillar.name" />:</td>
					<td width="200" nowrap="nowrap"><sj:div id="pillarsDivId">
							<sj:autocompleter list="platformsMap" name="pillarId"
								listKey="key" listValue="value" theme="simple" id="pillarId"
								onSelectTopics="/pillarSelect" selectBox="true"
								selectBoxIcon="true"></sj:autocompleter>
						</sj:div></td>
				</tr>
				<tr>
					<td style="color: #2779aa;"><s:text name="pts.user.stream" />:
					</td>
					<td width="200"><sj:autocompleter list="streamsMap"
							name="searchStream" listKey="key" listValue="value"
							theme="simple" id="searchStream" selectBox="true"
							selectBoxIcon="true"></sj:autocompleter></td>

					<td style="color: #2779aa;"><s:text name="pts.user.location" />:
					</td>
					<td width="200" align="left"><sj:autocompleter
							list="locationsMap" name="searchLocation" listKey="key"
							listValue="value" theme="simple" id="searchLocation"
							selectBox="true" selectBoxIcon="true" cssStyle="width:200px;"></sj:autocompleter></td>

					<td width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
						onclick="submitContributionReportApp()" /><img
						src="../images/go.png" width="25" height="25" /></a></td>
				</tr>
			</table>
		</s:else>
	</s:div>
	<s:div cssStyle="background: #FFF; white-space:nowrap;">

	</s:div>
	<div style="overflow-y: auto;">
		<table>
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
														<th style="background-color: #cdcdcd; text-align: center;">Test</th>
														<th style="background-color: #cdcdcd; text-align: center;">Dev</th>
														<th style="background-color: #cdcdcd; text-align: center;">PM</th>
														<th style="background-color: #cdcdcd; text-align: center;">SE</th>
														<th style="background-color: #cdcdcd; text-align: center;">PTL</th>
														<th style="background-color: #cdcdcd; text-align: center;">#</th>
													</tr>
												</table>
											</s:if> <s:elseif test="value.platform eq 'Admin'">
												<table style="width: 60px; table-layout: fixed;">
													<col width=40>
													<col width=20>
													<tr>
														<th style="background-color: #cdcdcd; text-align: center;">Admin</th>
														<th style="background-color: #cdcdcd; text-align: center;">#</th>
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
</div>
