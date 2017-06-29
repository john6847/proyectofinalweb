<#include "header.ftl">
<#include "nav.ftl">

<div class="container">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
            <div class="myTable">
                <h3>Paste con mas Hits</h3>
                <div class="myTable-white">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Name/Title</th>
                            <th>Hits</th>
                            <th>Posted</th>
                            <th>Syntax</th>
                        </tr>
                        </thead>
                    <tbody>
                    <#if pastes??>
                        <#list pastes as paste>
                        <tr>
                            <td>${paste.getTitulo()}</td>
                            <td>${paste.getCantidadVista()}</td>
                            <td>${paste.getFechaPublicacion()}</td>
                            <td>${paste.getSintaxis()}</td>
                            <td>
                            </td>
                        </tr>
                        </tbody>
                        </#list>
                    </#if>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "footer.ftl">