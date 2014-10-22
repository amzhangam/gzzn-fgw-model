package com.gzzn.fgw.model.pojo;

import java.text.Collator;

/**
 * 
 * <p>Title: TreeNodes</p>
 * <p>Description: 树形菜单节点类</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author Administrator
 * @version 1.0<p>
 * 修改记录:<p>
 * 下面填写修改的内容以及修改的日期
 * 1.2013-9-18上午10:47:13  lhq    new<p>
 */
public class TreeNodesPojo implements Comparable<TreeNodesPojo>{
	
	private String id;//当前节点id
	private String pId;//当前节点的父节点id
	private String name;//当前节点名称
	private String icon;//替换图片名称
	private String file;//对应的文件路径
	private boolean open;//是否打开
	private boolean checked;//当前节点是否被选中
	private boolean nocheck;//设置节点是否隐藏 checkbox /radio [setting.check.enable = true 时有效]
	
	
	public TreeNodesPojo(){
	}
	
	/**构造方法：用于获取逻辑常量JSON数据类*/
	public TreeNodesPojo(String id,String name){
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public boolean getOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean getNocheck() {
		return nocheck;
	}
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
	
	
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int compareTo(TreeNodesPojo o) {
		return Collator.getInstance().getCollationKey(this.getName()).compareTo(Collator.getInstance().getCollationKey(o.getName()));
		//return this.getName().compareTo(o.getName()); 
	}
	
	
	public static void  main(String[] args){
		
		System.out.println("====");
		
		Collator.getInstance().getCollationKey("成智").compareTo(Collator.getInstance().getCollationKey("成惨智"));

		
	}
	
}
