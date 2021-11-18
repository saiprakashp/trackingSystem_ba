<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
	<tr>
		<td align="center" style="color: #2779aa;"><s:text
				name="pts.netwok.codes" /></td>
		<td style="width: 50px;">&nbsp;</td>
		<td align="center" style="color: #2779aa;"><s:text
				name="pts.assigned.netwok.codes" /></td>
	</tr>
	<tr>
		<td><s:select list="networkCodesMap" name="networkCodeNames"
				theme="simple" multiple="true" cssClass="multiselect1"
				cssStyle="width:390px;height:200px;"></s:select></td>
		<td align="center" valign="middle" style="width: 50px;"><img
			onclick="addRight()" src="../images/arrow_right.png"> <br /> <img
			onclick="addAllRight()" src="../images/arrow_right_all.png"> <br />
			<img onclick="addLeft()" src="../images/arrow_left.png"> <br />
			<img onclick="addAllLeft()" src="../images/arrow_left_all.png"></td>
		<td><s:select list="selectedNetworkCodesMap"
				name="selectedNetworkCodeNames" theme="simple" multiple="true"
				cssClass="multiselect2" cssStyle="width:390px;height:200px;"></s:select></td>
	</tr>
</table>
