<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
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
<style type="text/css">
.activityTable1 {
	border: 1px solid #2779AA;
	border-radius: 5px;
	border-spacing: 1px;
}

.unselectable {
	background-color: #ddd;
	cursor: not-allowed;
}

.activityTable1 TH {
	color: #333;
	font-style: normal;
	font-weight: 600;
	font-family: Arial, San-Serif;
	font-size: 12px;
	background-color: #D2D2D2;
	text-align: left;
	height: 20px;
	padding-left: 5px;
}

.activityTable1 TD {
	color: #333;
	font-style: normal;
	font-weight: normal;
	font-family: Arial, San-Serif;
	font-size: 12px;
	text-align: left;
}

.activityTable1 select, text {
	font-style: normal;
	font-size: 12px;
	font-family: Arial, sans-serif;
}

.container {
	display: block;
	position: relative;
	padding-left: 30px;
	margin-bottom: 7px;
	cursor: pointer;
	font-size: 10px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}
/* Hide the browser's default checkbox */
.container input {
	position: absolute;
	opacity: 0;
	cursor: pointer;
	height: 0;
	width: 0;
}
/* Create a custom checkbox */
.checkmark {
	position: absolute;
	top: 0;
	left: 0;
	height: 15px;
	width: 15px;
	background-color: #ECECEC;
}
/* On mouse-over, add a grey background color */
.container:hover input ~ .checkmark {
	background-color: #ccc;
}
/* When the checkbox is checked, add a blue background */
.container input:checked ~ .checkmark {
	background-color: #2196F3;
}
/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
	content: "";
	position: absolute;
	display: none;
}
/* Show the checkmark when checked */
.container input:checked ~ .checkmark:after {
	display: block;
}
/* Style the checkmark/indicator */
.container .checkmark:after {
	left: 6px;
	top: 4px;
	width: 1px;
	height: 5px;
	border: solid white;
	border-width: 0 3px 3px 0;
	-webkit-transform: rotate(45deg);
	-ms-transform: rotate(45deg);
	transform: rotate(45deg);
}
</style>
<script type="text/javascript">
var feasibilityEnabled = ' <s:property value="feasibilityEnabled" />';
var ptlEnabled = '<s:property value="ptlEnabled" />';
var projectSupportEnabled = ' <s:property value="projectSupportEnabled" />';
var securityEnabled = ' <s:property value="securityEnabled" />';
var additionProj = true;
var userDataMap;
function updateUserCapacity(ref) {
	var tmp='';
	var id=ref.id.split(".")[0].slice(-2,-1);
	var nw=document.getElementById("activityList["+id+"].networkId").value;
	userDataMap.forEach((data) => {  if(data.search(nw+":") >= 0){ tmp=(data); return; }  })
	var totalPoints=(parseFloat(tmp.split(":")[1]) * 8.0);
	var total=(	
			parseFloat(document.getElementById("activityList["+id+"].monHrs").value)+
			parseFloat(document.getElementById("activityList["+id+"].tueHrs").value)+
			parseFloat(document.getElementById("activityList["+id+"].wedHrs").value)+
			parseFloat(document.getElementById("activityList["+id+"].thuHrs").value)+
			parseFloat(document.getElementById("activityList["+id+"].friHrs").value)+
			parseFloat(document.getElementById("activityList["+id+"].satHrs").value)+
			parseFloat(document.getElementById("activityList["+id+"].sunHrs").value)
			);
	var diff=totalPoints - total;
	document.getElementById("userCapacity_td_"+id).innerHTML=diff;
	if((diff/totalPoints * 100) > 80){
	document.getElementById("userCapacity_td_"+id).style="background: #d4edda;    text-align: center;color:black;font-weight: bolder;"
	}else if ( (diff/totalPoints * 100)>= 75 && (diff/totalPoints * 100) < 80 ) {
		document.getElementById("userCapacity_td_"+id).style="background: orange;    text-align: center;color:black;font-weight: bolder;"	
	}
	else{
		document.getElementById("userCapacity_td_"+id).style="background: #dc3545;    text-align: center;color:black;font-weight: bolder;"
		}
}
function loadMap() {
	 
	userDataMap=document.getElementById("userCapacity").value;
	 userDataMap=userDataMap.replaceAll("{","").replaceAll("}","").replaceAll(" ","").replaceAll("=",":").split(",")
//	userDataMap.forEach((data) => {  if(data.search("455=") >= 0){ tmp=(data); return; }  })

	 var weekSummation = '0.0';
		var monSummation = '0.0';
		var tueSummation = '0.0';
		var wedSummation = '0.0';
		var thuSummation = '0.0';
		var friSummation = '0.0';
		var satSummation = '0.0';
		var sunSummation = '0.0';
		var userCapacity = '0.0';
		
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
		 	$(this).find("span[id*='userCapacity']").each(function(e) {
				var userCapacity = $(this).val();
				if (userCapacity == '') {
					userCapacity = '0.0';
				}
				var tmp='';
				var nw=document.getElementById("activityList["+$(this).attr("id").split("_")[1]+"].networkId").value;
				userDataMap.forEach((data) => {  if(data.search(nw+":") >= 0){ tmp=(data); return; }  })
				userCapacity = parseFloat(userCapacity) +  parseFloat(monSummation) + parseFloat(tueSummation)
				+ parseFloat(wedSummation) + parseFloat(thuSummation)
				+ parseFloat(friSummation) + parseFloat(satSummation)
				+ parseFloat(sunSummation) ;
				document.getElementById("userCapacity_"+$(this).attr("id").split("_")[1]).innerHTML=(parseFloat(tmp.split(":")[1]) * 8.0) - userCapacity;
				document.getElementById("userCapacity_"+$(this).attr("id").split("_")[1]).value=(parseFloat(tmp.split(":")[1]) * 8.0) - userCapacity;
			});
			
			
		});  
}



