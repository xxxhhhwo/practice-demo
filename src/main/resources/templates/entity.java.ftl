package ${package.Entity};

<#assign isHaveDate = "no">
<#list table.importPackages as pkg>
    <#if pkg ?contains("Date") || pkg?contains("Time")>
        <#if isHaveDate == "no">
import java.util.Date;
            <#assign isHaveDate = "yes">
        </#if>
    <#elseif pkg ?contains("Model")>
    <#elseif pkg ?contains("Serializable")>
import java.io.Serializable;
    <#else>
import ${pkg};
    </#if>
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
</#if>
<#if entityLombokModel>
import lombok.Data;
</#if>

/**
* ${entity} Entity
* @Author: ${author}
* @Date: ${date}
*/
<#if swagger2>
@ApiModel(value="${entity} Entity", description="${table.comment!}")
</#if>
<#if entityLombokModel>
@Data
</#if>
<#if table.convert>
@TableName("${table.name}")
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
<#elseif activeRecord>
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "${table.name}")
public class ${entity} {
<#else>
public class ${entity} implements Serializable {
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.commonFields as field>
    /**
    * ${field.comment}
    */
    <#if field.propertyType?contains("Date")>
    private Date ${field.propertyName};
    <#else>
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>

<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.comment!?length gt 0>
    /**
    * ${field.comment}
    */
    <#if swagger2>
    @ApiModelProperty(value = "${field.comment}")
    </#if>
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
        <#if field.keyIdentityFlag>
    @TableId(value = "${field.name}", type = IdType.AUTO)
        <#elseif idType??>
    @TableId(value = "${field.name}", type = IdType.${idType})
        <#elseif field.convert>
    @TableId("${field.name}")
        </#if>
    <#-- 普通字段 -->
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->
        <#if field.convert>
    @TableField(value = "${field.name}", fill = FieldFill.${field.fill})
        <#else>
    @TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
    @TableField("${field.name}")
    </#if>
<#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.name>
    @TableLogic
    </#if>
    <#if field.propertyType?contains("Date")>
    private Date ${field.propertyName};
    <#else>
    private ${field.propertyType} ${field.propertyName};
    </#if>

</#list>
}
