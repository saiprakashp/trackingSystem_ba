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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/styles/pts.css" />
<script src="<%=request.getContextPath()%>/js/pts.js"
	type="text/javascript"></script>
<script type="text/javascript">
	datePick = function(element) {
		$(element).datepicker();
	};
	$.subscribe('/supervisorSelect', function(event, data) {
		setTimeout("getPillarsMap()", 10);
		setTimeout("getProjectsMap()", 100);
		//setTimeout("getReleaseMap()", 100);
	});
	$.subscribe('/pillarSelect', function(event, data) {
		setTimeout("getProjectsMap()", 10);
		//setTimeout("getReleaseMap()", 100);
	});
	$.subscribe('/projectSelect', function(event, data) {
		//setTimeout("getReleaseMap()", 10);
	});
	$.subscribe('releaseTrainComp', function(event, data) {
		$("div[id*='_gridmultitable_feasibility']").html(
				'Feasibility (' + feasibilityIndex + ')');
		$("div[id*='_gridmultitable_development']").html(
				'Development (' + developmentIndex + ')');
		$("div[id*='_gridmultitable_systemTest']").html(
				'System Test (' + systemTestIndex + ')');
		$("div[id*='_gridmultitable_deployment']").html(
				'Deployment (' + deploymentIndex + ')');
		$("div[id*='_gridmultitable_hold']").html('Hold (' + holdIndex + ')');
	});

	var feasibilityIndex = 0;
	var developmentIndex = 0;
	var systemTestIndex = 0;
	var deploymentIndex = 0;
	var holdIndex = 0;

	function feasibilityFormatter(cellvalue, options, row) {
		if (cellvalue != '' && cellvalue != null) {
			++feasibilityIndex;
			return nameFormatter(cellvalue, options, row)
		} else
			return "";
	}
	function developmentFormatter(cellvalue, options, row) {
		if (cellvalue != '' && cellvalue != null) {
			++developmentIndex;
			return nameFormatter(cellvalue, options, row)
		} else
			return "";
	}
	function systemTestFormatter(cellvalue, options, row) {
		if (cellvalue != '' && cellvalue != null) {
			++systemTestIndex;
			return nameFormatter(cellvalue, options, row)
		} else
			return "";
	}
	function deploymentFormatter(cellvalue, options, row) {
		if (cellvalue != '' && cellvalue != null) {
			++deploymentIndex;
			return nameFormatter(cellvalue, options, row)
		} else
			return "";
	}
	function holdFormatter(cellvalue, options, row) {
		if (cellvalue != '' && cellvalue != null) {
			++holdIndex;
			return nameFormatter(cellvalue, options, row)
		} else
			return "";
	}
	function nameFormatter(cellvalue, options, row) {
		if (cellvalue != '' && cellvalue != null) {

			var projectname = '';
			var strArr = cellvalue.split('|');
			var id = strArr[0];
			var projectStage = strArr[1];
			var networkCodeArr = strArr[2];
			var comment = '';
			var projCol = 0;
			var descriptionTxt = '';
			if (networkCodeArr != null && !/^\s*$/.test(networkCodeArr)) {
				var networkCodes = networkCodeArr.split('____')[0]
						.split('~~~~~');
				networkCode = networkCodes[0];
				if (networkCodes[1] != null) {
					comment = encodeURI(networkCodes[1].trim());
				}
				if (networkCodes[2] != null) {
					descriptionTxt = encodeURI(networkCodes[2].trim());
				}
				projCol = networkCodeArr.split('____')[1];
			}
			var fvo = '';
			var len = networkCode.length;
			if (len > 0) {
				var parsedDate = Date.parse(networkCode.substring((len - 10),
						len));
				if (parsedDate > 0) {
					fvo = networkCode.substring((len - 10), len);
				}
			}
			if (fvo == '') {
				fvo = networkCode.substring(networkCode.lastIndexOf(", ") + 1,
						len);
			}
			var tmpNetworkCode = networkCode.substring(0, networkCode
					.lastIndexOf(", "));
			if (projCol == 'green' || projCol == 'red') {
				return '<span class="cellWithoutBackground" style="background-color: '+projCol+';color:white;"><a href="javascript:void()" style="color:white;text-decoration:none;" onclick="OpenProjectPhaseChange(\''
						+ id
						+ '\' ,\''
						+ tmpNetworkCode
						+ '\' ,\''
						+ fvo
						+ '\' ,\''
						+ comment
						+ '\' ,\''
						+ descriptionTxt
						+ '\' ,\''
						+ projCol
						+ '\' ,\''
						+ projectStage
						+ '\')" style="text-decoration:none">'
						+ networkCode
						+ '</a></span>';
			} else if (projCol == 'orange' || projCol == 'yellow') {
				return '<span class="cellWithoutBackground" style="background-color: '+projCol+';color:white;"><a href="javascript:void()" onclick="OpenProjectPhaseChange(\''
						+ id
						+ '\' ,\''
						+ tmpNetworkCode
						+ '\' ,\''
						+ fvo
						+ '\' ,\''
						+ comment
						+ '\' ,\''
						+ descriptionTxt
						+ '\' ,\''
						+ projCol
						+ '\' ,\''
						+ projectStage
						+ '\')" style="text-decoration:none">'
						+ networkCode
						+ '</a></span>';
			} else {
				return '<a href="javascript:void()" onclick="OpenProjectPhaseChange(\''
						+ id
						+ '\' ,\''
						+ tmpNetworkCode
						+ '\' ,\''
						+ fvo
						+ '\' ,\''
						+ comment
						+ '\' ,\''
						+ descriptionTxt
						+ '\' ,\''
						+ projCol
						+ '\' ,\''
						+ projectStage
						+ '\')" style="text-decoration:none">'
						+ networkCode
						+ '</a>';
			}
		} else
			return "";
	}
	function OpenProjectPhaseChange(id, networkCode, fvo, comment,
			descriptionTxt, projCol, projectStage) {
		document.getElementById("networkCodeId").value = id;
		document.getElementById("networkCodeNameId").innerHTML = networkCode;
		document.getElementById("projectStageId").value = projectStage;
		document.getElementById("dialogProjectStageId").value = projectStage;
		document.getElementById("projCol").value = projCol;
		document.getElementById("selectedProjCol").value = projCol;
		comment = decodeURI(comment);

		var comments = comment.split(' ~~~ ');
		comment = '';
		if (comments != null && !/^\s*$/.test(comments)) {
			for (var i = 0; i < comments.length; i++) {
				comment += comments[i] + '\n';
			}
		}

		descriptionTxt = decodeURI(descriptionTxt);

		var description = descriptionTxt.split(' ~~~ ');
		descriptionTxt = '';
		if (description != null && !/^\s*$/.test(description)) {
			for (var i = 0; i < description.length; i++) {
				descriptionTxt += description[i] + '\n';
			}
		}

		$('#fvo').text(fvo);
		document.getElementById("fvo").value = fvo;
		document.getElementById("commentsvalTMP").value = comment;
		document.getElementById("descriptionTmp").value = descriptionTxt;
		document.getElementById("ProjectPhaseAId").click();
	}

	function closeModalAndReload() {
		submitForm('../reports/releaseTrainReport.action');
	}

	function okButton() {
		document.getElementById("projectStageId").value = document
				.getElementById("dialogProjectStageId").value;
		document.getElementById("projCol").value = document
				.getElementById("selectedProjCol").value;
		document.getElementById("comments").value = $('#commentsvalTMP').val();
		document.getElementById("description").value = $('#descriptionTmp')
				.val();
		submitForm('../reports/saveProjectReleasePhase.action')
	}
	function cancelButton() {
		$('#ProjectPhaseDialog').dialog('close');
	}
	function addCommentClick() {

		var d = new Date();
		var strDate = d.getFullYear() + ":" + (d.getMonth() + 1) + ":"
				+ d.getDate();
		if (/^\s*$/.test($('#commentsTemp').val())) {
			alert(' Please add Comments ');
		} else {
			if (!/^\s*$/.test($('#commentsvalTMP').val())) {

				if ($('#commentsvalTMP').val().indexOf(strDate) > 0
						|| $('#commentsvalTMP').val().substring(0, 9).trim() == (strDate)) {

					$('#commentsvalTMP').append(
							' ~~~ ' + $('#commentsTemp').val());
				} else {

					$('#commentsvalTMP').append(
							' ~~~ ' + strDate + ' ' + $('#commentsTemp').val());
				}
			} else {

				$('#commentsvalTMP').append(
						strDate + ' ' + $('#commentsTemp').val());
			}
		}
	}

	$.subscribe('gridComplete', function(event, data) {
	});
	var Path = '../images/';
	var ImgAry = new Array('', 'green.png', 'red.png', 'yellow.png',
			'orange.png');

	function addImages(obj, id) {
		var i = obj.selectedIndex;
		if (i < 1) {
			return;
		}
		document.getElementById(id).src = Path + ImgAry[i];
	}

	jQuery()
			.ready(
					function() {
						function change(text){
							console.log(text);
						}
						/* Custom select design */
						jQuery('.drop-down').append(
								'<div class="button"></div>');
						jQuery('.drop-down').append(
								'<ul class="select-list"></ul>');
						jQuery('.drop-down select option')
								.each(
										function() {
											var bg = jQuery(this).css(
													'background-image');
											jQuery('.select-list')
													.append(
															'<li class="clsAnchor"><span value="'
																	+ jQuery(
																			this)
																			.val()
																	+ '" class="'
																	+ jQuery(
																			this)
																			.attr(
																					'class')
																	+ '" style=background-image:'
																	+ bg
																	+ '>'
																	+ jQuery(
																			this)
																			.text()
																	+ '</span></li>');
										});
						jQuery('.drop-down .button')
								.html(
										'<span style=background-image:'
												+ jQuery('.drop-down select')
														.find(':selected')
														.css('background-image')
												+ '>'
												+ jQuery('.drop-down select')
														.find(':selected')
														.text()
												+ '</span>'
												+ '<a href="javascript:void(0);" style="float:right;color: black;text-decoration: none;" class="select-list-link">&#9660;</a>');
						jQuery('.drop-down ul li')
								.each(
										function() {
											if (jQuery(this).find('span')
													.text() == jQuery(
													'.drop-down select').find(
													':selected').text()) {
												jQuery(this).addClass('active');
											}
										});
						jQuery('.drop-down .select-list span')
								.on(
										'click',
										function() {
											var dd_text = jQuery(this).text();
											var dd_img = jQuery(this).css(
													'background-image');
											var dd_val = jQuery(this).attr(
													'value');
											jQuery('.drop-down .button')
													.html(
															'<span style=background-image:' + dd_img + '>'
																	+ dd_text
																	+ '</span>'
																	+ '<a href="javascript:change('+dd_text+');" style="float:right;color: black;text-decoration: none;" class="select-list-link">&#9660;</a>');
											jQuery(
													'.drop-down .select-list span')
													.parent().removeClass(
															'active');
											jQuery(this).parent().addClass(
													'active');
											$('.drop-down select[name=options]')
													.val(dd_val);
											$('.drop-down .select-list li')
													.slideUp();
										});
						jQuery('.drop-down .button').on('click',
								'a.select-list-link', function() {
									jQuery('.drop-down ul li').slideToggle();
								});
						/* End */
					});
