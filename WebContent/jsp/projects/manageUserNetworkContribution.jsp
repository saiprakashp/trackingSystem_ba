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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/struts/js/base/jquery.ui.datepicker.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jszip.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/FileSaver.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/myexcel.js"></script>
<script>





	var totalStoryPoints=50;
	var projectMap={};
	var releaseMap={}; 
	var ids=[];
	function showAllResourcesClick() {
		//document.getElementById("showAllResources").value=document.getElementById("showAllResources").checked;
		document.forms[0].method = "post";
		document.forms[0].action = "../projects/assignResources.action";
		document.forms[0].submit();
	}
	function loadProjects() {
		totalStoryPoints=parseInt(document.getElementById("totalStoryPoints").value);
		document.getElementById("loadingIndicator").style.display="block"
			$("#release").val('')
		var obj = {
				'project' : $("#project").val(),
				'showUserNwCapacity':"No"

		};
		var jqxhr = $.post('../utilization/getProjectsMapAjax.action', (obj),
				function(res) {
				if(res!=null && res.projectsMapObj!=null){
					projectMap= new Map(Object.entries(res.projectsMapObj)); ;
					Object.values(res.projectsMapObj).map(elem =>
					$("#projects").append("<option value='"+elem+"'>"));
				} }).fail(function(err) {
				 console.log(err)
		});		document.getElementById("loadingIndicator").style.display="none"
	}
	function loadRelease() {	
		if($("#project").val() === ''){
			$("#release").val('')
		}else{
		document.getElementById("loadingIndicator").style.display="block"
			$("#releases").empty();
		$("#release").val('')
		var obj = {
			'project' : $("#project").val(),
			'showUserNwCapacity':"No"
		};		
			var jqxhr = $.post('../utilization/getReleaseMapAjax.action', (obj),
					function(res) {
					if(res!=null && res.releasesMapObj!=null){
						releaseMap= res.releasesMapObj ;
					 	Object.values(res.releasesMapObj).map(elem=> $("#releases").append("<option value='"+elem+"'>"+elem+"</option>"));
					} 
					}).fail(function(err) {
					 console.log(err)
			});
			
			document.getElementById("loadingIndicator").style.display="none"
			}
		}
	
	function getUserNwCapacity(){
		document.getElementById("loadingIndicator").style.display="block"
			$("#contributionNW").empty();
		var obj = {
				'project' : $("#project").val(),
				'release' : $("#releases").val(),
				 'showUserNwCapacity':"Yes"
			};		
		var jqxhr = $.post('../projects/manageUserNetworkContributionAjax.action', (obj),
				function(res) {
				generatetable(res)
				checkAndLoadStableTeams()
				}).fail(function(err) {
				 console.log(err)
		});
		document.getElementById("loadingIndicator").style.display="none"
	}
	function checkAndLoadStableTeams(){
		if(document.getElementById("addStableToNwContribution").checked){
			stableIds = document.querySelectorAll('[id ^= "stableContrib_"]');
			Array.prototype.forEach.call(stableIds, callback);
			if(ids.length > 0){
				ids.forEach(data => {
					document.getElementById("ncStableContrib_"+data).value =document.getElementById("stableContrib_"+data).value;
					document.getElementById("diffContribution_"+data).value	= parseInt(document.getElementById("stableContrib_"+data).value) *totalStoryPoints /100;
				})
			}
		}
	}
	function generatetable(res){
		if(res!=null && res.networkGridModel!=null){
			if(res.networkGridModel.length <=0){
				alert('No Users assigned for this release')
			}else{
				document.getElementById("projectDiv").style.display="none";
				document.getElementById("gridDiv").style.display="grid";
			}
			totalStoryPoints=res.networkGridModel[0]['totalStoryPoints'];
			document.getElementById("totalStoryPoints").value=totalStoryPoints;
			Object.values(res.networkGridModel).map(elem=>{
				 	$("#contributionNW").append(
				 			"<tr>"+
				 			"<td><input id='id_"+elem.id+"' type='hidden' value='"+elem.id+"' /> "+
				 			"<input readonly='readonly' type='text' style='text-align: center;border: none;' value='"+elem.name+"' /></td> "+
				 			"<td><input id='nwId_"+elem.id+"' type='hidden' value='"+elem.nwId+"' /> "+
				 			"<input  readonly='readonly' type='text' style='text-align: center;border: none;' value='"+elem.networkCode+"'/></td>"+
				 			"<td><input onChange='updateStableContribution(this)' id='ncStableContrib_"+elem.id+"' type='text' style='width:35px !important;text-align: center;' value='"+elem.nccStableContribution+"'></input></td>"+
				 			"<td><input id='stableContrib_"+elem.id+"'  type='text' style='display:none;' value='"+elem.stableContribution+"'></input>"+
				 			"<input onChange='updateStableContribution(this)' id='pmStableContrib_"+elem.id+"' type='text' style='width:35px !important;text-align: center;' value='"+elem.pmStableContribution+"'></input></td>"+
				 			"<td><input id='userContrib_"+elem.id+"' disabled type='text' style='width:35px !important;text-align: center;' value='"+(elem.nccStableContribution -elem.pmStableContribution )+"'></input></td>"+
				 			"<td><input id='diffContribution_"+elem.id+"' disabled type='text' style='width:35px !important;text-align: center;' value='"+((elem.nccStableContribution*totalStoryPoints)/100)+"'></input></td>"+
							"</tr>"
				 			);  
				 		
				 	});
			
		 	}
		checkAndLoadStableTeams();
	}
	var data=[];
	
	function downloadExcel(data){
		   var excel = $JExcel.new("Calibri black 11 #000000");	
			
			excel.set( {sheet:0, value:"User Contributions" } );
		    excel.addSheet("Sheet 2");
			
			
			/*excel.set(0,8,1,15);		
			excel.set(0,8,2,13);		
			excel.set(0,7,3,"15+13");		
			excel.set(0,8,3,"=I2+I3");		

			
			var evenRow=excel.addStyle ( {
				border: "none,none,none,thin #333333"});

			var oddRow=excel.addStyle ( {
				fill: "#ECECEC" ,
				border: "none,none,none,thin #333333"}); 
			*/
			/*
			for (var i=1;i<50;i++) excel.set({row:i,style: i%2==0 ? evenRow: oddRow  });
			excel.set({row:3,value: 30  });
*/
			var headers=["Resource","Release","Contribution","Committed Story Points","Un-utilized Contribution","Contribution based on story points"];
			var formatHeader=excel.addStyle ( {
					fill: "#ECECEC" ,
					border: "thin #000000",
					font: "Calibri 12 #000000 B"});

			for (var i=0;i<headers.length;i++){
				excel.set(0,i,0,headers[i],formatHeader);
				excel.set(0,i,undefined,"auto");
			}
			
			
			// Now let's write some data
			var initDate = new Date(2000, 0, 1);
			var endDate = new Date(2016, 0, 1);
			var dateStyle = excel.addStyle ( { 
					align: "R",
					format: "yyyy.mm.dd hh:mm:ss",
					font: "#00AA00"});
		
	 
		Object.values(net).map((elem,i)=>{
			excel.set(0,0,i+1,elem.name);
			excel.set(0,1,i+1,elem.networkCode);
			excel.set(0,2,i+1,elem.nccStableContribution);
			excel.set(0,3,i+1,elem.pmStableContribution);
			excel.set(0,4,i+1,(elem.nccStableContribution -elem.pmStableContribution ));
			excel.set(0,5,i+1,((elem.nccStableContribution*totalStoryPoints)/100));
	 	});

		 excel.generate("UserContribution.xlsx");
	}
	
	function downloadUserNwContribution(){
		var obj = {
				'project' : $("#project").val(),
				'release' : $("#releases").val(),
				 'showUserNwCapacity':"Yes"
			};		
		var jqxhr = $.post('../projects/goDownloadUsernwContribution.action', (obj),
				function(res) {
			net=res.networkGridModel;
				 if(res.networkGridModel.length <=0){
						alert('No Users assigned for this release to download')
					}else{
						 downloadExcel(res.networkGridModel)
					}
				}).fail(function(err) {
				 console.log(err)
		});
	}
	
	function saveUserNwContribution(){
		document.getElementById("loadingIndicator").style.display="block";
		data=[];
		$("#grid2 tbody tr").each(function() {
			var tmpId=$(this).find('td').find('input').attr('id').split("_")[1];
			var obj={ 
					"id":$("#id_"+tmpId).val(),
					"nwId":$("#nwId_"+tmpId).val(),
					"nccStableContribution":$("#ncStableContrib_"+tmpId).val(),
					"stableContribution":$("#stableContrib_"+tmpId).val(),
					"pmStableContribution":$("#pmStableContrib_"+tmpId).val()
				};
				
			 data.push(obj)
			});
	
		var ajaxData = {"totalStoryPoints":parseInt(document.getElementById("totalStoryPoints").value),"assignDefNeContribution":document.getElementById("addStableToNwContribution").checked};
	    ajaxData["networkGridModel"] = data; 
		  $.ajax( {
	        "dataType": 'json',
	        "type": "POST", 
	         url: '../projects/addUserNetworkContributionAjax.action',
	        "data":  JSON.stringify(ajaxData),
	        contentType: "application/json; charset=utf-8",
	        async : false,
	        success: function (res) {
	           alert("Saved Succesfully");
	           getUserNwCapacity();
	        },
	        complete: function (msg,a,b) {
	      
	        },
	        error : function(msg,a,b){
	        	console.log(msg,a,b);
	        	 alert("Unabel to Save")
	        }
	    } );
		document.getElementById("loadingIndicator").style.display="none"
	}
	function showProjectData(){
		document.getElementById("projectDiv").style.display="table";
		document.getElementById("gridDiv").style.display="none";
	}
	function addStableToNwContribution(){
			 getUserNwCapacity();
	}
	function callback(element, iterator) {
		ids.push( element.id.split("_")[1]);
		 }
	function updateStableContribution(ref){
		document.getElementById("userContrib_"+ref.id.split("_")[1] ).value=
			 (parseInt(document.getElementById("ncStableContrib_"+ref.id.split("_")[1] ).value)-parseInt(document.getElementById("pmStableContrib_"+ref.id.split("_")[1] ).value ))  ;
	}