function enableThisRow(ref, val) {
	if (ref.checked) {
		document.getElementById("activityList[" + val + "].networkId").className = "";
		document.getElementById("activityList[" + val + "].activityCode").className = "";
		document.getElementById("activityList[" + val + "].type").className = "";
		document.getElementById("activityList[" + val + "].monHrs").className = "";
		document.getElementById("activityList[" + val + "].tueHrs").className = "";
		document.getElementById("activityList[" + val + "].wedHrs").className = "";
		document.getElementById("activityList[" + val + "].thuHrs").className = "";
		document.getElementById("activityList[" + val + "].friHrs").className = "";
		document.getElementById("activityList[" + val + "].satHrs").className = "";
		document.getElementById("activityList[" + val + "].sunHrs").className = "";
	} else {
		document.getElementById("activityList[" + val + "].networkId").className = "unselectable";
		document.getElementById("activityList[" + val + "].activityCode").className = "unselectable";
		document.getElementById("activityList[" + val + "].type").className = "unselectable";
		document.getElementById("activityList[" + val + "].monHrs").className = "unselectable";
		document.getElementById("activityList[" + val + "].tueHrs").className = "unselectable";
		document.getElementById("activityList[" + val + "].wedHrs").className = "unselectable";
		document.getElementById("activityList[" + val + "].thuHrs").className = "unselectable";
		document.getElementById("activityList[" + val + "].friHrs").className = "unselectable";
		document.getElementById("activityList[" + val + "].satHrs").className = "unselectable";
		document.getElementById("activityList[" + val + "].sunHrs").className = "unselectable";
	}
}

	function checkData(val) {
		$('#activityTable1 > tbody > tr').each(
				function() {
					if (this.style.display == 'table-row') {
						if (document.getElementById(this.id.split('_')[0]
								+ '_td') != null) {
							document.getElementById(this.id.split('_')[0]
									+ '_check').checked = true;
						}
					}
				});

	}
	function showaddAditionalProjects() {
		if (additionProj) {
			$("#addAditionalProjects").fadeOut();
		} else {
			$("#addAditionalProjects").fadeIn();
		}
		additionProj = !additionProj;

	}

	function changeCheckBoxVal(val) {
		document.getElementById(val + '_check').checked = !document
				.getElementById(val + '_check').checked;
	}

	function spaceReplacor(val) {
		val.replace(/ /g, "_")
	}

	function changeStyle(val) {
		$("." + val + "_tr").each(
				function() {

					if ($(this).css('display') != null
							&& $(this).css('display') === 'table-row') {

						$(this).css({
							'display' : 'none'
						});
					} else {
						$(this).css({
							'display' : 'table-row'
						});
					}

				})

	}
	function calculateSummationNew1(obj) {

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

	function loadTotalCap() {
		calculateSummationNew1('');
		////console.log(<s:property value="month" />);
		////console.log(<s:property value="plannedCapacity" />);

		//	$('#totalCapacity').html('');
	}

	$.subscribe('datechange', function(event, data) {
		clearErrorDiv();
		document.getElementById("loadingIndicator").style.display = "";
		submitForm('../timesheet/logTimesheet.action')
	});
	function saveTimeSheetTemplate() {
		var sThisVal = [];
		$('#addAditionalProjects input:checkbox').each(function() {
			if (this.checked)
				sThisVal.push(this.id);
		});
		document.getElementById("additionalProjects").value = sThisVal
				.join(",");

		clearErrorDiv();
		document.forms[0].method = "post";
		document.forms[0].action = '../timesheet/saveTemplate.action';
		document.forms[0].submit();

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
			summation = parseFloat(monHrs) + parseFloat(tueHrs)
					+ parseFloat(wedHrs) + parseFloat(thuHrs)
					+ parseFloat(friHrs) + parseFloat(satHrs)
					+ parseFloat(sunHrs);

			$('#summationtdid' + index).html(summation);
		}
		var weekSummation = '0.0';
		var monSummation = '0.0';
		var tueSummation = '0.0';
		var wedSummation = '0.0';
		var thuSummation = '0.0';
		var friSummation = '0.0';
		var satSummation = '0.0';
		var sunSummation = '0.0';
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
		var weekName = '';
		var message = 'Total time for a given day cannot exceed 24 hrs for: '
		weekName;
		var showTimeLimit = false;
		if (monSummation > 24.00) {
			showTimeLimit = true;
			weekName += 'Monday ';
		}
		if (tueSummation > 24.00) {
			showTimeLimit = true;
			weekName += ' TuesDay  ';
		}
		if (wedSummation > 24.00) {
			weekName += ' Wednesday ';
			showTimeLimit = true;
		}
		if (thuSummation > 24.00) {
			weekName += ' Thursday ';
			showTimeLimit = true;
		}
		if (friSummation > 24.00) {
			weekName += ' Friday ';
			showTimeLimit = true;
		}
		if (satSummation > 24.00) {
			weekName += ' Saturday ';
			showTimeLimit = true;
		}
		if (sunSummation > 24.00) {
			weekName += ' Sunday ';
			showTimeLimit = true;
		}
		if (showTimeLimit) {
			alert(message + weekName);
		}
		$('#monSummation').html(monSummation);
		$('#tueSummation').html(tueSummation);
		$('#wedSummation').html(wedSummation);
		$('#thuSummation').html(thuSummation);
		$('#friSummation').html(friSummation);
		$('#satSummation').html(satSummation);
		$('#sunSummation').html(sunSummation);

		weekSummation = parseFloat(monSummation) + parseFloat(tueSummation)
				+ parseFloat(wedSummation) + parseFloat(thuSummation)
				+ parseFloat(friSummation) + parseFloat(satSummation)
				+ parseFloat(sunSummation);
		$('#weekSummation').html(weekSummation);
	}

	function validateHours() {
		var weekName = '';
		var message = 'Total time for a given day cannot exceed 24 hrs for: ';
		var showTimeLimit = false;

		var mon = $('#monSummation').text();
		var tue = $('#tueSummation').text();
		var wed = $('#wedSummation').text();
		var thu = $('#thuSummation').text();
		var fri = $('#friSummation').text();
		var sat = $('#satSummation').text();
		var sun = $('#sunSummation').text();
		if ((mon != null && parseFloat(mon) > 24.00)
				|| (tue != null && parseFloat(tue) > 24.00)
				|| (wed != null && parseFloat(wed) > 24.00)
				|| (thu != null && parseFloat(thu) > 24.00)
				|| (fri != null && parseFloat(fri) > 24.00)
				|| (sat != null && parseFloat(sat) > 24.00)
				|| (sun != null && parseFloat(sun) > 24.00)) {
			showTimeLimit = true;
		}
		if (showTimeLimit) {
			alert(message + weekName);

		} else {
			submitForm('../timesheet/saveUserActivity.action')
		}
		return false;
	}

	function disabeldtestrest(ref, e, flag) {
		if (flag) {
			$("#activityTable1").find("select,:text,textarea").attr("disabled",
					"disabled");
		} else {
			var findex = ref.id.lastIndexOf('[');
			var lindex = ref.id.lastIndexOf(']');
			var id = (ref.id.substr((findex + 1), (lindex - findex - 1)));
			if (document.getElementById("activityNew[" + id + "].activityCode").disabled) {
				document.getElementById("activityNew[" + id + "].activityCode").disabled = false;
			} else {
				document.getElementById("activityNew[" + id + "].activityCode").disabled = true;
			}

			if (document.getElementById("activityNew[" + id + "].FESABILITY").disabled) {
				document.getElementById("activityNew[" + id + "].FESABILITY").disabled = false;
			} else {
				document.getElementById("activityNew[" + id + "].FESABILITY").disabled = true;
			}

			if (document.getElementById("activityNew[" + id + "].AD_HOC").disabled) {
				document.getElementById("activityNew[" + id + "].AD_HOC").disabled = false;
			} else {
				document.getElementById("activityNew[" + id + "].AD_HOC").disabled = true;
			}

			if (document.getElementById("activityNew[" + id
					+ "].FOURTH_LEVEL_SUPPORT").disabled) {
				document.getElementById("activityNew[" + id
						+ "].FOURTH_LEVEL_SUPPORT").disabled = false;
			} else {
				document.getElementById("activityNew[" + id
						+ "].FOURTH_LEVEL_SUPPORT").disabled = true;
			}

			if (document.getElementById("activityNew[" + id
					+ "].SECONDND_LEVEL_SUPPORT").disabled) {
				document.getElementById("activityNew[" + id
						+ "].SECONDND_LEVEL_SUPPORT").disabled = false;
			} else {
				document.getElementById("activityNew[" + id
						+ "].SECONDND_LEVEL_SUPPORT").disabled = true;
			}
		}
	}

	function deleteactivityrow(ref, e, id, type) {
		var data = id.split('_');
		$('#removedIds')
				.val(
						$('#removedIds').val()
								+ ', '
								+ document
										.getElementById("timeSheetRecord.activityListProductsToSave["
												+ data[data.length - 1]
												+ "].id").value)
		$("#" + id).remove()

	}
	function addactivityrow(ref, e) {
		e.preventDefault();
		var table = $('#activityTable1');

		var $tr = $(table).find('tr:last').clone();

		$tr.find('td').each(
				function() {
					var el = $(this).find(':first-child');
					var id = el.attr('id') || null;

					if (id) {
						var findex = id.lastIndexOf('[');
						var lindex = id.lastIndexOf(']');
						var index = (id.substr((findex + 1),
								(lindex - findex - 1)));

						index = parseInt(index) + 1;
						if (id.search("activityCode")) {
							el.attr('id', "activityNew[" + index
									+ "].activityCode");
							el.attr('name', "activityNewToSave[" + index
									+ "].activityCode");
						} else if (id.search("FESABILITY")) {
							el.attr('id', "activityNew[" + index
									+ "].FESABILITY");
							el.attr('name', "activityNewToSave[" + id
									+ "].FESABILITY");
						} else if (id.search("AD_HOC")) {
							el.attr('id', "activityNew[" + index + "].AD_HOC");
							el.attr('name', "activityNewToSave[" + index
									+ "].AD_HOC");
						} else if (id.search("FOURTH_LEVEL_SUPPORT")) {
							el.attr('id', "activityNew[" + index
									+ "].FOURTH_LEVEL_SUPPORT");
							el.attr('name', "activityNewToSave[" + index
									+ "].FOURTH_LEVEL_SUPPORT");
						} else if (id.search("SECONDND_LEVEL_SUPPORT")) {
							el.attr('id', "activityNew[" + index
									+ "].SECONDND_LEVEL_SUPPORT");
							el.attr('name', "activityNewToSave[" + index
									+ "].SECONDND_LEVEL_SUPPORT");
						}

					}
				});
		$(table).append($tr);

		var index = parseInt($('#activityTable1 tr:last').prev().attr('id')
				.split("_")[2]) + 1;
		$('#activityTable1 tr:last').attr('id', "activity_tr_" + index)

	}
	function addDataToMap(ref, e) {
		//console.log(ref)
	}

	function addRow_new(ref, event) {
		var nextid = $('#activityTable1 tr').length - 1;
		var id = event.target.getAttribute("trId");
		var x = id.split('_');
		var current = x[x.length - 1];

		$("#" + event.target.getAttribute("trId")).after(
				$("#" + event.target.getAttribute("trId")).clone().find(
						"select").each(
						function() {

							$(this).attr(
									{
										'id' : function(_, id) {
											return $(this).attr('id').replace(
													current, nextid);
										},
										'name' : function(_, name) {
											return $(this).attr('name')
													.replace(current, nextid);
										},
										'value' : '-1'
									}

							);
						}

				).end().find("input").each(
						function() {

							$(this).attr(
									{
										'id' : function(_, id) {
											return $(this).attr('id').replace(
													current, nextid);
										},
										'name' : function(_, name) {
											return $(this).attr('name')
													.replace(current, nextid);
										},
										'value' : '-1'
									});
							$(this).val("");

						}).end());
		document.getElementById("timeSheetRecord.activityListProductsToSave["
				+ nextid + "].networkId").value = document
				.getElementById("timeSheetRecord.activityListProductsToSave["
						+ current + "].networkId").value;
		document.getElementById("timeSheetRecord.activityListProductsToSave["
				+ nextid + "].activityDesc").value = document
				.getElementById("timeSheetRecord.activityListProductsToSave["
						+ current + "].activityDesc").value;
		document.getElementById("timeSheetRecord.activityListProductsToSave["
				+ nextid + "].activityType").value = document
				.getElementById("timeSheetRecord.activityListProductsToSave["
						+ current + "].activityType").value;
		document.getElementById("timeSheetRecord.activityListProductsToSave["
				+ nextid + "].type").value = document
				.getElementById("timeSheetRecord.activityListProductsToSave["
						+ current + "].type").value;

	}

	function addRow(re, obj) {
		var trId = event.target.getAttribute("trId");
		var nextid = $('#activityTable0 tr').length - 1;
		var id = event.target.getAttribute("trId");
		var current = nextid - 1;
		console.log(current, nextid, trId)
		$("#activityList_tr_" + current).after(
				$("#activityList_tr_" + current).clone().attr("id",
						"activityList_tr_" + nextid).find("select").each(
						function() {

							$(this).attr(
									{
										'id' : function(_, id) {
											return $(this).attr('id').replace(
													current, nextid);
										},
										'name' : function(_, name) {
											return $(this).attr('name')
													.replace(current, nextid);
										},
										'value' : '-1'
									}

							);
						}

				).end().find("input").each(
						function() {

							$(this).attr(
									{
										'id' : function(_, id) {
											return $(this).attr('id').replace(
													current, nextid);
										},
										'name' : function(_, name) {
											return $(this).attr('name')
													.replace(current, nextid);
										},
										'value' : '-1'
									});
							$(this).val("");

						}).end().find("img").each(
						function() {
							if ($(this).attr("trid") != null) {
								$(this).attr(
										{
											'trid' : function(_, id) {
												return $(this).attr('trid')
														.replace(current,
																nextid);
											}
										});
							}

						}).end());
		if (document.getElementById("timeSheetRecord.activityListToSave["
				+ current + "].networkId"))
			document.getElementById("timeSheetRecord.activityListToSave["
					+ nextid + "].networkId").value = document
					.getElementById("timeSheetRecord.activityListToSave["
							+ current + "].networkId").value;

		if (document.getElementById("timeSheetRecord.activityListToSave["
				+ current + "].activityDesc"))
			document.getElementById("timeSheetRecord.activityListToSave["
					+ nextid + "].activityDesc").value = document
					.getElementById("timeSheetRecord.activityListToSave["
							+ current + "].activityDesc").value;

		if (document.getElementById("timeSheetRecord.activityListToSave["
				+ current + "].activityType"))
			document.getElementById("timeSheetRecord.activityListToSave["
					+ nextid + "].activityType").value = document
					.getElementById("timeSheetRecord.activityListToSave["
							+ current + "].activityType").value;

		if (document.getElementById("timeSheetRecord.activityListToSave["
				+ current + "].type") != null)
			document.getElementById("timeSheetRecord.activityListToSave["
					+ nextid + "].type").value = document
					.getElementById("timeSheetRecord.activityListToSave["
							+ current + "].type").value;

	}
	function saveTimeSheet() {

		validateHours();

	}
	function saveTimeSheet1() {
		var time1 = [];
		var time2 = [];

		$("#activityTable0 tbody tr")
				.each(
						function() {
							var index = -1;
							var obj = {
								"id" : null,
								"networkId" : null,
								"networkIdDesc" : null,
								"activityCode" : 0,
								"activityDesc" : "",
								"monHrs" : 0.0,
								"tueHrs" : 0.0,
								"wedHrs" : 0.0,
								"thuHrs" : 0.0,
								"friHrs" : 0.0,
								"satHrs" : 0.0,
								"sunHrs" : 0.0,
								"totalHrs" : null,
								"userName" : null,
								"userId" : null,
								"templateId" : null,
								"projectAssignmentId" : null,
								"type" : "14",
								"approvalStatus" : null,
								"approvedStatusFlag" : false,
								"rejectedStatusFlag" : false,
								"activityType" : "P",
								"createdBy" : null,
								"createdDate" : null,
								"weekendingDate" : null,
								"addCheckFlag" : false,
								"total" : 0.0,
								"activitySummation" : 0.0
							};

							$("#" + this.id + " td>")
									.each(
											function() {
												if (this.id.includes(".id")) {
													obj.id = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".templateId")) {
													obj.templateId = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".createdBy")) {
													obj.createdBy = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".createdDate")) {
													obj.createdDate = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".networkId")) {
													obj.networkId = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".activityCode")) {
													obj.activityCode = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".type")) {
													obj.type = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".monHrs")) {
													obj.monHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".tueHrs")) {
													obj.tueHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".wedHrs")) {
													obj.wedHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".thuHrs")) {
													obj.thuHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".friHrs")) {
													obj.friHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".satHrs")) {
													obj.satHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".sunHrs")) {
													obj.sunHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												}

												var findex = name
														.lastIndexOf('[');
												var lindex = name
														.lastIndexOf(']');
												index = (name.substr(
														(findex + 1), (lindex
																- findex - 1)));
											});
							obj.createdDate = (document
									.getElementById("activityList[" + index
											+ "].createdDate") != null) ? document
									.getElementById("activityList[" + index
											+ "].createdDate").value
									: null;
							obj.createdBy = (document
									.getElementById("activityList[" + index
											+ "].createdBy") != null) ? document
									.getElementById("activityList[" + index
											+ "].createdBy").value
									: null;
							time1.push(obj);
						});
		///////////////////////////////////////////////////////////////////

		$("#activityTable1 tbody tr")
				.each(
						function() {
							var index = -1;
							var obj = {
								"id" : null,
								"networkId" : null,
								"networkIdDesc" : null,
								"activityCode" : 0,
								"activityDesc" : "",
								"monHrs" : 0.0,
								"tueHrs" : 0.0,
								"wedHrs" : 0.0,
								"thuHrs" : 0.0,
								"friHrs" : 0.0,
								"satHrs" : 0.0,
								"sunHrs" : 0.0,
								"totalHrs" : null,
								"userName" : null,
								"userId" : null,
								"templateId" : null,
								"projectAssignmentId" : null,
								"type" : "14",
								"approvalStatus" : null,
								"approvedStatusFlag" : false,
								"rejectedStatusFlag" : false,
								"activityType" : "P",
								"createdBy" : null,
								"createdDate" : null,
								"weekendingDate" : null,
								"addCheckFlag" : false,
								"total" : 0.0,
								"activitySummation" : 0.0,
								"activityCodetype" : null
							};

							$("#" + this.id + " td>")
									.each(
											function() {

												if (this.id.includes(".id")) {
													obj.id = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".templateId")) {
													obj.templateId = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".createdBy")) {
													obj.createdBy = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".createdDate")) {
													obj.createdDate = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".networkId")) {
													obj.networkId = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".activityCodetype")) {
													obj.activityCodetype = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".	")) {
													obj.activityCode = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".type")) {
													obj.type = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".monHrs")) {
													obj.monHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".tueHrs")) {
													obj.tueHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".wedHrs")) {
													obj.wedHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".thuHrs")) {
													obj.thuHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".friHrs")) {
													obj.friHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".satHrs")) {
													obj.satHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												} else if (this.id
														.includes(".sunHrs")) {
													obj.sunHrs = (document
															.getElementById(this.id) != null) ? document
															.getElementById(this.id).value
															: null;
												}

												var findex = name
														.lastIndexOf('[');
												var lindex = name
														.lastIndexOf(']');
												index = (name.substr(
														(findex + 1), (lindex
																- findex - 1)));
											});
							time2.push(obj);
						});

		///////////////////////////////////////////////////////////////////////
		console.log(time2)
		var body = {
			"activityListToSave" : time1,
			"activityListProductsToSave" : time2

		}
		/* 	var jqxhr = $.post( "../timesheet/saveUserActivity.action",  
					{
				  
				 
					}	
				)
				  .done(function() {
					  console.log( "second success" );
				  })
				  .fail(function(err) {
					  console.log( "error",err );
				  })
				  .always(function() {
					  console.log( "finished" );
				  }); */
	}
