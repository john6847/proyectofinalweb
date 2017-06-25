<#include "header.ftl">
<div class="container">
    <h1 class="well">Registrar Usuario</h1>
    <div class="col-md-12">
        <div class="row">
            <form>
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <label>Username</label>
                            <input type="text" placeholder="favor introducir usuario" class="form-control">
                        </div>

                        <div class="col-sm-6 form-group">
                            <label>Password</label>
                            <input type="password" class="form-control">
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-sm-4 form-group">
                            <label>Nombre</label>
                            <input type="text" class="form-control">
                        </div>
                        <div class="col-sm-4 form-group">
                            <label class="form-label-spacing" for="gender">Administrador:</label>
                            <div class="radio">
                                <label><input type="radio" name="gender" id="gender">Yes</label><br/>
                                <label><input type="radio" name="gender" id="gender">No</label>
                            </div>
                            <div class="col-sm-4 form-group">
                                <label class="form-label-spacing" for="gender">Autor:</label>
                                <div class="radio">
                                    <label><input type="radio" name="gender" id="gender">Yes</label><br/>
                                    <label><input type="radio" name="gender" id="gender">N</label>
                                </div>
                            </div>
                        </div>
                    </div>
            </form>
        </div>
    </div>
</div>
<#include "footer.ftl">

