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


    <div class="custom-control custom-checkbox">
        <input type="checkbox" class="custom-control-input" id="defaultUnchecked">
        <label class="custom-control-label" for="defaultUnchecked">Shipment at own expense to the address Moscow, Novoryazanskoye Highway, 151</label>
    </div>

    <div class="custom-control custom-checkbox">
        <input type="checkbox" class="custom-control-input" id="defaultUnchecked1">
        <label class="custom-control-label" for="defaultUnchecked1">Delivery to</label>
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