package com.gzzn.fgw.webUtil;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javassist.expr.NewArray;

import javax.imageio.ImageIO;

/**
 * 一、有两点需要注意：

    1、Object 对象必须是可序列化对象 。


    2、可序列化的 Object 对象都可以转换为一个磁盘文件；反过来则不一定成立，只有序列
         化文件才可以转换为 Object 对象。


二、相关的转换方法：
 */
public class Byte_File_Object {
	/** 
     * 文件转化为字节数组
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Long length = f.length();
            byte[] b = new byte[length.intValue()];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return null;
    }

    /**
     * 把字节数组保存为一个文件
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
            fstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 从字节数组获取对象
     */
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
//        if (objBytes == null || objBytes.length == 0) {
//            return null;
//        }
    	String str = "我爱你";
    	File f = new File("E:\\image\\a.jpg");
//    	objBytes = getBytesFromObject(str);
    	objBytes = getBytesFromFile(f);
    	objBytes = getBytesFromObject(objBytes);
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }

    /**
     * 从对象获取一个字节数组
     */
    public static byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }
	
	public static void main(String[] args){

//	    File file = new File("AdbeRdr1010_zh_CN.exe");
//	    byte[] bytes = getBytesFromFile(file);
//	    try {
//		String xml = new String(bytes,"UTF-8");
//		System.out.println(xml);
//	    }
//	    catch (UnsupportedEncodingException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	    }
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    System.out.println(sdf.format(new Date()));
	}
	
	public static Image aa(byte[] b) throws IOException{
		InputStream buffin = new ByteArrayInputStream(b,0,b.length);
		BufferedImage img = ImageIO.read(buffin);
		return img;
	}
}

