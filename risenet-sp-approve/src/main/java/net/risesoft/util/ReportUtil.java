package net.risesoft.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.risesoft.approve.entity.senate.SenateReportVO;
import net.risesoft.utilx.StringX;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
public class ReportUtil<T> {
	public static Map<String,List<Map<String, Object>>> reportDataListMap = new HashMap<String,List<Map<String, Object>>>();//查询时存放数据，为导出做准备
	
	public void openzjfc(InputStream is, String filename,HttpServletResponse  response) throws IOException {
		java.io.BufferedOutputStream bos = null;
		try {
			response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment; filename="+ filename+ ".xls");
			bos = new java.io.BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = is.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
			if (bos != null)
				bos.close();
		}
	}
	
	private void download(String path, HttpServletResponse response) {  
        try {  
            // path是指欲下载的文件的路径。  
            File file = new File(path);  
            // 取得文件名。  
            String filename = file.getName();  
            // 以流的形式下载文件。  
            InputStream fis = new BufferedInputStream(new FileInputStream(path));  
            byte[] buffer = new byte[fis.available()];  
            fis.read(buffer);  
            fis.close();  
            // 清空response  
            response.reset();  
            // 设置response的Header  
            response.addHeader("Content-Disposition", "attachment;filename="  
                    + new String(filename.getBytes()));  
            response.addHeader("Content-Length", "" + file.length());  
            OutputStream toClient = new BufferedOutputStream(  
                    response.getOutputStream());  
            response.setContentType("application/vnd.ms-excel;charset=gb2312");  
            toClient.write(buffer);  
            toClient.flush();  
            toClient.close();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
    }  
	
	@SuppressWarnings("unchecked")
	public void exportData(String filename,String[] headers,List<Map<String, Object>> mapList,OutputStream out,T tp) {
		List<T> dataset = new ArrayList<T>();
		try {
			for(Map<String, Object> map : mapList){
				T t = (T) tp.getClass().newInstance();
				Field[] fields = t.getClass().getDeclaredFields();
				for (short n = 0; n < fields.length; n++) {
					Field field = fields[n];
					String fieldName = field.getName();
					field.setAccessible(true);
					field.set(t, StringX.getNullString(map.get(fieldName.toUpperCase())));
				}
				dataset.add(t);
			}
			exportExcel(filename, headers, dataset, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * excl导出
	 * 
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param out
	 * @param pattern
	 */
	@SuppressWarnings({ "deprecation", "unused", "unchecked", "rawtypes" })
	public byte[] exportExcel(String title, String[] headers,Collection<T> dataset,OutputStream out) {
		String pattern="yyyy-MM-dd";
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		sheet.setDefaultRowHeight((short) 30);
		// 生成一个样式
		// HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		// style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		//
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//
		// style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		//
		// style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		//
		// style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//
		// style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//
		// style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		// HSSFFont font = workbook.createFont();
		//
		// font.setColor(HSSFColor.VIOLET.index);
		//
		// font.setFontHeightInPoints((short) 12);
		//
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		// style.setFont(font);
		// 生成并设置另一个样式
		// HSSFCellStyle style2 = workbook.createCellStyle();
		//
		// style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		//
		// style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//
		// style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		//
		// style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		//
		// style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//
		// style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//
		// style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//
		// style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//
		// // 生成另一个字体
		//
		// HSSFFont font2 = workbook.createFont();
		//
		// font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		//
		// // 把字体应用到当前的样式
		//
		// style2.setFont(font2);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
		font.setFontHeight((short) 380); // 设置字体大小
		font.setFontName("宋体"); // 设置单元格字体
		HSSFCellStyle style0 = workbook.createCellStyle();
		style0.setFont(font);
		style0.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 声明一个画图的顶级管理器
		// HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		// HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
		// 0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		// comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		// comment.setAuthor("leno");
		// 产生表格标题行
		HSSFRow row0 = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			row0.createCell(i);
			if (i == headers.length - 1) {
				sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) i));
				HSSFCell cells = row0.createCell((short) 0);
				HSSFRichTextString text0 = new HSSFRichTextString(title);
				cells.setCellValue(text0);
				cells.setCellStyle(style0);
			}
		}
		HSSFFont font1 = workbook.createFont();
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
		font1.setFontHeight((short) 240); // 设置字体大小
		font1.setFontName("宋体"); // 设置单元格字体
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setFont(font1);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 产生表格标题行
		HSSFRow row = sheet.createRow(1);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cells = row.createCell(i);
			cells.setCellStyle(style1);
			HSSFRichTextString texts = new HSSFRichTextString(headers[i]);
			cells.setCellValue(texts);
		}
		HSSFFont font2 = workbook.createFont();
		font2.setFontHeight((short) 200); // 设置字体大小
		font2.setFontName("宋体"); // 设置单元格字体
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFont(font2);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 1;
		int columIdex=0;//列序号
		while (it.hasNext()) {
			index++;
			columIdex++;
			row = sheet.createRow(index);
			//第一列产生序号
			HSSFCell cell0 = row.createCell(0);
			cell0.setCellValue(columIdex);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i+1);
				cell.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
				+ fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Integer) {
						int intValue = (Integer) value;
						cell.setCellValue(intValue);
					} else if (value instanceof Float) {
						float fValue = (Float) value;
						textValue = new HSSFRichTextString(
								String.valueOf(fValue)).getString();
						cell.setCellValue(textValue);
					} else if (value instanceof Double) {
						double dValue = (Double) value;
						textValue = new HSSFRichTextString(
								String.valueOf(dValue)).getString();
						cell.setCellValue(textValue);
					} else if (value instanceof Long) {
						long longValue = (Long) value;
						cell.setCellValue(longValue);
					}
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = "男";
						if (!bValue) {
							textValue = "女";
						}
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
						1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						// patriarch.createPicture(anchor, workbook.addPicture(
						// bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串简单处理
						if (value != null)
							textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							HSSFFont font3 = workbook.createFont();
							// font3.setColor(HSSFColor.BLUE.index);
							richString.applyFont(font2);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		byte[] tobyte=null;
		try {
			OutputStream out2 = new FileOutputStream("E://"+title+".xls"); 
//			workbook.write(out1);
			workbook.write(out2);
			workbook.write(out);
//			tobyte = out1.toByteArray();
			out2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
		}
		return tobyte;
	}
}
