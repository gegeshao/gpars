/**
 * Created by root on 10/9/19.
 */

/**
 * @ClassName ChainedDataflowVariablenew
 * @description TODO
 * @Author root
 * @Date 10/9/19 4:27 PM
 * @Version 1.0
 **/

// GPars - Groovy Parallel Systems
//
// Copyright © 2008-2012  The original author or authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


import groovy.lang.Closure;
import groovyx.gpars.actor.impl.MessageStream;
import groovyx.gpars.group.PGroup;
import groovyx.gpars.scheduler.Pool;
import groovyx.gpars.dataflow.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.groovy.runtime.MethodClosure;

import java.util.concurrent.Callable;

/**
 * Represents a thread-safe single-assignment, multi-read variable with delayed initialization.
 * Each instance of DataflowVariable can be read repeatedly any time using the 'val' property and assigned once
 * in its lifetime using the '&lt;&lt;' operator. Reads preceding assignment will be blocked until the value
 * is assigned.
 * For actors and Dataflow Operators the asynchronous non-blocking variants of the getValAsync() methods can be used.
 * They register the request to read a value and will send a message to the actor or operator once the value is available.
 *
 * @param <T> Type of values to bind with the DataflowVariable
 * @author Vaclav Pech
 *         Date: April 4, 2013
 */
@SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "UnqualifiedStaticUsage"})
final class ChainedDataflowVariablenew<T> extends DataflowVariable<T> {
    private Closure[]  initializer = null;
    private AtomicBoolean initializationStarted = new AtomicBoolean(false);
    private PGroup group = null;

    /**
     * Creates a new unbound Lazy Dataflow Variable
     */
    public ChainedDataflowVariablenew(Closure[] c) {
        this(Dataflow.DATA_FLOW_GROUP, c);
    }

    /**
     * Creates a new unbound Lazy Dataflow Variable with specific a PGroup to use for running the initializer
     */
    public ChainedDataflowVariablenew(final PGroup group, Closure[] c) {
        if (group == null)
            throw new IllegalArgumentException("The AsyncDataflowVariable initialization group must not be null.");
        this.group = group;
        this.initializer = c;
        if (initializer == null)
            throw new IllegalArgumentException("The AsyncDataflowVariable initializer must not be null.");
    }

    private Closure initErrorHandler = null;

