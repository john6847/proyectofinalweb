$(document).ready(function () {
    var cant = parseInt($('#cant').text());
    var sizeTotal=parseInt($('#size').text());
    for(var i = 0 ; i<cant;i++){
        if(i===0){
            $("#pagin").append('<li><a href="/">'+(i+1)+'</a></li> ');
        }else{
            var c= i+1;
            $("#pagin").append(
                $('<li>').append(
                    $('<a>').addClass("pagesNumber").append(c)));
        }

    }

    $('.pagesNumber').click(function () {
        $.ajax({
            url:'/page/galery/'+sizeTotal+"/"+$(this).text(), success:function (data) {
                $('.principal').html(data);
            }
        });
    });

});