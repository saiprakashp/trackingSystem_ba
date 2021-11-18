<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<table>
	<thead  class="ui-state-default ui-jqgrid-hdiv">
		<tr>
			<th>Location</th>
			<th>Date</th>
			<th>Holiday</th>
			<th>Day</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="egiHolidayList" var="loc">
			<tr class="ui-widget-content jqgrow ui-row-ltr">
				<td><s:property value="#loc.locationName" /></td>
				<td><s:property value="#loc.date" /></td>
				<td><s:property value="#loc.holiday" /></td>
				<td><s:property value="#loc.day" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>