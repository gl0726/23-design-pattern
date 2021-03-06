package org.example.design.structural.proxy.statics;

/**
 *  代理模式的前提是必须要有相同的接口, 否则无法使用
 * Author: GL
 * Date: 2021-10-09
 */
public interface UserDao {
    void save(String data);
    void update();
}
