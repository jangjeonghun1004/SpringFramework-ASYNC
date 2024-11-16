package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.util.concurrent.*;

@Controller
@RequestMapping("/async")
public class AsyncController {
    // 스레드 풀 생성
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    /**
     * Callable 비동기
     * 특별한 설정 없이 비동기를 구현할 수 있다.
     * @return ModelAndView 객체
     */
    @GetMapping("/callable")
    public Callable<ModelAndView> async() {
        return() -> {
            // Thread.sleep 을 통해 시간이 소요되는 작업을 진행 중이라고 가정합니다.
            Thread.sleep(Duration.ofSeconds(5));
            return new ModelAndView("callable");
        };
    }

    /**
     * DeferredResult 비동기
     * 결과를 원하는 시점에 클라이언트에게 반환할 수 있습니다.
     * 타임 아웃, 예외 상황에 대한 세부 제어가 가능합니다.
     * @return ModelAndView 객체
     */
    @GetMapping("/deferredResult")
    public DeferredResult<ModelAndView> deferredResult() {
        // DeferredResult 객체 생성 (타임아웃: 5000ms)
        DeferredResult<ModelAndView> deferredResult = new DeferredResult<>(
                6000L,
                new ModelAndView("/exception/asyncTimeoutException")
        );

        // 3초 후 비동기 작업 완료
        this.executorService.schedule(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(2));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            ModelAndView modelAndView = new ModelAndView("deferredResult");
            deferredResult.setResult(modelAndView);
        }, 3, TimeUnit.SECONDS);

        return deferredResult;
    }

    /**
     * CompletableFuture 비동기
     * 다양한 콜백 함수를 지원한다.
     * 비동기 실행 후 콜백 함수를 통해 결과를 처리할 수 있기다.
     * supplyAsync: 비동기적으로 결과를 반환하는 작업을 실행.
     * runAsync: 결과를 반환하지 않는 비동기 작업 실행.
     * thenApply: 작업 결과를 변환.
     * thenAccept: 결과를 소비.
     * thenCombine: 두 CompletableFuture 결과를 병합.
     * exceptionally: 예외 처리.
     * @return ModelAndView 객체
     */
    @GetMapping("/completableFuture")
    public CompletableFuture<ModelAndView> completableFuture() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000); // 3초 지연
                //throw new RuntimeException("Something went wrong.");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            return new ModelAndView("completableFuture");
        })
        .thenApply(result -> result)
        .handle((result, ex) -> {
            return new ModelAndView("/exception/asyncTimeoutException");
        });
    }

}