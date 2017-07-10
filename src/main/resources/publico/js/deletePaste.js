$(document).ready(function () {
    setInterval(function(){
        $.ajax({
            url:"/paste/eliminar", success:function (data) {
                console.log(data)
            }
        })
        }, 120000);
});