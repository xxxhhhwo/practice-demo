<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <#if baseResultMap>
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
            <#list table.fields as field>
                <#if field.keyFlag><#--生成主键排在第一位-->
                    <#if field.keyFlag>
                        <#if field.columnType == "STRING">
        <id column="${field.name}" property="${field.propertyName}" jdbcType="VARCHAR"/>
                        <#elseif field.columnType?contains("DATE") || field.columnType?contains("TIME") || field.columnType?contains("YEAR")>
        <id column="${field.name}" property="${field.propertyName}" jdbcType="TIMESTAMP"/>
                        <#elseif field.columnType == "BLOB">
        <id column="${field.name}" property="${field.propertyName}" jdbcType="BLOB" typeHandler="org.apache.ibatis.type.BlobTypeHandler"/>
                        <#else>
        <id column="${field.name}" property="${field.propertyName}" jdbcType="${field.columnType}"/>
                        </#if>
                    </#if>
                </#if>
            </#list>
            <#list table.commonFields as field><#--生成公共字段 -->
                <#if field.columnType == "STRING">
        <id column="${field.name}" property="${field.propertyName}" jdbcType="VARCHAR"/>
                <#elseif field.columnType?contains("DATE") || field.columnType?contains("TIME") || field.columnType?contains("YEAR")>
        <id column="${field.name}" property="${field.propertyName}" jdbcType="TIMESTAMP"/>
                <#elseif field.columnType == "BLOB">
        <id column="${field.name}" property="${field.propertyName}" jdbcType="BLOB" typeHandler="org.apache.ibatis.type.BlobTypeHandler"/>
                <#else>
        <id column="${field.name}" property="${field.propertyName}" jdbcType="${field.columnType}"/>
                </#if>
            </#list>
            <#list table.fields as field>
                <#if !field.keyFlag><#--生成普通字段 -->
                    <#if field.columnType == "STRING">
        <result column="${field.name}" property="${field.propertyName}" jdbcType="VARCHAR"/>
                    <#elseif field.columnType?contains("DATE") || field.columnType?contains("TIME") || field.columnType?contains("YEAR")>
        <result column="${field.name}" property="${field.propertyName}" jdbcType="TIMESTAMP"/>
                    <#elseif field.columnType == "BLOB">
        <result column="${field.name}" property="${field.propertyName}" jdbcType="BLOB" typeHandler="org.apache.ibatis.type.BlobTypeHandler"/>
                    <#else>
        <result column="${field.name}" property="${field.propertyName}" jdbcType="${field.columnType}"/>
                    </#if>
                </#if>
            </#list>
    </resultMap>
    </#if>

    <#if baseColumnList>

    <sql id="Base_Column_List">
            <#list table.commonFields as field>
        ${field.name},
            </#list>
        ${table.fieldNames}
    </sql>

    </#if>

    <select id="findByQuery" resultType="${package.Entity}.${entity}">
        select
        <include refid="Base_Column_List"/>
        from ${table.name}
        <where>
            <if test="query !=null">

            </if>
        </where>
    </select>

</mapper>
