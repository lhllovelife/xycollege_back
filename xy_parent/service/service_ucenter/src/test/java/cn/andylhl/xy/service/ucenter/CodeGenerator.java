package cn.andylhl.xy.service.ucenter;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/***
 * @Title: CodeGenerator
 * @Description: mybatis-plus代码生成器
 * @author: lhl
 * @date: 2021/1/29 19:44
 */
public class CodeGenerator {

    @Test
    public void testPath() {
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
    }

    @Test
    public void genCode() {

        // 数据库库名 = 前缀 + 模块名
        // 前缀
        String prefix = "xy_";
        // 模块名
        String moduleName = "ucenter";

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        // 获取当前程序所在项目的根路径（绝对路径）
        String projectPath = System.getProperty("user.dir");
        // 设置生成后代码放置的文件夹
        gc.setOutputDir(projectPath + "/src/main/java");
        // 设置作者
        gc.setAuthor("lhl");
        // 生成后是否打开资源管理器
        gc.setOpen(false);
        // 重新生成时文件是否覆盖
        gc.setFileOverride(false);
        // 去掉Service接口的首字母I
        gc.setServiceName("%sService");
        // 主键策略(雪花算法)
        gc.setIdType(IdType.ASSIGN_ID);
        // 定义生成的实体类中日期类型，只使用 java.util.date
        gc.setDateType(DateType.ONLY_DATE);
        // 开启Swagger2模式
        gc.setSwagger2(true);
        // 将定义的全局配置对象设置到代码生成器中
        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // 数据库配置信息
        // dsc.setUrl("jdbc:mysql://localhost:3306/" + prefix + moduleName + "?serverTimezone=GMT%2B8");
        dsc.setUrl("jdbc:mysql://192.168.43.193:3306/"+ prefix + moduleName +"?useUnicode=true&characterEncoding=UTF-8");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("333");
        dsc.setDbType(DbType.MYSQL);
        // 将定义的数据源配置对象设置到代码生成器中
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        // 模块名
        pc.setModuleName(moduleName);

        // pc.setParent("com.atguigu.guli.service");
        pc.setParent("cn.andylhl.xy.service");
        // 包名
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        // 将定义的包配置对象设置到代码生成器中
        mpg.setPackageInfo(pc);


        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 数据库表映射到实体的命名策略（数据库表中使用全小写加下划线，java中使用驼峰命名）
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 设置表前缀不生成
        strategy.setTablePrefix(moduleName + "_");
        // 数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityLombokModel(true);
        // 逻辑删除字段名
        strategy.setLogicDeleteFieldName("is_deleted");
        // 去掉布尔值的is_前缀
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);

        //自动填充
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);
        // restful api风格控制器
        strategy.setRestControllerStyle(true);
        // url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);

        // 设置BaseEntity
        // strategy.setSuperEntityClass("com.atguigu.guli.service.base.model.BaseEntity");
        strategy.setSuperEntityClass("cn.andylhl.xy.service.base.model.BaseEntity");

        // 填写BaseEntity中的公共字段
        strategy.setSuperEntityColumns("id", "gmt_create", "gmt_modified");

        // 将定义的策略置对象设置到代码生成器中
        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }

}
