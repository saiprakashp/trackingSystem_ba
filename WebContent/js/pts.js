function reloadForm() {
	location.reload();
}

function clearErrorDiv() {
	$("#errorDiv").html("");
}

function goAssignNetworkCodes() {
	$("#goAssignNetworkCodesHrefId").click();
}

function getDashboardUtilizationHrefId() {
	$("#getDashboardUtilizationHrefId").click();
}

function getPillarsMap() {
	if (document.getElementById('pillarId_widget')) {
		if (document.getElementById('pillarId')) {
			document.getElementById('pillarId_widget').value == ''
			document.getElementById('pillarId').value = "";
		}
	}
	$("#getPillarsHrefId").click();
}

function getProjectsMap() {
	if (document.getElementById('projectId_widget')) {
		if (document.getElementById('projectId')) {
			document.getElementById('projectId_widget').value == '';
			document.getElementById('projectId').value = "";
		}
	}
	$("#getProjectsHrefId").click();
}

function getReleaseMap() {
	if (document.getElementById('releaseId_widget')) {
		if (document.getElementById('releaseId')) {
			document.getElementById('releaseId_widget').value == ''
			document.getElementById('releaseId').value = "";
		}
	}
	$("#getReleaseHrefId").click();
}

function getProgramManagersMap() {
	$("#getProgramManagersHrefId").click();
}

function getProjectManagersMap() {
	$("#getProjectManagersHrefId").click();
}
function getProjectManagersMap() {
	$("#getProjectManagersHrefId").click();
}

function getStableTeams() {
	$("#getStableTeamsHrefId").click();
}

function getCapacityStreamImage() {
	$("#getCapacityImageHrefId").click();
}

function getUserContributionReport() {
	$("#getUserContributionReportHrefId").click();
}

function validateFloatKeyPress(el, evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	var number = el.value.split('.');
	if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	// just one dot (thanks ddlab)
	if (number.length > 1 && charCode == 46) {
		return false;
	}
	// get the carat position
	var caratPos = getSelectionStart(el);
	var dotPos = el.value.indexOf(".");
	if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
		return false;
	}
	return true;
}

// thanks: http://javascript.nwbox.com/cursor_position/
function getSelectionStart(o) {
	try {
		if (o.createTextRange) {
			var r = document.selection.createRange().duplicate()
			r.moveEnd('character', o.value.length)
			if (r.text == '')
				return o.value.length
			return o.value.lastIndexOf(r.text)
		} else
			return o.selectionStart
	} catch (e) {

	}
}

function addRow(obj) {
	var i = $('#activityTable' + obj + ' tr').length - 1;
	$("#activityTable" + obj + " tr:nth-child(2)")
			.clone()
			.find("select")
			.each(
					function() {
						$(this)
								.attr(
										{
											'id' : function(_, id) {
												return id.replace(
														"activityList[0]",
														"activityList[" + i
																+ "]");
											},
											'name' : function(_, name) {
												return name
														.replace(
																"activityListToSave[0]",
																"activityListToSave["
																		+ i
																		+ "]");
											},
											'value' : '-1'
										});
						if ($(this).attr('id') == "activityList[" + i
								+ "].activityCode") {
							$(this).val($("#tempActivityCode").val());
						} else if ($(this).attr('id') == "activityList[" + i
								+ "].type") {
							$(this).val($("#tempType").val());
						} else {
							$(this).val('-1');
						}
					})
			.end()
			.find("input")
			.each(
					function() {
						$(this)
								.attr(
										{
											'id' : function(_, id) {
												return id.replace(
														"activityList[0]",
														"activityList[" + i
																+ "]");
											},
											'name' : function(_, name) {
												return name
														.replace(
																"activityListToSave[0]",
																"activityListToSave["
																		+ i
																		+ "]");
											},
											'value' : ''
										});
						$(this).val("");
					}).end().find("span").each(
					function() {
						$(this).attr(
								{
									'id' : function(_, id) {
										id.replace("approvalstatusid0",
												"approvalstatusid" + i);
										return id.replace("summationtdid0",
												"summationtdid" + i);
									}
								});
						$(this).html("");
					}).end().insertBefore('.summationtdid');
	
}

function addRow_old(obj) {
	var i = $('#activityTable' + obj + ' tr').length - 1;
	$("#activityTable" + obj + " tr:nth-child(2)")
			.clone()
			.find("select")
			.each(
					function() {
						$(this)
								.attr(
										{
											'id' : function(_, id) {
												return id.replace(
														"activityList[0]",
														"activityList[" + i
																+ "]");
											},
											'name' : function(_, name) {
												return name
														.replace(
																"timeSheetRecord.activityListToSave[0]",
																"timeSheetRecord.activityListToSave["
																		+ i
																		+ "]");
											},
											'value' : '-1'
										});
						if ($(this).attr('id') == "activityList[" + i
								+ "].activityCode") {
							$(this).val($("#tempActivityCode").val());
						} else if ($(this).attr('id') == "activityList[" + i
								+ "].type") {
							$(this).val($("#tempType").val());
						} else {
							$(this).val('-1');
						}
					})
			.end()
			.find("input")
			.each(
					function() {
						$(this)
								.attr(
										{
											'id' : function(_, id) {
												return id.replace(
														"activityList[0]",
														"activityList[" + i
																+ "]");
											},
											'name' : function(_, name) {
												return name
														.replace(
																"activityListToSave[0]",
																"activityListToSave["
																		+ i
																		+ "]");
											},
											'value' : ''
										});
						$(this).val("");
					}).end().find("span").each(
					function() {
						$(this).attr(
								{
									'id' : function(_, id) {
										id.replace("approvalstatusid0",
												"approvalstatusid" + i);
										return id.replace("summationtdid0",
												"summationtdid" + i);
									}
								});
						$(this).html("");
					}).end().appendTo('#activityTable' + obj);
}

