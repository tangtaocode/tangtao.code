
function selectTab(tab) {
	$("#table_content_fr").toggle();
	$("#type").toggle();
	$("#tab_list").removeClass();
	$("#tab_type").removeClass();
	if(tab=='list'){
		$("#tab_list").addClass("tabon");
		$("#tab_type").addClass("taboff");
	}else{
		$("#tab_type").addClass("tabon");
		$("#tab_list").addClass("taboff");
	}
	
}
