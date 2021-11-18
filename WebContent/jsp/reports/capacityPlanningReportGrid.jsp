<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<sjg:grid id="gridmultitable"
	href="../reports/ajaxCapacityPlanningReport.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="capacityReportDetailsgridModel" navigator="false"
	navigatorEdit="false" navigatorAdd="false" navigatorDelete="false"
	navigatorSearch="false" navigatorRefresh="false"
	navigatorCloneToTop="true" rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true" 
	formIds="manageCapacityReportFormId"  onCompleteTopics="gridComplete"  onBeforeTopics="gridComplete"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">


	<sjg:gridColumn name="userName" index="userName" title="Name" width="500"
		sortable="true" />
	<sjg:gridColumn name="supervisorName" index="supervisorName" width="500"
		title="Supervisor" sortable="true" />
	<sjg:gridColumn name="headCount" index="headCount" title="Head count" width="300"
		align="center" editable="false" />
	<sjg:gridColumn name="targetHrs" index="targetHrs" title="Target Capacity" width="300"
		align="center" editable="false" />
	<sjg:gridColumn name="janCapacity" index="janCapacityId" title="Jan"
		align="center" editable="false" />
	<sjg:gridColumn name="febCapacity" index="febCapacityId" title="Feb"
		align="center" editable="false" />
	<sjg:gridColumn name="marCapacity" index="marCapacityId" title="Mar"
		align="center" editable="false" />
	<sjg:gridColumn name="aprCapacity" index="aprCapacityId" title="Apr"
		align="center" editable="false" />
	<sjg:gridColumn name="mayCapacity" index="mayCapacityId" title="May"
		align="center" editable="false" />
	<sjg:gridColumn name="junCapacity" index="junCapacityId" title="Jun"
		align="center" editable="false" />
	<sjg:gridColumn name="julCapacity" index="julCapacityId" title="Jul"
		align="center" editable="false" />
	<sjg:gridColumn name="augCapacity" index="augCapacityId" title="Aug"
		align="center" editable="false" />
	<sjg:gridColumn name="sepCapacity" index="sepCapacityId" title="Sep"
		align="center" editable="false" />
	<sjg:gridColumn name="octCapacity" index="octCapacityId" title="Oct"
		align="center" editable="false" />
	<sjg:gridColumn name="novCapacity" index="novCapacityId" title="Nov"
		align="center" editable="false" />
	<sjg:gridColumn name="decCapacity" index="decCapacityId" title="Dec"
		align="center" editable="false" />

</sjg:grid>