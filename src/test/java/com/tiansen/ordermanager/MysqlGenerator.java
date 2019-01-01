package com.tiansen.ordermanager;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 * @author rylai
 * @since 2018-09-12
 */
public class MysqlGenerator {
    public final static String[] allTables=new String[]{/*"sys_access","sys_menu","sys_role","sys_role_access","sys_user","sys_user_role",*/
           "consumable_definition","consumable_detail","combination","express","order","proposer", "purchase","store","supplier","product_detail","product_definition"};

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setActiveRecord(true);//extends model，this and BaseEntity，choise one
        gc.setDateType(DateType.ONLY_DATE);
        gc.setIdType(IdType.INPUT);
        gc.setFileOverride(true);// 是否覆盖文件
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setAuthor("rylai");
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
//        dsc.setTypeConvert(new MySqlTypeConvert(){
//            // 自定义数据库表字段类型转换【可选】
//            @Override
//            public DbColumnType processTypeConvert(String fieldType) {
//                System.out.println("转换类型：" + fieldType);
//                return super.processTypeConvert(fieldType);
//            }
//        });
        dsc.setUrl("jdbc:mysql://localhost:3306/xiaduo?useSSL=false&useUnicode=true&characterEncoding=UTF-8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("mybatis"));
        pc.setParent("com.tiansen.ordermanager");
        pc.setController("controller");
        pc.setEntity("mybatis.entity");
        pc.setMapper("mybatis.mapper");
        pc.setService("mybatis.service");
        pc.setServiceImpl("mybatis.service.impl");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/"  + tableInfo.getEntityName() + "Mapper" + ".xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        TemplateConfig templateConfig = new TemplateConfig();
//        templateConfig.setController(null);//不生成Controller
        // tc.setController("...");
//        templateConfig.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。

        mpg.setCfg(cfg);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.nochange);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.esim.railway.mybatis.BaseEntity");
//        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
//        strategy.setSuperEntityColumns("id");
        strategy.setEntityLombokModel(true);
        strategy.setEntityBuilderModel(true);
        strategy.setEntityColumnConstant(true);//【实体】是否生成字段常量（默认 false）
//        strategy.setSuperControllerClass("com.esim.railway.BaseController");
//        String tableName=scanner("表名（全部表输入all）");
        String tableName="all";
        if(null!=tableName&& tableName.equalsIgnoreCase("all")) {
            strategy.setInclude(allTables);
        }else {
            strategy.setInclude(tableName);
        }

        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
