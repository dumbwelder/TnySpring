import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanxi
 * @date 2019.11.07
 * 实现一个简单的IOC容器，可以通过xml文件配置的bean地址，直接获取bean
 * 单例模式，饿汉式加载，初始化容器时即加载全部bean
 * bean属性可以实现基本数据类型与引用数据类型的注入，注入方式为反射直接修改域
 * 未解决循环依赖，bean加载顺序影响结果
 */
public class SimpleIOC {
    //ioc容器通过使用hashmap存储bean。
    private Map<String, Object> beanMap = new HashMap<>();

    //

    /**
     * @param location xml文件位置
     * @throws Exception 为创建IO流及创建xml解析类时引起的异常
     */
    public SimpleIOC(String location) throws Exception {
        loadBeans(location);
    }


    /**
     * 通过xml文件创建bean
     *
     * @param location xml文件位置
     */
    private void loadBeans(String location) throws Exception {
        //获取xml文件内容，将其转换为Element、Node
        InputStream is = new FileInputStream(location);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(is);
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                String id = ele.getAttribute("id");
                String className = ele.getAttribute("class");

                Class beanClass = null;
                try {
                    beanClass = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                Object bean = beanClass.newInstance();

                NodeList propertyNodes = ele.getElementsByTagName("property");
                for (int j = 0; j < propertyNodes.getLength(); j++) {
                    Node propertyNode = propertyNodes.item(j);
                    if (propertyNode instanceof Element) {
                        Element propertyElement = (Element) propertyNode;
                        String name = propertyElement.getAttribute("name");
                        String value = propertyElement.getAttribute("value");

                        Field declaredField = bean.getClass().getDeclaredField(name);
                        declaredField.setAccessible(true);

                        if (value != null && value.length() > 0) {
                            declaredField.set(bean, value);
                        } else {
                            String ref = propertyElement.getAttribute("ref");
                            if (ref == null || ref.length() == 0) {
                                throw new IllegalArgumentException("ref config error");
                            }
                            declaredField.set(bean, getBean(ref));
                        }
                        registerBean(id, bean);
                    }
                }
            }

        }
    }

    private void registerBean(String id, Object bean) {
        beanMap.put(id, bean);
    }

    public Object getBean(String name) {
        Object bean = beanMap.get(name);
        if (bean == null) {
            throw new IllegalArgumentException("there is no bean with name " + name);
        }
        return bean;

    }
}
