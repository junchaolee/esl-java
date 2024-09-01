package com.xbstar.esl.dao;

import com.xbstar.esl.domain.CallRecord;
import com.xbstar.esl.common.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @Author:janus
 * @Date:2024年8月31日下午11:57:41
 * @Version:1.0.0
 */
@Mapper
public interface CallRecordMapper extends BaseMapper<CallRecord> {

    CallRecord findByuuid(String uuid);
}