<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<sjg:grid id="gridmultitable"
	href="../projects/ajaxManageNetworkCodes.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="networkGridModel" navigator="true" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="true" navigatorSearch="false"
	navigatorDeleteOptions="{reloadAfterSubmit:true,closeAfterDelete:true}"
	navigatorRefresh="false" navigatorCloneToTop="true"
	navigatorExtraButtons="{
			
			edit : { 
	    		title : 'Edit Release', 
	    		icon: 'ui-icon-pencil',
	    		position:'first',
	    		onclick: function(){ macdUser('editnetworkcode'); }
    		},
    		Clone : { 
	    		title : 'Clone Release', 
	    		icon: 'ui-icon-copy', 
	    		position:'first',
	    		onclick: function(){ macdUser('cloneNetworkCode'); }
    		},
    		add : { 
	    		title : 'Create Release', 
	    		icon: 'ui-icon-plus', 
	    		position:'first',
	    		onclick: function(){ macdUser('addnetworkcode'); }
    		},
    		exportexcel : { 
	    		title : 'Export Excel', 
	    		icon: 'ui-icon-document', 
	    		onclick: function(){ macdUser('downloadNetworkCodes'); }
    		}
    		
    	}"
	rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	multiselect="true" editurl="../projects/networkMACDOperation.action"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	onSelectRowTopics="ncrowselect" formIds="manageNetworkCodeFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">
	<sjg:gridColumn name="id" index="id" title="Id" hidden="true"
		editable="true" />

	<sjg:gridColumn name="TFSEpic" index="TFSEpic" title="TFS Epic"
		sortable="true" editable="true" editoptions="{readonly:'true'}" />

	<sjg:gridColumn name="releaseId" index="releaseId" title="Release Id"
		sortable="true" editable="true" editoptions="{readonly:'true'}" />
	<sjg:gridColumn name="releaseName" index="releaseName"
		title="Release Name" sortable="true" editable="true"
		editoptions="{readonly:'true'}" width="300" />

	<sjg:gridColumn name="pillar" index="pillarName" search="false"
		title="Pillar" sortable="true" editable="true" edittype="select"
		formoptions="{rowpos:4, colpos:2}"
		editoptions="{value:'%{pillarsMap}', dataEvents: [
                                       {  type: 'change', fn: function(value) {
                                    var params = 'selectedPillar='+this.value;
                                    $.getJSON('../projects/loadProjects.action?'+params, function(retVal) 
                                    {
                                          var dropDown = document.getElementById('project');
                                            dropDown.innerHTML = retVal.projectData;
                                    });
                             }
                          }
                      ]}" />
	<sjg:gridColumn name="project" index="projectName" search="false"
		title="Application" sortable="true" editable="true" edittype="select"
		editoptions="{value:'%{projectsMap}'}" />



	<sjg:gridColumn name="programManager" index="programManagerName"
		search="false" title="Program Manager" sortable="true" editable="true"
		edittype="select" formoptions="{rowpos:10, colpos:2}"
		editoptions="{value:'%{programManagersMap}'}" />

	<sjg:gridColumn name="projectManager" index="projectManagerName"
		search="false" title="Project Manager" sortable="true" editable="true"
		edittype="select" formoptions="{rowpos:10, colpos:3}"
		editoptions="{value:'%{projectManagersMap}'}" />


	<sjg:gridColumn name="startDate" index="startDate" title="Start Date"
		value="formattedStartDate" sortable="true" formatter="date"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}"
		editable="true" formoptions="{rowpos:10, colpos:4}"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />

	<sjg:gridColumn name="endDate" index="endDate" title="End Date"
		value="formattedEndDate" sortable="true" formatter="date"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}"
		editable="true" formoptions="{rowpos:10, colpos:5}"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />


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

	<sjg:gridColumn name="sreqNo" index="sreqNo" search="false"
		title="No. of SREQs" editable="true" hidden="true"
		editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: false, edithidden:true}" />

	<sjg:gridColumn name="status" index="status" search="false"
		title="Status" sortable="true" editable="true" edittype="select"
		formoptions="{rowpos:39, colpos:2}"
		editoptions="{value:'%{statusMap}'}" />
<%-- 
	<sjg:gridColumn name="totalLocalLoe" index="totalLocalLoe"
		search="false" title="Local LOE" sortable="false" editable="false" />

	<sjg:gridColumn name="totalGlobalLoe" index="totalGlobalLoe"
		search="false" title="Global LOE" sortable="false" editable="false" /> --%>
	<sjg:gridColumn name="totalLOE" index="totalLOE" title="Total LOE"
		sortable="true" editable="true" editoptions="{readonly:'true'}" />
	<sjg:gridColumn name="stableTeamName" index="stableTeamName" title="Stable Team Name"
		sortable="true" editable="true" editoptions="{readonly:'true'}" />

</sjg:grid>

