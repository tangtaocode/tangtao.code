'ʹ��VBScript������֤
Function getTheLength(strValue)
  Dim theLength
  Dim i

  theLength = 0
  For i= 1 to len(strValue)
     If(asc(mid(strValue,i,1))<0) Then
          theLength = theLength + 2
      Else
          theLength = theLength + 1
      End If
  Next

  getTheLength = theLength
End Function

'for checkbox oe radio
Function isChecked(theName, theObject)
    Dim  blState
    Dim i
    
    If isFormObject(theObject) = False Then
        alert("[" + theName + "]��Ӧ�ı�Ԫ�ز�����")
	    isChecked = False
	    Exit Function
    End If

    blState = False
    On Error Resume Next
    If isNull(theObject.length)  Then
        If theObject.checked = True Then blState = True  
    Else
        For i=0 to theObject.length-1
            If theObject(i).checked = True Then blState = True
        Next
    End If
    If blState = False Then 
        alert("��ѡ������" & theName)
        On Error Resume Next
        call doForCheck(theObject)
        On Error goto 0
    End If

    isChecked = blState
End Function    

Function limitStringLenth(theName,theRealObject,theObject,theLength)
  Dim strValue
  Dim strCode
  
  If isFormObject(theObject) = False Then
      alert("[" + theName + "]��Ӧ�ı�Ԫ�ز�����")
	  limitStringLenth = False
	  Exit Function
  End If

  If theObject.tagName = "SELECT" Then
     If theObject.selectedIndex <> -1 Then
         strValue = theObject.options(theObject.selectedIndex).value
     Else
         strValue = ""
     End If
	 strCode = strValue
  Else
      strCode = theRealObject.value
	  strValue = theObject.value
  End If
  
  If theObject.name <> theRealObject.name Then
      If strCode = "" and strValue <> "" then
          alert(theName & "Ϊ�������ѡ�����룡")
		  On Error Resume Next
			call doForCheck(theObject)
			theObject.focus
			On Error goto 0
			limitStringLenth = False
			Exit Function
	  End If
	  strValue = strCode
  End If

  If getTheLength(strValue) > theLength  Then
      alert(theName & "���볤�ȴ���" & theLength)
      On Error Resume Next
      call doForCheck(theObject)
      theObject.focus
      On Error goto 0
      limitStringLenth = False
      Exit Function
  End If

  limitStringLenth = True
End Function 

Function isNumber(theName,theRealObject,theObject,theType,theLength)
  Dim strValue
  
  If isFormObject(theObject) = False Then
      alert("[" + theName + "]��Ӧ�ı�Ԫ�ز�����")
	  isNumber = False
	  Exit Function
  End If

  If theObject.tagName = "SELECT" Then
     If theObject.selectedIndex <> -1 Then
         strValue = theObject.options(theObject.selectedIndex).value
     Else
         strValue = ""
     End If
  Else
      strValue = theRealObject.value
  End If

  If trim(strValue) = "" Then
      isNumber = True
      Exit Function
  End If

  If instr(strValue,",") >0 Or instr(strValue,"��") then
      alert(theName & "�����а������������ţ�")
      On Error Resume Next
      call doForCheck(theObject)
      theObject.focus
      On Error goto 0
      isNumber = False
      Exit Function
  End If

  'for float type
  If theType=0 and instr(strValue,".")>0 Then
      alert(theName & "�������Ϊ������")
      On Error Resume Next
      call doForCheck(theObject)
      theObject.focus
      On Error goto 0
      isNumber = False
      Exit Function
  End If

  'for integer type
  If Not isNumeric(strValue) Then
      alert(theName & "�������Ϊ��ֵ��")
      theObject.focus
      isNumber = False
      Exit Function
  End If

  If parseFloat(trim(strValue)) >= 10^theLength  Then
      alert(theName & "��ֵ�������" & String(theLength,"9") & "��")
      On Error Resume Next
      call doForCheck(theObject)
      theObject.focus
      On Error goto 0
      isNumber = False
      Exit Function
  End If

  isNumber = True
End Function

Function isDateFormat(theName,theRealObject,theObject)
  Dim strValue
  
  If isFormObject(theObject) = False Then
      alert("[" + theName + "]��Ӧ�ı�Ԫ�ز�����")
	  isDateFormat = False
	  Exit Function
  End If
  strValue = theObject.value
  If trim(strValue) = "" Then
      isDateFormat = true
      Exit Function
  End If

  If Not isDate(strValue) Then
      alert(theName & "�������Ϊ���ڣ���ʽΪ2004/05/28��")
      On Error Resume Next
      call doForCheck(theObject)
      theObject.focus
      On Error goto 0
      isDateFormat = False
      Exit Function
  End If

  isDateFormat = true
End Function

Function isNotBlank(theName,theRealObject,theObject)
  Dim strValue
  Dim strCode
  Dim errMsg
  
  If isFormObject(theObject) = False Then
      alert("[" + theName + "]��Ӧ�ı�Ԫ�ز�����")
	  isNotBlank = False
	  Exit Function
  End If 
  strValue = theObject.value
  If theObject.tagName = "SELECT" Then
     If theObject.selectedIndex <> -1 Then
         strCode = theObject.options(theObject.selectedIndex).value
     Else
         strCode = ""
     End If
  Else
      strCode = theRealObject.value
  End If

  If theObject.name <> theRealObject.name Then
      If strCode = "" then
          If strValue = "" then 
              errMsg = "���벻��Ϊ�գ�"
          Else
              errMsg =  "Ϊ�������ѡ�����룡"
          End If
      Else
          If strValue = "" then errMsg = "���벻��Ϊ�գ�"
      End If
  Else
       If strValue = "" then errMsg = "���벻��Ϊ�գ�"
  End If
  
  If errMsg <> "" Then
      alert(theName & errMsg)
      On Error Resume Next
      call doForCheck(theObject)
      theObject.focus
      On Error goto 0
      isNotBlank = False
      Exit Function
  End If

  isNotBlank = True  
End Function

'convert String to float
Function toFloat(strValue)
  If isNumeric(strValue) Then
      toFloat = cDbl(strValue)
  Else
      toFloat = 0
  End If
End Function 