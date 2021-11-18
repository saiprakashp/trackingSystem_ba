<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../capacity/ajaxCcapacityPlanningDetailsEditableReport.action"
	dataType="json" pager="true" toppager="true" pagerPosition="center"
	gridModel="ncGridModel" navigator="false" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="false" navigatorSearch="false"
	navigatorRefresh="true" navigatorCloneToTop="true"
	rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	formIds="manageCapacityDetailReportFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">

	<sjg:grid onSelectRowTopics="selecRow" footerrow="false"
		dataType="json" id="subgridtable" indicator="false"
		subGridUrl="../capacity/ajaxCapacityPlanningNCUserDetailsEditableReport.action"
		gridModel="subGridModel" pager="false" toppager="false"
		viewsortcols="false" rownumbers="false" navigator="true" width="970"
		shrinkToFit="true" pagerButtons="false"
		formIds="manageCapacityDetailReportFormId" navigatorEdit="false"
		onEditInlineBeforeTopics="reloadParent" navigatorAdd="false"
		navigatorSearch="false" navigatorRefresh="false" editinline="true"
		onselect="Selected()"
		editurl="../capacity/ajaxCapacityPlanningUserEditReport.action"
		navigatorDelete="true" onCellEditSuccessTopics="reloadParent()"
		navigatorDeleteOptions="{reloadAfterSubmit:true,closeAfterDelete:true}"
		multiselect="true" viewrecords="true"
		onGridCompleteTopics="subgridComplete">
		<sjg:gridColumn name="id" index="id" accesskey="true" title="Id"
			hidden="true" editable="true" />
		<sjg:gridColumn name="networkCode" index="networkCode"
			title="NetworkId" editable="true" hidden="true" edittype="select"
			editoptions="{dataUrl:'../capacity/ajaxNetworkId.action'}"
			search="false" />
		<sjg:gridColumn name="year" index="year" title="Year" editable="true"
			edittype="text" hidden="true" search="false"
			editoptions="{defaultValue:function(){ return document.getElementById('selectedYear').value; }}" />

		<sjg:gridColumn name="capUserId" index="userName" width="300"
			title="Resource Name" align="left" editable="true" edittype="select"
			editoptions="{dataUrl: '%{selecturl}', postData: function (rowid, value) {
            return {
                id: rowid,
                selectedUserId: value,
                supervisorId : document.getElementById('searchSupervisor').value
            }
        }
			}"
			search="false" />

		<sjg:gridColumn name="janCapacity" index="janCapacity" title="Jan"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="febCapacity" index="febCapacity" title="Feb"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="marCapacity" index="marCapacity" title="Mar"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="aprCapacity" index="aprCapacity" title="Apr"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="mayCapacity" index="mayCapacity" title="May"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="junCapacity" index="junCapacity" title="Jun"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="julCapacity" index="julCapacity" title="Jul"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="augCapacity" index="augCapacity" title="Aug"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="sepCapacity" index="sepCapacity" title="Sep"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="octCapacity" index="octCapacity" title="Oct"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="novCapacity" index="novCapacity" title="Nov"
			editrules="{number: true}" align="center" editable="true" />
		<sjg:gridColumn name="decCapacity" index="decCapacity" title="Dec"
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


	<sjg:gridColumn name="id" index="id" title="ProjectId" hidden="true" />
	<sjg:gridColumn name="networkCodeId" index="networkCodeId"
		title="networkCodeId" sortable="false" hidden="true" />

	<sjg:gridColumn name="networkCode" index="networkCode" title="Project"
		width="550" sortable="false" />
	<sjg:gridColumn name="appName" index="appName" title="Application"
		sortable="false" />


	<sjg:gridColumn name="totDevLoe" index="originalDevLoe" title="Dev LOE"
		align="center" sortable="false" />

	<%--<sjg:gridColumn name="testLoe" index="originalTestLoe" title="Test LOE"
		align="center" sortable="false" />--%>
	<sjg:gridColumn name="totalManagerCapacity"
		index="totCap" title="Planned Capacity" align="center"
		sortable="false" />
	<sjg:gridColumn name="totCharg" index="totCharg" title="Charged Hours"
		align="center" sortable="false" />


</sjg:grid>
