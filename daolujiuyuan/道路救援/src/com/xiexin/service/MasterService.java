package com.xiexin.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xiexin.bean.MasterExample;
import com.xiexin.bean.Master;

public interface MasterService {
   
    long countByExample(MasterExample example);

    int deleteByExample(MasterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Master record);

    int insertSelective(Master record);

    List<Master> selectByExample(MasterExample example);

    Master selectByPrimaryKey(Integer id);
  
    int updateByExampleSelective(@Param("record") Master record, @Param("example") MasterExample example);

    int updateByExample(@Param("record") Master record, @Param("example") MasterExample example);

    int updateByPrimaryKeySelective(Master record);

    int updateByPrimaryKey(Master record);

}
