<#if check??>
    ${check}
</#if>
<#list honey as honeys>
    <tr>
        <td>${honeys.id}.</td>
        <td>Name: ${honeys.name}.</td>
        <td>Description: ${honeys.description}</td>
        <td>Price: ${honeys.cost} RUB. <br></td>
    </tr>
</#list>