package com.hgsoft.zengzhiyingyong.module.rbac.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgsoft.zengzhiyingyong.common.context.SecurityContextHolder;
import com.hgsoft.zengzhiyingyong.common.domain.Page;
import com.hgsoft.zengzhiyingyong.module.rbac.dao.*;
import com.hgsoft.zengzhiyingyong.module.rbac.domain.*;
import com.hgsoft.zengzhiyingyong.util.DateUtil;
import com.hgsoft.zengzhiyingyong.util.InitData;
import com.hgsoft.zengzhiyingyong.util.MD5Coder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by WQP on 2018/10/17.
 * 卡余连续性检验Service
 */
@Service
public class BalanceService {

    private Logger logger = LoggerFactory.getLogger(BalanceService.class);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");

    @Autowired
    private CardDao cardDao;

    @Autowired
    private BusinessDao businessDao;

    
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ConsumeDao consumeDao;

    @Autowired
    private VoucherDao voucherDao;

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private ConsumeService consumeService;




}