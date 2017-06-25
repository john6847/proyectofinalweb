<#include "header.ftl">
<#include "nav.ftl">

<#if articulos??>
    <#if articulos?size!=0>
        <#list articulos as articulo>
        <div class="article-container line-content">
            <div class="row">
                <div class="col-md-offset- 2 col-md-9" id="header">
                    <a id="" href="/ver/articulo/${articulo.getId()}">
                        <h3 class="article-title">${articulo.getTitulo()}</h3>
                    </a>
                </div>

            </div>
            <div class="row">
                <div class="col-md-offset-2 col-md-9">
                    <#assign cuerpoArticulo = articulo.getCuerpo()>
                    <#if cuerpoArticulo?length &gt; 70>
                        <#assign maxLength = 70>
                    <#else>
                        <#assign maxLength = cuerpoArticulo?length>
                    </#if>
                    <p class="article-preview">${cuerpoArticulo?substring(0, maxLength)} ...<a href="/ver/articulo/${articulo.getId()}" >Leer mas</a></p>

                    <#if size??>
                        <#assign result =size/5>
                        <p id="cant" style="display: none">${result?ceiling}</p>
                        <p id="size" style="display: none">${size}</p>
                    </#if>


                </div>

            </div>

            <div class="row">
                <div class="col-md-offset-8 col-md-4">
                    <p><b>By:</b> <a href="/usuario/${articulo.getAutor().getId()}">${articulo.getAutor().getNombre()} <i class="fa fa-user"></i></a></p>
                </div>

                <#assign articuloEtiqueta = articulo.getEtiquetas()>
                <#if articuloEtiqueta?size != 0>
                    <div class="col-md-offset-1 col-md-6 article-tags">
                        <p>
                        <div >
                            Etiqueta(s) <i class="fa fa-tags"></i>:

                            <#list articuloEtiqueta as etiqueta>
                                <a class="btn icon-btn btn-default" style="color: #ffffff;font-size:14px;margin:4px;background-color:#9d9d9d;"  href="/etiqueta/${etiqueta.getEtiqueta()}/articulos">
                                    <span class="glyphicon glyphicon-tags"></span>${etiqueta.getEtiqueta()}
                                </a>

                            </#list>
                        </div>


                    </div>
                </#if>

            </div>

        </div>

        <br>

        </#list>
    <#else>
        <#if noDatos??>
        <div class="row">
            <div class="col-md-12">
                <div class="col-md-12">
                    <h3>${noDatos}...<a href="/agregar/articulo">Agregar un articlo?
                    </a></h3>
                </div>
            </div>
        </div>
        </#if>
    </#if>
</#if>
<div class="row" id="pagina">
    <div class="col-md-offset-5 col-md-7">
        <ul id="pagin">
        </ul>
    </div>

    <script type="text/javascript">

        var cant = parseInt($('#cant').text());

        for(var i = 0 ; i<cant;i++){
            if(i===0){
                $("#pagin").append('<li><a href="/">'+(i+1)+'</a></li> ');
            }else{
                var c= i+1;
                var sizeTotal=parseInt($('#size').text());
                $("#pagin").append(
                        $('<li>').append(
                                $('<a>').attr('href', '/page/galery/'+sizeTotal+"/"+c).append(c)));
            }

        }

    </script>
<#include "footer.ftl">