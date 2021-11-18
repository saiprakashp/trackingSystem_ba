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
	background-color: #f44336;
	a color: white;
	opacity: 0.83;
	transition: opacity 0.6s;
	margin-bottom: 15px;
}

.button {
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 7px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
}

.button2 {
	background-color: #008CBA;
} /* Blue */
.button3 {
	background-color: #f44336;
} /* Red */
.button4 {
	background-color: #e7e7e7;
	color: black;
} /* Gray */
.button5 {
	background-color: #555555;
} /* Black */
</style>
<script>
	$(document).ready(function() {
		$('#feedbackform').DataTable();
	});
	function saveFeedBack(ref) {
		$("#alert").css("display", "none");
		var id = -1;

		if (ref != null) {
			id = ref.id.split('_')[1];
		}
		if (id != null) {
			var current = id.split("_")[2];
			var obj = {
				'title' : $("#title_" + id).val(),
				'description' : $("#summary_" + id).val(),
				'priority' : $("#priority_" + id).val(),
				'reporter' : $("#reporter_" + id).val()
			};
			var jqxhr = $.post(
					'../others/savefeedback.action',
					(obj),
					function(res) {
						$("#alert").attr("class", "alert success ").css(
								"text-align", "center").css("display", "block")
								.text("Success");
						location.reload(); 
					}).fail(
					function(err) {
						$("#alert").attr("class", "alert").css("text-align",
								"center").css("display", "block").html(
								"Success");
					});

		}
		return false;

	}

	$(document)
			.ready(
					function() {
						$('#addRow')
								.click(
										function() {
											var len = $("#totalCount").val();
											len += 1;
											$("#totalCount").val(len);
											var t = $('#feedbackform')
													.DataTable();
											t.row
													.add(
															[
																	'<input name="title" id="title_'+len+'" class="title" type="text" value="" placeholder="Summary" required/>',
																	'<textarea  name="summary" id="summary_'+len+'" class="summary" type="text" value="" placeholder="Description" required rows="4" cols="50" required/>',
																	'<select size="1" id="priority_'+len+'" name="priority">'
																			+ '<option value="1">1</option>'
																			+ '<option value="2">2</option>'
																			+ '<option value="3">3</option>'
																			+ '<option value="4">4</option>'
																			+ '</select>',
																	'<input name="reporter" id="reporter_'+len+'" class="reporter" type="reporter" value="" placeholder="Reporter By" required/>',
																	'<a onclick="saveFeedBack(this)" href="#" id="button_'
																			+ len
																			+ '">save</a>' ])
													.draw(false);

										});
					});
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
					<table id="example" class="display" width="100%"></table>
					<a id="addRow" class="button button2">Add New FeedBack</a>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div style="width: 100%; height: auto;">
					<input type="hidden" id="totalCount"
						value="<s:property value='total'/>" />
					<table id="feedbackform" class="display" style="width: 100%">
						<thead>
							<tr>
								<th>Summary</th>
								<th>Description</th>
								<th>Priority</th>
								<th>Reported By</th>
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
									<td></td>
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