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
            </tr>
        <#else>
            Your cart is empty
        </#list>
        </tbody>
    </table>

    <p>General sum</p>

    <input type="text" name="sum" class="form-control" value="${sum}" readonly="readonly"/>

    <a class="btn btn-primary mt-3" href="/pay">
        To pay
    </a>

</@c.page>