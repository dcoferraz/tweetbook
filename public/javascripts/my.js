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

/**
 * Liking a post
 * @param postId
 * @description ajax call to persist like
 */
function like(idPost, idUser) {

    $.ajax({
        url: '/like',
        data: {"idPost": idPost, "idUser": idUser},
        method: "GET",
        success: function (data) {
            var $like = $('#like-' + idPost);
            if (data == "remove") {
                $like.removeClass("blue-text");
                return;
            }
            $like.addClass(data);
            Materialize.toast('Liked :)', 2000);
        },
        error: function () {
            Materialize.toast('<span>Ocorreu um erro ao curtir... Tente novamente mais tarde!</span><a class="btn-flat red-text" href="#!">OK<a>', 4000);
            //alert("Ocorreu um erro ao curtir... Tente novamente mais tarde!");
        }
    });
}

/**
 * Open Modal
 * @param postId
 * @description Opens Modal to comment com Post
 */
function comment(id) {

    $.ajax({
        url: '/getComments',
        data: {"idPost": id},
        method: "GET",
        success: function (data) {
            if (data == "erro") {
                alert("Erro... :(");
                return;
            }

            $("#modal-content ul").empty();

            console.log(data);

            $(data).each(function (i, e) {
                console.log(e);
                var commmentString = '<li class="collection-item avatar"><img src="' + e["criador"]["urlImagem"] + '" class="circle"><span class="title">' + e["criador"]["nome"] + '</span><p>' + e["texto"] + '</p></li>';
                $("#modal-content ul").append(commmentString);
            });

        },
        error: function () {
            Materialize.toast("Ocorreu um erro carregar os comentarios... Tente novamente mais tarde!", 4000);
        }
    });


    $('#idPost').val(id);
    $('#commentModal').openModal();
}

/**
 * Add Comment
 * @description Gets the form from the modal screen and ajax's it to persist
 */
function addComment(idUsuario) {
    var pessoaNome = $('#pessoaNome').val();
    var comentario = $('#comentario').val();
    var idPost = $('#idPost').val();

    $.ajax({
        url: '/addComment',
        data: {"idPessoa": idUsuario, "comentario": comentario, "idPost": idPost},
        method: "GET",
        success: function (data) {
            if (data == "erro") {
                alert("Erro... :(");
                return;
            }

            var commmentString = '<li class="collection-item avatar"><i class="material-icons circle red">comment</i><span class="title">' + pessoaNome + '</span><p>' + comentario + '</p></li>';
            $("#modal-content ul").append(commmentString);
            $('#comentario').val("");

            Materialize.toast('Coment&aacute;rio adicionado!', 4000);

        },
        error: function () {
            Materialize.toast("Ocorreu um erro ao comentar... Tente novamente mais tarde!", 4000);
            //alert("Ocorreu um erro ao comentar... Tente novamente mais tarde!");
        }
    });
}

/**
 * Salva novo amigo
 * @param idUser, idAmigo
 * @description Salva novo amigo no banco
 */
function addAmigo(idUser, idAmigo) {
    $.ajax({
        url: '/addAmigo',
        data: {"idUser": idUser, "idAmigo": idAmigo},
        method: "GET",
        success: function (data) {
            var $addFriend = $('#user-' + idAmigo);

            if (data == "remove") {
                $addFriend.text('person_add');
                $addFriend.removeClass("red-text");
                $addFriend.addClass("green-text");

                Materialize.toast('<span>Removido com sucesso</span><a class="btn-flat yellow-text" href="#!">OK<a>', 4000);
                return;
            }

            $addFriend.removeClass("green-text");
            $addFriend.addClass("red-text");
            $addFriend.text('cancel');
            Materialize.toast('<span>Adicionado com sucesso</span><a class="btn-flat green-text" href="#!">OK<a>', 4000);
        },
        error: function () {
            alert("Ocorreu um erro ao adicionar um amigo... Tente novamente mais tarde!");
        }
    });
}

/**
 * Open Modal
 * @param groupId
 * @description Opens Modal to invite friends to group
 */
function invite(idGrupo) {

    $.ajax({
        url: '/getPessoas',
        data: {},
        method: "GET",
        success: function (data) {
            if (data == "erro") {
                alert("Erro... :(");
                return;
            }

            $("#modal-content ul").empty();

            console.log(data);

            $(data).each(function (i, e) {
                console.log(e);
                var pessoaDOMString = '<li class="collection-item avatar"><img src="'+e["urlImagem"]+'" alt="'+e["nome"]+'" class="circle"><span class="title">'+e["nome"]+'</span>'+
                    '<a href="javascript:addParticipante('+idGrupo+','+e["id"]+')" class="secondary-content"><i class="material-icons grey-text" id="user-'+e["id"]+'">group_add</i></a></li>';
                $("#modal-content ul").append(pessoaDOMString);
            });

        },
        error: function () {
            Materialize.toast("Ocorreu um erro carregar os comentarios... Tente novamente mais tarde!", 4000);
        }
    });

    $('#idGrupo').val(idGrupo);
    $('#inviteModal').openModal();
}

/**
 * Salva novo participante
 * @param idGrupo, idParticipante
 * @description Salva novo participante na relação do grupo
 */
function addParticipante(idGrupo, idParticipante) {
    $.ajax({
        url: '/addParticipante',
        data: {"idGrupo": idGrupo, "idParticipante": idParticipante},
        method: "GET",
        success: function (data) {
            var $addParticipante = $('#user-' + idParticipante);

            if (data == "remove") {
                $addParticipante.removeClass("green-text");
                $addParticipante.addClass("grey-text");

                Materialize.toast('<span>Removido com sucesso</span><a class="btn-flat yellow-text" href="#!">OK<a>', 4000);
                return;
            }

            $addParticipante.removeClass("grey-text");
            $addParticipante.addClass("green-text");
            Materialize.toast('<span>Adicionado com sucesso</span><a class="btn-flat yellow-text" href="#!">OK<a>', 4000);
        },
        error: function () {
            Materialize.toast("Ocorreu um erro ao adicionar um amigo... Tente novamente mais tarde!");
        }
    });
}

/**
 * Create new Group
 *
 * @description Creates new group
 */
function addGroup() {

    var ativo = $('#ativo').is(':checked');
    var publico = $('#publico').is(':checked');
    var groupName = $('#groupName').val();
    var idUser = $('#idUser').val();

    $.ajax({
        url: '/addGroup',
        data: {"idUser": idUser, "publico": publico, "ativo": ativo, "nome": groupName},
        method: "GET",
        success: function (data) {

            if (data == "ok") {
                Materialize.toast('<span>Grupo adicionado com sucesso</span><a class="btn-flat yellow-text" href="#!">OK<a>', 4000);
            }

            return;
        },
        error: function () {
            Materialize.toast("Ocorreu um erro ao adicionar um grupo... Tente novamente mais tarde!");
        }
    });
}
