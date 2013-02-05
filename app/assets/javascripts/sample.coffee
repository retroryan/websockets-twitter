$ ->
  $.get "/cities", (data) ->
    $.each data, (index, item) ->
      $("#cities").append $("<li>").text item.name