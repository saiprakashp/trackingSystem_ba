<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<div>
	<table style="width: 100%; border: 1px solid;">
		<thead>
			<tr>
				<th style="color: #2779aa;" align="center">Location Name:</th>
				<th style="color: #2779aa;" align="center">Head Count:</th>
				<th style="color: #2779aa;" align="center">Working Days:</th>

			</tr>
		</thead>
		<tbody align="center">
			<s:iterator value="dashbordDetails" var="utilization">
				<tr>
					<td><s:textfield name="locationName" disabled="true"
							id="locationName" /></td>
					<td><s:textfield name="resourceCount" id="resourceCount" /></td>
					<td><s:textfield name="workingdays" id="workingdays" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<table style="width: 100%; border: 1px solid;">
		<thead>
			<tr>
				<th style="color: #2779aa;" align="center">Location:</th>
				<th style="color: #2779aa;" align="center">No Of Hours Per Day:</th>
			</tr>
		</thead>
		<tbody>
			<s:if test="egiWorkingHours != null && egiWorkingHours!=-1">
				<tr>
					<td style="color: #2779aa;" align="center">EGI</td>
					<td align="center"><s:textfield name="egiWorkingHours"
							id="egiWorkingHours" /></td>
				</tr>
			</s:if>
			<s:if test="manaWorkingHours neq null && manaWorkingHours!=-1">
				<tr>
					<td align="center">MANA:</td>
					<td align="center"><s:textfield name="manaWorkingHours"
							value="0" id="manaWorkingHours" /></td>
				</tr>
			</s:if>
		</tbody>
	</table>
	<input type="image" src="../images/save.png" id="saveupdateUtil"
		style="float: right;"
		onclick="submitthisform('../projects/updatePlannedUtilization')">
</div>