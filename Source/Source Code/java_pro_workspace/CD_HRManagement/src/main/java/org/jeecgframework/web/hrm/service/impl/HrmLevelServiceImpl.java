//package org.jeecgframework.web.hrm.service.impl;
//
//import org.jeecgframework.core.common.service.CommonService;
//import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
//import org.jeecgframework.web.hrm.bean.HrmLevel;
//import org.jeecgframework.web.hrm.service.HrmLevelService;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@Transactional
//public class HrmLevelServiceImpl extends CommonServiceImpl implements HrmLevelService {
//
//    @Override
//    public List<HrmLevel> findAll() {
//        String sql="from HrmLevel";
//        List<HrmLevel> levels=this.findHql(sql);
//        return levels;
//    }
//}
