<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%
	request.setAttribute("decorator", "none");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<div style="overflow-y: auto; background-color: #FFF;">
	<table style="width: 100%;" id="userContribution">
		<tr>
			<td><sj:accordion id="accordion" heightStyle="content"
					animate="true" cssStyle="width:80%;" collapsible="true">
					<s:iterator value="userAcctPillarAppContribution"
						status="acctcontr">
						<s:hidden
							name="%{'userAPACToSave['+#acctcontr.index+'].accountId'}"
							id="%{'userAPACToSave['+#acctcontr.index+'].accountId'}"
							value="%{userAcctPillarAppContribution[#acctcontr.index].accountId}"
							theme="simple" />

						<s:hidden
							name="%{'userAPACToSave['+#acctcontr.index+'].accountName'}"
							id="%{'userAPACToSave['+#acctcontr.index+'].accountName'}"
							value="%{userAcctPillarAppContribution[#acctcontr.index].accountName}"
							theme="simple" />

						<sj:accordionItem
							title="%{userAcctPillarAppContribution[#acctcontr.index].accountName}"
							cssStyle="width: 100%;background-color: #FFF;">
							<sj:div id="divInAccrodionItem%{#acctcontr.index}"
								csstyle="width: 100%;background-color: #FFF;">
								<table cellspacing="10" style="width: 100%;" border="0">
									<s:iterator value="%{userPACList}" status="contr">
										<tr>
											<td align="right" nowrap="nowrap" class="ptslabel"><s:hidden
													name="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].pillarId'}"
													id="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].pillarId'}"
													value="%{userAcctPillarAppContribution[#acctcontr.index].userPACList[#contr.index].pillarId}"
													theme="simple" /> <s:hidden
													name="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].pillarContribution'}"
													id="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].pillarContribution'}"
													value="%{userAcctPillarAppContribution[#acctcontr.index].userPACList[#contr.index].pillarContribution}"
													theme="simple" /> <s:hidden
													name="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].pillarName'}"
													id="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].pillarName'}"
													value="%{userAcctPillarAppContribution[#acctcontr.index].userPACList[#contr.index].pillarName}"
													theme="simple" /> <s:property
													value="%{userAcctPillarAppContribution[#acctcontr.index].userPACList[#contr.index].pillarName}" />

											</td>
											<s:iterator value="%{userACList}" status="appcontr">
												<td nowrap="nowrap"><s:hidden
														name="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].userACList['+#appcontr.index+'].appId'}"
														id="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].userACList['+#appcontr.index+'].appId'}"
														value="%{userAcctPillarAppContribution[#acctcontr.index].userPACList[#contr.index].userACList[#appcontr.index].appId}"
														theme="simple" /> <s:hidden
														name="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].userACList['+#appcontr.index+'].appName'}"
														id="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].userACList['+#appcontr.index+'].appName'}"
														value="%{userAcctPillarAppContribution[#acctcontr.index].userPACList[#contr.index].userACList[#appcontr.index].appName}"
														theme="simple" />

													<div style="float: left;">
														<label style="display: block;"><s:property
																value="%{userAcctPillarAppContribution[#acctcontr.index].userPACList[#contr.index].userACList[#appcontr.index].appName}" /></label>

														<s:if
															test="%{userAPACToSave[#acctcontr.index].userPACList[#contr.index].userACList[#appcontr.index].appContribution > 0}">
															<s:textfield theme="simple" cssClass="contributionList"
																name="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].userACList['+#appcontr.index+'].appContribution'}"
																id="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].userACList['+#appcontr.index+'].appContribution'}"
																onchange='showStableTeams(event)'
																value="%{userAPACToSave[#acctcontr.index].userPACList[#contr.index].userACList[#appcontr.index].appContribution}"
																style="width:50px;" />
														</s:if>
														<s:else>
															<s:textfield theme="simple" cssClass="contributionList1"
																appType="%{userAcctPillarAppContribution[#acctcontr.index].userPACList[#contr.index].userACList[#appcontr.index].appName}"
																name="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].userACList['+#appcontr.index+'].appContribution'}"
																id="%{'userAPACToSave['+#acctcontr.index+'].userPACList['+#contr.index+'].userACList['+#appcontr.index+'].appContribution'}"
																onchange='showStableTeams(event)'
																value="%{userAcctPillarAppContribution[#acctcontr.index].userPACList[#contr.index].userACList[#appcontr.index].appContribution}"
																style="width:50px;" />
														</s:else>
													</div></td>
											</s:iterator>
										</tr>

									</s:iterator>
								</table>
							</sj:div>
						</sj:accordionItem>
					</s:iterator>
				</sj:accordion></td>
		</tr>
	</table>
</div>
