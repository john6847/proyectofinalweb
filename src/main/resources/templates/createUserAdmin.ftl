<#include "header.ftl">
<#include "nav.ftl">

<link rel="stylesheet" href="font-awesome.min.css">

<style>
    .button,.button2, .button:visited,.button2:visited {
        background-color:grey ;
        color: white;
        padding: 2px 5px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
    }


    .button:hover,.button2:hover,.button:active, .button2:active {
        background-color: #008CBA;
        text-decoration: none;
        text-emphasis-color: whitesmoke;
    }
</style>

<div class="container">
    <div class="container">
        <div class="row">
            <div class="col-md-offset-1 col-md-9">
                <div class="myTable">
                    <h3 style="text-align: center">List user </h3>
                    <div class="myTable-white">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>Username</th>
                                <th>Name</th>
                                <th>Set as admin</th>
                                <th></th>

                            </tr>
                            </thead>
                            <tbody id="table-body">
                            <#if usuarios??>
                                <#list usuarios as usuario>
                                <tr>
                                    <td>${usuario.getId()}</td>
                                    <td>${usuario.getUsername()}</td>
                                    <td>${usuario.getName()}</td>
                                    <td><a href="/user/add/admin/${usuario.getId()?string.computer}"  class="button button2" >Set as Admin</a></td>
                                </tr>
                                </#list>
                            <#else >
                                    <p>${"No existe usuario para transformar como administrador en el momento"}</p>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
</div>

        <script type="text/javascript">

            $(document).ready(function () {
                $("#findUser").on("input",function (e) {
                    var texto=$(this).text();

                    $.ajax({
                        datatype: "json",
                        url: '/user/add/admin',
                        data:{text: texto},
                        success: function (data) {
                            console.log(data);
                        }
                    })
                });
            });

//                    $.post('/user/add/admin',
//                            {
//                                text: texto
//                            },
//
//                            function (data, status)
//                            {
//                            var text=JSON.parse(data.data);
//                            console.log(text)
//                            });
//                    });

        </script>
<#include "footer.ftl">