import org.junit.Test;

public class SimpleAOPTest {
    @Test
    public void getProxy(){
        //使用匿名内部类，创建一个增强逻辑
        MethodInvocation logTask =()-> System.out.println("log task start");
        //要被增强的对象
        HelloService helloService = new HelloServiceImpl();
        //构造切面，即将逻辑织入切点
        Advice beforeAdvice = new BeforeAdvice(helloService,logTask);
        //获取代理对象
        HelloService helloServiceProxy = (HelloService) SimpleAOP.getProxy(helloService,beforeAdvice);
        //自动执行被增强的方法
        helloServiceProxy.sayHelloWorld();
    }
}
