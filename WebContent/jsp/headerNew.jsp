<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet" />

<div class="d-flex  justify-content-between"
	style="height: 82px !important; background: #f2f2f2;">
	<div class="p-2 bd-highlight ">
		<a class="" href="/pts" style="text-decoration: none;"> <img
			src="<%=request.getContextPath()%>/images/logo.png"
			style="width: 120px;" class="rounded" alt="Ericsson" /> <b
			class="projectTitle"> <s:text name="pts.project.title" />
		</b></a>
	</div>
	<div class="p-2 bd-highlight ">


		<div class="d-flex float-start justify-content-between">
			<div class="p-2 bd-highlight text-bolder ">
				<span><s:text name="pts.networkcode.account.names" /> :</span>
				<s:select list="%{#session.customerContracts}" listKey="key"
					name="%{#session.selectedAccountId}" id="contractDetails"
					listValue="value"></s:select>
			</div>
			<div class="p-2 bd-highlight ">
				<span> <s:property value="%{#session.fullName}" /></span> <br /> <a
					href="../login/logout.action"><span class="text-black">
						<s:text name="pts.logout" />
				</span></a>
			</div>
		</div>
		<div class="d-flex  float-end justify-content-between">
			<div class="p-2 bd-highlight ">
				<img src="../usersImage/user1.png" height="29" width="47" />
			</div>
		</div>
	</div>
</div>