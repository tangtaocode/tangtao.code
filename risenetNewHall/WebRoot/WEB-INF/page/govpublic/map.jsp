<%@ page contentType="text/html; charset=gb2312" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!--���ðٶȵ�ͼAPI-->
<title>XXXX�ֵ���λ��</title>
<style type="text/css">
    html,body{margin:0;padding:0;}
    .iw_poi_title {color:#CC5522;font-size:14px;font-weight:bold;overflow:hidden;padding-right:13px;white-space:nowrap}
    .iw_poi_content {font:12px arial,sans-serif;overflow:visible;padding-top:4px;white-space:-moz-pre-wrap;word-wrap:break-word}
</style>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>

</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="right_table">
          <tr><td colspan="4"><div style="width:990px;height:590px;border:#ccc solid 1px;" id="dituContent"></div> </td></tr>
          </table>
  <!--�ٶȵ�ͼ����-->
  
</body>
<script type="text/javascript">
	//����ȫ��MAP
	var map = new BMap.Map("dituContent");//�ڰٶȵ�ͼ�����д���һ����ͼ
    //�����ͳ�ʼ����ͼ������
    function initMap(x,y,info){
        createMap(x,y,info);//������ͼ
        setMapEvent();//���õ�ͼ�¼�
        addMapControl();//���ͼ��ӿؼ�
    }
    
    //������ͼ������
    function createMap(x,y,info){
        var point = new BMap.Point(x,y);//����һ�����ĵ�����
        map.setCurrentCity("����");          // ���õ�ͼ��ʾ�ĳ��� �����Ǳ������õ�
        map.centerAndZoom(point,29);//�趨��ͼ�����ĵ�����겢����ͼ��ʾ�ڵ�ͼ������
        var marker = new BMap.Marker(point);  // ������ע
		map.addOverlay(marker);              // ����ע��ӵ���ͼ��
		var infoWindow = new BMap.InfoWindow(info);
		marker.addEventListener("onclick", function(){this.openInfoWindow(infoWindow);});
		//map.openInfoWindow(infoWindow, map.getCenter());      
        map.addControl(new BMap.MapTypeControl());          //��ӵ�ͼ���Ϳؼ�
        window.map = map;//��map�����洢��ȫ��
    }
    
    //��ͼ�¼����ú�����
    function setMapEvent(){
        map.enableDragging();//���õ�ͼ��ק�¼���Ĭ������(�ɲ�д)
        map.enableScrollWheelZoom();//���õ�ͼ���ַŴ���С
        map.enableDoubleClickZoom();//�������˫���Ŵ�Ĭ������(�ɲ�д)
        map.enableKeyboard();//���ü����������Ҽ��ƶ���ͼ
    }
    
    //��ͼ�ؼ���Ӻ�����
    function addMapControl(){
        //���ͼ��������ſؼ�
	var ctrl_nav = new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE});
		map.addControl(ctrl_nav);
        //���ͼ���������ͼ�ؼ�
	var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:0});
		map.addControl(ctrl_ove);
        //���ͼ����ӱ����߿ؼ�
	var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
		map.addControl(ctrl_sca);
    }
    
    initMap(114.138089,22.554639,"<p style='font-size:14px;'>�޺��������������</p><p style='font-size:14px;'>��ַ���޺����Ľ���·1008��һ¥</p>"+
    "<p style='font-size:14px;'>�˳�·�ߣ� 2��27��29��40��83��104��111��&nbsp;&nbsp;&nbsp;&nbsp;<br/>214��229��242��312��363��372��������·&nbsp;&nbsp;&nbsp;&nbsp;<br/>�������������޺���ί��վ�³�</p>");//�����ͳ�ʼ����ͼ
    //��ͼ��ѯ
</script>
</html>