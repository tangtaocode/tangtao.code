package net.risesoft.fileflow.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.common.util.ContextUtil;
import net.risesoft.fileflow.entity.solr.FullText;
import net.risesoft.fileflow.repository.solr.FullTextRepository;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 2000017950715151001L;

	private static Logger logger = LoggerFactory.getLogger(InitServlet.class);

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	@Override
	public void init() throws ServletException
	{
		// init_solr();
		// cell();
	}

	private void init_jms()
	{
		//JmsTemplate jmsTemplate = ContextUtil.getBean("jmsTemplate");
		//jmsTemplate.convertAndSend("2222222222  jms 2222222222222222");
		//jmsTemplate.convertAndSend("jmsDefaultDestinationName", "11111111111  jms 1111111111111111");
	}

	private void init_solr()
	{
		/*FullTextRepository repository = ContextUtil.getBean("fullTextRepository");
		Iterable<FullText> list = repository.findAll(new PageRequest(0, 10));
		for (FullText ft : list) {
			logger.info("1111:  " + ft.getId() + "--" + ft.getTitle() + "--" + ft.getText_txt_cn());
		}

		Page<FullText> pages = repository.findByTitle("计划局公文处理", new PageRequest(0, 10));
		List<FullText> list2 = pages.getContent();
		for (FullText ft : list2) {
			logger.info("2222:  " + ft.getId() + "--" + ft.getTitle() + "--" + ft.getText());
		}

		List<String> list3 = Arrays.asList("计划局", "cat-2", "cat-3");
		Page<FullText> pages4 = repository.findByTextIn(list3, new PageRequest(0, 10));
		List<FullText> list4 = pages.getContent();
		for (FullText ft : list4) {
			logger.info("3333:  " + ft.getId() + "--" + ft.getTitle() + "--" + ft.getLast_modified());
		}*/

	}

	private void cell()
	{
		// FullTextRepository repository = ContextUtil.getBean("fullTextRepository");

		/*AutoDetectParser tikaParser = new AutoDetectParser();
		ParseContext context = new ParseContext();
		StringWriter sw = new StringWriter();
		ContentHandler contentHandler = new BodyContentHandler(sw);
		Metadata metadata = new Metadata();

		try {
			InputStream is = new FileInputStream("F:\\docs\\ftoa\\替换 Windows Vista 及其以后版本中的 DHTML 编辑控件 .pdf");
			tikaParser.parse(is, contentHandler, metadata, context);
			IOUtils.closeQuietly(is);
			logger.info(sw.toString());
			logger.info(contentHandler.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}*/

		logger.info("");
	}
}
