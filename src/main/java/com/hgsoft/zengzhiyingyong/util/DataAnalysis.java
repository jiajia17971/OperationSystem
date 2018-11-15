package com.hgsoft.zengzhiyingyong.util;

import com.hgsoft.zengzhiyingyong.module.rbac.domain.AmtFlowEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.AnalysisEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.ConsumeEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.TransferEntity;
import com.hgsoft.zengzhiyingyong.module.rbac.service.CardService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.SimpleAttributeSet;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataAnalysis {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    private static Logger logger = LoggerFactory.getLogger(DataAnalysis.class);

    public static   List<Integer> retrievalData(List<ConsumeEntity> consumeList, List<TransferEntity> transferList){
        List<Integer> idlist = new ArrayList<Integer>();
        List<AmtFlowEntity> aList = new ArrayList<AmtFlowEntity>();

        if(consumeList!=null&&consumeList.size()!=0){
            for (ConsumeEntity con:consumeList) {
                AmtFlowEntity amt = new AmtFlowEntity();
                amt.setIdCode(con.getId());
                amt.setDataType(1);
                amt.setBusinessTime(con.getBusinessTime());
                amt.setOptAmount(con.getAmount());
                amt.setPostBalance(con.getCardBalance());
                amt.setTransNo(con.getSeriNo());
                amt.setIdCode(con.getId());
                aList.add(amt);
            }
        }else{
            return null;
        }


        sort(aList,"transNo","desc");

        try {
            if (transferList != null && transferList.size() != 0) {
                for (TransferEntity trans : transferList) {
                    AmtFlowEntity amt = new AmtFlowEntity();
                    amt.setIdCode(trans.getId());
                    amt.setDataType(2);
                    amt.setBusinessTime(trans.getCreateTime());
                    amt.setOptAmount(trans.getTransferAmt());
                    amt.setPostBalance(null);
                    for (int i = 0;i<aList.size()-1;i++) {
                       long transTime =  sdf.parse(trans.getCreateTime()).getTime();
                        if ( (transTime > sdf.parse(aList.get(i+1).getBusinessTime()).getTime()&&transTime<sdf.parse(aList.get(i).getBusinessTime()).getTime())||(transTime < sdf.parse(aList.get(i+1).getBusinessTime()).getTime()&&transTime > sdf.parse(aList.get(i).getBusinessTime()).getTime())) {
                            aList.add(i+1,amt);
                        }
                    }
                }
            }


            for (int i=0;i<aList.size();i++) {
                if(aList.get(i).getDataType()==1){//第一条为消费流水
                   BigDecimal nbalance =  aList.get(i).getPostBalance();
                   BigDecimal noptAmt = aList.get(i).getOptAmount();

                   BigDecimal transferAmount = new BigDecimal(0);;
                   BigDecimal pbalance =  new BigDecimal(0);
                   BigDecimal poptAmt = new BigDecimal(0);

                    for(int j=i+1;j<aList.size()&&j>i;j++){
//                    int j= i+ 1;
                        if(aList.get(j).getDataType()==1&&j<aList.size()){
                            logger.info(i + "=="+  nbalance+ "--"+ noptAmt);
                            logger.info(j + "=="+ aList.get(j).getPostBalance()+ "--"+ aList.get(j).getOptAmount());
                            if(nbalance.add(noptAmt).equals(aList.get(j).getPostBalance())){
                                logger.info(i + "=======");
                                break;//流水i和流水j金额连续
                            }else{
                                logger.info(i + "++++++" );
                                logger.info(i + ">>>id" +aList.get(i).getIdCode() );
                                //流水i和流水j金额不连续
                                //返回流水i和流水j
                                if(!idlist.contains(aList.get(i).getIdCode())){
                                    idlist.add(aList.get(i).getIdCode());
                                }
                                if(!idlist.contains(aList.get(j).getIdCode())){
                                    idlist.add(aList.get(j).getIdCode());
                                }

                                break;
                            }
                        }else{
                            //流水j为圈存流水
                            nbalance = nbalance.subtract(aList.get(j).getOptAmount());
                        }
                    }

                }else{//第一条为圈存流水

                }
            }
            return idlist;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }





    /**
     * list排序工具
     * @param targetList
     * @param sortField
     * @param sortMode
     */
    public static void sort(List<AmtFlowEntity> targetList, final String sortField, final String sortMode) {

        Collections.sort(targetList, new Comparator() {
            public int compare(Object obj1, Object obj2) {
                int retVal = 0;
                try {
                    //首字母转大写
                    String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");
                    String methodStr="get"+newStr;

                    Method method1 = ((AmtFlowEntity)obj1).getClass().getMethod(methodStr, null);
                    Method method2 = ((AmtFlowEntity)obj2).getClass().getMethod(methodStr, null);
                    if (sortMode != null && "desc".equals(sortMode)) {
                        retVal = method2.invoke(((AmtFlowEntity) obj2), null).toString().compareTo(method1.invoke(((AmtFlowEntity) obj1), null).toString()); // 倒序
                    } else {
                        retVal = method1.invoke(((AmtFlowEntity) obj1), null).toString().compareTo(method2.invoke(((AmtFlowEntity) obj2), null).toString()); // 正序
                    }
                } catch (Exception e) {
                    throw new RuntimeException();
                }
                return retVal;
            }
        });
    }

    public static void  main(String[] args){
        ConsumeEntity con1 = new ConsumeEntity();
        con1.setAmount(new BigDecimal(48015));
        con1.setCardBalance(new BigDecimal(504128));
        con1.setId(226397736);
        con1.setSeriNo(2);
        con1.setBusinessTime("2018-10-14 11:09:18.000");
        ConsumeEntity con2 = new ConsumeEntity();
        con2.setAmount(new BigDecimal(46350));
        con2.setCardBalance(new BigDecimal(542143));
        con2.setId(226382471);
        con2.setSeriNo(4);
        con2.setBusinessTime("2018-10-13 23:05:41.000");
        ConsumeEntity con3 = new ConsumeEntity();
        con3.setAmount(new BigDecimal(3500));
        con3.setCardBalance(new BigDecimal(588493));
        con3.setId(226383802);
        con3.setSeriNo(4);
        con3.setBusinessTime("2018-10-13 18:14:09.000");
        ConsumeEntity con4 = new ConsumeEntity();
        con4.setAmount(new BigDecimal(62000));
        con4.setCardBalance(new BigDecimal(595993));
        con4.setId(226381017);
        con4.setSeriNo(6);
        con4.setBusinessTime("2018-10-13 17:26:33.000");
        ConsumeEntity con5 = new ConsumeEntity();
        con5.setAmount(new BigDecimal(30500));
        con5.setCardBalance(new BigDecimal(657993));
        con5.setId(226372969);
        con5.setSeriNo(8);
        con5.setBusinessTime("2018-10-13 03:26:33.000");
        List<ConsumeEntity> conList = new ArrayList<ConsumeEntity>();
        conList.add(con1);
        conList.add(con2);
        conList.add(con3);
        conList.add(con4);
        conList.add(con5);




        List<TransferEntity> tranList = new ArrayList<TransferEntity>();
        TransferEntity tran1 = new TransferEntity();
        tran1.setId(1231);
        tran1.setTransferAmt(new BigDecimal(10000));
        tran1.setTransferTime("2018-10-14 10:14:09.000");
        TransferEntity tran2 = new TransferEntity();
        tran2.setId(1232);
        tran2.setTransferAmt(new BigDecimal(200));
        tran2.setTransferTime("2018-10-13 03:26:33.000");
        TransferEntity tran3 = new TransferEntity();
        tran3.setId(1233);
        tran3.setTransferAmt(new BigDecimal(200));
        tran3.setTransferTime("2018-10-13 03:26:33.000");
        TransferEntity tran4 = new TransferEntity();
        tran4.setId(1234);
        tran4.setTransferAmt(new BigDecimal(200));
        tran4.setTransferTime("2018-10-13 03:26:33.000");
        List<TransferEntity> tList = new ArrayList<TransferEntity>();
        tList.add(tran1);
        tList.add(tran2);
        tList.add(tran3);
        tList.add(tran4);
        System.out.print(retrievalData(conList,tList).toString());

    }


}
