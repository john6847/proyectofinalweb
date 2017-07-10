ok
Bien-Aime Jerry
<#include "header.ftl">
<#include "nav.ftl">

<div class="container" id="paste-bin">
    <div class="panel panel-primary" style="width: 300px">
        <div class="panel-heading" style="background-color: #802022">
            <a href="/paste/show/list">
                <h4 class="public" style="text-decoration: none; color: white;">Public</h4>
            </a>
        </div>
        <div class="panel-body">
            <ul class="list-group">
            <#if publicPaste??>
                <#list publicPaste as p>
                    <li class="list-group-item"><a href="/paste/show/${p.getId()}">${p.getTitulo()}</a><span class="pull-right">${p.getSintaxis()}</span></li>
                </#list>
            </#if>
            </ul>
        </div>
    </div>
    <div class="panel panel-primary" style="width:800px; margin-left: 30px">
        <div class="panel-heading">Copy and Paste</div>
        <div class="panel-body">

            <form action="" method="post" onsubmit="return(validate());" >
                <div class="row">
                    <div class="col-md-offset col-md-7">
                        <div class="form-group">
                            <label for="title">Title</label>
                            <input type="text" class="form-control" name="title" id="title"
                                   placeholder="Enter the Paste title here" value="${paste.getTitulo()}">
                        </div>
                        <div class="form-group">
                            <label for="bloqueDeTexto">New Paste</label>
                            <textarea class="form-control" name="bloqueDeTexto" id="bloqueDeTexto" cols="50"
                                      rows="20">${paste.getBloqueDeCodigo()}</textarea>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <div class="form-group" style="margin-top:77px">
                            <label for="syntax">Syntax</label>
                            <select class="form-control" name="syntax" id="syntax">
                                <option selected="disabled">Select One</option>
                                <option>apache</option>
                                <option>bash</option>
                                <option>c#</option>
                                <option>c++</option>
                                <option>css</option>
                                <option>coffeeScript</option>
                                <option>diff</option>
                                <option>html</option>
                                <option>xml</option>
                                <option>http</option>
                                <option>ini</option>
                                <option>json</option>
                                <option>java</option>
                                <option>javascript</option>
                                <option>makefile</option>
                                <option>markdown</option>
                                <option>ngnix</option>
                                <option>objective-C</option>
                                <option>php</option>
                                <option>perl</option>
                                <option>python</option>
                                <option>ruby</option>
                                <option>sql</option>
                                <option>shell</option>
                                <option>session</option>
                                <option>arduino</option>
                                <option>arm assembler</option>
                                <option>clojure</option>
                                <option>django</option>
                                <option>excel</option>
                                <option>f#</option>
                                <option>go</option>
                                <option>haskell</option>
                                <option>groovy</option>
                                <option>r</option>
                                <option>sml</option>
                                <option>swift</option>
                                <option>vb.net</option>
                                <option>yaml</option>

                            </select>
                        </div>
                        <div class="form-group">
                            <label for="expirationDate">Expiration Date</label>
                            <select class="form-control" name="expirationDate" id="expirationDate">
                                <option selected="disabled">Select One</option>
                                <option>never</option>
                                <option>10 minutes</option>
                                <option>15 minutes</option>
                                <option>30 minutes</option>
                                <option>1 hour</option>
                                <option>1 day</option>
                                <option>1 week</option>
                            </select>
                            <div class="form-group">
                                <label for="expositionType">Exposition Type</label>
                                <select class="form-control" name="expositionType" id="expositionType">
                                    <option selected="disabled">Select One</option>
                                    <option>public</option>
                                    <option>private</option>
                                    <option>unlisted</option>
                                </select>
                            </div>
                            <div class="row">
                                <div class="col-md-offset-6 col-md-6">
                                    <div class="form-group" style="margin-top:190px">
                                        <button class="btn btn-primary form-control" type="submit">Create New Paste
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
            </form>
        </div>
    </div>

</div>

<script type="text/javascript">

    function validate() {

        if (document.myForm.title.value === "") {
            alert("Please provide the title of the form");
            document.myForm.title.focus();
            return false;
        }

        if (document.myForm.bloqueDeTexto.value === "") {
            alert("Please provide the text block of the form");
            document.myForm.bloqueDeTexto.focus();
            return false;
        }

        if (document.myForm.syntax.value === "Select One")
        {
            alert('Please enter Syntax name');
            document.getElementById('syntax').style.borderColor = "red";
            return false;
        }

        if (document.myForm.expirationDate.value === "Select One")
        {
            alert('Please enter expiration date');
            document.getElementById('expirationDate').style.borderColor = "red";
            return false;
        }

        if (document.myForm.expositionType.value === "Select One")
        {
            alert('Please enter expiration date');
            document.getElementById('expositionType').style.borderColor = "red";
            return false;
        }


    }

</script>
<#include "footer.ftl">