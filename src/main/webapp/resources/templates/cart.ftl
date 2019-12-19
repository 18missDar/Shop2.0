<#import "parts/common.ftl" as c>

<@c.page>

<table class="table">
  <thead>
    <tr>
      <th scope="col">Title</th>
      <th scope="col">Cost</th>
      <th scope="col">Amount</th>
    </tr>
  </thead>
  <tbody>
   <#list messages as message>
            <tr>
             <td>${message.title}</td>
              <td>${message.cost}</td>
              <td>${message.amount}</td>
              <td><a href="/deleteitem/${message.uid}" class="btn btn-primary btn-lg active">delete</a></td>
            </tr>
              <#else>
               Your cart is empty
        </#list>
  </tbody>
</table>

</@c.page>