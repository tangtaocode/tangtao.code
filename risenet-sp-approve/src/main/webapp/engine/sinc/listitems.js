/**
    calculate for line
**/
function LineFormula(expression, precision, fireEventName)
{
    var objNames = [];
    this.dataLines = 0;
    this.name = "__script1";
    this.sum = [];
	this.isShowByThousand = false;
    this.expression = expression;
    this.parseExpression = [];
    this.fireEventName= "onblur";
    if(precision > 10)
    {
	    precision = precision-10;
		this.isShowByThousand = true;
	}

    this.precision = precision;
	if(fireEventName != null) this.fireEventName = fireEventName;    

    this.addSum = function(sumObj)
    {
        this.sum[this.sum.length] = sumObj;
    }

    this.getObjectValue = function(name, index)
    {
        if(document.getElementsByName(name) == null || document.getElementsByName(name).length == 0) 
        {
            throw "error";
        }
        var _value0 = document.getElementsByName(name)[index].value;
        var _value = _value0.replace(/[^0-9\.-]/g, "");

        if(_value0 != _value)
        {
            document.getElementsByName(name)[index].value = _value;
        }

        return (_value=="")?"0":_value;
    }
    
    this.round = function (digit, how)  
    {  
        var _digit = new String(Math.round(digit*Math.pow(10, how))/Math.pow(10, how));  
        var pos = _digit.lastIndexOf(".");
		
		if(how == 0)
        {
             if(pos !=-1)
             {
                 return _digit.substing(0, pos)
             }
             return _digit;
        }
        if(pos !=-1)
        {
            if((_digit.length-pos-1)<how)
            {
                _digit = _digit + this.multiChar("0", how-(_digit.length-pos-1));
            }
        }
        else
        {
            _digit = _digit + "." + this.multiChar("0", how);
        }

        return  _digit;  
    }

    this.multiChar = function (_char, num)
    {
        var chars = "";
        for(var i=0; i<num; i++)
        {
            chars = chars + _char;
        }
        return chars;
    }
    
    this.addObjName = function(objName)
    {
        var isExist = false;
        for(var i=0; i<objNames.length; i++)
        {
            if(objNames[i] == objName){isExist = true; break;}
        }
        if(!isExist) objNames[objNames.length] = objName;
    }

    this.parse = function(expression)
    {
        var pos1;
        var pos = expression.indexOf("=");
        if(pos == -1) return;
        var leftExp = expression.substring(0, pos+1);
        var rightExp = expression.substring(pos+1);
        
        //parse the name
        pos = rightExp.indexOf("FD[");
        while(pos != -1)
        {
            var pos1 = rightExp.indexOf("]", pos+3)
            var oName = rightExp.substring(pos+3, pos1);
            this.addObjName(oName);
            pos = rightExp.indexOf("FD[", pos1);
        }

        leftExp = leftExp.replace(/FD\[/, "document.getElementsByName(\"").replace(/\]/, "\")[_x].value");
        rightExp = rightExp.replace(/FD\[/g, "parseFloat(this.getObjectValue(\"").replace(/\]/g, "\", _x))");
        var _precision = this.precision;
        var _pos = -1;
        if((_pos=rightExp.indexOf("#")) != -1)
        {
            _precision = parseInt(rightExp.substring(_pos+1))
            rightExp = rightExp.substring(0, _pos);
        }
        if(_precision != null) 
	    {
            if(this.isShowByThousand)
            {
                rightExp = "this.formatThousandNumber(this.round(" + rightExp + "," + _precision + "))";
            }
            else
            {
                rightExp = "this.round(" + rightExp + "," + _precision + ")";
            }
        }
        else
        {
            if(this.isShowByThousand)
            {
                rightExp = "this.formatThousandNumber(" + rightExp +  ")";
            }
        }

        return leftExp + rightExp;
    }

    this.init = function()
    {
        var arr = this.expression.split(";");
        for(var i=0; i<arr.length; i++)
        {
            this.parseExpression[this.parseExpression.length] = this.parse(arr[i]);
        }
        //get all Names
        this.dataLines = document.getElementsByName(objNames[0]).length;
        for(var i=0; i<this.dataLines; i++)
        {
            for(var j=0; j<objNames.length; j++) this.addEvent(objNames[j], i);
        }
    }
    
    this.calculate = function(_x, isSum)
    {
        //1¡¢valid
        var _value;

        //2¡¢get the result
        for(var i=0; i<this.parseExpression.length; i++)
        {
            try
            {
                eval(this.parseExpression[i]);
            }
            catch(e){}
        }
        //3 do sum
        if(!isSum) return;
        if(this.sum.length != 0)
        {
            for(var i=0; i<this.sum.length; i++)
            {
                this.sum[i].calculate();
            }
        }
    }
    
    this.calculateAll = function()
    {
        this.dataLines = document.getElementsByName(objNames[0]).length;
        for(var i=0; i<this.dataLines; i++)
        {
            this.calculate(i);
        }
        if(this.sum.length != 0)
        {
            for(var i=0; i<this.sum.length; i++)
            {
                this.sum[i].calculate();
            }
        }
        
    }

    this.addEvent = function(name, index)
    {
        _x = index;
		if(document.getElementsByName(name) == null || document.getElementsByName(name).length == 0) return;
        var _obj = eval("window." + this.name);
        if(_obj == null) _obj = this;
        eval("document.getElementsByName(name)[index]." + this.fireEventName + "= function(){_obj.calculate(index, true)}");
    }

    this.formatThousandNumber = function(value)
    {
        var bufValue = "";
        var isNegative = value.charAt(0) == "-";
        if(isNegative) value = value.substring(1);
        var pos = value.indexOf(".");
        var strValue = null;
        var tail = null;

        if(pos == -1)
        {
            strValue = value;
            tail = "";
        }
        else
        {
            strValue = value.substring(0, pos);
            tail = value.substring(pos);
        }
        var m = strValue.length;

        if(m > 3)
        {
            for(var i=m; i>0; i--)
            {
                if(i>=3 && i<m && (i%3) == 0) bufValue = bufValue + ",";
                bufValue = bufValue + strValue.charAt(m-i);
            }
            value = bufValue + tail;
        }

        return isNegative?"-"+value:value;
    }
}

