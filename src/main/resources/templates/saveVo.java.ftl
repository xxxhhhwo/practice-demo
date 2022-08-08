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
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Getter;
import lombok.Setter;
</#if>

/**
* ${entity}保存 Vo
* @Author: ${author}
* @Date: ${date}
*/
<#if entityLombokModel>
@Getter
@Setter
</#if>
public class V${entity}Save implements Serializable {

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
    <#-- 普通字段 -->
    <#if field.propertyType?contains("Date")>
    private Date ${field.propertyName};
    <#else>
    private ${field.propertyType} ${field.propertyName};
    </#if>

</#list>
}
