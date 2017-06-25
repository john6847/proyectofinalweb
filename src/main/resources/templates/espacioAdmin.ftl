<#include "header.ftl">
<#include "nav.ftl">

<style type="text/css">
    .v-divider{
        width:1px;
        height:100%;
        border-left:1px solid gray;
    }
</style>
<div class="row">
    <div class="col-md-3">
        <h2>Usuarios conectados</h2>
        <ul class="list-group userlist" id="userlist">

        </ul>
    </div>

    <div class="col-md-6 v-divider">
        <div class="container snippet">
            <div class="row">
                <div class="col-md-10">
                    <div class="portlet portlet-default">
                        <div class="portlet-heading">
                            <div class="portlet-title">

                                    <h4><i class="portlet-title name">

                                </i></h4>

                            </div>
                            <div class="portlet-widgets">
                            <#if usuario??>
                                <input type="hidden" id="user" value="${usuario.getUsername()}">
                            </#if>
                                <button type="button" class="btn btn-primary" id="Conectar">Conectar</button>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div id="chat" class="panel-collapse collapse in">
                            <div>
                                <div class="portlet-body chat-widget" style="overflow-y: auto; width: auto; height: 300px;">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <p id='fechaDehoy' class="text-center text-muted small"></p>
                                        </div>

                                        <div class="row" id="del">
                                            <div class="col-lg-12">
                                                <div class="media">
                                                    <a class="pull-left" href="#">
                                                        <img class="media-object img-circle" src="" alt="">
                                                    </a>
                                                    <div class="media-body">
                                                        <h4 class="media-heading">
                                                            <span class="small pull-right"></span>
                                                        </h4>
                                                        <p></p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </div>
                            <div class="portlet-footer">
                                <form role="form">
                                    <div class="form-group">
                                        <textarea class="form-control" id="message" placeholder="Enter message..."></textarea>
                                    </div>
                                    <div class="form-group">
                                        <button type="button" id="send" class="btn btn-default pull-right">Send</button>
                                        <div class="clearfix"></div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.col-md-4 -->
            </div>
        </div>

    </div>

</div>


<script type="text/javascript" src="/js/websocketAdmin.js">


</script>
<#include "footer.ftl">