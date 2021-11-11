package org.example.design.behavioral.iterator.begin.one;

import java.util.Iterator;

/**
 *  这里我们模拟无Iterable，只由集合类ReverseArrayCollection[倒序集合]实现Iterator接口
 *
 *  暂不考虑元素迭代过程中集合增加数据的情况
 *
 *  如下Test代码：
 *      如果集合类单独实现Iterator接口，这会导致在集合类中将会维护一个指针index，当多个函数或线程进行遍历的时候会导致指针无法确定位置！
 *      除非再单独刷新将指针重置后，才可以继续使用遍历
 *
 *  预期结果：
 *      用户想要的无非就是想简单的遍历集合即可，无论是多个线程还是多个函数，只想在每个函数或线程中获得一个从头开始计数的迭代器。
 *      那这最好的方法就是抽出一层iterable，里面有获取iterator迭代器函数，集合通过new对象的方式来返回一个新的从头开始计数的迭代器即可！参考two代码
 *
 *  jdk设计：
 *      jdk的Iterable使用函数itertor()抽象函数然后子类分别实现返回一个iterator实例，实例中包含一个可以从头到尾开始计数的迭代器。
 *
 *  引用解释：
 *      因为Iterator接口的核心方法next()、hasNext(), 是依赖于迭代器的当前迭代位置的。
 *      如果Collection直接实现Iterator接口，势必导致集合对象中包含当前迭代位置的数据(指针)。
 *      当集合在不同方法间被传递时[比如两个线程都在循环集合，A线程循环了3个元素，此时B线程想从头开始迭代开始调用next，会发现返回的是第四个元素！即便使用线程同步锁，
 *      但是Iterator接口的核心方法next()、hasNext()是两个函数！]，这会导致多个函数中对集合的操作让当前迭代位置不可预置，那么next()方法的结果会变成不可预知。
 *      除非再为Iterator接口添加一个reset()方法，用来重置当前迭代位置！
 *      但即时这样，Collection也只能同时存在一个当前迭代位置。
 *
 *      所以我们需要一个Iterable，有一个返回Iterator接口的函数，这样就可以每次调用iterator() 次调用都会返回一个从头开始计数的迭代器。
 *      多个迭代器是互不干扰的！
 *
 *      参考two代码
 *
 * Author: GL
 * Date: 2021-11-08
 */
public class Test {
    public static void main(String[] args) {
        Iterator<Integer> iterator = new ReverseArrayCollection<>(1,2,3,4,5);
        System.out.println(iterator);
        new Thread(() -> {
            while (iterator.hasNext()) {
                System.out.println(String.format(" %s - %s", Thread.currentThread().getName(), iterator.next()));
            }
        }).start();
        new Thread(() -> {
            while (iterator.hasNext()) {
                System.out.println(String.format(" %s - %s", Thread.currentThread().getName(), iterator.next()));
            }
        }).start();
    }
}