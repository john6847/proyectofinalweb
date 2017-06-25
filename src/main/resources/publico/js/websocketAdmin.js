var webSocket;
var reconnect = 5000;

function conectar() {
    // Web socket code
    webSocket = new WebSocket("ws://"+location.hostname+":"+location.port+"/mensajeServidor");
    webSocket.onmessage = function (data) {updateChat(data);}
    webSocket.onopen= function(e){console.log("Conectando - status "+this.readyState);}
    webSocket.onclose= function (e) {console.log("Desconectando - status"+ this.readyState);}
    webSocket.onerror = function (){console.log("Hubo un error al conectar");}
}

function openSocket() {
    if(!webSocket || webSocket.readyState===3){
        conectar();
    }
}

var $sender="";
$(document).ready(function () {
    $('#userlist').on('click', ".list-group-item",function (e) {
        var $messages =$(this).children('p').text();
        $sender = $(this).contents().get(0).nodeValue;
        $('.del').fadeTo('fast', 0);
        $('.linea').fadeTo('fast', 0);
        $messages=$messages.split("~");
        $(this).hide();
        var mensajeFinal=""
        $messages.forEach(function (mes) {
            mensajeFinal=$sender+"~"+mes
            recibirInformacionServidor(mensajeFinal);
        });
    });
});

$(document).ready(function () {
    //event to connect
    $('#Conectar').click(function () {
        var $username= $('#user').val();
        var isAdminOrAutor=1;
        $('.name').append($username);
        var mensajeFinal= $username+"~"+"connect-120lk./,o/h"+"~"+isAdminOrAutor;
        webSocket.send(mensajeFinal);
        $(this).fadeTo('fast',0);
    });


    //sending message
    $('#send').click(function () {
        var $message= $('textarea#message').val();
        $('textarea#message').val('');
        var $nombre = $('#user').val();
        var isAdminOrAutor=1;
        var mensajeFinal= $nombre+"~"+$message+"~"+isAdminOrAutor+"~"+$sender;
        webSocket.send(mensajeFinal);

    });
});

function updateChat(msg) {
    var data = JSON.parse(msg.data);

    if(data.userList){
        var count=1
        data.userList.forEach(function (user) {
            $(".userlist").append(
                $('<li>').addClass('list-group-item').append(user).attr('id',"user"+count).append(
                    $('<span>').addClass('badge').append(1).css({'background-color':"red", "color":"white"})))
            count++;
        });
    }
    if(data.userMessage){
        var count=1;
        data.userMessage.forEach(function (mes) {

            $('#user'+count).append($('<p>').append(mes).css({"display":"none"}));
            count ++;
        }) ;
    }
    if(data.userMes){
        recibirInformacionServidor(data.userMes);
    }

    return data;
}
function recibirInformacionServidor(mensaje) {

    console.log("Recibiendo informacion del Servidor");
    if($('#del').length){
        $('#del').hide()
    }
    var dt = new Date();
    var time = dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
    $('.portlet-body').append(
        $('<div>').addClass('row del').append(
            $('<div>').addClass('col-lg-12').append(
                $('<div>').addClass('media').append(
                    $('<a>').addClass('pull-left').append(
                        $('<img>').addClass('media-object img-circle').attr('src','https://lorempixel.com/30/30/people/1/').attr('alt','')
                    ),
                    $('<div>').addClass('media-body').append(
                        $('<h4>').addClass("media-heading").append(mensaje.split('~')[0]).append(
                            $('<span>').addClass('small pull-right').append(time)
                        ),
                        $('<p>').append(mensaje.split('~')[1])
                    )
                )
            )
        ),$('<hr>').addClass("linea")
    )
    $('textarea#message').val('');

}

setInterval(openSocket, reconnect);



