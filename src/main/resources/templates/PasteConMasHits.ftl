<#include "header.ftl">
<link type="text/css" rel="stylesheet" href="path_to/simplePagination.css"/>
<#include "nav.ftl">

<div class="container">
    <div class="row">
        <div class="col-md-offset-3 col-md-6">
            <div class="myTable">
                <h3>Paste con mas Hits</h3>
                <p id="pasteSize" style="display: none">${pasteSize}</p>
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
                        </tbody>
                        </#list>
                    </#if>
                    </table>
                </div>
                <div class="pagination">

                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="path_to/jquery.js"></script>
<script type="text/javascript" src="path_to/jquery.simplePagination.js">
    var $pasteSize = parseInt($('#pasteSize').text());

    $(function() {
        $(".pagination").pagination({
            items: $pasteSize,
            itemsOnPage: 10,
            cssStyle: 'light-theme',
            displayedPages: 3,
            edges:2,
            currentPage:1,
            prevText:'prev',
            nextText: 'next',
            onPageClick: function(pageNum) {
                // Which page parts do we show?
                currentPage=pageNum
                var start = itemsOnPage * (pageNum - 1);
                var end = start + itemsOnPage

                $.ajax({
                    url:'/paste/show/list/'+pageNum, success:function (data) {
                        $('#table-body').html(data);
                    }
                });

                // First hide all page parts
                // Then show those just for our page
                $(".pagination").hide()
                        .slice(start, end).show();
            }

        });
    });


</script>
<#include "footer.ftl">