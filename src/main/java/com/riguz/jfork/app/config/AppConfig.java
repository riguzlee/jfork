package com.riguz.jfork.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.Sqls;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.riguz.jfork.app.controller.RootController;
import com.riguz.jfork.model._MappingKit;

public class AppConfig extends JFinalConfig {
    private static Logger logger = LoggerFactory.getLogger(AppConfig.class.getName());

    @Override
    public void configConstant(Constants me) {
        this.loadPropertyFile("app.properties");
        me.setDevMode(this.getPropertyToBoolean("devMode", true));
        me.setViewType(ViewType.FREE_MARKER);
        me.setEncoding("UTF-8");

        // 配置错误页面
        me.setError404View("/html/error/404.html");
        me.setError401View("/html/error/401.html");
        me.setError403View("/html/error/403.html");
        me.setError500View("/html/error/500.html");

        // 配置Beetl视图渲染引擎
        me.setBaseViewPath("/WEB-INF/template");
        me.setFreeMarkerViewExtension(".ftl");
    }

    Routes routes = null;

    @Override
    public void configRoute(Routes me) {
        this.routes = me;
        me.add("/", RootController.class);
    }


    @Override
    public void configPlugin(Plugins me) {
        // 生成两个数据源
        ActiveRecordPlugin coreRecordPlugin = this.createDruidPlugin(me, "jfork");
        _MappingKit.mapping(coreRecordPlugin);
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {
        // 加载路径处理器，处理${CONTEXT_PATH}
        me.add(new ContextPathHandler());
    }

    protected ActiveRecordPlugin createDruidPlugin(Plugins plugins, String group) {
    	this.loadPropertyFile("jdbc.properties");
        String url = this.getProperty(group + ".jdbc.url");
        String driver = this.getProperty(group + ".jdbc.driver");
        String user = this.getProperty(group + ".jdbc.user");
        String passwd = this.getProperty(group + ".jdbc.password");

        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(driver) || Strings.isNullOrEmpty(user)
                || Strings.isNullOrEmpty(passwd))
            return null;
        DruidPlugin dbPlugin = new DruidPlugin(url, user, passwd, driver);
        plugins.add(dbPlugin);

        // 配置ActiveRecord 插件
        ActiveRecordPlugin recordPlugin = new ActiveRecordPlugin(group, dbPlugin);
        recordPlugin.setContainerFactory(new CaseInsensitiveContainerFactory(false));
        plugins.add(recordPlugin);
        return recordPlugin;
    }


	@Override
	public void afterJFinalStart() {
		logger.info("Loadding sqls.sql");
		Sqls.load("sqls.sql");
	}

}
