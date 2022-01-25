package com.example.pettycash.Utality;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

    public App() throws ExecutionException, InterruptedException {




        long input = 20L;
// get a callable task to be submitted to the executor service
Callable<Long> task = new Action(input);
// create an ExecutorService with a fixed thread pool having one
ExecutorService executorService = Executors.newSingleThreadExecutor();
// submit the task to the executor service and store the Future
Future<Long> futureObject = executorService.submit(task);
// wait for the get() method that blocks until the computation is complete.
        Log.v("appBack","Process  returned output %d for the input %d"+ futureObject.get()+ input);
        System.out.printf("Process  returned output %d for the input %d", futureObject.get(), input);// done. shutdown the executor service since we don't need it
        executorService.shutdown();}}