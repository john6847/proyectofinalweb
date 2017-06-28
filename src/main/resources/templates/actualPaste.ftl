<#include "header.ftl">
<#include "nav.ftl">
<div class="container">

    <div class="row">
        <div class="col-md-10" style="display:flex">
            <div style="height:100px;width:80px;border:1px solid green;margin-bottom:20px" >
                <img src="" alt="">
            </div>
            <div style="margin-left:30px">
                <h2 style="margin-bottom:20px">{Title}</h2>
                <div>
                    <img class="iconos"  src="/images/see.png" alt="seen">0
                    <img class="iconos" src="/images/calendar.png" alt="calendar">publication date
                    <img class="iconos" src="/images/guest-xxl.png" alt="guest">guest
                </div>
            </div>

        </div>
    </div>
    <div class="panel panel-primary">
        <div class="panel-body">

            <div class="panel panel-primary" >
                <div class="panel panel-heading" style="padding:15px"> {Language}
                    <div class="pull-right">
                        <button class="btn btn-primary">raw</button>
                        <button class="btn btn-primary">embed</button>
                        <button class="btn btn-primary">edit</button>
                        <button class="btn btn-primary">delete</button>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row">

                    </div>

                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <form action="">
                        <div class="form-group">
                            <label for="raw">Raw Paste Data</label>
                            <textarea name="raw" id="raw" class="form-control" cols="30" rows="10"></textarea>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

<#include "footer.ftl">