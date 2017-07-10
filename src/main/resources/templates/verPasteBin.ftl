<#include "header.ftl">
<#include "nav.ftl">

<div class="container">
    <div class="row">
        <div class="col-md-offset-1 col-md-9">
            <div class="myTable">
                <h3 style="text-align: center">My Pastes</h3>
                <div class="myTable-white">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>title</th>
                            <th>Hits</th>
                            <th>Date Created</th>
                            <th>Syntaxe</th>
                            <th>&nbsp</th>
                            <th>&nbsp</th>
                        </tr>
                        </thead>
                    <tbody>
                    <#if pastes??>
                        <#list pastes as paste>
                        <tr>
                            <td><a href="/paste/show/${paste.getId()}">${paste.getTitulo()}</a></td>
                            <td>${paste.getCantidadVista()}</td>
                            <td>${paste.getFechaPublicacion()}</td>
                            <td>${paste.getSintaxis()}</td>
                            <td>
                                <a href="/paste/modify/${paste.getId()}">
                                    <img src="/images/modify.png" alt="" title="update paste">
                                </a>
                            </td>
                            <td>
                                <a id="del" href="/user/delete/${paste.getId()}">
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
<script type="text/javascript">
    $(document).ready(function () {
        $("#del").click(function () {
            var response = prompt("Do you really want to delete that paste?");
            if(response.toLowerCase()==="no"){
                window.location.href = this.getAttribute('href');
            }
        });
    });



</script>
<#include "footer.ftl">