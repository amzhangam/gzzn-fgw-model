package com.gzzn.fgw.service.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.rpc.Call;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzzn.fgw.service.ISmsService;
import com.gzzn.fgw.service.SmsConfig;
import com.gzzn.fgw.service.SmsDao;


@Service
public class SmsServiceImpl implements ISmsService {

    @Autowired
    private SmsDao smsDao;
    @Resource
    private SmsConfig smsConfig;

    @Override
    public void sendSms() {
        System.out.println("come in");
        String sql = "select * from SYS_DX where SFBJ=1";
        Connection conn = null;
        // 存放已发送的短信Id
        List<Integer> idList = new ArrayList<Integer>();
        try {
            // conn = smsDao.getJdbcTemplate().getDataSource().getConnection();
            conn = ConnectionUtils.getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            boolean result = false;

            while (rs.next()) {
                // 最多执行3次
                for (int i = 0; i < 3; i++) {
                    try {

                        System.out.println(this.invoke(new String[] { rs.getString("SJHM") }, rs.getString("SFNR")));

                        idList.add(rs.getInt("DX_ID"));
                        result = true;
                    } catch (Exception e) {
                        result = false;
                        e.printStackTrace();
                    }

                    if (result) {
                        break;
                    }
                }
            }

            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }

        // 更新短信状态
        this.updateStatus(idList);
    }

    private void updateStatus(List<Integer> idList) {
        if (!idList.isEmpty()) {
            String sql = "update SYS_DX set SFBJ=2 where DX_ID=?";
            List<Object[]> list = new ArrayList<Object[]>();
            for (Integer id : idList) {
                list.add(new Object[] { id });
            }
            smsDao.getJdbcTemplate().batchUpdate(sql, list);
        }
    }

    /**
     * 调用服务接口
     * 
     * @param smsNo 接收人号码
     * @param content 内容
     * @return
     * @throws Exception
     */
    public String invoke(String[] smsNo, String content) throws Exception {
        System.out.println("开始发送");
        String result = "-12";

        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(smsConfig.getSmsUrl());
        call.setOperationName(new QName("sendSM"));

        result = String.valueOf(call.invoke(new Object[] { "tzc711", smsConfig.getSmsUserName(), smsConfig.getSmsPassword(), smsNo, content, Math.round(Math.random() * 99999998) }));

        System.out.println("发送结束");
        return result;
    }

}