function addRow_new(obj) {
	var i = $('#activityTable' + obj + ' tr').length - 1;
	$("#activityTable" + obj + " tr:nth-child(2)")
			.clone()
			.find("select")
			.each(
					function() {
						$(this)
								.attr(
										{
											'id' : function(_, id) {
												return id
														.replace(
																"activityListProjects[0]",
																"activityListProjects["
																		+ i
																		+ "]");
											},
											'name' : function(_, name) {
												return name
														.replace(
																"timeSheetRecord.activityListProjectsToSave[0]",
																"timeSheetRecord.activityListProjectsToSave["
																		+ i
																		+ "]");
											},
											'value' : '-1'
										});
						if ($(this).attr('id') == "activityListProjects[" + i
								+ "].activityCode") {
							$(this).val($("#tempActivityCode").val());
						} else if ($(this).attr('id') == "activityListProjects["
								+ i + "].type") {
							$(this).val($("#tempType").val());
						} else {
							$(this).val('-1');
						}
					})
			.end()
			.find("input")
			.each(
					function() {
						$(this)
								.attr(
										{
											'id' : function(_, id) {
												return id
														.replace(
																"activityListProjects[0]",
																"activityListProjects["
																		+ i
																		+ "]");
											},
											'name' : function(_, name) {
												return name
														.replace(
																"activityListProjectsToSave[0]",
																"activityListProjectsToSave["
																		+ i
																		+ "]");
											},
											'value' : ''
										});
						$(this).val("");
					}).end().find("span").each(
					function() {
						$(this).attr(
								{
									'id' : function(_, id) {
										id.replace("approvalstatusid1",
												"approvalstatusid" + i);
										return id.replace("summationtdid1",
												"summationtdid" + i);
									}
								});
						$(this).html("");
					}).end().appendTo('#activityTable' + obj);
}

function deleteRow(obj, id, removedId, removedTemplateId) {
	if (removedId != '') {
		$('#removedIds').val($('#removedIds').val() + ', ' + removedId);
	}
	if (removedTemplateId != '') {
		$('#removedTemplateIds').val(
				$('#removedTemplateIds').val() + ', ' + removedTemplateId);
	}
	if ($('#activityTable' + id + ' tr').length > 2) {
		$(obj).parent().parent().remove();
	} else {
		$(obj).parent().parent().find("select").each(function() {
			$(this).val('-1');
		});
		$(obj).parent().parent().find("input").each(function() {
			$(this).attr({
				'value' : ''
			});
		});
	}

	$("#activityTable" + id + " tr")
			.each(
					function(i) {
						if (i != 0) {
							$(this)
									.find("select")
									.each(
											function() {
												$(this)
														.attr(
																{
																	'id' : function(
																			_,
																			id) {
																		return (id
																				.substr(
																						0,
																						id
																								.lastIndexOf('[') + 1)
																				+ (i - 1) + id
																				.substr(id
																						.lastIndexOf(']')));
																	},
																	'name' : function(
																			_,
																			name) {
																		return (name
																				.substr(
																						0,
																						name
																								.lastIndexOf('[') + 1)
																				+ (i - 1) + name
																				.substr(name
																						.lastIndexOf(']')));
																	}
																});
											});
							$(this)
									.find("input")
									.each(
											function() {
												$(this)
														.attr(
																{
																	'id' : function(
																			_,
																			id) {
																		return (id
																				.substr(
																						0,
																						id
																								.lastIndexOf('[') + 1)
																				+ (i - 1) + id
																				.substr(id
																						.lastIndexOf(']')));
																	},
																	'name' : function(
																			_,
																			name) {
																		return (name
																				.substr(
																						0,
																						name
																								.lastIndexOf('[') + 1)
																				+ (i - 1) + name
																				.substr(name
																						.lastIndexOf(']')));
																	}
																});
											});
						}
					});
	calculateSummationNew('');
}

