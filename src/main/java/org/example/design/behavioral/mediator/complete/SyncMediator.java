package org.example.design.behavioral.mediator.complete;

import lombok.extern.log4j.Log4j2;

/**
 *  具体中介者
 *
 * Author: GL
 * Date: 2021-11-10
 */
@Log4j2
public class SyncMediator<T> extends AbstractMediator<DatabaseType, T, AbstractDatabase<T>> {

    // 根据类型进行不同方式的同步
    @Override
    public void changed(DatabaseType database, T data) {
        switch (database) {
            case MYSQL:
                getDataMap().get(DatabaseType.REDIS).receive(data);
                getDataMap().get(DatabaseType.ES).receive(data);
                break;
            case REDIS:
                log.info("redis 无需同步");
                break;
            case ES:
                getDataMap().get(DatabaseType.MYSQL).receive(data);
                break;
            default:
                throw new RuntimeException("数据库类型错误");
        }
    }


}
