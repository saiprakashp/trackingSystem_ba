<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%
	request.setAttribute("decorator", "none");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<table style="width: 100%;" cellspacing="10">

	<tr>
		<td align="center" style="color: #2779aa;"><s:text
				name="pts.accounts" /></td>
		<td style="width: 50px;">&nbsp;</td>
		<td align="center" style="color: #2779aa;"><s:text
				name="pts.assigned.accounts" /></td>
	</tr>
	<tr>
		<td align="center"><s:hidden theme="simple"
				name="hiddenSupervisorId" id="hiddenSupervisorId" /> <s:select
				list="accountMap" name="accountNames" theme="simple" multiple="true"
				cssClass="multiselect1" cssStyle="width:350px;height:300px;"></s:select></td>
		<td align="center" valign="middle" style="width: 100px;"><img
			onclick="addRight()" src="../images/arrow_right.png"> <br /> <img
			onclick="addAllRight()" src="../images/arrow_right_all.png"> <br />
			<img onclick="addLeft()" src="../images/arrow_left.png"> <br />
			<img onclick="addAllLeft()" src="../images/arrow_left_all.png"></td>
		<td align="center"><s:select list="selectedAccountMap"
				name="selectedAccountIds" theme="simple" multiple="true"
				cssClass="multiselect2" cssStyle="width:350px;height:300px;"></s:select></td>
	</tr>
</table>