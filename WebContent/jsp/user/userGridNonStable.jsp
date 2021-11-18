<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable" href="../user/ajaxManageUser.action"
	dataType="json" pager="true" toppager="true" pagerPosition="center"
	gridModel="gridModel" navigator="true" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="true" navigatorSearch="false"
	navigatorDeleteOptions="{reloadAfterSubmit:true,closeAfterDelete:true}"
	navigatorRefresh="false" navigatorCloneToTop="true"
	navigatorExtraButtons="{
			edit : { 
	    		title : 'Edit User', 
	    		icon: 'ui-icon-pencil',
	    		position:'first',
	    		onclick: function(){ macdUser('edit'); }
    		},
    		add : { 
	    		title : 'Create User', 
	    		icon: 'ui-icon-plus', 
	    		position:'first',
	    		onclick: function(){ macdUser('add'); }
    		},
    		
    		exportexcel : { 
	    		title : 'Export Excel', 
	    		icon: 'ui-icon-document', 
	    		onclick: function(){ macdUser('exportexcel'); }
    		}
    	}"
	rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	multiselect="true" editurl="../user/macdOperation.action"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	onSelectRowTopics="rowselect" formIds="manageUserFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">
	<sjg:gridColumn name="id" index="id" title="UserId" hidden="true" />
	<sjg:gridColumn name="name" index="name" title="Name" width="250"
		sortable="true" />
	<sjg:gridColumn name="userName" index="userName" title="SIGNUM"
		sortable="true" />
	<sjg:gridColumn name="userType" index="userType" title="Employee Type"
		sortable="true" />
	<sjg:gridColumn name="role" index="role" title="Role" sortable="true" />
	<sjg:gridColumn name="streamName" index="streamName" title="Stream"
		sortable="true" />
	<sjg:gridColumn name="supervisor" index="supervisor" title="Supervisor"
		width="250" sortable="true" />
	<sjg:gridColumn name="backFillOff" index="backFillOff"
		title="Back fill off" sortable="true" width="200" />
	<sjg:gridColumn name="status" index="status" width="100" title="Status"
		sortable="true" />
	<sjg:gridColumn name="locationName" index="location" width="120"
		title="Location" sortable="true" />
	<sjg:gridColumn name="lineManager" index="lineManager" width="120"
		title="Line Manager" sortable="true" />
</sjg:grid>
