<%@ taglib prefix="s" uri="/struts-tags"%>
<table id="dashboardtable" style="width: 578px !important;  height: 420px !important; min-height: 50px !important;">
	<thead>
		<tr>
			<th align="center">Month</th>
			<th align="center" nowrap="nowrap">Head Count</th>
			<th align="center">Utilization</th>
			<th align="center" nowrap="nowrap">Target Hrs</th>
			<th align="center" nowrap="nowrap">ESS Hrs</th>
			<th align="center" nowrap="nowrap">PTS Hrs</th>
			<th align="center">Diff</th>
			<%-- <th align="center" class='ptsPercent'style="display: none;">Pts %</th>
			<th align="center" class='essPercent'style="display: none;">Ess %</th>
			<th align="center">%</th>
			--%>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="dashboardUtilizationReportList"
			var="dashboardUtilizationReport">
			<tr>
				<td><s:property value="month" /></td>
				<td><s:property value="headCount" /></td>
				<td><s:property value="utilization" /></td>
				<td><s:property value="targetHrs" /></td>
				<td><s:property value="essActualHrs" /></td>
				<td><s:property value="actualHrs" /></td>
				<td class='ptsPercent' style="display: none;"><s:property
						value="ptspercentage" /></td>
				<td class='essPercent' style="display: none;"><s:property
						value="esspercentage" /></td>

				<td><s:property value="essPtsDiff" /></td>
				<%--
				<td class='ptsPercent'style="display: none;"><s:property value="ptspercentage" /></td>
				<td class='essPercent' style="display: none;"><s:property value="esspercentage" /></td>
				<td><s:property value="percentage" /></td>
		--%>
			</tr>
		</s:iterator>
	</tbody>
</table>