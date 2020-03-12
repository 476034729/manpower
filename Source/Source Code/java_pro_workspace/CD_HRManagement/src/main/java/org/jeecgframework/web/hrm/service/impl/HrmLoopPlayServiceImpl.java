package org.jeecgframework.web.hrm.service.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.jeecgframework.core.common.dao.impl.CommonDao;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.hrm.bean.HrmDepart;
import org.jeecgframework.web.hrm.bean.HrmLoopPlayConfig;
import org.jeecgframework.web.hrm.bean.dto.LoopPlayConfigDTO;
import org.jeecgframework.web.hrm.controller.vo.response.LoopPlayConfigVO;
import org.jeecgframework.web.hrm.service.HrmLoopPlayService;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Wang Yu
 */
@Service
public class HrmLoopPlayServiceImpl extends CommonServiceImpl implements HrmLoopPlayService {

    private static final org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(HrmLoopPlayServiceImpl.class);


    /**
     * 更新配置规则
     * @param configList need Img Id
     * @param optUser
     * @return
     */
    @Override
    @Transactional
    public Boolean saveConfig(List<HrmLoopPlayConfig> configList, TSUser optUser) {

        logger.info(">>HrmLoopPlayServiceImpl:saveConfig:");

        // 先将规则表清空
        String deleteAll = "delete from t_hrm_loop_play_config";
        Integer effectRows = executeSql(deleteAll);
        if (effectRows < 0){
            throw new RuntimeException("saveConfig , 删除失败");
        }
        // 再将新的规则保存
        for (int i = 0 ; i < configList.size(); i++){  // 插入更新人信息
            HrmLoopPlayConfig config = configList.get(i);
            config.setCreateDate(new Date());
            config.setCreateId(optUser.getId());
            config.setCreateUser(optUser.getRealName());
            config.setModifyDate(new Date());
            config.setModifyUser(optUser.getRealName());
            config.setModifyId(optUser.getId());
            config.setRank(i);
        }
        batchSave(configList);
        return Boolean.TRUE;
    }

    @Override
    public List<LoopPlayConfigDTO> listInOrder() {

        logger.info(">>HrmLoopPlayServiceImpl:listInOrder:");

//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String sql = "SELECT c.id, c.img_id as 'imgId', c.rank, c.create_date as 'createDate', c.create_user as 'createUser'," +
                " i.url, c.title, c.description, c.modify_user as 'modifyUser', c.modify_date as 'modifyDate'" +
                "FROM t_hrm_loop_play_config c " +
                "INNER JOIN t_hrm_image_resource i ON c.img_id = i.id " +
                "ORDER BY c.`rank` ASC";
//        Query query = entityManager.createNativeQuery(sql, LoopPlayConfigDTO.class);
        Query query = getSession().createSQLQuery(sql);
        query.setResultTransformer(Transformers.aliasToBean(LoopPlayConfigDTO.class));
        List<LoopPlayConfigDTO> list = query.list();
        return list;
    }
}
