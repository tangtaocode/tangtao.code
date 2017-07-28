var tableDataMap;
var inputDataMap;
var checkBoxDataMap;
var inputTagMap;
var checkBoxTagMap;
var tableTagMap;

//处理Word文件的主体程序
function runMain(url)
{
    init();
    var objWord = new ActiveXObject("Word.Application");
    var wdReplaceAll = 2;
    var objDoc = objWord.Documents.Open(url);
    var objSelection = objWord.ActiveDocument.Content;
    objWord.Visible = true;

    //parseTemplate(objWord);
    //objWord.Quit(false);
    dealTable(objWord);
    dealCheckBox(objWord);
    dealInput(objWord);
    objWord.Visible = true;
}

//处理初始化的数据：取得Table，CheckBox,Input的数据：
function init()
{
    var xmlDoc = document.getElementById("xmlDoc");
    tableDataMap = new Map();
    //if(xmlData==null||xmlData.value.length<=0){
    //	return;
    //}else{
    //	xmlData =xmlData.value;
    //}
    //var xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
    //xmlDoc.async=false;  
    //xmlDoc.loadXML(xmlData);

    if(xmlDoc==null) return;
    var rootNode = xmlDoc.documentElement;
    if(rootNode==null) return;
    /**
      解析孩子的部分
    */
    var xmlChildNodeList = rootNode.childNodes;
    if(xmlChildNodeList!=null&&xmlChildNodeList.length>0)
    {
        for( var i=0;i<xmlChildNodeList.length;i++)
        {
            var xmlChildNode = xmlChildNodeList[i];
            var nodeName=xmlChildNode.nodeName;
            if(nodeName=="tables")
            {		
                var tableDataNodeList = xmlChildNode.childNodes;
                if(tableDataNodeList!=null&&tableDataNodeList.length>0)
                {
                    for(var j=0;j<tableDataNodeList.length;j++)
                    {
                        var tableDataChildNode  = tableDataNodeList[j];
                        var tableName = tableDataChildNode.nodeName;
                        tableDataArray = new Array();
                        var rowDataNodeList =tableDataChildNode.childNodes;
                        if(rowDataNodeList!=null&&rowDataNodeList.length>0)
                        {
                            for(var k=0;k<rowDataNodeList.length;k++)
                            {
                                var rowXmlDataT = rowDataNodeList[k];
                                var rowDataMap = new Map();
                                var leafColXmlDataT = rowXmlDataT.childNodes;
                                if(leafColXmlDataT!=null&&leafColXmlDataT.length>0)
                                {
                                    for(var l=0;l<leafColXmlDataT.length;l++)
                                    {
                                        var colXmlData = leafColXmlDataT[l];
                                        rowDataMap.put(colXmlData.nodeName,colXmlData.text);
                                    }
                                }
                                tableDataArray[tableDataArray.length]= rowDataMap;
                            }
                        } 
                        tableDataMap.put(tableName,tableDataArray);
                    }
                }
            }
            else if(nodeName=="inputdata")
            {
                inputDataMap = new Map();
                var inputDataNodeList = xmlChildNode.childNodes;
                for(var j=0;j<inputDataNodeList.length;j++)
                {
                    var inputNode = inputDataNodeList[j];
                    inputDataMap.put(inputNode.nodeName,inputNode.text);
                }
            }
            else if(nodeName=="checkboxdata")
            {
                checkBoxDataMap = new Map();
                var nodeList = xmlChildNode.childNodes;
                for(var j=0;j<nodeList.length;j++)
                {
                    var checkNode = nodeList[j];
                    checkBoxDataMap.put(checkNode.nodeName,checkNode.text);
                }
            }
        }
    }
}
		      
		  	  
/**
**处理不规则表格：
**/
function dealTable(objWord)
{
	for(var tabIndex=1;tabIndex<=objWord.ActiveDocument.Tables.Count;tabIndex++)
	{
		var vTable = objWord.ActiveDocument.Tables(tabIndex);
		if(vTable==null) return;
		var cells = vTable.Range.Cells;
		//alert(cells.Count);
		for(var cellIndex=1;cellIndex<=cells.Count;cellIndex++)
		{
			var myCell = cells.Item(cellIndex);
			var textStr = myCell.Range.Text;
			//一个动态表的第一个Cell的配置是：tableName.ColumnName,其他为ColumnName
			if(textStr.indexOf("$$")>=0&&textStr.indexOf(".")>=0)
			{
				textStr=textStr.substr(0,textStr.length-2);
				var strArray = textStr.split("$$");
				//读取不规则表格原有的行数
				//var originalRowCount = parseInt(strArray[0]);
				var tableInfo = strArray[0];
				var originalRowCount = parseInt(tableInfo.split("_")[0]);
				var originalCellCount = parseInt(tableInfo.split("_")[1]);
				//alert("originalRowCount:"+originalRowCount);
				textStr = strArray[1];
				textStr=textStr.substr(0,textStr.length-1);
				var strArray = textStr.split(".");
				if(strArray.length!=2) continue;
				var tableName = strArray[0];
				var columnName =strArray[1];
				//alert("tableName   " + tableName)
			    //alert("columnName  " + columnName);
				//alert("originalRowCount:"+originalRowCount);
				//alert("originalCellCount:"+originalCellCount);
				myCell.Select();
				var sel = objWord.ActiveDocument.Application.Selection;
				//var firstRowIndex = myCell.rowIndex;
				//var firstRow = vTable.rows(firstRowIndex);							
				var cellsNum = originalCellCount;
				var columnArray = new Array();
				for(var i=0;i<cellsNum;i++)
				{
					var tmpText = cells.Item(cellIndex+i).Range.Text;
					tmpText=tmpText.substr(0,tmpText.length-2);
					if(i == 0)
					{
					    tmpText = tmpText.substr(tmpText.indexOf(".")+1,tmpText.length-1);
					}
					columnArray[i]=tmpText;					
				}
				var tableData =tableDataMap.get(tableName);
				if(tableData==null||tableData.length<=0)
				{
					//要删除当前两行:或者置为空
					//alert(cellsNum);
					for(var i=0;i<cellsNum;i++)
					{
					    cells.Item(cellIndex+i).Range.Text="";
					}	
				}
				else
				{
					var myindex = cellIndex;
					if(tableData.length>originalRowCount){
						for(var i = 0 ; i < tableData.length-originalRowCount ; i++)
						{
							//vTable.Rows.Add(myCell);
							for(var j=0;j<cellsNum;j++){
								cells.Item(cellIndex+j).Split(2,1);
							}
						}
					}
					//循环tableData，相当于循环row。如果tableData的条数小于table的行数就直接插入数据进去，如果大于table的行数就插入新的row，然后插入数据到相应cell。
					//alert("originalRowCount:"+originalRowCount)
					myindex = cellIndex;
					for(var i = 0 ; i < tableData.length ; i++)
					{
              var tmpDataMap =tableData[i]; 			  	  	 	  	      
              var valusArray =tmpDataMap.keys();
              for(var j=0;j<cellsNum;j++)
              {
                  var columnName = columnArray[j];
                  var value = tmpDataMap.get(columnName);
                  if(value==null)
                  {
                      value = "";
                  } 
                  cells.Item(myindex++).Range.Text = value;
                  //vTable.rows(firstRowIndex+i).cells(j+1).Range.Text = value;
              }		
					}	
				}
			}
		}
	}
}
		  	 		  	  
