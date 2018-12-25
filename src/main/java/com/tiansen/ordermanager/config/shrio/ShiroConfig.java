package com.tiansen.ordermanager.config.shrio;

import com.tiansen.ordermanager.common.Constants;
import com.tiansen.ordermanager.config.shrio.filter.CustomPermissionFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(customRealm());
        // 设置sessionManager
        manager.setSessionManager(sessionManager());
        // 设置cahcheManager为ehcache
        manager.setCacheManager(ehcacheManager());

        return manager;
    }

    @Bean
    public SessionManager sessionManager(){
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
        shiroSessionManager.setGlobalSessionTimeout(Constants.SHIRO_SESSION_TIMEOUT_MILL_SECONDS);
        shiroSessionManager.setDeleteInvalidSessions(true);
        // 缓存改用ehcahce管理
        shiroSessionManager.setCacheManager(ehcacheManager());
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setActiveSessionsCacheName(Constants.SHIRO_SESSION_CACHE_NAME);
        shiroSessionManager.setSessionDAO(enterpriseCacheSessionDAO);

        return shiroSessionManager;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public MyShiroRealm customRealm() {
        MyShiroRealm realm = new MyShiroRealm();
        realm.setPermissionResolver(permissionString -> new MyPermission(permissionString));
        realm.setAuthorizationCacheName(Constants.SHIRO_AUTHORIZATION_CACHE_NAME);
        return realm;
    }

    @Bean(name = "railwayCacheManager")
    public EhCacheManager ehcacheManager() {
        EhCacheManager ehcacheManager = new EhCacheManager();
        ehcacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehcacheManager;
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 添加自己的过滤器取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(16);
//        filterMap.put("formftr", new CustomFormFilter());
        filterMap.put("defrest", new CustomPermissionFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);

        // 自定义url规则
        Map<String, String> filterRuleMap = new HashMap<>(16);

        // swagger
        filterRuleMap.put("/swagger-resources/**", "anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/v2/api-docs**", "anon");
        filterRuleMap.put("/swagger-ui.html/**", "anon");

        // 静态文件
        filterRuleMap.put("/**/druid/**", "anon");
        filterRuleMap.put("/**/js/**", "anon");
        filterRuleMap.put("/**/css/**", "anon");
        filterRuleMap.put("/**/html/**", "anon");

        // login register
        filterRuleMap.put("/**/login/**", "anon");
        filterRuleMap.put("/**/register/**", "anon");

        // custom perms filter
        filterRuleMap.put("/**", "defrest");

        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}