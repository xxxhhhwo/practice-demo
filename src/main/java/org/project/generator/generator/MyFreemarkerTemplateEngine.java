package org.project.generator.generator;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author jyj
 * @Date 2020/4/26
 **/
public class MyFreemarkerTemplateEngine extends AbstractTemplateEngine {

    /**
     * 启用默认输出文件（默认：启用。controller、service、dao分模块时，自定义文件输出需要禁用它，否则生成两份代码）
     */
    private Boolean enableDefaultOutputFile = true;
    /**
     * 启用默认输出xml文件（默认：启用。当Mapper.xml与Mapper.java分离，放在resources下时，自定义文件输出需要禁用它，否则在resources生成Mapper.xml后，Mapper.java所在目录或它下的xml目录下也会生成Mapper.xml）
     */
    private Boolean enableDefaultOutputXmlFile = true;

    private Configuration configuration;

    @Override
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        this.configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        this.configuration.setDefaultEncoding(ConstVal.UTF8);
        this.configuration.setClassForTemplateLoading(MyFreemarkerTemplateEngine.class, "/");
        return this;
    }

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        Template template = this.configuration.getTemplate(templatePath);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        Throwable var6 = null;

        try {
            template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
        } catch (Throwable var15) {
            var6 = var15;
            throw var15;
        } finally {
            if (fileOutputStream != null) {
                if (var6 != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Throwable var14) {
                        var6.addSuppressed(var14);
                    }
                } else {
                    fileOutputStream.close();
                }
            }

        }

        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }

    @Override
    public String templateFilePath(String filePath) {
        return filePath + ".ftl";
    }

    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = this.getConfigBuilder().getTableInfoList();
            Iterator var2 = tableInfoList.iterator();

            //自定义文件输出
            while (var2.hasNext()) {
                TableInfo tableInfo = (TableInfo) var2.next();
                Map<String, Object> objectMap = this.getObjectMap(tableInfo);
                Map<String, String> pathInfo = this.getConfigBuilder().getPathInfo();
                TemplateConfig template = this.getConfigBuilder().getTemplate();
                InjectionConfig injectionConfig = this.getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initTableMap(tableInfo);
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        Iterator var9 = focList.iterator();

                        while (var9.hasNext()) {
                            FileOutConfig foc = (FileOutConfig) var9.next();
                            if (this.isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                                this.writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                            }
                        }
                    }
                }

                if (this.enableDefaultOutputFile) {
                    String entityName = tableInfo.getEntityName();
                    String controllerFile;
                    if (null != entityName && null != pathInfo.get("entity_path")) {
                        controllerFile = String.format((String) pathInfo.get("entity_path") + File.separator + "%s" + this.suffixJavaOrKt(), entityName);
                        if (this.isCreate(FileType.ENTITY, controllerFile)) {
                            this.writer(objectMap, this.templateFilePath(template.getEntity(this.getConfigBuilder().getGlobalConfig().isKotlin())), controllerFile);
                        }
                    }

                    if (null != tableInfo.getMapperName() && null != pathInfo.get("mapper_path")) {
                        controllerFile = String.format((String) pathInfo.get("mapper_path") + File.separator + tableInfo.getMapperName() + this.suffixJavaOrKt(), entityName);
                        if (this.isCreate(FileType.MAPPER, controllerFile)) {
                            this.writer(objectMap, this.templateFilePath(template.getMapper()), controllerFile);
                        }
                    }

                    if (this.enableDefaultOutputXmlFile) {
                        if (null != tableInfo.getXmlName() && null != pathInfo.get("xml_path")) {
                            controllerFile = String.format((String) pathInfo.get("xml_path") + File.separator + tableInfo.getXmlName() + ".xml", entityName);
                            if (this.isCreate(FileType.XML, controllerFile)) {
                                this.writer(objectMap, this.templateFilePath(template.getXml()), controllerFile);
                            }
                        }
                    }

                    if (null != tableInfo.getServiceName() && null != pathInfo.get("service_path")) {
                        controllerFile = String.format((String) pathInfo.get("service_path") + File.separator + tableInfo.getServiceName() + this.suffixJavaOrKt(), entityName);
                        if (this.isCreate(FileType.SERVICE, controllerFile)) {
                            this.writer(objectMap, this.templateFilePath(template.getService()), controllerFile);
                        }
                    }

                    if (null != tableInfo.getServiceImplName() && null != pathInfo.get("service_impl_path")) {
                        controllerFile = String.format((String) pathInfo.get("service_impl_path") + File.separator + tableInfo.getServiceImplName() + this.suffixJavaOrKt(), entityName);
                        if (this.isCreate(FileType.SERVICE_IMPL, controllerFile)) {
                            this.writer(objectMap, this.templateFilePath(template.getServiceImpl()), controllerFile);
                        }
                    }

                    if (null != tableInfo.getControllerName() && null != pathInfo.get("controller_path")) {
                        controllerFile = String.format((String) pathInfo.get("controller_path") + File.separator + tableInfo.getControllerName() + this.suffixJavaOrKt(), entityName);
                        if (this.isCreate(FileType.CONTROLLER, controllerFile)) {
                            this.writer(objectMap, this.templateFilePath(template.getController()), controllerFile);
                        }
                    }
                }
            }
        } catch (Exception var11) {
            logger.error("无法创建文件，请检查配置信息！", var11);
        }

        return this;
    }

    public Boolean getEnableDefaultOutputFile() {
        return enableDefaultOutputFile;
    }

    public void setEnableDefaultOutputFile(Boolean enableDefaultOutputFile) {
        this.enableDefaultOutputFile = enableDefaultOutputFile;
    }

    public Boolean getEnableDefaultOutputXmlFile() {
        return enableDefaultOutputXmlFile;
    }

    public void setEnableDefaultOutputXmlFile(Boolean enableDefaultOutputXmlFile) {
        this.enableDefaultOutputXmlFile = enableDefaultOutputXmlFile;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