function submitForm(s) {
	clearErrorDiv();
	$('select.multiselect2 option').each(function() {
		this.selected = true;
	});
	if (document.getElementById('searchSupervisor_widget')
			&& document.getElementById('searchSupervisor_widget').value == '') {
		if (document.getElementById('searchSupervisor')) {
			document.getElementById('searchSupervisor').value = "";
		}
	}
	if (document.getElementById('searchStatus_widget')
			&& document.getElementById('searchStatus_widget').value == '') {
		if (document.getElementById('searchStatus')) {
			document.getElementById('searchStatus').value = "";
		}
	}
	if (document.getElementById('searchLocation_widget')
			&& document.getElementById('searchLocation_widget').value == '') {
		if (document.getElementById('searchLocation')) {
			document.getElementById('searchLocation').value = "";
		}
	}
	if (document.getElementById('searchUserType_widget')
			&& document.getElementById('searchUserType_widget').value == '') {
		if (document.getElementById('searchUserType')) {
			document.getElementById('searchUserType').value = "";
		}
	}
	if (document.getElementById('searchTechnology_widget')
			&& document.getElementById('searchTechnology_widget').value == '') {
		if (document.getElementById('searchTechnology')) {
			document.getElementById('searchTechnology').value = "";
		}
	}
	if (document.getElementById('searchStream_widget')
			&& document.getElementById('searchStream_widget').value == '') {
		if (document.getElementById('searchStream')) {
			document.getElementById('searchStream').value = "";
		}
	}
	if (document.getElementById('employeeListId_widget')
			&& document.getElementById('employeeListId_widget').value == '') {
		if (document.getElementById('employeeListId')) {
			document.getElementById('employeeListId').value = "";
		}
	}

	if (document.getElementById('ricoLocationId_widget')
			&& document.getElementById('ricoLocationId_widget').value == '') {
		if (document.getElementById('ricoLocationId')) {
			document.getElementById('ricoLocationId').value = "";
		}
	}

	if (document.getElementById('projectId_widget')
			&& document.getElementById('projectId_widget').value == '') {
		if (document.getElementById('projectId')) {
			document.getElementById('projectId').value = "";
		}
	}

	if (document.getElementById('pillarId_widget')
			&& document.getElementById('pillarId_widget').value == '') {
		if (document.getElementById('pillarId')) {
			document.getElementById('pillarId').value = "";
		}
	}

	if (document.getElementById('skillId_widget')
			&& document.getElementById('skillId_widget').value == '') {
		if (document.getElementById('skillId')) {
			document.getElementById('skillId').value = "";
		}
	}

	if (document.getElementById('primaryFlag_widget')
			&& document.getElementById('primaryFlag_widget').value == '') {
		if (document.getElementById('primaryFlag')) {
			document.getElementById('primaryFlag').value = "";
		}
	}
	if (document.getElementById('selectedYear_widget')
			&& document.getElementById('selectedYear_widget').value == '') {
		if (document.getElementById('selectedYear')) {
			document.getElementById('selectedYear').value = "0";
		}
	}
	if (document.getElementById('searchProjectManager_widget')
			&& document.getElementById('searchProjectManager_widget').value == '') {
		if (document.getElementById('searchProjectManager')) {
			document.getElementById('searchProjectManager').value = "";
		}
	}

	document.forms[0].method = "post";
	document.forms[0].action = s;
	document.forms[0].submit();
}

function addRight() {
	var options = $('select.multiselect1 option:selected').sort().clone();
	$('select.multiselect2').append(options);
	$("select.multiselect2 option:selected").removeAttr("selected");
	$('select.multiselect1 option:selected').remove();
}
function addAllRight() {
	var options = $('select.multiselect1 option').sort().clone();
	$('select.multiselect2').append(options);
	$("select.multiselect2 option:selected").removeAttr("selected");
	$('select.multiselect1').empty();
}
function addLeft() {
	var options = $('select.multiselect2 option:selected').sort().clone();
	$('select.multiselect1').append(options);
	$("select.multiselect1 option:selected").removeAttr("selected");
	$('select.multiselect2 option:selected').remove();
}
function addAllLeft() {
	var options = $('select.multiselect2 option').sort().clone();
	$('select.multiselect1').append(options);
	$("select.multiselect1 option:selected").removeAttr("selected");
	$('select.multiselect2').empty();
}

function validateActivityGrid(obj, tableId, strToReplace, replaceWith) {
	var activityId = obj.id;
	var networkId = activityId.replace(strToReplace, replaceWith);
	var tempNtId = '';
	var tempNtIdVal = '';
	var tempActId = '';
	var tempActIdVal = '';
	var networkIdValue = document.getElementById(networkId).value;
	var activityIdValue = obj.value;
	$("#activityTable" + tableId + " tr").each(
			function(i) {
				$(this).find("select").each(function() {
					if ($(this).attr('id').indexOf(replaceWith) > -1) {
						tempNtId = $(this).attr('id');
						tempNtIdVal = $(this).val();
					} else if ($(this).attr('id').indexOf(strToReplace) > -1) {
						tempActId = $(this).attr('id');
						tempActIdVal = $(this).val();
					}
				});
				if (tempNtId != networkId && tempActId != activityId) {
					if (networkIdValue == tempNtIdVal
							&& activityIdValue == tempActIdVal) {
						alert("Record already exists..");
						obj.value = '-1';
						return false;
					}
				}
			});
}

