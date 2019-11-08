import java.lang.reflect.Proxy;

/**
 * @author yanxi
 * 实现简单的aop功能，即对于一个包含切面（要增强的方法）的对象
 * 将对应的增强即通知advice，实现了invocationHandler接口，
 * 将advice和目标对象生成代理
 */
public class SimpleAOP {
    //调用动态代理的方法生成代理类，其中
    public static Object getProxy(Object bean, Advice advice) {
        return Proxy.newProxyInstance(SimpleAOP.class.getClassLoader(),
                bean.getClass().getInterfaces(), advice);
    }
}
