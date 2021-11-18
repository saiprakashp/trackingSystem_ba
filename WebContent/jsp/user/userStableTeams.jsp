<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%
	request.setAttribute("decorator", "none");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<style>
</style>
<table style="width: 100%;" cellspacing="10">
	<tr>
		<th>Please Specify Contribution in %</th>
	</tr>
	<tr>
		<td>
			<table cellspacing="10" style="width: 100%;" border="0">
				<s:iterator value="stableTeams" status="contr" var="stableTeams">
					<tr>
						<td><s:hidden name="%{id}" value="%{id}" id="stable_%{id}" />
							<s:hidden
								name="%{'userAPACToSave[0].stableTeams['+#contr.index+'].teamName'}"
								value="%{teamName}" id="teamName_%{id}" /></td>
						<td><s:textfield
								name="%{'userAPACToSave[0].stableTeams['+#contr.index+'].teamName'}"
								value="%{teamName}" disabled="true" id="teamName_%{id}"
								style="background: transparent;border: none;color: #2779aa;
							font-weight: bold;" /></td>
						<td><s:textfield appId="%{id}"
								name="%{'userAPACToSave[0].stableTeams['+#contr.index+'].value'}"
								value="%{value}" onchange="setStableTeamValues(event)"
								id="stableId" /></td>
					</tr>
				</s:iterator>
				<tr>
					<td>
						<p></p>
					</td>
					<td>
						<p
							style="background: transparent; border: none; color: #2779aa; font-weight: bold;">
							<b>Total:</b>
						</p>
					</td>
					<td><p style="font-weight: bolder;" id="stableTeamTotal"></p></td>

				</tr>


			</table>
		</td>


	</tr>
</table>
