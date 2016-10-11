function exportEasyUIExcel(tableID,datas){
	var excelRows=new Array();
	var rows=datas||$("#"+tableID).datagrid("getRows");
	var columns=$("#"+tableID).datagrid("options").columns[0];
		
    for (i = 0; i < rows.length; i++) {
    	var excelRow=new Array();
        for (j=0;j<columns.length;j++) 
        	excelRow.push(rows[i][columns[j].field]);
        excelRows.push(excelRow);
    } 
    saveArrayAsExcel(excelRows,columns);
}

function saveArrayAsExcel(rows,columns){
    var jXls,myWorksheet;
    try {
        jXls = new ActiveXObject('Excel.Application');
    }
    catch (e) {
        alert(e.message);
        return false;
    }

    jXls.Visible = true;
    myWorksheet = jXls.Workbooks.Add().ActiveSheet;
    
    var splitNum=0;
    var startSplit=0;
    var isSplited=false;
    for (i=0;i<rows.length;i++){
    	for (j=0;j<rows[i].length;j++){
    		if(columns[j].excelSplit){
    			var splited=rows[i][j].split(columns[j].excelSplit);
    			startSplit=j;
    			for(k=0;k<splited.length;k++){
    				var sv=splited[k]==columns[j].excelBlank?'':splited[k];
    	    		myWorksheet.Cells(i+2,j+k+1).Value = sv; 
    			}
    			if(!isSplited){
    				isSplited=true;
    				splitNum+=splited.length;
    			}
    		}else{
    			if(j<startSplit||startSplit==0)
    				myWorksheet.Cells(i+2,j+1).Value = rows[i][j]; 
    			else
    				myWorksheet.Cells(i+2,j+splitNum).Value = rows[i][j];
    		}
    	}
    }
    
    for(l=0;l<columns.length;l++){
    	if(l>startSplit&&splitNum>0)
    		myWorksheet.Cells(1,l+splitNum).Value = columns[l].title; 
    	else
    		myWorksheet.Cells(1,l+1).Value = columns[l].title; 
	}
    
    var range=myWorksheet.Range(myWorksheet.Cells(1,1),myWorksheet.Cells(i+1,j+(splitNum==0?0:(splitNum-1))));
    for(k=1;k<=4;k++)
    	range.Borders(k).Weight=2;
    for(i=0;i<columns.length;i++){
    	if(columns[i].excelFormat)
    		myWorksheet.Columns(i+1+(i<splitNum?0:splitNum)).NumberFormat=columns[i].excelFormat;
    }
    if(splitNum>0)
    	myWorksheet.Range(myWorksheet.Cells(1,startSplit+1),myWorksheet.Cells(1,startSplit+splitNum)).MergeCells=true;
    myWorksheet.Rows(1).Font.Bold=true;
    myWorksheet.Rows(1).HorizontalAlignment = 3; 

	myWorksheet.Columns.AutoFit;
    jXls.UserControl = true;
    myWorksheet=null;
    jXls=null;
}