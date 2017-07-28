package net.risesoft.approve.risefile.content;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.server.UID;
import java.util.Calendar;
import java.util.Map;

import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.exception.RiseFileSystemException;
import net.risesoft.approve.risefile.handler.HandleManager;
import net.risesoft.approve.risefile.handler.RiseFileOutputStream;
import net.risesoft.commons.util.GUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ParameterParser;
import org.apache.commons.io.output.DeferredFileOutputStream;

/**
 * @author wangdong
 * @see org.apache.commons.fileupload.diskDiskFileItem
 * 负责文件的io操作的实现
 */
public class RiseFileItemIODirect implements FileItem, RiseFileItem{
	 // ----------------------------------------------------- rise extends constants
	
	public String fileGUID;// 主键，生成。
	
	public String fileNameExt;

	public String titile;
	
	public RiseFileConfig config;
	
	public String currentUser;
	
	public String fileType;

	public double fileSize;
	
	public boolean isManager;
    // ----------------------------------------------------- Manifest constants


    /**
     * Default content charset to be used when no explicit charset
     * parameter is provided by the sender. Media subtypes of the
     * "text" type are defined to have a default charset value of
     * "ISO-8859-1" when received via HTTP.
     */
    public static final String DEFAULT_CHARSET = "ISO-8859-1";


    // ----------------------------------------------------------- Data members


    /**
     * UID used in unique file name generation.
     */
    private static final String UID =
            new UID().toString().replace(':', '_').replace('-', '_');

    /**
     * Counter used in unique identifier generation.
     */
    private static int counter = 0;


    /**
     * The name of the form field as provided by the browser.
     */
    private String fieldName;


    /**
     * The content type passed by the browser, or <code>null</code> if
     * not defined.
     */
    private String contentType;


    /**
     * Whether or not this item is a simple form field.
     */
    private boolean isFormField;


    /**
     * The original filename in the user's filesystem.
     */
    private String fileName;


    /**
     * The size of the item, in bytes. This is used to cache the size when a
     * file item is moved from its original location.
     */
    private long size = -1;


    /**
     * The threshold above which uploads will be stored on disk.
     */
    private int sizeThreshold;


    /**
     * The directory in which uploaded files will be stored, if stored on disk.
     */
    private File repository;
    
    
    public String fileFullName;


    /**
     * Cached contents of the file.
     */
    private byte[] cachedContent;


    /**
     * Output stream for this item.
     */
    private transient DeferredFileOutputStream dfos;

    /**
     * File to allow for serialization of the content of this item.
     */
    private File dfosFile;


    // ----------------------------------------------------------- Constructors


    /**
     * Constructs a new <code>DiskFileItem</code> instance.
     *
     * @param fieldName     The name of the form field.
     * @param contentType   The content type passed by the browser or
     *                      <code>null</code> if not specified.
     * @param isFormField   Whether or not this item is a plain form field, as
     *                      opposed to a file upload.
     * @param fileName      The original filename in the user's filesystem, or
     *                      <code>null</code> if not specified.
     * @param sizeThreshold The threshold, in bytes, below which items will be
     *                      retained in memory and above which they will be
     *                      stored as a file.
     * @param repository    The data repository, which is the directory in
     *                      which files will be created, should the item size
     *                      exceed the threshold.
     */
    
    public RiseFileItemIODirect(String fieldName, String contentType,
            boolean isFormField, String fileName, int sizeThreshold,
            File repository ,RiseFileConfig config) {
        this.fieldName = fieldName;
        this.contentType = contentType;
        this.isFormField = isFormField;
        this.sizeThreshold = sizeThreshold;
        this.repository = repository;
        this.fileName = fileName;
        if(isFormField){
        	return;
        }
        
    	fileGUID=new GUID().toString();
    	int i=fileName.lastIndexOf(".");
    	titile=fileName.substring(0,i);
    	fileNameExt=fileName.substring(i+1,fileName.length());
    	this.config=config;
    	//this.minVersion=""+RiseFileUtil.getNextMinVersion(config,titile);
    }

    // ------------------------------- Methods from javax.activation.DataSource


