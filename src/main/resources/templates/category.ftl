
<#import "parts/common.ftl" as c>

<@c.page>
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
</@c.page>