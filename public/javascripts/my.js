$(document).ready(function () {

    $(".button-collapse").sideNav();

    $(".dropdown-button").dropdown({
        belowOrigin: true
    });

    $('select').material_select();

    $('.datepicker').pickadate({
        selectMonths: true,
        selectYears: 15
    });

    $('.modal-trigger').leanModal({
            dismissible: true, // Modal can be dismissed by clicking outside of the modal
            opacity: .5, // Opacity of modal background
            in_duration: 300, // Transition in duration
            out_duration: 200, // Transition out duration
            ready: function () {
                alert('Ready');
            }, // Callback for Modal open
            complete: function () {
                alert('Closed');
            } // Callback for Modal close
        }
    );

});


function like(id) {
    $.ajax({
        url: '/like',
        data: {"id": id},
        method: "GET",
        success: function (data) {
            var $like = $('#like-'+id);
            if(data == "remove"){
                $like.removeClass("blue-text");
                return;
            }
            $like.addClass(data);
        },
        error: function () {
            alert("Ocorreu um erro ao curtir... Tente novamente mais tarde!");
        }
    });
}

function comment(id) {
    $('#commentModal').openModal();
}


function addComment() {
    var pessoaNome  = $('#pessoaNome').val();
    var comentario  = $('#comentario').val();
    var idPost      = $('#idPost').val();;

    $.ajax({
        url: '/addComment',
        data: {"idPessoa": pessoaNome, "comentario" : comentario, "idPost" : idPost},
        method: "GET",
        success: function (data) {
            if(data == "erro"){
                alert("Erro... :(");
                return;
            }

            var commmentString = '<li class="collection-item avatar"><i class="material-icons circle red">play_arrow</i><span class="title">'+ pessoaNome +'</span><p>'+comentario+'</p></li>';
            $("#modal-content ul").append(commmentString);
            $('#comentario').val("");

        },
        error: function () {
            alert("Ocorreu um erro ao comentar... Tente novamente mais tarde!");
        }
    });
}

