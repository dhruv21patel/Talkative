const SockJS = require('sockjs-client');
const Stomp = require('stompjs');

// Create the WebSocket connection
const socket = new SockJS('http://localhost:8085/ws'); // This matches your Spring endpoint
const stompClient = Stomp.over(socket);

// Function to send a message asynchronously
async function sendMessage(stompClient, Roomid, message) {
    return new Promise((resolve, reject) => {
        try {
            console.log('Sending room:', Roomid);
            stompClient.send(`/app/Msg/`+ Roomid, {}, JSON.stringify(message));
            console.log('Message sent:', message);
            resolve();
        } catch (error) {
            console.error('Error sending message:', error);
            reject(error);
        }
    });
}

// Connect to WebSocket
stompClient.connect({}, async function(frame) {
    console.log('Connected: ' + frame);
    
    // Subscribe to a topic
    const Roomid = '27d44b9f-ad83-49c4-8e26-07f761363c45'; // Replace with the actual room ID
    console.log('Sending to topic:', "/app/Msg/"+Roomid);
    // Function to send messages at intervals
    async function sendMessagesAtIntervals() {
        const message = {
            "Userid1": 1234,
            "Userid2": 4321,
            "Is_Group": false,
            "Group_name": null,
            "Message": "LOL dhurvvagfugorlibfib"
        };

        while (true) {
            await sendMessage(stompClient, Roomid, message);
            await new Promise(resolve => setTimeout(resolve, 1000)); // Wait for 1 second
        }
    }

    // Start sending messages
    sendMessagesAtIntervals();

});
