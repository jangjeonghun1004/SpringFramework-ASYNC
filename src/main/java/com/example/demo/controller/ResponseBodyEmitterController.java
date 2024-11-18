package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("responseBodyEmitter")
public class ResponseBodyEmitterController {

    @GetMapping
    public String responseBodyEmitterGet() {
        return "responseBodyEmitter";
    }

    @GetMapping("getResponseBodyEmitter")
    public ResponseBodyEmitter responseBodyEmitterPost() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // 비동기로 데이터를 생성
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    emitter.send("Data chunk " + i + "\n"); // 데이터를 클라이언트로 전송
                    Thread.sleep(1000); // 데이터 생성 지연 시뮬레이션
                }
                emitter.complete(); // 스트리밍 종료
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e); // 에러 발생 시 클라이언트로 에러 전송
            }
        });

        return emitter;
    }

    @GetMapping("responseEntity")
    public ResponseEntity<ResponseBodyEmitter> responseEntity() {
        ResponseBodyEmitter responseBodyEmitter = new ResponseBodyEmitter();

        // 비동기로 데이터를 생성
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    responseBodyEmitter.send("Data chunk " + i + "\n"); // 데이터를 클라이언트로 전송
                    Thread.sleep(1000); // 데이터 생성 지연 시뮬레이션
                }
                responseBodyEmitter.complete(); // 스트리밍 종료
            } catch (IOException | InterruptedException e) {
                responseBodyEmitter.completeWithError(e); // 에러 발생 시 클라이언트로 에러 전송
            }
        });

        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                .header("Custom-Header", "Custom-Value")
                .body(responseBodyEmitter);
    }

    @GetMapping("sseEmitter")
    public SseEmitter sseEmitter() {
        SseEmitter sseEmitter = new SseEmitter();

        // 비동기로 데이터를 생성
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    sseEmitter.send("Data chunk " + i + "\n"); // 데이터를 클라이언트로 전송
                    Thread.sleep(1000); // 데이터 생성 지연 시뮬레이션
                }
                sseEmitter.complete(); // 스트리밍 종료
            } catch (IOException | InterruptedException e) {
                sseEmitter.completeWithError(e); // 에러 발생 시 클라이언트로 에러 전송
            }
        });

        return sseEmitter;
    }

    /**
     * 여러 값을 반환 합니다.
     * @return 문자열
     */
    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getFlux() {
        return Flux.just("Hello", "WebFlux", "World!").delayElements(Duration.ofSeconds(1));
    }

    /**
     * Flux로 실시간 데이터 스트리밍
     * @return 숫자
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> getStream() {
        return Flux.interval(Duration.ofSeconds(1)).take(10);
    }

}