</script>
<style type="text/css">
#addComment {
	background: #d7ebf9 url(images/ui-bg_glass_80_d7ebf9_1x400.png) 50% 50%
		repeat-x;
	font-weight: bold;
	color: #2779aa;
	width: 121px;
	height: 23px;
}
.button{
width:120px;
background: white;
}
.drop-down {
	position: relative;
	display: inline-block;
	width: auto;
	margin-top: 0;
	font-family: verdana;
}

.drop-down select {
	display: none;
}

.drop-down .select-list {
	position: absolute;
	top: 0;
	left: 0;
	z-index: 1;
	margin-top: 40px;
	padding: 0;
	background-color: #595959;
}

.drop-down .select-list li {
	display: none;
}

.drop-down .select-list li span {
	display: inline-block;
	min-height: 40px;
	min-width: 280px;
	width: 100%;
	padding: 5px 15px 5px 35px;
	background-color: #595959;
	background-position: left 10px center;
	background-repeat: no-repeat;
	font-size: 16px;
	text-align: left;
	color: #FFF;
	opacity: 0.7;
	box-sizing: border-box;
}

.drop-down .select-list li span:hover, .drop-down .select-list li span:focus
	{
	opacity: 1;
}
</style>
</head>
<body>
	<div class="drop-down">
		<select name="options">
			<option class="" value="">Please Select</option>
			<option class="red" value="red"
				style="background-image: url('../images/red.png');">Red</option>
			<option class="green" value="green"
				style="background-image: url('../images/green.png');">Green</option>
			<option class="orange" value="orange"
				style="background-image: url('../images/orange.png');">Amber</option>
		</select>
	</div>
	<s:form id="releaseTrainReportFormId" method="POST" theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<s:hidden name="id" id="networkCodeId" theme="simple" />
		<s:hidden name="projectStage" id="projectStageId" theme="simple" />
		<s:hidden name="comments" id="comments" theme="simple" />
		<s:hidden name="description" id="description" theme="simple" />
		<s:hidden name="projectColor" id="projCol" theme="simple" />
		<div id="breadcrumbDivParent"
			style="width: 100%; background: url('../images/header_bg_shadow.png') repeat-x; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.release.train.report" />
					Report
				</div>
			</div>
		</div>
		<div>
			<!-- description comments <sj:dialog id="ProjectPhaseDialog"
				href="../projects/goProjectPhaseEdit.action" title="Project Phase"
				autoOpen="false" formIds="releaseTrainReportFormId" />-->
			<sj:dialog id="ProjectPhaseDialog" width="600"
				buttons="{
        'Save':function() { okButton(); },
        'Cancel':function() { cancelButton(); }
    }"
				autoOpen="false" modal="false" title="Project Phase"
				cssStyle="display:none;">
				<table>
					<tr>
						<td style="color: #2779aa;" align="right"><s:text
								name="pts.netwok.code" />:</td>
						<td id="networkCodeNameId" colspan="3"><s:property
								value="networkCode" /></td>
					</tr>
					<tr>
						<td style="color: #2779aa;" align="right"><s:text
								name="Phase" />:</td>
						<td><s:select
								list="#{'Completed':'Completed','Deployment':'Deployment','Development':'Development','Feasibility':'Feasibility', 'Hold':'Hold','System Test':'System Test'}"
								name="dialogProjectStage" theme="simple" headerKey=""
								headerValue="None" id="dialogProjectStageId"></s:select>
						<td style="color: #2779aa;" align="right">FVO Date:</td>
						<td><span id="fvo"></span></td>
					</tr>
					<tr>
						<td>Flag:</td>
						<td><select id="selectedProjCol"
							onchange="addImages(this,'flags');"
							style="background-color: lightblue;">
								<option value=''>None</option>
								<option value='green'>Green</option>
								<option value='red' style="color: red;">Red</option>
								<option value='orange' style="color: orange;">Amber</option>
						</select></td>
					</tr>
					<tr>
						<td style="color: #2779aa;" align="right">Description:</td>
						<td colspan="3"><s:textarea style="width:400px;height:100px"
								name="descriptionTmp" id="descriptionTmp"></s:textarea></td>
					</tr>
					<tr>
						<td style="color: #2779aa;" align="right">Comments:</td>
						<td colspan="3"><s:textarea style="width:400px;height:100px"
								name="commentsvalTMP" id="commentsvalTMP" disabled="false"></s:textarea></td>
					</tr>
				</table>
			</sj:dialog>

			<sj:a openDialog="ProjectPhaseDialog" id="ProjectPhaseAId"
				cssStyle="cursor: pointer;text-decoration: underline;">
			</sj:a>
		</div>
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.release.train.report" /> Report
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text
									name="pts.user.supervisor" />:</td>
							<td width="200" nowrap="nowrap"><sj:autocompleter
									list="supervisorMapForManage" name="searchSupervisor"
									listKey="key" listValue="value" theme="simple"
									id="searchSupervisor" selectBox="true" selectBoxIcon="true"
									onSelectTopics="/supervisorSelect">
								</sj:autocompleter> <sj:a id="getPillarsHrefId"
									href="../utilization/getPillarsMap.action"
									targets="pillarsDivId" formIds="releaseTrainReportFormId"
									indicator="pillarLoadingIndicator" /><img
								id="pillarLoadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.networkcode.project.manager" />:
							</td>
							<td><sj:div id="projectManagersDivId">
									<sj:autocompleter list="projectManagersMapObj"
										name="searchProjectManager" listKey="key" listValue="value"
										theme="simple" id="searchProjectManager" selectBox="true"
										selectBoxIcon="true"></sj:autocompleter>
								</sj:div></td>
							<td>Flag:</td>
							<td><select id="searchFlag" name="searchFlag">
									<option value=''>None</option>
									<option value='green'>Green</option>
									<option value='red' style="color: red;">Red</option>
									<option value='orange' style="color: orange;">Amber</option>
							</select></td>
						</tr>
						<tr>
							<td style="color: #2779aa;" align="right"><s:text
									name="pts.networkcode.pillar.name" />:</td>
							<td width="200" nowrap="nowrap"><sj:div id="pillarsDivId">
									<sj:autocompleter list="pillarsMapObj" name="pillar"
										listKey="key" listValue="value" theme="simple" id="pillarId"
										onSelectTopics="/pillarSelect" selectBox="true"
										selectBoxIcon="true"></sj:autocompleter>
								</sj:div> <sj:a id="getProjectsHrefId"
									href="../utilization/getProjectsMap.action"
									targets="projectsDivId" formIds="releaseTrainReportFormId"
									indicator="projectLoadingIndicator" /> <img
								id="projectLoadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.networkcode.project" />:
							</td>
							<td width="200"><sj:div id="projectsDivId">
									<sj:autocompleter list="projectsMapObj" name="project"
										listKey="key" listValue="value" theme="simple" id="projectId"
										selectBox="true" selectBoxIcon="true"
										onSelectTopics="/projectSelect"></sj:autocompleter>
								</sj:div> <sj:a id="getReleaseHrefId"
									href="../utilization/getReleaseMap.action"
									targets="releaseDivId" formIds="releaseTrainReportFormId"
									indicator="releaseLoadingIndicator" /> <img
								id="releaseLoadingIndicator" src="../images/indicatorImg.gif"
								style="display: none" /></td>
							<!-- <td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.netwok.code" />:
							</td>
							<td width="200"><sj:div id="releaseDivId">
									<sj:autocompleter list="releasesMapObj" name="release"
										listKey="key" listValue="value" theme="simple" id="releaseId"
										selectBox="true" selectBoxIcon="true"></sj:autocompleter>
								</sj:div></td> -->
							<td style="padding-left: 20px;"><s:submit theme="simple"
									src="../images/go.png" type="image"
									onclick="submitForm('../reports/releaseTrainReport.action')" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<table style="width: 100%;">
					<tr>
						<td><jsp:include page="releaseTrainReportGrid.jsp" /></td>
					</tr>
				</table>
			</div>

			<s:div id="spacer10px">&nbsp;</s:div>
			<s:div id="spacer10px">&nbsp;</s:div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>