    /**
     * Returns an {@link java.io.InputStream InputStream} that can be
     * used to retrieve the contents of the file.
     *
     * @return An {@link java.io.InputStream InputStream} that can be
     *         used to retrieve the contents of the file.
     *
     * @throws IOException if an error occurs.
     */
    public InputStream getInputStream()
        throws IOException {
    	return null;
    	//throw new UnsupportedOperationException("只写文件，不能通过此方法获得数据");
    }


    /**
     * Returns the content type passed by the agent or <code>null</code> if
     * not defined.
     *
     * @return The content type passed by the agent or <code>null</code> if
     *         not defined.
     */
    public String getContentType() {
        return contentType;
    }


    /**
     * Returns the content charset passed by the agent or <code>null</code> if
     * not defined.
     *
     * @return The content charset passed by the agent or <code>null</code> if
     *         not defined.
     */
    public String getCharSet() {
        ParameterParser parser = new ParameterParser();
        parser.setLowerCaseNames(true);
        // Parameter parser can handle null input
        Map params = parser.parse(getContentType(), ';');
        return (String) params.get("charset");
    }


    /**
     * Returns the original filename in the client's filesystem.
     *
     * @return The original filename in the client's filesystem.
     */
    public String getName() {
        return fileName;
    }


    // ------------------------------------------------------- FileItem methods


    /**
     * Provides a hint as to whether or not the file contents will be read
     * from memory.
     *
     * @return <code>true</code> if the file contents will be read
     *         from memory; <code>false</code> otherwise.
     */
    public boolean isInMemory() {
	//  if (cachedContent != null) {
	//      return true;
	//  } else {
	//      return dfos.isInMemory();
	//  }
    	return false;
    }


    /**
     * Returns the size of the file.
     *
     * @return The size of the file, in bytes.
     */
    public long getSize() {
    	return dfos.getFile().length();
    }


    /**
     * Returns the contents of the file as an array of bytes.  If the
     * contents of the file were not yet cached in memory, they will be
     * loaded from the disk storage and cached.
     *
     * @return The contents of the file as an array of bytes.
     */
    public byte[] get() {
    	throw new UnsupportedOperationException("只写文件，不能通过此方法获得数据");
    	
		//        byte[] fileData = new byte[(int) getSize()];
		//        FileInputStream fis = null;
		//
		//        try {
		//            fis = new FileInputStream(dfos.getFile());
		//            fis.read(fileData);
		//        } catch (IOException e) {
		//            fileData = null;
		//        } finally {
		//            if (fis != null) {
		//                try {
		//                    fis.close();
		//                } catch (IOException e) {
		//                    // ignore
		//                }
		//            }
		//        }
		//
		//        return fileData;
    }


    /**
     * Returns the contents of the file as a String, using the specified
     * encoding.  This method uses {@link #get()} to retrieve the
     * contents of the file.
     *
     * @param charset The charset to use.
     *
     * @return The contents of the file, as a string.
     *
     * @throws UnsupportedEncodingException if the requested character
     *                                      encoding is not available.
     */
    public String getString(final String charset)
        throws UnsupportedEncodingException {
    	return getString();
    }


    /**
     * Returns the contents of the file as a String, using the default
     * character encoding.  This method uses {@link #get()} to retrieve the
     * contents of the file.
     *
     * @return The contents of the file, as a string.
     *
     * @todo Consider making this method throw UnsupportedEncodingException.
     */
    public String getString() {
    	throw new UnsupportedOperationException("只写文件，不能通过此方法获得数据");
    }


