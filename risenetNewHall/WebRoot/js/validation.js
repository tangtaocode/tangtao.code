//限制输入框输入的数字，numberType数字类型,1,整数，2小数;
//decimal长度，类型为整数时计算整数部分，类型为小数时计算小数部分
function restrictNumber(obj,numberType,decimal){
		obj.value = obj.value.replace(/[^\d.]/g,"");
		obj.value = obj.value.replace(/^\./g,"");
		obj.value = obj.value.replace(/\.{2,}/g,"");
		if(numberType=="2"){
			if(obj.value.split(".").length-1>1){
			  obj.value=obj.value.substring(0,obj.value.length-1);
			}
			if(obj.value.indexOf(".")>-1&&obj.value.split(".")[1].length>decimal){
			obj.value=obj.value.substring(0,obj.value.indexOf(".")+(decimal+1));
			}
		}else{
			obj.value=obj.value.substring(0,decimal);
		}
}
function validations(ids){
	var $tags = $("#"+ids+" input");
	var $arx = $("#"+ids+" textarea");
	for (var i = 0; i < $arx.length; i++) {
		$tags.push($arx[i]);
	}
	var $sele = $("#"+ids+" select");
	for(var i=0;i<$tags.length;i++){
		var vfy = $tags[i].getAttribute("verify");
		var inputType = $tags[i].getAttribute("type");
		var value = $tags[i].value;
		if(value){
			value = (""+value).replace(/(^\s*)|(\s*$)/g,"");
		}
		if(vfy){
			var verifys = vfy.split("\|");
			var name = verifys[0];
			var isNull = verifys[1];
			var otherCheck = verifys[2];
			if(inputType=="text"){
				if(isNull=="NotNull"&&value==""){
					Dialog.alert(name+":不能为空",function(){
						changeInput($tags[i]);
					});
					return false;
				}
				if(otherCheck=="email"&&value!=""){
					var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
					if(!reg.test(value)){
 						Dialog.alert(name+":格式错误，请输入例如：example@exp.com",function(){
							changeInput($tags[i]);
						});
						return false;
					}
				}
				if(otherCheck=="phone"&&value!=""){
					var reg =  /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
					if(!reg.exec(value)){
 						Dialog.alert(name+":格式错误,请重新输入",function(){
							changeInput($tags[i]);
						});
						return false;
					}
				}
				if(otherCheck=="telephone"&&value!=""){
					var re = '[^0-9-]{1,}';
					if(value.match(re)!=null){
 						Dialog.alert(name+":输入不正确,请输入例如：0755-25668888或者25668888",function(){
							changeInput($tags[i]);
						});
						return false;
					}
				}
			}else{
				var areId = $tags[i].id;
				if(areId=="affirms"){
					if(isNull=="NotNull"&&isValueNull()){
					Dialog.alert(name+":不能为空",function(){
						changeInput($tags[i]);
					});
					return false;
					}
				}else{
					if(isNull=="NotNull"&&value==""){
					Dialog.alert(name+":不能为空",function(){
						changeInput1($tags[i]);
					});
					return false;
					}
				}
				
			}
		}
	}
	for(var i=0;i<$sele.length;i++){
		var vfy = $sele[i].getAttribute("verify");
		var value = $sele[i].value;
		if(vfy){
			var verifys = vfy.split("\|");
			var name = verifys[0];
			var isNull = verifys[1];
			if(isNull=="NotNull"&&value==""){
					Dialog.alert(name+":不能为空");
					return false;
			}
		}
	}
	return true;
}
function changeInput(obj){
	obj.focus();
	obj.setAttribute("class","errorInput");
	obj.setAttribute("onchange","setClass(this)");	
}
function changeInput1(obj){
	obj.focus();
	obj.setAttribute("class","errorInput1");
	obj.setAttribute("onchange","setClass1(this)");	
}
function setClass(obj){
	obj.setAttribute("class","zc_input02");
}
function setClass1(obj){
	obj.setAttribute("class","zc_textarea");
}
function checkRadio(name,mess){
	var item = $("input[name='"+name+"']:checked").val();
	if(!item){
		Dialog.alert("请选择："+mess);
		return false;
	}
	return true;
}


	/**检验身份证号码是否合法
		15位身份证号码组成：
        ddddddyymmddxxs共15位，其中：
        dddddd为6位的地方代码，根据这6位可以获得该身份证号所在地。
        yy为2位的年份代码，是身份证持有人的出身年份。
        mm为2位的月份代码，是身份证持有人的出身月份。
        dd为2位的日期代码，是身份证持有人的出身日。
                      这6位在一起组成了身份证持有人的出生日期。
        xx为2位的顺序码，这个是随机数。
        s为1位的性别代码，奇数代表男性，偶数代表女性。		
		18位身份证号码组成：ddddddyyyymmddxxsp共18位，其中：
                      其他部分都和15位的相同。年份代码由原来的2位升级到4位。最后一位为校验位。
                      校验规则是：
                        （1）十七位数字本体码加权求和公式
           S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
           Ai:表示第i位置上的身份证号码数字值
           Wi:表示第i位置上的加权因子
           Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
                       （2）计算模
           Y = mod(S, 11)
                       （3）通过模得到对应的校验码
           Y: 0 1 2 3 4 5 6 7 8 9 10
                             校验码: 1 0 X 9 8 7 6 5 4 3 2
                            也就是说，如果得到余数为1则最后的校验位p应该为对应的0.如果校验位不是，则该身份证号码不正确。
		**/
		function isValidIdCard(idCard){
     	   var w=[7 ,9,10,5,8,4,2, 1,6, 3, 7, 9 ,10, 5 ,8 ,4, 2];     	   
		   if(idCard.length == 18){
		     //身份证号码长度必须为18，只要校验位正确就算合法
		      var crc=idCard.substring(17);
		      var a =new Array();
		      var sum=0;
		      for(var i=0;i<17;i++){
		        a.push(idCard.substring(i,i+1));
		        sum+=parseInt(a[i],10)*parseInt(w[i],10);
		      }
		      sum%=11;
		      var res="-1";
		      switch (sum){
		      case 0:{
		        res="1";
		        break;
		      }
		      case 1:{ 
			  		res="0";
		            break;
		                   }
		      case 2:{
		            res="X";
		            break;
		                   }
		      case 3:{
		                   res="9";
		                   break;
		                   }
		      case 4:{
		                   res="8";
		                   break;
		                   }
		      case 5:{
		                   res="7";
		                   break;
		                   }
		      case 6:{
		                   res="6";
		                   break;
		                   } 
		       case 7:{
		                   res="5";
		                   break;
		                   } 
		       case 8:{
		                   res="4";
		                   break;
		                   } 
		        case 9:{
		                   res="3";
		                   break;
		                   }
		         case 10:{
		                   res="2";
		                   break;
		                   }
		      }
		      if(crc.toLowerCase()==res.toLowerCase()){
		         return true; 
		      }else{
			  	 return false; 
			  }
		   }else if(idCard.length == 15){
		        //15位的身份证号，只验证是否全为数字
		        var pattern = /\d/;
		        return pattern.test(idCard); 
		   }
		  return false;	   
		}	
		
		
		/**
		*验证组织机构代码是否合法：组织机构代码为8位数字或者拉丁字母+“-”+1位校验码。
		*验证最后那位校验码是否与根据公式计算的结果相符。
		*编码规则请参看
		*http://wenku.baidu.com/view/d615800216fc700abb68fc35.html
		*/
		function isValidOrgCode(orgCode){
		    var codeVal = ["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
		    var intVal =    [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35];
		    var crcs =[3,7,9,10,5,8,4,2];
		    if(orgCode.length==10){
		       var sum=0;
		       for(var i=0;i<8;i++){
		          var codeI=orgCode.substring(i,i+1);
		          var valI=-1;
		          for(var j=0;j<codeVal.length;j++){
		              if(codeI==codeVal[j]){
		                 valI=intVal[j];
		                 break;
		              }
		          }
		          sum+=valI*crcs[i];
		       }
		       var crc=11- (sum %  11);
		       switch (crc){
		       case 10:{
		         	crc="X";
		        	break;
		        }default:{
		        	break;
		        }
		       }
		       if(crc==orgCode.substring(9)){
		       		return true;
		       }
		    }else{
				return false;
			}
		}
		
       /**
		*验证营业执照是否合法：营业执照长度须为15位数字，前14位为顺序码，
		*最后一位为根据GB/T 17710 1999(ISO 7064:1993)的混合系统校验位生成算法
		*计算得出。此方法即是根据此算法来验证最后一位校验位是否政正确。如果
		*最后一位校验位不正确，则认为此营业执照号不正确(不符合编码规则)。
		*以下说明来自于网络:
		*我国现行的营业执照上的注册号都是15位的，不存在13位的，从07年开始国
		*家进行了全面的注册号升级就全部都是15位的了，如果你看见的是13位的注
		*册号那肯定是假的。
		*15位数字的含义，代码结构工商注册号由14位数字本体码和1位数字校验码
		*组成，其中本体码从左至右依次为：6位首次登记机关码、8位顺序码。　　
　　  *   一、前六位代表的是工商行政管理机关的代码，国家工商行政管理总局用
        *           “100000”表示，省级、地市级、区县级登记机关代码分别使用6位行
        *             政区划代码表示。设立在经济技术开发区、高新技术开发区和保税区
        *             的工商行政管理机关（县级或县级以上）或者各类专业分局应由批准
        *             设立的上级机关统一赋予工商行政管理机关代码，并报国家工商行政
        *             管理总局信息化管理部门备案。
　　  *   二、顺序码是7-14位，顺序码指工商行政管理机关在其管辖范围内按照先
        *             后次序为申请登记注册的市场主体所分配的顺序号。为了便于管理和
        *              赋码，8位顺序码中的第1位（自左至右）采用以下分配规则：
　　  *　　          1）内资各类企业使用“0”、“1”、“2”、“3”；
　　  *　　          2）外资企业使用“4”、“5”；
　　  *　　          3）个体工商户使用“6”、“7”、“8”、“9”。　　
　　  *   顺序码是系统根据企业性质情况自动生成的。　　
　　  *三、校验码是最后一位，校验码用于检验本体码的正确性
		*/
		function isValidBusCode(busCode){
		  if(busCode.length==15){
		    var sum=0;
                    var s=[];
                    var p=[];
                    var a=[];
                    var m=10;
                    p[0]=m;
		    for(var i=0;i<busCode.length;i++){
		       a[i]=parseInt(busCode.substring(i,i+1),m);
                       s[i]=(p[i]%(m+1))+a[i];
                       if(0==s[i]%m){
                         p[i+1]=10*2;
                       }else{
                         p[i+1]=(s[i]%m)*2;
                        }    
		    }                                       
		    if(1==(s[14]%m)){
		       //营业执照编号正确!
		        return true;
		    }else{
		       //营业执照编号错误!
                return  false;
             }
		  }else{
		  	return false;
		  }
		}
		/**
		*验证固定电话号码。固定电话号码格式为：区号-号码
		*/
		function isValidPhone(phone){
		  if(""==phone || (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?/.test(phone))){
		      return true;
		  }
		  return false;
		}