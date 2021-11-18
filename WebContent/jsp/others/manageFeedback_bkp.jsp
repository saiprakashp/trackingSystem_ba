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

<script src="<%=request.getContextPath()%>/js/jquery.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/datatables.min.css" />
<script src="<%=request.getContextPath()%>/js/datatables.min.js"
	type="text/javascript"></script>


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
	background-color: #f44336;a
	color: white;
	opacity: 0.83;
	transition: opacity 0.6s;
	margin-bottom: 15px;
}
</style>
<script>
	$(document).ready(function() {
		$('#feedbackform').DataTable();
	});
	function saveFeedBack(event) {
		$("#alert").css("display", "none");
		if (event.id != null) {
			var current = event.id.split("_")[2];
			var obj = {
				'feedId' : -1,
				'comments' : '',
				'status' : ''
			};
			$("#feedback_tr_" + current + " > td >").each(function() {
				if (this.id == 'feedid')
					obj["feedId"] = this.value;
				if (this.id == 'comments')
					obj["comments"] = this.value;
				if (this.id == 'status') {
					obj["status"] = this.value;
				}

			})
			var jqxhr = $.post(
					'../others/goUpdateFeedBackStatus.action',
					(obj),
					function(res) {
						$("#alert").attr("class", "alert success ").css(
								"text-align", "center").css("display", "block")
								.text("Success");
					}).fail(
					function(err) {
						$("#alert").attr("class", "alert").css("text-align",
								"center").css("display", "block").html(
								"Success");
					});

		}
		return false;

	}
</script>
</head>
<body>
	<s:form theme="simple" method="POST" id="savefeedback"
		name="savefeedback">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:text name="pts.menu.home" />
					>
					<s:text name="pts.menu.resource.feedback" />
				</div>
			</div>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">&nbsp;</s:div>
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

				<div class="alert" style="display: none" id="alert"></div>

				<div id="spacer10px">&nbsp;</div>
				<div style="width: 100%; height: auto;">
					<table id="feedbackform" class="display" style="width: 100%">
						<thead>
							<tr>
								<th>Summary</th>
								<th>Description</th>
								<th>Priority</th>
								<th>Reported By</th>
								<th>Reported Date</th>
								<th>Action</th>
								<th>Comment</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="feedbackList" status="actstat">
								<tr id='feedback_tr_<s:property value="%{#actstat.index}"/>'>
									<td><input type="hidden" value='<s:property value="id"/>'
										id='feedid' /> <s:property value="title" /></td>
									<td><s:property value="description" /></td>
									<td><s:property value="priority" /></td>
									<td><s:property value="reporteBy" /></td>
									<td><s:property value="updatedDate" /></td>
									<s:if
										test="#session['role'] != null && (#session['role'] eq 'ADMIN'  )">
										<td><select size="1" id="status" name="status">
												<option value="Pending">Pending</option>
												<option value="Accepted">Accepted</option>
												<option value="Completed">Completed</option>
												<option value="Rejected">Rejected</option>
										</select></td>

										<td><s:textfield id="comments" name="comments"></s:textfield></td>
										<td><a onclick="saveFeedBack(this)" href="#"
											id='button_tr_<s:property value="%{#actstat.index}"/>'>save</a></td>
									</s:if>
									<s:else>
										<td><s:property value="status" /></td>
										<td><s:property value="comments" /></td>
										<td></td>
									</s:else>
								</tr>
							</s:iterator>

						</tbody>
						<tfoot>
							<tr>

							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>