
let translations = {
    "en-US": {
        "select_payment_method": "Select a payment method.",
        "create_payment_method": "Create Payment Method",
        "create_payment_method_header": "Create a new payment method",
        "last4": "Last Four Of Card",
        "type": "Payment Method Type",
        "sub_type": "Type of Credit Card",
        "create_button": "Create"
    },
    "es-US": {
        "select_payment_method": "seleccione un método de pago",
        "create_payment_method": "Crear método de pago",
        "create_payment_method_header": "Crear un nuevo método de pago",
        "last4": "Últimos cuatro de la tarjeta",
        "type": "Tipo de método de pago",
        "sub_type": "Tipo de tarjeta de crédito",
        "create_button": "Crear"
    }
}

function translate(language) {
    var objectsToTranslate = document.getElementsByClassName("translate");

    for(let object of objectsToTranslate) {
        if (object.innerText) {
            var message = translations[language][object.innerText];
            if (message === undefined) {
                message = object.innerText;
            }
            object.innerHTML = message;
        }
    }
}

function sendPaymentId(paymentMethodId) {
    parent.postMessage(`PaymentMethodSelected:${paymentMethodId}`);
}

function deletePaymentMethod(paymentMethodId) {
    alert(`Deleting payment method ${paymentMethodId}`);
}