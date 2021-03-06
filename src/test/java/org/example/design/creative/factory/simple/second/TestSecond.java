package org.example.design.creative.factory.simple.second;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

/**
 *  此案例用来实现一对多静态简单工厂模式
 *
 *      顾名思义, 一对多静态简单工厂模式就是针对多个类而创建的工厂类, 此工厂类包含一个静态方法通过传参来创建对应的类
 *      缺点是静态函数中会冗余大量逻辑, 由于违反单一职责原则, 故不推荐使用此模式
 *      解决参考[complete]
 *
 *  结构：
 *             静态工厂类                               枚举类
 *     ┌───────────────────────────────────┐
 *     │enum UserFactory                   │       ┌────────────┐
 *     │  └static Person create(UserType)  │------>│UserType    │
 *     └────────────│──────────────────────┘       │└USER_PARENT│
 *                  │                              │└USER_CHILD │
 *                  │                              └────────────┘
 *                  │
 *                  │                               ┌───────┐
 *                  └------------------------------>│Person │   实现类
 *                                                  └───────┘
 *                                                      ▲
 *                                              ┌───────┼───────┐
 *                                         ┌────────┐       ┌───────┐
 *                                         │Parent  │       │Child  │
 *                                         └────────┘       └───────┘
 *
 *  注意：UserFactory类
 *        1、此类可以设计为enum, 更安全
 *        2、此类是不能实现依赖倒置原则, 如果实现则要继承某个Factory接口的create抽象函数再实现, 但此create函数是静态函数！接口中的函数只能是普通函数, 如果实现Factory接口的create函数, 实现后的只能是普通函数！
 *           外界就不能直接调用
 *        3、create函数返回的类型要是Parent 和 Child 的共同父类
 *
 * Author: GL
 * Date: 2021-11-25
 */
@Log4j2
public class TestSecond {
    @Test
    public void test() {
        log.info(UserFactory.create(UserType.USER_PARENT, "parent"));
        log.info(UserFactory.create(UserType.USER_CHILD, "child"));
    }
}
