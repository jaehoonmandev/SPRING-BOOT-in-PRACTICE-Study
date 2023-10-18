
let sock = new SockJS('http://localhost:8080/ws'); // http://localhost:8080/ws와 연결하는 웹 소켓 생성.


let client = Stomp.over(sock);// 웹소켓 엔드포인트를 포함하는 StompClient 객체 생성.


// STOMP 통신 시작.
// CONNECTED 프레임 도작할 때 콜백 함수를 제공.
client.connect({}, (frame) => {

	console.log("Frame is: " +frame);
	// /topic/messages 를 구독하여 메시지를 반환 받을 때 마다 메시지를 화면에 노축한다.
	client.subscribe('/topic/messages', (payload) => {
		let message_list = document.getElementById('message-list');
		let message = document.createElement('li');
		let output = JSON.parse(payload.body);
		message.appendChild(document.createTextNode(output.content +" at " +output.time));
		message_list.appendChild(message);
	});
});

// Function to send message. This function is invoked while you click on the
// Send in the HTML page. It takes the value in the ‘message-input’ text field 
// and send it to the server with empty headers ({}).
//메시지 전송 메서드
function sendMessage() {
	console.log("Sending message");
	// message-input 의 요소 값을 읽어와 InputMessage 로 서버에 전송된다.
	let input = document.getElementById('message-input');
	client.send('/app/chat', {}, JSON.stringify({ content: input.value }));
}
