let stompClient = null;


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    let socket = new SockJS('/connect');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        let session_id =  /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];

        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/new-event',function(new_event){
            console.log(new_event)
        });

        stompClient.subscribe('/topic/sensor-appeared',function(sensor_appeared){
            console.log(sensor_appeared)
        });

        stompClient.subscribe('/topic/sensor-disappeared',function(sensor_disappeared){
        console.log((sensor_disappeared))
        });

        stompClient.subscribe('/topic/get-last-events' + session_id,function(last_events){
        console.log(last_events);
        });

        stompClient.subscribe('/topic/get-sensor-state' + session_id,function(sensor_state){
         console.log(sensor_state);
        });

        stompClient.subscribe('/topic/get-sensors' + session_id,function(sensors){
       console.log(sensors)
        });

       //  stompClient.send("/app/get-last-events",{});
      //   stompClient.send("/app/get-sensor-state",{});
         stompClient.send("/app/get-sensors",{});
});
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

//function sendName() {
//    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
//}
