<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../reports/goGetUnPlannedCapacitySupname.action"
	dataType="json" pager="true" toppager="true" pagerPosition="center"
	gridModel="unPlancptyList" navigator="false" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="false" navigatorSearch="false"
	navigatorRefresh="true" navigatorCloneToTop="true"
	rowList="20,50,100,150,200" 
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true"  shrinkToFit="true"
	formIds="getUnlannedDetails" cssClass="table table-responsive text-center" autowidth="true"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">

	<sjg:grid onSelectRowTopics="selecRow" footerrow="false"
		dataType="json" id="subgridtable" indicator="false" autowidth="true"
		subGridUrl="../reports/goGetUnPlannedCapacityBySup.action"
		gridModel="unPlancptySunList" pager="false" toppager="false"  cssClass="table table-responsive text-center"
		viewsortcols="false" rownumbers="false" navigator="true" width="970"
		shrinkToFit="true" pagerButtons="false"
		formIds="getUnlannedDetails" navigatorEdit="false"
		onEditInlineBeforeTopics="reloadParent" navigatorAdd="false"
		navigatorSearch="false" navigatorRefresh="false" editinline="true"
		onselect="Selected()"
		navigatorDelete="true" onCellEditSuccessTopics="reloadParent()"
		navigatorDeleteOptions="{reloadAfterSubmit:true,closeAfterDelete:true}"
		multiselect="false" viewrecords="true"
		onGridCompleteTopics="subgridComplete">
		
		<sjg:gridColumn name="id" index="id" accesskey="true" title="userId"
			hidden="true" editable="true" />
		
		<sjg:gridColumn name="applicationName" index="applicationName" title="Application Name:"
		sortable="false" />


	</sjg:grid>


		<sjg:gridColumn name="id" index="id" title="id" hidden="true" />
	
	<sjg:gridColumn name="name" index="name" width="550" 
		title="Manager Name:"  sortable="false"/>


</sjg:grid>
