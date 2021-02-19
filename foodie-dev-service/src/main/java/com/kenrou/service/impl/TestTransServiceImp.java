package com.kenrou.service.impl;

import com.kenrou.service.TestTransService;
import com.kenrou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class TestTransServiceImp implements TestTransService {

    @Autowired
    private UserService userService;

    /**
     * 事务传播 - Propagation
     *          REQUIRED:       Required       使用当前的事务，如果当前没有事务，则新建一个事务，子方法必须运行在一个事务中; 如果当前存在事务，则加入这个事务，成为一个整体
     *          SUPPORTS:       Support        如果当前有事务，则使用事务；如果当前没有事务，则不使用事务
     *          MANDATORY:      Mandatory      Throw an exception if none exists.
     *          REQUIRES_NEW:   Requires_new   Create a new transaction, and suspend(挂起) the current transaction if one exists.
     *          NOT_SUPPORTED:  Not_Supported  Execute non-transactionally, suspend the current transaction if one exists. 以无事务方式运行
     *          NEVER:          Never          Execute non-transactionally, throw an exception if a transaction exists.
     *          NESTED:         Nested         父子事务，同REQUIRES_NEW区别，当父事务报错，REQUIRES_NEW子事务不回滚，NESTED回滚
     *                                                 同REQUIRED区别，当子事务报错，REQUIRED影响父事务，NESTED通过try catch可以起到不影响父事务
     */
    @Override
    public void testPropagationTrans() {

    }
}
