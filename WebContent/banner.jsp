<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container-fluid">
		<nav class="navbar navbar-expand-lg navbar-light">
			<div class="col-sm-2">
				<div class="navbar-header navbar-right">
					<a class="navbar-brand" href="#"> <img id="logo"
						src="<%=request.getContextPath()%>/images/logo.gif">
					</a>
				</div>
			</div>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="col-sm-4" style='paddingvleft:2%;'>
				<div class="collapse navbar-collapse" id="navbarNavDropdown">
					<span class="navbar-text navTitle text-primary"> <s:text
							name="pts.project.titte" />
					</span>
				</div>
			</div>
		</nav>
	</div>