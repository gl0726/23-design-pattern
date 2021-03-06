package org.example.design.behavioral.responsibility.connect;

import org.example.design.behavioral.responsibility.separate.FinanceRequest;
import org.junit.Test;

import java.math.BigDecimal;

/**
 *  此责任链模式是连接模式, 将各个处理器连接成一条链路, 并使用了回调函数作为责任建之间的调用, 算是责任链模式的一个变体；
 *  使用回调的好处在于无需在子类中判断父类的handler是否为空, 是否有下一个链, 而是返回给父类由父类进行判断, 让父子类之间职能更清晰
 *
 *  结构：
 *
 *                         责任链表标识接口            回调函数接口
 *                    ┌─────────────────────┐  ┌─────────────────────┐
 *                    │ ResponsibilityChain │  │ MessageCallBack     │
 *                    └─────────────────────┘  └─────────────────────┘
 *                              ▲                        ▲
 *                              │────────────────────────┘
 *                  ┌──────────────────────────┐
 *                  │FinanceHandlerChain       │-优先级责任链处理抽象类, 支持优先级处理, 同时实现回调函数
 *                  │ └setSuccessor[存放下游]   │
 *                  │ └process(FinanceRequest) │
 *                  └──────────────────────────┘
 *                               ▲
 *            ┌──────────────────┼───────────────────┐
 *   ┌──────────────────────┐        ┌──────────────────────┐
 *   │ManagerFinanceHandler │        │DirectorFinanceHandler│
 *   └──────────────────────┘        └──────────────────────┘
 *
 * Author: GL
 * Date: 2021-11-02
 */
public class TestConnect {

    @Test
    public void test() {
        // 组装责任链, 第一个审批人是权限最低的, 越往链路深处, 权限越高
        FinanceHandlerChain manager = new ManagerFinanceHandler("manager", BigDecimal.valueOf(1000.0));
        FinanceHandlerChain director = new DirectorFinanceHandler("director", BigDecimal.valueOf(2000.0));
        manager.setSuccessor(director);

        // 提交请求
        manager.process(new FinanceRequest("Jack Ma", BigDecimal.valueOf(900.0)));
        // 前台提交
        manager.process(new FinanceRequest("Reception", BigDecimal.valueOf(3000.0)));
    }

}
