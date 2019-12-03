<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
<#if name !="unknown">
<div class="row">
  <div class="col-3">
    <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
      <a class="nav-link active" id="v-pills-home-tab" data-toggle="pill" href="#" role="tab" aria-controls="v-pills-home" aria-selected="true">Category1</a>
      <a class="nav-link" id="v-pills-profile-tab" data-toggle="pill" href="#" role="tab" aria-controls="v-pills-profile" aria-selected="false">Category2</a>
      <a class="nav-link" id="v-pills-messages-tab" data-toggle="pill" href="#" role="tab" aria-controls="v-pills-messages" aria-selected="false">Category3</a>
      <a class="nav-link" id="v-pills-settings-tab" data-toggle="pill" href="#" role="tab" aria-controls="v-pills-settings" aria-selected="false">Category4</a>
    </div>
  </div>
  <div class="col-9">
    <div class="tab-content" id="v-pills-tabContent">
     <div class="form-row">
         <div class="form-group col-md-6">
             <form method="get" action="/main" class="form-inline">
                 <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Search by title">
                 <button type="submit" class="btn btn-primary ml-2">Search</button>
             </form>
         </div>
     </div>

     <#if isAdmin>
     <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
         Add new Good
     </a>
     </#if>
     <div class="collapse" id="collapseExample">
         <div class="form-group mt-3">
             <form method="post" enctype="multipart/form-data">
                 <div class="form-group">
                     <input type="text" class="form-control" name="title" placeholder="Введите название товара" />
                 </div>
                 <div class="form-group">
                     <input type="text" class="form-control" name="description" placeholder="Введите описание товара">
                 </div>
                 <div class="form-group">
                     <input type="text" class="form-control" name="cost" placeholder="Стоимость">
                 </div>
                 <div class="form-group">
                     <div class="custom-file">
                         <input type="file" name="file" id="customFile">
                         <label class="custom-file-label" for="customFile">Choose file</label>
                     </div>
                 </div>
                 <input type="hidden" name="_csrf" value="${_csrf.token}" />
                 <div class="form-group">
                     <button type="submit" class="btn btn-primary">Add</button>
                 </div>
             </form>
         </div>
     </div>
         <div class="card-columns">
             <#list messages as message>
             <div class="card my-3">
                 <#if message.filename??>
                 <img src="/img/${message.filename}" class="card-img-top">
                 </#if>
                 <div class="m-2">
                    <i>${message.title}</i>
                    <i>${message.description}</i>
                 </div>
                 <div class="card-footer text-muted">
                     <p> Цена: ${message.cost}</p>
                     <div class="form-group">
                        <a href="#" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Add to basket</a>
                     </div>
                 </div>
             </div>
             <#else>
             No message
             </#list>
         </div>

    </div>
  </div>
  <footer class="page-footer font-small teal pt-4">

    <!-- Footer Text -->
    <div class="container-fluid text-center text-md-left">
      <div class="row">
        <div class="col-md-10 mt-md-0 mt-3">
        <p>Your basket is </p>
        </div>
      </div>
    </div>
    <div class="footer-copyright text-center py-3">© 2019
    </div>

  </footer>
</div>
 </#if>












</@c.page>