</script>
<style type="text/css">
#capTab {
	width: 99%;
	padding-left: 1%;
}

.capacityH {
	color: #2779aa;
	font-style: normal;
	font-weight: 600;
	font-family: Arial, San-Serif;
	font-size: 12px;
	background-color: #D2D2D2;
	text-align: center;
	height: 20px;
	padding-left: 5px;
}

.capacityDiv {
	color: #2779aa;
	font-style: normal;
	font-weight: 600;
	font-family: Arial, San-Serif;
	font-size: 14px;
	height: 20px;
	padding-left: 5px;
}

.capVal {
	font-size: 11px;
	color: black;
	display: table-cell;
	vertical-align: inherit;
}

.contentarea1 {
	text-align: 0;
	clear: both;
	width: 1349px !important;
	background: white;
	margin: 0 auto;
	text-align: left;
	border-radius: 5px;
	margin: 0 auto;
}
</style>
</head>
<body
	onload="loadTotalCap();checkData(1);showaddAditionalProjects();loadMap();">
	<s:form onsubmit="return validateHours()"
		id="resourceUtilizationformId" method="POST" theme="simple">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" class="contentarea1"
			style="background: white; height: 12px; width: 1170px;">
			<div
				style="margin: 0 auto; width: 1143px; background: white; height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.resource.timesheet" />
				</div>
			</div>
		</div>
		<div class="contentarea1">
			<DIV style="margin: 0 auto; width: 1118px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.resource.timesheet" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table>
						<tr>
							<td style="color: #2779aa;"><s:text name="pts.resource.name" />
								:</td>
							<td align="left" width="200">
								<!--<sj:autocompleter
                    list="employeeList" name="selectedEmployee"  theme="simple" id="employeeListId"
                    selectBox="true" selectBoxIcon="true"></sj:autocompleter>-->
								<s:property value="#session['fullName']" /> <s:hidden
									name="selectedEmployee" id="selectedEmployee" />
							</td>
							<td style="color: #2779aa;"><s:text
									name="pts.week.ending.date" /> :</td>
							<td align="left"><sj:datepicker displayFormat="mm/dd/yy"
									minDate="minStartDate" name="selectedDate" value="%{fromDate}"
									changeYear="true" buttonImage="../images/DatePicker.png"
									buttonImageOnly="true" onChangeTopics="datechange" /></td>
							<td style="padding-left: 20px;"><img id="loadingIndicator"
								src="../images/indicatorImg.gif" style="display: none" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				 <s:hidden id="removedIds" name="removedIds" />
				<s:hidden id="additionalProjects" name="additionalProjects" />
				<s:hidden id="removeAdditional" name="removeAdditional" />
				<s:hidden id="removedIdsactivity" name="removedIdsactivity" />
				<s:hidden id="removedTemplateIds" name="removedTemplateIds" />
				<s:hidden id="tempActivityCode" name="tempActivityCode" />
				<s:hidden id="tempType" name="tempType" />
				<s:hidden id="tempActType" name="tempActType" />
				<s:hidden id="selectedHiddenDate" name="selectedHiddenDate" />
				 <input type="hidden" id="userCapacity"/>
				<sj:div id="activityLogDivId">
					<jsp:include page="timesheetSummaryNew.jsp" />
				</sj:div>
				<div id="spacer10px">&nbsp;</div>
				<div style="padding-right: 5px; text-align: right;">
					<img src="../images/SaveTemp.png" type="image"
						onclick="clearErrorDiv();saveTimeSheetTemplate();" /> <img
						src="../images/Submit.png" type="image" id="submitTimeSheet"
						onclick="clearErrorDiv();saveTimeSheet();" />
				</div>
				<div id="spacer10px">&nbsp;</div>
			</div>
			<div id="spacer10px">&nbsp;</div>
			<%-- 		<!-- Capacity Planned  -->
          <div id='capTab' class='capacityDiv'>Projects to be charged for
          	the current month...</div>
          <table id='capTab'>
          	<thead>
          		<tr>
          			<th class='capacityH'><s:text name="pts.netwok.codes" /></th>
          			<th class='capacityH'><s:text name="pts.capacity.total" /></th>
          			<th class='capacityH'><s:text name="pts.charged.hours" /></th>
          			<th class='capacityH'><s:text name="pts.remaining.hours" /></th>
          		</tr>
          	</thead>
          	<tbody>
          		<s:iterator value="plannedCapacityList">
          			<tr>
          				<td class='capVal'><s:property value="appName" /></td>
          				<td class='capVal' align="center"><s:property
          						value="targetHrs" /></td>
          				<td class='capVal' align="center"><s:property
          						value="chargedHrs" /></td>
          				<td class='capVal' align="center"><s:property
          						value="remainingHrs" /></td>
          			</tr>
          		</s:iterator>
          	</tbody>
          </table> --%>
			<div id="spacer10px">&nbsp;</div>
			<div id="spacer10px">&nbsp;</div>
		</div>
		<s:include value="/jsp/footer.jsp"></s:include>
	</s:form>
</body>
</html>

