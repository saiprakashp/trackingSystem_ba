<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<sjg:grid id="gridmultitable"
	href="../capacity/ajaxCapacityPlanning.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="gridModel" navigator="false" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="false" navigatorSearch="false"
	navigatorRefresh="false" navigatorCloneToTop="true"
	rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	formIds="manageUtilizationFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">
	
		  <sjg:grid 
            dataType="json" 
            id="subgridtable" 
            indicator="false"
            subGridUrl="../utilization/ajaxNCUtilizationDetails.action" 
            gridModel="subGridModel"
            pager="false" 
            rownumbers="false"
            navigator="false" 
            width="945" 
            shrinkToFit="true" 
            formIds="manageUtilizationFormId"
            >
            <sjg:gridColumn name="MONTH" index="MONTH" title="Month" align="left" />  
            <sjg:gridColumn name="NETWORKCODE" index="NETWORKCODE" title="Network Code" align="left" />
            <sjg:gridColumn name="ACTIVITYCODE" index="ACTIVITYCODE" title="Activity Code" align="left" />
            <sjg:gridColumn name="CHARGEDHRS" index="CHARGEDHRS" title="Total Hours Charged" align="right" />
	    	
	    </sjg:grid>
		
	<sjg:gridColumn name="id" index="id" title="UserId" hidden="true" />
	<sjg:gridColumn name="name" index="name" title="Name" width="250"
		sortable="true" />

	<sjg:gridColumn name="supervisor" index="supervisor" title="Supervisor"
		width="250" sortable="true" />
	
</sjg:grid>
