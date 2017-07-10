<#include "header.ftl">

<#include "nav.ftl">

<div class="container">

    <div class="caption">
        <h3 style="text-align:center;">Registrar</h3>
    </div>

    <form action="/user/signUp" method="post">

        <div class="row">
            <div class="col-md-offset-4 col-md-5">
                <div class="input-group input-group-md form-group">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-user"></i></span>
                    <input type="text" class="form-control" name="nombre" placeholder="Entre su Nombre" id="nombre" aria-describedby="sizing-addon1" size="10"  pattern=".{4,}" required>
                </div>
            </div>

        </div>

        <br>
        <div class="row">
            <div class="col-md-offset-4 col-md-5">
                <div class="input-group input-group-md form-group">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-user"></i></span>
                    <input type="text" class="form-control" name="username" placeholder="Nombre de usuario" id="Correo" pattern=".{4,}" aria-describedby="sizing-addon1" required>
                </div>
            </div>

        </div>
        <br>
        <div class="row">
            <div class="col-md-offset-4 col-md-5">
                <div class="input-group input-group-md form-group">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
                    <input type="password" name="password" class="form-control" placeholder="Entre su contrasena" pattern=".{4,}" aria-describedby="sizing-addon1" required>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-4 col-md-5">
                <div class="input-group input-group-md form-group">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
                    <input type="password" name="password-confirm" class="form-control" placeholder="Confirme su contrasena" pattern=".{4,}" aria-describedby="sizing-addon1" required>
                </div>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-md-offset-4 col-md-5 form-group">
                <button class="btn btn-lg btn-primary btn-block btn-signin form-control" id="IngresoLog" type="submit" >Registrar</button>

            </div>
        </div>
        <br>
    <#if confirm??>
        <p style="color:red;font-weight: bold">${confirm}</p>
    </#if>
    <#if message??>
        <p style="color:red;font-weight: bold">${message}</p>
    </#if>
    </form>
</div>


<#include "footer.ftl">