function validateActivityGridNew(obj, tableId, objStr) {
	var activityId = '';
	var networkId = '';
	var typeId = '';

	var networkIdValue = '';
	var activityIdValue = '';
	var typeIdValue = '';

	var tempNtId = '';
	var tempNtIdVal = '';
	var tempActId = '';
	var tempActIdVal = '';
	var tempTypeId = '';
	var tempTypeVal = '';

	var objId = obj.id;
	if (objStr == 'type') {
		typeId = objId;
		typeIdValue = obj.value;

		networkId = objId.replace(objStr, "networkId");
		networkIdValue = document.getElementById(networkId).value;

		activityId = objId.replace(objStr, "activityCode");
		activityIdValue = document.getElementById(activityId).value;
	} else if (objStr == 'networkId') {
		networkId = objId;
		networkIdValue = obj.value;

		activityId = objId.replace(objStr, "activityCode");
		activityIdValue = document.getElementById(activityId).value;

		typeId = objId.replace(objStr, "type");
		typeIdValue = document.getElementById(typeId).value;
	} else if (objStr == 'activityCode') {
		activityId = objId;
		activityIdValue = obj.value;

		networkId = objId.replace(objStr, "networkId");
		networkIdValue = document.getElementById(networkId).value;

		typeId = objId.replace(objStr, "type");
		typeIdValue = document.getElementById(typeId).value;
	}
	$("#activityTable" + tableId + " tr")
			.each(
					function(i) {
						$(this)
								.find("select")
								.each(
										function() {
											if ($(this).attr('id').indexOf(
													"networkId") > -1) {
												tempNtId = $(this).attr('id');
												tempNtIdVal = $(this).val();
											} else if ($(this).attr('id')
													.indexOf("activityCode") > -1) {
												tempActId = $(this).attr('id');
												tempActIdVal = $(this).val();
											} else if ($(this).attr('id')
													.indexOf("type") > -1) {
												tempTypeId = $(this).attr('id');
												tempTypeVal = $(this).val();
											}
										});
						if (tempNtId != networkId && tempActId != activityId
								&& tempTypeId != typeId) {
							if (networkIdValue == tempNtIdVal
									&& activityIdValue == tempActIdVal
									&& typeIdValue == tempTypeVal) {
								alert("Record already exists..");
								obj.value = '-1';
								return false;
							}
						}
					});
}

$.subscribe('rowselect', function(event, data) {
	var grid = event.originalEvent.grid;
	var sel_id = grid.jqGrid('getGridParam', 'selrow');
	var userId = grid.jqGrid('getCell', sel_id, 'id');
	document.getElementById('userId').value = userId;
});

$.subscribe('ncrowselect', function(event, data) {
	var grid = event.originalEvent.grid;
	var sel_id = grid.jqGrid('getGridParam', 'selrow');
	var ncId = grid.jqGrid('getCell', sel_id, 'id');
	document.getElementById('ncId').value = ncId;
});

$.subscribe('announcementrowselect', function(event, data) {
	var grid = event.originalEvent.grid;
	var sel_id = grid.jqGrid('getGridParam', 'selrow');
	var announcementId = grid.jqGrid('getCell', sel_id, 'id');
	document.getElementById('announcementId').value = announcementId;
});

function setAnnouncementID(announcementId) {
	document.getElementById('announcementId').value = announcementId;
}

function macdUser(obj) {
	if (obj == 'add') {
		document.forms[0].method = "post";
		document.forms[0].action = '../user/goCreateUser.action';
		document.forms[0].submit();
	} else if (obj == 'edit') {

		document.forms[0].method = "post";
		document.forms[0].action = '../user/goEditUser.action';
		document.forms[0].submit();
	} else if (obj == 'exportexcel') {

		document.forms[0].method = "post";
		document.forms[0].action = '../user/downloadUsers.action';
		document.forms[0].submit();
	} else if (obj == 'addnetworkcode') {

		document.forms[0].method = "post";
		document.forms[0].action = '../projects/goCreateNetworkCode.action';
		document.forms[0].submit();
	} else if (obj == 'editnetworkcode') {

		document.forms[0].method = "post";
		document.forms[0].action = '../projects/goEditNetworkCode.action';
		document.forms[0].submit();
	} else if (obj == 'cloneNetworkCode') {

		document.forms[0].method = "post";
		document.forms[0].action = '../projects/goCloneNetworkCode.action';
		document.forms[0].submit();
	} else if (obj == 'downloadNetworkCodes') {

		document.forms[0].method = "post";
		document.forms[0].action = '../projects/downloadNetworkCodes.action';
		document.forms[0].submit();
	} else if (obj == 'goViewNetworkCode') {
		document.forms[0].method = "post";
		document.forms[0].action = '../projects/goViewNetworkCode.action';
		document.forms[0].submit();
	} else if (obj == 'editannouncement') {
		document.forms[0].method = "post";
		document.forms[0].action = '../announcements/goEditAnnouncement.action';
		document.forms[0].submit();
	} else if (obj == 'addannouncement') {
		document.forms[0].method = "post";
		document.forms[0].action = '../announcements/goCreateAnnouncement.action';
		document.forms[0].submit();
	} else if (obj == 'editaccounts') {
		document.forms[0].method = "post";
		document.forms[0].action = '../announcements/goCreateAnnouncement.action';
		document.forms[0].submit();
	} else if (obj == 'addaccounts') {
		document.forms[0].method = "post";
		document.forms[0].action = '../announcements/goCreateAnnouncement.action';
		document.forms[0].submit();
	} else if (obj == 'editholidays') {
		document.forms[0].method = "post";
		document.forms[0].action = '../others/manageHolidays.action';
		document.forms[0].submit();
	} else if (obj == 'addholidays') {
		document.forms[0].method = "post";
		document.forms[0].action = '../others/manageHolidays.action';
		document.forms[0].submit();
	} else if (obj == 'downloadProjectWiseResourceUtilization') {
		document.forms[0].method = "post";
		document.forms[0].action = '../utilization/downloadnetworkCodeUtilization.action';
		document.forms[0].submit();
	}
}

