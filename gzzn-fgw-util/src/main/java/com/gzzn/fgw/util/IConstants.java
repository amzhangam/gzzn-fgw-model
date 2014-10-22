package com.gzzn.fgw.util;

/**
 * 
 * <p>Title: Iconstants</p>
 * <p>Description: 逻辑常量</p>
 * <p>Copyright: Copyright (c) 2014 ITDCL  All right reserved.</p>
 * <p>Company: ITDCL</p>
 * @author yjf
 * @version 1.0
 *
 * 修改记录:
 * 下面填写修改的内容以及修改的日期
 * 1.2013-11-30 下午6:05:32  yjf    new
 */
public interface IConstants {

	/*数据删除标记*/
	char DELETE_FLAG_TRUE = '1';//已删除
	char DELETE_FLAG_FALSE = '0';//未删除
	
	/*数据删除标记：2014-06-13 lhq加入*/
	public static final Integer DEL_FLAG_TRUE = 1;//已删除
	public static final Integer DEL_FLAG_FALSE = 0;//未删除

	/*
	 * 数据字典类型
	 */
	public static final String SJZD_TYPE_DWXZ = "单位性质";//单位性质
	
	public static final String SJZD_TYPE_SZQY = "所在区域";//所在区域
	
	public static final String SJZD_TYPE_XMZT = "项目状态";//项目状态
	
	public static final String SJZD_TYPE_FJLX = "附件类型";//附件类型
	
	public static final String SJZD_TYPE_XMSHLX = "项目审核类型";//项目审核类型
	
	public static final String SJZD_TYPE_XMLX = "项目类型";//项目类型
	
	/*
	 * 单位性质
	 */
	public static final Integer DWXZ_CODE_1 = 1;//国有企业
	public static final Integer DWXZ_CODE_2 = 2;//民营企业
	public static final Integer DWXZ_CODE_3 = 3;//外资企业
	public static final Integer DWXZ_CODE_4 = 4;//事业单位
	public static final Integer DWXZ_CODE_5 = 5;//行政单位
	
	public static final String DWXZ_NAME_1 = "国有企业";
	public static final String DWXZ_NAME_2 = "民营企业";
	public static final String DWXZ_NAME_3 = "外资企业";
	public static final String DWXZ_NAME_4 = "事业单位";
	public static final String DWXZ_NAME_5 = "行政单位";
	
	/*
	 * 用户角色类型
	 */
	public static final Integer ROLE_TYPE_FGW_CODE_1 = 1;//处长
	public static final Integer ROLE_TYPE_FGW_CODE_2 = 2;//副处长
	public static final Integer ROLE_TYPE_FGW_CODE_3 = 3;//经办
	
	public static final Integer ROLE_TYPE_ZGDW_CODE_1 = 1;//分管领导
	public static final Integer ROLE_TYPE_ZGDW_CODE_2 = 2;//联系人
	
	public static final String ROLE_TYPE_FGW_NAME_1 = "处长";
	public static final String ROLE_TYPE_FGW_NAME_2 = "副处长";
	public static final String ROLE_TYPE_FGW_NAME_3 = "经办";
	
	public static final String ROLE_TYPE_ZGDW_NAME_1 = "分管领导";
	public static final String ROLE_TYPE_ZGDW_NAME_2 = "联系人";
	
	/*
	 * 用户类型
	 */
	public static final Integer USER_TYPE_1 = 1;//业主用户
	
	public static final Integer USER_TYPE_2 = 2;//主管单位用户
	
	public static final Integer USER_TYPE_3 = 3;//市发改委用户
	
	public static final Integer USER_TYPE_4 = 4;//区县发展和改革局用户
	
	public static final Integer USER_TYPE_5 = 5;//超级管理员
	
	public static final String USER_TYPE_NAME_1 = "业主用户";//
	
	public static final String USER_TYPE_NAME_2 = "主管单位用户";//
	
	public static final String USER_TYPE_NAME_3 = "市发改委用户";//
	
	public static final String USER_TYPE_NAME_4 = "区县发展和改革局用户";//
	
	public static final String USER_TYPE_NAME_5 = "系统管理员";//
	
	
	
