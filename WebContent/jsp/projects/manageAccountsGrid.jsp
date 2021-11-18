<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable"
	href="../announcements/ajaxManageAnnouoncements.action" dataType="json"
	pager="true" toppager="true" pagerPosition="center"
	gridModel="gridModel" navigator="true" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="true" navigatorSearch="false"
	navigatorDeleteOptions="{reloadAfterSubmit:true,closeAfterDelete:true}"
	navigatorRefresh="false" navigatorCloneToTop="true"
	navigatorExtraButtons="{
			edit : {
				title : 'Edit Announcement',
				icon: 'ui-icon-pencil',
				position:'first',
				onclick: function(){ macdUser('editaccounts'); }
			},
			add : {
				title : 'Create Announcement',
				icon: 'ui-icon-plus',
				position:'first',
				onclick: function(){ macdUser('addaccounts'); }
			}
			
		}"
	rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	multiselect="true" editurl="../projects/manageAccounts.action"
	sortable="true" viewrecords="true" autowidth="true" shrinkToFit="true"
	onSelectRowTopics="announcementrowselect" formIds="announcementsFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">
	
	<sjg:gridColumn name="id" index="id" title="AnnouncementId"
		hidden="true" />
	<sjg:gridColumn name="announcementType" index="announcementType"
		title="Announcement Type" sortable="true" />
	<sjg:gridColumn name="subject" index="subject" title="Subject"
		sortable="true" editable="true" width="300" />
	
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