    private void initialize() {
        if (!initializationStarted.getAndSet(true)) {
            group.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        MethodClosure cWrap = new MethodClosure(ChainedDataflowVariablenew.this, "closureWrap");
                        Closure currentC = null;
                        Closure lastC = null;
                        for (int i = initializer.length - 1; i>=0; i--)
                        {
                            if(i == initializer.length - 1)
                            {
                                currentC = cWrap.curry(i,null);
                            }
                            else
                            {
                                currentC = cWrap.curry(i,lastC); //append closure one by one
                            }
                            lastC = currentC;
                        }
                        if (currentC != null)
                            currentC.call(); //start execute closure

                    } catch (Throwable e) {
                        ChainedDataflowVariablenew.this.bindError(e);
                    }
                }
            });
        }
    }

    private void closureWrap(int i, Closure errorHandler)
    {
        try {
            Object callResult = initializer[i].call();
            Promise p = null;
            if(callResult instanceof Promise)
            {
                p = (Promise)callResult;
            }
            else
            {
                Callable<String> callable = new Callable<String>(){
                    @Override
                    public String call() throws Exception {
                        if(callResult != null)
                            return callResult.toString();
                        else
                            return null;
                    }
                };
                MethodClosure initMc = new MethodClosure(callable, "call");
                p = new LazyDataflowVariable(initMc);
            }
            //MethodClosure mc = new MethodClosure(ChainedDataflowVariable.this, "leftShift");
            MethodClosure nextHandler = new MethodClosure(ChainedDataflowVariablenew.this, "nextHandler");
            Closure mc = nextHandler.curry(errorHandler);
            if (errorHandler != null)
            {
                p.then(mc,errorHandler);
            }
            else
            {
                handle error = new handle(){
                    public void handle(Throwable error){
                        ChainedDataflowVariablenew.this.bindError(error);
                    }
                };
                MethodClosure eH = new MethodClosure(error, "handle");
                p.then(mc,eH);
            }

        } catch (Throwable e) {
            if (errorHandler != null && errorHandler.getMaximumNumberOfParameters() == 1)
            {
                errorHandler.call(e);
            }
            else if(errorHandler != null)
            {
                errorHandler.call();
            }
            else if(errorHandler == null || (errorHandler.getMaximumNumberOfParameters() != 1 &&errorHandler.getMaximumNumberOfParameters() != 0))
            {
                if (initErrorHandler != null && initErrorHandler.getMaximumNumberOfParameters() == 1)
                {
                    initErrorHandler.call(e);
                }
                else if(initErrorHandler != null)
                {
                    initErrorHandler.call();
                }
                else
                {
                    System.err.println("errorHandler is null");
                    e.printStackTrace();
                }
            }
            else
            {
                e.printStackTrace();
            }
        }
    }
    private void nextHandler(Closure nextHandler, String v)
    {
        if( (v!= null && !v.isEmpty())||nextHandler == null)
        {
            ChainedDataflowVariablenew.this.leftShift((T)v);
        }
        else
        {
            if (nextHandler.getMaximumNumberOfParameters() == 1)
            {
                nextHandler.call((T)v);
            }
            else
            {
                nextHandler.call();
            }
        }
    }
    @Override
    public void touch() {
        super.touch();
        initialize();
    }

    /**
     * Checks if the promise is bound to an error
     *
     * @return True, if an error has been bound
     */
    @Override
    public final boolean isError() {
        initialize();
        return super.isError();
    }

    /**
     * Returns the error bound to the promise
     *
     * @return The error
     * @throws IllegalStateException If not bound or not bound to an error
     */
    @Override
    public final Throwable getError() {
        initialize();
        return super.getError();
    }

    /**
     * Schedule closure to be executed after data became available.
     * It is important to notice that even if the expression is already bound the execution of closure
     * will not happen immediately but will be scheduled
     *
     * @param closure      closure to execute when data becomes available. The closure should take at most one argument.
     * @param errorHandler closure to execute when an error (instance of Throwable) gets bound. The closure should take at most one argument.
     * @return A promise for the results of the supplied closure. This allows for chaining of then() method calls.
     */
    @Override
    public final <V> Promise<V> then(final Closure<V> closure, final Closure<V> errorHandler) {
        initErrorHandler = errorHandler;
        initialize();
        return super.then(closure, errorHandler);
    }

    /**
     * Schedule closure to be executed after data becomes available.
     * It is important to notice that even if the expression is already bound the execution of closure
     * will not happen immediately but will be scheduled.
     *
     * @param pool         The thread pool to use for task scheduling for asynchronous message delivery
     * @param closure      closure to execute when data becomes available. The closure should take at most one argument.
     * @param errorHandler closure to execute when an error (instance of Throwable) gets bound. The closure should take at most one argument.
     * @return A promise for the results of the supplied closure. This allows for chaining of then() method calls.
     */
    @Override
    public final <V> Promise<V> then(final Pool pool, final Closure<V> closure, final Closure<V> errorHandler) {
        initErrorHandler = errorHandler;
        initialize();
        return super.then(pool, closure, errorHandler);
    }

    /**
     * Schedule closure to be executed after data becomes available.
     * It is important to notice that even if the expression is already bound the execution of closure
     * will not happen immediately but will be scheduled.
     *
     * @param group        The PGroup to use for task scheduling for asynchronous message delivery
     * @param closure      closure to execute when data becomes available. The closure should take at most one argument.
     * @param errorHandler closure to execute when an error (instance of Throwable) gets bound. The closure should take at most one argument.
     * @return A promise for the results of the supplied closure. This allows for chaining of then() method calls.
     */
    @Override
    public final <V> Promise<V> then(final PGroup group, final Closure<V> closure, final Closure<V> errorHandler) {
        initErrorHandler = errorHandler;
        initialize();
        return super.then(group, closure, errorHandler);
    }

    @Override
    public T getVal() throws InterruptedException {
        initialize();
        return super.getVal();
    }

    @Override
    public T getVal(final long timeout, final TimeUnit units) throws InterruptedException {
        initialize();
        return super.getVal(timeout, units);
    }

    @Override
    public void getValAsync(final MessageStream callback) {
        initialize();
        super.getValAsync(callback);
    }

    @Override
    public void getValAsync(final Object attachment, final MessageStream callback) {
        initialize();
        super.getValAsync(attachment, callback);
    }
}



