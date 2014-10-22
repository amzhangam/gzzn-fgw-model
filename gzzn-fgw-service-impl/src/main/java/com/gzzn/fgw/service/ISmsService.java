package com.gzzn.fgw.service;

public interface ISmsService {

    /**
     * 发送短信
     */
    public void sendSms();
    
    /**
     * 调用服务接口
     * 
     * @param smsNo 接收人号码
     * @param content 内容
     * @return
     * @throws Exception
     */
    public String invoke(String[] smsNo, String content) throws Exception;
}
