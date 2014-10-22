package com.gzzn.fgw.webUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 *文件流化
 */
public class ReadFileByteUtil {
	
	/**
	 * 将文件转化成字节
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(File file) throws IOException{
			byte[] bytes=null;
			if(file!=null){
				if(file.length()>Integer.MAX_VALUE){
					System.out.println("文件的长度超过了int");
					return null;
				}
				int flength=(int)file.length();
				InputStream is=new FileInputStream(file);
				bytes=new byte[flength];
				int offset=0;
				int numRead=0;
				while(offset<bytes.length&&(numRead=is.read(bytes, offset, bytes.length-offset))>=0){
					offset+=numRead;
				}
				if(offset<bytes.length){
					System.out.println("字节的长度与文件的长度不一致！");
					return null;
				}
				is.close();
			}
			return bytes;
		}
	}