function nceffortReport() {
	if (document.getElementById('nceffortReport')) {
		document.getElementById('nceffortReport').src = "../images/indicatorImg.gif";
		// process responseget
		// document.getElementById('nceffortReport').src =
		// "../reports/nceffortReport.action?networkCodeId=1";
		document.getElementById('nceffortReport').src = "../reports/nceffortStatisticsReport.action";
	}
}
function calculateLOE() {
	var globalDesignLOE = document.getElementById('globalDesignLOE').value;
	var globalDevLOE = document.getElementById('globalDevLOE').value;
	var globalTestLOE = document.getElementById('globalTestLOE').value;
	var globalImplementationLOE = document
			.getElementById('globalImplementationLOE').value;
	var globalProjectManagementLOE = document
			.getElementById('globalProjectManagementLOE').value;
	var globalKitLOE = document.getElementById('globalKitLOE').value;

	var localDesignLOE = document.getElementById('localDesignLOE').value;
	var localDevLOE = document.getElementById('localDevLOE').value;
	var localTestLOE = document.getElementById('localTestLOE').value;
	var localImplementationLOE = document
			.getElementById('localImplementationLOE').value;
	var localProjectManagementLOE = document
			.getElementById('localProjectManagementLOE').value;
	var localKitLOE = document.getElementById('localKitLOE').value;

	/*
	 * document.getElementById('totalLOE').value = (globalDesignLOE == '' ? 0 :
	 * parseInt(globalDesignLOE)) + (globalDevLOE == '' ? 0 :
	 * parseInt(globalDevLOE)) + (globalTestLOE == '' ? 0 :
	 * parseInt(globalTestLOE)) + (globalKitLOE == '' ? 0 :
	 * parseInt(globalKitLOE)) + (globalImplementationLOE == '' ? 0 :
	 * parseInt(globalImplementationLOE)) + (globalProjectManagementLOE == '' ?
	 * 0 : parseInt(globalProjectManagementLOE)) + (localDesignLOE == '' ? 0 :
	 * parseInt(localDesignLOE)) + (localDevLOE == '' ? 0 :
	 * parseInt(localDevLOE)) + (localTestLOE == '' ? 0 :
	 * parseInt(localTestLOE)) + (localImplementationLOE == '' ? 0 :
	 * parseInt(localImplementationLOE)) + (localProjectManagementLOE == '' ? 0 :
	 * parseInt(localProjectManagementLOE)) + (localKitLOE == '' ? 0 :
	 * parseInt(localKitLOE));
	 */

	calculateOriginalLOE();
}

function calculateOriginalLOE() {
	var globalDesignLOE = document.getElementById('globalDesignLOE').value;
	var globalDevLOE = document.getElementById('globalDevLOE').value;
	var globalTestLOE = document.getElementById('globalTestLOE').value;
	var globalImplementationLOE = document
			.getElementById('globalImplementationLOE').value;
	var globalProjectManagementLOE = document
			.getElementById('globalProjectManagementLOE').value;
	var globalKitLOE = document.getElementById('globalKitLOE').value;

	var localDesignLOE = document.getElementById('localDesignLOE').value;
	var localDevLOE = document.getElementById('localDevLOE').value;
	var localTestLOE = document.getElementById('localTestLOE').value;
	var localImplementationLOE = document
			.getElementById('localImplementationLOE').value;
	var localProjectManagementLOE = document
			.getElementById('localProjectManagementLOE').value;
	var localKitLOE = document.getElementById('localKitLOE').value;

	var originalDesignLOE = (globalDesignLOE == '' ? 0
			: parseInt(globalDesignLOE))
			+ (localDesignLOE == '' ? 0 : parseInt(localDesignLOE));
	document.getElementById('originalDesignLOE').value = originalDesignLOE;
	var originalDevLOE = (globalDevLOE == '' ? 0 : parseInt(globalDevLOE))
			+ (localDevLOE == '' ? 0 : parseInt(localDevLOE));
	document.getElementById('originalDevLOE').value = originalDevLOE;

	var originalTestLOE = (globalTestLOE == '' ? 0 : parseInt(globalTestLOE))
			+ (localTestLOE == '' ? 0 : parseInt(localTestLOE));
	document.getElementById('originalTestLOE').value = originalTestLOE;

	var originalImplementationLOE = (globalImplementationLOE == '' ? 0
			: parseInt(globalImplementationLOE))
			+ (localImplementationLOE == '' ? 0
					: parseInt(localImplementationLOE));
	document.getElementById('originalImplementationLOE').value = originalImplementationLOE;

	var originalProjectManagementLOE = (globalProjectManagementLOE == '' ? 0
			: parseInt(globalProjectManagementLOE))
			+ (localProjectManagementLOE == '' ? 0
					: parseInt(localProjectManagementLOE));
	document.getElementById('originalProjectManagementLOE').value = originalProjectManagementLOE;

	var originalKitLOE = (globalKitLOE == '' ? 0 : parseInt(globalKitLOE))
			+ (localKitLOE == '' ? 0 : parseInt(localKitLOE));
	document.getElementById('originalKitLOE').value = originalKitLOE;
	var temp = 0;
	if (document.getElementById('totalOriginalLOE').value != '') {
		temp = parseInt(document.getElementById('totalOriginalLOE').value);
	}
	console.log(parseInt(originalDesignLOE));
	console.log(parseInt(originalDevLOE));
	console.log(parseInt(originalTestLOE));
	console.log(parseInt(originalImplementationLOE));
	console.log(parseInt(originalProjectManagementLOE));
	console.log(parseInt(originalKitLOE));

	temp += (originalDesignLOE == '' ? 0 : parseInt(originalDesignLOE))
			+ (originalDevLOE == '' ? 0 : parseInt(originalDevLOE))
			+ (originalTestLOE == '' ? 0 : parseInt(originalTestLOE))
			+ (originalImplementationLOE == '' ? 0
					: parseInt(originalImplementationLOE))
			+ (originalProjectManagementLOE == '' ? 0
					: parseInt(originalProjectManagementLOE))
			+ (originalKitLOE == '' ? 0 : parseInt(originalKitLOE));
	document.getElementById('totalOriginalLOE').value = 0;
	document.getElementById('totalOriginalLOE').value = temp;
}

