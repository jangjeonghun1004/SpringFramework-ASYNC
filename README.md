# 비동기 타임 아웃 설정

    setDefaultTimeout() 
    해당 함수를 이용하면 비동기 실행의 기본 타임 아웃을 설정할 수 있습니다.

# 타임 아웃 예외 핸들링
    타임 아웃 예외가 발생하면 "AsyncRequestTimeoutException.class" 호출됩니다.
    @ControllerAdive 를 사용해서 사용자가 예외를 핸들링 할 수 있습니다.

# 비동기 반환 클래스/인터페이스

    # Callable<T> 인터페이스
        특별한 설정 없이 비동기를 구현할 수 있다.

    # DeferedResult<T> 클래스
        비동기 처리에 대한 결과 값을 받을 수 있다.
        결과를 원하는 시점에 클라이언트에게 반환할 수 있습니다.
        타임 아웃, 예외 상황에 대한 세부 제어가 가능합니다.

    # CompletableFuture<T> 클래스
        다양한 콜백 함수를 지원합니다.
        결과를 조합하고 변환하는 유연한 API 제공.
        스레드 풀을 효율적으로 사용하여 성능 향상.

    "DefferedResult 클래스"는 결과 값을 받을 수는 있지만 어느 시점에 결과 값이 도착하는지 알 수 없다.
    하지만 "CompletableFuture 클래스"는 콜백 함수를 지원하기 때문에 결과 도착 시점을 정확히 알 수 있다.

    즉 다시 말해 "DefferedResult 클래스"는 결과 값을 단순히 기록한다.
    만약 결과 값을 받은 후 후행 작업이 추가적으로 있다면 "CompletableFuture 클래스"를 사용한다.
    

# 비동기 관련 추가 사항

    # Executor 인터페이스
        쓰레드를 미리 만들어두고 재사용하기 위한 쓰레드 풀(Thread Pool)

    # ExecutorService 인터페이스
        ExecutorService는 Executor를 상속받아서 작업 등록 뿐만 아니라 실행을 위한 책임도 갖습니다.

# 참고 자료

    https://velog.io/@ssssujini99/Java-Callable-Executors-ExecutorService-%EC%9D%98-%EC%9D%B4%ED%95%B4-%EB%B0%8F-%EC%82%AC%EC%9A%A9%EB%B2%95



    


    
