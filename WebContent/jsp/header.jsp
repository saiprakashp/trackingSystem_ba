<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


<style>
#customerSelector {
	text-align: justify;
	max-width: 368px;
	width: 235px !important;
	height: 66px;
	border: 1px solid darkgray;
	margin: auto;
	width: 183px;
	height: 66px;
	border: 1px solid darkgray;
	height: 66px;
	border: 1px solid darkgray;
}

#contentarea {
	text-align: 0;
	clear: both;
	width: 990px;
	background: white;
	margin: 0 auto;
	text-align: left;
	border-radius: 5px;
	margin: 0 auto;
}

#custonerName {
	color: blue
}

.ui-button.ui-corner-all.ui-widget.ui-button-icon-only.ui-dialog-titlebar-close
	{
	display: none;
}
</style>
<script type="text/javascript">
	function setBlur() {
		$('#breadcrumbDivParent').css("filter", "blur(3px)");
		$('#contentarea').css("filter", "blur(3px)");
	}

	function okButton() {

		$("#apploader").addClass("loader");

		var custName = document.getElementById("contractDetails").value;
		if (custName != "-1") {
			$.post("../login/ajaxSetCustomer.action", {
				custometId : custName
			}, function(data, status) {
				console.log(data);
				$('#customerDialog').dialog('close');
				$("#apploader").removeClass("loader");
				$("#custonerName").html(
						$("#contractDetails option:selected").text());

				$('#breadcrumbDivParent').css("filter", "blur(0px)");
				$('#contentarea').css("filter", "blur(0px)");
			});

			setTimeout(function() {
				$('#customerDialog').dialog('close')
				$("#apploader").removeClass("loader");

				$("#custonerName").html(
						$("#contractDetails option:selected").text());
				$('#breadcrumbDivParent').css("filter", "blur(0px)");
				$('#contentarea').css("filter", "blur(0px)");
			}, 8000);
		} else {
			$('#customerDialog').dialog('close')
			$("#apploader").removeClass("loader");
			$("#custonerName").html(
					$("#contractDetails option:selected").text());
			$('#breadcrumbDivParent').css("filter", "blur(0px)");
			$('#contentarea').css("filter", "blur(0px)");
		}
	};

	function cancelButton() {
		$('#breadcrumbDivParent').css("filter", "blur(0px)");
		$('#contentarea').css("filter", "blur(0px)");
		$('#customerDialog').dialog('close');
	};
</script>

<div style="width: 1000px; margin: 0 auto; text-align: left;">
	<table style="width: 100%;">
		<tr>
			<td width="36px;" valign="bottom"><img src="../images/logo.png"
				style="height: 36px;"></td>
			<td valign="bottom"><div class="projectTitle">
					<s:text name="pts.project.title" />
				</div></td>
			<td align="right" valign="top">
				<table>
					<tr>
						<td align="right"><s:if
								test="%{#session.customerContracts neq null}">
								<table>
									<tr>
										<td valign="bottom" align="right" nowrap="nowrap"><s:text
												name="pts.networkcode.account.names" />: <s:select
												list="%{#session.customerContracts}" listKey="key"
												name="%{#session.selectedAccountId}" id="contractDetails"
												listValue="value"></s:select></td>
										<td valign="bottom" align="right"><s:property
												value="%{#session.fullName}" /> <br /> <a
											href="../login/logout.action"><s:text name="pts.logout" />
												<!-- <img
											src="../images/logout.png" align="middle" /> --></a></td>
										<td><img src="../usersImage/user1.png" height="29"
											width="47"></td>
									</tr>
								</table>
							</s:if> <s:else>
								<%
									response.sendRedirect("/pts/login/login.action");
								%>
							</s:else></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
