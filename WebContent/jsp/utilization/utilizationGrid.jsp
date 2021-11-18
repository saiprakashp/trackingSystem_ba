<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../utilization/ajaxManageUserUtilization.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="gridModel" rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true" width="1044" shrinkToFit="true"
	formIds="manageUtilizationFormId">


	<sjg:gridColumn name="id" index="id" title="UserId" hidden="true"
		editable="true" key="true" />
	<sjg:gridColumn name="signum" index="signum" title="Signum"
		key="true" />
	<sjg:gridColumn name="resourceName" index="resourceName"
		title="Resource Name" />
	<sjg:gridColumn name="weekEndingDate" index="weekEndingDate"
		title="Weekending Date" />
	<sjg:gridColumn name="networkCodeName" index="networkCodeName"
		title="Project Name" align="center" />
		
			<sjg:gridColumn name="activityCodeName" index="activityCodeName"
		title="Activity Code" align="center" />
		
	<sjg:gridColumn name="effort" index="effort" title="Effort"
		align="center" />
	<sjg:gridColumn name="supervisorName" index="supervisorName"
		title="Supervisor" align="center" />





</sjg:grid>
