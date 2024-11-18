<!DOCTYPE html>
<html>
<head>
    <title>WebSocket STOMP Test</title>
    <script src="https://cdn.jsdelivr.net/sockjs/1.0.3/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
</head>
<body>
    <h2>WebSocket STOMP Example</h2>
    <div>
        <input id="from" placeholder="Your Name" />
        <input id="text" placeholder="Message" />
        <button onclick="sendMessage()">Send</button>
    </div>
    <ul id="messages"></ul>

    <script>
        var stompClient = null;

        function connect() {
            var socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/messages', function (message) {
                    showMessage(JSON.parse(message.body));
                });
            });
        }

        function sendMessage() {
            var from = document.getElementById("from").value;
            var text = document.getElementById("text").value;
            stompClient.send("/app/message", {}, JSON.stringify({ from: from, text: text }));
        }

        function showMessage(message) {
            var messages = document.getElementById("messages");
            var li = document.createElement("li");
            li.appendChild(document.createTextNode(message.from + ": " + message.text + " (" + new Date(message.time) + ")"));
            messages.appendChild(li);
        }

        connect();
    </script>
</body>
</html>
