<#if pastes??>
    <#list pastes as paste>
    <tr>
        <td>${paste.getTitulo()}</td>
        <td>${paste.getCantidadVista()}</td>
        <td>${paste.getFechaPublicacion()}</td>
        <td>${paste.getSintaxis()}</td>
        <td>
        </td>
    </tr>
    </tbody>
    </#list>
</#if>