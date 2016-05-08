/*
    @parameter description String mensaje a mostrar en el alert
    @parameter parameters Hash
        @parameter int type: 1 => success, 2 => info, 3 => error
        @parameter int interval: duracion en seundos del alert
 */
function showAlert(description, parameters) {

  if (typeof parameters == 'undefined') {
    parameters = {type: 1, interval: 3};
  }

  Messenger.options = {
      extraClasses: 'messenger-fixed messenger-on-top messenger-on-right',
      theme: 'flat'
  };

  var typeAlert;
  switch (parameters.type) {
    case 2:
      typeAlert = 'info';
      break;

    case 3:
      typeAlert = 'error';
      break;

    default:
      typeAlert = 'success';
  }

  parameters.interval = (typeof parameters.interval == 'undefined')? 4 : parameters.interval;

    Messenger().post({
        message: description,
        type: typeAlert,
        hideAfter: parameters.interval,
        showCloseButton: true
    });
}