function charsAllowed(e, type) {
	var key;
	var keychar;

	if (window.event)
		key = window.event.keyCode;
	else if (e)
		key = e.which;
	else
		return true;
	keychar = String.fromCharCode(key);
	keychar = keychar.toLowerCase();
	if (e.ctrlKey == 1)
		return true;
	else
	// control keys
	if ((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13)
			|| (key == 27))
		return true;

	else
	// numeric and plus
	if (type == 'num')
		if ((("+0123456789").indexOf(keychar) > -1))
			return true;
		else
			return false;
	// numeric only
	else if (type == 'numonly')
		if ((("0123456789").indexOf(keychar) > -1))
			return true;
		else
			return false;
	else if (type == 'float')
		if ((("0123456789.").indexOf(keychar) > -1))
			return true;
		else
			return false;
	// alphanumeric
	else if (type == 'alph')
		if ((("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")
				.indexOf(keychar) > -1))
			return true;
		else
			return false;
	// alphanumericspecialchar
	else if (type == 'alphnumbsplchar')
		if ((("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789?/|!@#$%^&*()-_+={}][;':,~`\\\"")
				.indexOf(keychar) > -1)) {
			return true;
		} else {
			return false;
		}
	// shared Subscriber
	else if (type == 'shared')
		if ((("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_")
				.indexOf(keychar) > -1))
			return true;
		else
			return false;
	// alphanumeric and special chars
	else if (type == 'alphspl')
		if ((("abcdefghijklmnopqrstuvwxyz_-0123456789#'@.,: ").indexOf(keychar) > -1))
			return true;
		else
			return false;
	else if (type == 'alpha_period')
		if ((("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_ .")
				.indexOf(keychar) > -1))
			return true;
		else
			return false;

}

