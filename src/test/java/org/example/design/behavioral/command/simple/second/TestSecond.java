package org.example.design.behavioral.command.simple.second;

import org.example.design.behavioral.command.require.first.StockManagerReceive;
import org.example.design.behavioral.command.require.first.StockReceive;
import org.junit.Test;

/**
 *  此案例为正常的命令设计模式, 用于解决usually包下弊端.
 *
 *  设计思路：每一个命令都是一个实体！命令模式的核心是把发出命令的客户端和执行命令的接收方 分隔开, 委派给不同的命令的对象处理！
 *
 *  结构：
 *
 *    请求标识接口                                命令标识接口                         接收方标识接口
 *    ┌────────┐
 *    │ Client │                               ┌─────────┐
 *    └────────┘                               │ Command │                         ┌─────────┐
 *         ▲                                   └─────────┘                         │ Receive │
 *         │                                        ▲                              └─────────┘
 *   ┌───────────────────┐                          │                                   ▲
 *   │StockClient        │                    ┌─────────────┐                           │
 *   │└add(StockCommand) │<-------------------│StockCommand │                     ┌─────────────┐
 *   └───────────────────┘                    │└stockService│<--------------------│StockReceive │只有StockCommand持有StockReceive引用
 *         ▲                                  └─────────────┘                     └─────────────┘
 *         │                                       ▲                                     ▲
 *  ┌────────────────┐             ┌───────────────┼───────────────┐                     │
 *  │StockUserClient │      ┌─────────────────┐            ┌───────────────┐    ┌────────────────────┐
 *  └────────────────┘      │StockSellCommand │            │StockBuyCommand│    │StockManagerReceive │
 *                          └─────────────────┘            └───────────────┘    └────────────────────┘
 *
 *  总结：请求方不在引用任何变量！而是单纯的接受命令实现类, 来进行请求访问接收方！
 *
 *  优点：通过将命令设计成不同命令类, 将每一个命令抽象, 让发送方和接收方都无需了解彼此, 彻底将双方解耦, 并且实现了开闭原则
 *       如接收方增加一个函数, 那么只需要创建一个新的StockCommand类即可！其他类无需改动；
 *
 *  不足：
 *      1、当遇到复杂的命令场景时, 我们应该具有历史任务的一个备份和撤回操作！ 可以参考complete包下的Test
 *      2、目前的设计是客户端和服务端一对一, 但是当一个客户端需要对应多个服务端请求的时候, 则命令父类中需要持有多个服务端的引用并进行管理, 
 *         此时就违背了命令类的单一原则, 解决方案是在参Command类中持有一个中介类, 由中介类统一管理所有服务端的引用, 详细参考[behavioral.mediator]
 *
 * Author: GL
 * Date: 2021-11-05
 */
public class TestSecond {
    @Test
    public void test() {
        // 构建服务方
        StockReceive maYun = new StockManagerReceive("Jack Ma");
        // 构建命令
        StockCommand buyCommand = new StockBuyCommand(maYun);
        StockCommand sellCommand = new StockSellCommand(maYun);
        // 构建请求方
        StockUserClient userClient = new StockUserClient("Yong Hao Luo");
        // 发送请求
        userClient.send(buyCommand, sellCommand);
    }
}
