<#import "spring.ftl" as spring />
<#if check??>
    ${check}
</#if>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<form id="productForm" action="/honey/add" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input id="name" type="text" name="name" placeholder="<@spring.message 'honey.name'/>">
    <input id="price" type="number" name="price" placeholder="<@spring.message 'honey.price'/>">
    <input type="submit" name="addHoney" id="addHoney" class="form-submit" value="<@spring.message 'honey.add'/>"/>
</form>