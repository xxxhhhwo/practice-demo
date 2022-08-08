package ${package.Controller};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

/**
* ${table.controllerName}
* @Author ${author}
* @Date ${date}
**/
@Api(description = "${entity ?cap_first} Controller")
@RestController
@RequestMapping("/${entity ?uncap_first}")
public class ${table.controllerName} {

    @Autowired
    private  ${table.serviceName} ${table.serviceName ?uncap_first};

    /**
    * 新增
    *
    * @param ${entity ?uncap_first}
    */
    @ApiOperation(value = "add", notes = "")
    @PostMapping("/add")
    public ResponseEntity<ResultMsg> add(@RequestBody ${entity} ${entity ?uncap_first}) {
        ${table.serviceName ?uncap_first}.save(${entity ?uncap_first});
    }

    /**
    * 删除
    *
    * @param ids
    */
    @ApiOperation(value = "delete", notes = "")
    @GetMapping("/delete")
    public ResponseEntity<ResultMsg> delete(@RequestParam("ids") Long... ids) {
        ${table.serviceName ?uncap_first}.removeByIds(Arrays.asList(ids));
    }

    /**
    * 更新
    *
    * @param ${entity ?uncap_first}
    */
    @ApiOperation(value = "update", notes = "")
    @PostMapping("/update")
    public ResponseEntity<ResultMsg> update(@RequestBody ${entity} ${entity ?uncap_first}) {
        ${table.serviceName ?uncap_first}.updateById(${entity ?uncap_first});
    }

    /**
    * 详情
    *
    * @param id
    * @return
    */
    @ApiOperation(value = "detail", notes = "")
    @GetMapping("/detail")
    public ResponseEntity<ResultMsg> detail(@RequestParam("id") Long id) {
        return ${table.serviceName ?uncap_first}.getById(id);
    }

    /**
    * 分页
    *
    * @param query
    * @return
    */
    @ApiOperation(value = "page", notes = "")
    @PostMapping("/page")
    public ResponseEntity<ResultMsg> getPage(@RequestBody   ${entity} ${entity ?uncap_first}) {
        return ${table.serviceName ?uncap_first}.page(${entity ?uncap_first});
    }

}