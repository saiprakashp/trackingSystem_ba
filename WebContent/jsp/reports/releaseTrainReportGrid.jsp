<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../reports/ajaxReleaseTrainReport.action" dataType="json"
	pager="false" toppager="false" pagerPosition="center"
	gridModel="releaseTrainGridModel" navigator="false" rowList="200"   rowNum="200"
	formIds="releaseTrainReportFormId" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="false" navigatorSearch="false"
	onCompleteTopics="releaseTrainComp" multiselect="false"
	sortable="false" viewrecords="true" width="975" shrinkToFit="true">
	
	<sjg:gridColumn name="feasibility" index="feasibility" search="true"
		formatter="feasibilityFormatter" width="500" title="Feasibility"
		sortable="false" editable="false" />
	<sjg:gridColumn name="development" index="development" search="true"
		formatter="developmentFormatter" width="500" title="Development"
		sortable="false" editable="false" />
	<sjg:gridColumn name="systemTest" index="systemTest" search="true"
		formatter="systemTestFormatter" width="500" title="System Test"
		sortable="false" editable="false" />
	<sjg:gridColumn name="deployment" index="deployment" search="true"
		formatter="deploymentFormatter" width="500" title="Deployment"
		sortable="false" editable="false" />
	<sjg:gridColumn name="hold" index="hold" search="true" title="Hold"
		formatter="holdFormatter" width="500" sortable="false"
		editable="false" />
	<sjg:gridColumn name="none" index="none" search="true" title="None"
		formatter="noneFormatter" width="500" sortable="false"
		editable="false" />
</sjg:grid>
