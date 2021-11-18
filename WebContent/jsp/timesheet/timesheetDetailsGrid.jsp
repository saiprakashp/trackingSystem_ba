<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../utilization/ajaxUserUtilizationReport.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="gridModel" rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	formIds="manageRICOUtilizationReportFormId">
	<sjg:gridColumn name="weekEndingDate" index="weekEndingDate"
		title="Weekend" sortable="false" align="center" />
	<sjg:gridColumn name="networkCodeName" index="networkCodeName"
		title="Project" sortable="false" align="left" />
	<sjg:gridColumn name="activityCodeName" index="activityCodeName"
		title="Activity" sortable="false" align="left" />
	<sjg:gridColumn name="type" index="type" title="Time type"
		sortable="false" align="left" />
	<sjg:gridColumn name="approvedHrs" index="rejectedHrs"
		title="Approved Hours" sortable="false" align="center" />
	<sjg:gridColumn name="rejectedHrs" index="rejectedHrs"
		title="Pending/ Rejected Hours" sortable="false" align="center" />
</sjg:grid>
