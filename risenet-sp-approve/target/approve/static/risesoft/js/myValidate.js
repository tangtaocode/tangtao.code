///////////////////////////////////////////////////////////////////////
//                          实现jsp验证                                                             //
//                                                                   //
//                                                                   //
//                                                                   //
//                                                                   //
///////////////////////////////////////////////////////////////////////

	//验证超链接地址格式
	function isURL(str_url){
		var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
		 + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@  
		 + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184  
		 + "|" // 允许IP和DOMAIN（域名） 
		 + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.  
		 + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名  
		 + "[a-z]{2,6})" // first level domain- .com or .museum  
		 + "(:[0-9]{1,4})?" // 端口- :80  
		 + "((/?)|" // a slash isn't required if there is no file name  
		 + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";  
		var re=new RegExp(strRegex);  
		//re.test() 
		if (re.test(str_url)){ 
			return true;  
		}else{
			return false;  
		} 
	}
	
	//验证邮箱格式
	function isEmail(str) {
        var re = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if (re.test(str)){
			return true;
		}else{
			return false;
		}
    }
	
	//验证数字长度是否为指定长度
	function checkNumberLength(num,length)
	{
		if(num.length != length)
		{
			return false;
		}else
		{
			return true;
		}
	}
	
	//验证是否是数字
	function isNumber(num){
		var reg = new RegExp("^[0-9]*$");
		if(reg.test(num)){
			return true;
		}else
		{
			return false;
		}
	}
	
	//验证是否为空
	function isNull(str)
	{
		if(str=="")
		{
			return false;
		}else
		{
			return true;
		}
	}

