package net.risesoft.services.webservice.jShare.jyxt;
import java.io.*;
import org.apache.soap.*;
import org.apache.soap.util.*;
import org.apache.soap.util.xml.*;
import org.apache.soap.rpc.SOAPContext;
public class MsgBody extends Body 
{ 
	 private String msgSink="";//记录传入body内容
	 
	 public MsgBody(String msgSink){
		 this.msgSink = msgSink;
	 }
	 
	 public void marshall (String strEncodeStyle,Writer msgSink,NSStack nameSpaceStack,
	                        XMLJavaMappingRegistry registry,SOAPContext context) 
	        throws IllegalArgumentException, IOException 
	 {
	     // Start Element
	 msgSink.write ('<'+"SOAP-ENV"+':'+ Constants.ELEM_BODY+'>'+ StringUtils.lineSeparator);
	 msgSink.write(this.msgSink);
	  // End Element
	 msgSink.write ("</" + "SOAP-ENV" + ':'+  Constants.ELEM_BODY+'>'+ StringUtils.lineSeparator);
	     nameSpaceStack.popScope ();
	     
	     System.out.println(msgSink);
	 }
}