//处理CheckBox的数据
function  dealCheckBox(objWord)
{
    //alert(checkBoxDataMap);
    if(checkBoxDataMap == null) 
      return;
    var objSelection = objWord.ActiveDocument.Content;
    var keyArray = checkBoxDataMap.keys();
    //alert(keyArray.length);
    if(keyArray!=null&&keyArray.length>0)
    {
        for(var i=0 ; i<keyArray.length ; i++)
        {
            objSelection = objWord.ActiveDocument.Content;
            var keyName = keyArray[i];
            var keyValue = checkBoxDataMap.get(keyName);
            objSelection.Find.Text = "口$"+keyName+"#";
            //alert("口$"+keyName+"#: "+objSelection.Find.Execute());
            if(objSelection.Find.Execute())
            {
                if(keyValue == "1")
                {
                    objSelection.Text = 'R';
                    objSelection.Font.Name = "Wingdings 2";
                    objSelection.Font.Size = 12;
                }
                else
                {
                    objSelection.Text = '口';
                }
            }           
        }
    }
}

//处理InputText的数据
function dealInput(objWord)
{
    if(inputDataMap==null) return;
    var objSelection = objWord.ActiveDocument.Content;
    var keyArray = inputDataMap.keys();
    if(keyArray!=null&&keyArray.length>0)
    {
        for(var i=0;i<keyArray.length;i++)
        {
            var keyName = keyArray[i];
            var keyValue = inputDataMap.get(keyName);
            //alert("keyName:==="+keyName + "," + keyValue);
            keyName="$"+keyName+"$";
            try
            {
                objSelection.Find.Execute(keyName,false,true,false,false,false,true,1,false,keyValue,2);
            }
            catch(e){}
        }
    }
}		  	    	    

function parseTemplate(objWord)
{
    parseCheckBox(objWord);
    parseInput(objWord);
    parseTable(objWord);
}

