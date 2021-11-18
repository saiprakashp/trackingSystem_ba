<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../utilization/manageProjectNC.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="ncGridModel" navigator="true" navigatorDelete="false"
	navigatorAdd="false" navigatorEdit="false" navigatorSearch="false"
	navigatorRefresh="false"
	navigatorExtraButtons="{
			edit : {
				title : 'Download This Release Data',
				icon: 'ui-icon-document',
				position:'first',
				onclick: function(){document.getElementById('download').value='true';  macdUser('downloadProjectWiseResourceUtilization'); }
			},
			
		}"
	navigatorCloneToTop="true" rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	formIds="manageUtilizationFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">



	<sjg:grid dataType="json" id="subgridtable" indicator="false"
		subGridUrl="../utilization/manageUserProjectNC.action"
		gridModel="userNCGridModel" pager="false" rownumbers="false"
		navigator="false" width="945" shrinkToFit="true"
		formIds="manageUtilizationFormId">

		<sjg:grid dataType="json" id="subsubgridtable" indicator="false"
			subGridUrl="../utilization/manageUserNCUtilizationDetails.action"
			gridModel="userEffortGridModel" pager="false" rownumbers="false"
			navigator="false" width="915" shrinkToFit="true"
			formIds="manageUtilizationFormId">
			<sjg:gridColumn name="YEAR" index="YEAR" title="Year" align="right" />
			<sjg:gridColumn name="MONTH" index="MONTH" title="Month" align="left" />
			<sjg:gridColumn name="ACTIVITYCODE" index="ACTIVITYCODE"
				title="Activity Code" align="left" />
			<sjg:gridColumn name="CHARGEDHRS" index="CHARGEDHRS" title="Effort"
				align="right" />

		</sjg:grid>

		<sjg:gridColumn name="id" index="id" title="UserId" hidden="true"
			key="true" />
		<sjg:gridColumn name="USERNAME" index="USERNAME" title="Resource Name"
			align="left" />
		<sjg:gridColumn name="SUPERVISOR" index="SUPERVISOR"
			title="Supervisor" align="left" />
		<sjg:gridColumn name="SUMMATION" index="SUMMATION"
			title="Hours Charged" align="right" />

	</sjg:grid>

	<sjg:gridColumn name="id" index="id" title="Project ID" hidden="true"
		key="true" />
	<sjg:gridColumn name="NETWORKCODE" index="NETWORKCODE" title="Project"
		align="left" />

	<sjg:gridColumn name="SUMMATION" index="SUMMATION"
		title="Total Hours Charged" align="right" />
	<s:if test="TEAMEFFORT neq null">
		<sjg:gridColumn name="TEAMEFFORT" index="TEAMEFFORT"
			title="Hours Charged By Team" align="right" />
	</s:if>
	<s:if test="type=='MONTH'">
		<sjg:gridColumn name="MONTH" index="MONTH" title="MONTH" align="right" />
	</s:if>
	<s:if test="type=='WEEKEND_DATE'">
		<sjg:gridColumn name="WEEKENDING_DATE" index="WEEKENDING_DATE"
			title="DATE" align="right" />
	</s:if>
	<s:if test="reportType=='USER' && USER neq'USER' ">
		<sjg:gridColumn name="USER" index="USER" title="USER" align="right" />
	</s:if>
</sjg:grid>