function calculateSummationNew(obj) {
	if (obj != '') {
		var name = obj.name;
		var findex = name.lastIndexOf('[');
		var lindex = name.lastIndexOf(']');
		var index = (name.substr((findex + 1), (lindex - findex - 1)));

		var monHrs = (document.getElementById('activityList[' + index
				+ '].monHrs')) ? document.getElementById('activityList['
				+ index + '].monHrs').value : 0.0;
		//console.log(monHrs)
		var tueHrs = (document.getElementById('activityList[' + index
				+ '].tueHrs')) ? document.getElementById('activityList['
				+ index + '].tueHrs').value : 0.0;
		var wedHrs = (document.getElementById('activityList[' + index
				+ '].wedHrs')) ? document.getElementById('activityList['
				+ index + '].wedHrs').value : 0.0;
		var thuHrs = (document.getElementById('activityList[' + index
				+ '].thuHrs')) ? document.getElementById('activityList['
				+ index + '].thuHrs').value : 0.0;
		var friHrs = (document.getElementById('activityList[' + index
				+ '].friHrs')) ? document.getElementById('activityList['
				+ index + '].friHrs').value : 0.0;
		var satHrs = (document.getElementById('activityList[' + index
				+ '].satHrs')) ? document.getElementById('activityList['
				+ index + '].satHrs').value : 0.0;
		var sunHrs = (document.getElementById('activityList[' + index
				+ '].sunHrs')) ? document.getElementById('activityList['
				+ index + '].sunHrs').value : 0.0;

		var monHrs1 = (document.getElementById('activityListProjects['
				+ index + '].monHrs')) ? document
				.getElementById('activityListProjects[' + index
						+ '].monHrs').value : 0.0;
		var tueHrs1 = (document.getElementById('activityListProjects['
				+ index + '].tueHrs')) ? document
				.getElementById('activityListProjects[' + index
						+ '].tueHrs').value : 0.0;
		var wedHrs1 = (document.getElementById('activityListProjects['
				+ index + '].wedHrs')) ? document
				.getElementById('activityListProjects[' + index
						+ '].wedHrs').value : 0.0;
		var thuHrs1 = (document.getElementById('activityListProjects['
				+ index + '].thuHrs')) ? document
				.getElementById('activityListProjects[' + index
						+ '].thuHrs').value : 0.0;
		var friHrs1 = (document.getElementById('activityListProjects['
				+ index + '].friHrs')) ? document
				.getElementById('activityListProjects[' + index
						+ '].friHrs').value : 0.0;
		var satHrs1 = (document.getElementById('activityListProjects['
				+ index + '].satHrs')) ? document
				.getElementById('activityListProjects[' + index
						+ '].satHrs').value : 0.0;
		var sunHrs1 = (document.getElementById('activityListProjects['
				+ index + '].sunHrs')) ? document
				.getElementById('activityListProjects[' + index
						+ '].sunHrs').value : 0.0;

		var summation = 0.0;
		if (monHrs == '') {
			monHrs = 0.0;
		}
		if (tueHrs == '') {
			tueHrs = 0.0;
		}
		if (wedHrs == '') {
			wedHrs = 0.0;
		}
		if (thuHrs == '') {
			thuHrs = 0.0;
		}
		if (friHrs == '') {
			friHrs = 0.0;
		}
		if (satHrs == '') {
			satHrs = 0.0;
		}
		if (sunHrs == '') {
			sunHrs = 0.0;
		}
		if (monHrs1 == '') {
			monHrs1 = 0.0;
		}
		if (tueHrs1 == '') {
			tueHrs1 = 0.0;
		}
		if (wedHrs1 == '') {
			wedHrs1 = 0.0;
		}
		if (thuHrs1 == '') {
			thuHrs1 = 0.0;
		}
		if (friHrs1 == '') {
			friHrs1 = 0.0;
		}
		if (satHrs1 == '') {
			satHrs1 = 0.0;
		}
		if (sunHrs1 == '') {
			sunHrs1 = 0.0;
		}
		summation = parseFloat(monHrs) + parseFloat(tueHrs)
				+ parseFloat(wedHrs) + parseFloat(thuHrs)
				+ parseFloat(friHrs) + parseFloat(satHrs)
				+ parseFloat(sunHrs);
		//console.log('#summationtdid' + index)
		//console.log(sunHrs)
		$('#summationtdid' + index).html(summation);
		summation = parseFloat(monHrs1) + parseFloat(tueHrs1)
				+ parseFloat(wedHrs1) + parseFloat(thuHrs1)
				+ parseFloat(friHrs1) + parseFloat(satHrs1)
				+ parseFloat(sunHrs1);
		$('#psummationtdid' + index).html(summation);
	}
	var weekSummation = '0.0';
	var monSummation = '0.0';
	var tueSummation = '0.0';
	var wedSummation = '0.0';
	var thuSummation = '0.0';
	var friSummation = '0.0';
	var satSummation = '0.0';
	var sunSummation = '0.0';

	var weekSummation1 = '0.0';
	var monSummation1 = '0.0';
	var tueSummation1 = '0.0';
	var wedSummation1 = '0.0';
	var thuSummation1 = '0.0';
	var friSummation1 = '0.0';
	var satSummation1 = '0.0';
	var sunSummation1 = '0.0';

	$('#activityTable0 > tbody > tr').each(function() {
		$(this).find("input[id$='.monHrs']").each(function() {
			var monVal = $(this).val();
			if (monVal == '') {
				monVal = '0.0';
			}
			monSummation = parseFloat(monSummation) + parseFloat(monVal);
		});
		$(this).find("input[id$='.tueHrs']").each(function() {
			var tueVal = $(this).val();
			if (tueVal == '') {
				tueVal = '0.0';
			}
			tueSummation = parseFloat(tueSummation) + parseFloat(tueVal);
		});
		$(this).find("input[id$='.wedHrs']").each(function() {
			var wedVal = $(this).val();
			if (wedVal == '') {
				wedVal = '0.0';
			}
			wedSummation = parseFloat(wedSummation) + parseFloat(wedVal);
		});
		$(this).find("input[id$='.thuHrs']").each(function() {
			var thuVal = $(this).val();
			if (thuVal == '') {
				thuVal = '0.0';
			}
			thuSummation = parseFloat(thuSummation) + parseFloat(thuVal);
		});
		$(this).find("input[id$='.friHrs']").each(function() {
			var friVal = $(this).val();
			if (friVal == '') {
				friVal = '0.0';
			}
			friSummation = parseFloat(friSummation) + parseFloat(friVal);
		});
		$(this).find("input[id$='.satHrs']").each(function() {
			var satVal = $(this).val();
			if (satVal == '') {
				satVal = '0.0';
			}
			satSummation = parseFloat(satSummation) + parseFloat(satVal);
		});
		$(this).find("input[id$='.sunHrs']").each(function() {
			var sunVal = $(this).val();
			if (sunVal == '') {
				sunVal = '0.0';
			}
			sunSummation = parseFloat(sunSummation) + parseFloat(sunVal);
		});
	});

	$('#activityTable1 > tbody > tr').each(function() {
		$(this).find("input[id$='.monHrs']").each(function() {
			var monVal = $(this).val();
			if (monVal == '') {
				monVal = '0.0';
			}
			monSummation1 = parseFloat(monSummation1) + parseFloat(monVal);
		});
		$(this).find("input[id$='.tueHrs']").each(function() {
			var tueVal = $(this).val();
			if (tueVal == '') {
				tueVal = '0.0';
			}
			tueSummation1 = parseFloat(tueSummation1) + parseFloat(tueVal);
		});
		$(this).find("input[id$='.wedHrs']").each(function() {
			var wedVal = $(this).val();
			if (wedVal == '') {
				wedVal = '0.0';
			}
			wedSummation1 = parseFloat(wedSummation1) + parseFloat(wedVal);
		});
		$(this).find("input[id$='.thuHrs']").each(function() {
			var thuVal = $(this).val();
			if (thuVal == '') {
				thuVal = '0.0';
			}
			thuSummation1 = parseFloat(thuSummation1) + parseFloat(thuVal);
		});
		$(this).find("input[id$='.friHrs']").each(function() {
			var friVal = $(this).val();
			if (friVal == '') {
				friVal = '0.0';
			}
			friSummation1 = parseFloat(friSummation1) + parseFloat(friVal);
		});
		$(this).find("input[id$='.satHrs']").each(function() {
			var satVal = $(this).val();
			if (satVal == '') {
				satVal = '0.0';
			}
			satSummation1 = parseFloat(satSummation1) + parseFloat(satVal);
		});
		$(this).find("input[id$='.sunHrs']").each(function() {
			var sunVal = $(this).val();
			if (sunVal == '') {
				sunVal = '0.0';
			}
			sunSummation1 = parseFloat(sunSummation1) + parseFloat(sunVal);
		});
	});

	$('#monSummation').html(monSummation + monSummation1);
	$('#tueSummation').html(tueSummation + tueSummation1);
	$('#wedSummation').html(wedSummation + wedSummation1);
	$('#thuSummation').html(thuSummation + thuSummation1);
	$('#friSummation').html(friSummation + friSummation1);
	$('#satSummation').html(satSummation + satSummation1);
	$('#sunSummation').html(sunSummation + sunSummation1);
	weekSummation = parseFloat(monSummation) + parseFloat(tueSummation)
			+ parseFloat(wedSummation) + parseFloat(thuSummation)
			+ parseFloat(friSummation) + parseFloat(satSummation)
			+ parseFloat(sunSummation) + parseFloat(monSummation1)
			+ parseFloat(tueSummation1) + parseFloat(wedSummation1)
			+ parseFloat(thuSummation1) + parseFloat(friSummation1)
			+ parseFloat(satSummation1) + parseFloat(sunSummation1);
	$('#weekSummation').html(weekSummation);
}

