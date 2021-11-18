<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<sjg:grid id="gridmultitable" href="../others/goAjaxGetfeedback.action"
	dataType="json" pager="true" toppager="true" pagerPosition="center"
	gridModel="feedbackList" navigator="true" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="true" navigatorSearch="false"
	navigatorDeleteOptions="{reloadAfterSubmit:true,closeAfterDelete:true}"
	navigatorRefresh="false" navigatorCloneToTop="true"
	rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	multiselect="true" editurl="../others/goUpdateFeedBackStatus.action"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	onSelectRowTopics="ncrowselect" formIds="manageNetworkCodeFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">
	<sjg:gridColumn name="id" index="id" title="Id" hidden="true"
		editable="true" />

	<sjg:gridColumn name="title" index="Title" title="title"
		editable="true" />



</sjg:grid>

