/**
 * �˵���ϵͳ����
 */
function rate(obj, oEvent) {
        //==================  
        // ͼƬ��ַ����  
        //==================  
        var imgSrc = '/images/share/star_2.png'; //û����ɫ������
        var imgSrc_2 = '/images/share/star_1.png'; //��ֺ�����ɫ������
        //---------------------------------------------------------------------------  
        if (obj.rateFlag) return;
        var e = oEvent || window.event;
        var target = e.target || e.srcElement;
        var imgArray = obj.getElementsByTagName("img");
        for (var i = 0; i < imgArray.length; i++) {
            imgArray[i]._num = i;
            imgArray[i].onclick = function () {
                if (obj.rateFlag) return;
                obj.rateFlag = true;
                $.post("/home/starRrating.html",{'appraise':this._num + 1},function(data){
                	showInfo(data.message);
                });
            };
        }
        if (target.tagName == "IMG") {
            for (var j = 0; j < imgArray.length; j++) {
                if (j <= target._num) {
                    imgArray[j].src = imgSrc_2;
                } else {
                    imgArray[j].src = imgSrc;
                }
            }
        } else {
            for (var k = 0; k < imgArray.length; k++) {
                imgArray[k].src = imgSrc;
            }
        }
    }