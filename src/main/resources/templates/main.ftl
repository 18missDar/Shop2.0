<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div>
    <@l.logout />
</div>
    <div>
        <form method="post">
            <input type="text" name="title" placeholder="Введите название товара" />
            <input type="text" name="description" placeholder="Введите описание товара" />
            <input type="text" name="cost" placeholder="Стоимость">
              <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button type="submit">Добавить</button>
        </form>
    </div>
    <div>Список товаров в базе</div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter}">
        <button type="submit">Найти</button>
    </form>
    <#list messages as message>
    <div>
        <b>${message.id}</b>
        <i>${message.title}</i>
        <i>${message.description}</i>
        <i>${message.cost}</i>
    </div>
    </#list>
</@c.page>