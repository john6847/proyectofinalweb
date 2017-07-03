<#if pastes??>
    <#list pastes as paste>
    <tr>
        <td><a href="/paste/show/${paste.getId()}">${paste.getTitulo()}</a></td>
        <td>${paste.getCantidadVista()}</td>
        <#assign fecha = (paste.getFechaPublicacion()*1000)?number_to_datetime>
        <td>${fecha}</td>
        <td>${paste.getSintaxis()}</td>
        <td>
        </td>
    </tr>
    </#list>
</#if>