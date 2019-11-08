import java.lang.reflect.Proxy;

/**
 * @author yanxi
 * 实现简单的aop功能，即对于一个包含切面（要增强的方法）的对象
 * 对应的增强（即通知advice，实现了invocationHandler接口），
 * 使用advice和目标对象生成代理对象
 * 目前存在的问题：在实现advice时，已经将bean导入
 */
public class SimpleAOP {

    /**
     * 调用动态代理的方法生成代理类
     * @param bean 被代理对象
     * @param advice 增强方式，实现了invocationHandler
     * @return 代理对象
     */
    public static Object getProxy(Object bean, Advice advice) {
        return Proxy.newProxyInstance(SimpleAOP.class.getClassLoader(),
                bean.getClass().getInterfaces(), advice);
    }
}
