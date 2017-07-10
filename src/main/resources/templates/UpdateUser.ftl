<#include "header.ftl">
<#include  "nav.ftl">
<div class="container">
    <h1 class="well">Actualizar Usuario</h1>

    <div class="panel panel-primary">

        <div class="panel-body">
            <form enctype="multipart/form-data" method="post" action="/user/update/profile">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="nombre">Name:</label>
                            <input type="text" class="form-control" id="nombre" name="name" pattern="[A-Za-z].{4,}" value="${usuario.getName()}"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" readonly class="form-control" pattern="[A-Za-z].{4,}" value="${usuario.getUsername()}">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">

                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" class="form-control" name="email">
                            </div>
                    </div>

                    <div class="col-md-6">
                            <div class="form-group">
                                <label for="occupation">Occupation</label>
                                <input type="text" class="form-control" pattern="[A-Za-z].{5,}" id="occupation" class="form-control" name="occupation">
                            </div>
                    </div>
                </div>


                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="phoneNumber">Phone Number</label>
                            <input type="tel" class="form-control" min="0" step="1" pattern="\d*" id="phoneNumber" name="phoneNumber">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label for="dateOfBirth"> Date of Birth</label>
                            <input type="date" id="dateOfBirth" pattern="\d{1,2}/\d{1,2}/\d{4}" name="dateOfBirth" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label id="photo">Choose Perfil Icon</label>
                            <input type="file" name="photo" class="form-control">
                        </div>
                    </div>
                    <div class="col-md-6">

                    </div>
                </div>

                <div class="row">
                    <div class="col-md-4">

                    </div>
                    <div class="col-md-8">
                        <div class="col-md-3">
                            <div class="form-group">
                                <input type="reset" class="btn btn-primary form-control" value="Reset">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <input type="submit" class="btn btn-primary form-control" value="Update">
                            </div>
                        </div>

                    </div>
                </div>
            </form>

        </div>
    </div>
</div>
</div>

<#include "footer.ftl">

