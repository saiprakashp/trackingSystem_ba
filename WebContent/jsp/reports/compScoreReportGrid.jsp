<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable" href="../reports/ajaxCompScoreReport.action"
	dataType="json" pager="true" toppager="true" pagerPosition="center"
	gridModel="gridModel" navigator="false" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="false" navigatorSearch="false"
	navigatorRefresh="false" navigatorCloneToTop="true" 
	rowList="20,50,100,150,200" rowNum="%{#session.rowNum == null? 20 : #session.rowNum}" multiselect="true"
	sortable="true"
	viewrecords="true" width="975" shrinkToFit="true" 
	formIds="compScoreReportFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">
	<sjg:gridColumn name="userName" index="userName" title="User Name" 
		sortable="true" />
	<sjg:gridColumn name="supervisorName" index="supervisorName" title="Supervisor" 
		sortable="false" />
	<sjg:gridColumn name="platformName" index="platformName"
		title="Platform Name" sortable="true" />
	<sjg:gridColumn name="compScore" index="compScore" 
		title="Competency Score" sortable="false" align="center"/>
	
</sjg:grid>