function sumFormula(source, target, precision, targetAttr)
{
    this.source = source;
    this.target = target;
    this.targetAttr = targetAttr;
    this.fn = new LineFormula();
	  this.isShowByThousand = false;
    if(precision > 10)
    {
        precision = precision-10;
        this.isShowByThousand = true;
	  }
    this.precision = precision;

    this.calculate = function()
    {
        var oos = document.getElementsByName(this.source);
        var sum = 0;
        for(var i=0; i<oos.length; i++)
        {
            var _value = oos[i].value.replace(/[^0-9\.-]/g, "");
            if(_value == "") continue;
            sum += parseFloat(_value);
        }
        var tg = "document.getElementsByName(this.target)[0]";
        if(this.targetAttr == null) this.targetAttr = "value";
        tg = tg + "." + this.targetAttr + "=";
        if(this.precision != null)
        {
            tg = tg + ((this.isShowByThousand)?"this.formatThousandNumber(":"") + "this.fn.round(" + sum +"," + this.precision + ")" + ((this.isShowByThousand)?")":"");
        }
        else
        {
            tg = tg + ((this.isShowByThousand)?"this.formatThousandNumber(":"") + sum + ((this.isShowByThousand)?")":"");
        }
        eval(tg);
        try{_fireCalEvent()}catch(e){}
    }

    this.formatThousandNumber = function(value)
    {
        var bufValue = "";
        var isNegative = value.charAt(0) == "-";
        if(isNegative) value = value.substring(1);
        var pos = value.indexOf(".");
        var strValue = null;
        var tail = null;

        if(pos == -1)
        {
            strValue = value;
            tail = "";
        }
        else
        {
            strValue = value.substring(0, pos);
            tail = value.substring(pos);
        }
        var m = strValue.length;

        if(m > 3)
        {
            for(var i=m; i>0; i--)
            {
                if(i>=3 && i<m && (i%3) == 0) bufValue = bufValue + ",";
                      bufValue = bufValue + strValue.charAt(m-i);
            }

            value = bufValue + tail;
        }

        return isNegative?"-"+value:value;
    }
}