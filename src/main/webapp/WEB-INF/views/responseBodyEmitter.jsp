<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ResponseBodyEmitter Streaming</title>
    <style>
        #output {
            white-space: pre-wrap; /* 줄바꿈 유지 */
            background-color: #f5f5f5;
            padding: 10px;
            border: 1px solid #ddd;
            font-family: monospace;
            height: 300px;
            overflow-y: auto;
        }
    </style>
</head>
<body>
    <h1>Streaming Data</h1>
    <div id="output">Waiting for data...</div>

    <script>
        // Fetch API로 서버에서 데이터를 실시간 스트리밍
        const outputDiv = document.getElementById("output");

        //fetch("/responseBodyEmitter/responseEntity")
        //fetch("/responseBodyEmitter/sseEmitter")
        //fetch("/responseBodyEmitter/flux")
        //fetch("/responseBodyEmitter/stream")
        fetch("/responseBodyEmitter/getResponseBodyEmitter")
            .then(response => {
                const reader = response.body.getReader();
                const decoder = new TextDecoder("utf-8");

                function readStream() {
                    reader.read().then(({ done, value }) => {
                        if (done) {
                            outputDiv.textContent += "\n[Stream Ended]";
                            return;
                        }

                        // 수신된 데이터를 디코딩하여 출력
                        const chunk = decoder.decode(value, { stream: true });
                        outputDiv.textContent += "\n" + chunk;

                        // 스크롤을 맨 아래로 이동
                        outputDiv.scrollTop = outputDiv.scrollHeight;

                        // 다음 청크 읽기
                        readStream();
                    });
                }

                readStream();
            })
            .catch(error => {
                outputDiv.textContent = "Error: " + error.message;
            });
    </script>
</body>
</html>
