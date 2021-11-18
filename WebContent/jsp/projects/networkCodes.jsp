<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<sj:autocompleter list="releasesMapObj" name="selectedNetworkCodeId"
	theme="simple" id="networkCodesListId"
	onSelectTopics="/autocompleteSelect" selectBox="true"
	selectBoxIcon="true"></sj:autocompleter>