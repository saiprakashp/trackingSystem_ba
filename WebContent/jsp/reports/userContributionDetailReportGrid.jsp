<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../reports/ajaxUserContributionDetailsReport.action"
	dataType="json" pager="true" toppager="true" pagerPosition="center"
	gridModel="contributiongridModel" navigator="true"
	navigatorEdit="false" navigatorAdd="false" navigatorDelete="false"
	navigatorSearch="false"
	navigatorExtraButtons="{
			edit : { 
	    		title : 'Edit User', 
	    		icon: 'ui-icon-pencil',
	    		position:'first',
	    		onclick: function(){ macdUser('edit'); }
    		}
    	}"
	navigatorRefresh="false" navigatorCloneToTop="true"
	rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	multiselect="false" sortable="true" viewrecords="true" width="975"
	onSelectRowTopics="rowselect" shrinkToFit="true" formIds="contributionDetailReportFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">

	<sjg:gridColumn name="id" index="id" title="UserId" hidden="true" />
	<sjg:gridColumn name="name" index="name" title="Resource Name"
		width="300" sortable="true" />
	<sjg:gridColumn name="userName" index="userName" title="SIGNUM"
		sortable="true" />
	<sjg:gridColumn name="supervisor" index="supervisor" title="Supervisor"
		width="300" sortable="false" />
	<sjg:gridColumn name="location" index="location" title="Location"
		sortable="false" />
	<sjg:gridColumn name="streamName" index="streamName" title="Stream"
		sortable="false" />
	<sjg:gridColumn name="status" index="status" title="Status"
		sortable="false" />
	<sjg:gridColumn name="platformName" index="platformName" title="Pillar"
		sortable="false" />
	<sjg:gridColumn name="contribution" index="contribution" align="center"
		title="Contribution" sortable="true" />

</sjg:grid>
