package org.example.design.behavioral.memento.complete;

/**
 *  命令标识接口
 *
 * Author: GL
 * Date: 2021-11-05
 */
public interface Command {
    void execute();
    void undo();
}
