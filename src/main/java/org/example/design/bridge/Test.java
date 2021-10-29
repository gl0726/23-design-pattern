package org.example.design.bridge;

import org.example.design.bridge.brand.Benz;
import org.example.design.bridge.brand.Bmw;
import org.example.design.bridge.car.Car;
import org.example.design.bridge.car.SportsCar;
import org.example.design.bridge.car.Suv;
import org.example.design.bridge.engine.HybridEngine;
import org.example.design.bridge.engine.OilEngine;

/**
 *  桥接模式是一种结构型设计模式， 可将一个大类或一系列紧密相关的类拆分为多个抽象和实现的层次结构， 从而能在开发时分别使用。
 *
 *   理解：
 *      其实桥接模式在我们开发中用的很多，尤其是后端的三层架构中(MVC), 比如我们经常使用的service业务层中会设置不同表的dao层接口为成员变量，
 *          service只调用dao层接口的抽象函数，并不需要知道是哪个实现类，由此将service层和dao层的接口区别开！
 *      而且只是通过成员变量的引用为桥梁来连接service层和dao层，同时也不影响service层和dao层的各自下游的实现类，只是通过引用为桥梁连接两片世界(service层世界)和(dao层世界)。
 *          十分友好：因为不影响各自世界的扩展和实现，只是通过引用桥梁沟通，举例如下：
 *
 *  假设某个汽车厂商生产的汽车有两种类型：SUV和SportsCar，每种类型的车又可以选择不同的引擎(engine)：燃油(Oil)和混合动力(Hybrid)。 和不同的品牌(brand):Bmw 和 Benz;
 *
 *  如果用传统的继承来表示各个最终车型，一共有2个抽象类加8个最终子类：
 *
 *                    ┌───────┐
 *                    │  Car  │
 *                    └───────┘
 *                        ▲
 *     ┌──────────────────┼───────────────┐
 *     │                                  │
 * ┌───────┐                         ┌─────────┐
 * │SUV    │                         │SportsCar│
 * └───────┘                         └─────────┘
 *     ▲                                  ▲
 *     │                                  │
 *     │ ┌───────────────┐                │ ┌───────────────┐
 *     ├─│  BmwOilSuv    │                ├─│BmwOilSportsCar│
 *     │ └───────────────┘                │ └───────────────┘
 *     │ ┌───────────────┐                │ ┌──────────────────┐
 *     ├─│  BmwHybridSuv │                ├─│BmwHybridSportsCar│
 *     │ └───────────────┘                │ └──────────────────┘
 *     │ ┌───────────────┐                │ ┌──────────────────┐
 *     └─│  BenzOilSuv   │                └─│ BenzOilSportsCar │
 *     │ └───────────────┘                │ └──────────────────┘
 *     │ ┌───────────────┐                │ ┌────────────────────┐
 *     └─│  BenzHybridSuv│                └─│BenzHybridSportsCar │
 *       └───────────────┘                  └────────────────────┘
 *
 *  如果要新增一个品牌，或者加一个新的引擎（比如核动力），亦或者那么子类的数量增长更快。所以我们需要将品牌和引擎单独抽离出来，使用桥接模式将他们连接即可，修改后的架构如下：
 *
 *                      桥梁                          桥梁
 *   ┌─────────┐         │         ┌───────────┐      │     ┌─────────┐
 *   │ Brand   │ <─ ─ ─ ─ ─ ─ ─ ─  │    Car    │ ─ ─ ─ ─ ─> │ Engine  │
 *   └─────────┘                   └───────────┘            └─────────┘
 *     ▲                                 ▲                       ▲
 *     │ ┌──────────────┐       ┌────────┼────────┐              │ ┌──────────────┐
 *     ├─│  Bmw         │       │                 │              ├─│ HybridEngine │
 *     │ └──────────────┘   ┌──────────┐      ┌───────┐          │ └──────────────┘
 *     │ ┌──────────────┐   │SportsCar │      │  Suv  │          │ ┌──────────────┐
 *     ├─│  Benz        │   └──────────┘      └───────┘          ├─│  OilEngine   │
 *       └──────────────┘                                          └──────────────┘
 *
 *  实现见代码
 *
 * Author: GL
 * Date: 2021-10-28
 */
public class Test {

    public static void main(String[] args) {
        // 构建奔驰SUV混合动力
        final Car car = new Suv(new HybridEngine(), new Benz());
        car.drive();
        car.introduce();

        System.out.println("----------------------------------");

        // 构建宝马跑车汽油动力
        final Car car2 = new SportsCar(new OilEngine(), new Bmw());
        car2.drive();
        car2.introduce();

    }
}