    /**
     * A convenience method to write an uploaded item to disk. The client code
     * is not concerned with whether or not the item is stored in memory, or on
     * disk in a temporary location. They just want to write the uploaded item
     * to a file.
     * <p>
     * This implementation first attempts to rename the uploaded item to the
     * specified destination file, if the item was originally written to disk.
     * Otherwise, the data will be copied to the specified file.
     * <p>
     * This method is only guaranteed to work <em>once</em>, the first time it
     * is invoked for a particular item. This is because, in the event that the
     * method renames a temporary file, that file will no longer be available
     * to copy or rename again at a later time.
     *
     * @param file The <code>File</code> into which the uploaded item should
     *             be stored.
     *
     * @throws Exception if an error occurs.
     */
    public void write(File file) throws Exception {
    	throw new UnsupportedOperationException("此方法在有生fileitem中暂时没有实现");
//            File outputFile = getStoreLocation();
//            if (outputFile != null) {
//                    BufferedInputStream in = null;
//                    BufferedOutputStream out = null;
//                    try {
//                        in = new BufferedInputStream(
//                            new FileInputStream(outputFile));
//                        out = new BufferedOutputStream(
//                                new FileOutputStream(file));
//                        IOUtils.copy(in, out);
//                    } finally {
//                        if (in != null) {
//                            try {
//                                in.close();
//                            } catch (IOException e) {
//                                // ignore
//                            }
//                        }
//                        if (out != null) {
//                            try {
//                                out.close();
//                            } catch (IOException e) {
//                                // ignore
//                            }
//                        }
//                    }
//                }
    }


    /**
     * Deletes the underlying storage for a file item, including deleting any
     * associated temporary disk file. Although this storage will be deleted
     * automatically when the <code>FileItem</code> instance is garbage
     * collected, this method can be used to ensure that this is done at an
     * earlier time, thus preserving system resources.
     */
    public void delete() {
    	throw new UnsupportedOperationException("只写文件，不能通过此方法操作数据");
    }


    /**
     * Returns the name of the field in the multipart form corresponding to
     * this file item.
     *
     * @return The name of the form field.
     *
     * @see #setFieldName(java.lang.String)
     *
     */
    public String getFieldName() {
        return fieldName;
    }


    /**
     * Sets the field name used to reference this file item.
     *
     * @param fieldName The name of the form field.
     *
     * @see #getFieldName()
     *
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    /**
     * Determines whether or not a <code>FileItem</code> instance represents
     * a simple form field.
     *
     * @return <code>true</code> if the instance represents a simple form
     *         field; <code>false</code> if it represents an uploaded file.
     *
     * @see #setFormField(boolean)
     *
     */
    public boolean isFormField() {
        return isFormField;
    }


    /**
     * Specifies whether or not a <code>FileItem</code> instance represents
     * a simple form field.
     *
     * @param state <code>true</code> if the instance represents a simple form
     *              field; <code>false</code> if it represents an uploaded file.
     *
     * @see #isFormField()
     *
     */
    public void setFormField(boolean state) {
        isFormField = state;
    }


    /**
     * Returns an {@link java.io.OutputStream OutputStream} that can
     * be used for storing the contents of the file.
     *
     * @return An {@link java.io.OutputStream OutputStream} that can be used
     *         for storing the contensts of the file.
     *
     * @throws IOException if an error occurs.
     */
    public OutputStream getOutputStream()
        throws IOException {
    	
    	//暂时对form不做任何处理
    	if(isFormField){
    		return new ByteArrayOutputStream();
    	}
    	Calendar today=Calendar.getInstance();
		// shouwen/200612/AAAA.{GUID}.1.doc.zip
    	StringBuffer filePath=new StringBuffer()
								.append(config.getFileRoot()).append(File.separatorChar)
								.append(today.get(Calendar.YEAR)).append(today.get(Calendar.MONTH)+1)
								.append(File.separatorChar)
								.append(this.getTitile()).append(".")
								.append(this.getFileGUID()).append(".")
								.append("tempRiseVersion").append(".")
								.append(this.fileNameExt);
    	
        try {
        	RiseFileOutputStream fos=HandleManager.getRiseFileOutputStream(config,filePath.toString());
        	fileFullName=fos.fileFullName;
        	return fos.outupStream;
		} catch (RiseFileSystemException e) {
			throw new IOException(e.getMessage());
		}
		
    }

    public String getFileGUID(){
    	return fileGUID;
    }
	
	public String getFileNameExt(){
		return fileNameExt;
	}

	public String getTitile(){
		return titile;
	}
	
	public String getFileName(){
		return getName();
	}

	public String getFileFullName() {
		return fileFullName;
	}

	public void setFileFullName(String fileFullName) {
		this.fileFullName = fileFullName;
	}
	
	public String getCurrentUser(){
		return currentUser;
	}
	
	public void setCurrentUser(String currentUser){
		this.currentUser=currentUser;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

}
