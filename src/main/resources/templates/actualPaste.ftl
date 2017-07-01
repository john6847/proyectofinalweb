<#include "header.ftl">
<#include "nav.ftl">
<div class="container">

    <div class="row">
        <div class="col-md-10" style="display:flex">
            <div style="height:100px;width:80px;border:1px solid green;margin-bottom:20px" >
                <img src="" alt="">
            </div>
            <div style="margin-left:30px">
                <h2 style="margin-bottom:20px">${paste.getTitulo()}</h2>
                <div>
                    <img class="iconos"  src="/images/see.png" alt="seen">0
                    <img class="iconos" src="/images/calendar.png" alt="calendar">${fecha}
                    <img class="iconos" src="/images/guest.png" alt="guest">${user}
                </div>
            </div>

        </div>
    </div>
    <div class="panel panel-primary">
        <div class="panel-body">

            <div class="panel panel-primary" >
                <div class="panel panel-heading" style="padding:15px"> ${paste.getSintaxis()}
                    <div class="pull-right">
                        <button class="btn btn-default">raw</button>
                        <button class="btn btn-default">embed</button>
                        <button class="btn btn-default">edit</button>
                        <button class="btn btn-default">delete</button>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-12">
                            <form action="">
                                <div class="form-group">
                                    <label for="raw">${titulo}</label>
                                        <pre >
                                            <code class="${paste.getSintaxis()}">

                                                ${paste.getBloqueDeCodigo()}

                                            </code>


                                        </pre>

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


    <script>
        $(document).ready(function() {
            $('pre code').each(function(i, e) {
                hljs.highlightBlock(e);
            });
        });
   </script>

<#include "footer.ftl">