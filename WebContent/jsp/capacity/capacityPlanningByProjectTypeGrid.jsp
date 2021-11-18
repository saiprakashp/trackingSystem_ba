<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<sjg:grid id="gridmultitable"
	href="../capacity/goGetCapacityDetailsByProjectType.action"
	dataType="json" pager="false" toppager="false" pagerPosition="center"
	gridModel="cptyPlanDetByTypeList" navigator="false"
	navigatorEdit="false" navigatorAdd="false" navigatorDelete="false"
	navigatorSearch="false" navigatorRefresh="false"
	navigatorCloneToTop="true" sortable="true" viewrecords="true"
	width="975" shrinkToFit="true" formIds="manageCapacityReportFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">


	<sjg:gridColumn name="month" index="month" title="Month" align="center"
		sortable="true" />
	<sjg:gridColumn name="headCount" index="headCount"
		align="center" title="Head Count" sortable="true" />
	<sjg:gridColumn name="targetCapacity" index="targetCapacity"
		align="center" title="Target Capacity" sortable="true" />
	<sjg:gridColumn name="projectCapacity" index="projectCapacity"
		align="center" title="Project" sortable="true" />
	<sjg:gridColumn name="adhocCapacity" index="adhocCapacity" align="center"
		title="Adhoc" sortable="true" />
	<sjg:gridColumn name="feasibilityCapacity" index="feasibilityCapacity"
		align="center" title="Feasibility" sortable="true" />
	<sjg:gridColumn name="fouthSupportCapacity"
		index="fouthSupportCapacity" align="center" title="4th Support"
		sortable="true" />
	<sjg:gridColumn name="totalCapacity" index="totalCapacity" align="center"
		title="Total" sortable="true" />
	<sjg:gridColumn name="capacityDiff" index="capacityDiff" align="center" width="300"
		title="Difference (Target - Total)" sortable="true" />


</sjg:grid>