package com.bandaoti.employee.dao;

import com.bandaoti.employee.entity.RequestRole;
import com.bandaoti.employee.entity.RequestRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RequestRoleMapper {
    long countByExample(RequestRoleExample example);

    int deleteByExample(RequestRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RequestRole record);

    int insertSelective(RequestRole record);

    List<RequestRole> selectByExample(RequestRoleExample example);

    RequestRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RequestRole record, @Param("example") RequestRoleExample example);

    int updateByExample(@Param("record") RequestRole record, @Param("example") RequestRoleExample example);

    int updateByPrimaryKeySelective(RequestRole record);

    int updateByPrimaryKey(RequestRole record);
}