<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable" href="../user/ajaxUserUtilization.action"
	dataType="json" pager="true" toppager="true" pagerPosition="center"
	pagerButtons="false" pagerInput="false"
	formIds="manageUserUtilizationFormId"
	gridModel="userUtilizationGridModel" navigator="false"
	navigatorEdit="false" navigatorAdd="false" navigatorDelete="false"
	navigatorSearch="false" navigatorRefresh="false"
	navigatorCloneToTop="true" rowNum="-1" sortable="true" footerrow="true"
	onGridCompleteTopics="gridComplete" viewrecords="true" width="975"
	shrinkToFit="true">

	<sjg:gridColumn name="signum" index="signum" search="false"
		title="SIGNUM" sortable="false" editable="false" />
	<sjg:gridColumn name="resourceName" index="resourceName" search="false"
		title="Resource Name" sortable="false" editable="false" />
	<sjg:gridColumn name="supervisorName" index="supervisorName"
		search="false" title="Supervisor" sortable="false" editable="false" />
	<sjg:gridColumn name="targetHours" index="targetHours" search="false"
		title="Target Hours" sortable="false" editable="false" align="center" />
	<sjg:gridColumn name="essHrs" index="essHrs" search="false"
		hidden="false" title="ESS Charged Hours" sortable="false"
		editable="false" align="center" />
	<sjg:gridColumn name="actualHours" index="actualHours" search="false"
		title="PTS Charged Hours" sortable="false" editable="false"
		align="center" />
	<sjg:gridColumn name="differenceHrs" index="differenceHrs"
		search="false" title="Difference" sortable="false" editable="false"
		align="center" />
	<%-- 	<sjg:gridColumn name="percentage" index="percentage" search="false"
		title="Percentage" sortable="false" editable="false" align="center" /> --%>
</sjg:grid>