//解析input域，把$tab_input1$解析为表名tab+字段名input1 ,存入inputTagMap
function parseInput(objWord)
{
    inputTagMap = new Map();
    var objSelection = objWord.ActiveDocument.Content;
    objSelection.Find.Text = "$*.*$";
    var myFind = objSelection.Find;
    myFind.MatchWildcards = true;
    myFind.Execute();
    //alert(myFind.Found);
    while(myFind.Found)
    {
        tagText = objSelection.Text;
        //alert(tagText.substr(2,tagText.length-3));
        tagText = tagText.substr(2,tagText.length-3);
        var strArray = tagText.split(".");
        var tableName = strArray[0];
        var columName = strArray[1];
        inputTagMap.put(tableName,columName);
        objSelection.Text = "";
        myFind.Execute();
    }
}

//解析checkbox域，把口$tab_ch1#解析为表名tab+字段名ch1 ,存入checkBoxTagMap
function parseCheckBox(objWord)
{
    checkBoxTagMap = new Map();
    var objSelection = objWord.ActiveDocument.Content;
    objSelection.Find.Text = "口>$*.*#<";
    var myFind = objSelection.Find;
    myFind.MatchWildcards = true;
    myFind.Execute();
    while(myFind.Found)
    {
        tagText = objSelection.Text;
        //alert(tagText.substr(2,tagText.length-3));
        tagText = tagText.substr(2,tagText.length-3);
        var strArray = tagText.split(".");
        var tableName = strArray[0];
        var columName = strArray[1];
        checkBoxTagMap.put(tableName,columName);
        objSelection.Text = "";
        myFind.Execute();
    }
}
//解析不规则table域，把（4$tableName1.a1 a2 a3 a4...）解析为表名tableName1+字段名a1 a2 a3 a4 ... ,存入tableTagMap
function parseTable(objWord)
{
    tableTagMap = new Map();
    for(var tabIndex=1;tabIndex<=objWord.ActiveDocument.Tables.Count;tabIndex++)
    {
        var vTable = objWord.ActiveDocument.Tables(tabIndex);
        if(vTable==null) return;
        var cells = vTable.Range.Cells;
        for(var cellIndex=1;cellIndex<=cells.Count;cellIndex++)
        {
            var myCell = cells.Item(cellIndex);
            var textStr = myCell.Range.Text;
            //一个动态表的第一个Cell的配置是：tableName.ColumnName,其他为ColumnName
            if(textStr.indexOf("$$")>=0&&textStr.indexOf(".")>=0)
            {
                textStr=textStr.substr(0,textStr.length-2);
                var strArray = textStr.split("$$");
                //读取不规则表格原有的行数
                var originalRowCount = parseInt(strArray[0]);
                textStr = strArray[1];
                textStr=textStr.substr(0,textStr.length-1);
                var strArray = textStr.split(".");
                if(strArray.length!=2) continue;
                var tableName = strArray[0];
                var columnName =strArray[1];
                myCell.Select();
                var sel = objWord.ActiveDocument.Application.Selection ;
                var firstRowIndex = myCell.rowIndex;
                var firstRow = vTable.rows(firstRowIndex);							
                var cellsNum = firstRow.cells.Count;
                var columnArray = new Array();
                for(var i=0;i<cellsNum;i++)
                {
                    var tmpText = firstRow.cells(i+1).Range.Text;
                    tmpText=tmpText.substr(0,tmpText.length-2);
                    if(i == 0)
                    {
                        tmpText = tmpText.substr(tmpText.indexOf(".")+1,tmpText.length-1);
                    }
                    //alert(tableName+"   "+tmpText);
                    columnArray[i]=tmpText;
                }		
                tableTagMap.put(tableName,columnArray);
            }
        }
    }
}

//test
function openDocFromUrl()
{
    var wordComponent = document.getElementById("riseOffice");
    //var objWord = new ActiveXObject("Word.Application");
    var wordUrl = document.getElementById("templateUrl").value;
    var wdReplaceAll = 2;
    // var objDoc = objWord.Documents.Open("d:\\111.doc");
    wordComponent.OpenFromURL(wordUrl);
    var objWord=wordComponent;

    var objSelection = objWord.ActiveDocument.Content;
    objWord.Visible = true;
    var vI=0

    var activeDocument =  objWord.ActiveDocument;
    for(var i=1;i<=objWord.ActiveDocument.Tables.Count;i++)
    {
        var vTable = objWord.ActiveDocument.Tables(i);
        dealTable1(objWord,vTable);
    }
    dealInput(objWord);
    dealCheckBox(objWord);			  	  
}