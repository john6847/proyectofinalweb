<#include "header.ftl">
<#include "nav.ftl">
<div class="container">

    <div class="row">
        <div class="col-md-10" style="display:flex">
            <div style="height:100px;width:80px;border:1px solid green;margin-bottom:20px;" >
                <#if user?has_content && user?is_string && user=="guest">
                    <img style="height:100px;width:80px" src="/images/png/man-2.png" alt="guest">
                </#if>
                <#if usuarioId??><img style="height:100px;width:80px" src="/images/users/user${usuarioId}.png" alt="usuario"></#if>

            </div>
            <div style="margin-left:30px">
                <h2 style="margin-bottom:15px">${paste.getTitulo()}</h2>
                <div>
                    <img class="iconos"  src="/images/see.png" alt="seen"><span id="vista">${paste.getCantidadVista()}</span>
                    <img class="iconos" src="/images/calendar.png" alt="calendar"><span id="fecha">${fecha}</span>
                    <img class="iconos" src="/images/guest.png" alt="guest"><span id="user">${user}</span>
                </div>
            </div>

        </div>
    </div>
    <div class="panel panel-primary">
        <div class="panel-body">

            <div class="panel panel-primary" >
                <div class="panel panel-heading" style="padding:15px"> ${paste.getSintaxis()}
                    <div class="pull-right">
                        <a href="/paste/raw/${paste.getId()}" class="btn btn-default raw" >raw</a>
                        <a class="btn btn-default" href="/paste/embed/${paste.getId()}">embed</a>
                        <a href="/paste/modify/${paste.getId()}" class="btn btn-default change">edit</a>
                        <a href="/paste/delete/${paste.getId()}" class="btn btn-default change">delete</a>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-12">
                            <form action="">
                                <div class="form-group">
                                    <label for="raw">${titulo}</label>
                                    <div class="raw-hjs">
                                        <pre >
                                            <code class="${paste.getSintaxis()}">${paste.getBloqueDeCodigo()}</code>
                                        </pre>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    </div>

                </div>

            <div class="row" id="raw">
                <div class="col-md-12">
                    <form action="">
                        <div class="form-group">
                            <label for="raw">Raw Paste Data</label>
                            <textarea name="raw" class="form-control" cols="30" rows="10">${paste.getBloqueDeCodigo()}</textarea>
                        </div>
                    </form>
                </div>
            </div>
        </div>
</div>
    <div id="pasteId" style="display: none;">${paste.getId()}</div>
    <#if canEditAndDelete??>
        <div id="canEdit" style="display: none;">${canEditAndDelete}</div>
    </#if>

</div>

    <script>
        $(document).ready(function() {
            $('pre code').each(function(i, e) {
                hljs.highlightBlock(e);
            });

            var id = $('#pasteId').text();
            //$("#vista").

            $.ajax(
                {
                url: '/paste/update/hits/' + id, success: function (data) {
                    $('#vista').text(data);
                }
            });

            var $user = $("#user").text();
            var $canEdit = $("#canEdit").text()
            if($user==="guest"){
                $(".change").hide()
            }else if($canEdit==="no"){
                $(".change").hide()
            }else{
                $(".change").show()
            }
        });



    </script>

<#include "footer.ftl">