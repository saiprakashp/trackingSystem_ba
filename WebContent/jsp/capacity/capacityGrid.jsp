<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
 
<sjg:grid id="gridmultitable"
	href="../capacity/ajaxCapacityPlanning.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="gridModel" navigator="false" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="false" navigatorSearch="false"
	navigatorRefresh="false" navigatorCloneToTop="true"
	rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	formIds="manageCapacityFormId" onSubGridRowExpanded="subgridExpanded"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">
	<%--onSubGridRowExpanded="subgridExpanded" --%>

	<sjg:grid dataType="json" id="subgridtable" indicator="false"
		subGridUrl="../capacity/ajaxCapacityPlanningDetails.action"
		gridModel="subGridModel" pager="false" toppager="false"
		rownumbers="false" navigator="true" width="945" shrinkToFit="true"
		formIds="manageCapacityFormId"
		navigatorEdit="false" navigatorAdd="false" navigatorSearch="false"
		navigatorRefresh="false"
		editinline="true"
		editurl="../capacity/capacityPlanningMACD.action" 
		navigatorDelete="true" navigatorDeleteOptions="{reloadAfterSubmit:true,closeAfterDelete:true}" 
		multiselect="true"  footerrow="true" onGridCompleteTopics="subgridComplete"
		viewrecords="true">
		<sjg:gridColumn name="id" index="id" title="Id" hidden="true"
			editable="true" />
		<sjg:gridColumn name="userId" index="userId" title="UserId"
			editable="true" hidden="true" edittype="select"
			editoptions="{dataUrl:'../capacity/ajaxUserId.action'}"
			search="false" />

		<sjg:gridColumn name="year" index="year" title="Year" editable="true"
			edittype="text" hidden="true" search="false"
			editoptions="{defaultValue:function(){ return document.getElementById('selectedYear').value; }}" />

		<sjg:gridColumn name="networkCode" index="networkCodeId" width="800"
			title="Network Code" align="left" editable="true" edittype="select"
			editoptions="{dataUrl: '%{selecturl}', postData: function (rowid, value) {
            return {
                id: rowid,
                networkId: value
            }
        }
			}"
			search="false" />
		<sjg:gridColumn name="janCapacity" index="janCapacityId" title="Jan"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="febCapacity" index="febCapacityId" title="Feb"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="marCapacity" index="marCapacityId" title="Mar"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="aprCapacity" index="aprCapacityId" title="Apr"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="mayCapacity" index="mayCapacityId" title="May"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="junCapacity" index="junCapacityId" title="Jun"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="julCapacity" index="julCapacityId" title="Jul"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="augCapacity" index="augCapacityId" title="Aug"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="sepCapacity" index="sepCapacityId" title="Sep"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="octCapacity" index="octCapacityId" title="Oct"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="novCapacity" index="novCapacityId" title="Nov"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="decCapacity" index="decCapacityId" title="Dec"
			editrules="{number: true}" align="center" editable="true" />

		<sjg:gridColumn name="createdBy" index="createdBy" title="Created By"
			hidden="true" editable="true" />
		<sjg:gridColumn name="createdDate" index="createdDate"
			title="Created Date" hidden="true" editable="true" />
		<sjg:gridColumn name="formattedCreatedDate" index="createdDate"
			search="false" title="Created Date" sortable="true" hidden="true" />
		<sjg:gridColumn name="updatedBy" index="updatedBy" search="false"
			title="Updated By" sortable="true" hidden="true" />
		<sjg:gridColumn name="formattedUpdatedDate" index="updatedDate"
			search="false" title="Updated Date" sortable="true" hidden="true" />

	</sjg:grid>


	<sjg:gridColumn name="id" index="id" title="UserId" hidden="true" />
	<sjg:gridColumn name="name" index="name" title="Name" width="250"
		sortable="true" />

	<sjg:gridColumn name="supervisor" index="supervisor" title="Supervisor"
		width="250" sortable="true" />
	<sjg:gridColumn name="primarySkillName" index="primarySkillName" width="100" title="Primary Skill"
		sortable="false" />
	<sjg:gridColumn name="region" index="region"
			search="false" title="Region" sortable="false" hidden="true" />
</sjg:grid>
