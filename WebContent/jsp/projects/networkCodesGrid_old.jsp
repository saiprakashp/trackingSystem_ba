<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../projects/ajaxManageNetworkCodes.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="networkGridModel" navigator="true"
	navigatorAddOptions="{height:350,width:1350,reloadAfterSubmit:true,closeAfterAdd:true}"
	navigatorEditOptions="{height:350,width:1350,reloadAfterSubmit:true,closeAfterEdit:true}"
	navigatorDeleteOptions="{reloadAfterSubmit:true,closeAfterDelete:true}"
	navigatorSearchOptions="{reloadAfterSubmit:true,closeAfterSearch:true}"
	rowList="10,15,25" rowNum="10" multiselect="true"
	editurl="../projects/networkMACDOperation.action" sortable="true"
	viewrecords="true" width="975" shrinkToFit="true">
	<sjg:gridColumn name="id" index="id" title="Id" hidden="true"
		editable="true" />
	<sjg:gridColumn name="networkCodeId" index="networkCodeId"
		title="Network Id" sortable="true" editable="true"
		editrules="{number: true, required: true}" />
	<sjg:gridColumn name="networkCode" index="networkCode"
		title="Network Code" sortable="true" editable="true" formoptions="{rowpos:2, colpos:2}"
		editrules="{required: true}" />
	<sjg:gridColumn name="customer" index="customerName" search="true"
		title="Account" sortable="true" editable="true"
		edittype="select"
		editoptions="{value:'%{customersMap}', dataEvents: [
                                       {  type: 'change', fn: function(value) {
                                    var params = 'selectedCustomer='+this.value;
                                    $.getJSON('../projects/loadPillars.action?'+params, function(retVal) 
                                    {
                                          var dropDown = document.getElementById('pillar');
                                            dropDown.innerHTML = retVal.pillarData;
                                    });
                             }
                          }
                      ]}" />
	<sjg:gridColumn name="pillar" index="pillarName" search="false"
		title="Pillar/ Product Name" sortable="true" editable="true"
		edittype="select"  formoptions="{rowpos:4, colpos:2}"
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
		
	<sjg:gridColumn name="projectCategory" index="projectCategory" search="false"
		title="Project Category" sortable="true" editable="true" hidden="true"
		edittype="select" editrules="{edithidden:true}" formoptions="{rowpos:6, colpos:2}"
		editoptions="{value:'%{projectCategoryMap}', dataEvents: [
                                       {  type: 'change', fn: function(value) {
                                    var params = 'selectedProjectCategory='+this.value;
                                    $.getJSON('../projects/loadProjectSubCategory.action?'+params, function(retVal) 
                                    {
                                          var dropDown = document.getElementById('projectSubCategory');
                                            dropDown.innerHTML = retVal.subProjectCategoryData;
                                    });
                             }
                          }
                      ]}" />
	<sjg:gridColumn name="projectSubCategory" index="projectSubCategory" search="false" formoptions="{rowpos:6, colpos:3}"
		title="Project Sub Category" sortable="true" editable="true" edittype="select" hidden="true"
		editoptions="{value:'%{subProjectCategoryMap}'}" editrules="{edithidden:true}" />
		
	<sjg:gridColumn name="productOwner" index="productOwnerName" search="false"  formoptions="{rowpos:6, colpos:4}"
		title="Product Owner" sortable="true" editable="true" edittype="select" 
		editoptions="{value:'%{productOwnersMap}'}" />
	
	<sjg:gridColumn name="priority" index="priority" 
		title="Priority" sortable="true" editable="true" editoptions="{size: 12}"/>
		
	<sjg:gridColumn name="programManager" index="programManagerName" search="false"
		title="Program Manager" sortable="true" editable="true" edittype="select"   formoptions="{rowpos:10, colpos:2}"
		editoptions="{value:'%{programManagersMap}'}" />
		
	<sjg:gridColumn name="projectManager" index="projectManagerName" search="false"
		title="Project Manager" sortable="true" editable="true" edittype="select"  formoptions="{rowpos:10, colpos:3}" 
		editoptions="{value:'%{projectManagersMap}'}" />
		
	
	<sjg:gridColumn name="startDate" index="startDate" title="Start Date"
		value="formattedStartDate" sortable="true" formatter="date"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}"
		editable="true"  formoptions="{rowpos:10, colpos:4}"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />

	<sjg:gridColumn name="endDate" index="endDate" title="End Date"
		value="formattedEndDate" sortable="true" formatter="date"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}"
		editable="true"  formoptions="{rowpos:10, colpos:5}"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />
		

	<sjg:gridColumn name="plannedDesignDate" index="plannedDesignDate" title="Planned Design Date"
		value="formattedStartDate" sortable="true" formatter="date" hidden="true" editrules="{edithidden:true}"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}"
		editable="true"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />

	<sjg:gridColumn name="plannedDevDate" index="plannedDevDate" title="Development Date"
		value="formattedStartDate" sortable="true" formatter="date" hidden="true" editrules="{edithidden:true}"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}"
		editable="true"  formoptions="{rowpos:15, colpos:2}"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />	
		
	<sjg:gridColumn name="plannedTestDate" index="plannedTestDate" title="Testing Date"
		value="formattedStartDate" sortable="true" formatter="date" hidden="true" editrules="{edithidden:true}"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}"
		editable="true" formoptions="{rowpos:15, colpos:3}"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />	
		
	<sjg:gridColumn name="plannedImplDate" index="plannedImplDate" title="Implementation Date"
		value="formattedStartDate" sortable="true" formatter="date" hidden="true" editrules="{edithidden:true}"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}"
		editable="true" formoptions="{rowpos:15, colpos:4}"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />
	
	<sjg:gridColumn name="plannedOprHandoffDate" index="plannedOprHandoffDate" title="Operational Handoff Date"
		value="formattedStartDate" sortable="true" formatter="date" hidden="true" editrules="{edithidden:true}"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}"
		editable="true" formoptions="{rowpos:15, colpos:5}"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />

	<sjg:gridColumn name="actualDesignDate" index="actualDesignDate" title="Actual Design Date"
		value="formattedEndDate" sortable="true" formatter="date"  
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}" hidden="true" editrules="{edithidden:true}"
		editable="true" 
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />		
		
	<sjg:gridColumn name="actualDevDate" index="actualDevDate" title="Development Date"
		value="formattedEndDate" sortable="true" formatter="date"  formoptions="{rowpos:20, colpos:2}"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}" hidden="true" editrules="{edithidden:true}"
		editable="true" 
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />	

	<sjg:gridColumn name="actualTestDate" index="actualTestDate" title="Testing Date"
		value="formattedEndDate" sortable="true" formatter="date"  formoptions="{rowpos:20, colpos:3}"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}" hidden="true" editrules="{edithidden:true}"
		editable="true"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />
		
	<sjg:gridColumn name="actualImplDate" index="actualImplDate" title="Implementation Date"
		value="formattedEndDate" sortable="true" formatter="date"  formoptions="{rowpos:20, colpos:4}"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}" hidden="true" editrules="{edithidden:true}"
		editable="true"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />

	<sjg:gridColumn name="actualOprHandoffDate" index="actualOprHandoffDate" title="Operational Handoff Date"
		value="formattedEndDate" sortable="true" formatter="date"  formoptions="{rowpos:20, colpos:5}"
		formatoptions="{newformat : 'm/d/Y', srcformat : 'Y-m-d H:i:s'}" hidden="true" editrules="{edithidden:true}"
		editable="true"
		editoptions="{size: 12, maxlength: 10, dataInit:datePick,attr:{title:'Select the date'} }" />								

	<sjg:gridColumn name="globalDevLOE" index="globalDevLOE" search="false"
		title="Global Dev LOE" editable="true" hidden="true"  editoptions="{size: 12, maxlength: 10}" 
		editrules="{number: true, required: true, edithidden:true}" />
		
	<sjg:gridColumn name="globalTestLOE" index="globalTestLOE" search="false"
		title="Global Test LOE" editable="true" hidden="true"  formoptions="{rowpos:25, colpos:2}" editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: true, edithidden:true}" />	
		
	<sjg:gridColumn name="globalProjectManagementLOE" index="globalProjectManagementLOE" search="false"
		title="Global PM LOE" editable="true" hidden="true"    formoptions="{rowpos:25, colpos:3}" editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: false, edithidden:true}" />
				
	<sjg:gridColumn name="globalImplementationLOE" index="globalImplementationLOE" search="false"
		title="Global Impl LOE" editable="true" hidden="true"  formoptions="{rowpos:25, colpos:4}" editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: false, edithidden:true}" />
		
	<sjg:gridColumn name="globalOperationsHandOffLOE" index="globalOperationsHandOffLOE" search="false"
		title="Global Opr Handoff LOE" editable="true" hidden="true" formoptions="{rowpos:25, colpos:5}" editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: false, edithidden:true}" />
		
	<sjg:gridColumn name="globalOthersLOE" index="globalOthersLOE" search="false"
		title="Global Others LOE" editable="true" hidden="true" formoptions="{rowpos:25, colpos:6}" editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: false, edithidden:true}" />		
		

	<sjg:gridColumn name="localDevLOE" index="localDevLOE" search="false"
		title="Local Dev LOE" editable="true" hidden="true"  editoptions="{size: 12, maxlength: 10}" 
		editrules="{number: true, required: true, edithidden:true}" />
		
	<sjg:gridColumn name="localTestLOE" index="localTestLOE" search="false"
		title="Local Test LOE" editable="true" hidden="true"  formoptions="{rowpos:31, colpos:2}" editoptions="{size: 12, maxlength: 10}" 
		editrules="{number: true, required: true, edithidden:true}" />	
		
	<sjg:gridColumn name="localProjectManagementLOE" index="localProjectManagementLOE" search="false"
		title="Local PM LOE" editable="true" hidden="true"    formoptions="{rowpos:31, colpos:3}" editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: false, edithidden:true}" />
				
	<sjg:gridColumn name="localImplementationLOE" index="localImplementationLOE" search="false"
		title="Local Impl LOE" editable="true" hidden="true"  formoptions="{rowpos:31, colpos:4}" editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: false, edithidden:true}" />
		
	<sjg:gridColumn name="localOperationsHandOffLOE" index="localOperationsHandOffLOE" search="false"
		title="Local Opr Handoff LOE" editable="true" hidden="true" formoptions="{rowpos:31, colpos:5}" editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: false, edithidden:true}" />
		
	<sjg:gridColumn name="localOthersLOE" index="localOthersLOE" search="false"
		title="Local Others LOE" editable="true" hidden="true" formoptions="{rowpos:31, colpos:6}" editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: false, edithidden:true}" />				
		
					
		
	<sjg:gridColumn name="createdBy" index="createdBy" search="false"
		title="Created By" sortable="true" />
	<sjg:gridColumn name="createdBy" index="createdBy" title="Created By"
		hidden="true" editable="true" />
	<sjg:gridColumn name="createdDate" index="createdDate"
		title="Created Date" hidden="true" editable="true" />
	<sjg:gridColumn name="formattedCreatedDate" index="createdDate"
		search="false" title="Created Date" sortable="true" />
	<sjg:gridColumn name="updatedBy" index="updatedBy" search="false"
		title="Updated By" sortable="true" />
	<sjg:gridColumn name="formattedUpdatedDate" index="updatedDate"
		search="false" title="Updated Date" sortable="true" />
		
	<sjg:gridColumn name="sreqNo" index="sreqNo" search="false"
		title="No. of SREQs" editable="true" hidden="true" editoptions="{size: 12, maxlength: 10}"
		editrules="{number: true, required: false, edithidden:true}" />
		
	<sjg:gridColumn name="status" index="status" search="false"
		title="Status" sortable="true" editable="true" edittype="select"   formoptions="{rowpos:39, colpos:2}"
		editoptions="{value:'%{statusMap}'}" />
			
	<sjg:gridColumn name="description" index="description" search="false"
		title="Description" editable="true" edittype="textarea" formoptions="{rowpos:39, colpos:3}"/>
</sjg:grid>

