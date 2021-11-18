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
	<s:div cssStyle="background: #FFF;">
		<table id="activityTable0" class="activityTable">
			<tr style="background-color: #cdcdcd;">
				<th>Resource</th>
				<th>Week</th>

				<th>Project/ Assignment</th>
				<th>Activity</th>
				<th>Type</th>
				<th>Mon</th>
				<th>Tue</th>
				<th>Wed</th>
				<th>Thu</th>
				<th>Fri</th>
				<th>Sat</th>
				<th>Sun</th>
				<th></th>
				<th>Total</th>
				<th nowrap="nowrap" align="left"><s:checkbox name="approveAll"
						id="approveAll" theme="simple" /> Approve</th>
				<th nowrap="nowrap" align="left"><s:checkbox name="rejectAll"
						id="rejectAll" theme="simple" /> Reject</th>
			</tr>

			<s:if test="activityList.size() > 0 ">
				<s:set name="usernameshown" value="" />
				<s:iterator value="activityList" var="activity" status="actstat">
					<tr>
						<td align="left"><s:if
								test="%{#usernameshown eq '' || (#usernameshown != '' && (activityList[#actstat.index].userName != #usernameshown))}">
								<s:property value="%{activityList[#actstat.index].userName}" />
								<s:set name="usernameshown"
									value="%{activityList[#actstat.index].userName}" />
							</s:if> <s:hidden theme="simple"
								name="%{'activityListToSave['+#actstat.index+'].id'}"
								id="%{'activityList['+#actstat.index+'].id'}"
								value="%{activityList[#actstat.index].id}" /> <s:hidden
								theme="simple"
								name="%{'activityListToSave['+#actstat.index+'].weekendingDate'}"
								id="%{'activityList['+#actstat.index+'].weekendingDate'}"
								value="%{activityList[#actstat.index].weekendingDate}" /> <s:hidden
								theme="simple"
								name="%{'activityListToSave['+#actstat.index+'].createdBy'}"
								id="%{'activityList['+#actstat.index+'].createdBy'}"
								value="%{activityList[#actstat.index].createdBy}" /> <s:hidden
								theme="simple"
								name="%{'activityListToSave['+#actstat.index+'].createdDate'}"
								id="%{'activityList['+#actstat.index+'].createdDate'}"
								value="%{activityList[#actstat.index].createdDate}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].userId'}"
								value="%{activityList[#actstat.index].userId}"
								id="%{'activityList['+#actstat.index+'].userId'}" theme="simple" /></td>
						<td align="center" nowrap="nowrap"
							style="background: rgb(198, 198, 198);"><s:property
								value="%{activityList[#actstat.index].weekendingDate}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].weekendingDate'}"
								value="%{activityList[#actstat.index].weekendingDate}"
								id="%{'activityList['+#actstat.index+'].weekendingDate'}"
								theme="simple" /></td>
						<td align="left"><s:property
								value="%{activityList[#actstat.index].networkIdDesc}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].networkId'}"
								value="%{activityList[#actstat.index].networkId}"
								id="%{'activityList['+#actstat.index+'].networkId'}"
								theme="simple" /></td>
						<td align="left"><s:property
								value="%{activityList[#actstat.index].activityDesc}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].activityCode'}"
								value="%{activityList[#actstat.index].activityCode}"
								id="%{'activityList['+#actstat.index+'].activityCode'}"
								theme="simple" /></td>
						<td align="left"><s:if test=" !type.matches('[0-9]+') ">
								<s:property value="%{activityList[#actstat.index].type}" />
							</s:if> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].type'}"
								value="%{activityList[#actstat.index].type}"
								id="%{'activityList['+#actstat.index+'].type'}" theme="simple" /></td>

						<td align="center"><s:property
								value="%{activityList[#actstat.index].monHrs}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].monHrs'}"
								value="%{activityList[#actstat.index].monHrs}"
								id="%{'activityList['+#actstat.index+'].monHrs'}" theme="simple" /></td>
						<td align="center"><s:property
								value="%{activityList[#actstat.index].tueHrs}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].tueHrs'}"
								value="%{activityList[#actstat.index].tueHrs}"
								id="%{'activityList['+#actstat.index+'].tueHrs'}" theme="simple" /></td>
						<td align="center"><s:property
								value="%{activityList[#actstat.index].wedHrs}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].wedHrs'}"
								value="%{activityList[#actstat.index].wedHrs}"
								id="%{'activityList['+#actstat.index+'].wedHrs'}" theme="simple" /></td>
						<td align="center"><s:property
								value="%{activityList[#actstat.index].thuHrs}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].thuHrs'}"
								value="%{activityList[#actstat.index].thuHrs}"
								id="%{'activityList['+#actstat.index+'].thuHrs'}" theme="simple" /></td>
						<td align="center"><s:property
								value="%{activityList[#actstat.index].friHrs}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].friHrs'}"
								value="%{activityList[#actstat.index].friHrs}"
								id="%{'activityList['+#actstat.index+'].friHrs'}" theme="simple" /></td>
						<td align="center"><s:property
								value="%{activityList[#actstat.index].satHrs}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].satHrs'}"
								value="%{activityList[#actstat.index].satHrs}"
								id="%{'activityList['+#actstat.index+'].satHrs'}" theme="simple" /></td>
						<td align="center"><s:property
								value="%{activityList[#actstat.index].sunHrs}" /> <s:hidden
								name="%{'activityListToSave['+#actstat.index+'].sunHrs'}"
								value="%{activityList[#actstat.index].sunHrs}"
								id="%{'activityList['+#actstat.index+'].sunHrs'}" theme="simple" /></td>

						<td align="center" style="background: rgb(198, 198, 198);"><s:property
								value="%{activityList[#actstat.index].activitySummation}" /></td>
						<s:if
							test="%{#activity.totalHrs != null && #activity.totalHrs != ''}">
							<td align="center" id="total"
								rowspan="<s:property
									value='#activity.totalHrs.split("\\\|")[0]' />"
								style="background: rgb(198, 198, 198);"><s:property
									value='#activity.totalHrs.split("\\\|")[2]' /></td>
						</s:if>
						<s:else>

						</s:else>

						<td align="justify">&nbsp;&nbsp;<s:checkbox
								cssClass="approveAllClass"
								name="%{'activityListToSave['+#actstat.index+'].approvedStatusFlag'}"
								id="%{'activityList['+#actstat.index+'].approvedStatusFlag'}"
								value="%{activityList[#actstat.index].approvedStatusFlag}"
								theme="simple" /></td>
						<td align="justify">&nbsp;&nbsp;<s:checkbox
								cssClass="rejectAllClass"
								name="%{'activityListToSave['+#actstat.index+'].rejectedStatusFlag'}"
								id="%{'activityList['+#actstat.index+'].rejectedStatusFlag'}"
								value="%{activityList[#actstat.index].rejectedStatusFlag}"
								theme="simple" /></td>
					</tr>
				</s:iterator>
			</s:if>
		</table>
	</s:div>
</div>