	public static final Integer XMFL_1 = 1;//基本建设投资类项目
	public static final Integer XMFL_2 = 2;//更新改造投资类项目
	public static final Integer XMFL_3 = 3;//其他固定资产投资类项目
	
	public static final String XMFL_NAME_1 = "基本建设投资类项目";//
	public static final String XMFL_NAME_2 = "更新改造投资类项目";//
	public static final String XMFL_NAME_3 = "其他固定资产投资类项目";//
	
	/*
	 * 单位类型
	 */
	public static final Integer ORGAN_TYPE_1 = 1;//业主单位
	
	public static final Integer ORGAN_TYPE_2 = 2;//主管单位
	
	public static final Integer ORGAN_TYPE_3 = 3;//发改委单位
	
	public static final Integer ORGAN_TYPE_4 = 4;//区县发展和改革局
	
	public static final String ORGAN_TYPE_NAME_1 = "业主单位";
	
	public static final String ORGAN_TYPE_NAME_2 = "主管单位";
	
	public static final String ORGAN_TYPE_NAME_3 = "发改委单位";
	
	public static final String ORGAN_TYPE_NAME_4 = "区县发展和改革局";
	
	
	/*
	 * 审核状态
	 */
	public static final Integer SHENHE_STATUS_1 = 1;//待审批
	
	public static final Integer SHENHE_STATUS_2 = 2;//审批通过
	
	public static final Integer SHENHE_STATUS_3 = 3;//审批不通过
	
	public static final Integer SHENHE_STATUS_4 = 4;//4-销户
	
	public static final String SHENHE_STATUS_NAME_1 = "待审批";
	
	public static final String SHENHE_STATUS_NAME_2 = "审批通过";
	
	public static final String SHENHE_STATUS_NAME_3 = "审批不通过";
	
	public static final String SHENHE_STATUS_NAME_4 = "销户";
	
	/*
	 * 数据字典类型
	 */
	public static final String DICTIONARY_ITEM_DWXZ = "单位性质";
	
	public static final String DICTIONARY_ITEM_SZQY = "所在区域";
	
	public static final String DICTIONARY_ITEM_JS = "角色";
	
	public static final String DICTIONARY_ITEM_XMZT = "项目状态";
	
	public static final String DICTIONARY_ITEM_FJLX = "附件类型";
	
	public static final String DICTIONARY_ITEM_XMSHLX = "项目审核类型";
	
	public static final String DICTIONARY_ITEM_NF = "年份";
	
	public static final String DICTIONARY_ITEM_XMLX = "项目类型";
	
	public static final String DICTIONARY_ITEM_ZJXZ = "资金性质";
	
//	public static final String DICTIONARY_ITEM_HYFL = "行业分类";
	
	public static final String DICTIONARY_ITEM_XMJD = "项目进度";
	
	public static final String DICTIONARY_ITEM_XMFL = "项目分类";
	
	public static final String DICTIONARY_ITEM_LSH = "流水号";//用于提取SysDictionaryitems里面的流水号
	
	/*
	 * 项目申报类型
	 */
	public static final Integer DECLARE_TYPE_CODE_1 = 1;
	
	public static final Integer DECLARE_TYPE_CODE_2 = 2;
	
	public static final String DECLARE_TYPE_NAME_1 = "业主提交";
	
	public static final String DECLARE_TYPE_NAME_2 = "主管单位提交";
	
	/*
	 * 附件类型
	 */
	public static final Integer FJLX_0 = 0;//立项批复
	public static final Integer FJLX_1 = 1;//规划选址
	public static final Integer FJLX_2 = 2;//用地预审
	public static final Integer FJLX_3 = 3;//环境影响评价
	public static final Integer FJLX_4 = 4;//节能评估审查
	public static final Integer FJLX_5 = 5;//可研批复
	public static final Integer FJLX_6 = 6;//初步设计及概算
	public static final Integer FJLX_7 = 7;//招标投标情况
	public static final Integer FJLX_8 = 8;//征地拆迁
	public static final Integer FJLX_9 = 9;//其他前期工作
	public static final Integer FJLX_10 = 10;//项目申报依据
	public static final Integer FJLX_11 = 11;//工程形象进度
	public static final Integer FJLX_12 = 12;//存在的问题
	public static final Integer FJLX_13 = 13;//项目书面材料
	public static final Integer FJLX_14 = 14;//项目月报
	public static final Integer FJLX_15 = 15;//项目现场图片缩略图
	public static final Integer FJLX_16 = 16;//项目现场图片
	public static final Integer FJLX_17 = 17;//(重大项目)社会风险评估)
	public static final Integer FJLX_18 = 18;//施工图设计与预算
	public static final Integer FJLX_19 = 19;//需要补充的其它事项
	public static final Integer FJLX_20 = 20;//投资项目基本情况描述(对于更新改造类项目必填)
	
