package org.project.generator.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author jyj
 * @Date 2020/4/26
 **/
public class MyAutoGenerator extends AutoGenerator {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 启用默认输出文件（默认：启用。controller、service、dao分模块时，自定义文件输出需要禁用它，否则生成两份代码）
     */
    private Boolean enableDefaultOutputFile = true;

    @Override
    public void execute() {
        logger.debug("==========================准备生成文件...==========================");
        if (null == this.config) {
            this.config = new ConfigBuilder(this.getPackageInfo(), this.getDataSource(), this.getStrategy(), this.getTemplate(), this.getGlobalConfig());
            if (null != this.injectionConfig) {
                this.injectionConfig.setConfig(this.config);
            }
        }

        if (null == this.getTemplateEngine()) {
            this.setTemplateEngine(new VelocityTemplateEngine());
        }

        AbstractTemplateEngine templateEngine = this.getTemplateEngine().init(this.pretreatmentConfigBuilder(this.config));
        if (this.enableDefaultOutputFile) {
            templateEngine.mkdirs();
        }
        templateEngine.batchOutput().open();
        logger.debug("==========================文件生成完成！！！==========================");
    }

    public Boolean getEnableDefaultOutputFile() {
        return enableDefaultOutputFile;
    }

    public void setEnableDefaultOutputFile(Boolean enableDefaultOutputFile) {
        this.enableDefaultOutputFile = enableDefaultOutputFile;
    }
}
