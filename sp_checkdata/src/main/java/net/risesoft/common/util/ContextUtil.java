package net.risesoft.common.util;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.ServletContextAware;

/**
 * 获取WebApplicationContext的一条??
 */
public class ContextUtil implements ApplicationContextAware, ServletContextAware {

    private static ApplicationContext actx;
    private static ServletContext servletContext;

    @Override
    public void setApplicationContext(ApplicationContext actx) throws BeansException {
        ContextUtil.actx = actx;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        ContextUtil.servletContext = servletContext;
    }

    public static String getContextPath() {
        return servletContext.getContextPath();
    }
    
    public static ServletContext getServletContext(String uripath) {
        return servletContext.getContext(uripath);
    }
    
    public static String getWebRootRealPath() {
        return servletContext.getRealPath("/");
    }

    public static ApplicationContext getAc() {
        return actx;
    }
    
    public static void publishEvent(ApplicationEvent event) {
        actx.publishEvent(event);
    }

    /**
     * 获取对象
     * 
     * @param name
     * @return Object 丿?以所给名字注册的bean的实便
     * @throws org.springframework.beans.BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) actx.getBean(name);
    }

    /**
     * 获取类型为requiredType的对豿
     * 
     * @param clz
     * @return
     * @throws org.springframework.beans.BeansException
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        T result = (T) actx.getBean(clz);
        return result;
    }

    /**
     * 如果BeanFactory包含丿?与所给名称匹配的bean定义，则返回true
     * 
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return actx.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是丿?prototype?如果与给定名字相应的bean定义没有被找到，将会抛出丿?异常（NoSuchBeanDefinitionException＿
     * 
     * @param name
     * @return boolean
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return actx.isSingleton(name);
    }

    /**
     * @param name
     * @return Class 注册对象的类垿
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return actx.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     * 
     * @param name
     * @return
     * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return actx.getAliases(name);
    }

}