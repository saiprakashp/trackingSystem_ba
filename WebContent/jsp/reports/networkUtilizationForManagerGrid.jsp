<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../reports/ajaxUserNWUtilization.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center" scroll="false"
	navigatorRefresh="false" gridModel="supervisorNwList" navigator="false"
	formIds="supervisorNwListId" navigatorEdit="false" navigatorAdd="false"
	navigatorDelete="false" navigatorSearch="false" multiselect="false"
	rowList="20,200,500" rowNum="20" sortable="false" viewrecords="true"
	width="975" shrinkToFit="true">
	<sjg:gridColumn name="NETWORKCODE" index="NETWORKCODE" search="false"
		width="500" title="Release Name" sortable="false" editable="false" />
	<sjg:gridColumn name="TFSEpic" index="TFSEpic" search="false"
		width="500" title="TFS Epic Id" sortable="false" editable="false" />
	<sjg:gridColumn name="NETWORKCODE" index="NETWORKCODE" search="false"
		width="500" title="Release Name" sortable="false" editable="false" />
	<sjg:gridColumn name="STATUS" index="STATUS" search="true" width="500"
		title="Status" sortable="false" editable="false" />
	<sjg:gridColumn name="RELEASETYPE" index="RELEASETYPE" search="true"
		width="500" title="Relase Name" sortable="false" editable="false" />
	<sjg:gridColumn name="SUPERVISORNAME" index="SUPERVISORNAME"
		search="false" width="500" title="Supervisor" sortable="false"
		editable="false" />
	<sjg:gridColumn name="PROJECT" index="PROJECT" search="false"
		width="500" title="Project" sortable="false" editable="false" />
	<sjg:gridColumn name="TOTALCAPACITY" index="TOTALCAPACITY"
		search="false" width="500" title="Total Capacity" sortable="false"
		editable="false" />
	<sjg:gridColumn name="SUMMATION" index="SUMMATION" search="false"
		width="500" title="Total Effort" sortable="false" editable="false" />
	<sjg:gridColumn name="IMPLEMENTATIONDATE" index="IMPLEMENTATIONDATE"
		search="false" width="500" title="Implementation Date"
		sortable="false" editable="false" />
	<sjg:gridColumn name="MONTH" index="MONTH" search="false" width="500"
		title="Month" sortable="false" editable="false" />
	<sjg:gridColumn name="YEAR" index="YEAR" search="false" width="500"
		title="Year" sortable="false" editable="false" />

</sjg:grid>
