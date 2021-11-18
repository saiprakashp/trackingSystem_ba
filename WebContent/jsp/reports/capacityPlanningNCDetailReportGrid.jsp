<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
 
<sjg:grid id="gridmultitable"
	href="../reports/ajaxCapacityPlanningUserDetailsReport.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="ncGridModel" navigator="false" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="false" navigatorSearch="false"
	navigatorRefresh="false" navigatorCloneToTop="true"
	rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	formIds="manageCapacityDetailReportFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">
	<%--onSubGridRowExpanded="subgridExpanded" --%>

	<sjg:grid dataType="json" id="subgridtable" indicator="false"
		subGridUrl="../reports/ajaxCapacityPlanningNCUserDetailsReport.action"
		gridModel="subGridModel" pager="false" toppager="false"
		rownumbers="false" navigator="false" width="945" shrinkToFit="true"
		formIds="manageCapacityDetailReportFormId"
		navigatorEdit="false" navigatorAdd="false" navigatorSearch="false"
		navigatorRefresh="false"
		editinline="false"
		navigatorDelete="false" 
		multiselect="false"
		viewrecords="true">
		<sjg:gridColumn name="id" index="id" title="Id" hidden="true"
			editable="true" />
		
		<sjg:gridColumn name="userName" index="userName" 
			title="Resource Name" align="left" editable="false" width="400"
			search="false" />
		<sjg:gridColumn name="janCapacity" index="janCapacityId" title="Jan"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="febCapacity" index="febCapacityId" title="Feb"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="marCapacity" index="marCapacityId" title="Mar"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="aprCapacity" index="aprCapacityId" title="Apr"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="mayCapacity" index="mayCapacityId" title="May"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="junCapacity" index="junCapacityId" title="Jun"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="julCapacity" index="julCapacityId" title="Jul"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="augCapacity" index="augCapacityId" title="Aug"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="sepCapacity" index="sepCapacityId" title="Sep"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="octCapacity" index="octCapacityId" title="Oct"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="novCapacity" index="novCapacityId" title="Nov"
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="decCapacity" index="decCapacityId" title="Dec"
			editrules="{number: true}" align="center" editable="false" />

		<sjg:gridColumn name="createdBy" index="createdBy" title="Created By"
			hidden="true" editable="false" />
		<sjg:gridColumn name="createdDate" index="createdDate"
			title="Created Date" hidden="true" editable="false" />
		<sjg:gridColumn name="formattedCreatedDate" index="createdDate"
			search="false" title="Created Date" sortable="true" hidden="true" />
		<sjg:gridColumn name="updatedBy" index="updatedBy" search="false"
			title="Updated By" sortable="true" hidden="true" />
		<sjg:gridColumn name="formattedUpdatedDate" index="updatedDate"
			search="false" title="Updated Date" sortable="true" hidden="true" />

	</sjg:grid>


	<sjg:gridColumn name="id" index="id" title="ProjectId" hidden="true" />
	<sjg:gridColumn name="networkCode" index="networkCode" title="Project" width="250"
		sortable="true" />
	<sjg:gridColumn name="appName" index="appName" title="Application" width="250"
		sortable="true" />
	

</sjg:grid>
