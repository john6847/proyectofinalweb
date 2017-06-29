<#include "header.ftl">
<#include "nav.ftl">

<div class="container">
    
    <div class="panel panel-primary">
      <div class="panel-heading">Copy and Paste</div>
      <div class="panel-body">
        
            <form action="/" method="post">
               <div class="row">
                    <div class="col-md-offset col-md-7">
                           <div class="form-group">
                               <label for="title">Title</label>
                               <input type="text" class="form-control" name="title" id="title" placeholder="Enter the Paste title here">
                           </div>
                            <div class="form-group">
                                  <label for="bloqueDeTexto">New Paste</label>
                                  <textarea class="form-control" placeholder="Enter text to paste" name="bloqueDeTexto" id="bloqueDeTexto" cols="50" rows="20">
                                  </textarea>
                            </div>
                     </div>
                        <div class="col-md-5">
                             <div class="form-group" style="margin-top:77px">
                                  <label for="syntax">Syntax</label>
                                   <select class="form-control" name="syntax" id="syntax">
                                       <option>
                                           
                                       </option>
                                   </select>
                             </div>
                            <div class="form-group">
                                <label for="expirationDate">Expiration Date</label>
                                <input type="date" name="expirationDate" class="form-control" id="expirationDate">
                            </div>
                            <div class="form-group">
                               <label for="expositionType">Exposition Type</label>
                                <select class="form-control" name="expositionType" id="expositionType">
                                       <option selected="false"> 
                                       Select One
                                       </option>
                                   </select>
                            </div>
                            <div class="row">
                                <div class="col-md-offset-6 col-md-6">
                                <div class="form-group" style="margin-top:190px">
                                <button class="btn btn-primary form-control" type="submit">Create New Paste</button>
                            </div>
                                </div>
                            </div>
                        </div>
                      
                </div>
            </form>
            
        
    </div>
    </div>
<#include "footer.ftl">