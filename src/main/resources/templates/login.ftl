<#include "header.ftl">
<#include "nav.ftl">


    <div class="container">
    
         <div class="image">
        <!--Icono de usuario-->
        <img class="img-responsive" style="margin:auto; height:100px;width:100px;"  src="/images/login.png"/>
            </div>
           
    <div class="caption">
        <h3 style="text-align:center;">Login</h3>
    </div>
     <br>
    <form action="/login" method="post">
        
        <div class="row">
          <div class="col-md-offset-4 col-md-5">              
        <div class="input-group input-group-md form-group">
            <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-envelope"></i></span>
            <input type="text" class="form-control" name="username" min="2" placeholder="Nombre de usuario" id="usuario" aria-describedby="sizing-addon1" required>
        </div> 
          </div> 
            
        </div>
        <br>
        <div class="row">
            <div class="col-md-offset-4 col-md-5">                  
                <div class="input-group input-group-md form-group">
                    <span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
                    <input type="password" name="password" min="4" class="form-control" placeholder="******" aria-describedby="sizing-addon1" required>
                </div>
            </div>
        </div>
      <div class="row">
          <div class="col-md-offset-4 col-md-5 form-group">
                <button class="btn btn-lg btn-primary btn-block btn-signin form-control" id="IngresoLog" type="submit" >Entrar</button>
                <br>
                <div class="opcioncontra"><a href="/registrar" style="color:blue;">No tienes cuentas, Registrar ahora?</a></div>
          </div>
      </div>
        <br>
        <br>
        <br>
        <#if message??>
        <p style="color:red;font-weight: bold">${message}</p>
        </#if>
    </form>
    </div>

    
<#include "footer.ftl">