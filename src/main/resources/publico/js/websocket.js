var webSocket;
var reconnect = 5000;
var sender="";

function conectar() {
    // Web socket code
    webSocket = new WebSocket("ws://"+location.hostname+":"+location.port+"/mensajeServidor");
    webSocket.onmessage = function (data) {recibirInformacionServidor(data);}
    webSocket.onopen= function(e){console.log("Conectando - status "+this.readyState);}
    webSocket.onclose= function (e) {console.log("Desconectando - status"+ this.readyState);}
    webSocket.onerror = function (){console.log("Hubo un error al conectar");}
}

function openSocket() {
    if(!webSocket || webSocket.readyState===3){
        conectar();
    }
}


$(document).ready(function(){
    conectar();
    //websocket stuff
    console.info("Iniciando Jquery - Ejemplo Webservices");

    $('#Conectar').click(function () {
        var $nombre = $('#nombre').val();
        if($nombre.length>=2){
            $(".portlet-title").append($('<h4>').append($('<i>').addClass("portlet-title name").append($nombre)));
            var isAdminOrAutor=0;
            var mensajeFinal= $nombre+"~"+"connect-120lk./,o/h"+"~"+isAdminOrAutor;
            webSocket.send(mensajeFinal);
            $(this).fadeTo('fast',0);
            $("#nombre").fadeTo('fast',0);

        }else{
            $("#nombre").css({"border":"1px solid red"});
            alert("You should enter a valid name!");
        }

    })
});

function recibirInformacionServidor(mensaje) {

    console.log("Recibiendo informacion del Servidor");
    if($('#del').length){
        $('#del').hide()
    }
    sender = mensaje.data.split("~")[3];
    if(mensaje.data.split("~")[2]==="1"){
        sender = mensaje.data.split("~")[0];
    }
    alert(sender)
    var dt = new Date();
    var time = dt.getHours() + ":" + dt.getMinutes() + ":" + dt.getSeconds();
    $('.portlet-body').append(
        $('<div>').addClass('row').append(
            $('<div>').addClass('col-lg-12').append(
                $('<div>').addClass('media').append(
                    $('<a>').addClass('pull-left').append(
                        $('<img>').addClass('media-object img-circle').attr('src','https://lorempixel.com/30/30/people/1/').attr('alt','')
                    ),
                    $('<div>').addClass('media-body').append(
                        $('<h4>').addClass("media-heading").append(mensaje.data.split('~')[0]).append(
                            $('<span>').addClass('small pull-right').append(time)
                        ),
                            $('<p>').append(mensaje.data.split('~')[1])
                        )
                    )
                )
            ),$('<hr>')
        )
    $('textarea#message').val('');
}


$(document).ready(function () {
    var currentDate = new Date();
    var day = currentDate.getDate();
    var month = currentDate.getMonth() + 1;
    var year = currentDate.getFullYear();

    $('#fechaDehoy').append("<b>" + day + "/" + month + "/" + year + "</b>");
});


$(document).ready(function () {
    $('#chat').click(function () {
        if ( $( ".bootstrap" ).is( ":hidden" ) ) {
            $(".bootstrap" ).slideDown( "slow" );
            $(".line-content").fadeTo('slow', 0.5);
        } else {
            $( ".bootstrap" ).slideUp();
            $(".line-content").fadeTo('slow',1);
        }
    });

    $('#send').click(function () {
        var isAdminOrAutor = 0;
        var $username=$('#nombre').val();
        var $message =$('#message').val()
        alert($username+"~"+$message+"~"+isAdminOrAutor+"~"+sender);
        webSocket.send($username+"~"+$message+"~"+isAdminOrAutor+"~"+sender);

    });

});
setInterval(openSocket, reconnect);