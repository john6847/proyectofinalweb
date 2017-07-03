<#include "header.ftl">
<#include "nav.ftl">

<div class="container">
    <div class="row">
        <div class="col-md-offset-1 col-md-9">
            <div class="myTable">
                <h3 style="text-align: center">Public Paste</h3>
                <#if pasteSize??>
                    <p id="pasteSize" style="display: none">${pasteSize}</p>
                </#if>
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
                    <tbody id="table-body">
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
                        </#list>
                    </#if>
                    </tbody>
                    </table>
                </div>
                <div class="row" id="pagina">
                    <div class="col-md-offset-5 col-md-7">
                        <ul id="pagin">
                        </ul>
                    </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        var $pasteSize = parseInt($('#pasteSize').text());
        var numberOfPages= Math.ceil($pasteSize / 10);

        var element = {
            items: $pasteSize,
            NumberOfpage:numberOfPages
        };
        for (var i = 0; i <element.NumberOfpage; i++) {
            if (i === 0) {
                $("#pagin").append('<li><a href="/paste/show/list">' + (i + 1) + '</a></li> ');
            } else {
                var c = i + 1;
                $("#pagin").append(
                        $('<li>').append(
                                $('<a>').addClass("pagesNumber").append(c)));
             }
        }

        $('.pagesNumber').click(function () {
            $.ajax({
                url: '/paste/show/list/' + element.items + '/' + $(this).text(), success: function (data) {
                    $('tbody').html(data);
                }
            });
        });
});

</script>
<#include "footer.ftl">