$(function(){
    $("#icon1").mouseover(function(){
        $("#icon1").animate({
            width: "120px",
            height: "121px"
        })
    });
    $("#icon1").mouseout(function(){
        $("#icon1").animate({
            width: "100px",
            height: "101px"
        })
    });
    $("#icon2").mouseover(function(){
        $("#icon2").animate({
            width: "120px",
            height: "121px"
        })
    });
    $("#icon2").mouseout(function(){
        $("#icon2").animate({
            width: "100px",
            height: "101px"
        })
    });
    $("#icon4").mouseover(function(){
        $("#icon4").animate({
            width: "120px",
            height: "121px"
        })
    });
    $("#icon4").mouseout(function(){
        $("#icon4").animate({
            width: "100px",
            height: "101px"
        })
    });
    $("#icon5").mouseover(function(){
        $("#icon5").animate({
            width: "120px",
            height: "121px"
        })
    });
    $("#icon5").mouseout(function(){
        $("#icon5").animate({
            width: "100px",
            height: "101px"
        })
    });
    $("#goto_gd").mouseover(function(){
        $("#goto_gd").removeClass("index_gd_home");
        $("#goto_gd").addClass("index_gd")
    });
    $("#goto_gd").mouseout(function(){
        $("#goto_gd").removeClass("index_gd");
        $("#goto_gd").addClass("index_gd_home")
    });
    
    $('.a_home').click(function(){
        $('.map_box').fadeIn(200);
    });
    $('.map_exit').click(function(){
        $('.map_box').fadeOut(200);
    });
    
});

//�������ʾһ���㣬�ò���ڿ�Ϊdiv2������ 
function showData(){
    var showdata = $("#start_item"); //��Ҫ�����Ĳ� 
    if (showdata.html()) {
        showdata.css("display", ""); //div1��ʼ״̬�ǲ��ɼ��ģ����ÿ�Ϊ�ɼ� 
        showdata.css("z-index", "999999");
        //window.event�����¼�״̬�����¼�������Ԫ�أ�����״̬�����λ�ú���갴ť״. 
        //clientX�����ָ��λ������ڴ��ڿͻ������ x ���꣬���пͻ����򲻰�����������Ŀؼ��͹������� 
        showdata.css("left", event.clientX + 20); //���Ŀǰ��X���ϵ�λ�ã���10��Ϊ�����ұ��ƶ�10��px���㿴������
        showdata.css("top", parseInt(event.clientY) - $("#start_item").height() / 2);
        showdata.css("position", "absolute");//����ָ��������ԣ�������޷�������궯 
    }
}

//�رղ�div1����ʾ 
function closeData(){
    $("#start_item").css("display", "none");
}

function fc(id){
    $.post("/home/findCommunity.html", {
        "streetGuid": id
    }, function(data){
        $("#start_item").html(data);
        showData();
    });
}

function chImg(type){
    $("#streetImage").attr("src", "/images/home/street/" + type + ".png");
}

function bcImg(){
    $("#streetImage").attr("src", "/images/home/street/street.png");
}

function MM_findObj(n, d){ //v4.01
    var p, i, x;
    if (!d) 
        d = document;
    if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
        d = parent.frames[n.substring(p + 1)].document;
        n = n.substring(0, p);
    }
    if (!(x = d[n]) && d.all) 
        x = d.all[n];
    for (i = 0; !x && i < d.forms.length; i++) 
        x = d.forms[i][n];
    for (i = 0; !x && d.layers && i < d.layers.length; i++) 
        x = MM_findObj(n, d.layers[i].document);
    if (!x && d.getElementById) 
        x = d.getElementById(n);
    return x;
}

function MM_preloadImages(){ //v3.0
    var d = document;
    if (d.images) {
        if (!d.MM_p) 
            d.MM_p = new Array();
        var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
        for (i = 0; i < a.length; i++) 
            if (a[i].indexOf("#") != 0) {
                d.MM_p[j] = new Image;
                d.MM_p[j++].src = a[i];
            }
    }
}

function MM_swapImgRestore(){ //v3.0
    var i, x, a = document.MM_sr;
    for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++) 
        x.src = x.oSrc;
}

function MM_swapImage(){ //v3.0
    var i, j = 0, x, a = MM_swapImage.arguments;
    document.MM_sr = new Array;
    for (i = 0; i < (a.length - 2); i += 3) 
        if ((x = MM_findObj(a[i])) != null) {
            document.MM_sr[j++] = x;
            if (!x.oSrc) 
                x.oSrc = x.src;
            x.src = a[i + 2];
        }
}

$(document).ready(function(){
	MM_preloadImages('/images/home/street/street.png','/images/home/street/sg.png','/images/home/street/qsh.png','/images/home/street/nh.png','/images/home/street/lt.png','/images/home/street/hb.png','/images/home/street/gy.png','/images/home/street/dx.png','/images/home/street/dm.png','/images/home/street/dh.png','/images/home/street/cz.png');
});