function onchangeS(selectEl) {
	selectEl.title = selectEl.options(selectEl.selectedIndex).text;
}

function loadAgeSelector() {
	var startyear = 2015;
	var endyear = new Date().getFullYear();

	for (var i = startyear; i <= endyear; i++) {
		node = document.createElement("Option");
		textnode = document.createTextNode(i);
		node.appendChild(textnode);
		document.getElementById("tempSelectedYear").appendChild(node);
	}

	if (document.getElementById("selectedYear")
			&& document.getElementById("selectedYear").value != '') {
		for (var i = 0; i < document.getElementById('tempSelectedYear').options.length; i++) {
			if (document.getElementById('tempSelectedYear').options[i].value === document
					.getElementById("selectedYear").value) {
				document.getElementById('tempSelectedYear').selectedIndex = i;
				break;
			}
		}
	} else {
		document.getElementById('tempSelectedYear').selectedIndex = 0;
		document.getElementById("selectedYear").value = document
				.getElementById('tempSelectedYear').options[0].value
	}
}

function setYear(val) {
	if (document.getElementById("selectedYear")) {
		document.getElementById("selectedYear").value = val;
	}
}

function ricoTrainings() {
	document.forms[0].target = window.open(
			'http://tulc3deva.vzbi.com:18080/RICOTrainings/', 'rep',
			'resizable=1, scrollbars=1, status=0, toolbar=0');
}

function removeOption(val, fromSelectBoxId, toSelectBoxId) {
	var fromselectobject = document.getElementById(fromSelectBoxId)
	var toselectobject = document.getElementById(toSelectBoxId)
	var hexvalues = [];
	var labelvalues = [];
	var html = "";
	$('#' + toSelectBoxId + ' :selected').each(function(i, selectedElement) {
		hexvalues[i] = $(selectedElement).val();
		labelvalues[i] = $(selectedElement).text();
	});

	var j = 0;
	toselectobject.options.length = 0;
	// toselectobject.options.length = fromselectobject.options.length - 2;

	for (var i = 0; i < fromselectobject.length; i++) {
		if (fromselectobject.options[i].value != val
				&& fromselectobject.options[i].value != '-1') {
			/*
			 * toselectobject.options[j].text =
			 * fromselectobject.options[i].text; toselectobject.options[j].value =
			 * fromselectobject.options[i].value;
			 */
			html = '<option value="' + fromselectobject.options[i].value + '">'
					+ fromselectobject.options[i].text + '</option>';
			$('#' + toSelectBoxId).append(html);

			if (jQuery.inArray(toselectobject.options[j].value, hexvalues) != -1) {
				toselectobject.options[j].selected = true;
			} else {
				toselectobject.options[j].selected = false;
			}
			j++;
		}
	}
}

function getMonthData(type) {
	if (type == 'dec') {

		var dt = new Date(document.getElementById("selectedDate").value
				.replace(/(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3"));
		dt.setDate(dt.getDate() - 7);
		document.getElementById("selectedDate").value = (dt.getMonth() + 1)
				+ '/' + dt.getDate() + '/' + dt.getFullYear();
		document.getElementById("submitDate").click()
	} else {
		var dt = new Date(document.getElementById("selectedDate").value
				.replace(/(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3"));
		dt.setDate(dt.getDate() + 7);
		document.getElementById("selectedDate").value = (dt.getMonth() + 1)
				+ '/' + dt.getDate() + '/' + dt.getFullYear();
		document.getElementById("submitDate").click()
	}

}
