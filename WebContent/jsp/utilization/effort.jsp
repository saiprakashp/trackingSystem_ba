<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
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
	<sj:accordion id="accordion" heightStyle="content" animate="true"
		collapsible="true">
		<s:iterator value="activityList" status="actstat">
			<sj:accordionItem title="%{name}">
				<s:div cssStyle="background: #FFF;">
					<s:hidden name="%{'activityList['+#actstat.index+'].id'}"
						id="%{'activityList['+#actstat.index+'].id'}" />
					<s:hidden name="%{'activityList['+#actstat.index+'].name'}"
						id="%{'activityList['+#actstat.index+'].name'}" />
					<s:iterator value="%{selectedNetworkCodesMap}">
						<s:hidden
							name="activityList[%{#actstat.index}].selectedNetworkCodesMap[%{key}]"
							value="%{value}" />
					</s:iterator>
					<table>
						<tr>
							<td>
								<table id="activityTable<s:property value='%{#actstat.index}'/>"
									class="activityTable">
									<tr style="background-color: #cdcdcd;">
										<td style="background: #FFF;">&nbsp;</td>
										<th><s:text name="pts.netwok.code" /></th>
										<th><s:text name="pts.activity.code" /></th>
										<th><s:text name="pts.category" /></th>
										<th><s:text name="pts.wbs" /></th>
										<s:iterator value="weekList">
											<th><s:property /></th>
										</s:iterator>
									</tr>
									<s:if test="weekEffortList.size() > 0 ">
										<s:iterator value="weekEffortList" status="stat">
											<tr>
												<td><img src="../images/delete_red.png" style="height: 16px;"
													title="Delete Row"
													onclick="deleteRow(this,'effort', <s:property value='%{#actstat.index}'/>,'','')" /></td>
												<td><s:select list="%{selectedNetworkCodesMap}"
														name="%{'activityList['+#actstat.index+'].weekEffortList['+#stat.index+'].networkId'}"
														id="%{'activityList['+#actstat.index+'].weekEffortList['+#stat.index+'].networkId'}"
														theme="simple" headerKey="-1" headerValue="Please Select"
														cssStyle="width:112px;"
														onchange="validateActivityGrid(this, '%{#actstat.index}', 'networkId', 'activityId')"></s:select></td>
												<td><s:select list="activityCodesMap"
														name="%{'activityList['+#actstat.index+'].weekEffortList['+#stat.index+'].activityId'}"
														id="%{'activityList['+#actstat.index+'].weekEffortList['+#stat.index+'].activityId'}"
														theme="simple" headerKey="-1" headerValue="Please Select"
														cssStyle="width:112px;"
														onchange="validateActivityGrid(this, '%{#actstat.index}', 'activityId', 'networkId')"></s:select></td>
												<td><s:select list="categoryList" listKey="id"
														listValue="category" headerKey="-1"
														headerValue="Please Select"
														name="%{'activityList['+#actstat.index+'].weekEffortList['+#stat.index+'].categoryId'}"
														id="%{'activityList['+#actstat.index+'].weekEffortList['+#stat.index+'].categoryId'}"
														theme="simple" cssStyle="width:112px;"></s:select></td>
												<td><s:select list="wbsList" listKey="id"
														listValue="wbs" headerKey="-1" headerValue="Please Select"
														name="%{'activityList['+#actstat.index+'].weekEffortList['+#stat.index+'].wbsId'}"
														id="%{'activityList['+#actstat.index+'].weekEffortList['+#stat.index+'].wbsId'}"
														theme="simple" cssStyle="width:112px;"></s:select></td>
												<s:iterator value="weekList" status="weekstat">
													<td align="center"><s:textfield
															name="%{'activityList['+#actstat.index+'].weekEffortList['+#stat.index+'].weekEndingEffort.weekEffort'+#weekstat.index}"
															id="%{'activityList['+#actstat.index+'].weekEffortList['+#stat.index+'].weekEndingEffort.weekEffort'+#weekstat.index}"
															theme="simple" cssStyle="width:65px;"
															onkeypress="return validateFloatKeyPress(this,event);" /></td>
												</s:iterator>
											</tr>
										</s:iterator>
									</s:if>
									<s:else>
										<s:if test="selectedNetworkCodesMap.size() > 0 ">
											<s:iterator value="selectedNetworkCodesMap" status="ncstat">
												<tr>
													<td><img src="../images/delete_red.png"  style="height: 16px;"
														title="Delete Row"
														onclick="deleteRow(this,'effort', <s:property value='%{#actstat.index}'/>,'','')" /></td>
													<td><s:select list="%{selectedNetworkCodesMap}"
															name="%{'activityList['+#actstat.index+'].weekEffortList['+#ncstat.index+'].networkId'}"
															id="%{'activityList['+#actstat.index+'].weekEffortList['+#ncstat.index+'].networkId'}"
															theme="simple" headerKey="-1" headerValue="Please Select"
															cssStyle="width:112px;"
															onchange="validateActivityGrid(this, '%{#actstat.index}', 'networkId', 'activityId')"></s:select></td>
													<td><s:select list="activityCodesMap"
															name="%{'activityList['+#actstat.index+'].weekEffortList['+#ncstat.index+'].activityId'}"
															id="%{'activityList['+#actstat.index+'].weekEffortList['+#ncstat.index+'].activityId'}"
															theme="simple" headerKey="-1" headerValue="Please Select"
															cssStyle="width:112px;"
															onchange="validateActivityGrid(this, '%{#actstat.index}', 'activityId', 'networkId')"></s:select></td>
													<td><s:select list="categoryList" listKey="id"
															listValue="category" headerKey="-1"
															headerValue="Please Select"
															name="%{'activityList['+#actstat.index+'].weekEffortList['+#ncstat.index+'].categoryId'}"
															id="%{'activityList['+#actstat.index+'].weekEffortList['+#ncstat.index+'].categoryId'}"
															theme="simple" cssStyle="width:112px;"></s:select></td>
													<td><s:select list="wbsList" listKey="id"
															listValue="wbs" headerKey="-1"
															headerValue="Please Select"
															name="%{'activityList['+#actstat.index+'].weekEffortList['+#ncstat.index+'].wbsId'}"
															id="%{'activityList['+#actstat.index+'].weekEffortList['+#ncstat.index+'].wbsId'}"
															theme="simple" cssStyle="width:112px;"></s:select></td>
													<s:iterator value="weekList" status="weekstat">
														<td align="center"><s:textfield
																name="%{'activityList['+#actstat.index+'].weekEffortList['+#ncstat.index+'].weekEndingEffort.weekEffort'+#weekstat.index}"
																id="%{'activityList['+#actstat.index+'].weekEffortList['+#ncstat.index+'].weekEndingEffort.weekEffort'+#weekstat.index}"
																theme="simple" cssStyle="width:65px;"
																onkeypress="return validateFloatKeyPress(this,event);" /></td>
													</s:iterator>
												</tr>
											</s:iterator>
										</s:if>
										<s:else>
											<tr>
												<td><img src="../images/delete_red.png" style="height: 16px;"
													title="Delete Row"
													onclick="deleteRow(this,'effort', <s:property value='%{#actstat.index}'/>,'','')" /></td>
												<td><s:select list="%{selectedNetworkCodesMap}"
														name="%{'activityList['+#actstat.index+'].weekEffortList[0].networkId'}"
														id="%{'activityList['+#actstat.index+'].weekEffortList[0].networkId'}"
														theme="simple" headerKey="-1" headerValue="Please Select"
														cssStyle="width:112px;"
														onchange="validateActivityGrid(this, '%{#actstat.index}', 'networkId', 'activityId')"></s:select></td>
												<td><s:select list="activityCodesMap"
														name="%{'activityList['+#actstat.index+'].weekEffortList[0].activityId'}"
														id="%{'activityList['+#actstat.index+'].weekEffortList[0].activityId'}"
														theme="simple" headerKey="-1" headerValue="Please Select"
														cssStyle="width:112px;"
														onchange="validateActivityGrid(this, '%{#actstat.index}', 'activityId', 'networkId')"></s:select></td>
												<td><s:select list="categoryList" listKey="id"
														listValue="category" headerKey="-1"
														headerValue="Please Select"
														name="%{'activityList['+#actstat.index+'].weekEffortList[0].categoryId'}"
														id="%{'activityList['+#actstat.index+'].weekEffortList[0].categoryId'}"
														theme="simple" cssStyle="width:112px;"></s:select></td>
												<td><s:select list="wbsList" listKey="id"
														listValue="wbs" headerKey="-1" headerValue="Please Select"
														name="%{'activityList['+#actstat.index+'].weekEffortList[0].wbsId'}"
														id="%{'activityList['+#actstat.index+'].weekEffortList[0].wbsId'}"
														theme="simple" cssStyle="width:112px;"></s:select></td>
												<s:iterator value="weekList" status="weekstat">
													<td align="center"><s:textfield
															name="%{'activityList['+#actstat.index+'].weekEffortList[0].weekEndingEffort.weekEffort'+#weekstat.index}"
															id="%{'activityList['+#actstat.index+'].weekEffortList[0].weekEndingEffort.weekEffort'+#weekstat.index}"
															theme="simple" cssStyle="width:65px;"
															onkeypress="return validateFloatKeyPress(this,event);" /></td>
												</s:iterator>
											</tr>
										</s:else>
									</s:else>
								</table>
							</td>
							<td valign="bottom"><img src="../images/add_row.png" style="height: 18px;"
								title="Add Row"
								onclick="addRow(<s:property value='%{#actstat.index}'/>)" /></td>
						</tr>
					</table>
				</s:div>
			</sj:accordionItem>
		</s:iterator>
	</sj:accordion>
</div>