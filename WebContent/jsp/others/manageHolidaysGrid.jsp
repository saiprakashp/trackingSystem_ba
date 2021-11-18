<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="gridmultitable" href="../others/ajaxManageHolidays.action"
	dataType="json" pager="true" toppager="true" pagerPosition="center"
	gridModel="holidayList" navigator="true" navigatorEdit="false"
	navigatorAdd="false" navigatorDelete="false" navigatorSearch="false"
	navigatorDeleteOptions="{reloadAfterSubmit:true,closeAfterDelete:true}"
	navigatorRefresh="false" navigatorCloneToTop="false" editinline="true"
	rowList="20,50,100,150,200" width="986" height="400"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	multiselect="true" editurl="../others/manageHolidays.action"
	sortable="true" viewrecords="true" autowidth="false" shrinkToFit="true"
	formIds="holidaysFormId"
	cssStyle="font-style:normal;font-family:Arial, Helvetica, San-Serif;font-weight:400;">

	<sjg:gridColumn name="id" index="id" title="AnnouncementId"
		hidden="true" />
	<sjg:gridColumn name="date" index="date" search="false" title="Date"
		sortable="true" editable="true" />
	<sjg:gridColumn name="locationName" index="locationName" search="true	"
		title="Location Name" sortable="true" editable="true" />
	<sjg:gridColumn name="holiday" index="holiday" search="false"
		title="Holiday Name" sortable="true" editable="true" />
	<sjg:gridColumn name="country" index="country" search="false"
		title="Country Name" sortable="true" editable="true" />
</sjg:grid>