</script>
</head>
<body onload="loadProjects()">
	<div method="POST" id="manageNetworkCodeFormId">
		<jsp:include page="../header.jsp" />
		<jsp:include page="../menu.jsp" />
		<div id="breadcrumbDivParent" style="width: 100%; height: 22px;">
			<div
				style="margin: 0 auto; width: 1000px; background: url('../images/breadcrum_bg.png'); height: 22px; text-align: left; line-height: 22px;">
				<div id="breadcrumbDiv" style="margin-left: 25px; clear: both;">
					<s:a href="../login/showDashboard.action">
						<s:text name="pts.menu.home" />
					</s:a>
					>
					<s:text name="pts.menu.manage.network.user.stable.codes" />
				</div>
			</div>
		</div>
		<s:hidden name="id" id="ncId" />
		<div id="contentarea">
			<DIV style="margin: 0 auto; width: 980px;">
				<div class="titleContainer">
					<DIV id=spacer5px>&nbsp;</DIV>
					<s:div cssClass="pagetitle">
						<s:text name="pts.menu.manage.network.user.stable.codes" />
					</s:div>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<div id="spacer10px">&nbsp;</div>
				<div>
					<table id="projectDiv" style="margin: auto;">
						<tr>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.networkcode.pillar.name" />:
							</td>
							<td width="600"><input list="projects" name="project"
								style="width: 600px; font-size: 15px;" onchange="loadRelease()"
								id="project" /> <datalist id="projects">
								</datalist></td>
						</tr>
						<tr>
							<td style="color: #2779aa;" align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:text
									name="pts.netwok.code" />:
							</td>

							<td width="600"><select name="release" id="releases"
								style="width: 100%; font-size: 15px;" size="15">

							</select></td>

							<td><img id="loadingIndicator"
								src="../images/indicatorImg.gif" style="display: none" /></td>
							<td style="padding-left: 20px;"><img src="../images/go.png"
								onclick="getUserNwCapacity()" /></td>
						</tr>
					</table>
				</div>
				<div id="spacer10px">&nbsp;</div>
				<p style="color: red;" id="message"></p>
				<div id="spacer10px">&nbsp;</div>
				<div id="gridDiv" style="display: none;">
					<table>
						<tbody>
							<tr>
								<td><label for="totalStoryPoints">Total Story
										Points</label> <input type="text" value="50" name="totalStoryPoints"
									id="totalStoryPoints" /></td>
								<td></td>
								<td><p
										style="text-align: right !important; margin-right: 11%;">
										Get Stable Contributions <input
											onClick="addStableToNwContribution()" type="checkbox"
											id="addStableToNwContribution" />
									</p></td>
							</tr>
						</tbody>
					</table>
					<div
						style="border-bottom: solid 1px black; overflow-y: auto; height: 400px;">
						<div id="spacer1px">&nbsp;</div>
						<table id="grid2"
							style="width: 95%; margin: auto; text-align: center; border: 1px solid">
							<thead>
								<tr style="background-color: #cdcdcd;">
									<th>Resource</th>
									<th>Release</th>
									<th>Contribution</th>
									<th>Committed Story <br /> Points
									</th>
									<th>Un-utilized <br /> Contribution
									</th>
									<th>Contribution based <br /> on story points
									</th>
								</tr>
							</thead>
							<tbody id="contributionNW">
							</tbody>
							<tfoot>
							</tfoot>
						</table>
					</div>
					<div style="margin-top: 1%;">
						<button
							style="float: left; padding: 3px; margin-left: 2px; background-color: #87C1FF; color: black; font-weight: bold; font-size: 13px;"
							onclick="showProjectData()">Go Back</button>
						<div style="float: right;">
							<button
								style="padding: 3px; margin-right: 2px; background-color: #87C1FF; color: black; font-weight: bold; font-size: 13px;"
								onclick="downloadUserNwContribution()">Download</button>
							<button
								style="padding: 3px; margin-right: 2px; background-color: #87C1FF; color: black; font-weight: bold; font-size: 13px;"
								onclick="saveUserNwContribution()">Submit</button>
						</div>
					</div>

				</div>
				<s:div id="spacer10px">&nbsp;</s:div>
				<s:div id="spacer10px">&nbsp;</s:div>
			</div>
			<s:include value="/jsp/footer.jsp"></s:include>
		</div>
	</div>
</body>
</html>