<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<sjg:grid id="essFeedModal" href="../timesheet/showEssData.action"
	dataType="json" pager="true" toppager="true" pagerPosition="center"
	gridModel="essFeedModal" rowList="20,50,100,150,200"
	rowNum="%{#session.rowNum == null? 20 : #session.rowNum}"
	sortable="true" viewrecords="true" width="975" shrinkToFit="true"
	formIds="essFeedModalFormId">
	
	<sjg:gridColumn name="weekEndingDate" index="weekEndingDate"
		title="Weekend" sortable="false" align="center" />
	<sjg:gridColumn name="signum" index="signum" title="Signum"
		sortable="false" align="left" />
	<sjg:gridColumn name="year" index="year" title="Year" sortable="false"
		align="left" />
	<sjg:gridColumn name="month" index="month" title="Month"
		sortable="false" align="left" />
	<sjg:gridColumn name="chargedHrs" index="chargedHrs" title="Ess  Hours"
		sortable="false" align="center" />
	<sjg:gridColumn name="targetHrs" index="targetHrs"
		title="Ess Target Hours" sortable="false" align="center" />
</sjg:grid>
