
<h3 class="translate">
    select_payment_method
</h3>

<table>
    <#list paymentMethods as paymentMethod>
        <tr>
            <td>
                <a href="javascript:sendPaymentId('${paymentMethod.id}')">
                    ${paymentMethod.type} - ${paymentMethod.subType} - ${paymentMethod.last4}
                </a>
                <a href="javascript:deletePaymentMethod('${paymentMethod.id}')">
                    <i class="delete fa-solid fa-circle-xmark"></i>
                </a>
            </td>
        </tr>
    </#list>
</table>
<br />
<a class="translate" href='/v1/owner/${id}/wallet/new?language=${language}'>
    create_payment_method
</a>