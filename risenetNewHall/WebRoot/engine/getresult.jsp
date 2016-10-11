<%
/*��ʾ���
���������޸�/�����ʾʱ��ȡXML�ļ�
*/
%>
<%@ page language="java" %>
<%@ page contentType="text/xml; charset=gb2312" %>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="net.sysmain.common.Operator"%>
<%@ page import="net.sysmain.common.I_TemplateConstant"%>
<%@ page import="net.sysmain.common.I_UserConstant"%>
<%@ page import="net.business.engine.DataEngine"%>
<%@ page import="net.business.engine.common.I_Parser"%>
<%@ page import="net.business.engine.Template"%>
<%@ page import="net.business.engine.common.I_TemplatePara"%>
<%@ page import="net.business.engine.TemplateField"%>
<%@ page import="net.sysmain.util.StringTools"%>
<%@ page import="java.sql.Connection"%>
<%
I_TemplatePara para = null;
DataEngine de = null;
Template temp = null;
Connection conn = null;
String queryString = "";
String deptCode ="";
Operator user = (Operator)session.getAttribute(I_UserConstant.USER_INFO);

if(user != null)
{
    deptCode = user.getUserOrgId();
}
queryString = request.getParameter("querystring");
//1��������������ʵ��
de = DataEngine.getInstance();
conn = de.getConnection();
//2���õ�ģ��
temp = de.getTemplate(request); 

if(temp.getVersion()>= 3.0)
{//�µ�ģ�����ͣ����ܵ���
    out.print("<?xml version=\"1.0\" encoding=\"gb2312\" ?><Root>");
    out.println("ģ�岻֧�ָ�ģʽ�ĵ���");
    out.print("</Root>");
    return;
}
//��������Ϊ��ʾ���޸�(����ʱ��������)
para = net.business.engine.common.Tools.getTemplatePara(temp);
para.setEditType(1);
para.setQueryString(queryString);
try
{
    out.print("<?xml version=\"1.0\" encoding=\"gb2312\" ?><Root>");
    out.println(getXmlNotFromField(temp, request));
    out.print(de.readUserXMLData(request, conn, temp, para, deptCode));
    out.print("</Root>");
}
catch(Exception e)
{
  out.print("<![CDATA[" + e.getMessage() + "]]>");
  out.print("</Root>");
}
de.closeConnection(conn);

//��ȡ��form��session�д��ݹ���������
%>
<%!private String getXmlNotFromField(Template template, HttpServletRequest request)
{
    TemplateField theField = null;
    StringBuffer xmlValueNotFromField = new StringBuffer("");
    HttpSession session1 = request.getSession(false);
    Hashtable  virtualTable = new Hashtable();

    for(int i=0; i<template.length(); i++)
    {
        theField = template.get(i);
        String fieldAlias = theField.getFieldAlias().trim();
	      if(virtualTable.containsKey(fieldAlias))
        {
            continue;
        }
        else
        {   
            virtualTable.put(fieldAlias,"");
        }
        if(theField.getField_Id() == 0 && !theField.getFormCtrlName().equals("") && !fieldAlias.equals(""))
        {
            String bindAlias = fieldAlias.replace('.','_');
            int pos = fieldAlias.indexOf(".");
            if(pos>=0)
            {
                //���طǰ��ֶε�������Դ������
                String dataFromType = fieldAlias.substring(0, pos).toLowerCase();
                if(dataFromType.equals("form"))
                {
                    String theValue = StringTools.getFormValue(request, fieldAlias, theField.geDataFormat());
                    if(theValue != null)
                    {
                        xmlValueNotFromField.append("<" + bindAlias + "><![CDATA[" + theValue + "]]></" + bindAlias + ">");
                    }
                }
                else if(dataFromType.equals("session"))
                {
                    if(session1 != null)
                    {
                        String theValue = null;
                        Operator user = (Operator)session1.getAttribute(I_UserConstant.USER_INFO);
                        //������������Ͳ��Ŵ���
                        if(user != null)
                        {
                            //�ж����еļ�������------------�д��Ľ���ͬϵͳ�й�
                            theValue = StringTools.getSessionValue(request, fieldAlias);
                            if(theValue != null)
                            {
                                xmlValueNotFromField.append("<" + bindAlias + "><![CDATA[" + theValue + "]]></" + bindAlias + ">");
                            }
                        }
                    }
                }
            }
        }
    }//--for

    return xmlValueNotFromField.toString();
}
%>