	/**
	 * 项目状态
	 */
	public static final Integer XMZT_0 = 0;//草稿
	public static final Integer XMZT_1 = 1;//待主管单位审核
	public static final Integer XMZT_2 = 2;//待各处室处长审核
	public static final Integer XMZT_3 = 3;//待各处室经办审核
	public static final Integer XMZT_4 = 4;//待各处室副处长审核
	public static final Integer XMZT_5 = 5;//待各处室处长确认审核
	public static final Integer XMZT_6 = 6;//待投资处处长审核
	public static final Integer XMZT_7 = 7;//待投资处经办审核
	public static final Integer XMZT_8 = 8;//待投资处副处长审核
	public static final Integer XMZT_9 = 9;//待投资处处长确认审核
	public static final Integer XMZT_10 = 10;//正常项目
	public static final Integer XMZT_11 = 11;//过期
	public static final Integer XMZT_12 = 12;//审核不通过
	public static final Integer XMZT_13 = 13;//转评中心评审
	public static final Integer XMZT_14 = 14;//研评中心评审中
	public static final Integer XMZT_15 = 15;//研评中心已评审通过
	public static final Integer XMZT_16 = 16;//研评中心评审不通过
	
	
	/**
	 * 审核类型
	 */
	public static final Integer SHLX_1 = 1;//主管审核
	public static final Integer SHLX_2 = 2;//处室处长审核
	public static final Integer SHLX_3 = 3;//处室经办审核
	public static final Integer SHLX_4 = 4;//处室副处长审核
	public static final Integer SHLX_5 = 5;//处室处长确认审核
	public static final Integer SHLX_6 = 6;//投资处处长审核
	public static final Integer SHLX_7 = 7;//投资处经办审核
	public static final Integer SHLX_8 = 8;//投资处副处长审核
	public static final Integer SHLX_9 = 9;//投资处处长确认审核
	
	/**
	 * 项目新的状态表示
	 */
	public static final Integer XMXZT_1 = 1;//草稿
	public static final Integer XMXZT_2 = 2;//审核不通过
	public static final Integer XMXZT_3 = 3;//主管单位待审核
	public static final Integer XMXZT_4 = 4;//主管单位审核通过
	public static final Integer XMXZT_5 = 5;//各处室待审核
	public static final Integer XMXZT_6 = 6;//各处室审核通过
	public static final Integer XMXZT_7 = 7;//投资处待审核
	public static final Integer XMXZT_8 = 8;//投资处审核通过
	
	public static final String XMXZT_1_NAME = "草稿";//草稿
	public static final String XMXZT_2_NAME = "审核不通过";//审核不通过
	public static final String XMXZT_3_NAME = "主管单位待审核";//主管单位待审核
	public static final String XMXZT_4_NAME = "主管单位审核通过";//主管单位审核通过
	public static final String XMXZT_5_NAME = "各处室待审核";//各处室待审核
	public static final String XMXZT_6_NAME = "各处室审核通过";//各处室审核通过
	public static final String XMXZT_7_NAME = "投资处待审核";//投资处待审核
	public static final String XMXZT_8_NAME = "投资处审核通过";//投资处审核通过
	
	
	/**
	 * 月报状态
	 */
	public static final Integer XMYBZT_0 = 0;//暂存
	public static final Integer XMYBZT_1 = 1;//提交
	
	/*
	 * 上传路径
	 */
	public static final String UPLOAD_URL = "ss";
	
	/**
	 * 审核结果
	 */
	public static final String SHJG_1 = "1";//审核通过
	public static final String SHJG_2 = "2";//审核不通过
	public static final String SHJG_3 = "3";//转交其他处室
	
