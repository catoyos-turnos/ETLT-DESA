$(function() {
	gen_mail_to_link('catoyos+ETLT', 'gmail.com', 'Sobre ETLT...');
	/*
});

$(window).ready(function() {
*/
	recuperaContadores();
});

function recuperaContadores() {
	var urlAux = $('#VAL_RAIZ').val() + '/ajax/contadores';
    $.ajax({
        url : urlAux,
        async : true,
        success :  function(data) {populaContadores(data)}
    });
}

function populaContadores(data) {
	data = data.trim();
	if(data=='' || data=='0') {
		$('.label-num-msg').html('');
	} else {
        $('.label-num-msg').html(data);
	}
}


function recuperaHorario() {
	var urlAux = $('#VAL_RAIZ').val() + '/ajax/contadores';
    $.ajax({
        url : urlAux,
        async : true,
        success : function(data) {
        	data = data.trim();
        	if(data=='' || data=='0') {
        		$('.label-num-msg').html('');
        	} else {
                $('.label-num-msg').html(data);
        	}
        }
    });
}

function populaHorario(data) {
	
}

/* escribe correo */
function gen_mail_to_link(lhs, rhs, subject) {
	$("#correo-par").html(' ');
	$("#correo-par").append('<a href=\"mailto:' + lhs + '@' + rhs
			+ '?subject=\'' + subject + '\'\"> catoyos</a>. 2016');
}