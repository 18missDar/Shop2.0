<#import "parts/common.ftl" as c>

<@c.page>

     <div class="form-row">
         <div class="form-group col-md-6">
             <form method="get" action="/usercarts" class="form-inline">
                 <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Search by username">
                 <button type="submit" class="btn btn-primary ml-2">Search</button>
             </form>
         </div>
     </div>

<table class="table">
  <thead>
    <tr>
      <th scope="col">Username</th>
      <th scope="col">Title</th>
      <th scope="col">Cost</th>
      <th scope="col">Amount</th>
    </tr>
  </thead>
  <tbody>
   <#list messages as message>
            <tr>
              <td>${message.username}</td>
              <td>${message.title}</td>
              <td>${message.cost}</td>
              <td>${message.amount}</td>
            </tr>
              <#else>
               Carts are empty
        </#list>
  </tbody>
</table>

</@c.page>