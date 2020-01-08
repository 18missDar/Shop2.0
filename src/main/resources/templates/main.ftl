<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>

    <div class="row">
        <div class="col-3">
            <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
                <form method="get" action="/main/category" class="form-inline">
                    <input type="hidden" name="category" class="form-control" value="${1}">
                    <button type="submit" class="btn btn-primary ml-4 mt-1">Category 1</button>
                </form>
                <form method="get" action="/main/category" class="form-inline">
                    <input type="hidden" name="category" class="form-control" value="${2}">
                    <button type="submit" class="btn btn-primary ml-4 mt-1">Category 2</button>
                </form>
                <form method="get" action="/main/category" class="form-inline">
                    <input type="hidden" name="category" class="form-control" value="${3}">
                    <button type="submit" class="btn btn-primary ml-4 mt-1">Category 3</button>
                </form>
                <form method="get" action="/main/category" class="form-inline">
                    <input type="hidden" name="category" class="form-control" value="${4}">
                    <button type="submit" class="btn btn-primary ml-4 mt-1">Category 4</button>
                </form>
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
                                <input type="text" class="form-control" name="category" placeholder="Введите артикул категории">
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

                <#if isAdmin>
                    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample1" role="button" aria-expanded="false" aria-controls="collapseExample">
                        Add goods from XML
                    </a>
                </#if>
                <div class="collapse" id="collapseExample1">
                    <div class="form-group mt-3">
                        <form method="post" action="/xml" >
                            <textarea type = "text" style="overflow: auto" name = "file" cols="100" rows="12"></textarea>
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <div class="form-group">
                                <button type="submit" class="btn btn-primary"> Add XML</button>
                            </div>
                        </form>
                    </div>
                </div>


                <#if name =="guest">
                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                        <strong>Attention!</strong>  If you want to add goods in your cart, please, <a href="/login" class="alert-link">Log in</a> to the system . Thank you:)

                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </#if>

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
                                    <#if isAdmin>
                                        <a href="/delete/${message.id}" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Delete</a>
                                        <a href="/update/${message.id}" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Update</a>
                                    <#else>
                                        <a href="/add/${message.id}" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Add to basket</a>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    <#else>
                        No message
                    </#list>
                </div>

            </div>
        </div>

    </div>


</@c.page>