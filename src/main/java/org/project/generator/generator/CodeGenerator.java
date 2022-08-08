package org.project.generator.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import freemarker.template.utility.StringUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author jyj
 * @Date 2020/4/21
 **/
public class CodeGenerator {

    /**
     * 数据源配置
     */
//    private static final String URL = "jdbc:mysql://127.0.0.1:3308/project?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8";
//    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
//    private static final String USERNAME = "root";
//    private static final String password = "123456";

    private static final String URL = "jdbc:mysql://10.10.22.25:3306/cms_test_0622?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "dev_write";
    private static final String password = "gcloud123456";

    //项目目录
    private static final String projectPath = System.getProperty("user.dir");
    private static final String javaDir = "/src/main/java/";
    private static final String resourcesDir = "/src/main/resources/";

    public static void main(String[] args) {

        //生成单模块Mapper和Mapper.xml在一起的代码
        singleModuleMapperAndXmlTogether();

        //生成单模块Mapper和Mapper.xml分开的代码（Mapper位置还是有Mapper.xml，记得删除）
//        singleModuleMapperAndXmlSeparate();

        //生成多模块代码
//        multiModule();
    }

    /**
     * 单模块（Controller、Service、Dao在同一工程下） Mapper和Mapper.xml在一起
     */
    public static void singleModuleMapperAndXmlTogether() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = getGlobalConfig(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig();
        mpg.setDataSource(dsc);

        // 包配置
        String parentPackage = "com.bettem";
        String mapperName = "mapper";
        String serviceName = "service";
        String serviceImplName = "service.impl";
        String entityName = "entity";
        String controllerName = "controller";
        String xmlName = "mapper";
        PackageConfig pc = getPackageConfig(parentPackage, mapperName, entityName, serviceName, serviceImplName, controllerName, xmlName);
        mpg.setPackageInfo(pc);

        // 模板配置
        TemplateConfig templateConfig = getTemplateConfig();
        mpg.setTemplate(templateConfig);

        //配置使用的模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 策略配置
        StrategyConfig strategy = getStrategyConfig();
        mpg.setStrategy(strategy);
        mpg.execute();
    }

    /**
     * 单模块（Controller、Service、Dao在同一工程下） Mapper和Mapper.xml分开，即Mapper.xml放在resources下
     */
    public static void singleModuleMapperAndXmlSeparate() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = getGlobalConfig(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig();
        mpg.setDataSource(dsc);

        // 包配置
        String parentPackage = "org.project.generator.module";
        String mapperName = "mapper";
        String serviceName = "service";
        String serviceImplName = "service.impl";
        String entityName = "entity";
        String controllerName = "controller";
        String xmlName = "mapper";
        PackageConfig pc = getPackageConfig(parentPackage, mapperName, entityName, serviceName, serviceImplName, controllerName, xmlName);
        mpg.setPackageInfo(pc);

        // 模板配置
        TemplateConfig templateConfig = getTemplateConfig();
        mpg.setTemplate(templateConfig);

        //配置使用的模板引擎
        MyFreemarkerTemplateEngine freemarkerTemplateEngine = new MyFreemarkerTemplateEngine();
        freemarkerTemplateEngine.setEnableDefaultOutputXmlFile(false);
        mpg.setTemplateEngine(freemarkerTemplateEngine);

        // 自定义配置
        InjectionConfig cfg = getMapperInjectionConfig(pc);
        mpg.setCfg(cfg);

        // 策略配置
        StrategyConfig strategy = getStrategyConfig();
        mpg.setStrategy(strategy);
        mpg.execute();
    }

