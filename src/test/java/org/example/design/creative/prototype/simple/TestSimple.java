package org.example.design.creative.prototype.simple;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

/**
 *  此案例通过Object - clone函数实现克隆[此方式最为简单], 需要注意的是被克隆类需要实现Cloneable接口, 否则会抛出CloneNotSupportedException异常.
 *
 *  克隆类和原实例并不相等, 克隆出的是新的实例
 *
 * Author: GL
 * Date: 2021-12-06
 */
@Log4j2
public class TestSimple {
    @Test
    public void test() {
        final Prototype prototype = new Prototype(1, "prototype");
        final Prototype clone = prototype.clone();
        log.info(prototype);
        log.info(clone);
        log.info(clone == prototype); // 克隆类和原实例并不相等, 克隆出的是新的实例
    }
}
