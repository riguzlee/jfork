package com.riguz.jfinal;

import javax.sql.DataSource;

import com.google.common.base.Strings;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.BaseModelGenerator;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.ModelGenerator;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * GeneratorDemo
 */
public class RunGenerator {
    static final Prop p     = PropKit.use("jdbc.properties");
    String      group = "";

    public RunGenerator(String dbGroup) {
        this.group = dbGroup;
    }


    public DataSource getDataSource() {
        DruidPlugin dbPlugin = new DruidPlugin(
        		this.getConfig("jdbc.url"),
        		this.getConfig("jdbc.user"),
        		this.getConfig("jdbc.password"));
        dbPlugin.setDriverClass(this.getConfig("jdbc.driver"));
        dbPlugin.start();
        return dbPlugin.getDataSource();
    }

    private String getConfig(String key){
    	return p.get(this.group + "." +  key);
    }
    public void generate() {
        String baseModelPackageName = this.getConfig("model.base.package");
        String baseModelOutputDir = PathKit.getWebRootPath() + this.getConfig("model.base.dir");

        String modelPackageName = this.getConfig("model.package");
        String modelOutputDir = PathKit.getWebRootPath() + this.getConfig("model.dir");

        BaseModelGenerator baseGenerator = new BaseModelGenerator(baseModelPackageName, baseModelOutputDir);
        ModelGenerator modelGenerator = new ModelGenerator(modelPackageName, baseModelPackageName, modelOutputDir);
        Generator generator = new Generator(this.getDataSource(), baseGenerator, modelGenerator);

        generator.setGenerateDaoInModel(true);
        generator.setGenerateDataDictionary(false);
        generator.setRemovedTableNamePrefixes(this.getConfig("model.table.prefix"));

        String excludedTables = this.getConfig("model.excluded");
        if (!Strings.isNullOrEmpty(excludedTables))
            generator.addExcludedTable(excludedTables.split(","));

        generator.generate();
    }

    public static void main(String[] args) {
        RunGenerator coreG = new RunGenerator("jfork");
        coreG.generate();
        System.out.println("Done!:>");

    }
}
