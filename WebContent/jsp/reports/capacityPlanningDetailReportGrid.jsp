<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
 
<sjg:grid id="gridmultitable"
	href="../reports/ajaxCapacityPlanningUserDetailsReport.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="capacityReportDetailsgridModel" navigator="false" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="false" navigatorSearch="false"
	navigatorRefresh="false" navigatorCloneToTop="true"
	rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	formIds="manageCapacityDetailReportFormId" onGridCompleteTopics="gridCompleteTopic" onBeforeTopics="gridCompleteTopic"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">
	<%--onSubGridRowExpanded="subgridExpanded" --%>

	<sjg:grid dataType="json" id="subgridtable" indicator="false"
		subGridUrl="../reports/ajaxCapacityPlanningDetailsReport.action"
		gridModel="subGridModel" pager="false" toppager="false"
		rownumbers="false" navigator="false" width="945" shrinkToFit="true"
		formIds="manageCapacityDetailReportFormId"
		navigatorEdit="false" navigatorAdd="false" navigatorSearch="false"
		navigatorRefresh="false"
		editinline="false"
		editurl="../capacity/capacityPlanningMACD.action" 
		navigatorDelete="false" navigatorDeleteOptions="{reloadAfterSubmit:true,closeAfterDelete:true}" 
		multiselect="false" onGridCompleteTopics="subgridComplete" onBeforeTopics="subgridComplete"
		viewrecords="true">
		<sjg:gridColumn name="id" index="id" title="Id" hidden="true"
			editable="true" />
		<sjg:gridColumn name="userId" index="userId" title="UserId"
			editable="true" hidden="true" edittype="select"
			editoptions="{dataUrl:'../capacity/ajaxUserId.action'}"
			search="false" />

		<sjg:gridColumn name="year" index="year" title="Year" editable="false"
			edittype="text" hidden="true" search="false"
			editoptions="{defaultValue:function(){ return document.getElementById('selectedYear').value; }}" />

		<sjg:gridColumn name="networkCode" index="networkCodeId"  width="798"
			title="Network Code" align="left" editable="false" edittype="select"
			editoptions="{dataUrl: '%{selecturl}', postData: function (rowid, value) {
            return {
                id: rowid,
                networkId: value
            }
        }
			}"
			search="false" />
		<sjg:gridColumn name="janCapacity" index="janCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="febCapacity" index="febCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="marCapacity" index="marCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="aprCapacity" index="aprCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="mayCapacity" index="mayCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="junCapacity" index="junCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="julCapacity" index="julCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="augCapacity" index="augCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="sepCapacity" index="sepCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="octCapacity" index="octCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="novCapacity" index="novCapacityId" title=""
			editrules="{number: true}" align="center" editable="false" />
		<sjg:gridColumn name="decCapacity" index="decCapacityId" title=""
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


	<sjg:gridColumn name="id" index="id" title="UserId" hidden="true" />
	<sjg:gridColumn name="userName" index="userName" title="Name" width="500"
		sortable="true" />
	<sjg:gridColumn name="primarySkill" index="primarySkill" width="270" title="Primary Skill"
		sortable="false" />
	<sjg:gridColumn name="janCapacity" index="janCapacityId" title="Jan"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="febCapacity" index="febCapacityId" title="Feb"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="marCapacity" index="marCapacityId" title="Mar"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="aprCapacity" index="aprCapacityId" title="Apr"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="mayCapacity" index="mayCapacityId" title="May"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="junCapacity" index="junCapacityId" title="Jun"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="julCapacity" index="julCapacityId" title="Jul"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="augCapacity" index="augCapacityId" title="Aug"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="sepCapacity" index="sepCapacityId" title="Sep"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="octCapacity" index="octCapacityId" title="Oct"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="novCapacity" index="novCapacityId" title="Nov"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
		<sjg:gridColumn name="decCapacity" index="decCapacityId" title="Dec"
			editrules="{number: true}" align="center" editable="false" formatter="colorFormatter"/>
	

</sjg:grid>
