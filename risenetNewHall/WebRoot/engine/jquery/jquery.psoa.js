$.extend($.fn.datagrid.defaults.editors, {
    calendar: {
        init: function(container, options){
            var input = $('<input type="text" class="datagrid-editable-input" onclick=calendar() size=10>').appendTo(container);
            return input;
        },
        getValue: function(target){
            return $(target).val();
        },
        setValue: function(target, value){
            $(target).val(value);
        }
    }
});

$.extend($.fn.datagrid.defaults.editors, {
    selectLD: {
        init: function(container, options){
            var input = $('<input type="text" class="datagrid-editable-input" readonly onclick=selectCLB_LD(this) size=10>').appendTo(container);
            return input;
        },
        getValue: function(target){
            return $(target).val().replace(new RegExp(' ','gm'),'&nbsp;');
        },
        setValue: function(target, value){
            $(target).val(value.replace(new RegExp('&nbsp;','gm'),' '));
        }
    }
});

var ldinput;
function selectCLB_LD(input){
	ldinput=input;
	$("#ldSelect").empty();
	$("#clb_ldWin").window("open");
	var value=input.value;
	value=value.replace(new RegExp(' ','gm'),'');
	value=value.replace('@','');
	var url="../default/selectCLB_LD.jsp?value="+value;
	$("#ldSelect").load(url);
}


function ok(){
	var values='';
	var vs=$("#clb_ldWin input:text");
	for(i=0;i<9;i++){
		var temp=vs[i]
		vs[i]=vs[i+9]
		vs[i+9]=temp;
	}	
	var allBlank=true;
	vs.each(function(){
		allBlank=allBlank&&this.value=='';
		values+=','+(this.value==''?'blank':this.value);
	});

	values=allBlank?'                               @'+values.substr(1):values.substr(1);
	$(ldinput).val(values);
	ldclose();
}

function ldclose(){
	$("#clb_ldWin").window("close");
}