const SockJS = require('sockjs-client');
const Stomp = require('stompjs');

// Create the WebSocket connection
const socket = new SockJS('http://localhost:8085/ws'); // URL where your Spring WebSocket is hosted
const stompClient = Stomp.over(socket);

// Function to handle message receiving
function onMessageReceived(messageOutput) {
    console.log("Received message: " + messageOutput.body);
    // Process the message as needed
}

// Connect to the WebSocket server
stompClient.connect({}, function(frame) {
    console.log('Connected: ' + frame);

    // Room ID you want to subscribe to
    const Roomid = '27d44b9f-ad83-49c4-8e26-07f761363c45'; // Replace with your Room ID

    // Subscribe to the room topic to listen for messages
    stompClient.subscribe('/topic/Msg/'+Roomid, onMessageReceived);

    console.log('Subscribed to topic: /topic/'+Roomid);
}, function(error) {
    console.error('STOMP connection error: ', error);
});
