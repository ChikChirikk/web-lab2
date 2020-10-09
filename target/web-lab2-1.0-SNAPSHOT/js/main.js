$(function() {
  let rval;

  function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
  }
  
  function validateY() {
    const Y_MIN = -3;
    const Y_MAX = 5;

    let numY = $('.input-form__text_y').val().replace(',', '.');
  
    if (isNumeric(numY) && numY >= Y_MIN && numY <= Y_MAX)
    {
      $('.input-form__info').text('Введите координаты точки')
      return true;
    } else {
      $('.input-form__info').text('Введите значение Y от -3 до 5!')
      return false;
    }
  }
  
  function validateR() {
    if (rval !== undefined) {
      $('.input-form__info').text('Введите координаты точки')
      return true;
    } else {
      $('.input-form__info').text('Выберите значение R!')
      return false;
    }
  }
  
  function validateForm() {
    return validateY() && validateR();
  }

  $('.input-form').on('submit', function(event) {
    event.preventDefault();
    if (!validateForm()) return;
    newRow = '<tr>';
    newRow += '<td>' + $('.input-form__select_x').val() + '</td>';
    newRow += '<td>' + $('.input-form__text_y').val() + '</td>';
    newRow += '<td>' + rval + '</td>';
    newRow += '<td>' + '---' + '</td>';
    newRow += '<td>' + '---' + '</td>';
    newRow += '<td>' + '???' + '</td>';
    $('.result-table').append(newRow);
  });

  $('.input-form__button_r').on('click', function(event) {
    rval = $(this).val();
    $(this).addClass('input-form__button_r_clicked');
    $('.input-form__button_r').not(this).removeClass('input-form__button_r_clicked');
  })
});
