package org.example.design.behavioral.mediator.simple;


import org.example.design.behavioral.mediator.complete.DatabaseType;

/**
 *  此案例展示简单版中介模式，将所有引用统一注入进中介类进行管理，业务类只需持有中介类即可
 *
 *  注意：此中介类并不负责各个业务之间逻辑，只是单纯存储各业务类引用
 *
 *  结构：
 *                                 数据库接口
 *                             ┌────────────────┐
 *                             │Database<T>     │
 *                             │└add(T data);   │
 *                             │└receive(T data)│
 *                             │└select();      │
 *                             └────────────────┘
 *                                     ▲
 *                                     │
 *      ┌───────────────────────────────────────────────────────────────┐
 *      │AbstractDatabase<T>                                            │                                   中介类                             数据库类型
 *      │ └DatabaseType databaseType                                    │              ┌────────────────────────────────────────────┐
 *      │ └AbstractMediator<DatabaseType, AbstractDatabase<T>> mediator │------------->│ AbstractMediator<K, V>                     │      ┌───────────────┐
 *      │ └forward(T data)                                              │              │  └Map<K, V> dataMap                        │<-----│ DatabaseType  │
 *      │ └add(T data){                                                 │              │  └register(K key, V colleague){}           │      │   └MYSQL      │
 *      │   receive(data);                                              │              │  └V getKey(K key)                          │      │   └REDIS      │
 *      │   forward(data);                                              │              │  └abstract void changed(K key, T data);    │      │   └ES         │
 *      │ };                                                            │              └────────────────────────────────────────────┘      └───────────────┘
 *      └───────────────────────────────────────────────────────────────┘                                    ▲
 *                                      ▲                                                                    │
 *        ┌─────────────────────────────┼─────────────────────────────┐                ┌─────────────────────────────────────────────┐
 *    ┌────────────────────┐  ┌────────────────────┐   ┌────────────────────┐          │SyncMediator                                 │
 *    │MysqlDatabase       │  │RedisDatabase       │   │EsDatabase          │          │  └changed(DatabaseType database, T data){}  │
 *    │ └dataset<T>        │  │ └dataset<T>        │   │ └dataset<T>        │          └─────────────────────────────────────────────┘
 *    │ └forward(T data){} │  │ └forward(T data){} │   │ └forward(T data){} │
 *    │ └receive(T data){} │  │ └receive(T data){} │   │ └receive(T data){} │
 *    │ └select(){}        │  │ └select(){}        │   │ └select(){}        │
 *    └────────────────────┘  └────────────────────┘   └────────────────────┘
 *
 *  总结：简单版中介模式虽然不用管理各个业务的逻辑引用，但从使用角度上看反而更加灵活，类似Spring-IOC
 *
 * Author: GL
 * Date: 2021-12-30
 */
public class TestSimple {
    public static void main(String[] args) {

        final AbstractMediator<DatabaseType, AbstractDatabase<String>> mediator = new SyncMediator<>();

        AbstractDatabase<String> mysqlDatabase = new MysqlDatabase<>(mediator);
        AbstractDatabase<String> redisDatabase = new RedisDatabase<>(mediator);
        AbstractDatabase<String> esDatabase = new EsDatabase<>(mediator);

        System.out.println("\n---------mysql 添加数据 1，将同步到Redis和ES中-----------");
        mysqlDatabase.add("1");
        mysqlDatabase.select();
        redisDatabase.select();
        esDatabase.select();

        System.out.println("\n---------Redis添加数据 2，将不同步到其它数据库-----------");
        redisDatabase.add("2");
        mysqlDatabase.select();
        redisDatabase.select();
        esDatabase.select();

        System.out.println("\n---------ES 添加数据 3，只同步到 Mysql-----------");
        esDatabase.add("3");
        mysqlDatabase.select();
        redisDatabase.select();
        esDatabase.select();
    }
}