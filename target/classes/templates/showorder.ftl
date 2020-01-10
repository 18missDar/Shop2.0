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

    <h3><p>General sum</p></h3>

    <input type="text" name="sum" class="form-control" value="${sum}" readonly="readonly"/>

    <h3><p>Choose a delivery method</p></h3>

    <div class="form-check">
        <label class="form-check-label">
            <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1" value="option1" checked>
            Shipment at own expense to the address Moscow, Novoryazanskoye Highway, 151
        </label>
    </div>
    <div class="form-check">
        <label class="form-check-label">
            <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios2" value="option2">
            Delivery to
        </label>
        <input type="text" class="form-control" placeholder="Your address" name = "address"/>
    </div>

    <h3><p>Comments</p></h3>
    <textarea type = "text" style="overflow: auto" name = "file" cols="70" rows="8"></textarea>

    <div>
        <a class="btn btn-primary mt-3" href="/pay">
            To pay
        </a>
    </div>

</@c.page>