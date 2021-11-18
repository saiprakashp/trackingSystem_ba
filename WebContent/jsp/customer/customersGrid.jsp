<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../customer/ajaxManageCustomers.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="customerGridModel" navigator="true"
	navigatorAddOptions="{height:300,width:400,reloadAfterSubmit:true,closeAfterAdd:true}"
	navigatorEditOptions="{height:300,width:400,reloadAfterSubmit:true,closeAfterEdit:true}"
	navigatorDeleteOptions="{reloadAfterSubmit:true}"
	navigatorSearchOptions="{reloadAfterSubmit:true,closeAfterSearch:true}"
	rowList="10,15,20" rowNum="10" multiselect="true"
	editurl="../customer/customerMACDOperation.action" sortable="true"
	viewrecords="true" width="975" shrinkToFit="true">
	<sjg:gridColumn name="id" index="id" title="Id" hidden="true"
		editable="true" />
	<sjg:gridColumn name="customerName" index="customerName" search="true"
		title="Account" sortable="true" editable="true"
		editrules="{required: true}" />
	<sjg:gridColumn name="createdBy" index="createdBy" search="false"
		title="Created By" sortable="true" />
	<sjg:gridColumn name="createdBy" index="createdBy" search="false"
		title="Created By" hidden="true" editable="true" />
	<sjg:gridColumn name="createdDate" index="createdDate" search="false"
		title="Created Date" hidden="true" editable="true" />
	<sjg:gridColumn name="formattedCreatedDate"
		index="formattedCreatedDate" search="false" title="Created Date"
		sortable="true" />
	<sjg:gridColumn name="updatedBy" index="updatedBy" search="false"
		title="Updated By" sortable="true" />
	<sjg:gridColumn name="formattedUpdatedDate"
		index="formattedUpdatedDate" search="false" title="Updated Date"
		sortable="true" />
	<sjg:gridColumn name="description" index="description" search="false"
		title="Description" editable="true" edittype="textarea" />
</sjg:grid>
