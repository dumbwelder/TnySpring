import java.lang.reflect.Method;

public class BeforeAdvice implements Advice {
    private Object bean;
    private MethodInvocation methodInvocation;


    public BeforeAdvice(Object bean, MethodInvocation methodInvocation) {
            this.methodInvocation = methodInvocation;
            this.bean = bean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        methodInvocation.invoke();
        return method.invoke(bean, args);
    }
}
