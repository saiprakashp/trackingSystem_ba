<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<sj:head jqueryui="true" ajaxcache="false" compressed="false"
	jquerytheme="cupertino" />
<title><s:text name="pts.project.title" /></title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/pts.css" />
<script src="<%=request.getContextPath()%>/js/pts.js"
	type="text/javascript"></script>
<style type="text/css">
.alert.success {
	background-color: #4CAF50;
}

.alert.warning {
	background-color: #ff9800;
}

.alert.info {
	background-color: #2196F3;
}

.alert {
	padding: 20px;
	background-color: #f44336;
	a color: white;
	opacity: 0.83;
	transition: opacity 0.6s;
	margin-bottom: 15px;
}

#contentarea {
	text-align: 0;
	clear: both;
	width: 1400px !important;
	background: white;
	margin: 0 auto;
	text-align: left;
	border-radius: 5px;
	margin: 0 auto;
}

#description {
	width: 293px !important;
	height: 140px !important;
}

#TblGrid_feedbackList {
	height: 300px !important;
}

#editmodfeedbackList {
	height: 400px !important;
}

#title {
	width: 293px !important;
}

#title {
	width: 293px !important;
}

#comments {
	width: 291px !important;
	height: 67px !important;
}
</style>
<script>
	var grid = $("#gbox_feedbackList");
	$.subscribe('checkSelectedRow', function(event, data) {
		var user = document.getElementById("userRole").value;
		var allowed = false;
		if ('ADMIN' !== user) {
			
			if (event.originalEvent.id != null) {
				$('#' + event.originalEvent.id + ' > td').each(function() {
					if (this.title != null && user === this.title) {
						allowed = true;
					}
				})
			}
			if ('ADMIN' !== user) {	if (!allowed) {
				$("#feedbackList").jqGrid("resetSelection");
			}
		}
		if(!allowed){
			 alert('Please select entries that are created by you only')
		 }}
	})

	
	$.subscribe('checkAllSelectedRow', function(event, data) {
		var user = document.getElementById("userRole").value;
		var allowed = false;
		if ('ADMIN' !== user) {
			$('#feedbackList > tbody > tr').each(function(){
				 var user=	document.getElementById("userNameHid");
				 var counter=0;
				 $('td', this).each(function() {
				 if( $(this).attr('aria-describedby')=='feedbackList_reporteBy')
					 if (this.title != null && user === this.title) {
							allowed = true;
						}
				
				 });
				
				 })   
			
			
				if ('ADMIN' !== user) {
			 if(!allowed){
				 alert('Please select entries that are created by you only')
			 }
			
			if (!allowed) {
				$("#feedbackList").jqGrid("resetSelection");
			}}
		}
		  		
	})
	function prevliadateData() {
 

		document.forms[0].method = "post";
		document.forms[0].action = '../others/goGetfeedback.action';
	 	document.forms[0].submit();
		return true;

	}

	function submitData(s) {

		document.forms[0].method = "post";
		document.forms[0].action = s;
		document.forms[0].submit();
	}
</script>
</head>
<body>


	<s:form theme="simple" method="POST" id="savefeedback"
		onsubmit="return prevliadateData()" name="savefeedback">
		<s:hidden id="userNameHid" value="%{#session.fullName}"></s:hidden>
		<s:hidden id="userRole" value="%{#session.role}"></s:hidden>
		<s:hidden id="showAllFeedBacks" name="showAllFeedBacks" value="TRUE"></s:hidden>
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1400px; background: white; height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:text name="pts.menu.home" />
					> Manage FeedBack
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 1300px !important;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
					 FeedBack Form
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div style="width: 100%;">
					<s:div cssStyle="margin: 0px auto;" id="errorDiv" theme="simple">
						<table style="margin: 0 auto;">
							<tr>
								<td align="center">
									<p id="errorData" style="margin-left: 10px; color: RED" />
								</td>
							</tr>
						</table>
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text name="pts.resource.name" />
								:</td>
							<td align="left" width="200"><s:property
									value="#session['fullName']" /> <s:hidden
									name="selectedEmployee" id="selectedEmployee" /></td>
						<%-- 	<s:if test="(#session['role'] eq 'LINE MANAGER') ">
								<td align="left"><s:checkbox name="showAllFeedBacks1"
										id="showAllFeedBacks1">Show All FeedBacks</s:checkbox></td>
								<td style="padding-right: 5px; text-align: right;" id="saveid"><s:submit
										src="../images/go.png" type="image" value="Go" /></td>
							</s:if> --%>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>

				<div class="alert" style="display: none" id="alert"></div>

				<div id="spacer10px">&nbsp;</div>
				<div style="width: 100%; height: auto;">
					<jsp:include page="manageFeedbackGrid.jsp"></jsp:include>
				</div>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>