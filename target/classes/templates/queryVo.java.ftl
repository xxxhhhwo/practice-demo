package ${package.Entity};

import com.framework.vo.VQuery;
<#if swagger2>
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Getter;
import lombok.Setter;
</#if>

/**
* ${entity}查询 Vo
* @Author: ${author}
* @Date: ${date}
*/
<#if entityLombokModel>
@Getter
@Setter
</#if>
public class V${entity}Query extends VQuery {

}
