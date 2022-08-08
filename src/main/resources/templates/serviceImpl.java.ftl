package ${package.ServiceImpl};


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package.Service}.${table.serviceName};
import ${package.Mapper}.${table.mapperName};


/**
* ${table.serviceImplName}
* @Author ${author}
* @Date ${date}
**/
@Service

public class ${table.serviceImplName} extends BaseServiceImpl<${table.mapperName}, ${entity}> implements ${table.serviceName} {



}