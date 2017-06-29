<#include "header.ftl">
<#include "nav.ftl">

<div class="container">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
            <div class="myTable">
                <h3>My Pastes</h3>
                <div class="myTable-white">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>title</th>
                            <th>Hits</th>
                            <th>Date Created</th>
                            <th>Syntaxe</th>
                            <th>&nbsp</th>
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
                                        <a href="/paste/modify/${paste.getId()}">
                                            <img src="/images/modify.png" alt="" title="update paste">
                                        </a>
                                        <a href="/paste/delete/${paste.getId()}">
                                            <img src="/images/poubelle.png" alt="" title="delete paste">
                                        </a>
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