<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    request.setAttribute("decorator", "none");
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
    response.setHeader("Pragma","no-cache"); //HTTP 1.0
    response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
	<s:div cssStyle="margin: 0px auto;" id="errorDiv" theme="simple">
	<s:if test="capacityStreamImage eq ''">
	
		<table style="margin: 0 auto;">
			<tr>
				<td align="center"><s:fielderror theme="simple"
						cssStyle="margin-left:10px;color: RED" /> <s:actionerror
						theme="simple" cssStyle="margin-left:10px;color: RED" /></td>
			</tr>
		</table>
		</s:if>
	</s:div>
<s:if test="capacityStreamImage != ''">
<div style="overflow:auto";>
	<img id="capacityimageid"
		src="/capacity/<s:property value='capacityStreamImage'/>">
		</div>
</s:if>