    /**
     * 多模块
     */
    public static void multiModule() {
        // 代码生成器
        MyAutoGenerator mpg = new MyAutoGenerator();
        mpg.setEnableDefaultOutputFile(false);

        // 全局配置
        GlobalConfig gc = getGlobalConfig(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig();
        mpg.setDataSource(dsc);

        // 包配置
        String parentPackage = "org.project";
        String mapperName = "dao.mapper";
        String serviceName = "service";
        String serviceImplName = "service.impl";
        String entityName = "dao.entity";
        String controllerName = "backend.controller";
        String xmlName = "dao.mapper";
        PackageConfig pc = getMultiModulePackageConfig(parentPackage, mapperName, entityName, serviceName, serviceImplName, controllerName, xmlName);
        mpg.setPackageInfo(pc);

        MyFreemarkerTemplateEngine freemarkerTemplateEngine = new MyFreemarkerTemplateEngine();
        freemarkerTemplateEngine.setEnableDefaultOutputFile(false);
        //配置使用的模板引擎
        mpg.setTemplateEngine(freemarkerTemplateEngine);

        // 自定义配置
        InjectionConfig cfg = getMultiModuleInjectionConfig("backend", "service", "dao", pc);
        mpg.setCfg(cfg);

        // 策略配置
        StrategyConfig strategy = getStrategyConfig();
        mpg.setStrategy(strategy);
        mpg.execute();
    }

    /**
     * 数据源配置
     *
     * @return
     */
    public static DataSourceConfig getDataSourceConfig() {
        return new DataSourceConfig()
                .setUrl(URL)
                .setDriverName(DRIVER_NAME)
                .setUsername(USERNAME)
                .setPassword(password);
    }

    /**
     * 包配置
     *
     * @return
     */
    public static PackageConfig getPackageConfig(String parentPackage, String mapperName, String entityName, String serviceName, String serviceImplName, String controllerName, String xmlName) {
        return new PackageConfig()
                .setParent(parentPackage)
                .setMapper(mapperName)
                .setEntity(entityName)
                .setService(serviceName)
                .setServiceImpl(serviceImplName)
                .setController(controllerName)
                .setXml(xmlName);
    }

    /**
     * 包配置
     *
     * @return
     */
    public static PackageConfig getMultiModulePackageConfig(String parentPackage, String mapperName, String entityName, String serviceName, String serviceImplName, String controllerName, String xmlName) {
        return new PackageConfig()
                .setParent(parentPackage)
                .setMapper(mapperName)
                .setEntity(entityName)
                .setService(serviceName)
                .setServiceImpl(serviceImplName)
                .setController(controllerName)
                .setXml(xmlName);
    }

    /**
     * 模板配置
     *
     * @return
     */
    public static TemplateConfig getTemplateConfig() {
        return new TemplateConfig()
                .setController("/templates/controller.java")
                .setService("/templates/service.java")
                .setServiceImpl("/templates/serviceImpl.java")
                .setMapper("/templates/mapper.java")
                .setEntity("/templates/entity.java");
    }

    /**
     * 全局配置
     *
     * @return
     */
    public static GlobalConfig getGlobalConfig(boolean single) {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig
                //是否覆盖已有文件
                .setFileOverride(false)
                //是否打开输出目录
                .setOpen(false)
                //是否在xml中添加二级缓存配置
                .setEnableCache(false)
                //开发人员
                .setAuthor("bettem")
                //开启 swagger2 模式
                .setSwagger2(true)
                //开启 BaseResultMap，即在xml中生产BaseResultMap
                .setBaseResultMap(true)
                //开启 baseColumnList，即在xml中生成baseColumnList
                .setBaseColumnList(true)
                //时间类型对应策略
                .setDateType(DateType.ONLY_DATE)
                //mapper 命名方式
                .setMapperName("%sMapper")
                //Mapper xml 命名方式
                .setXmlName("%sMapper")
                //service 命名方式
                .setServiceName("%sService")
                //service impl 命名方式
                .setServiceImplName("%sServiceImpl")
                //controller 命名方式
                .setControllerName("%sController");
        if (single) {
            //生成文件的输出目录
            globalConfig.setOutputDir(projectPath + javaDir);
        }
        return globalConfig;
    }

    /**
     * 策略配置(数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表)
     *
     * @return
     */
    public static StrategyConfig getStrategyConfig() {
        return new StrategyConfig()
                //数据库表映射到实体的命名策略（下划线转驼峰）
                .setNaming(NamingStrategy.underline_to_camel)
                //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行（下划线转驼峰）
                .setColumnNaming(NamingStrategy.underline_to_camel)
                //是否生成实体时，生成字段注解
                .setEntityTableFieldAnnotationEnable(true)
                //【实体】是否为lombok模型（默认 false）
                .setEntityLombokModel(true)
                //需要包含的表名，允许正则表达式（与exclude二选一配置）
                .setInclude(scanner("表名，多个英文逗号分割").split(","))
                //需要排除的表名，允许正则表达式
                //.setExclude(null)
                //表前缀(如果要生成的表不是此前缀，则不会去掉前缀)
                .setTablePrefix("sys_")
                //驼峰转连字符
                .setControllerMappingHyphenStyle(true);
    }

    /**
     * 自定义配置会被优先输出，但是Mapper位置还是会生成Mapper.xml，目前未发现解决办法，手动删除吧
     *
     * @return
     */
    public static InjectionConfig getMapperInjectionConfig(PackageConfig packageConfig) {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        String mapperXmlTemplatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(mapperXmlTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                //注意Mapper命名规则
                return projectPath + resourcesDir + packageConfig.getParent().replaceAll("\\.", "/") + "/" + packageConfig.getXml() + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 自定义配置（controller、service、dao分三层为例，目录根据自己的要求配置即可）
     *
     * @param controllerModule controller 模块名
     * @param serviceModule    service 模块名
     * @param daoModule        dao 模块名
     * @return
     */
    public static InjectionConfig getMultiModuleInjectionConfig(String controllerModule, String serviceModule, String daoModule, PackageConfig pc) {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };

        String mapperTemplatePath = "/templates/mapper.java.ftl";
        String mapperXmlTemplatePath = "/templates/mapper.xml.ftl";
        String entityTemplatePath = "/templates/entity.java.ftl";
        String saveVoTemplatePath = "/templates/saveVo.java.ftl";
        String queryVoTemplatePath = "/templates/queryVo.java.ftl";
        String serviceTemplatePath = "/templates/service.java.ftl";
        String serviceImplTemplatePath = "/templates/serviceImpl.java.ftl";
        String controllerTemplatePath = "/templates/controller.java.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // Mapper
        focList.add(new FileOutConfig(mapperTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/" + daoModule + javaDir + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getMapper().replaceAll("\\.", "/") + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
            }
        });

        // Entity
        focList.add(new FileOutConfig(entityTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/" + daoModule + javaDir + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getEntity().replaceAll("\\.", "/") + "/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });

        // save VO
        focList.add(new FileOutConfig(saveVoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/" + daoModule + javaDir + pc.getParent().replaceAll("\\.", "/") + "/dao/vo/V" + tableInfo.getEntityName() + "Save" + StringPool.DOT_JAVA;
            }
        });

        // query VO
        focList.add(new FileOutConfig(queryVoTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/" + daoModule + javaDir + pc.getParent().replaceAll("\\.", "/") + "/dao/vo/V" + tableInfo.getEntityName() + "Query" + StringPool.DOT_JAVA;
            }
        });

        // Mapper.xml
        focList.add(new FileOutConfig(mapperXmlTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/" + daoModule + resourcesDir + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getXml().replaceAll("\\.", "/") + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        // service
        focList.add(new FileOutConfig(serviceTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/" + serviceModule + javaDir + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getService().replaceAll("\\.", "/") + "/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
            }
        });

        //  serviceImpl
        focList.add(new FileOutConfig(serviceImplTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/" + serviceModule + javaDir + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getServiceImpl().replaceAll("\\.", "/") + "/" + tableInfo.getEntityName() + "ServiceImpl" + StringPool.DOT_JAVA;
            }
        });

        // controller
        focList.add(new FileOutConfig(controllerTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/" + controllerModule + javaDir + pc.getParent().replaceAll("\\.", "/") + "/" + pc.getController().replaceAll("\\.", "/") + "/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });

        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
