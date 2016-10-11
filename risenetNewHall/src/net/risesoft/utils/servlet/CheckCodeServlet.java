package net.risesoft.utils.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.risesoft.common.Common;
/**
 * 
  * @ClassName: CheckCodeServlet
  * @Description: 验证码servlet
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:22:18 PM
  *
 */
public class CheckCodeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6202557944001742458L;
	private int imageWidth = 70;
	private int imageHeight = 23;
	private int codeNumber = 4;
	private int backGround = 30;
	/**
	 * Constructor of the object.
	 */
	public CheckCodeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_BGR);
		Graphics2D g = bufferedImage.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, imageWidth , imageHeight );
		Random random = new Random();
		for (int i = 0; i < backGround; i++)
		{
			int red = random.nextInt(256);
			int green = random.nextInt(256);
			int blue = random.nextInt(256);
			g.setColor(new Color(red, green, blue));
			int start_X = random.nextInt(imageWidth);
			int start_Y = random.nextInt(imageHeight);
			if (random.nextInt(5) % 3 == 0)
			{
				int r = random.nextInt(10);
				int startAngle = random.nextInt(360);
				int arcAngle = random.nextInt(360);
				g.drawArc(start_X, start_Y, r, r, startAngle, arcAngle);
			}
			else
			{
				int change_X = random.nextInt() % 6;
				int change_Y = random.nextInt() % 6;
				g.drawLine(start_X, start_Y, start_X + change_X, start_Y + change_Y);
			}
		}
		StringBuffer ValidateCode = new StringBuffer();
		g.setFont(new Font("Tahoma", Font.BOLD, imageHeight - 3));
		for (int i = 0; i < codeNumber; i++)
		{
			int red = random.nextInt(100) + 50;
			int green = random.nextInt(100) + 50;
			int blue = random.nextInt(100) + 50;
			g.setColor(new Color(red, green, blue));
			String code = null;
			int type = random.nextInt(3);
			if (type == 1)
			{
				code = String.valueOf((char) (random.nextInt(26) + 65));
			}
			else if (type == 2)
			{
				code = String.valueOf((char) (random.nextInt(26) + 97));
			}
			else
			{
				code = String.valueOf(random.nextInt(10));
			}
			ValidateCode.append(code);
			g.drawString(code, imageWidth / codeNumber * i + 4, 16);
		}
		request.getSession().setAttribute(Common.sessionCheckCode, ValidateCode.toString());
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(bufferedImage, "jpeg", sos);
		bufferedImage = null;
		sos.close();
		random = null;
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
}
