package com.example.forkjoin.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

@Service
public class ForkJoinServiceImpl implements ForkJoinService {
    @Override
    public void forkJoin() {
        Long startTime = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100000000; i++) {
            list.add(i);
        }
        // 这是Fork/Join框架的线程池
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Integer> taskFuture = pool.submit(new MyForkJoinTask(list));
        try {
            Integer result = taskFuture.get();
            System.out.println("result = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace(System.out);
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("使用分治算法耗时：" + (endTime - startTime));
    }

    private static final Integer MAX = 10000;

    //使用匿名内部类
    static class MyForkJoinTask extends RecursiveTask<Integer> {

        private List<Integer> list;


        public MyForkJoinTask(List<Integer> list) {
            this.list = list;
        }

        @Override
        protected Integer compute() {
            // 如果条件成立，说明这个任务所需要计算的数值分为足够小了
            // 可以正式进行累加计算了
            if (list.size() < MAX) {
                Integer totalValue = 0;
                for (int i = 0; i < list.size(); i++) {
                    totalValue += list.size();
                }
                return totalValue;
            }
            // 否则再进行任务拆分，拆分成两个任务
            else {
                //怎么拆分任务才是重点
                List<Integer> minList = list.subList(0, list.size() / 2);
                List<Integer> maxList = list.subList(minList.size(), list.size());
                MyForkJoinTask myForkJoinTaskMin = new MyForkJoinTask(minList);
                MyForkJoinTask myForkJoinTaskMax = new MyForkJoinTask(maxList);
                myForkJoinTaskMin.fork();
                myForkJoinTaskMax.fork();
                return myForkJoinTaskMin.join() + myForkJoinTaskMax.join();
            }
        }
    }
}
