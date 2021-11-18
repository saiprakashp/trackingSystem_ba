<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
.footer {
	position: relative;
	left: 0;
	bottom: 0;
	width: 100%;
	background: #000000;
	color: white;
	text-align: center;
	display: grid;
	grid-template-columns: auto auto auto auto auto;
}

.grid-item {
	border: 1px solid rgba(0, 0, 0, 0.8);
	padding: 20px;
	text-align: center;
}
</style>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>
<%--
<div class="footer">
	<div class="grid-item">
		<img src="<%=request.getContextPath()%>/images/elogo.jpg"
			style="height: 36px;">
	</div>
	<div class="grid-item"></div>
	<div class="grid-item"></div>
	<div class="grid-item"></div>
	<div class="grid-item"></div>

</div>
 --%>