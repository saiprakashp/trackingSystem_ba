<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="feedbackList" href="../others/goAjaxGetfeedback.action"
	dataType="json" pager="true" toppager="true" pagerPosition="center"
	gridModel="feedbackList" navigator="true"
	navigatorAddOptions="{height:300,width:400,reloadAfterSubmit:true,closeAfterAdd:true}"
	navigatorEditOptions="{height:300,width:400,reloadAfterSubmit:true,closeAfterEdit:true}"
	navigatorDeleteOptions="{reloadAfterSubmit:true}"
	navigatorSearchOptions="{reloadAfterSubmit:true,closeAfterSearch:true}"
	rowList="10,15,20,50,100" rowNum="10" multiselect="true"
	onSelectRowTopics="checkSelectedRow" onSelectAllTopics="checkAllSelectedRow"
	editurl="../others/goAjaxManagefeedback.action" sortable="true"
	viewrecords="true" width="1340" shrinkToFit="true">
	<sjg:gridColumn name="id" index="id" title="id" hidden="true" />
	<sjg:gridColumn name="title" index="title" title="Summay"
		editable="true" />
	<sjg:gridColumn name="description" index="description"
		edittype="textarea" editable="true" title="Description" />
	<sjg:gridColumn name="priority" index="priority" search="true"
		title="Priority" sortable="true" editable="true" edittype="select"
		editoptions="{value:'%{priorityMap}' }" />
	<sjg:gridColumn name="reporteBy" index="reporteBy" title="Reported By"
		align="center" />
	<sjg:gridColumn name="updatedDate" index="updatedDate"
		title="Reported Date" align="center" />
	<sjg:gridColumn name="action" index="action" title="Action"
		hidden="true" align="center" />
	<s:if test="(#session['role'] != null && #session['role'] eq 'ADMIN')">
		<sjg:gridColumn name="status" index="status" search="true"
			title="Action" sortable="true" editable="true" edittype="select"
			editoptions="{value:'%{statusMap}' }" />

		<sjg:gridColumn name="comments" index="comments" title="Comments" edittype="textarea"
			editable="true" align="center" />
	</s:if>
	<s:else>
		<sjg:gridColumn name="status" index="status" title="Action"
			align="center" />
		<sjg:gridColumn name="comments" index="comments" title="Comments"
			align="center" />
	</s:else>


</sjg:grid>