	/**
	 * 主管单位审核通过后去向
	 */
	public static final String ZGDW_QX_1 = "1";//发改委各处室(包括投资处)
	
	/**
	 * 发改委各处室审核通过后去向(非投资处)
	 */
	public static final String FGWGCS_QX_1 = "1";//发往投资处
	public static final String FGWGCS_QX_2 = "2";//交由经办
	public static final String FGWGCS_QX_3 = "3";//转交其他处室
	
	/**
	 * 发改委投资处审核通过后去向
	 */
	public static final String FGWTZS_QX_1 = "1";//发往投资处
	public static final String FGWTZS_QX_2 = "2";//交由经办
	
	/**
	 * 是否重大项目(由发改委部门指定)
	 */
	public static final Integer SFZDXM_0 = 0;//否
	
	public static final Integer SFZDXM_1 = 1;//是
	
	/**
	 * 是否是转交过来的（投资处筛选各处室汇总到投资处的项目用）
	 */
	public static final Integer SFZJ_0 = 0;//否
	
	public static final Integer SFZJ_1 = 1;//是
	
	
	
	/**
	 * 辖区类型0-	国家、1-	省、2-市、3-区、4-街、5-社区
	 */
	public static final Integer XQLX_0 = 0;
	public static final Integer XQLX_1 = 1;
	public static final Integer XQLX_2 = 2;
	public static final Integer XQLX_3 = 3;
	public static final Integer XQLX_4 = 4;
	public static final Integer XQLX_5 = 5;
	public static final String  XQLX_NAME_0 = "国家";
	public static final String  XQLX_NAME_1= "省";
	public static final String  XQLX_NAME_2= "市";
	public static final String  XQLX_NAME_3= "区";
	public static final String  XQLX_NAME_4= "街";
	public static final String  XQLX_NAME_5= "社区";
	
	/**
	 * 区县项目情况：重点项目标注（0-非重点项目；1为省重点项目；2为市重点项目）
	 * */
	public static final Integer ZDXMBZ_0 = 0;
	public static final Integer ZDXMBZ_1 = 1;
	public static final Integer ZDXMBZ_2 = 2;
	public static final String ZDXMBZ_NAME_0 = "非重点项目";
	public static final String ZDXMBZ_NAME_1 = "省重点项目";
	public static final String ZDXMBZ_NAME_2 = "市重点项目";
	
	/**
	 * 区县项目汇总统计，项目类别
	 * 2013年以来立项（审批、核准、备案）项目
	 * 完工项目
	 * 在建项目
	 * */
	public static final String XMLB_NAME_0 = "未开工项目";
	public static final String XMLB_NAME_1 = "完工项目";
	public static final String XMLB_NAME_2 = "在建项目";
	
	
	/*
	 * 项目总投资规模
	 */
	public static final Integer EXPECTFINISHINVEST_TYPE_1 = 1;//1000万以下
	
	public static final Integer EXPECTFINISHINVEST_TYPE_2 = 2;//1000万（含）以上
	
	/*
	 * 审核项目总投资规模
	 */
	public static final Integer SHENHE_EXPECTFINISHINVEST_TYPE_1 = 1;//等于0或没有输入
	
	public static final Integer SHENHE_EXPECTFINISHINVEST_TYPE_2 = 2;//大于0
	
	public static final Integer SHENHE_EXPECTFINISHINVEST_TYPE_3 = 3;//1000万以下
	
	public static final Integer SHENHE_EXPECTFINISHINVEST_TYPE_4 = 4;//1000万（含）以上
	
	/**
	 * 
	 */
	public static final Double XMZTZ = 1000D;//1000万（含）以上
	
	
	/*
	 * 是否呈投资处汇总
	 */
	public static final String SFCTZCHZ_TYPE_1 = "1";//(是)呈投资处待汇总审核项目
	
	public static final String SFCTZCHZ_TYPE_2 = "2";//(否)其它待投资处审核项目
	
	/**
	 * 发改委单位Id
	 */
	public static final Integer FGW_ORGANIZATION_ID = 455;//发改委单位Id

	
}
